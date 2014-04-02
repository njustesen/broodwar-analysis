package crawler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class BWCrawler {
	
	// ROTW
	static String URL = "http://bwreplays.com";
	static String OUTPUT_FOLDER = "replays" + File.separator + "BW";
	
	// Member uploads
	//static String URL = "http://www.gamereplays.org/starcraft2/replays.php?game=33&show=index&tab=upcoming&st=";
	//static String OUTPUT_FOLDER = "replays" + File.separator + "members";
	
	static int DELAY = 1000;
	static boolean done = false;
	static final int BUFFER_SIZE = 4096;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int st = 1;
		while(!done){
			List<String> links = getLinks(st);
			
			for(int i = 0; i < links.size(); i++)
				links.set(i, URL + links.get(i));
			
			if (links.isEmpty())
				break;
				
			try {
				downloadFiles(links);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			
			st += 1;
			
		}
			
	}
	

	private static List<String> getLinks(int st) {
		List<String> links = new ArrayList<String>();
		try {
            URL replays = new URL(URL + "/p" + st);
            BufferedReader in = new BufferedReader(new InputStreamReader(replays.openStream()));
            String inputLine; 
 
            while ((inputLine = in.readLine()) != null) {
                
                String link = getLink(inputLine);
                if (link != null)
                	links.add(link);
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
			System.out.println("Link found: " + l);
			return l;
			
		}
		
		return null;
	}

	private static void downloadFiles(List<String> links) throws IOException {
		
		for(String link : links){
			//link = link.replace("&amp;", "&");
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
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    public static void downloadFile(String fileURL)
            throws IOException {
        URL url = new URL(fileURL);
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
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }
 
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);
 
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
 
            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }


}
