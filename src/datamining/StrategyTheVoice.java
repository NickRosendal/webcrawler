package datamining;

import java.util.HashSet;
import java.util.Set;
import org.joda.time.LocalDate;

import domain.MusicNumber;
import domain.WebsiteInformation;

public class StrategyTheVoice {
	Set<MusicNumber> artistNumberMap = new HashSet<MusicNumber>();

	public StrategyTheVoice(WebsiteInformation _websiteInformation, LocalDate date_) {
//		System.out.println("### What the voice Crawler knows ###");
//		System.out.println(_websiteInformation.getMainUrl());
//		System.out.println(_websiteInformation.getDay());
//		System.out.println(_websiteInformation.getMonth());
//		System.out.println(_websiteInformation.getYear());
//		System.out.println(_websiteInformation.getUnderPageURL());
//		System.out.println(_websiteInformation.getUnderPageURLType());
//		System.out.println(_websiteInformation.getRegExMusicNumberPattern());
//		System.out.println("### What the voice Crawler knows  done ###");
		// Set<MusicNumber> artistNumberMap = new HashSet<MusicNumber>();
		PageCrawler myCrawler = new PageCrawler();

		int UnderPageURLValue = 0;
		Set<MusicNumber> LastArtistNumberMap = new HashSet<MusicNumber>();

		boolean canIgo = true;
		while (canIgo) {

			String urlString = _websiteInformation.getMainUrl();

			// write the date into the URL
			System.out.println(date_.year().get() + " " + date_.monthOfYear().get() + " " + date_.dayOfMonth().get());
			urlString = urlString.replace(_websiteInformation.getYear(), date_.year().get() + "");
			urlString = urlString.replace(_websiteInformation.getMonth(), date_.monthOfYear().get() + "");
			urlString = urlString.replace(_websiteInformation.getDay(), date_.dayOfMonth().get() + "");

			// write the UnderPageURL into the URL
			urlString = urlString.replaceFirst(_websiteInformation.getUnderPageURL(), UnderPageURLValue + "");
			UnderPageURLValue++;

			Set<MusicNumber> tempArtistNumberMap = myCrawler.crawler(urlString, _websiteInformation, true);
			artistNumberMap.addAll(tempArtistNumberMap);

			// Loop exit check

			if (LastArtistNumberMap.equals(tempArtistNumberMap)) {
				canIgo = false;
			}
			System.out.println("Total found: " + artistNumberMap.size());
			LastArtistNumberMap = tempArtistNumberMap;
		}
		// System.out.println(artistNumberMap.toString());

	}

	public Set<MusicNumber> getArtistNumberMap() {
		return artistNumberMap;
	}
}
