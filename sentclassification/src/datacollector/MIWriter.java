package datacollector;
import com.csvreader.CsvWriter;
import data.MI;
import data.SentenceData;
import generalutils.ChiSquare;
import generalutils.MutualInformation;
import generalutils.TextToVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class MIWriter
{
	  public static Set<String> stopwords;
	  private static Set<String> dictwords;

	  public  MIWriter(){
		  MIWriter.stopwords = new HashSet<String>();
		  MIWriter.dictwords = new HashSet<String>();

	  }
	public static String preprocess(String sent){
		
		return sent;
	}
  public static String fullText = "";
  public static String sentModel = "";
  public static int[] NGRAMS = { 1, 2 };
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
  
  public static void loadStopwords()
  {
    File file = null;
    BufferedReader inputStream = null;
    try {
      file = new File("C:/Users/Hospice/backup/manyaspects_alphawks/manyaspects_alphawks/config/stopword.list");
      inputStream = new BufferedReader(new FileReader(file));
      String line;
      while ((line = inputStream.readLine()) != null) {
    	  //System.out.println(line);
        if ((line.length() == 0) || (line.trim().length() == 0))
          continue;
        MIWriter.stopwords.add(line.trim());
        
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
  }
  private void loadDictwords()
  {
    File file = null;
    BufferedReader inputStream = null;
    try {
      file = new File("C:/Users/Hospice/ART_Corpus_Out/words.txt");
      inputStream = new BufferedReader(new FileReader(file));
      String line;
      while ((line = inputStream.readLine()) != null) {
        if ((line.length() == 0) || (line.trim().length() == 0))
          continue;
        MIWriter.dictwords.add(line.trim());
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
  }
  
  public static void writeMI(String fileToWrite, List<SentenceData> sentences) {
    try {
      System.out.println("Im");
      Map<String, Integer>  WordFrequency = null;
      TextToVector ttv = new TextToVector(false);
      ttv.setNGrams(NGRAMS);
      for (SentenceData sd : sentences) {
      
          ttv.addText(sd.getNormalizedSentence());

      }
      //System.out.println("This is the word list :" + fullText);
      WordFrequency = new HashMap<String, Integer> ();
    
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
     // System.out.println(dictwords); 
      for (int i = 0; i < size; i++) {
        double c1p = 1.0D;
        double c1n = 1.0D;
        double c2p = 1.0D;
        double c2n = 1.0D;
        double c3p = 1.0D;
        double c3n = 1.0D;
        double c4p = 1.0D;
        double c4n = 1.0D;
        word = (String)wordList.get(i).toString();
        counter = 0;
        word = word.trim();
    
        for (Map map : mapList) {
          sentenceClass = ((SentenceData)sentences.get(counter)).getAssignedCategory();
          if (sentenceClass.equalsIgnoreCase("method")) {
            if (map.containsKey(word))
              c1p += 1.0D;
            else
              c1n += 1.0D;
          }
    
          else if (sentenceClass.equalsIgnoreCase("other")) {
            if (map.containsKey(word))
              c4p += 1.0D;
            else {
              c4n += 1.0D;
            }
          }
          counter++;	
        }
    
          double[][] matrix = { { c1p, c1n }, { c4p, c4n } };


        double miScore = ChiSquare.calculateChiSquare(matrix, false);
       
        mis.add(new MI(word, miScore));
      
      }
     // }
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
  public static Set<String> getStopWords()
  {
    return MIWriter.stopwords;
  }
  public Set<String> getDictWords()
  {
    return MIWriter.dictwords;
  }
  public static void main(String[] args) {
 
	 MIWriter mi = new MIWriter();

	 List<SentenceData> sdList = SentenceDataReader.readSentenceDate("C:/Users/Hospice/annotated_data/corpusout/MRCdata/MRCSelf/Alldata98conf2500andtensetwoclasses.csv");
	 writeMI("C:/Users/Hospice/annotated_data/corpusout/MRCdata/MRCSelf/Alldata98conf2500andtensetwoclassesbi_Chi.txt", sdList);
  }  
}