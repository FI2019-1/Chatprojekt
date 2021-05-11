package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

public class ServerThread extends Thread
{
		private Server s;
		private int port;
		
		public ServerThread (Server s, int port)
		{
			this.s = s;
			this.port = port;
		}
		
		@Override
		public void run()
		{
			System.out.println("Server l�uft");
			ServerSocket socket = null;
			try
			{
				socket = new ServerSocket(port);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			
			while(!isInterrupted() && socket != null)
			{
				
				try
				{
					Thread.sleep(100);
					socket.setSoTimeout(100);
					s.akzeptiereClient(new ClientProxy(s, socket.accept()));
				}
				catch (SocketTimeoutException e)
				{
					
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (InterruptedException e)
				{
					interrupt();
					try
					{
						socket.close();
						System.out.println("Server wird beendet");
					}
					catch (IOException ex)
					{
						ex.printStackTrace();
					}
				}
				
			}
			try
			{
				socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			socket = null;
		}
	}