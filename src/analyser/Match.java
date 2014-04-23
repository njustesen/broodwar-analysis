package analyser;

import parser.bwhf.model.Replay;
import parser.bwhf.control.BinRepParser;

import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Match
{
  public String id;
  public Player[] players;
  public Date date;
  public int gameLength;
  public String mapName;
  public Map map;
  public String version;

  public Match(String fileName, Replay replay, int winner)
  {
    String[] parts = fileName.substring(0, fileName.lastIndexOf('.')).split("_");
    for (int i = 0; i < parts.length - 1; i++)
    	parts[i] = parts[i].substring(0, parts[i].length() - 1);
    this.id = parts[2];
    this.players = new Player[2];
    for (int i = 0, p = 0; i < replay.replayHeader.playerNames.length; i++)
      if (replay.replayHeader.playerNames[i] != null
          && (replay.replayHeader.playerNames[i].replaceAll(" ", "").replaceAll("[^\\x00-\\x7F]", "").contains(parts[0])
              || replay.replayHeader.playerNames[i].replaceAll(" ", "").replaceAll("[^\\x00-\\x7F]", "").contains(parts[1])))
        this.players[p++] = new Player(replay.replayHeader.playerNames[i],
                                       replay.replayHeader.playerNames[i].replaceAll(" ", "").replaceAll("[^\\x00-\\x7F]", "").contains(parts[winner]),
                                       replay.replayHeader.playerRaces[i],
                                       replay.replayHeader.playerIdActionsCounts[replay.replayHeader.playerIds[i]],
                                       replay.replayHeader.getPlayerApm(i),
                                       replay.replayActions.playerNameActionListMap.get(replay.replayHeader.playerNames[i]));

    this.map = new Map(replay.replayHeader.mapName,
                       replay.replayHeader.mapWidth,
                       replay.replayHeader.mapHeight,
                       replay.mapData);
    this.date = replay.replayHeader.saveTime;
    this.gameLength = replay.replayHeader.convertFramesToSeconds(replay.replayHeader.gameFrames);
    this.version = replay.replayHeader.guessVersionFromDate();
  }

  public static void main(String[] argv)
  {
    List<Match> matches = new ArrayList<Match>();
    File folder = new File(argv.length != 0 ? argv[0] : "replays/BW/");
    File[] files = folder.listFiles();

    for (int i = 0; i < files.length; i++)
    {
      try
      {
        String replayName = files[i].getCanonicalPath();
        if (! replayName.contains(".rep"))
          continue;

        Replay replay = BinRepParser.parseReplay(new File(replayName), true, false, true, true);
        if (replay != null)
          matches.add(new Match(files[i].getName(), replay, 0));
      }
      catch (IOException e)
      {
        continue;
      }
    }
  }
}
