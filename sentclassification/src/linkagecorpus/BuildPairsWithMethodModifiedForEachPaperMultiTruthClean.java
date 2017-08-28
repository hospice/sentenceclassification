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

public class BuildPairsWithMethodModifiedForEachPaperMultiTruthClean{
	
	public BuildPairsWithMethodModifiedForEachPaperMultiTruthClean(){
	}
	public static void main(String[] args) throws IOException{
		String dir = "C:/Users/Hospice/Documents/boruta_data/linkageText/newfilesforrteMultiTruth/";
		
		File pdfFile = new File(dir);
		String[] filesPath;
		// array of files and directory
		filesPath = pdfFile.list();
		int t = 0;
		for(String fileName:filesPath){
			
			BuildRTEFromLinkage.builCorpusMultiTruth("C:/Users/Hospice/Documents/boruta_data/linkageText/newfilesforrteMultiTruth/"+fileName, "C:/Users/Hospice/Documents/boruta_data/linkageText/rteWithMultiTruth/"+fileName.replace(".txt", ".xml"));
		}
		
	}		
	    
	
	private static int lastIndexof(String string) {
		// TODO Auto-generated method stub
		return 0;
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