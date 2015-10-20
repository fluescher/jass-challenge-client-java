package com.zuehlke.jasschallenge.localserver;


import com.zuehlke.jasschallenge.game.Trumpf;
import com.zuehlke.jasschallenge.game.cards.Card;
import com.zuehlke.jasschallenge.game.cards.Color;
import com.zuehlke.jasschallenge.game.mode.Mode;
import com.zuehlke.jasschallenge.messages.Mapping;
import com.zuehlke.jasschallenge.messages.PlayedCards;
import com.zuehlke.jasschallenge.messages.RejectCard;
import com.zuehlke.jasschallenge.messages.RequestCard;
import com.zuehlke.jasschallenge.messages.responses.ChooseCard;
import com.zuehlke.jasschallenge.messages.type.RemoteCard;
import com.zuehlke.jasschallenge.messages.type.RemoteColor;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

class Round {

    private static final Random SHUFFLER = new SecureRandom();

    private final Map<Player, List<Card>> cardsMap;
    private boolean shifted = false;
    private Color trumpfColor;
    private Trumpf trumpf;
    private Map<Card, Player> playedCards;
    private int numberOfPlayedStiche = 0;


    public Round(Player player1Team1, Player player1Team2, Player player2Team1, Player player2Team2) {
        List<Card> cards = asList(Card.values());
        Collections.shuffle(cards, SHUFFLER);
        cardsMap = new HashMap<>();
        cardsMap.put(player1Team1, new ArrayList<>(cards.subList(0, 9)));
        cardsMap.put(player2Team1, new ArrayList<>(cards.subList(9, 18)));
        cardsMap.put(player1Team2, new ArrayList<>(cards.subList(18, 27)));
        cardsMap.put(player2Team2, new ArrayList<>(cards.subList(27, 36)));
        playedCards = new HashMap<>();
    }

    public List<Card> getCards(Player player) {
        return cardsMap.get(player);
    }

    public void setShifted() {
        if (shifted) {
            throw new AlreadyGschobeException();
        }
        shifted = true;
    }

    public void setTrumpf(Trumpf mode, Color color) {
        this.trumpf = mode;
        this.trumpfColor = color;
    }

    public StichResult playStich(Players players) {
        List<Card> playedCardsInOrder = new LinkedList<>();
        playedCards = new HashMap<>();
        int cards = 0;
        while (++cards <= 4) {
            Player nextToPlay = players.getNextToPlay();
            List<Card> playerCards = cardsMap.get(nextToPlay);
            ChooseCard chooseCard = nextToPlay.ask(new RequestCard(), ChooseCard.class);
            RemoteCard data = chooseCard.getData();
            Card playedCard = Mapping.mapToCard(data);
            playedCardsInOrder.add(playedCard);

            if ( ! getMode().canPlayCard(playedCard, playedCards.keySet(), playedCardsInOrder.isEmpty() ? null : playedCardsInOrder.get(0).getColor(), playerCards.stream().collect(Collectors.toSet()))) {
                nextToPlay.notify(new RejectCard(data));
                throw new RuntimeException("Illegal card by player " + nextToPlay);
            }

            playedCards.put(playedCard, nextToPlay);
            Function<Card, RemoteCard> cardToRemoteCardFunction = c -> new RemoteCard(c.getValue().getRank() + 5, RemoteColor.from(c.getColor()));
            cardsMap.keySet().forEach(player -> player.notify(new PlayedCards(playedCardsInOrder.stream().map(cardToRemoteCardFunction).collect(Collectors.toList()))));
            playerCards.remove(playedCard);
        }
        return new StichResult(playedCardsInOrder, playedCards, trumpf, trumpfColor, numberOfPlayedStiche++);
    }


    public boolean hasCardsToPlay() {
        return cardsMap.values().stream().flatMap(Collection::stream).findAny().isPresent();
    }

    public Mode getMode() {
        return Mode.from(trumpf, trumpfColor);
    }

}
