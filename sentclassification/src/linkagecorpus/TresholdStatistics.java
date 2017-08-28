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
import java.util.TreeMap;

import parser.ParserTest;
import sentences.SentenceBoundary;
import summary.Mainkmean;
import utils.Utilities;
import utils.ValueComparator;

import com.aliasi.util.Files;

public class TresholdStatistics{
	private Utilities help = new Utilities();
	static Map<String, Double> sim1M = new HashMap<String, Double>();
	static Map<String, Double> simM = new HashMap<String, Double>();

	static ValueComparator bvc =  new ValueComparator(sim1M);
	static TreeMap<String, Double> sorted_map = new TreeMap(bvc);
	
	static ValueComparator bvcF =  new ValueComparator(simM);
	static TreeMap<String, Double> sorted_mapF = new TreeMap(bvcF);
	static String content = "";
	static int count = 0;

   // public BuildPairsWithComparison(){
	//}
	public static void main(String[] args) throws Exception{
	processFileStatistics();
	}
	
	public static void processFileStatistics() throws Exception{ 
		String output = "testNumber"+"\t"+ "posCount"+"\t"+ "negposCount" +"\t" +"posCountF"+"\t"+ "negposCountF" +"\n";
		System.out.println(output);
		double testNumber = 0;
		double freqThreshold = (-1.0D / 0.0D);
	 //   Map<String, Double> simM = new HashMap<String, Double>();

		String textFromFile = Files.readFromFile(new File("C:/Users/Hospice/Downloads/Thesis/annotateSarah3/sarahfiles/annotationdata1Modified.txt"),"ISO-8859-1");
		String[] listFromFile = textFromFile.split("\n");
		for ( int p = 0; p < 100; p++) {
			testNumber+= 0.01;
		for (int n = 0; n <= 5; n++){
			sim1M.clear();
			sorted_map.clear();
			simM.clear();
			sorted_mapF.clear();
			count = 0;
		for (String line1:listFromFile){
			ArrayList<String> fileList1 = new ArrayList<String>();
			ArrayList<String> fileList2 = new ArrayList<String>();
		    String lineValues[] = line1.split("\t");
		    fileList1 = fileToList2(lineValues[0].trim());
		    fileList2 = fileToList(lineValues[2].trim(), n);
		    ArrayList<String> fileList = new ArrayList<String>();
		    
		    for (int k = 0; k < fileList2.size(); k++){
		    	writeFile("C:/Users/Hospice/combinesentences/fileciting.txt", lineValues[1]+"\n");
			    writeFile("C:/Users/Hospice/combinesentences/filecited.txt", fileList2.get(k)+"\n");
			    String file = "C:/Users/Hospice/combinesentences/fileciting.txt";
				String fileT = "C:/Users/Hospice/combinesentences/filecited.txt";
				ParserTest documentParserCiting = new ParserTest(file,freqThreshold );
		    	ParserTest documentParserCited = new ParserTest(fileT,freqThreshold );
		        
		    	//Mainkmean  mkm = new Mainkmean();
		    	TresholdStatistics ts = new TresholdStatistics();
		    	ts.showLinkage(documentParserCiting, documentParserCited);
		    	//content+= lineValues[1]+"\t" + fileList2.get(k)+"\t"+1+"\n";
		    }
		    for (int i = 0; i< fileList1.size();i++){
				//System.out.println(fileList1.get(i));

				if (!fileList1.get(i).trim().equals("")&&fileList2.contains(fileList1.get(i).trim())){
				   // content += lineValues[1]+"\t"+ fileList2.get(i).trim() +"\n" ;
				}
				else{
					writeFile("C:/Users/Hospice/combinesentences/filecitingF.txt", lineValues[1]+"\n");
				    writeFile("C:/Users/Hospice/combinesentences/filecitedF.txt", fileList1.get(i)+"\n");
				    String file = "C:/Users/Hospice/combinesentences/filecitingF.txt";
					String fileT = "C:/Users/Hospice/combinesentences/filecitedF.txt";
				   // content += lineValues[1]+"\t"+fileList1.get(i)+"\t"+0 +"\n" ;
					ParserTest documentParserCiting = new ParserTest(file,freqThreshold );
			    	ParserTest documentParserCited = new ParserTest(fileT,freqThreshold );
			        
			    	//Mainkmean  mkm = new Mainkmean();
			    	TresholdStatistics ts = new TresholdStatistics();
			    	ts.showLinkageForTarget(documentParserCiting, documentParserCited);
					

				}
				//System.out.println(fileList1.get(0)+"\t\t"+fileList1.get(i)+"\t\t"+0);
				
			}
		    
	
		}
		//System.out.println(count);
				
		sorted_map.putAll(sim1M);
		//System.out.println("results");
		//System.out.println(numSentences);
		
		//System.out.println(sorted_map);
		Set s=sorted_map.entrySet();
		Iterator it=s.iterator();
		int k = 0;
		String contentSimilarity = "";
		int posCount = 0;

		while(it.hasNext())
	        {
	            // key=value separator this by Map.Entry to get key and value
				
	            Map.Entry m =(Map.Entry)it.next();

	            // getKey is used to get key of Map
	            String key=(String)m.getKey();

	            // getValue is used to get value of key in Map
	            Double value=(Double)m.getValue();
	            if (value > testNumber)
	            	posCount ++;
	           // contentSimilarity += "Key :"+key+"  Value :"+value+"\n";
	           // System.out.println("Key :"+key+"  Value :"+value);
	        }
		
		sorted_mapF.putAll(simM);
		//System.out.println("results");
		//System.out.println(numSentences);
		
		//System.out.println(sorted_map);
		Set sF=sorted_mapF.entrySet();
		Iterator itF=sF.iterator();
		//int k = 0;
		//String contentSimilarity = "";
		int posCountF = 0;

		while(itF.hasNext())
	        {
	            // key=value separator this by Map.Entry to get key and value
				
	            Map.Entry m =(Map.Entry)itF.next();

	            // getKey is used to get key of Map
	            String key=(String)m.getKey();

	            // getValue is used to get value of key in Map
	            Double value=(Double)m.getValue();
	            if (value > testNumber)
	            	posCountF ++;
	           // contentSimilarity += "Key :"+key+"  Value :"+value+"\n";
	           // System.out.println("Key :"+key+"  Value :"+value);
	        }
		

	     //  System.out.println(posCount);
	       int negposCount = s.size()-posCount;
	      // System.out.println(s.size()-posCount);
	      // System.out.println(posCountF);
	       int negposCountF = sF.size()-posCountF;

	      // System.out.println(sF.size()-posCountF);
	       output+= testNumber+"\t"+ posCount+"\t"+ negposCount +"\t" +posCountF+"\t"+ negposCountF +"\n" ;
	       

		}
		 
		output+= "========================================================================================"+"\n";
	       System.out.println(output);


	}
	       writeFile("C:/Users/Hospice/combinesentences/tresholdstats3.txt", output );

	}
	private static ArrayList<String> fileToList(String fileString, int n){
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
			//for (int n = 0; n <= 5; n++){
			if (Integer.parseInt(line.trim().split("\t")[1].trim()) > n){
				count++;
				fileList.add(line.trim().split("\t")[0]);
			//System.out.println(line.trim().split("\t")[0].trim());
			}
			//System.out.println("count greater than " +n);
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
	public void showLinkage(ParserTest documentParserCiting, ParserTest documentParserCited) 
	  {
	    List sentences = documentParserCiting.getSentences();
	    //System.out.println("Sentence   "+ sentences);
	    List sparseSentences = documentParserCiting.getSparseSentences();
	    Map dictionary = documentParserCiting.getDictionary();
		//System.out.println(dictionary);
		List sentencesT = documentParserCited.getSentences();
	    List sparseSentencesT = documentParserCited.getSparseSentences();
	    //System.out.println("Sentence   "+ sparseSentences);

	    Map dictionaryT = documentParserCited.getDictionary();
	    /*if ((k > documentParser.getNumUsefulSentences()) || (k <= 0)) {
	      return null;
	    }*/
	    int numSentences = sparseSentences.size();
		
		int numSentencesT = sparseSentencesT.size();
			
			

		double[][] sim1 = new double[numSentences][numSentencesT];
		//ValueComparator bvc =  new ValueComparator(sim1M);
		//TreeMap<String, Double> sorted_map = new TreeMap(bvc);

		for (int i = 0; i < numSentences; i++) {
			for (int j = 0; j < numSentencesT; j++) {
				String str = (String)documentParserCiting.getSentences().get(i);
				String strT = (String)documentParserCited.getSentences().get(j);

				sim1[i][j] = this.help.pairwiseSimilarity(sparseSentences, sparseSentencesT, i, j);
				//System.out.println(sim1[i][j]);
				
				/////Modify to remove citing sentence
				//sim1M.put(str+"\n"+strT +"\n", sim1[i][j]);
				//System.out.println(sim1[i][j]);
				sim1M.put(str+"\t"+strT, sim1[i][j]); 
			}  
	    }
		/*sorted_map.putAll(sim1M);
		System.out.println("results");
		//System.out.println(numSentences);
		
		//System.out.println(sorted_map);
		Set s=sorted_map.entrySet();
		Iterator it=s.iterator();
		int k = 0;
		while(it.hasNext())
	        {
	            // key=value separator this by Map.Entry to get key and value
				
	            Map.Entry m =(Map.Entry)it.next();

	            // getKey is used to get key of Map
	            String key=(String)m.getKey();
				int pos = (int)sentencesT.indexOf(key);

	            // getValue is used to get value of key in Map
	            Double value=(Double)m.getValue();
	            System.out.println(pos);
	            System.out.println("Key :"+key+"  Value :"+value);
	        }
	   
	    /*for (String key : sorted_map.keySet()) {
		    //System.out.println(documentParser.getSentences().indexOf(key));
	        System.out.println("key/value: " + key + "/"+sorted_map.get(key) + "\n");
	    }*/
			
			//return sorted_map;  
	  }	
	
	
	public void showLinkageForTarget(ParserTest documentParserCiting, ParserTest documentParserCited) 
	  {
	    List sentences = documentParserCiting.getSentences();
	    //System.out.println("Sentence   "+ sentences);
	    List sparseSentences = documentParserCiting.getSparseSentences();
	    Map dictionary = documentParserCiting.getDictionary();
		//System.out.println(dictionary);
		List sentencesT = documentParserCited.getSentences();
	    List sparseSentencesT = documentParserCited.getSparseSentences();
	    //System.out.println("Sentence   "+ sparseSentences);

	    Map dictionaryT = documentParserCited.getDictionary();
	    /*if ((k > documentParser.getNumUsefulSentences()) || (k <= 0)) {
	      return null;
	    }*/
	    int numSentences = sparseSentences.size();
		
		int numSentencesT = sparseSentencesT.size();
			
			

		double[][] sim1 = new double[numSentences][numSentencesT];
		//ValueComparator bvc =  new ValueComparator(sim1M);
		//TreeMap<String, Double> sorted_map = new TreeMap(bvc);

		for (int i = 0; i < numSentences; i++) {
			for (int j = 0; j < numSentencesT; j++) {
				String str = (String)documentParserCiting.getSentences().get(i);
				String strT = (String)documentParserCited.getSentences().get(j);

				sim1[i][j] = this.help.pairwiseSimilarity(sparseSentences, sparseSentencesT, i, j);
				//System.out.println(sim1[i][j]);
				
				/////Modify to remove citing sentence
				//sim1M.put(str+"\n"+strT +"\n", sim1[i][j]);
				//System.out.println(sim1[i][j]);
				simM.put(str+"\t"+strT, sim1[i][j]); 
			}  
	    }
		/*sorted_map.putAll(sim1M);
		System.out.println("results");
		//System.out.println(numSentences);
		
		//System.out.println(sorted_map);
		Set s=sorted_map.entrySet();
		Iterator it=s.iterator();
		int k = 0;
		while(it.hasNext())
	        {
	            // key=value separator this by Map.Entry to get key and value
				
	            Map.Entry m =(Map.Entry)it.next();

	            // getKey is used to get key of Map
	            String key=(String)m.getKey();
				int pos = (int)sentencesT.indexOf(key);

	            // getValue is used to get value of key in Map
	            Double value=(Double)m.getValue();
	            System.out.println(pos);
	            System.out.println("Key :"+key+"  Value :"+value);
	        }
	   
	    /*for (String key : sorted_map.keySet()) {
		    //System.out.println(documentParser.getSentences().indexOf(key));
	        System.out.println("key/value: " + key + "/"+sorted_map.get(key) + "\n");
	    }*/
			
			//return sorted_map;  
	  }	
	private static void writeFile(String file, String text)throws IOException{
  
	PrintWriter writer = new PrintWriter(file, "UTF-8");
	writer.println(text);
	writer.close();
  }
}