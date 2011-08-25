package com.Lobretimgap.NetworkClient;

import com.Lobretimgap.NetworkClient.Threads.CoreNetworkThread;
import com.Lobretimgap.NetworkClient.Implementation.CoreNetworkThreadImpl;

public class NetworkVariables {
	public static final String TAG = "NetworkClient";
	public static final int port = 10282;
	public static final String hostname = "192.168.42.101";//"blue.cs.uct.ac.za";
	public static final int writeThreadBufferSize = 16;
	public static final int initialNetworkMessageMapSize = 8;
	
	//Replace CoreNetworkThread.class with a concrete implementation of that class.
	public static final Class<CoreNetworkThreadImpl> coreNetworkThreadClass = CoreNetworkThreadImpl.class;
	//64 kilobytes of input buffer. Should be enough for most objects.
	public static final int InputBufferSize = 64 * 1024;
	
	/**
	 * Uses the class type above to create a concrete instance of the CoreNetworkThread 
	 * class.
	 * @return Returns an instance of the CoreNetworkThread implementation
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static CoreNetworkThread getInstance() throws IllegalAccessException, InstantiationException
	{
		return (CoreNetworkThread) coreNetworkThreadClass.newInstance();
	}
	
}
