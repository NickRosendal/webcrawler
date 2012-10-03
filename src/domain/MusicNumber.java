package domain;

import java.io.Serializable;

public class MusicNumber implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String artist;
	private String title;

	public MusicNumber(String inArtist, String inTitle)
	{
		artist = inArtist;
		title = inTitle;
	}

	public String getArtist()
	{
		return artist;
	}

	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	@Override
	public String toString()
	{
		return artist + " - " + title;
	}

	@Override
	public int hashCode()
	{
		int hashcode = 0;
		for (int i = 0; i < artist.length(); i++)
		{
			hashcode = 31 * hashcode + artist.charAt(i);
		}
		for (int i = 0; i < title.length(); i++)
		{
			hashcode = 31 * hashcode + title.charAt(i);
		}
		// System.out.println("in hash code");
		// System.out.println(" value is " + hashcode);
		return hashcode;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;
		MusicNumber rhs = (MusicNumber) obj;
		if (rhs.getArtist().equals(artist) && rhs.getTitle().equals(title))
		{

			return true;
		}

		return false;

	}

}
