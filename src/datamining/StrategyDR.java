package datamining;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;

import domain.MusicNumber;
import domain.WebsiteInformation;

public class StrategyDR
{
	ArrayList<MusicNumber> artistNumberMap = new ArrayList<MusicNumber>();

	public StrategyDR(WebsiteInformation _websiteInformation, LocalDate _date)
	{
				
		String urlString = _websiteInformation.getMainUrl();
		//test if the musicnumber is on the negative list
	
		// write the date into the URL
		urlString = urlString.replace(_websiteInformation.getYear(), _date.year().get() + "");
		urlString = urlString.replace(_websiteInformation.getMonth(), _date.monthOfYear().get() + "");
		urlString = urlString.replace(_websiteInformation.getDay(), _date.dayOfMonth().get() + "");
		
		PageCrawler myCrawler = new PageCrawler();
		String linkPageString = myCrawler.readPage(urlString);
		
		
		Pattern regex = Pattern.compile("dr_data=\"(.*?)\"");
		Matcher matcher = regex.matcher(linkPageString);
		while (matcher.find())
		{
			
			String underPageUrl = _websiteInformation.getUnderPageURL();
			underPageUrl = underPageUrl.replace(_websiteInformation.getYear(), _date.year().get() + "");
			underPageUrl = underPageUrl.replace(_websiteInformation.getMonth(), _date.monthOfYear().get() + "");
			underPageUrl = underPageUrl.replace(_websiteInformation.getDay(), _date.dayOfMonth().get() + "");
			underPageUrl = underPageUrl.replace("#pid#", matcher.group(1));
			
			ArrayList<MusicNumber> tempArtistNumberMap = myCrawler.crawler(_date, underPageUrl,_websiteInformation,true);
			artistNumberMap.addAll(tempArtistNumberMap);
			
			//System.out.println(underPageString);
			System.out.println(tempArtistNumberMap.size());
			
		}
	}

	public ArrayList<MusicNumber> getArtistNumberMap()
	{
		return artistNumberMap;
	}
}
