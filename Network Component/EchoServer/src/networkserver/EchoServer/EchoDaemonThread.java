/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkserver.EchoServer;

import java.util.ArrayList;
import java.util.Vector;
import networkTransferObjects.NetworkMessage;
import networkTransferObjects.PlayerRegistrationMessage;
import networkserver.EventListeners.TerminationRequestReceivedListener;
import networkserver.EventListeners.UpdateReceivedListener;
import networkserver.Events.NetworkEvent;
import networkserver.Peer2Peer.ClientPeer;
import networkserver.ServerVariables;
import networkserver.Threads.ServerDaemonThread;

/**
 *
 * @author lwebley
 */
public class EchoDaemonThread extends ServerDaemonThread{
    
    public static ArrayList<EchoDaemonThread> playerThreads = new ArrayList<EchoDaemonThread>(4);
    
    public EchoDaemonThread()
    {
        super();        
        playerThreads.add(this);
        signUpForListeners();
    }
    
    private void signUpForListeners()
    {       
        this.addNetworkListener(UpdateReceivedListener.class, new UpdateReceivedListener() {
            public void EventOccured(NetworkEvent e) {
                
                int senderID = ((ServerDaemonThread)e.getSource()).playerID;
                for(int i = 0; i < playerThreads.size();i++)
                {                   
                    if(playerThreads.get(i).playerID != senderID)
                    {
                        playerThreads.get(i).sendGameUpdate((NetworkMessage)e.getMessage());
                    }
                }
            }
        });
        
        this.addTerminationRequestReceivedListener(new TerminationRequestReceivedListener() {
            public void EventOccured(NetworkEvent e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @Override
    protected void registerPlayer(PlayerRegistrationMessage initialMessage) {
        if(ServerVariables.playerNetworkAddressList.size() > 1)
        {
            NetworkMessage msg = new NetworkMessage("Second player available for battle");
            this.sendGameUpdate(msg);
        }
    }

    @Override
    protected NetworkMessage getInitialState() {
        return new NetworkMessage("Initial State");
    }

    @Override
    protected Vector<ClientPeer> getPeerList(int playerId, String playerName) {
        return new Vector<ClientPeer>();
    }    
}