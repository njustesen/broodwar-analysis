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
    for (int i = 0; i < parts.length - 1; i++)
    {
    	parts[i] = parts[i].substring(0, parts[i].length() - 1);
    	System.out.println(parts[i]);
    }
    this.id = parts[2];
    this.players = new Player[2];
    for (int i = 0, p = 0; i < replay.replayHeader.playerNames.length; i++)
    {
      System.out.print("Player: ");
      System.out.println(replay.replayHeader.playerNames[i]);
      if (replay.replayHeader.playerNames[i] != null
          && (replay.replayHeader.playerNames[i].equals(parts[0])
              || replay.replayHeader.playerNames[i].equals(parts[1])))
      {
        System.out.print("Player found: ");
        System.out.println(replay.replayHeader.playerNames[p]);
        this.players[p] = new Player(replay.replayHeader.playerNames[i],
                                       replay.replayHeader.playerRaces[i],
                                       replay.replayHeader.playerIdActionsCounts[p],
                                       replay.replayHeader.getPlayerApm(i),
                                       replay.replayActions.players[p].actions);
        p++;
      }
    }
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
          matches.add(new Match(files[i].getName(), replay));
      }
      catch (IOException e)
      {
        continue;
      }
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Player[] getPlayers() {
    return players;
  }

  public void setPlayers(Player[] players) {
    this.players = players;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getGameLength() {
    return gameLength;
  }

  public void setGameLength(int gameLength) {
    this.gameLength = gameLength;
  }

  public String getMapName() {
    return mapName;
  }

  public void setMapName(String mapName) {
    this.mapName = mapName;
  }

  public Map getMap() {
    return map;
  }

  public void setMap(Map map) {
    this.map = map;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
