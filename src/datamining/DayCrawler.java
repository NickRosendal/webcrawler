package datamining;

import java.util.Set;
import org.joda.time.LocalDate;

import domain.MusicNumber;
import domain.WebsiteInformation;

public class DayCrawler
{

	private WebsiteInformation myWebsiteInformation;

	public DayCrawler(WebsiteInformation inWebsiteInformation)
	{
		myWebsiteInformation = inWebsiteInformation;
	}

	public Set<MusicNumber> crawlDay(LocalDate inDate)
	{
		if (myWebsiteInformation.getUnderPageURLType().equals("int"))
		{
			StrategyTheVoice myStrategyTheVoice = new StrategyTheVoice(myWebsiteInformation, inDate);
			return myStrategyTheVoice.getArtistNumberMap();
		} else if (myWebsiteInformation.getUnderPageURLType().equals("NRJ"))
		{
			StrategyNRJGermany myStrategyNRJGermany = new StrategyNRJGermany(myWebsiteInformation, inDate);
			return myStrategyNRJGermany.getArtistNumberMap();
		} else if (myWebsiteInformation.getUnderPageURLType().equals("DR")){
			StrategyDR myStrategyDR = new StrategyDR(myWebsiteInformation, inDate);
			return myStrategyDR.getArtistNumberMap();
		}
		return null;
	}

}
