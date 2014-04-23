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

  public Match(String fileName, Replay replay, int winner) throws Exception
  {
    String[] parts = fileName.substring(0, fileName.lastIndexOf('.')).split("[Z, P, T]_");
    if (parts.length != 3)
    {
      String s = "";
      for (String part : parts)
        s += part + " - ";
      throw new Exception("not 3 parts: " + s);
    }
    this.id = parts[parts.length - 1];
    this.players = new Player[2];
    int p = 0;
    for (int i = 0; i < replay.replayHeader.playerNames.length; i++)
      if (replay.replayHeader.playerNames[i] != null
          && (replay.replayHeader.playerNames[i].replaceAll(" ", "").replaceAll("[^\\x00-\\x7F]|:", "").contains(parts[0])
              || replay.replayHeader.playerNames[i].replaceAll(" ", "").replaceAll("[^\\x00-\\x7F]|:", "").contains(parts[1])))
      {
        if (p == 2)
        {
          String s = "";
          for (String part : replay.replayHeader.playerNames)
            s += part + " - ";

          throw new Exception("more than 2 players: " + s + " | " + parts[0] + " - " + parts[1]);
        }

        this.players[p++] = new Player(replay.replayHeader.playerNames[i],
                                       replay.replayHeader.playerNames[i].replaceAll(" ", "").replaceAll("[^\\x00-\\x7F]", "").contains(parts[winner]),
                                       replay.replayHeader.playerRaces[i],
                                       replay.replayHeader.playerIdActionsCounts[replay.replayHeader.playerIds[i]],
                                       replay.replayHeader.getPlayerApm(i),
                                       replay.replayActions.playerNameActionListMap.get(replay.replayHeader.playerNames[i]));
      }

    if (p != 2)
    {
      String s = "";
      for (String part : replay.replayHeader.playerNames)
        s += part + " - ";
      throw new Exception("less than 2 players: " + s + " | " + parts[0] + " - " + parts[1]);

    }

    this.map = new Map(replay.replayHeader.mapName,
                       replay.replayHeader.mapWidth,
                       replay.replayHeader.mapHeight,
                       replay.mapData);
    this.date = replay.replayHeader.saveTime;
    this.gameLength = replay.replayHeader.convertFramesToSeconds(replay.replayHeader.gameFrames);
    this.version = replay.replayHeader.guessVersionFromDate();
  }
}
