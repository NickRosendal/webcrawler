package datamining;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.MusicNumber;

public class MusicNumberFormatter
{

	public MusicNumber checkAndCorrect(MusicNumber _targetNumber)
	{
		// System.out.print(_targetNumber.getArtist() + " : " +
		// _targetNumber.getTitle());
		_targetNumber.setArtist(removeUnicodeEscapes(_targetNumber.getArtist()));
		_targetNumber.setTitle(removeUnicodeEscapes(_targetNumber.getTitle()));

		_targetNumber.setArtist(replaceString(_targetNumber.getArtist(), "-", " "));

		checkAndCorrectArtistAndTitle(_targetNumber, "/", "");
		checkAndCorrectArtistAndTitle(_targetNumber, "\\*", "_");
		checkAndCorrectArtistAndTitle(_targetNumber, "\\\\", "&");
		checkAndCorrectArtistAndTitle(_targetNumber, "&amp;", "");
		checkAndCorrectArtistAndTitle(_targetNumber, "\\?", "");
		checkAndCorrectArtistAndTitle(_targetNumber, "\\t", "");
		
		_targetNumber.setArtist(capitalizeString(_targetNumber.getArtist()));
		_targetNumber.setTitle(capitalizeString(_targetNumber.getTitle()));
		
		Pattern regex = Pattern.compile("\\((.*?)\\)");
		Matcher regexMatcher = regex.matcher(_targetNumber.getTitle());
		String remixString;
		while (regexMatcher.find()) {
			remixString = regexMatcher.group(1);
			System.out.println("remixString: " + remixString);
			
			Pattern regex2 = Pattern.compile("(?i)mix");
			Matcher regexMatcher2 = regex2.matcher(remixString);
			if (regexMatcher2.find()) {
				System.out.println("it is a remix");
				_targetNumber.setRemix(regexMatcher.group(1));
				_targetNumber.setTitle(_targetNumber.getTitle().replace("(" + remixString + ")", ""));
			}
			
			Pattern regex3 = Pattern.compile("(?i)Radio Edit|Original|Album Version");
			Matcher regexMatcher3 = regex3.matcher(remixString);
			if (regexMatcher3.find()) {
				System.out.println("it is a Radio Edit|Original|Album Version");
				_targetNumber.setTitle(_targetNumber.getTitle().replace("(" + remixString + ")", ""));
			}
		} 
		

		_targetNumber = featMover(_targetNumber);

		// System.out.println(" == " + _targetNumber.getArtist() + " : "+
		// _targetNumber.getTitle());
		if (_targetNumber.getArtist() == null)
		{
			_targetNumber.setArtist("");
		}
		if (_targetNumber.getTitle() == null)
		{
			_targetNumber.setTitle("");
		}
		return _targetNumber;
	}

	private String removeUnicodeEscapes(String inString)
	{
		Pattern r = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
		Matcher m = r.matcher(inString);
		String returnString = inString;
		while (m.find())
		{
			try
			{

				String s = m.group(1);
				char c = (char) Integer.parseInt(s, 16);
				returnString = m.replaceAll(c + "");
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		return returnString;
	}

	public String capitalizeString(String string)
	{
		char[] chars = string.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; i++)
		{
			if (!found && Character.isLetter(chars[i]))
			{
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'')
			{ // You can add other chars here
				found = false;
			}
		}
		return String.valueOf(chars);
	}

	private MusicNumber checkAndCorrectArtistAndTitle(MusicNumber inMusicNumber, String lookFor, String replaceWith)
	{
		inMusicNumber.setArtist(replaceString(inMusicNumber.getArtist(), lookFor, replaceWith));
		inMusicNumber.setTitle(replaceString(inMusicNumber.getTitle(), lookFor, replaceWith));
		return inMusicNumber;

	}

	private String replaceString(String targetString, String lookFor, String replaceWith)
	{
		String returnString;
		Pattern r = Pattern.compile(lookFor);
		Matcher m = r.matcher(targetString);
		returnString = m.replaceAll(replaceWith);
		return returnString;
	}

	// virker den her ikke? for sangende har feat. til sidst og ikk f.
	private MusicNumber featMover(MusicNumber testNumber)
	{

		String artist = testNumber.getArtist();
		String title = testNumber.getTitle();
		String regex = " Feat\\.(.*)";
		String featuredArtists = "";
		Pattern r = Pattern.compile(regex);

		Matcher m = r.matcher(title);
		while (m.find())
		{
			featuredArtists += m.group(1);
			title = m.replaceAll("");
		}
		m = r.matcher(artist);
		while (m.find())
		{
			featuredArtists += m.group(1);
			artist = m.replaceAll("");
		}
		testNumber.setArtist(artist);
		testNumber.setTitle(title);

		regex = "& (.*)";
		r = Pattern.compile(regex);
		m = r.matcher(featuredArtists);
		while (m.find())
		{
			 testNumber.addFeaturedArtists(m.group(1).trim());

		}
		featuredArtists = m.replaceAll("");
		if (featuredArtists.length() > 0)
		{
			testNumber.addFeaturedArtists(featuredArtists.trim());
		}
		return testNumber;
	}
}