package serialization;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import org.joda.time.LocalDate;

import domain.MusicNumber;
import domain.Website;

public class DBConnector
{
	private String dbUrl = "jdbc:mysql://localhost:3306/webcrawler";
	private String dbClass = "com.mysql.jdbc.Driver";
	private String dbuser = "root";
	private String dbpassword = "admin";
	Connection con;

	public DBConnector()
	{

	}

	public void storeWebsite(Website inWebsite)
	{
		for (Map.Entry<LocalDate, ArrayList<MusicNumber>> entry : inWebsite.getDayArtistNumberMap().entrySet())
		{
		

			for (MusicNumber key : (ArrayList<MusicNumber>) entry.getValue())
			{
			
				addMusicNumberToDB(key);

			}
			

		}
	}

	public void addMusicNumberToDB(MusicNumber inMusicNumber)
	{
		if(inMusicNumber.getDateAndTime() == null ) return;
		
		try
		{
			inMusicNumber.setTitle(inMusicNumber.getTitle().replace("'", ""));
			con = DriverManager.getConnection(dbUrl, dbuser, dbpassword);

			ArrayList<String> queryList = new ArrayList<String>();
			queryList.add("INSERT IGNORE INTO `webcrawler`.`artist` (`Name`) VALUES ('" + inMusicNumber.getArtist() + "');");
			queryList.add("INSERT IGNORE INTO `webcrawler`.`number` (`artist`, `name`) VALUES ('" + inMusicNumber.getArtist() + "', '"
					+ inMusicNumber.getTitle() + "');");
			if (inMusicNumber.getFeaturedArtists() != null)
			{
				for (String featuredArtist : inMusicNumber.getFeaturedArtists())
				{
					queryList.add("INSERT IGNORE INTO `webcrawler`.`artist` (`Name`) VALUES ('" + featuredArtist + "');");
					queryList.add("INSERT IGNORE INTO `webcrawler`.`artist_featured_on_number` (`artist_Name`, `number_artist`, `number_name`) VALUES ('"
							+ featuredArtist + "', '" + inMusicNumber.getArtist() + "', '" + inMusicNumber.getTitle() + "');");
				}
			}

			String dateTimeStamp = inMusicNumber.getDateAndTime().year().get() + "-" + inMusicNumber.getDateAndTime().monthOfYear().get() + "-"
					+ inMusicNumber.getDateAndTime().dayOfMonth().get() + " " + inMusicNumber.getDateAndTime().hourOfDay().get() + ":" + inMusicNumber.getDateAndTime().minuteOfHour().get() + ":" + inMusicNumber.getDateAndTime().secondOfMinute().get();
		
			queryList.add("INSERT IGNORE INTO `webcrawler`.`station` (`name`) VALUES ('" + inMusicNumber.getStation() + "');");
			if (inMusicNumber.getRemix() != null)
			{
				queryList.add("INSERT IGNORE INTO `webcrawler`.`remix` (`name`, `number_artist`, `number_name`) VALUES ('" + inMusicNumber.getRemix() + "', '"
						+ inMusicNumber.getArtist() + "', '" + inMusicNumber.getTitle() + "');");

				queryList
						.add("INSERT IGNORE INTO `webcrawler`.`collected_music_numbers` (`station`, `date_and_time`, `number_artist`, `number_name`, `remix_name`) "
								+ "VALUES ('"
								+ inMusicNumber.getStation()
								+ "', '"
								+ dateTimeStamp
								+ "', '"
								+ inMusicNumber.getArtist()
								+ "', '" + inMusicNumber.getTitle() + "', '" + inMusicNumber.getRemix() + "');");
			} else
				queryList.add("INSERT IGNORE INTO `webcrawler`.`collected_music_numbers` (`station`, `date_and_time`, `number_artist`, `number_name`) "
						+ "VALUES ('" + inMusicNumber.getStation() + "', '" + dateTimeStamp + "', '" + inMusicNumber.getArtist() + "', '"
						+ inMusicNumber.getTitle() + "');");
			{
			}

			Class.forName(dbClass);
			Statement stmt = con.createStatement();
			for (int i = 0; i < queryList.size(); i++)
			{
				stmt.execute(queryList.get(i));
			}

			con.close();

		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
