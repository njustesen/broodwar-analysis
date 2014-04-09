package json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class TestEncoder {

	static final String folder = "tests" + File.separator;
	
	public static void main(String[] args) {
		
	}
	
	public void encode(TestObject test){
		
		 Gson gson = new Gson();
	     
		 //for(TestObject test : obj){
			 String json = gson.toJson(test);
			 System.out.println(json);
			 
			 saveToFile(json, "testfile.json");
		 //}
	     
		
	}
	
	private void saveToFile(String text, String filename){
		
		FileWriter out = null;
		try {
			out = new FileWriter(folder + filename);
			out.write(text);
			out.close();
			System.out.println(filename + " saved!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
