package linkagecandidate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


//import utils.DocumentPreprocessor;
import utils.Readdirectory;

public class LinkageFiles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	    Readdirectory readDir = new Readdirectory();
		LinkageFiles lf = new LinkageFiles();

		String[] files = readDir.readDirectory("C:/Users/Hospice/backup/annotateddatafile/dataoutsarapart1-9/");
		for (String file : files){
			lf.loadLinkageFile("C:/Users/Hospice/backup/annotateddatafile/dataoutsarapart1-9/"+file);
			System.out.println("============================================================");

		}
		
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
	        //int x = Integer.parseInt(line.replaceAll("  ","\t").split("\t")[1].trim());
            if ( Integer.parseInt(line.replaceAll("  ","\t").split("\t")[1].trim()) >=1)
            	arr.add(line.replaceAll("  ","\t").split("\t")[0].toLowerCase().trim().replaceAll("-"," ").replaceAll("=", ""));
               // System.out.println(line.replaceAll("  ","\t").split("\t")[0].toLowerCase().trim().replaceAll("-"," "));
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
public  HashMap<String, Double> loadLinkageFileMap(String fileName)
{
	//DocumentPreprocessor documentProcessing = new DocumentPreprocessor();
HashMap<String, Double> map = new HashMap<String, Double>();
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
    //int x = Integer.parseInt(line.replaceAll("  ","\t").split("\t")[1].trim());
    if ( Integer.parseInt(line.replaceAll("  ","\t").split("\t")[1].trim()) >=1)
    	map.put(line.replaceAll("  ","\t").split("\t")[0].toLowerCase().trim().replaceAll("-"," ").replaceAll("=", ""),Double.parseDouble(line.replaceAll("  ","\t").split("\t")[1]));
       // System.out.println(line.replaceAll("  ","\t").split("\t")[0].toLowerCase().trim().replaceAll("-"," "));
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
return map;
}


}
