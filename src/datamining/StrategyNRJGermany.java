package datamining;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import domain.MusicNumber;
import domain.WebsiteInformation;

public class StrategyNRJGermany
{
	Set<MusicNumber> artistNumberMap = new HashSet<MusicNumber>();
	PageCrawler myCrawler = new PageCrawler();

	public StrategyNRJGermany(WebsiteInformation website_, LocalDate date_)
	{
		for (int i = 0; i <= 23; i++)
		{ // website have a page for each hour of the day starting at 0
			String urlString = website_.getMainUrl();

			// write the date and hour into the URL
			urlString = urlString.replace(website_.getYear(), date_.year().get() + "");
			urlString = urlString.replace(website_.getMonth(), date_.monthOfYear().get() + "");
			urlString = urlString.replace(website_.getDay(), date_.dayOfMonth().get() + "");
			urlString = urlString.replace(website_.getUnderPageURL(), i + "");

			// ScreenScrape the website with the url for each underpage
			Set<MusicNumber> tempArtistNumberMap = myCrawler.crawler(urlString, website_, false);
			artistNumberMap.addAll(tempArtistNumberMap);
		}
	}

	public Set<MusicNumber> getArtistNumberMap()
	{
		return artistNumberMap;
	}
}
