package sample;


import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client extends Thread
{

	private Socket aSocket;
	private InputStream in;
	private OutputStream out;
	private boolean verbunden;

	private ArrayList<ClientProxy> clientList;

	private PrintWriter writer;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					//ClientGUI frame = new ClientGUI();
					//frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/*public Client(ClientGUI aClientGUI)
	{
		this.aClientGUI = aClientGUI;

	}*/

	/*public Client(JLabel lblNachrichten, JLabel lblPortnr, JTextField tFport, JList<String> list, JTextField tFEingabe,
			JLabel lbStatus)
	{
		this.lblNachrichten = lblNachrichten;
		this.lblPortnr = lblPortnr;
		this.tFport = tFport;
		this.list = list;
		this.lbStatus = lbStatus;
	}*/

	public void empfangeNachricht()
	{
		String test;

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		try
		{
			test = ("Empfangen vom Server" + reader.readLine());
			updateNachricht(test);
			System.out.println("Empfangen vom Server" + reader.readLine());

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void trenne() throws IOException
	{
		this.interrupt();
		in.close();
		out.close();
		aSocket.close();
		if (aSocket == null)
		{
			verbunden = false;

		}
	}

	public void sendeNachricht(String s)
	{
		writer.write(s + "\n");
		updateNachricht(s);
		writer.flush();

	}
	
	public void updateNachricht(String s)
	{
		if (s != null)
		{
			System.out.println("Test");
			//aClientGUI.zeigeNachricht(s);
		}
	}

	public void verbinde(String ip, int port) throws UnknownHostException, IOException
	{

		aSocket = new Socket(ip, port);

		if (aSocket != null)
		{
			in = aSocket.getInputStream();
			out = aSocket.getOutputStream();
			writer = new PrintWriter(out);
			verbunden = true;

		}
	}

	public void connect()
	{
		String ip = "localhost";
		int port = 8008;

		try
		{
			verbinde(ip, port);
			System.out.println("Verbindung wurde hergestellt!");

		}
		catch (UnknownHostException e)
		{
			System.out.println("Unbekannte IP");
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Verbindungsaufbau");
		}

	}

	protected boolean isVerbunden()
	{
		return verbunden;
	}

}
//Test
