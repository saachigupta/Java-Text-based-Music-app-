//Saachi Gupta
// 501217408

import java.util.ArrayList;

public class Podcast extends AudioContent
{
    public static final String TYPENAME =	"PODCAST";

    private String host;
    private ArrayList<Season> seasons; //season is a list of objects of the class Season (class defined in separate file)
    private int currentSeason = 0;
    private int currentEpisode = 0;


    public Podcast(String title, int year, String id, String type, String audioFile, int length, String hostName, ArrayList<Season> seasons)
    {
        super(title, year, id, type, audioFile, length);
        this.host = hostName;
        this.seasons = seasons;
    }

    public void printInfo()
    {
        super.printInfo();
        System.out.println("Host: " + host);
        System.out.println("Seasons: " + seasons.size());
    }

    public ArrayList<Season> getSeasons() // Accessor method for seasons arraylist
    {
        return seasons; 
    }

    public void play() // sets audiofile to the current eposide title and the content  
    {
       setAudioFile(seasons.get(currentSeason).episodeTitles.get(currentEpisode) +". " + "\n" + seasons.get(currentSeason).episodeFiles.get(currentEpisode));
       super.play(); // calls super from parent class AudioContent
    }

    public String getType() 
    {
        return TYPENAME; //returns type PODCAST for podcasts
    }

    //Prints table of contents of each season
    public void printTOC()
    {
        ArrayList<String> titles = this.seasons.get(currentSeason).episodeTitles; //gets the arraylist of titles of the current season
        for (int  i = 0; i<titles.size() ; i++)
        {
            System.out.println("Episode " + (i+1) + ". " + titles.get(i));
            System.out.println();
        }
  
    }
    public int getNumberofSeasons()
    {
        return seasons.size(); 
    }

    public int getNumberofEpisodes()
    {
        return seasons.get(currentSeason).episodeTitles.size();
    }

    public void selectSeason(int season) //updates the current season
    {
        if (season >= 1 && season <= seasons.size())
        {
            currentSeason = season - 1;
        }

    }

    public void selectEpisode(int episode) //updates to current episode
    {
        if (episode >= 1 && episode <= seasons.get(currentSeason).episodeTitles.size())
        {
            currentEpisode = episode - 1;
        }
    }

    //Two Podcasts are equal if their AudioContent information is equal and the hosts are equal
    public boolean equals(Object other)
    {
		if (other instanceof Podcast){ // checks if "other" is an instance of the Podcast class
		Podcast otherP = (Podcast) other; // casts other to a Podcast
		return (super.equals(otherP) && host.equals(otherP.host) );
        }
        return false;
    }

}


