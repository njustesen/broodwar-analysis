package analyser;

import parser.bwhf.model.MapData;
import parser.bwhf.model.ReplayHeader;

import java.util.Date;


public class Match
{
  Player player1;
  Player player2;
  Date date;
  int gameLength;
  String mapName;
  Map map;
  String version;

  public Match(ReplayHeader header, MapData map)
  {
    this.player1 = new Player(header.playerNames[0],
                              header.playerRaces[0],
                              header.playerIdActionsCounts[0],
                              header.getPlayerApm(0));
    this.player1 = new Player(header.playerNames[1],
                              header.playerRaces[1],
                              header.playerIdActionsCounts[1],
                              header.getPlayerApm(1));
    this.map = new Map(header.mapName, map);
    this.date = header.saveTime;
    this.gameLength = header.convertFramesToSeconds(header.gameFrames);
    this.version = header.guessVersionFromDate();
  }
}
