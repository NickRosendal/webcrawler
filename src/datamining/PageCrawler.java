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

import domain.MusicNumber;
import domain.WebsiteInformation;

public class PageCrawler {

	// private String lastCrawledPageString;
	public Set<MusicNumber> crawler(String inTarget, WebsiteInformation _websiteInformation, boolean artistNameFirst) {
		Set<MusicNumber> artistNumberMap = new HashSet<MusicNumber>();
		String pageString = readPage(inTarget);
		Matcher matcher = _websiteInformation.getRegExMusicNumberPattern().matcher(pageString);
		while (matcher.find()) {
			MusicNumber tempMusicNumber;
			if (artistNameFirst) {
				tempMusicNumber = new MusicNumber(matcher.group(1), matcher.group(2));
			} else {
				tempMusicNumber = new MusicNumber(matcher.group(2), matcher.group(1));
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
