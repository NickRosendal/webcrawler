package datamining;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import domain.MusicNumber;
import domain.WebsiteInformation;

public class PageCrawler {

	// private String lastCrawledPageString;
	public ArrayList<MusicNumber> crawler(LocalDate date, String inTarget, WebsiteInformation _websiteInformation, boolean artistNameFirst) {
		ArrayList<MusicNumber> artistNumberMap = new ArrayList<MusicNumber>();
		String pageString = readPage(inTarget);
		Matcher matcher = _websiteInformation.getRegExMusicNumberPattern().matcher(pageString);
		while (matcher.find()) {
			MusicNumber tempMusicNumber;
		
			LocalTime myTime = new LocalTime(matcher.group(1));
			DateTime myDateTime = new DateTime(date.toString() + "T" + myTime.toString());
	//	System.out.println(myDateTime.toString());
			
			if (artistNameFirst) {
				tempMusicNumber = new MusicNumber(myDateTime, matcher.group(2), matcher.group(3));
			} else {
				tempMusicNumber = new MusicNumber(myDateTime, matcher.group(3), matcher.group(2));
			}
			
			ArrayList<String> negativeList = _websiteInformation.getNegativeListStringArray();
			MusicNumberFormatter myMusicNumberFormatter = new MusicNumberFormatter();

			if (tempMusicNumber.getArtist().length() > 0 && tempMusicNumber.getTitle().length() > 0) {
				tempMusicNumber = myMusicNumberFormatter.checkAndCorrect(tempMusicNumber);
				boolean foundMatch = false;
				if (negativeList != null) {
					for (int i = 0; i < negativeList.size(); i++) {

						try {
							Pattern regex = Pattern.compile(negativeList.get(i));
							Matcher regexMatcher = regex.matcher(tempMusicNumber.toString());
							if (regexMatcher.find()) {
								foundMatch = true;
							}

						} catch (PatternSyntaxException ex) {
							ex.printStackTrace();
						}

					}
				}
				if (foundMatch == false) {
					System.out.println(tempMusicNumber.toString());
					artistNumberMap.add(tempMusicNumber);
				} else {
					System.out.println("number on negative list " + tempMusicNumber.toString());
				}
			}
		}
		System.out.println("Final : " + artistNumberMap.size());
		return artistNumberMap;
	}

	public String readPage(String inPage) {
		BufferedReader bufferedReader = null;
		StringBuilder sb = new StringBuilder();
		try {
			// bufferedReader = new BufferedReader(new InputStreamReader(new
			// URL(inPage).openConnection().getInputStream(), "UTF8"));
			bufferedReader = new BufferedReader(new InputStreamReader(
					new URL(inPage).openConnection().getInputStream(), "UTF-8"));
			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");

			}
			bufferedReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();

	}

}
