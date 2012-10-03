package domain;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class WebsiteInformation {
private String mainUrl;
private String year;
private String month;
private String day;
private String musicNumberPattern; //not in constructor
private String underPageURL;
private String underPageURLType;
private String underPageEnd;  //not in constructor
private Pattern regExMusicNumberPattern;
private String iconPath; //not in constructor
ArrayList<String> negativeListStringArray = new ArrayList<String>();
public WebsiteInformation(String inMainUrl, String inDay, String inMonth, String inYear, String inUnderPageURL, String 	inUnderPageURLType,Pattern inRegExMusicNumberPattern) {
	year = inYear;
	month = inMonth;
	day = inDay;
	mainUrl = inMainUrl;
	underPageURL = inUnderPageURL;
	underPageURLType = inUnderPageURLType;
	regExMusicNumberPattern = inRegExMusicNumberPattern;
}
public WebsiteInformation() {
	
}
public String getMainUrl() {
	return mainUrl;
}
public void setMainUrl(String mainUrl) {
	this.mainUrl = mainUrl;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public String getMonth() {
	return month;
}
public void setMonth(String month) {
	this.month = month;
}
public String getDay() {
	return day;
}
public void setDay(String day) {
	this.day = day;
}
public String getMusicNumberPattern() {
	return musicNumberPattern;
}
public void setMusicNumberPattern(String musicNumberPattern) {
	this.musicNumberPattern = musicNumberPattern;
}
public String getUnderPageURL() {
	return underPageURL;
}
public void setUnderPageURL(String underPageURL) {
	this.underPageURL = underPageURL;
}
public String getUnderPageURLType() {
	return underPageURLType;
}
public void setUnderPageURLType(String underPageURLType) {
	this.underPageURLType = underPageURLType;
}
public String getUnderPageEnd() {
	return underPageEnd;
}
public void setUnderPageEnd(String underPageEnd) {
	this.underPageEnd = underPageEnd;
}
public Pattern getRegExMusicNumberPattern()
{
	return regExMusicNumberPattern;
}
public void setRegExMusicNumberPattern(Pattern regExPattern)
{
	this.regExMusicNumberPattern = regExPattern;
}
public String getIconPath()
{
	return iconPath;
}
public void setIconPath(String iconPath)
{
	this.iconPath = iconPath;
}
public ArrayList<String> getNegativeListStringArray()
{
	return negativeListStringArray;
}

public void setNegativeListStringArray(ArrayList<String> _negativeListStringArray)
{
	System.out.println("someone is trying to set negative list ´, with " + _negativeListStringArray.size() + " itmes");
	negativeListStringArray = _negativeListStringArray;
}
public void addToNegativeListStringArray(String _string) {
	negativeListStringArray.add(_string);
}


}
