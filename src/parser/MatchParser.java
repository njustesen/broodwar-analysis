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
                        if (args.length < 1)
                                parse("replays/BW/");
                        else
                                parse(args[1]);

			// List<Match> decodedMatches = new MatchDecoder().decode();
			// for(Match match : decodedMatches){
			// 	System.out.println(match.getId());
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void parse(String replayPath) throws IOException {
                File folder = new File(replayPath);
                File[] files = folder.listFiles();

                Map<String, Integer> winnerMap = new HashMap<String, Integer>(files.length);
                BufferedReader winnerReader = new BufferedReader(new FileReader(replayPath + "/winners.csv"));
                String line = winnerReader.readLine();
                while ((line = winnerReader.readLine()) != null)
                        winnerMap.put(line.substring(0, line.substring(0, line.length() -1).lastIndexOf(";")),
                                      Integer.parseInt(line.substring(line.substring(0, line.length() -1).lastIndexOf(";") + 1, line.length() -1)));

                int failed = 0;
                int parsed = 0;
                int notPresent = 0;
                for (int i = 0; i < files.length; i++) {
                        String replayName = files[i].getCanonicalPath();
                        if (! replayName.contains(".rep"))
                                continue;

                        Replay replay = BinRepParser.parseReplay(new File(replayName), true, false, true, true);
                        if (replay != null) {
                                if (winnerMap.get(files[i].getName()) == null) {
                                        // not present in the winner file
                                        notPresent++;
                                        continue;
                                }

                                try {
                                        new MatchEncoder().encode(new Match(files[i].getName(), replay, winnerMap.get(files[i].getName())));
                                        parsed++;
                                } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                        e.printStackTrace();
                                        failed++;
                                        System.out.println( "Could not parse " + replayName + "!" );
                                }
                        } else {
                                failed++;
                                System.out.println( "Could not parse " + replayName + "!" );
                        }
                }
                System.out.println("Replays parsed: " + parsed);
                System.out.println("Replays no present in winner file: " + notPresent);
                System.out.println("Replays not parsed: " + failed);
        }
}
