package sentences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import datacollector.MIWriter;

public class ThisFilePaperCount {
private static HashSet hs = new HashSet();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadLines();
		System.out.println(hs.size());
	}
	public  ArrayList loadLinkageFile(String fileName)
    {
		    
    ArrayList arr = new ArrayList();
    File file = null;
    BufferedReader inputStream = null;
    try {
      file = new File(fileName);
      inputStream = new BufferedReader(new FileReader(file));
      String line;
      while ((line = inputStream.readLine()) != null) {
        if (line.contains("====="))
          continue;
        //System.out.println(line.split("  ")[0].toLowerCase().trim().replaceAll("-"," "));
        int x = Integer.parseInt(line.replaceAll("  ","\t").split("\t")[1].trim());
        if ( Integer.parseInt(line.replaceAll("  ","\t").split("\t")[1].trim()) >=0)
        	arr.add(line.replaceAll("  ","\t").split("\t")[0].toLowerCase().trim().replaceAll("-"," "));
            System.out.println(line.replaceAll("  ","\t").split("\t")[0].toLowerCase().trim().replaceAll("-"," "));
      }
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }  catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (inputStream != null)
        try {
          inputStream.close();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
    }
	return arr;
  }
	
	 public static void loadLines()
	  {
	    File file = null;
	    //HashSet hs = new HashSet();
	    BufferedReader inputStream = null;
	    try {
	      file = new File("C:/Users/Hospice/backup/sharename/thisCorpus/ThisMRC.txt");
	      inputStream = new BufferedReader(new FileReader(file));
	      String line;
	      while ((line = inputStream.readLine()) != null) {
	    	  //System.out.println(line);
	        if (line.contains(".nxml.sentences"))
	         // continue;
	        	ThisFilePaperCount.hs.add(line.split(".nxml.sentences")[0]);
	        
	      }
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
