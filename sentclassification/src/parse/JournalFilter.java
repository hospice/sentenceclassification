package parse;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;

import utils.TermTokenizer;

public class JournalFilter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashSet<String> hs = new HashSet<String>();
		try{
			File f = new File("C:/sharename/thisCorpus/outputFile.txt");
			  TermTokenizer tk = new TermTokenizer();
			  f.createNewFile();
			  PrintWriter out1 = new PrintWriter(new FileWriter(f));
			  // Open the file that is the first 
			  // command line parameter
			  String output = "";
			 FileInputStream fstream = new FileInputStream("C:/sharename/thisCorpus/ThisResultOutput.txt");
			  // Get the object of DataInputStream
			  
			  //FileInputStream fstream = new FileInputStream("./AllNgram.txt");

			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null )   {
				//  System.out.println(strLine);
				  if (strLine.startsWith("--")||strLine.endsWith("Line does not have a line to pair with; either it is Line 1 or it is a line in a group of 3 or more lines"))
				  {}
				 // if (strLine.isEmpty())
				  //{}

				  
				  else{
					  
			  // Print the content on the console
					  String[] sentencesT = strLine.split("\t");
					  
					  hs.add(sentencesT[0].trim()+"\n");
				  }
			  }
			  System.out.println(hs);
			 
			  out1.print(output);
			  //Close the input stream
			  out1.close();
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }

	}

}
