//Saachi Gupta
//501217408
import java.io.File;
import java.io.IOException;
import java.util.*;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		private Map<String, Integer> contentTitles;
		private Map<String, ArrayList<Integer>> contentArtists;
		private Map<String, ArrayList<Integer>> contentGenre;

		
		public AudioContentStore()
		{
			contents = new ArrayList<AudioContent>();
			contentTitles = new HashMap<String, Integer>();
			contentArtists = new HashMap<>();
			contentGenre = new HashMap<>();

			try
			{
				File store = new File("store.txt");
				Scanner scan = new Scanner(store);
			while(scan.hasNextLine())
			{
				String TYPENAME = scan.nextLine();
				while (TYPENAME.equals("SONG"))
				{
				String id = scan.nextLine();
				String title = scan.nextLine();

				int year = scan.nextInt();
				int length = scan.nextInt();
				scan.nextLine();
				String artist = scan.nextLine();
				String composer = scan.nextLine();
				Song.Genre genre = Song.Genre.valueOf(scan.nextLine());
				int numLyrics = scan.nextInt();
				String fileNew  = "";
				for (int i=0; i<numLyrics; i++)
				{
					fileNew+= scan.nextLine() + "\n";
				}
				Song song = new Song(title, year, id, TYPENAME, fileNew, length, artist, composer, genre, fileNew);
				contents.add(song);
				System.out.println("Loading SONG");
				TYPENAME = scan.nextLine();
			    }

				while (TYPENAME.equals("AUDIOBOOK"))
				{
					String id = scan.nextLine();
					String title = scan.nextLine();
					int year = scan.nextInt();
					int length = scan.nextInt();
					scan.nextLine();
					String author = scan.nextLine();
					String narrator = scan.nextLine();

					int numChapters = scan.nextInt();
					scan.next();
					
					ArrayList<String> titles = new ArrayList<>();
					for (int i=0; i< numChapters; i++)
					{
						titles.add(scan.nextLine());
					}

					int chapterLines;

					ArrayList<String> chapters = new ArrayList<>();

					while (scan.hasNextInt())
					{
						chapterLines = scan.nextInt();

						String chapter = "";
						for (int i=0; i<chapterLines; i++)
						{
							chapter+= scan.nextLine() + "\n";
						}
						chapters.add(chapter);
					}
					AudioBook book = new AudioBook(title, year, id, TYPENAME, "", length, author, narrator, chapters, chapters);
					contents.add(book);
					System.out.println("Loading AUDIOBOOK");
					TYPENAME = scan.nextLine();
				}
			
			

			}
			scan.close();
			}
			catch (IOException e)
			{
				System.exit(1);
			}
			
			for (int i =0; i<contents.size(); i++)
			{
				contentTitles.put(contents.get(i).getTitle(), (i+1));
				
			}

			for (int i=0; i<contents.size(); i++)
			{
				if (contents.get(i) instanceof Song)
				{
					Song song = (Song) contents.get(i);
					String artist = song.getArtist();
					if (! contentArtists.containsKey(artist))
				{
					contentArtists.put(artist, new ArrayList<>());
				}
				contentArtists.get(artist).add((i+1));
					
				}

				if (contents.get(i) instanceof AudioBook)
				{
					AudioBook audiobook = (AudioBook) contents.get(i);
					String author = audiobook.getAuthor();
				if (! contentArtists.containsKey(author))
				{
					contentArtists.put(author, new ArrayList<>());
				}
				contentArtists.get(author).add((i+1));
				}
			}

			for (int i=0; i<contents.size(); i++)
			{
				if (contents.get(i) instanceof Song)
				{
					Song song = (Song) contents.get(i);
					String genre = song.getGenre().toString();
					if (! contentGenre.containsKey(genre))
				{
					contentGenre.put(genre, new ArrayList<Integer>());
				}
				contentGenre.get(genre).add((i+1));
				}

			}

		}

		public Map<String,ArrayList<Integer>> getArtistMap()
		{
			return contentArtists;
		}



		public int searchTitle(String title)
		{
			if (contentTitles.containsKey(title))
			{
				return contentTitles.get(title);
			}
			return -1;

		}

		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}

		public void getTitleInfo(String input) throws AudioContentNotFoundException
		{
			if (!contentTitles.containsKey(input))
			{
				throw new AudioContentNotFoundException("No matches for " + input);
			}
			else{
			for (String title : contentTitles.keySet())
				{
					if (title.equals(input))
					{
						int index = contentTitles.get(title);
						AudioContent content = contents.get(index-1);
							if (content instanceof Song)
							{
								Song song = (Song) content;
								System.out.print(index + ". "); 
								song.printInfo();
								System.out.println();
							}

							if (content instanceof AudioBook)
								{
									AudioBook audiobook = (AudioBook) content;
									System.out.print(index + ". "); 
									audiobook.printInfo();
									System.out.println();
								}

								
						
					}
				}
			}
			}
		public void getArtistInfo(String artistName) throws AudioContentNotFoundException
		{
			if (! contentArtists.containsKey(artistName))
			{
				throw new AudioContentNotFoundException("No matches for " + artistName);
			}
			else
			{
				ArrayList<Integer> indices = new ArrayList<>();
				for (String artist : contentArtists.keySet())
				{
					if (artist.equals(artistName))
					{
						indices = contentArtists.get(artist);

					}

				}
				for (Integer index : indices)
				{
					AudioContent content = contents.get(index-1);
					if (content instanceof Song)
							{
								Song song = (Song) content;
								System.out.print(index + ". "); 
								song.printInfo();
								System.out.println();
							}

							if (content instanceof AudioBook)
								{
									AudioBook audiobook = (AudioBook) content;
									System.out.print(index + ". "); 
									audiobook.printInfo();
									System.out.println();
								}
					
				}
			}

		}

		public void getGenreInfo(String genreName) throws AudioContentNotFoundException
		{
			if (! contentGenre.containsKey(genreName))
			{
				throw new AudioContentNotFoundException("No matches for " + genreName);
			}
			else
			{
				ArrayList<Integer> indices = new ArrayList<>();
				for (String genre : contentGenre.keySet())
				{
					if (genre.equals(genreName))
					{
						indices = contentGenre.get(genre);

					}
				}
				for (Integer index : indices)
				{
					AudioContent content = contents.get(index-1);
					if (content instanceof Song)
							{
								Song song = (Song) content;
								System.out.print(index + ". "); 
								song.printInfo();
								System.out.println();
							}

							if (content instanceof AudioBook)
								{
									AudioBook audiobook = (AudioBook) content;
									System.out.print(index + ". "); 
									audiobook.printInfo();
									System.out.println();
								}
				}
			}

		}

		public ArrayList<AudioContent> getArtistContents(String artistName) throws AudioContentNotFoundException
		{
			ArrayList<AudioContent> result = new ArrayList<>();
			ArrayList<Integer> indices = new ArrayList<>();

			if (! contentArtists.containsKey(artistName))
			{
				throw new AudioContentNotFoundException(artistName + " not found");
			}
			else 
			{
				for (String artist : contentArtists.keySet())
				{
					if (artist.equals(artistName))
					{
						indices = contentArtists.get(artist);

					}

				}
				for (Integer index : indices)
				{
					result.add(contents.get(index-1));

				}
			}
			return result;

		}

		public ArrayList<AudioContent> getGenreContents(String genreName) throws AudioContentNotFoundException
		{
			ArrayList<AudioContent> result = new ArrayList<>();
			ArrayList<Integer> indices = new ArrayList<>();

			if (! contentGenre.containsKey(genreName))
			{
				throw new AudioContentNotFoundException(genreName+ " not found");
			}
			else 
			{
				for (String genre : contentGenre.keySet())
				{
					if (genre.equals(genreName))
					{
						indices = contentGenre.get(genre);

					}

				}
				for (Integer index : indices)
				{
					result.add(contents.get(index-1));

				}
			}
			return result;

		}
		
}

