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

public class MatchDecoder {
	
	static final String folder = "matches" + File.separator;

	public static void main(String[] args) throws IOException {
		
		List<Match> matches = new MatchDecoder().decode();
		
	}

	public List<Match> decode() throws IOException{
		
		File folderFile = new File(folder);
		File[] listOfFiles = folderFile.listFiles();
		
		String[] filenames = new String[listOfFiles.length];
		
		for(int i = 0; i < listOfFiles.length; i++){
			filenames[i] = listOfFiles[i].getCanonicalPath();
			//break;
		}
			
		List<Match> matches = new ArrayList<Match>();
		for ( String filename : filenames ) {
			String json;
			try {
				json = readFile(filename);

				Gson gson = new Gson();
				Type matchType = new TypeToken<Match>(){}.getType();
		        
				Match match = gson.fromJson(json, matchType);
				matches.add(match);
			} catch (IOException e) {
				e.printStackTrace();
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
