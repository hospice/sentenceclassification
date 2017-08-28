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

import com.aliasi.util.Files;

public class BuildPairsWithMethodOther{
	static String content = "";
	
	static String SentenceClass = "";
	public BuildPairsWithMethodOther(){
	}
	public static void main(String[] args) throws IOException{
		String textFromFile = Files.readFromFile(new File("C:/Users/Hospice/Downloads/Thesis/annotateSarah3/sarahfiles/annotationdata1Modified.txt"),"ISO-8859-1");

       

		//String textFromFile = Files.readFromFile(new File("C:/Users/Hospice/Downloads/Thesis/annotateSarah3/sarahfiles/annotationdata1ModifiedAddAll.txt"),"ISO-8859-1");
        int t = 0;
        int linecount = 0;
        
        int count2=0;
		String[] listFromFile = textFromFile.split("\n");
		for (String line:listFromFile){
			 int count2value =0;

			ArrayList<String> fileList1 = new ArrayList<String>();
			ArrayList<String> fileList2 = new ArrayList<String>();
			String lineValues[] = line.split("\t");
			fileList1 = fileToList2(lineValues[0].trim());
			fileList2 = fileToList(lineValues[2].trim());
			count2 = fileList2.size();
			for (int k = 0; k < fileList2.size(); k++){
             t++;
			//	content+= lineValues[1]+"\t" + fileList2.get(k)+"\t"+1+"\n";
			}
			for (int i = 0; i< fileList1.size();i++){
				//System.out.println(fileList1.get(i));

				String[] lineSplit = null;
				//if (fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase().length()>16)
					//	if (fileList2.get(i).replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase().trim().contentEquals((fileList1.get(k).replaceAll("	method", " ").replaceAll("	other", " ").replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase()))){
					if (fileList2.contains(fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim().toLowerCase().replaceAll("[^A-Za-z]", "").replaceAll(" ", "").trim().replaceAll("\n", ""))){
						// content += lineValues[1]+"\t"+ fileList2.get(i).trim() +"\n" ;
						content+= lineValues[1]+"\t" + fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()+"\t"+1+"\n";
						count2value++;
						SentenceClass+=linecount++ +"\t"+ fileList1.get(i).replaceAll("	order", " order").split("\t")[1]+"\n";
					}
					else{
						lineSplit = fileList1.get(i).toString().split("\t");
						// System.out.println(fileList1.get(i).split("\t")[0]);
						//if (lineSplit[1].trim().equals("other"))
							//content += lineValues[1]+"\t"+fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()+"\t"+0 +"\n" ;
							content += lineValues[1]+"\t"+fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()+"\t"+0 +"\n" ;
							SentenceClass+=linecount++ +"\t"+ fileList1.get(i).replaceAll("	order", " order").split("\t")[1]+"\n";

					}
				//System.out.println(fileList1.get(0)+"\t\t"+fileList1.get(i)+"\t\t"+0);

			}
			System.out.println(line + "\t"+ count2 + "\t" + count2value+ "\t" + (count2-count2value));
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
		System.out.println(t);
		//writeFile("C:/Users/Hospice/combinesentences/annotationdata1ModifiedAgain.txt", content.replaceAll("[<>&]", " ").replaceAll(" \t", " "));
		writeFile("C:/Users/Hospice/combinesentences/annotationdata1ModifiedAgainSentenceClass.txt", SentenceClass);

		
		BuildRTEFromLinkage brl = new BuildRTEFromLinkage();

		//BuildRTEFromLinkage.builCorpus("C:/Users/Hospice/combinesentences/annotationdata1ModifiedAgain.txt", "C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/annotationdata1ModifiedAgain.xml");
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
				//	continue;
				if (Integer.parseInt(line.trim().split("\t")[1].trim()) > 0)
					fileList.add(line.trim().split("\t")[0].toLowerCase().trim().replaceAll("[^A-Za-z]", "").replaceAll(" ", "").replaceAll("\n", ""));
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
	
	
	private static ArrayList<String> fileToList2(String fileString){
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
				fileList.add(line.trim());
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