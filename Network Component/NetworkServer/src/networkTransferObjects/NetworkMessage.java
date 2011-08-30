
package networkTransferObjects;

/**
 * Used to pass information between the client and the server
 * @date 2011/08/02
 * @author Lawrence Webley
 */
public class NetworkMessage
{

    //Used internally for network message classification.
    public enum MessageType //Comments show where the type can be received
    {
        UPDATE_MESSAGE, //Client and Server
        REQUEST_MESSAGE,//Client and Server
        INITIAL_GAME_STATE_MESSAGE, //Client only
        PARTIAL_GAMESTATE_UPDATE_MESSAGE, //Client only
        GAMESTATE_UPDATE_MESSAGE, //Client Only
        GAMESTATE_REQUEST_MESSAGE, //Server only
        TERMINATION_REQUEST_MESSAGE, //Server Only
        PEER_LIST_MESSAGE, //Client only
        PEER_LIST_REQUEST_MESSAGE, //Server Only
        LATENCY_REQUEST_MESSAGE, //Server & Client.
        LATENCY_RESPONSE_MESSAGE //Server & Client

    }

    private MessageType messageType;
    private String primeMessage;

    public NetworkMessage(String message)
    {
        primeMessage = message;
    }

    public NetworkMessage()
    {
        primeMessage = "";
    }

    public String getMessage()
    {
        return primeMessage;
    }

    //Used internally for network message classification. Don't use this.
    public void setMessageType(MessageType mType)
    {
        messageType = mType;
    }

    public MessageType getMessageType()
    {
        return messageType;
    }
    
}
