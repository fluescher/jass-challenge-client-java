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
        final Player localPlayer = new Player("localPlayer");

        final Player foundPlayer = new PlayerMapper(localPlayer).mapPlayer(new RemotePlayer(0, "localPlayer"));

        assertThat(foundPlayer, equalTo(localPlayer));
    }

    @Test
    public void mapPlayer_returnsNewlyCreatedPlayer() {
        final Player localPlayer = new Player("localPlayer");

        final Player foundPlayer = new PlayerMapper(localPlayer).mapPlayer(new RemotePlayer(0, "unknown"));

        assertThat(foundPlayer, not(equalTo(localPlayer)));
    }

    @Test
    public void mapPlayer_returnsPlayer_afterAUnknownPlayerWasMapped() {
        final Player localPlayer = new Player("localPlayer");
        final PlayerMapper playerMapper = new PlayerMapper(localPlayer);
        playerMapper.mapPlayer(new RemotePlayer(0, "will be created"));

        final Player foundPlayer = playerMapper.findPlayerByName("will be created");

        assertThat(foundPlayer.getName(), equalTo("will be created"));
    }

    @Test
    public void findPlayerByName_returnsFoundPlayer() {
        final Player localPlayer = new Player("localPlayer");

        final Player foundPlayer = new PlayerMapper(localPlayer).findPlayerByName("localPlayer");

        assertThat(foundPlayer, equalTo(localPlayer));
    }

    @Test(expected = RuntimeException.class)
    public void findPlayerByName_throwsException_ifNoSuchPlayerExists() {
        new PlayerMapper(new Player("localPlayer")).findPlayerByName("unknown");
    }

}