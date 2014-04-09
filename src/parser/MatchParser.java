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

	public List<Match> parse() throws IOException {
                List<Match> matches = new ArrayList<Match>();
                File folder = new File("replays/BW/");
                File[] files = folder.listFiles();
                int failed = 0;

                for (int i = 0; i < files.length; i++) {
                        String replayName = files[i].getCanonicalPath();
                        if (! replayName.contains(".rep"))
                                continue;

                        Replay replay = BinRepParser.parseReplay(new File(replayName), true, false, true, true);
                        if (replay != null) {
                                replay.replayHeader.printHeaderInformation(new PrintWriter(System.out));
                                matches.add(new Match(files[i].getName(), replay));
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
