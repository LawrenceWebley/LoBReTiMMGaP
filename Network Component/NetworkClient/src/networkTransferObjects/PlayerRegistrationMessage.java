
package networkTransferObjects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.Lobretimgap.NetworkClient.NetworkVariables;

/**
 * A once off message sent to the server which should contain any initialisation information
 * required to create an instance of the player in the servers game world. This should at least
 * include a player name and id, but could also include data such as starting locations, experience,
 * initial client states, etc.
 * @date 2011/08/02
 * @author Lawrence Webley
 */
public class PlayerRegistrationMessage implements Parcelable{
    
	public String playerName;
    public int playerID;
    
    private Bundle messageStorage;
    
    public PlayerRegistrationMessage(int playerId, String playerName)
    {
    	playerID = playerId;
    	this.playerName = playerName;
    	setMessageStorage(new Bundle(NetworkVariables.initialNetworkMessageMapSize));
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
		return 1;
	}


	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(playerName);
		dest.writeInt(playerID);
		dest.writeBundle(messageStorage);
	}
	
	public static final Parcelable.Creator<PlayerRegistrationMessage> CREATOR = new Parcelable.Creator<PlayerRegistrationMessage>() {

		public PlayerRegistrationMessage createFromParcel(Parcel source) {
			return new PlayerRegistrationMessage(source);
		}

		public PlayerRegistrationMessage[] newArray(int size) {
			return new PlayerRegistrationMessage[size];
		}		
	};
	
	private PlayerRegistrationMessage(Parcel source)
	{
		playerName = source.readString();
		playerID = source.readInt();
		messageStorage = source.readBundle();
	}
}
