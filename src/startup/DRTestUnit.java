package startup;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import datamining.DayCrawler;
import domain.Website;
import domain.WebsiteInformation;

public class DRTestUnit
{
	Website testWebsite;
	WebsiteInformation testWebsiteInformation;
	@Before
	public void setUp() throws Exception
	{
		testWebsite = new Website("DR P3");
		
		testWebsiteInformation = new WebsiteInformation();
		testWebsiteInformation.setUnderPageURLType("DR");
		
		testWebsiteInformation.setMainUrl("http://www.dr.dk/Playlister/feeds/searchSimple/searchSimple.drxml?cid=P3&d=#day#&m=#month#&y=#year#");
		testWebsiteInformation.setUnderPageURL("http://www.dr.dk/Playlister/feeds/getPlaylist/getPlaylist.drxml?pid=#pid#&date=#year#-#month#-#day#");
		
		testWebsiteInformation.setYear("#year#");
		testWebsiteInformation.setMonth("#month#");
		testWebsiteInformation.setDay("#day#");
		
		testWebsiteInformation.setRegExMusicNumberPattern(Pattern.compile("<span class=\"artist\">(.*?)</span></span><span><span class=\"title\">(.*?)</span>"));
	}
	
	@Test
	public void drShouldReadAPage()
	{
		DayCrawler myCrawler = new DayCrawler(testWebsiteInformation);
		LocalDate dayTotRead = new LocalDate(2012, 6, 15);
		int readMusicnumbersCount = myCrawler.crawlDay(dayTotRead).size();
		assertTrue(0 < readMusicnumbersCount);
	}

}
