package parser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import json.MatchDecoder;
import json.MatchEncoder;

import parser.bwhf.control.BinRepParser;
import parser.bwhf.model.Replay;
import analyser.Match;

public class MatchParser {

	public static void main(String[] args) {
		
		try {
			List<Match> matches = new MatchParser().parse();
			
			new MatchEncoder().encode(matches);
			
			List<Match> decodedMatches = new MatchDecoder().decode();
			
			for(Match match : decodedMatches){
				System.out.println(match.getId());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<Match> parse() throws IOException{
		
		BinRepParser parser = new BinRepParser();
		
		File folder = new File("replays/BW/");
		File[] listOfFiles = folder.listFiles();
		
		final String[] replayNames = new String[listOfFiles.length];
		
		for(int i = 0; i < listOfFiles.length; i++){
			replayNames[i] = listOfFiles[i].getCanonicalPath();
		}
			
		int x = 0;
		int n = 0;
		List<Match> matches = new ArrayList<Match>();
		for ( final String replayName : replayNames ) {
			if (!replayName.contains(".rep"))
				continue;
			final Replay replay = parser.parseReplay( new File( replayName ), true, false, true, false );
			if ( replay != null ){
				replay.replayHeader.printHeaderInformation( new PrintWriter( System.out ) );
				matches.add(new Match(new File( replayName ).getName(), replay));
				x++;
			}else{
				System.out.println( "Could not parse " + replayName + "!" );
				n++;
			}
			System.out.println("Replays parsed: " + x);
			System.out.println("Replays not parsed: " + n);
		}
		
		return matches;
		
	}
	
}
