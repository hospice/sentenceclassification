package datacollector;

import generalutils.FileOperations;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parse.SentenceTenseGetter;
import utils.Normalizer;
import data.SentenceData;

import org.junit.Test;


public class CSVWriterWithTenseAgain 
 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	   // List<SentenceData> sdList = SentenceDataReader.readSentenceDate("C:/Users/Hospice/New_workspace/sentclassification/datawithverbs/CSVThisResult_output.csv");

		// TODO Auto-generated method stub
		//String file = "C:/Users/Hospice/annotated_data/corpusout/MRCdata/annotate_MRCWithTense.csv";
		//FileInputStream fstream = new FileInputStream("C:/Users/Hospice/annotated_data/corpusout/MRCdata/annotate_MRC.csv");
		String file = "C:/Users/Hospice/annotated_data/corpusout/ARTdata/ART_ResultOut.csv";
		FileInputStream fstream = new FileInputStream("C:/Users/Hospice/annotated_data/corpusout/ARTdata/ART_Result.csv");
	    SentenceTenseGetter parser = new SentenceTenseGetter("C:/Users/Hospice/backup/imrad_classifier/data/englishPCFG.ser.gz");

		DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		  String category;
		  //Read File Line By Line
	      ArrayList<String> writeLines = new ArrayList<String>();
          //(VBD, VBN, VBG, VBZ, VBP and VB) 
		  try {
			while ((strLine = br.readLine()) != null )   {
			 // for (SentenceData sd : sdList) {
				//strLine= normText(strLine);
				//strLine = sd.getOriginalSentence();
				  
				//category = sd.getAssignedCategory();
				StringBuilder sb = new StringBuilder();
				 // System.out.println(strLine);
				String strLine1 = strLine.split(",")[0];
				sb.append(strLine1).append(",");
				//writeLines.add(new StringBuilder().append(strLine).append(",").append("result").toString());
			    Normalizer n = new Normalizer(true, true, false, false, false, true, true);

			   
			   String hasverb =  parser.parse(strLine1)[0]+","+parser.parse(strLine1)[1];
			   // System.out.println(sentence+", "+parser.parse(sentence)[0]+","+parser.parse(sentence)[1]);

				sb.append(hasverb);			
				sb.append(",");
				sb.append(strLine.split(",")[1]);
				System.out.println(sb.toString());
				writeLines.add(sb.toString());
			  }
			 // }
			 FileOperations.writeFile(file, writeLines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			  System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static String normText(String text){
		/*text=  text.replaceAll("of[\\s+]around", "UPTO");
		text=  text.replaceAll("of[\\s+]up[\\s+]to", "UPTO");

		text=  text.replaceAll("up[\\s+]to", "UPTO");
		text=  text.replaceAll("no[\\s+]longer[\\s+]than", "UPTO");
		text=  text.replaceAll("of[\\s+]about", "UPTO");
		text=  text.replaceAll("ranging[\\s+]up[\\s+]to", "UPTO");
		*/
		text=  text.replaceAll("\\d+-* ", "");
		text=  text.replaceAll("\\d+-*", "");

		text=  text.replaceAll(", ", "");
		text=  text.replaceAll("", "");

		text=  text.replaceAll("\\d+[\\.*\\d+]\\d ", "NNUMBER ");
		text=  text.replaceAll("% ", "PPERCENT ");
		text=  text.replaceAll("'", "");
		text=  text.replaceAll("[\\[:()/\\]]", "");
		text=  text.replaceAll("[:\"+]", "");
		text=  text.replaceAll("[:\"]", "");
		text=  text.replaceAll("[^A-Za-z ]", " ");




		  return text;
	  }
	public boolean tenseCheck(String text){
		//Pattern regexp = Pattern.compile("^\\\\N\\t(\\d)+\\t(\\w)+");
		//Matcher matcher = regexp.matcher("");
		String match1 = "";
		String match2 = "";
		String match3 = "";
		String match4 = "";
		String match5 = "";
		Pattern regexp = Pattern.compile(match1);
		Matcher matcher = regexp.matcher("");
		matcher.reset( text );
		if (matcher.find()){
			
			return true;
		}
		else{
			return false;
		}
		
		
	}

}
