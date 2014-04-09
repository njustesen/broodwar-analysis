package json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class TestDecoder {
	
	static final String folder = "tests" + File.separator;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public TestObject decode(){
		
		TestObject test = null;
		String json;
		try {
			json = readFile("testfile.json");

			Gson gson = new Gson();
			Type testType = new TypeToken<TestObject>(){}.getType();
	        
			test = gson.fromJson(json, testType);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return test;
		
	}

	private String readFile(String filename) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(folder + filename));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        
	        return sb.toString();
	    } finally {
	        br.close();
	    }
		
	}
	
}
