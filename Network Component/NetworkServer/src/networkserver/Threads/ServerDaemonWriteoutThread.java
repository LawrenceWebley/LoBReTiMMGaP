package networkserver.Threads;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import networkserver.ServerCustomisation;

/**
 * This thread allows the user to write an object to the client asynchronously.
 * Internally it adds the object to be written to a queue, and sends items over the
 * network in a FIFO fashion
 * @date 2011/08/02
 * @author Lawrence Webley
 */
public class ServerDaemonWriteoutThread extends Thread
{
    private Socket socket;
    private OutputStream os;
    private ArrayBlockingQueue<Object> messageQueue;
    private boolean stopOperation = false;

    

    public ServerDaemonWriteoutThread(Socket writeOutSocket) throws IOException
    {
        socket = writeOutSocket;
        os = socket.getOutputStream();
        messageQueue = new ArrayBlockingQueue<Object>(ServerCustomisation.threadWriteOutBufferSize);
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
