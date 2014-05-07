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
import analyser.Player.Race;

public class MatchDecoder {
	
	public static String folder = "matches" + File.separator;

	public List<Match> decode(int n, Race race, analyser.Map.Type mapType) throws IOException{
		
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
					for(Player player : match.players){
						if (player.race == race){
							matches.add(match);
							break;
						}
					}
					
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
