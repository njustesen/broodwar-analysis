package json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import analyser.Match;
import analyser.Player;
import analyser.Player.Race;

public class MatchDecoder {
	
	public static String folder = "matches" + File.separator;

	public static void main(String[] args) throws IOException {
		
		//List<Match> matches = new MatchDecoder().decode(1000);
		
	}

	public List<Match> decode(int n, Race race, analyser.Map.Type mapType) throws IOException{
		
		File folderFile = new File(folder);
		File[] listOfFiles = folderFile.listFiles();
		
		List<Match> matches = new ArrayList<Match>();
		int i = 0;
		for ( File file : listOfFiles ) {
			String json;
			try {
				json = readFile(file.getCanonicalPath());
				
				Gson gson = new Gson();
				Type matchType = new TypeToken<Match>(){}.getType();
		        
				Match match = gson.fromJson(json, matchType);
				//System.out.print("-");
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
				System.out.println(i + "/" + listOfFiles.length + " matches parsed \t " + matches.size() + " added. ");
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
