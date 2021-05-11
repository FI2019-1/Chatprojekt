package sample;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

public class Server
{
	private ServerThread t = null;
	//private ServerGUI aServerGUI;
	private JLabel lblNewLabel;
	private JList<String> list;
	private JLabel lblStatusServer;
	private JButton btnStartServer;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					//ServerGUI frame = new ServerGUI();
					//frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	//public Server(ServerGUI aServerGui)
	{
	//	this.aServerGUI = aServerGui;
	}

	/*public Server(JLabel lblNewLabel, JList<String> list, JLabel lblStatusServer)
	{
		this.lblNewLabel = lblNewLabel;
		this.list = list;
		this.lblStatusServer = lblStatusServer;

	}*/

	protected void starten(int port)
	{
		t = new ServerThread(this, port);
		t.start();
	}

	protected void beenden()
	{
		if (t != null)
		{

			t.interrupt();
			t = null;
		}
	}

	protected void akzeptiereClient(ClientProxy c)
	{
		System.out.println("Client wurde akzeptiert");
		// Liste hinzuf�gen add.c
	}

}
