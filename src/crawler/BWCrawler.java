package crawler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BWCrawler {
	
	// ROTW
	static String URL = "http://bwreplays.com";
	static String OUTPUT_FOLDER = "replays" + File.separator + "BW";
	static String WINNER_FILE = "replays" + File.separator + "BW" + File.separator + "winners.csv";
	
	static int DELAY = 1000;
	static boolean done = false;
	private static Map<String, Integer> files;
	static final int BUFFER_SIZE = 4096;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		files = new HashMap<String, Integer>();
		
		int st = 1;
		while(!done){
			
			System.out.println("Page="+st);
			
			List<BWLink> links = getLinks(st);
			
			if (links.isEmpty())
				break;
			
			try {
				downloadFiles(links);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			
			updateWinnerFile();
			
			st += 1;
			
		}
			
	}
	
	private static void updateWinnerFile() {
		
		File f = new File(WINNER_FILE);
		if(!f.exists() && !f.isDirectory()) { 
			FileWriter out = null;
			try {
				out = new FileWriter(WINNER_FILE);
				out.write("replay;winner;\n");
				out.close();
				System.out.println(WINNER_FILE + " is created!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			FileWriter out;
			try {
				out = new FileWriter(WINNER_FILE, true);
				for(String rep : files.keySet()){
					out.write(rep + ";" + files.get(rep) + ";\n");
				}
				files.clear();
				out.close();
				System.out.println(WINNER_FILE + " is updated!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}


	private static List<BWLink> getLinks(int st) {
		List<BWLink> links = new ArrayList<BWLink>();
		try {
			// Get HTML
            URL replays = new URL(URL + "/p" + st);
            BufferedReader in = new BufferedReader(new InputStreamReader(replays.openStream()));
            String inputLine; 
            String website = "";
            while ((inputLine = in.readLine()) != null)
            	website += inputLine;
            website = website.trim();
            
            // Clean rows
            String table = website.split("<table class=\"tableclass\">")[1].split("</table>")[0];
            String[] rows = table.split("<tr class=");
            List<String> cleaned = new ArrayList<String>();
            for(String row : rows){
            	String clean = "";
            	if (row.contains("\"row1\">"))
            		clean = row.split("\"row1\">")[1];
            	else if (row.contains("\"row2\">"))
            		clean = row.split("\"row2\">")[1];
            	else
            		continue;
            	clean = clean.split("</tr>")[0];
            	cleaned.add(clean);
            }
            
            // Parse link and winner
            for(String rep : cleaned){
            	
            	int winner = -1;
            	
            	// Parse winner
            	if(rep.contains("<span class=\"winner\"")){
            		
            		String first = rep.split("<td class=\"name race")[1];
            		
            		String second = rep.split("<td class=\"name race")[2];
            		
            		first = first.split("<td class=\"name racep\">")[0];
            		
            		if (first.contains("<span class=\"winner\""))
            			winner = 0;
            		else if (second.contains("<span class=\"winner\""))
            			winner = 1;
            	}
            	
            	// Parse replay if winner found
            	if (winner != -1){
            		String link = rep.split("<td class=\"dl\"><a href=\"")[1].split("\">download</a></td>")[0];
            		
            		links.add(new BWLink(URL + link, winner));
            		
            	}
            	
            }
            
            in.close(); 
 
        } catch (MalformedURLException me) {
            System.out.println(me); 
 
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
		
		return links;
	}

	private static String getLink(String line) {
		
		if(line.contains("download</a>")){
			
			String l = line.split("<a href=\"")[1].split("\">download")[0];
			//System.out.println("Link found: " + l);
			return l;
			
		}
		
		return null;
	}

	private static void downloadFiles(List<BWLink> links) throws IOException {
		
		for(BWLink link : links){
			
			downloadFile(link);
			
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
     * Downloads a file from a URL
     * @param link.getLink() HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    public static void downloadFile(BWLink link)
            throws IOException {
        URL url = new URL(link.getLink());
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
 
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = link.getLink().substring(link.getLink().lastIndexOf("/") + 1,
                        link.getLink().length());
            }
 
//            System.out.println("Content-Type = " + contentType);
//            System.out.println("Content-Disposition = " + disposition);
//            System.out.println("Content-Length = " + contentLength);
//            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = OUTPUT_FOLDER + File.separator + fileName;
            //String saveFilePath = fileName;
             
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println(fileName + " downloaded.");
            
            files.put(fileName, link.getWinnerIdx());
            
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }


}
