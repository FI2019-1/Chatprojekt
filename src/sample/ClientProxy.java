package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.DefaultListModel;

public class ClientProxy implements Runnable
{
	private Socket aSocket;
	private Server aServer;
	private InputStream in;
	private OutputStream out;
	private BufferedReader reader;
	private Thread t;
	
	
	public ClientProxy(Server s, Socket socket)
	{
		this.aServer = s;
		this.aSocket = socket;
		
		try
		{
			OutputStream out = socket.getOutputStream();
			
			InputStream in = socket.getInputStream();
			reader = new BufferedReader (new InputStreamReader(in));
			
			t = new Thread(this);
			t.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	protected void empfangeNachricht()
	{
		try
		{
			String s;
			if ((s = reader.readLine()) != null)
			System.out.println("Empfangen vom Server: " + s);
			//nachrichtenListe.addElement(s);
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	protected void sendeNachricht (String s) throws IOException 
	{
		if (out !=null)
		{
			PrintWriter writer = new PrintWriter(out);
			writer.write(s);
			writer.write("\n");
			writer.flush();	
		}
	}
	
	private void abmeldenClientProxy() throws IOException
	{
		if (t.isAlive())
			t.interrupt();
		if (in != null)
			in.close();
		if (out != null)
			out.close();
		if (aSocket != null)
			aSocket.close();		
	}
	
	
	

	@Override
	public void run()
	{
		System.out.println("Client angemeldet");
		while(!t.isInterrupted())
		{
			try
			{
				
				Thread.sleep(100);
				empfangeNachricht();
			}
			catch (InterruptedException e)
			{
				t.interrupt();
			}
		}
		
		// TODO Auto-generated method stub
		
	}

}
