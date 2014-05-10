package json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import analyser.Match;
import analyser.Player;
import analyser.Map;
import analyser.Player.Race;

public class MatchDecoder {

	public static String folder = "matches" + File.separator;

	public static void main(String[] args) throws IOException {

		//List<Match> matches = new MatchDecoder().decode(1000);

	}

        private File[] folders;
        private File[] files;
        private int folderIndex;
        private int fileIndex;
        private Map.Type map;
        private Race race;
        private int limit;
        private static Type type = new TypeToken<Match>(){}.getType();
        private static Gson jsonReader = new Gson();

        public MatchDecoder(String root, Map.Type map, Race race, int limit)
        {
                if ((folders = new File(root).listFiles()) == null)
                {
                        System.out.println("error: Cannot read match files.");
                }

                this.limit = limit;
                this.race = race;
                this.map = map;
                this.folderIndex = -1;
                this.fileIndex = -1;
        }

        public Match getMatch()
        {
                if (limit < 0 || folderIndex >= folders.length)
                {
                        System.out.println("error: No more match file.");
                        return null;
                }

                if (folderIndex == -1)
                {
                        if (map != null)
                        {
                                for (int index = 0; index < folders.length; index++)
                                        if (folders[index].getName().equals(map.toString()))
                                                folderIndex = index;

                                if (folderIndex == -1)
                                {
                                        System.out.println("error: Cannot read match files for the given map.");
                                        return null;
                                }
                        }
                        else
                                folderIndex = 0;

                        files = folders[folderIndex].listFiles();
                }

                if (++fileIndex >= files.length)
                {
                        if (map != null)
                        {
                                System.out.println("error: No more match on the given map.");
                                return null;
                        }
                        if (++folderIndex >= folders.length)
                        {
                                System.out.println("error: No more match file.");
                                return null;
                        }

                        fileIndex = 0;
                        files = folders[folderIndex].listFiles();
                }

                try
                {
                        Match match = jsonReader.fromJson(readFile(files[fileIndex].getCanonicalPath()), type);
                        for (Player player : match.players)
                        {
                                if (race == null || player.race == race)
                                {
                                        limit--;
                                        return match;
                                }
                        }
                }

                catch (IOException exception)
                {
                        System.out.println("error: Failed to read match file.");
                        return null;
                }
                return getMatch();
        }

        public MatchDecoder()
        {
        }

	public List<Match> decode(int n, Race race, Race secondRace, analyser.Map.Type mapType) throws IOException{

		File folderFile = new File(folder);
		File[] listOfFiles = folderFile.listFiles();
		List<File> files = new ArrayList<File>();
		for(File f : listOfFiles){
			if (f.isDirectory()){
				if (mapType == null || f.getName().equalsIgnoreCase(mapType.name())){
					List<File> mapFiles = Arrays.asList(f.listFiles());
					files.addAll(mapFiles);
					System.out.println(mapFiles.size() + " files added with the map " + f.getName());
					if (mapType != null)
						break;
				}
			}
		}
		
		List<Match> matches = new ArrayList<Match>();
		int i = 0;
		for ( File file : files ) {
			String json;
			try {
				json = readFile(file.getCanonicalPath());

				Gson gson = new Gson();
				Type matchType = new TypeToken<Match>(){}.getType();

				Match match = gson.fromJson(json, matchType);
				
				if (mapType == null || match.map.type == mapType){
					boolean a = (race == null);
					boolean b = (secondRace == null);
					for(Player player : match.players){
						if (player.race == race)
							a = true;
						else if (player.race == secondRace)
							b = true;
					}
					if (a && b)
						matches.add(match);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			if (matches.size() >= n)
				break;
			i++;
			if (i%100==0 && i != 0){
				System.out.println(i + "/" + files.size() + " matches parsed \t " + matches.size() + " added. ");
			}
		}

		return matches;

	}

	private String readFile(String filename) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filename));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }

	        return sb.toString();
	    } finally {
	        br.close();
	    }

	}

}
