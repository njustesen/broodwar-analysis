package analyser;

import parser.bwhf.model.ReplayHeader;
import parser.bwhf.model.Replay;
import parser.bwhf.control.BinRepParser;

import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Match
{
  String id;
  Player[] players;
  Date date;
  int gameLength;
  String mapName;
  Map map;
  String version;

  public Match(String fileName, Replay replay)
  {
    // TODO: get the winner

    fileName = fileName.substring(0, fileName.lastIndexOf('.'));
    String[] parts = fileName.split("_");
    for (String part : parts)
      System.out.println(part);
    this.id = parts[2];
    for (int i = 0, p = 0; i < replay.replayHeader.playerNames.length; i++)
      if (replay.replayHeader.playerNames[i] != null
          && (replay.replayHeader.playerNames[i].equals(parts[0])
              || replay.replayHeader.playerNames[i].equals(parts[1])))
        this.players[p++] = new Player(replay.replayHeader.playerNames[i],
                                       replay.replayHeader.playerRaces[i],
                                       replay.replayHeader.playerIdActionsCounts[i],
                                       replay.replayHeader.getPlayerApm(i),
                                       replay.replayActions.players[i].actions);

      this.map = new Map(replay.replayHeader.mapName,
                         replay.mapData);
    this.date = replay.replayHeader.saveTime;
    this.gameLength = replay.replayHeader.convertFramesToSeconds(replay.replayHeader.gameFrames);
    this.version = replay.replayHeader.guessVersionFromDate();
  }

  public static void main(String[] argv)
  {
    List<Match> matches = new ArrayList<Match>();
    File folder = new File(argv.length >= 20 ? argv[1] : "../replays/BW/");
    File[] files = folder.listFiles();

    for (int i = 0; i < files.length; i++)
    {
      try
      {
        String replayName = files[i].getCanonicalPath();
        if (! replayName.contains(".re"))
          continue;

        Replay replay = BinRepParser.parseReplay(new File(replayName), true, false, true, true);
        if (replay != null)
          matches.add(new Match(files[i].getName(), replay));
      }
      catch (IOException e)
      {
        continue;
      }
    }
  }
}
