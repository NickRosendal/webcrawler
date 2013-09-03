package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

public class MusicNumber implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String station;
	private String artist;
	private String title;
	private String remix;
	private Set<String> featuredArtists = new HashSet<String>();
	private DateTime dateAndTime;

	public MusicNumber(String inArtist, String inTitle)
	{
		artist = inArtist;
		title = inTitle;
	}

	public MusicNumber(DateTime inDateAndTime, String inArtist, String inTitle)
	{
		dateAndTime = inDateAndTime;
		artist = inArtist;
		title = inTitle;
	}

	public DateTime getDateAndTime()
	{
		return dateAndTime;
	}

	public void setDateAndTime(DateTime dateAndTime)
	{
		this.dateAndTime = dateAndTime;
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

	public String getRemix()
	{
		return remix;
	}

	public void setRemix(String remix)
	{
		this.remix = remix;
	}

	public void addFeaturedArtists(String inArtist)
	{
		featuredArtists.add(inArtist);
	}

	public void removeFeaturedArtists(String artistToRemove)
	{
		featuredArtists.remove(artistToRemove);
	}

	public Set<String> getFeaturedArtists()
	{
		return featuredArtists;
	}

	public void setFeaturedArtists(Set<String> featuredArtists)
	{
		this.featuredArtists = featuredArtists;
	}

	public String getStation()
	{
		return station;
	}

	public void setStation(String station)
	{
		this.station = station;
	}

	@Override
	public String toString()
	{
		String returnString = "";
		if (dateAndTime != null)
		{
			returnString += dateAndTime.toString();
		}
		returnString += " " + artist + " ";
		if (featuredArtists != null)
			if (featuredArtists.size() > 0)
			{
				{
					returnString += "F. ";
					for (String i : featuredArtists)
					{
						returnString += i + " ";
					}
				}
			}
		returnString += "- " + title;
		return returnString;

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
		int j = 1;
		if (dateAndTime != null)
		{
			for (int i = 0; i < dateAndTime.toString().length(); ++i)
			{
				char c = dateAndTime.toString().charAt(i);
				j = +(int) c;

			}
		}
		hashcode = hashcode * j;
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
		if (obj.getClass() != getClass()) // if the object we compare to is not
											// the same type
			return false;
		MusicNumber rhs = (MusicNumber) obj;
		if (rhs.getDateAndTime() != null & rhs.getArtist().equals(artist) && rhs.getTitle().equals(title) && rhs.getDateAndTime().equals(dateAndTime))
		{
			return true;
		}

		return false;

	}

}
