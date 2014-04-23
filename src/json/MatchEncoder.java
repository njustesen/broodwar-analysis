package json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import analyser.Match;

public class MatchEncoder {

	static final String folder = "matches" + File.separator;

	public static void main(String[] args) {

		// Get matches
		List<Match> matches = new ArrayList<Match>();

		// Encode
		new MatchEncoder().encode(matches);

	}

	public void encode(List<Match> matches){

		 Gson gson = new Gson();

		 for(Match match : matches){
			 String json = gson.toJson(match);
			 System.out.println(json);
			 saveToFile(json, match.id);
		 }

	}

	private void saveToFile(String text, String filename){

		FileWriter out = null;
		try {
			out = new FileWriter(folder + filename);
			out.write(text);
			out.close();
			System.out.println(filename + " saved!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
