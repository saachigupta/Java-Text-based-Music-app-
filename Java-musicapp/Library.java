//Saachi Gupta
//501217408

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library 
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	private ArrayList<Podcast>      podcasts;
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	    podcasts		= new ArrayList<Podcast>(); 
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content) throws AudioContentAlreadyDownloadedException
	{
		// check if the type of the content is Song
		if (content.getType().equals(Song.TYPENAME)){
			// Cast the content to a Song object
			Song newSong = (Song) content;
			// Check if the song is already downloaded
			if (!songs.contains(newSong)){
				// If the song is not already downloaded, add it to the list of downloaded songs
				songs.add(newSong);
				System.out.println(content.getType() + " " + newSong.getTitle() + " Added to Library");
			}
			else{
				// If the song is already downloaded, update the error message and return false
				throw new AudioContentAlreadyDownloadedException("Song " + newSong.getTitle() + " already downloaded");
			}
		}
		// check if the type of the content is AudioBook
		else if (content.getType().equals(AudioBook.TYPENAME)){
			// Cast the content to an AudioBook object
			AudioBook newBook = (AudioBook) content;

			// Check if the audiobook is already downloaded
			if (! audiobooks.contains(newBook)){
				// If the audiobook is not already downloaded, add it to the list of downloaded audiobooks
				audiobooks.add(newBook);
				System.out.println(content.getType() + " " + newBook.getTitle() + " Added to Library");
			}
			else{
				// If the audiobook is already in the list of downloaded audiobooks, return false with an error message
				throw new AudioContentAlreadyDownloadedException("AudioBook " + newBook.getTitle() + " already downloaded");
			}
		}
		//check if the type of the content is Podcast
		else if (content.getType().equals(Podcast.TYPENAME)){
			// If the content is a podcast, cast it to a Podcast object
			Podcast newPodcast = (Podcast) content;
			// If the podcast is not already in the list of downloaded podcasts, add it
			if (! podcasts.contains(newPodcast)){
				podcasts.add(newPodcast);
				System.out.println(content.getType() + " " + newPodcast.getTitle() + " Added to Library");
			}
			// If the podcast is already in the list of downloaded podcasts, return false and update error message
			else {
				throw new AudioContentAlreadyDownloadedException("Podcast " + newPodcast.getTitle() + " already downloaded");
			}

		}
		// Return true to indicate that the download was successful
	}

	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		for (int i=0; i < podcasts.size(); i++){
			int index = i+1;
			System.out.print(""+ index + ". ");
			podcasts.get(i).printInfo();
			System.out.println();

		}
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i =0; i<playlists.size(); i++){
			int index = i+1;
			System.out.print(""+ index+ ". ");
			System.out.println(playlists.get(i).getTitle());

		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> artists = new ArrayList<String>();
		for (int i =0; i<songs.size();i++){ 
			// Check to see if artist is already in arraylist
			if (! artists.contains(songs.get(i).getArtist())){   
				// If not, add it to the Artists arraylist
				artists.add(songs.get(i).getArtist());
			}
		}
		for (int i=0; i< artists.size(); i++){
			int index = i+1; 
			System.out.print(""+ index+ ". ");
			System.out.println(artists.get(i));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index) throws AudioContentNotFoundException
	{
		// Check if index is valid
		if (index < 1 || index > songs.size()){
			throw new AudioContentNotFoundException("Song Not Found");
		}
		// Remove the song from all playlists that contain it
		for (Playlist playlist : playlists){
			int i = playlist.getContentindex(songs.get(index-1).getTitle());
			if (i != -1){ //if i == -1, the song does not exist in a playlist
				playlist.deleteContent(i+1);
			}
		}
		// Remove the song from the list of songs
			songs.remove(index-1);
		// Return true if the song was removed
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		Collections.sort(songs, new SongYearComparator()); //creates a new object of SongYearComparator and sorts songs based on length
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song songA, Song songB){ //uses compare method of Comparator interface
			return Integer.compare(songA.getYear(), songB.getYear());
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator()); // Creates a new object of SongLengthComparator and sorts songs based on length

	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song songA, Song songB){ //uses compare method of Comparator interface
			return Integer.compare(songA.getLength(), songB.getLength());
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);

	}
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index) throws AudioContentNotFoundException
	{
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Song Not Found");
		}
		songs.get(index-1).play();

	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public void playPodcast(int index, int season, int episode) throws AudioContentNotFoundException
	{   //Checks if index is valid
		if (index < 1 || index > podcasts.size())
	    {
		throw new AudioContentNotFoundException("Podcast Not Found");
	    }
	    if (season < 1 || season > podcasts.get(index-1).getNumberofSeasons())
		{
		throw new AudioContentNotFoundException("Season Not Found");

		}
		if (episode < 1 || episode > podcasts.get(index-1).getNumberofEpisodes())
		{
			throw new AudioContentNotFoundException("Episode Not Found");
		}
		// Update the current season and episode and then play.
	    podcasts.get(index-1).selectSeason(season); //Sets current season to the specified season
		podcasts.get(index-1).selectEpisode(episode); // sets current episode to the specified episode
		podcasts.get(index-1).play(); // plays from current season and episode
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public void printPodcastEpisodes(int index, int season) throws AudioContentNotFoundException
	{   // Checks if index is valid
		if (index < 1 || index > podcasts.size())
	    {
			throw new AudioContentNotFoundException("Podcast Not Found");
	    }
	    if (season < 1 || season > podcasts.get(index-1).getNumberofSeasons())
	    {
			throw new AudioContentNotFoundException("Season Not Found");
	    }
	   podcasts.get(index-1).selectSeason(season); // Sets current season to the specified season
	   podcasts.get(index-1).printTOC(); // Prints table of contents of particular season
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter) throws AudioContentNotFoundException
	{   //  Checks if index is valid
		if (index < 1 || index > audiobooks.size())
	    {
			throw new AudioContentNotFoundException("Audiobook Not Found");
	    }
		if (chapter < 1 || chapter > audiobooks.get(index-1).getNumberOfChapters())
		{
			throw new AudioContentNotFoundException("Chapter Not Found");
		}
		audiobooks.get(index-1).selectChapter(chapter); // sets current chapter to the specified chapter
		audiobooks.get(index-1).play(); // plays from set chapter
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index) throws AudioContentNotFoundException
	{
		if (index < 1 || index > audiobooks.size())
		{
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}
		audiobooks.get(index-1).printTOC();
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title) throws AudioContentAlreadyDownloadedException
	{
		Playlist p = new Playlist(title); // creates a new playlist object with the given title
		if (playlists.contains(p))
		{
			throw new AudioContentAlreadyDownloadedException("Playlist " + title + " already exists");
		}
		playlists.add(p); // adds playlist to list of playlists

	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title) throws AudioContentNotFoundException
	{
		for (Playlist playlist : playlists)
		{
			if (! playlist.getTitle().equals(title)){
				throw new AudioContentNotFoundException("Playlist" + title + "does not exist");
	
			}
			playlist.printContents();
		}
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle) throws AudioContentNotFoundException
	{
		for (Playlist playlist: playlists){
			if (!playlist.getTitle().equals(playlistTitle)){ 
				throw new AudioContentNotFoundException("Playlist "+ playlist + " Not Found");

			}
			else if (playlist.getTitle().equals(playlistTitle))
			{
			playlist.playAll();
			}
		}
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL) throws AudioContentNotFoundException
	{
	    for (Playlist playlist : playlists){
			if (playlist.getTitle().equals(playlistTitle)){  // finds playlist whose title equals specified title
			if (indexInPL < 1 || indexInPL > playlist.getContent().size()) // checks valid index
			{
				errorMsg = "Content Not Found";
				throw new AudioContentNotFoundException(errorMsg);
			}
			    System.out.println(playlistTitle);
				playlist.play(indexInPL);
			}
		}
	}

	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void  addContentToPlaylist(String type, int index, String playlistTitle) throws AudioContentNotFoundException
	{
		// creates new "Playlist" object with the given title
		Playlist p = new Playlist(playlistTitle);

		// Check if the playlists list contains the newly created Playlist object
		if (! playlists.contains(p)){
			errorMsg = "Playlist Not Found";
			throw new AudioContentNotFoundException(errorMsg);
		}

        // finds the target playlist based on its title
		Playlist targetPlaylist = null;
		for (Playlist playlist : playlists)
		{
			if (playlist.getTitle().equals(playlistTitle)){
				// sets the desired playlist to the target
				targetPlaylist = playlist;
				break;
			}
		}
		// Get the list of content currently in the target playlist
		ArrayList<AudioContent> content = targetPlaylist.getContent();

		// Add content to the playlist based on the given type
		switch (type) {
			case "SONG":
			// Check if the given index is valid and that the content is not already in the playlist
			if (index > 0 && index <= songs.size())
				if (!content.contains(songs.get(index-1)))
				// If the content is not already in the playlist, add it
					targetPlaylist.getContent().add(songs.get(index-1));
				
				else {
					// if the content is already in the playlisy, set an error message and return false
					errorMsg = "Content Already in Playlist";
					throw new AudioContentNotFoundException(errorMsg);
				}

			else {
				// if the index is invalid, set an erorr message and return false
				errorMsg = "Content Not Found";
				throw new AudioContentNotFoundException(errorMsg);
			}
			// return true if content is successfully added
			

			case "AUDIOBOOK":
			// If the index is valid, add the audiobook to the playlist
			if (index > 0 && index <= audiobooks.size())
				if (!content.contains(audiobooks.get(index-1)))
					targetPlaylist.getContent().add(audiobooks.get(index-1));
                
				// If the audiobook is already in the playlist, return false
				else {
					errorMsg = "Content Alreay in Playlist";
					throw new AudioContentNotFoundException(errorMsg);
				}
			// If the index is not valid, return false
			else {
				errorMsg = "Content Not Found";
				throw new AudioContentNotFoundException(errorMsg);
			}
			// If the audiobook was successfully added, return true
			

			case "PODCAST":
			// If the index is valid, and the podcast isn't already in the playlist, add the podcast to the playlist
			if (index > 0 && index <= podcasts.size())
				if (!content.contains(podcasts.get(index-1)))
					targetPlaylist.getContent().add(podcasts.get(index-1));
				
				// If the podcast is already in the playlist, return false
				else {
					errorMsg = "Content Alreay in Playlist";
					throw new AudioContentNotFoundException(errorMsg);
				}

			// If the index is not valid, return false
			else {
				errorMsg = "Content Not Found";
				throw new AudioContentNotFoundException(errorMsg);
			}
			// If the podcast was successfully added, return true
			

		// If the content type is not valid, return false
		default:
		    errorMsg = "Content Not Found";
			throw new AudioContentNotFoundException(errorMsg);
		}
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title) throws AudioContentNotFoundException
	{
		// Search for the playlist with the given title
		Playlist targetPlaylist = null;
		for (Playlist playlist : playlists){
			if (playlist.getTitle().equals(title)){
				targetPlaylist = playlist;
			}
		}
		// If playlist not found, update error message and return false
		if (targetPlaylist == null)
		{
			errorMsg = "Playlist Not Found";
			throw new AudioContentNotFoundException(errorMsg);
		}

		// Get the list of contents from the target playlist
		ArrayList<AudioContent> contents = targetPlaylist.getContent();

		// Check if the index is valid. If not, update error message and return false
		if (index < 1 || index > contents.size()){
			errorMsg = "Content Not Found";
			throw new AudioContentNotFoundException(errorMsg);
		}
		// Remove the content at the given index from the target playlist's contents list
		contents.remove(index-1);

		// Return true to indicate that the content was successfully removed
		
	}


}

