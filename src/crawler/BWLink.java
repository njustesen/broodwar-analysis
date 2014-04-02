package crawler;

public class BWLink {

	private String link;
	private int winnerIdx;
	
	public BWLink(String link, int winnerIdx) {
		super();
		this.link = link;
		this.winnerIdx = winnerIdx;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getWinnerIdx() {
		return winnerIdx;
	}
	public void setWinnerIdx(int winnerIdx) {
		this.winnerIdx = winnerIdx;
	}
	
	

}
