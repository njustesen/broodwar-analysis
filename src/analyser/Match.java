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

	public Player getPlayer1() {
		return player1;
	}
	
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public void setPlayer2(Player player2) {
		this.player2 = player2;
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
