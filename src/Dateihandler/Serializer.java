package Dateihandler;

import Client.Nachricht;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Serializer
{
	public Serializer()
	{
		
	}
	public void serialisierung(Nachricht n)
	{
		try
		{
			OutputStream fos = null;
			try
			{
				fos = new FileOutputStream("Test.ser");
				ObjectOutputStream o = new ObjectOutputStream(fos);
				o.writeObject(n);
				
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			finally 
			{
				fos.close();
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
			InputStream fis = null;
			try
			{
				fis = new FileInputStream("Test.ser");
				
				ObjectInputStream o = new ObjectInputStream(fis);
				Nachricht n = ((Nachricht)o.readObject());
				return n;
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			finally
			{
				fis.close();
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		return null;
	}
	
	
}
