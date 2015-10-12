package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemotePlayer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PlayerMapperTest {

    @Test
    public void mapPlayer_returnsExistingPlayer() {
        final Player localPlayer = new Player(0, "localPlayer");

        final Player foundPlayer = new PlayerMapper(localPlayer).mapPlayer(new RemotePlayer(0, "localPlayer"));

        assertThat(foundPlayer, equalTo(localPlayer));
    }

    @Test
    public void mapPlayer_returnsNewlyCreatedPlayer() {
        final Player localPlayer = new Player(1, "localPlayer");

        final Player foundPlayer = new PlayerMapper(localPlayer).mapPlayer(new RemotePlayer(0, "unknown"));

        assertThat(foundPlayer, not(equalTo(localPlayer)));
    }

    @Test
    public void mapPlayer_returnsPlayer_afterAUnknownPlayerWasMapped() {
        final Player localPlayer = new Player("localPlayer");
        final PlayerMapper playerMapper = new PlayerMapper(localPlayer);
        playerMapper.mapPlayer(new RemotePlayer(0, "will be created"));

        final Player foundPlayer = playerMapper.findPlayerById(0);

        assertThat(foundPlayer.getId(), equalTo(0));
    }

    @Test
    public void findPlayerByName_returnsFoundPlayer() {
        final Player localPlayer = new Player(1, "localPlayer");

        final Player foundPlayer = new PlayerMapper(localPlayer).findPlayerById(1);

        assertThat(foundPlayer, equalTo(localPlayer));
    }

    @Test(expected = RuntimeException.class)
    public void findPlayerByName_throwsException_ifNoSuchPlayerExists() {
        new PlayerMapper(new Player(1, "localPlayer")).findPlayerById(2);
    }

}