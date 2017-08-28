package datacollector;
import generalutils.FileOperations;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parse.SentenceTenseGetter;
import utils.Normalizer;
import data.SentenceData;

import org.junit.Test;

public class CVSWriterWithTenseTest {
   
	public  CVSWriterWithTenseTest() {
	   }

	@Test
	public void testCSVWithTense() throws Exception{
		String file = "C:/Users/Hospice/annotated_data/corpusout/ARTdata/ART_CorpusOut.csv";
		FileInputStream fstream = new FileInputStream("C:/Users/Hospice/annotated_data/corpusout/ARTdata/ART_Corpus.csv");
		DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		  String category;
		  //Read File Line By Line
	      ArrayList<String> writeLines = new ArrayList<String>();
          //(VBD, VBN, VBG, VBZ, VBP and VB) 
		    //Normalizer n = new Normalizer(true, true, false, false, false, true, true);

		  try {
			while ((strLine = br.readLine()) != null )   {
			 // for (SentenceData sd : sdList) {
				//strLine= normText(strLine);
				//strLine = sd.getOriginalSentence();
			    Normalizer n = new Normalizer(true, true, false, false, false, true, true);
				//category = sd.getAssignedCategory();
				StringBuilder sb = new StringBuilder();
				 // System.out.println(strLine);
				String[] strLineSplit = strLine.split(","); 
				String strLine1 = strLineSplit[0];
                String sentNorm = n.normalize(strLine1);
                if (strLineSplit.length > 2){
				sb.append(sentNorm).append(",");

				sb.append(strLine.split(",")[1]);			
				sb.append(",");
				sb.append(strLineSplit[2]);			
				sb.append(",");
				sb.append(strLine.split(",")[3]);
				System.out.println(sb.toString());
				writeLines.add(sb.toString());
                }
			  }
			 // }
			 FileOperations.writeFile(file, writeLines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			  System.err.println("Error: " + e.getMessage());
		}
	} 
}
