//Saachi Gupta
//501217408

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())

		{
			String action = scanner.nextLine();
			try{
			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) // download particular Audiocontent from Store
			{
				int startIndex = 0;
				
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					startIndex = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				int endIndex = 0;
				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt())
				{
					endIndex = scanner.nextInt();
					scanner.nextLine();
				}

				for (int i=startIndex ; i<=endIndex ; i++)
				{
				AudioContent content = store.getContent(i);
				try{
				mylibrary.download(content);
				
				}
				catch (AudioContentAlreadyDownloadedException e)
				{
					System.out.println(e.getMessage());
				}
			}
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				int index = 0;
				System.out.print("Song Number: ");

				if (scanner.hasNextInt()){
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				
			// Print error message if the song doesn't exist in the library

				mylibrary.playSong(index);

				
			}
		}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				int index = 0;
				System.out.print("Audio Book Number: ");

				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				
			// Print error message if the book doesn't exist in the library

				mylibrary.printAudioBookTOC(index);

			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int index = 0;
				System.out.print("Audio Book Number: ");

				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}

				int chapterNum = 0;
				System.out.print("Chapter: ");

				if (scanner.hasNextInt())
				{
					chapterNum = scanner.nextInt();
					scanner.nextLine();
				}

			// Print error message if the song doesn't exist in the library

				mylibrary.playAudioBook(index, chapterNum);

				
			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				int index = 0;
				System.out.print("Podcast Number: ");

				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				int seasonNum = 0;
				System.out.print("Season: ");

				if (scanner.hasNextInt())
				{
					seasonNum = scanner.nextInt();
					scanner.nextLine();
				}

				mylibrary.printPodcastEpisodes(index, seasonNum);
			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				int index = 0;
				System.out.print("Podcast Number: ");

				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				int seasonNum = 0;
				System.out.print("Season: ");

				if (scanner.hasNextInt())
				{
					seasonNum = scanner.nextInt();
					scanner.nextLine();
				}
				int episodeNum = 0;
				System.out.print("Episode: ");

				if (scanner.hasNextInt())
				{
					episodeNum = scanner.nextInt();
					scanner.nextLine();
				}

				mylibrary.playPodcast(index, seasonNum, episodeNum);

			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				String playlistTitle = "";
				System.out.print("Playlist Title: ");

				if (scanner.hasNext())
				{
					playlistTitle = scanner.next();
					scanner.nextLine();
				}
				mylibrary.playPlaylist(playlistTitle);

			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				String playlistTitle = "";
				System.out.print("Playlist Title: ");

				if (scanner.hasNext())
				{
					playlistTitle = scanner.next();
					scanner.nextLine();
				}
				int contentNum = 0;
				System.out.print("Content Number: ");

				if (scanner.hasNextInt())
				{
				    contentNum = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.playPlaylist(playlistTitle, contentNum);
				
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int index = 0;
				System.out.print("Library Song #: ");

				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				try 
				{
					mylibrary.deleteSong(index);
				}
				catch (AudioContentNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
					
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				String playlistTitle = "";
				System.out.print("Playlist Title: ");

				if (scanner.hasNext())
				{
					playlistTitle = scanner.next();
					scanner.nextLine();
				}
				try
				{
					mylibrary.makePlaylist(playlistTitle);
				}
				catch (AudioContentAlreadyDownloadedException e)
				{
					System.out.println(e.getMessage());
				}
				
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String playlistTitle = "";
				System.out.print("Playlist Title: ");

				if (scanner.hasNext())
				{
					playlistTitle = scanner.next();
					scanner.nextLine();
				}
				mylibrary.printPlaylist(playlistTitle);

			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				String playlistTitle = "";
				System.out.print("Playlist Title: ");

				if (scanner.hasNext())
				{
					playlistTitle = scanner.next();
					scanner.nextLine();
				}
				String contentType = "";
				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");

				if (scanner.hasNext())
				{
					contentType = scanner.next();
					scanner.nextLine();
				}
				contentType = contentType.toUpperCase();
				int libraryIndex = 0;
				System.out.print("Library Content #: ");

				if (scanner.hasNextInt())
				{
				    libraryIndex = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.addContentToPlaylist(contentType, libraryIndex, playlistTitle);
				
				
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				String playlistTitle = "";
				System.out.print("Playlist Title: ");

				if (scanner.hasNext())
				{
					playlistTitle = scanner.next();
					scanner.nextLine();
				}
				int contentNum = 0;
				System.out.print("Playlist Content #: ");

				if (scanner.hasNextInt())
				{
				    contentNum = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.delContentFromPlaylist(contentNum, playlistTitle);
				
			}

			else if (action.equalsIgnoreCase("SEARCH"))	// print playlist content
			{
				String inputTitle = "";
				System.out.print("Title: ");

				if (scanner.hasNextLine())
				{
					inputTitle = scanner.nextLine();
				}
				try
				{
					store.getTitleInfo(inputTitle);
				}
				catch (AudioContentNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
			}

			else if (action.equalsIgnoreCase("SEARCHA"))	// print playlist content
			{
				String inputArtist = "";
				System.out.print("Artist: ");

				if (scanner.hasNextLine())
				{
					inputArtist = scanner.nextLine();
				}
				try
				{
					store.getArtistInfo(inputArtist);;
				}
				catch (AudioContentNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
			}

			else if (action.equalsIgnoreCase("SEARCHG"))	// print playlist content
			{
				String inputGenre = "";
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");

				if (scanner.hasNextLine())
				{
					inputGenre = scanner.nextLine();
				}
				inputGenre = inputGenre.toUpperCase();
				try
				{
					store.getGenreInfo(inputGenre);
				}
				catch (AudioContentNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
			}
			
			else if (action.equalsIgnoreCase("DOWNLOADA"))	// print playlist content
			{
				String artistName = "";
				System.out.print("Artist Name: ");

				if (scanner.hasNextLine())
				{
					artistName = scanner.nextLine();
				}
				ArrayList<AudioContent> artistContent = store.getArtistContents(artistName);
				for (AudioContent content : artistContent)
				try
				{
					mylibrary.download(content);
				}
				catch (AudioContentAlreadyDownloadedException e)
				{
					System.out.println(e.getMessage());
				}
				catch (AudioContentNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
			}

			else if (action.equalsIgnoreCase("DOWNLOADG"))	// print playlist content
			{
				String genreName = "";
				System.out.print("Genre: ");

				if (scanner.hasNextLine())
				{
					genreName = scanner.nextLine();
				}
				genreName = genreName.toUpperCase();
				ArrayList<AudioContent> genreContent = store.getGenreContents(genreName);
				for (AudioContent content : genreContent)
				try
				{
					mylibrary.download(content);
				}
				catch (AudioContentAlreadyDownloadedException e)
				{
					System.out.println(e.getMessage());
				}
				catch (AudioContentNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
			}
				

			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}
		}
		catch (AudioContentNotFoundException e)
		{
			System.out.println(e.getMessage());
		}

		catch (AudioContentAlreadyDownloadedException e)
		{
			System.out.println(e.getMessage());
		}

			System.out.print("\n>");
		}
	}
}
