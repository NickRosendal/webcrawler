package startup;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.UIManager;

import Gui.*;
import javax.swing.UIManager.LookAndFeelInfo;

import serialization.Serilizar;
import domain.Website;
import domain.WebsiteInformation;

import javax.swing.UIManager.*;
import javax.swing.UIManager;

public class Start
{
	public static ArrayList<Website> websitelist = new ArrayList();
	public static Website theVoice;
	public static Website Nrj;
	public static Website dRP3Website;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// set look n feel

		try
		{
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if ("Metal".equals(info.getName()))

				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e)
		{
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}

		// loads thevoice website from disk (WE DONT CHECK FOR NEW STUFF YET)
//		theVoice = new Website("The Voice");
//		websitelist.add(theVoice);
//		WebsiteInformation theVoiceWebsiteInformation = new WebsiteInformation("http://www.thevoice.dk/component/nowplaying/?dp=#under#&day=#year#-#month#-#day#",
//				"#day#", "#month#", "#year#", "#under#", "int", Pattern.compile("<span class=\"artist\">(.*)</span>[\\w\\W]+?<span class=\"titel\">(.*)+?</span>"));
//		theVoiceWebsiteInformation.setIconPath("icons/theVoice.gif");
//		theVoice.setMyInformation(theVoiceWebsiteInformation);
//		Serilizar projectSerilizar = new Serilizar();
//		try
//		{
//			projectSerilizar.loadWebsiteSongs(theVoice);
//		} catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Serilizar projectSerilizar = new Serilizar();
		theVoice = new Website("The Voice");
		
		theVoice = projectSerilizar.loadAllWebsites().get(2);
		
		System.out.println("getIconPath " + theVoice.getMyInformation().getIconPath());
		websitelist.add(theVoice);
		try
		{
			projectSerilizar.loadWebsiteSongs(theVoice);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// loads NRJgermany form disk
		Nrj = new Website("NRJGermany");
		websitelist.add(Nrj);
		WebsiteInformation NRJWebsiteInformation = new WebsiteInformation(
				"http://www.energy.de/index.php?eID=energyplaylistfl&datepicker=#day#.#month#.#year#&startTime=#under#%3A00&station=berlin", "#day#", "#month#",
				"#year#", "#under#", "NRJ",
				Pattern.compile("\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td>(?!\\d{2}(?:\\.|:)\\d{2}|<img|\\\\)(.*?)<\\\\/td>\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td id=\\\\\"openWebradioStationid\\\\\"><span id=\\\\\"1\\\\\" style=\\\\\"color: rgb\\(226, 0, 26\\);\\\\\">Webradio starten<span><\\\\/td>\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td>(.*?)<\\\\/td>"));
			//  Pattern.compile("\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td>(?!<img|\\d{2}.\\d{2}}|\\\\n)(.*?)<\\\\/td>.*?\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td>(.*?)<\\\\/td>(?<=<\\\\/td>)"));
				//Pattern.compile("\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td>(?!<img|\\d|\\\\n)(.*?)<\\\\/td>.*?\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td>(.*?)<\\\\/td>(?<=<\\\\/td>)"));
		NRJWebsiteInformation.setIconPath("icons/nrj.gif");
		Nrj.setMyInformation(NRJWebsiteInformation);
		try
		{
			projectSerilizar.loadWebsiteSongs(Nrj);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// loads DR P3 form disk
				dRP3Website = new Website("DR P3");
				websitelist.add(dRP3Website);
				
				WebsiteInformation drP3WebsiteInformation;
				drP3WebsiteInformation = new WebsiteInformation();
				drP3WebsiteInformation.setUnderPageURLType("DR");
				
				drP3WebsiteInformation.setMainUrl("http://www.dr.dk/Playlister/feeds/searchSimple/searchSimple.drxml?cid=P3&d=#day#&m=#month#&y=#year#");
				drP3WebsiteInformation.setUnderPageURL("http://www.dr.dk/Playlister/feeds/getPlaylist/getPlaylist.drxml?pid=#pid#&date=#year#-#month#-#day#");
				
				drP3WebsiteInformation.setYear("#year#");
				drP3WebsiteInformation.setMonth("#month#");
				drP3WebsiteInformation.setDay("#day#");
				
				//drP3WebsiteInformation.setRegExMusicNumberPattern(Pattern.compile("<span class=\"artist\">(.*?)</span>(| )</span>(| )<span><span class=\"title\">(.*?)</span>"));
				drP3WebsiteInformation.setRegExMusicNumberPattern(Pattern.compile("<span class=\"artist\">(.*?)</span></span><span><span class=\"title\">(.*?)</span>"));
				drP3WebsiteInformation.setIconPath("icons/nrj.gif");
				
				dRP3Website.setMyInformation(drP3WebsiteInformation);
				
				try
				{
					projectSerilizar.loadWebsiteSongs(dRP3Website);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
		
		
		// draw our main gui window
				MainGui myMainGui1 = new MainGui();
		// adds, our mainGui, as observers of the website
		for (Website web : websitelist)
		{
			web.registerObserver(myMainGui1);
		}
		// theVoice.registerObserver(myMainGui);
		// Nrj.registerObserver(myMainGui);

	}
}
