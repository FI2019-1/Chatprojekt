package Gemeinsam;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Serializer
{
	private Socket client;
	public Serializer(Socket client)
	{
		this.client = client;
	}
	public void serialisierung(Nachricht n)
	{
		try
		{
			try
			{
				//fos = new FileOutputStream("Test.ser");
				ObjectOutputStream o = new ObjectOutputStream(client.getOutputStream());
				o.writeObject(n);

			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			finally
			{
				//fos.close();
			}
		}
		catch(Exception ex)
		{
			ex.getMessage();
		}
	}
	public Nachricht deserialisierung() throws IOException
	{
		try
		{
			//InputStream fis = null;
			try
			{
				ObjectInputStream o = new ObjectInputStream(client.getInputStream());
				Nachricht n = ((Nachricht)o.readObject());
				return n;
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			finally
			{
				//fis.close();
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}

		return null;
	}


}