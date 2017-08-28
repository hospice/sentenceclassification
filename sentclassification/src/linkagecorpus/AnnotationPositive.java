package linkagecorpus;

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

import sentences.SentenceBoundary;
import BuildRTEFromLinkage.BuildRTEFromLinkage;
import artcorpus.Document;
import artcorpus.Folder;

import com.aliasi.util.Files;

public class AnnotationPositive{
	static String content = "";
    public AnnotationPositive(){
	}
	public static void main(String[] args) throws IOException{
		
		
		String textFromFile = Files.readFromFile(new File("C:/Users/Hospice/Downloads/Thesis/annotateSarah3/sarahfiles/annotationdata1ModifiedAddAllRemoveZero.txt"),"ISO-8859-1");

		Set<String> set = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();

		String[] listFromFile = textFromFile.split("\n");
		for (String line:listFromFile){
			ArrayList<String> fileList1 = new ArrayList<String>();
			ArrayList<String> fileList2 = new ArrayList<String>();
		    String lineValues[] = line.split("\t");
		    fileList1 = fileToList2(lineValues[0].trim());
		    fileList2 = fileToList(lineValues[2].trim());
		   // System.out.println(lineValues[2].trim()+"\n"+"=============================================");
		    ArrayList positiveList = new ArrayList();
		    for (int k = 0; k < fileList2.size(); k++){
		    	positiveList.add(fileList2.get(k).replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase().trim());
		    	content+= lineValues[1]+"\t" + fileList2.get(k)+"\t"+1+"\n";
		    	//content+= fileList2.get(k).replaceAll(" \t", " ")+"\n";
                //System.out.println(fileList2.get(k));
		    }
		   for (int t = 0; t < fileList1.size(); t++){
			   
			   if (!fileList1.get(t).trim().equals("")&&positiveList.contains(fileList1.get(t).replaceAll("	method", " ").replaceAll("	other", " ").replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase())){
				    content += lineValues[1]+"\t"+ fileList1.get(t).replaceAll("	method", " ").replaceAll("	other", " ").trim() +"\t"+0+"\n" ;
				}
		   }
			   
		    Iterator itr = fileList1.iterator();

		    String nextIt = "";
	    	//while(itr.hasNext()){
	    		//nextIt = ((String) itr.next());
		    for (int k = 0; k< fileList1.size();k++){
	    		//System.out.println(nextIt);
		    for (int i = 0; i< fileList2.size();i++){
		    	set.add(fileList2.get(i));
				//System.out.println(fileList1.get(i));
		    		//System.out.println(itr.next());
		    		try {
						
						if (fileList1.get(k).replaceAll("	method", " ").replaceAll("	other", " ").replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase().length()>16)
							if (fileList2.get(i).replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase().trim().contentEquals((fileList1.get(k).replaceAll("	method", " ").replaceAll("	other", " ").replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase()))){
                              set2.add(fileList2.get(i));
								
						}
						
						
						else{

							
							
						}//else{}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//set.add(fileList2.get(i));

		    	}
				String[] lineSplit = null;
				//if (fileList2.contains(fileList1.get(i).replaceAll("	method", " ").trim())){
				   // content += lineValues[1]+"\t"+ fileList2.get(i).trim() +"\n" ;
					//content += fileList1.get(i);
					//System.out.println(fileList1.get(i));
				}
				
			}
		set.removeAll(set2);
		
		for (String s: set){
			
			//System.out.println(s);
		}
		writeFile("C:/Users/Hospice/combinesentences/annotationdatacorpusModifiedAddAllRemoveZero.txt", content.replaceAll("[<>&]", " "));
        BuildRTEFromLinkage brl = new BuildRTEFromLinkage();
		
		BuildRTEFromLinkage.builCorpus("C:/Users/Hospice/combinesentences/annotationdatacorpusModifiedAddAllRemoveZero.txt", "C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/annotationdatacorpusModifiedAddAllRemoveZero.xml");
		}
		
	private static ArrayList<String> fileToList(String fileString){
	    ArrayList<String> fileList = new ArrayList<String>();
		File file = null;
		BufferedReader inputStream = null;
		try {
		  file = new File(fileString);
		  inputStream = new BufferedReader(new FileReader(file));
		  String line;
		  while ((line = inputStream.readLine()) != null) {
			//if ((line.length() == 0) || (line.trim().length() == 0))
			 // continue;
			if (Integer.parseInt(line.trim().split("\t")[1].trim()) > 0)
				fileList.add(line.replaceAll(" \t", " ").replaceAll("\n", " ").trim().split("\t")[0]);
			//System.out.println(line.trim());
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
		  return fileList; 
	  }
	private static ArrayList<String> fileToList2(String fileString){
	    ArrayList<String> fileList = new ArrayList<String>();
		File file = null;
		BufferedReader inputStream = null;
		try {
		  file = new File(fileString);
		  inputStream = new BufferedReader(new FileReader(file));
		  String line;
		  while ((line = inputStream.readLine()) != null) {
			//if ((line.length() == 0) || (line.trim().length() == 0))
			 // continue;
			fileList.add(line.replaceAll(" \t", " ").replaceAll("\n", " ").trim());
			//System.out.println(line.trim().split("\t")[0].trim());
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
		  return fileList; 
	  }
	private static ArrayList<String> fileSentenceToList(String fileString) throws IOException{
		SentenceBoundary sb = new SentenceBoundary();
		File textFile = new File( fileString);
		String text = Files.readFromFile(textFile,"ISO-8859-1");
		
		ArrayList sentList = new ArrayList();
        sentList = (ArrayList) sb.sentence(text);
		  return sentList; 
	  }

	private static void writeFile(String file, String text)throws IOException{
  
	PrintWriter writer = new PrintWriter(file, "UTF-8");
	writer.println(text);
	writer.close();
  }
}