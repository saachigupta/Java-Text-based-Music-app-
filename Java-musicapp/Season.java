// Saachi Gupta
// 501217408

import java.util.ArrayList;

//defining a class called Season that consists of three arraylists
public class Season
{
    public ArrayList<String> episodeFiles;
    public ArrayList<String> episodeTitles;
    public ArrayList<Integer> episodeLengths;

    // Creating a constructor for the Season class and initialising the Arraylists
    public Season()
    {  
        this.episodeFiles = new ArrayList<String>();
        this.episodeTitles = new ArrayList<String>();
        this.episodeLengths = new ArrayList<Integer>();
    }

}