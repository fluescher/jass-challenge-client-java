package com.zuehlke.jasschallenge.localserver;


import com.zuehlke.jasschallenge.game.Trumpf;
import com.zuehlke.jasschallenge.game.cards.Card;
import com.zuehlke.jasschallenge.game.cards.Color;
import com.zuehlke.jasschallenge.messages.Mapping;
import com.zuehlke.jasschallenge.messages.PlayedCards;
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

    private Map<Player, List<Card>> cardsMap;
    private boolean shifted = false;
    private Color trumpfColor;
    private Trumpf trumpf;
    private Map<Card, Player> playedCards;
    private int numberOfPlayedRounds;


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
            throw new AlreadyGschobeException("Already gschobe");
        }
        shifted = true;
    }

    public void setTrumpf(Trumpf mode, Color color) {
        this.trumpf = mode;
        this.trumpfColor = color;
    }

    private boolean isFinished() {
        return playedCards.size() == 4;
    }

    public StichResult playStich(Players players) {
        List<Card> playedCardsInOrder = new LinkedList<>();
        playedCards = new HashMap<>();
        while (!isFinished()) {
            Player nextToPlay = players.getNextToPlay();
            ChooseCard chooseCard = nextToPlay.ask(new RequestCard(), ChooseCard.class);
            // TODO check if played card is valid
            RemoteCard data = chooseCard.getData();
            RemoteCard card = new RemoteCard(data.getNumber(), data.getColor());
            Card playedCard = Mapping.mapToCard(card);
            playedCardsInOrder.add(playedCard);
            playedCards.put(playedCard, nextToPlay);
            Function<Card, RemoteCard> cardToRemoteCardFunction = c -> new RemoteCard(c.getValue().getRank() + 5, RemoteColor.from(c.getColor()));
            cardsMap.keySet().forEach(player -> player.notify(new PlayedCards(playedCardsInOrder.stream().map(cardToRemoteCardFunction).collect(Collectors.toList()))));
            cardsMap.get(nextToPlay).remove(playedCard);
        }
        return new StichResult(playedCardsInOrder, playedCards, trumpf, trumpfColor, ++numberOfPlayedRounds);
    }



    public boolean hasCardsToPlay() {
        return  playedCards != null && playedCards.size() < 4;
    }
}
