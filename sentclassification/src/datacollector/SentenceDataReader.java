package datacollector;

import com.csvreader.CsvReader;

import data.SentenceData;
//import fulltextclassifier.data.SentenceData;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class SentenceDataReader
{
	public static String preprocess(String sent){
		sent =sent.replaceAll("\\b[B-Zb-z]\\b", "");
		/*sent = sent.replaceAll("finding", "find");
		sent = sent.replaceAll("findings", "find");

		sent = sent.replaceAll("found", "find");
		sent = sent.replaceAll("methods", "method");
		sent = sent.replaceAll("", "result");
		sent = sent.replaceAll("methods", "method");
		sent = sent.replaceAll("fig", "figure");
 
        sent = sent.replaceAll("results", "result");
        */
		//sent = sent.replaceAll("\\b[\\w']{1,2}\\b", "");
		//sent = sent.replaceAll("\\s{2,}", " ");		
		return sent;
	}

	public static SentenceData readSentenceData(String sentence){
		String[] sentCSV = sentence.split(",");
		
		SentenceData sd = new SentenceData(sentCSV[0]);
		sd.setNormalizedSentence(sentCSV[0]);
        //sd.setOriginalCategory(Integer.parseInt("1"));
        sd.setHasPresentVerb(Boolean.parseBoolean(sentCSV[1]));
        sd.setHasPastVerb(Boolean.parseBoolean(sentCSV[2]));
        sd.setAssignedCategory(sentCSV[3]);
		return sd;
		
		
	}
  public static List<SentenceData> readSentenceDate(String sentenceDataFile)
  {
    List sdList = new ArrayList();
    int counter = 0;
    try {
      CsvReader reader = new CsvReader(sentenceDataFile);

      while (reader.readRecord()) {
        counter++;
        SentenceData sd = new SentenceData(reader.get(0));
        sd.setNormalizedSentence(reader.get(0));
        //sd.setOriginalCategory(Integer.parseInt("1"));
        sd.setHasPresentVerb(Boolean.parseBoolean(reader.get(1)));
        sd.setHasPastVerb(Boolean.parseBoolean(reader.get(2)));
        sd.setAssignedCategory(reader.get(3));
        //System.out.println(reader.get(1));
      //  sd.setAssignedCategory(reader.get(1));
        
        //////(VBD, VBN, VBG, VBZ, VBP and VB) 
       // sd.setHasVBD(Boolean.parseBoolean(reader.get(1)));
       // sd.setHasVBN(Boolean.parseBoolean(reader.get(2)));
       // sd.setHasVBG(Boolean.parseBoolean(reader.get(3)));
      //  sd.setHasVBZ(Boolean.parseBoolean(reader.get(4)));
        /* sd.setHasVBP(Boolean.parseBoolean(reader.get(5)));
        sd.setHasVB(Boolean.parseBoolean(reader.get(6)));
        */


        sdList.add(sd);
      }
      reader.close();
    } catch (Exception ex) {
      System.err.println("Problem at line: " + counter);
      ex.printStackTrace();
      System.exit(1);
    }
    return sdList;
  }
}