package datacollector;
import com.csvreader.CsvWriter;
import data.MI;
import data.SentenceData;
import generalutils.ChiSquare;
import generalutils.MutualInformation;
import generalutils.TextToVector;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MIWriterMore
{
	public static String preprocess(String sent){
		sent = sent.replaceAll("\\b[B-Zb-z]\\b", "");
		sent = sent.replaceAll("finding", "find");
		sent = sent.replaceAll("findings", "find");

		sent = sent.replaceAll("found", "find");
		sent = sent.replaceAll("methods", "method");
		//sent = sent.replaceAll("results", "result");
		sent = sent.replaceAll("methods", "method");
		sent = sent.replaceAll("fig", "figure");
 
        sent = sent.replaceAll("results", "result");
        
		//sent = sent.replaceAll("\\b[\\w']{1,2}\\b", "");
		//sent = sent.replaceAll("\\s{2,}", " ");		
		return sent;
	}
  public static String fullText = "";
  public static String sentModel = "";
  public static int[] NGRAMS = {3};
  public static String[] verbClass1 = "give involve provide contain carry yield stretch represent reflect play reach detail allow exhibit combine display include".split(" ");
  public static String[] verbClass2 = "fit extend shift limit compare reduce assign relate set apply bind couple add attribute".split(" ");
  public static String[] verbClass3 = "fix associate excite depend charge label mix base adsorb select".split(" ");
  public static String[] verbClass4 = "vary bond quench result exist change arise occur increase start differ consist decrease state point".split(" ");
  public static String[] verbClass5 = "calculate employ study perform identify achieve derive monitor introduce discuss detect develop determine prepare describe examine estimate obtain remove investigate evaluate locate measure treat compute record".split(" ");
  public static String[] verbClass6 = "illustrate explain suggest predict indicate require show reveal confirm follow demonstrate".split(" ");
  public static String[] verbClass7 = "affect enhance improve characterize support form produce isolate separate induce modify generate control cause define".split(" ");
  public static String[] verbClass8 = "interest make take list remain lie use choose need present become".split(" ");
  public static String[] verbClass9 = "report assume note observe expect consider propose find see conclude".split(" ");
  public static String[] verbClass10 = "seem accord appear correspond lead contribute".split(" ");

  public static ArrayList<String[]> vebClass = null;
  
  public static String sentProcess(String sent){
	  ArrayList<String[]> arr = returnVerbClass();
	  for (String[]verbC : arr){
		  sent = checkVerb(verbC, sent);
	  }
	 // sent = sent.replaceAll("\\b[\\w']{1,2}\\b", "");
	 // sent = sent.replaceAll("\\s{2,}", " ");		
	  return sent;
  }
  
  public static ArrayList<String[]> returnVerbClass(){
	  vebClass = new ArrayList<String[]>();
	  vebClass.add(verbClass1);	  
	  vebClass.add(verbClass2);
	  vebClass.add(verbClass3);
	  vebClass.add(verbClass4);
	  vebClass.add(verbClass5);
	  vebClass.add(verbClass6);
	  vebClass.add(verbClass7);
	  vebClass.add(verbClass8);
	  vebClass.add(verbClass9);
	  vebClass.add(verbClass10);

	  
	  return vebClass;
	  
  }
  
  public static String checkVerb(String[] verbClass, String sent){
	  for(int i = 0; i < verbClass.length; i++){
		  if(Pattern.compile(verbClass[i].trim()).matcher(sent).find()){
		  //sentModel = sent;
		  sent = sent.replaceAll(verbClass[i], verbClass[0]);
		 // System.out.println(verbClass[0]);	 
		  }
	  }
	  	  return sent;
  }
  
  
  
  public static void writeMI(String fileToWrite, List<SentenceData> sentences) {
    try {
      System.out.println("Im");
     // String fullText = "";
      Map<String, Integer>  WordFrequency = null;
      TextToVector ttv = new TextToVector(false);
      ttv.setNGrams(NGRAMS);
      for (SentenceData sd : sentences) {
         ttv.addText(preprocess(sd.getNormalizedSentence()));
      	 // String SentF = preprocess(sd.getNormalizedSentence());
      	//	  sentProcess(SentF);
      	  //fullText +=SentF + " ";
           // System.out.println(fullText);

      	 // ttv.addText(sentProcess(SentF));
       }
      //System.out.println("This is the word list :" + fullText);
      WordFrequency = new HashMap<String, Integer> ();
     // System.out.println("This is the word list :" + fullText);
      //WordFrequency = ttv.getTextFrequencyMap(fullText);
      //System.out.println(WordFrequency.get("method"));
     
     // System.out.println("This is the word list :" + fullText);
      ttv.generateWordList();

      List wordList = ttv.getWordList();
      
      String word = "";
      String sentenceClass = "";
      int size = wordList.size();
      List<MI> mis = new ArrayList<MI>(size);

      int counter = 0;

      List<Map<String,Integer>> mapList = new ArrayList<Map<String,Integer>>();

      for (SentenceData sd : sentences) {
        mapList.add(ttv.getTextFrequencyMap(sd.getNormalizedSentence()));
      }

      for (int i = 0; i < size; i++) {
        double c1p = 0.0D;
        double c1n = 0.0D;
        double c2p = 0.0D;
        double c2n = 0.0D;
        double c3p = 0.0D;
        double c3n = 0.0D;
        double c4p = 0.0D;
        double c4n = 0.0D;
        double c5p = 0.0D;
        double c5n = 0.0D;
        double c6p = 0.0D;
        double c6n = 0.0D;
       // double c7p = 0.0D;
        //double c7n = 0.0D;
        word = (String)wordList.get(i).toString();
        counter = 0;
     //if (word.length()!=0 && WordFrequency.get(word) > 20)//{
        for (Map map : mapList) {
          sentenceClass = ((SentenceData)sentences.get(counter)).getAssignedCategory();
          if (sentenceClass.equalsIgnoreCase("experiment")) {
            if (map.containsKey(word))
              c1p += 1.0D;
            else
              c1n += 1.0D;
          }
          else if (sentenceClass.equalsIgnoreCase("method")) {
            if (map.containsKey(word))
              c2p += 1.0D;
            else
              c2n += 1.0D;
          }
          else if (sentenceClass.equalsIgnoreCase("result")) {
            if (map.containsKey(word))
              c3p += 1.0D;
            else
              c3n += 1.0D;
          }
          else if (sentenceClass.equalsIgnoreCase("finding")) {
            if (map.containsKey(word))
              c4p += 1.0D;
            else {
              c4n += 1.0D;
            }
          }
          else if (sentenceClass.equalsIgnoreCase("hypothesis")) {
              if (map.containsKey(word))
                c5p += 1.0D;
              else {
                c5n += 1.0D;
              }
            }
          else if (sentenceClass.equalsIgnoreCase("analysis")) {
              if (map.containsKey(word))
                c6p += 1.0D;
              else {
                c6n += 1.0D;
              }
            }
          /*else if (sentenceClass.equalsIgnoreCase("observation")) {
              if (map.containsKey(word))
                c7p += 1.0D;
              else {
                c7n += 1.0D;
              }
            }*/
          counter++;	
        }
        //double[][] matrix = { { c1p, c1n }};
          double[][] matrix = { { c1p, c1n }, { c2p, c2n }, { c3p, c3n }, { c4p, c4n } , { c5p, c5n }, { c6p, c6n }};

        double miScore = ChiSquare.calculateChiSquare(matrix, false);
          //double miScore = MutualInformation.calculateMutualInformation(matrix, false);
        mis.add(new MI(word, miScore));
      
      }
      //}
      Collections.sort(mis);

      CsvWriter writer = new CsvWriter(fileToWrite);

      for (MI mi : mis) {
    	 // if(WordFrequency.get(mi.getTerm().trim()).intValue()> 0){
        String[] writeLine = new String[2];
        writeLine[0] = mi.getTerm();
        writeLine[1] = (mi.getMi() + "");
        writer.writeRecord(writeLine);
      }
    //  }
      writer.close();
    } catch (Exception ex) {
      System.err.println("Problem while writing IDF values: " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    //List<SentenceData> sdList = SentenceDataReader.readSentenceDate("C:/Users/Hospice/New_workspace/sentclassification/datawithverbs/CSVExperiment_lem.csv");
   // writeMI("C:/Users/Hospice/New_workspace/sentclassification/datawithverbs/datasetwithverbsExperiment_lem_uni_chi.txt", sdList);
	//List<SentenceData> sdList = SentenceDataReader.readSentenceDate("C:/Users/Hospice/New_workspace/sentclassification/annotated2316.csv");
	 //writeMI("C:/Users/Hospice/New_workspace/sentclassification/annotated2316_OneWordRemoved_chi.txt", sdList);
	 
	 
	 List<SentenceData> sdList = SentenceDataReader.readSentenceDate("C:/Users/Hospice/New_workspace/sentclassification/6classes/6class4verbs.csv");
	 writeMI("C:/Users/Hospice/New_workspace/sentclassification/6classes/FeatureScore/6class4verbs_3chi.txt", sdList);
  }
}