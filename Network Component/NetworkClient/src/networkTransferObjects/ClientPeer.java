
package networkTransferObjects;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.Lobretimgap.NetworkClient.NetworkVariables;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * @date 2011/08/02
 * @author Lawrence Webley
 */
public class ClientPeer implements Parcelable {
    
	public String playerName;
    public int playerId;
    public InetAddress networkAddress;
    //Port that the client is communicating through
    public int networkPort;

    public ClientPeer(int playerId, String playerName)
    {
        this.playerId = playerId;
        this.playerName = playerName;        
    }

	public int describeContents() {	
		return 3;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(playerName);
		dest.writeInt(playerId);
		dest.writeByteArray(networkAddress.getAddress());
		dest.writeInt(networkPort);
	}    
	
	public static final Parcelable.Creator<ClientPeer> CREATOR = new Creator<ClientPeer>() {
		
		public ClientPeer[] newArray(int size) {
			return new ClientPeer[size];
		}
		
		public ClientPeer createFromParcel(Parcel source) {
			return new ClientPeer(source);
		}
	};
	
	private ClientPeer(Parcel source)
	{
		playerName = source.readString();
		playerId = source.readInt();
		try {
			networkAddress = InetAddress.getByAddress(source.createByteArray());
			networkPort = source.readInt();
		} catch (UnknownHostException e) {
			Log.w(NetworkVariables.TAG, "Failed to resolve peer IP address... ", e);
		}
	}
}
