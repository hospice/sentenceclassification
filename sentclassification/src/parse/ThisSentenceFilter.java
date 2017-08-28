package parse;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import utils.TermTokenizer;

public class ThisSentenceFilter {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
			 // Sentences sentences = new Sentences();
		  HashMap<String, Double> hm = new HashMap<String, Double>();

		try{
			File f = new File("C:/xampp/htdocs/annotateSelfCorpus/data/ThisResultonesentence.txt");
			  TermTokenizer tk = new TermTokenizer();
			  f.createNewFile();
			  PrintWriter out1 = new PrintWriter(new FileWriter(f));
			  // Open the file that is the first 
			  // command line parameter
			  String output = "";
			 FileInputStream fstream = new FileInputStream("C:/xampp/htdocs/annotateSelfCorpus/data/ThisResultdatabothsentences.txt");
			  // Get the object of DataInputStream
			  
			  //FileInputStream fstream = new FileInputStream("./AllNgram.txt");

			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null )   {
				//  System.out.println(strLine);
				  if (strLine.startsWith("This")||strLine.startsWith("--")||strLine.endsWith("Line does not have a line to pair with; either it is Line 1 or it is a line in a group of 3 or more lines"))
				  {}
				 // if (strLine.isEmpty())
				  //{}

				  
				  else{
					  
			  // Print the content on the console
					  //String[] sentencesT = strLine.split("sentences[-:]\\d+[-:]");
					 // if (strLine.startsWith("This"))
						 // System.out.println (sentences[1]);
					      //output+=sentencesT[0].split("/")[1]+"\t"+sentencesT[1]+"\n";
					  output+=strLine.replaceAll(",","")+",result"+"\n";
					//  hm = sentences.getTextFrequencyMap(strLine);
					  
				  }
			  }
			 
			  out1.print(output);
			  //Close the input stream
			  out1.close();
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
			    
			   // sentences.printMap(hm);
			    
			   // ThisSentence Sentence = new ThisSentence();
			  //  Sentence.normalizeSentence("Subsequently the RNA synthesis in this region was analysed by RT PCR in cells growing exponentially in minimal medium .");
    

	}

}
