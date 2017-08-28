package BuildRTEFromLinkage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


public class BuildRTEFromLinkage{

	
	  public static ArrayList<String> sentenceCitation;

	  public  BuildRTEFromLinkage(){
		  BuildRTEFromLinkage.sentenceCitation = new ArrayList<String>();


	  }

	public static void main(String[] args)throws IOException{
		String corpus = "";
	    BuildRTEFromLinkage  brte = new BuildRTEFromLinkage();
	    final File folder = new File("C:/Users/Hospice/Linkage_Data/Linkagedata_Final");
	    for (final File fileName : folder.listFiles()) 
		    {
	    	BuildRTEFromLinkage.builCorpusMultiTruth("C:/Users/Hospice/Linkage_Data/Linkagedata_Final/"+fileName.getName(), "C:/Users/Hospice/Linkage_Data/Linkagedata_Final_out/"+fileName.getName().replace(".txt", ".xml"));

		    	
		    }
		
	}
	public static void builCorpusMultiTruth(String fileIn, String fileOut) throws IOException{
		String corpus = "";
	    BuildRTEFromLinkage  brte = new BuildRTEFromLinkage();
	    ArrayList<String> SCitation = brte.loadCitationLinkage(fileIn);
	
		int i = -1;
		int k = 0;
		StringBuilder result = new StringBuilder();
		     int j = 0;
			 String start = "<entailment-corpus>";
			String end = "</entailment-corpus>";
			corpus += start+"\n";
		for(String lineCitation: SCitation){
			System.out.println(lineCitation);

			if(lineCitation != null && !lineCitation.startsWith("==")){
			String[] lineLinkage =  lineCitation.split("\t+");
			
            if (lineLinkage.length > 2){
			String pair = "";
			String text = "";
			String hyp = "";

			int entVal = 0;
			i++;
					
			if (Integer.parseInt(lineLinkage[2].trim()) < 1){
			    pair += "<pair"+" task="+"\"" +"IR"+"\""+" id="+ "\""+ j++ +"\""+" entailment="+"\""+"NO"+"\""+">";
				
				text = "<t>"+lineLinkage[0]+"</t>";
				hyp = "<h>"+lineLinkage[1]+"</h>";
				pair += text + hyp +"</pair>";
			}
			if (Integer.parseInt(lineLinkage[2].trim()) ==1 ){
			    pair += "<pair"+" task="+"\"" +"IR"+"\""+" id="+ "\""+ j++ +"\""+" entailment="+"\""+"ONE"+"\""+">";
				
				text = "<t>"+lineLinkage[0]+"</t>";
				hyp = "<h>"+lineLinkage[1]+"</h>";
				pair += text + hyp +"</pair>";
			}
			if (Integer.parseInt(lineLinkage[2].trim()) == 2){
			    pair += "<pair"+" task="+"\"" +"IR"+"\""+" id="+ "\""+ j++ +"\""+" entailment="+"\""+"TWO"+"\""+">";
				
				text = "<t>"+lineLinkage[0]+"</t>";
				hyp = "<h>"+lineLinkage[1]+"</h>";
				pair += text + hyp +"</pair>";
			}
			if (Integer.parseInt(lineLinkage[2].trim()) == 3){
			    pair += "<pair"+" task="+"\"" +"IR"+"\""+" id="+ "\""+ j++ +"\""+" entailment="+"\""+"THREE"+"\""+">";
				
				text = "<t>"+lineLinkage[0]+"</t>";
				hyp = "<h>"+lineLinkage[1]+"</h>";
				pair += text + hyp +"</pair>";
			}
			if (Integer.parseInt(lineLinkage[2].trim()) == 4){
			    pair += "<pair"+" task="+"\"" +"IR"+"\""+" id="+ "\""+ j++ +"\""+" entailment="+"\""+"FOUR"+"\""+">				
				text = "<t>"+lineLinkage[0]+"</t>";
				hyp = "<h>"+lineLinkage[1]+"</h>";
				pair += text + hyp +"</pair>";
			}
			if (Integer.parseInt(lineLinkage[2].trim()) == 5){
			    pair += "<pair"+" task="+"\"" +"IR"+"\""+" id="+ "\""+ j++ +"\""+" entailment="+"\""+"FIVE"+"\""+">";
				text = "<t>"+lineLinkage[0]+"</t>";
				hyp = "<h>"+lineLinkage[1]+"</h>";
				pair += text + hyp +"</pair>";
			}
			else {
			    
			}
			
			System.out.println(pair);
			corpus+=pair+"\n";
	
		}
			}
		}
		 corpus += end;
		 writeFile(fileOut,corpus);

	}
	
	public static void builCorpus(String fileIn, String fileOut) throws IOException{
		String corpus = "";
	    BuildRTEFromLinkage  brte = new BuildRTEFromLinkage();
	    ArrayList<String> SCitation = brte.loadCitationLinkage(fileIn);
		int i = -1;
		int k = 0;
		StringBuilder result = new StringBuilder();
		     int j = 0;
			 String start = "<entailment-corpus>";
			String end = "</entailment-corpus>";
			corpus += start+"\n";
		for(String lineCitation: SCitation){
			System.out.println(lineCitation);
			if(lineCitation != null && !lineCitation.startsWith("==")){
			String[] lineLinkage =  lineCitation.split("\t+");
			
            if (lineLinkage.length > 2){
			String pair = "";
			String text = "";
			String hyp = "";

			int entVal = 0;
			i++;
			
			if (Integer.parseInt(lineLinkage[2].trim()) < 1){
			    pair += "<pair"+" task="+"\"" +"IR"+"\""+" id="+ "\""+ j++ +"\""+" entailment="+"\""+"NO"+"\""+">";				
				text = "<t>"+lineLinkage[0]+"</t>";
				hyp = "<h>"+lineLinkage[1]+"</h>";
				pair += text + hyp +"</pair>";
			}
			else {
			    pair += "<pair"+" task="+"\"" +"IR"+"\""+" id="+ "\""+ j++ +"\""+" entailment="+"\""+"YES"+"\""+">";
				text = "<t>"+lineLinkage[0]+"</t>";
				hyp = "<h>"+lineLinkage[1]+"</h>";
				pair += text + hyp +"</pair>";
			}
			
			System.out.println(pair);
			corpus+=pair+"\n";
	
		}
			}
		}
		 corpus += end;
		 writeFile(fileOut,corpus);

	}

	private void loadCitationLinkage()
  {
    File file = null;
    BufferedReader inputStream = null;
    try {
      file = new File("C:/Users/Hospice/combinesentences/annotationdata1ModifiedAndaddAll.txt");
      inputStream = new BufferedReader(new FileReader(file));
      String line;
      while ((line = inputStream.readLine()) != null) {
        BuildRTEFromLinkage.sentenceCitation.add(line.trim());
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
	
	public ArrayList<String> loadCitationLinkage(String fileName)
	  {
		ArrayList<String> sentenceCitation = new ArrayList<String>();
	    File file = null;
	    BufferedReader inputStream = null;
	    try {
	      file = new File(fileName);
	      inputStream = new BufferedReader(new FileReader(file));
	      String line;
	      while ((line = inputStream.readLine()) != null) {

	       sentenceCitation.add(line.trim());
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
		return sentenceCitation;
	  }
  private static void writeFile(String file, String text)throws IOException{
  
	PrintWriter writer = new PrintWriter(file, "UTF-8");
	writer.println(text);
	writer.close();
  }

}