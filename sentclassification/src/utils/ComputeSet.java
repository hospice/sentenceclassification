package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import datacollector.MIWriter;

public class ComputeSet {
	private static HashSet<String> chiSet = new HashSet<String>();
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadStopwords();

	}

	 public static void loadStopwords()
	  {
	    File file = null;
	    BufferedReader inputStream = null;
	    try {
	      file = new File("C:/Users/Hospice/annotated_data/imrad_data/chiAdded.txt");
	      inputStream = new BufferedReader(new FileReader(file));
	      String line;
	      while ((line = inputStream.readLine()) != null) {
	    	  //System.out.println(line);
	        if ((line.length() == 0) || (line.trim().length() == 0))
	          continue;
	        chiSet.add(line.trim());
	        
	      }
	      System.out.println(chiSet.toString().replaceAll(",","\n"));
	    } catch (FileNotFoundException ex) {
	      ex.printStackTrace();
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    } finally {
	      if (inputStream != null)
	        try {
	          inputStream.close();
	        } catch (IOException ex) {
	          ex.printStackTrace();
	        }
	    }
	  }
}
