package testArea;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class SendCommandsToBrowser
{

	public void staticSend(String Url_)
	{

		if (Desktop.isDesktopSupported())
		{
			try
			{
				System.out.println(Url_);
				Desktop.getDesktop().browse(new URI(Url_));
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				
			} catch (URISyntaxException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}