package com.Lobretimgap.NetworkClient.Threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import android.os.Parcel;
import android.os.Parcelable;

import com.Lobretimgap.NetworkClient.NetworkVariables;

public class NetworkWriteThread extends Thread
{	
	Socket socket;
	private OutputStream os;
	private ArrayBlockingQueue<Object> messageQueue;
	private boolean stopOperation = false;
	
	public NetworkWriteThread(Socket netSocket) throws IOException
	{
		socket = netSocket;
		os = socket.getOutputStream();
        messageQueue = new ArrayBlockingQueue<Object>(NetworkVariables.writeThreadBufferSize);
	}
	
	//Tries to add the message to the queue of messages waiting to be sent to
    //the client. If the message queue is full, it will return false, otherwise true.
    public <T extends Parcelable> boolean writeMessage(T message)
    {
        return messageQueue.offer(message);
    }

    @Override
    public void run()
    {
        while(!stopOperation)
        {
            try
            {
                Object message = messageQueue.take();
                final Parcel p1 = Parcel.obtain();
                p1.writeParcelable((Parcelable) message, 0);
                final byte [] bytes = p1.marshall();                
                os.write(bytes);
                os.flush();
            }
            catch(IOException e)
            {
                System.err.println("Failed to send object to client! \n"+e);
            }
            catch(InterruptedException e)
            {
                //We have been interrupted, so restart the loop.
                //This is used in shutdownThread, after setting stopOperation to true
                //To enforce an immediate thread shutdown.
            }
            
        }
    }

    public void shutdownThread()
    {
        stopOperation = true;
        this.interrupt();
    }
}
