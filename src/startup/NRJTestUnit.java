package startup;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import serialization.Serilizar;

import datamining.DayCrawler;
import datamining.MusicNumberFormatter;
import domain.MusicNumber;
import domain.Website;
import domain.WebsiteInformation;

public class NRJTestUnit
{
	Website testWebsite;

	@Before
	public void setUp() throws Exception
	{
		testWebsite = new Website("NRJGermany");
	}
	@Test
	public void feelingALittleLazy(){
		Serilizar mySerilizar = new Serilizar();
		mySerilizar.loadAllWebsites();
		assertTrue(false);
	}
	// @Test
	public void AWebsiteCanLoadDataFromDisk()
	{
		Serilizar mySerilizar = new Serilizar();
		boolean loadWebsiteSongsSuccess = false;
		try
		{
			mySerilizar.loadWebsiteSongs(testWebsite);
			loadWebsiteSongsSuccess = true;

		} catch (Exception e)
		{
			e.getMessage();
		}
		assertTrue(loadWebsiteSongsSuccess);
	}

	// @Test
	public void AWebsiteCanStillCrawlTheVoiceAnStore()
	{
		WebsiteInformation theVoiceWebsiteInformation = new WebsiteInformation(
				"http://www.thevoice.dk/component/nowplaying/?dp=#under#&day=#year#-#month#-#day#", "#day#", "#month#", "#year#", "#under#", "int",
				Pattern.compile("<span class=\"artist\">(.*)</span>[\\w\\W]+?<span class=\"titel\">(.*)+?</span>"));
		// LocalDate myDate = new LocalDate(2012, 4, 1);
		
		// int readMusicnumbersCount = myCrawler.crawlDay(myDate).size();
		// assertTrue(0 < readMusicnumbersCount);

		Website theVoice = new Website("The Voice");
		DayCrawler myCrawler = new DayCrawler(theVoiceWebsiteInformation,theVoice.getWebsiteTitle());
		LocalDate dayToStartRead = new LocalDate(2012, 6, 15);
		;
		LocalDate dayToReadTo = LocalDate.now().plusDays(1);

		while (dayToStartRead.isBefore(dayToReadTo))
		{
			theVoice.addDay(dayToStartRead, myCrawler.crawlDay(dayToStartRead));
			dayToStartRead = dayToStartRead.plusDays(1);
		}

		Serilizar projectSerilizar = new Serilizar();
		try
		{
			projectSerilizar.storeWebsite(theVoice);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//@Test
	public void ANRJWebsiteCanCrawlADayAtNRJ()
	{
		WebsiteInformation NRJWebsiteInformation = new WebsiteInformation(
				"http://www.energy.de/index.php?eID=energyplaylistfl&datepicker=#day#.#month#.#year#&startTime=#under#%3A00&station=berlin",
				"#day#",
				"#month#",
				"#year#",
				"#under#",
				"NRJ",
				Pattern.compile("\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td>(?!<img|\\d|\\\\n)(.*?)<\\\\/td>.*?\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td>(.*?)<\\\\/td>(?<=<\\\\/td>)"));
		DayCrawler myCrawler = new DayCrawler(NRJWebsiteInformation, "NRJ");
		LocalDate myDate = new LocalDate(2012, 1, 2);
		int readMusicnumbersCount = myCrawler.crawlDay(myDate).size();
		assertTrue(0 < readMusicnumbersCount);

	}

	//@Test
//	public void canTranslateThingyes()
//	{
//		MusicNumberFormatter myMusicNumberFormatter = new MusicNumberFormatter();
//		MusicNumber myMusicNumber = new MusicNumber("", "Snoop Dogg vs. David Guetta : Sweat\\/Wet ");
//		myMusicNumberFormatter.checkAndCorrect(myMusicNumber);
//	}

//	//@Test
//	public void ANRJWebsiteCanStoreADateOnDisk()
//	{
//		WebsiteInformation NRJWebsiteInformation = new WebsiteInformation(
//				"http://www.energy.de/index.php?eID=energyplaylistfl&datepicker=#day#.#month#.#year#&startTime=#under#%3A00&station=berlin",
//				"#day#",
//				"#month#",
//				"#year#",
//				"#under#",
//				"NRJ",
//				Pattern.compile("\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td>(?!<img|\\d|\\\\n)(.*?)<\\\\/td>.*?\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<td>(.*?)<\\\\/td>(?<=<\\\\/td>)"));
//
//		DayCrawler myCrawler = new DayCrawler(NRJWebsiteInformation);
//
//		LocalDate firstDate = new LocalDate(2012, 1, 2);
//		LocalDate secoundDate = new LocalDate(2012, 3, 3);
//
//		// testWebsite.addDay(firstDate, myCrawler.crawlDay(firstDate));
//		testWebsite.addDay(secoundDate, myCrawler.crawlDay(secoundDate));
//		Serilizar projectSerilizar = new Serilizar();
//		boolean weDidStore = false;
//		try
//		{
//			projectSerilizar.storeWebsite(testWebsite);
//			weDidStore = true;
//
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		assertTrue(weDidStore);
//	}
	
}
