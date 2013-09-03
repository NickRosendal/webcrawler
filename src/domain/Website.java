package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.awt.RenderingHints.Key;
import java.io.Serializable;

import observer.interfaces.Observer;
import observer.interfaces.Subject;

import org.joda.time.LocalDate;

import serialization.DBConnector;
import serialization.Serilizar;

import datamining.DayCrawler;

public class Website implements Subject
{
	private String websiteTitle;
	private WebsiteInformation myInformation;
	private ArrayList<MusicNumber> masterMap = new ArrayList<MusicNumber>();
	private TreeMap<LocalDate, ArrayList<MusicNumber>> dayArtistNumberMap = new TreeMap<LocalDate, ArrayList<MusicNumber>>();

	public Website(String inWebsiteTitle)
	{
		websiteTitle = inWebsiteTitle;
	}

	public void addDay(LocalDate inDate, ArrayList<MusicNumber> inMusicNumber)
	{
		inMusicNumber.removeAll(masterMap);

		masterMap.addAll(inMusicNumber);
		dayArtistNumberMap.put(inDate, inMusicNumber);

	}

	public TreeMap<LocalDate, ArrayList<MusicNumber>> getDays()
	{
		return dayArtistNumberMap;
	}

	public ArrayList getSongDay(LocalDate day)
	{
		System.out.println(dayArtistNumberMap.get(day).size());

		return null;

	}

	public ArrayList<MusicNumber> getSongsOfDay(LocalDate dateToPull)
	{
		ArrayList returnHas = new ArrayList();
		returnHas = (ArrayList) dayArtistNumberMap.get(dateToPull);
		return returnHas;

	}

	@Override
	public String toString()
	{
		Set<Entry<LocalDate, ArrayList<MusicNumber>>> dayArtistNumberMapSet = dayArtistNumberMap.entrySet();
		Iterator i = dayArtistNumberMapSet.iterator();
		String returnString = new String();
		while (i.hasNext())
		{

			Map.Entry dayArtistNumberMapEntry = (Map.Entry) i.next();
			LocalDate dayArtistNumberMapDate = ((LocalDate) dayArtistNumberMapEntry.getKey());
			returnString += dayArtistNumberMapDate.year().get() + " / " + dayArtistNumberMapDate.monthOfYear().get() + " / "
					+ dayArtistNumberMapDate.dayOfMonth().get() + " \n";
			Set<MusicNumber> artistNumberMap = (Set<MusicNumber>) dayArtistNumberMapEntry.getValue();
			Set artistNumberMapSet = artistNumberMap;
			// Iterator j = artistNumberMapSet.iterator();
			//
			// while (j.hasNext()) {
			// Map.Entry artistNumberMapSetEntry = (Map.Entry) j.next();
			// returnString += artistNumberMapSetEntry.getKey() + " : " +
			// artistNumberMapSetEntry.getValue();
			// }

			for (MusicNumber key : artistNumberMap)
			{
				returnString += key.toString() + " ";

			}

			returnString += "\n";
		}
		return returnString;
	}

	public TreeMap<LocalDate, ArrayList<MusicNumber>> getDayArtistNumberMap()
	{
		return dayArtistNumberMap;
	}

	public String getWebsiteTitle()
	{
		return websiteTitle;
	}

	public WebsiteInformation getMyInformation()
	{
		return myInformation;
	}

	public void setMyInformation(WebsiteInformation myInformation)
	{
		this.myInformation = myInformation;
	}

	public int size()
	{
		return 1;
	}

	public void save()
	{
		Serilizar projectSerilizar = new Serilizar();
		try
		{
			DBConnector myDBConnector = new DBConnector();
		//	projectSerilizar.storeWebsite(this);
			myDBConnector.storeWebsite(this);
			//

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update()
	{
		Thread thread = new Thread(new Runnable()
		{
			public void run()
			{

				System.out.println(websiteTitle + " should now be updating in a new thread!");
				LocalDate lastDayInMap = dayArtistNumberMap.lastKey();
				// LocalDate dayToStartRead = lastDayInMap.plusDays(1);
				LocalDate dayToStartRead = lastDayInMap;
				LocalDate dayToReadTo = LocalDate.now().plusDays(1);
				// LocalDate dayToReadTo = dayToStartRead.plusDays(1); //TODO
				// KIM

				DayCrawler crawler = new DayCrawler(myInformation, websiteTitle);
				while (dayToStartRead.isBefore(dayToReadTo))
				{
					notifyObservers("We are now reading " + dayToStartRead);
					addDay(dayToStartRead, crawler.crawlDay(dayToStartRead));
					notifyObservers("Finished reading " + dayToStartRead);
					notifyObservers(websiteTitle);
					
					dayToStartRead = dayToStartRead.plusDays(1);

				}

				save();
				

			}
		});
		thread.start();

	}

	private ArrayList<Object> registredObservers = new ArrayList();

	@Override
	public void registerObserver(Observer o)
	{
		registredObservers.add(o);

	}

	@Override
	public void removeObserver(Observer o)
	{
		registredObservers.remove(o);

	}

	@Override
	public void notifyObservers(String event)
	{
		for (int i = 0; i < registredObservers.size(); i++)
		{
			((observer.interfaces.Observer) registredObservers.get(i)).update(event);
		}

	}

}
