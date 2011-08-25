/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package networkserver.Threads;

import java.util.ArrayList;
import networkTransferObjects.ClientPeer;
import networkTransferObjects.NetworkMessage;
import networkTransferObjects.PlayerRegistrationMessage;


/**
 * @date 2011/08/11
 * @author Lawrence Webley
 */
public class CustomDaemonThread extends ServerDaemonThread
{  

    @Override
    protected void registerPlayer(PlayerRegistrationMessage initialMessage) {
        System.out.println("Player registed: ID="+initialMessage.playerID+", Name="+initialMessage.playerName);
    }

    @Override
    protected NetworkMessage getInitialState() {
        return new NetworkMessage("Initial state not yet configured.");
    }

    @Override
    protected ArrayList<ClientPeer> getPeerList(int playerId, String playerName) {
        return new ArrayList<ClientPeer>();
    }

}
