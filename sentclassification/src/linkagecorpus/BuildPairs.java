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

import com.aliasi.util.Files;

public class BuildPairs{
	static String content = "";
    public BuildPairs(){
	}
	public static void main(String[] args) throws IOException{
		String textFromFile = Files.readFromFile(new File("C:/Users/Hospice/Downloads/Thesis/annotateSarah3/sarahfiles/annotationdata.txt"),"ISO-8859-1");
		String[] listFromFile = textFromFile.split("\n");
		for (String line:listFromFile){
			ArrayList<String> fileList1 = new ArrayList<String>();
			ArrayList<String> fileList2 = new ArrayList<String>();
		    String lineValues[] = line.split("\t");
		    fileList1 = fileSentenceToList(lineValues[0].trim());
		    fileList2 = fileToList(lineValues[2].trim());
		    for (int k = 0; k < fileList2.size(); k++){
		    	
		    	content+= lineValues[1]+"\t" + fileList2.get(k)+"\t"+1+"\n";
		    }
		    for (int i = 0; i< fileList1.size();i++){
				//System.out.println(fileList1.get(i));

				if (!fileList1.get(i).trim().equals("")&&fileList2.contains(fileList1.get(i).trim())){
				   // content += lineValues[1]+"\t"+ fileList2.get(i).trim() +"\n" ;
				}
				else{
				    content += lineValues[1]+"\t"+fileList1.get(i)+"\t"+0 +"\n" ;

				}
				//System.out.println(fileList1.get(0)+"\t\t"+fileList1.get(i)+"\t\t"+0);
				
			}
		}
		/*ArrayList<String> fileList1 = new ArrayList<String>();
		ArrayList<String> fileList2 = new ArrayList<String>();	
		fileList1 = fileToList("C:/Users/Hospice/Downloads/annotateddatafile/citationfiledata1/1759-8753-2-2_sent.txt");
		fileList2 = fileToList("C:/Users/Hospice/Downloads/annotateddatafile/1759-8753-2-2_sent_rankinglinkage.txt");
		//for (String sentence:fileList1){
		for (int i = 1; i< fileList1.size();i++){
			if (fileList2.contains(fileList1.get(i).replaceAll("  ","\t").split("\t")[0]))
			    continue;
			System.out.println(fileList1.get(0)+"\t\t"+fileList1.get(i)+"\t\t"+0);
			
		}
		*/
		writeFile("C:/Users/Hospice/combinesentences/annotationcorpusAllfrom1.txt", content.replaceAll("[<>&]", " "));
		
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
			if ((line.length() == 0) || (line.trim().length() == 0))
			  continue;
			if (Integer.parseInt(line.trim().split("\t")[1].trim()) > 0)
				fileList.add(line.trim().split("\t")[0]);
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
        sentList = (ArrayList) sb.sentence(text.replaceAll("\t", " "));
		  return sentList; 
	  }

	private static void writeFile(String file, String text)throws IOException{
  
	PrintWriter writer = new PrintWriter(file, "UTF-8");
	writer.println(text);
	writer.close();
  }
}