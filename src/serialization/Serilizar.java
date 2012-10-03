package serialization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.TreeMap;

import javax.print.DocFlavor.URL;

import org.joda.time.LocalDate;
import domain.MusicNumber;
import domain.Website;
import domain.WebsiteInformation;

public class Serilizar {
	public ArrayList<Website> loadAllWebsites() {

		File folder = new File("Websites/");
		ArrayList<Website> tempArrayList = new ArrayList<Website>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				WebsiteInformation tempWebsiteInformation = new WebsiteInformation();
				tempWebsiteInformation = loadWebsiteInformation(fileEntry.getName());
				Website tempWebsite = new Website(fileEntry.getName());
				tempWebsite.setMyInformation(tempWebsiteInformation);
				tempArrayList.add(tempWebsite);
			}

		}
		for (Website myWebsite : tempArrayList) {
			System.out.println(myWebsite.getWebsiteTitle());
		//	System.out.println(myWebsite.getMyInformation().getIconPath());
		}
		return tempArrayList;
	}

	private WebsiteInformation loadWebsiteInformation(String _websiteToLoad) {
		WebsiteInformation tempWebsiteInformation = new WebsiteInformation();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		tempWebsiteInformation.setIconPath(classLoader.getResource("").toString().substring(6) + "Websites/" + _websiteToLoad + "/000setup/"
				+ _websiteToLoad + ".gif");
		//System.out.println(tempWebsiteInformation.getIconPath());
		try {
			BufferedReader in = new BufferedReader(new FileReader("Websites/" + _websiteToLoad
					+ "/000setup/WebsiteInformation.txt"));
			String str;
			int currentLine = 1;

			while ((str = in.readLine()) != null) {
				if (currentLine == 1) {
					tempWebsiteInformation.setMainUrl(str);
				} else if (currentLine == 2) {
					tempWebsiteInformation.setDay(str);
				} else if (currentLine == 3) {
					tempWebsiteInformation.setMonth(str);
				} else if (currentLine == 4) {
					tempWebsiteInformation.setYear(str);
				} else if (currentLine == 5) {
					tempWebsiteInformation.setUnderPageURL(str);
				} else if (currentLine == 6) {
					tempWebsiteInformation.setUnderPageURLType(str);
				} else if (currentLine == 7) {
					tempWebsiteInformation.setRegExMusicNumberPattern(Pattern.compile(str));
				}
				currentLine++;
			}

			in.close();
		} catch (IOException e) {
			tempWebsiteInformation = null;
			e.printStackTrace();
		}
		return tempWebsiteInformation;
	}

	public void loadWebsiteSongs(Website inWebsite) throws Exception {

		if (inWebsite.getWebsiteTitle() == null || inWebsite.getWebsiteTitle() == "")
			throw new Exception("your website title is fucked");

		File folder = new File("Websites/" + inWebsite.getWebsiteTitle());
		for (File fileEntry : folder.listFiles()) {
			/*
			 * browses website folder for stuff, if condition checks that we
			 * only get folders
			 */
			if (fileEntry.isDirectory() && !(fileEntry.getName().equals("000setup"))) {
				String folderDateString = fileEntry.getName();

				File dateFolder = new File("Websites/" + inWebsite.getWebsiteTitle() + "/" + folderDateString);

				Set<MusicNumber> artistNumberMap = new HashSet<MusicNumber>();

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				char delimiter = '-';
				String year = folderDateString.substring(0, folderDateString.indexOf(delimiter));
				// cuts off the start of folderDateString xxxx-xx-xx becomes
				// xx-xx
				folderDateString = folderDateString.substring(folderDateString.indexOf(delimiter) + 1,
						folderDateString.length());

				// gets from index 0 to delimiter ï¿½-ï¿½
				String month = folderDateString.substring(0, folderDateString.indexOf(delimiter));

				// cuts off the start of folderDateString xx-xx becomes xx
				folderDateString = folderDateString.substring(folderDateString.indexOf(delimiter) + 1,
						folderDateString.length());
				String date = folderDateString.substring(0, folderDateString.length());

				LocalDate thisDate = new LocalDate(Integer.parseInt(year), Integer.parseInt(month),
						Integer.parseInt(date));
				// System.out.println(year+"-"+month+"-"+date);

				for (final File fileEntey1 : dateFolder.listFiles()) {

					// cast fileEntery1 to musicNumber
					// CHRIS BROWN_BEAUTIFUL PEOPLE feat. BENNY BENASSI .dat
					// and add the music number to artistmap

					MusicNumber myMusicNumber = loadMusicNumber(fileEntey1.getAbsolutePath());
					artistNumberMap.add(myMusicNumber);

				}
				inWebsite.addDay(thisDate, artistNumberMap);
			}
			if (fileEntry.getName().equals("negativeList.txt")) {
				// TODO kim add a list from text file with music names we dont
				// want, test me --- will getMyInformation work?

				WebsiteInformation myWebsiteInformation = inWebsite.getMyInformation();
				myWebsiteInformation.setNegativeListStringArray(loadNegativeList(fileEntry));
				inWebsite.setMyInformation(myWebsiteInformation);
			}

		}

	}

	private ArrayList<String> loadNegativeList(File _file) {
		FileInputStream myFileInputStream = null;
		ArrayList<String> returnStringArray = new ArrayList<String>();

		try {
			myFileInputStream = new FileInputStream(_file);
			BufferedReader myBufferedReader = new BufferedReader(new InputStreamReader(myFileInputStream));
			String line;

			while ((line = myBufferedReader.readLine()) != null) {
				System.out.println(line);
				returnStringArray.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStringArray;
	}

	private MusicNumber loadMusicNumber(String inReadPath) {

		FileInputStream fis = null;
		ObjectInputStream ois = null;

		MusicNumber tempMusicNumber = null;
		try {
			fis = new FileInputStream(inReadPath);

			ois = new ObjectInputStream(fis);

			tempMusicNumber = (MusicNumber) ois.readObject();

			ois.close();
		} catch (java.io.EOFException e) {
			// Hey if there is a error in reading the file lets just get rid of
			// it
			File fileToDelete = new File(inReadPath);
			fileToDelete.delete();
		} catch (Exception e) {
			System.out.println(inReadPath + " Error: " + e);
			e.printStackTrace();
		}
		return tempMusicNumber;

	}

	public void storeWebsite(Website inWebsite) throws Exception {

		File myFile_ = new File("Websites/" + inWebsite.getWebsiteTitle());

		if (!myFile_.exists()) { // if the libery is not there
			if (!myFile_.mkdirs()) { // if we cant create the libery
				throw new Exception("cannot create libary: " + myFile_.getName());
			}
		}
		for (Map.Entry<LocalDate, Set<MusicNumber>> entry : inWebsite.getDayArtistNumberMap().entrySet()) {
			String filePathString = "Websites/" + inWebsite.getWebsiteTitle() + "/"
					+ ((LocalDate) entry.getKey()).year().get() + "-"
					+ ((LocalDate) entry.getKey()).monthOfYear().get() + "-"
					+ ((LocalDate) entry.getKey()).dayOfMonth().get();
			myFile_ = new File(filePathString);
			if (myFile_.exists() || myFile_.mkdirs()) // if the libary exists or
														// we can create it
			{
				// System.out.println("Libery " + myFile_.toString());

				for (MusicNumber key : (Set<MusicNumber>) entry.getValue()) {
					// System.out.println(key.toString() + " ");
					storeObject(filePathString, key);

				}
				// System.out.println("--- that was the set ----");
			}

		}

	}

	public void storeObject(String inWritepath, MusicNumber inMusicNumber) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		try {
			// System.out.println(inWritepath + "/" + inMusicNumber.getArtist()
			// + "_" + inMusicNumber.getTitle() + ".dat");
	//		System.out.println("inWritepath: "+ inWritepath);
	//		System.out.println("inMusicNumber.getArtist(): "+ inMusicNumber.getArtist());
			fos = new FileOutputStream(inWritepath + "/" + inMusicNumber.getArtist() + "_" + inMusicNumber.getTitle()
					+ ".dat");
			out = new ObjectOutputStream(fos);
			out.writeObject(inMusicNumber);
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
