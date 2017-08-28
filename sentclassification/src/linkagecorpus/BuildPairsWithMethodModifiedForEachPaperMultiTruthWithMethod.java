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

public class BuildPairsWithMethodModifiedForEachPaperMultiTruthWithMethod{
	
	public BuildPairsWithMethodModifiedForEachPaperMultiTruthWithMethod(){
	}
	public static void main(String[] args) throws IOException{
		String textFromFile = Files.readFromFile(new File("C:/Users/Hospice/Downloads/Thesis/annotateSarah3/sarahfiles/annotationdata1Modified.txt"),"ISO-8859-1");



		//String textFromFile = Files.readFromFile(new File("C:/Users/Hospice/Downloads/Thesis/annotateSarah3/sarahfiles/annotationdata1ModifiedAddAll.txt"),"ISO-8859-1");

		String[] listFromFile = textFromFile.split("\n");
		for (String line:listFromFile){
			
			
		    String content = "";
			ArrayList<String> fileList1 = new ArrayList<String>();
			ArrayList<String> fileList2 = new ArrayList<String>();
			
			String lineValues[] = line.split("\t");
			Map<String, String> fileToListMap = fileToListMap(lineValues[2].trim());
			fileList1 = fileToList2(lineValues[0].trim());
			fileList2 = fileToList2(lineValues[2].trim());
			for (int k = 0; k < fileList2.size(); k++){
				//Integer.parseInt(line.trim().split("\t")[1].trim()) > 0
				if (Integer.parseInt(fileList2.get(k).split("\t")[1].trim()) == 1)
					content+= lineValues[1]+"\t" + fileList2.get(k)+"\t"+1+"\n";
				if (Integer.parseInt(fileList2.get(k).split("\t")[1].trim()) == 2)
					content+= lineValues[1]+"\t" + fileList2.get(k)+"\t"+2+"\n";
				if (Integer.parseInt(fileList2.get(k).split("\t")[1].trim()) == 3)
					content+= lineValues[1]+"\t" + fileList2.get(k)+"\t"+3+"\n";
				if (Integer.parseInt(fileList2.get(k).split("\t")[1].trim()) == 4)
					content+= lineValues[1]+"\t" + fileList2.get(k)+"\t"+4+"\n";
				if (Integer.parseInt(fileList2.get(k).split("\t")[1].trim()) == 5)
					content+= lineValues[1]+"\t" + fileList2.get(k)+"\t"+5+"\n";
			}
			for (int i = 0; i< fileList1.size();i++){
				//System.out.println(fileList1.get(i));
                if(fileList1.get(i).trim().split("\t")[1].trim().equals("method")){
                	//System.out.println(fileList1.get(i).trim().split("\t")[1].trim());
				String[] lineSplit = null;
				//if (fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase().length()>16)
					//	if (fileList2.get(i).replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase().trim().contentEquals((fileList1.get(k).replaceAll("	method", " ").replaceAll("	other", " ").replaceAll("\\s", "").replaceAll("[^A-Za-z\\-]", "").replaceAll("\\d+", "").toLowerCase()))){
					if (fileToListMap.keySet().contains(fileList1.get(i).split("\t")[0].trim())){
						
						/*if (Integer.parseInt(fileToListMap.get(fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()))==1){
							content += lineValues[1]+"\t"+fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()+"\t"+1+"\n";
						}
						if (Integer.parseInt(fileToListMap.get(fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()))==2){
							content += lineValues[1]+"\t"+fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()+"\t"+2+"\n";
						}
						if (Integer.parseInt(fileToListMap.get(fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()))==3){
							content += lineValues[1]+"\t"+fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()+"\t"+3+"\n";
						}
						if (Integer.parseInt(fileToListMap.get(fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()))==4){
							content += lineValues[1]+"\t"+fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()+"\t"+4+"\n";
						}
						if (Integer.parseInt(fileToListMap.get(fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()))==5){
							content += lineValues[1]+"\t"+fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()+"\t"+5+"\n";
						}*/
					}
					else{
						//lineSplit = fileList1.get(i).toString().split("\t");
						// System.out.println(fileList1.get(i).split("\t")[0]);
						//if (lineSplit[1].trim().equals("method"))
							content += lineValues[1]+"\t"+fileList1.get(i).replaceAll("	method", " ").replaceAll("	other", " ").trim()+"\t"+0 +"\n" ;

					}
				//System.out.println(fileList1.get(0)+"\t\t"+fileList1.get(i)+"\t\t"+0);
			

			}
			}
			
			System.out.println("==============================================================");
			String fileName = lineValues[0].trim().substring(lineValues[0].trim().lastIndexOf('/'));
			//System.out.println(fileName.lastIndexOf('/'));
			
			writeFile("C:/Users/Hospice/combinesentences/filesforrteMultiTruthWithMethod/"+fileName, content.replaceAll("[<>&]", " ").replaceAll(" \t", " "));
			BuildRTEFromLinkage brl = new BuildRTEFromLinkage();

			BuildRTEFromLinkage.builCorpusMultiTruth("C:/Users/Hospice/combinesentences/filesforrteMultiTruthWithMethod/"+fileName, "C:/Users/Hospice/combinesentences/rteWithMultiTruthWithMethod/"+fileName.replace(".txt", ".xml"));
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
		/*writeFile("C:/Users/Hospice/combinesentences/annotationdata1ModifiedOtherOnlyOctWOlengthlimit.txt", content.replaceAll("[<>&]", " ").replaceAll(" \t", " "));
		BuildRTEFromLinkage brl = new BuildRTEFromLinkage();

		BuildRTEFromLinkage.builCorpus("C:/Users/Hospice/combinesentences/annotationdata1ModifiedOtherOnlyOctWOlengthlimit.txt", "C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/annotationdata1ModifiedOtherOnlyOctWOlengthlimit.xml");
	    */
	}
	private static int lastIndexof(String string) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private static Map<String, String> fileToListMap(String fileString){
		Map<String, String> fileList = new HashMap<String, String>();
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
					fileList.put(line.trim().split("\t")[0],line.trim().split("\t")[1].trim());
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