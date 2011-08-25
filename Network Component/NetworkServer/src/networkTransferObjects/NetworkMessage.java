package networkTransferObjects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import networkserver.ServerCustomisation;

/**
 * Used to pass information between the client and the server
 * @date 2011/08/02
 * @author Lawrence Webley
 */
public class NetworkMessage implements Parcelable {
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
    private Bundle messageStorage;
    private MessageType messageType;
    private String primeMessage;

    public NetworkMessage(String message) {
        primeMessage = message;
        setMessageStorage(new Bundle(ServerCustomisation.initialNetworkMessageMapSize));
    }

    public String getMessage() {
        return primeMessage;
    }

    //Used internally for network message classification. Don't use this.
    public void setMessageType(MessageType mType) {
        messageType = mType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @param messageStorage the messageStorage to set
     */
    public void setMessageStorage(Bundle messageStorage) {
        this.messageStorage = messageStorage;
    }

    /**
     * @return the messageStorage
     */
    public Bundle getMessageStorage() {
        return messageStorage;
    }

    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        // Write our objects values to the parcel

        //We start with our message type.
        out.writeInt(messageType.ordinal());
        //Now our prime message
        out.writeString(primeMessage);
        //now our message storage
        out.writeBundle(messageStorage);
    }
    public static final Parcelable.Creator<NetworkMessage> CREATOR = new Parcelable.Creator<NetworkMessage>() {

        public NetworkMessage createFromParcel(Parcel source) {
            return new NetworkMessage(source);
        }

        public NetworkMessage[] newArray(int size) {
            return new NetworkMessage[size];
        }
    };

    /* Private constructor, that allows the CREATOR to create an instance
     * of the NetworkMessage using a received parcel
     */
    private NetworkMessage(Parcel source) {
        messageType = MessageType.values()[source.readInt()];
        primeMessage = source.readString();
        messageStorage = source.readBundle();
    }
}
