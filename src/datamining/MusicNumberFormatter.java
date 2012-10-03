package datamining;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.MusicNumber;

public class MusicNumberFormatter
{

	public MusicNumber checkAndCorrect(MusicNumber _targetNumber)
	{
	//	System.out.print(_targetNumber.getArtist() + " : " + _targetNumber.getTitle());
		_targetNumber.setArtist(removeUnicodeEscapes(_targetNumber.getArtist()));
		_targetNumber.setTitle(removeUnicodeEscapes(_targetNumber.getTitle()));

		_targetNumber.setArtist(replaceString(_targetNumber.getArtist(), "-", " "));

		checkAndCorrectArtistAndTitle(_targetNumber, "/", "");
		checkAndCorrectArtistAndTitle(_targetNumber, "\\*", "_");
		checkAndCorrectArtistAndTitle(_targetNumber, "\\\\", "&");
		checkAndCorrectArtistAndTitle(_targetNumber, "&amp;", "");
		checkAndCorrectArtistAndTitle(_targetNumber, "\\?", "");
		checkAndCorrectArtistAndTitle(_targetNumber, "\\t", "");
		_targetNumber = featMover(_targetNumber);
		_targetNumber.setArtist(replaceString(_targetNumber.getArtist(), "(?i)feat\\.", "F."));
		_targetNumber.setArtist(capitalizeString(_targetNumber.getArtist()));
		_targetNumber.setTitle(capitalizeString(_targetNumber.getTitle()));
//		System.out.println(" == " + _targetNumber.getArtist() + " : "+ _targetNumber.getTitle());
		if (_targetNumber.getArtist() == null) {
			_targetNumber.setArtist("");
		}
		if (_targetNumber.getTitle() == null) {
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
		String regex = " feat\\.(.*)";

		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(title);
		while (m.find())
		{
			artist += " f." + m.group(1);
			title = m.replaceAll("");
		}
		testNumber.setArtist(artist);
		testNumber.setTitle(title);
		return testNumber;
	}
}