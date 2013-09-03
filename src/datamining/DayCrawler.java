package datamining;

import java.util.ArrayList;
import java.util.Set;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.joda.time.LocalDate;

import domain.MusicNumber;
import domain.WebsiteInformation;

public class DayCrawler
{

	private WebsiteInformation myWebsiteInformation;
private String websiteName;
	public DayCrawler(WebsiteInformation inWebsiteInformation, String inWebsiteName)
	{
		myWebsiteInformation = inWebsiteInformation;
		websiteName = inWebsiteName;
	}

	public ArrayList<MusicNumber> crawlDay(LocalDate inDate)
	{
		ArrayList<MusicNumber> returnList = new ArrayList<MusicNumber>();
		if (myWebsiteInformation.getUnderPageURLType().equals("int"))
		{
			StrategyTheVoice myStrategyTheVoice = new StrategyTheVoice(myWebsiteInformation, inDate);
			returnList = myStrategyTheVoice.getArtistNumberMap();
		} else if (myWebsiteInformation.getUnderPageURLType().equals("NRJ"))
		{
			StrategyNRJGermany myStrategyNRJGermany = new StrategyNRJGermany(myWebsiteInformation, inDate);
			returnList = myStrategyNRJGermany.getArtistNumberMap();
		} else if (myWebsiteInformation.getUnderPageURLType().equals("DR")){
			StrategyDR myStrategyDR = new StrategyDR(myWebsiteInformation, inDate);
			returnList = myStrategyDR.getArtistNumberMap();
		}
		for(MusicNumber i: returnList){
			i.setStation(websiteName);
		}
		return returnList;
	}

}
