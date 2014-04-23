package parser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import json.MatchDecoder;
import json.MatchEncoder;

import parser.bwhf.control.BinRepParser;
import parser.bwhf.model.Replay;
import analyser.Match;

public class MatchParser {

	public static void main(String[] args) {

		try {
			List<Match> matches = new MatchParser().parse();

			// new MatchEncoder().encode(matches);

			// List<Match> decodedMatches = new MatchDecoder().decode();

			// for(Match match : decodedMatches){
			// 	System.out.println(match.getId());
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Match> parse() throws IOException {
                List<Match> matches = new ArrayList<Match>();
                File folder = new File("replays/BW/");
                File[] files = folder.listFiles();
                int failed = 0;

                Map<String, Integer> winnerMap = new HashMap<String, Integer>(files.length);
                BufferedReader winnerReader = new BufferedReader(new FileReader(("replays/winners.csv")));
                String line = winnerReader.readLine();
                while ((line = winnerReader.readLine()) != null)
                        winnerMap.put(line.substring(0, line.substring(0, line.length() -1).lastIndexOf(";")),
                                      Integer.parseInt(line.substring(line.substring(0, line.length() -1).lastIndexOf(";") + 1, line.length() -1)));

                for (int i = 0; i < files.length; i++) {
                        String replayName = files[i].getCanonicalPath();
                        if (! replayName.contains(".rep"))
                                continue;

                        Replay replay = BinRepParser.parseReplay(new File(replayName), true, false, true, true);
                        if (replay != null) {
                                if (winnerMap.get(files[i].getName()) == null)
                                        continue; // not present in the winner file
                                // replay.replayHeader.printHeaderInformation(new PrintWriter(System.out));
                                matches.add(new Match(files[i].getName(), replay, winnerMap.get(files[i].getName())));
                        } else {
                                failed++;
                                System.out.println( "Could not parse " + replayName + "!" );
                        }
                }
                System.out.println("Replays parsed: " + matches.size());
                System.out.println("Replays not parsed: " + failed);

                return matches;
        }
}
