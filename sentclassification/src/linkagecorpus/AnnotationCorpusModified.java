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

import org.junit.Test;

import sentences.SentenceBoundary;
import utils.FileUtils;
import BuildRTEFromLinkage.BuildRTEFromLinkage;
import artcorpus.Document;
import artcorpus.Folder;

import com.aliasi.util.Files;

public class AnnotationCorpusModified{
	static String content = "";
	public AnnotationCorpusModified(){
	}
	
	/*public static void main(String[] args) throws IOException{
		fileToListgetScore("C:/Users/Hospice/combinesentences/annotationdatacorpusModifiedAddAllRemoveZeroExpandAgain.txt");

		
	}*/
	public static void main(String[] args) throws IOException{
		//String textFromFile = Files.readFromFile(new File("C:/Users/Hospice/Downloads/Thesis/annotateSarah3/sarahfiles/annotationdata1Modified.txt"),"ISO-8859-1");
		Folder folder = Folder.fromDirectory(new File("C:/Users/Hospice/annotated_data/PaperOneSentencesWithOTenseGetClassAgain/"));
	      //  int i  = 0;
	        for (Document document : folder.getDocuments()) {

				List<String> fileList1 = null;
				fileList1 = document.getLines();

	        	for (String line: document.getLines()){}
	        }


		String textFromFile = Files.readFromFile(new File("C:/Users/Hospice/Downloads/Thesis/annotateSarah3/sarahfiles/annotationdata1ModifiedAddAllRemoveZero.txt"),"ISO-8859-1");

		//C:\Users\Hospice\annotated_data\PaperOneSentencesWithOTenseGetClassAgain
		Set<String> set = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();


		String[] listFromFile = textFromFile.split("\n");
		for (String line:listFromFile){
		    String content = "";

			ArrayList<String> fileList1 = new ArrayList<String>();
			ArrayList<String> fileList2 = new ArrayList<String>();
			String lineValues[] = line.split("\t");
			fileList1 = fileToList2(lineValues[0].trim());
			fileList2 = fileToList(lineValues[2].trim());
			// System.out.println(lineValues[2].trim()+"\n"+"=============================================");
			ArrayList positiveList = new ArrayList();
			for (int k = 0; k < fileList2.size(); k++){
				positiveList.add(fileList2.get(k).replaceAll(" \t", " ").replaceAll("\n", " ").trim().split("\t")[0].replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase().trim());
				content+= FileUtils.sentExpand(lineValues[1])+"\t" + FileUtils.sentExpand(fileList2.get(k))+"\n";
				//content+= fileList2.get(k).replaceAll(" \t", " ")+"\n";
				//System.out.println(fileList2.get(k));
			}
			for (int t = 0; t < fileList1.size(); t++){

				if (!fileList1.get(t).trim().equals("")&&!positiveList.contains(fileList1.get(t).replaceAll("	method", " ").replaceAll("	other", " ").replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase())){
					content += FileUtils.sentExpand(lineValues[1])+"\t"+ FileUtils.sentExpand(fileList1.get(t).replaceAll("	method", " ").replaceAll("	other", " ").trim()) +"\t"+0+"\n" ;
				}
				else{}
			}
			
			

			String fileName = lineValues[0].trim().substring(lineValues[0].trim().lastIndexOf('/'));
			//System.out.println(fileName.lastIndexOf('/'));
			
			writeFile("C:/Users/Hospice/Linkage_Data/"+fileName, content.replaceAll("[<>&%]", " "));
			//BuildRTEFromLinkage brl = new BuildRTEFromLinkage();

			//BuildRTEFromLinkage.builCorpus("C:/Users/Hospice/combinesentences/filesforrteAgainExpand/"+fileName, "C:/Users/Hospice/combinesentences/rtetestExpand/"+fileName.replace(".txt", ".xml"));
		

		}
	//	writeFile("C:/Users/Hospice/combinesentences/annotationdatacorpusModifiedAddAllRemoveZeroExpandAgain.txt", content.replaceAll("[<>&]", " "));
		//BuildRTEFromLinkage brl = new BuildRTEFromLinkage();

		//BuildRTEFromLinkage.builCorpus("C:/Users/Hospice/combinesentences/annotationdatacorpusModifiedAddAllRemoveZeroExpand.txt", "C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/annotationdatacorpusModifiedAddAllRemoveZeroExpand.xml");
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
					//fileList.add(line.replaceAll(" \t", " ").replaceAll("\n", " ").trim().split("\t")[0]);
					fileList.add(line.trim());
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
	
	public static void fileToListgetScore(String fileString) throws IOException{
		
		//fileString = "C:/Users/Hospice/combinesentences/annotationdatacorpusModifiedAddAllRemoveZeroExpandAgain.txt";
		String output = "";
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
				
				String[] lineS  = line.trim().split("\\s+");
				
				if(lineS.length > 2){
					line = line.replaceAll(" \\d+", " ").replaceAll(" 	\\d+", " ");
					//System.out.println(line);
					output+= line+"\n";
					//fileList.add(line.replaceAll(" \t", " ").replaceAll("\n", " ").trim().split("\t")[0]);
					//fileList.add(lineS[lineS.length-1]);
				}
				else{}
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
		
		for(String s: fileList){
		//	output+=s+"\n";
			
		}
		writeFile("C:/Users/Hospice/combinesentences/annotationdatacorpusModifiedAddAllRemoveZeroExpandAgainInput.txt", output);

		//System.out.println(output);
		//return fileList; 
	}
	//@Test
	public void testfileToListgetScore() throws IOException{
		
		fileToListgetScore("C:/Users/Hospice/combinesentences/annotationdatacorpusModifiedAddAllRemoveZeroExpandAgain.txt");
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