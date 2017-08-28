package datacollector;

import com.csvreader.CsvReader;
import data.SentenceData;
import generalutils.FileOperations;
import generalutils.TextToVector;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ARFFWriter
{
  public static int[] NGRAMS = { 1, 2 };

  public void writeARFF(String file, List<SentenceData> sentenceData, String mutualInformationFile, int numFeaturesToUse, boolean useOriginalCategory, boolean useTenses)
  {
   // writeARFF(file, sentenceData, mutualInformationFile, numFeaturesToUse, useOriginalCategory, useTenses, "experiment,method,result,conclusion,background,motivation,hypothesis,observation,object,goal,model");
	  writeARFF(file, sentenceData, mutualInformationFile, numFeaturesToUse, useOriginalCategory, useTenses, "method,other");
  }

  public void writeARFFSimple(String file, List<SentenceData> listOfSentenceData, String mutualInformationFile, int numFeaturesToUse, String categories, int[] nGrams)
  {
    NGRAMS = nGrams;
    writeARFF(file, listOfSentenceData, mutualInformationFile, numFeaturesToUse, false, false, categories);
  }

  public void writeARFF(String file, List<SentenceData> sentenceData, String mutualInformationFile, int numFeaturesToUse, boolean useOriginalCategory, boolean useTenses, String categories)
  {
    try
    {
      List<String> features = getTopMITerms(mutualInformationFile, numFeaturesToUse);
      ArrayList<String> writeLines = new ArrayList();

      TextToVector ttv = new TextToVector(false);
      ttv.setNGrams(NGRAMS);

      writeLines.add("@relation 'Generic Classifier'");
      for (String feature : features) {
        writeLines.add(new StringBuilder().append("@attribute '").append(feature).append("' numeric").toString());
      }
      if (useOriginalCategory)
      {
        writeLines.add("@attribute 'original_category_0' numeric");
        writeLines.add("@attribute 'original_category_1' numeric");
        writeLines.add("@attribute 'original_category_2' numeric");
        writeLines.add("@attribute 'original_category_3' numeric");
      }
      if (useTenses) {
        writeLines.add("@attribute 'present_tense' numeric");
        writeLines.add("@attribute 'past_tense' numeric");
        
       /* writeLines.add("@attribute 'HasVBZ' numeric");
        writeLines.add("@attribute 'HasVBP' numeric");
        writeLines.add("@attribute 'HasVBD' numeric");
        writeLines.add("@attribute 'HasVBG' numeric");*/
       // writeLines.add("@attribute 'HasVBP' numeric");
       // writeLines.add("@attribute 'HasVB' numeric");
      }

      writeLines.add(new StringBuilder().append("@attribute category {").append(categories).append("}").toString());
      writeLines.add("@data");

      for (SentenceData sd : sentenceData) {
        Map wordFrequencyMap = ttv.getTextFrequencyMap(sd.getNormalizedSentence());
        String writeString = "";
        for (String feature : features) {
          if (wordFrequencyMap.containsKey(feature))
            writeString = new StringBuilder().append(writeString).append("1,").toString();
          else {
            writeString = new StringBuilder().append(writeString).append("0,").toString();
          }
        }
        if (useOriginalCategory)
        {
          int originalCat = sd.getOriginalCategory();
          for (int i = 0; i < 4; i++) {
            if (originalCat == i)
              writeString = new StringBuilder().append(writeString).append("1,").toString();
            else {
              writeString = new StringBuilder().append(writeString).append("0,").toString();
            }
          }
        }
        /*if (useTenses) {
      //////(VBD, VBN, VBG, VBZ, VBP and VB) 
         // writeString = new StringBuilder().append(writeString).append(sd.isHasPresentVerb() ? "1" : "0").append(",").toString();
          //writeString = new StringBuilder().append(writeString).append(sd.isHasPastVerb() ? "1" : "0").append(",").toString();
        	 writeString = new StringBuilder().append(writeString).append(sd.isHasVBD() ? "1" : "0").append(",").toString();
        	 writeString = new StringBuilder().append(writeString).append(sd.isHasVBN() ? "1" : "0").append(",").toString();
        	 
        	 writeString = new StringBuilder().append(writeString).append(sd.isHasVBG() ? "1" : "0").append(",").toString();
        	 writeString = new StringBuilder().append(writeString).append(sd.isHasVBZ() ? "1" : "0").append(",").toString();
        	 
        	// writeString = new StringBuilder().append(writeString).append(sd.isHasVBP() ? "1" : "0").append(",").toString();
        	// writeString = new StringBuilder().append(writeString).append(sd.isHasVB() ? "1" : "0").append(",").toString();
        	 
        }*/
        if (useTenses) {
        //////(VBD, VBN, VBG, VBZ, VBP and VB) 
            writeString = new StringBuilder().append(writeString).append(sd.isHasPresentVerb() ? "1" : "0").append(",").toString();
            writeString = new StringBuilder().append(writeString).append(sd.isHasPastVerb() ? "1" : "0").append(",").toString();
          	/* 
        	writeString = new StringBuilder().append(writeString).append(sd.isHasVBD() ? "1" : "0").append(",").toString();
          	 writeString = new StringBuilder().append(writeString).append(sd.isHasVBN() ? "1" : "0").append(",").toString();
          	 
          	 writeString = new StringBuilder().append(writeString).append(sd.isHasVBG() ? "1" : "0").append(",").toString();
          	 writeString = new StringBuilder().append(writeString).append(sd.isHasVBZ() ? "1" : "0").append(",").toString();
          	 */
          	// writeString = new StringBuilder().append(writeString).append(sd.isHasVBP() ? "1" : "0").append(",").toString();
          	// writeString = new StringBuilder().append(writeString).append(sd.isHasVB() ? "1" : "0").append(",").toString();
          	 
          }
        writeString = new StringBuilder().append(writeString).append(sd.getAssignedCategory()).toString();
       // System.out.println(writeString);
        writeLines.add(writeString);	
      }

      FileOperations.writeFile(file, writeLines);
    } catch (Exception ex) {
      System.err.println("Problem while writing arff file.");
      ex.printStackTrace();
    }
  }	

  private List<String> getTopMITerms(String mutualInformationFile, int numTerms) {
    List<String> topFeatures = new ArrayList<String>(numTerms);
    try {
      CsvReader reader = new CsvReader(mutualInformationFile);
      for (int i = 0; (i < numTerms) && (reader.readRecord()); i++)
        topFeatures.add(reader.get(0));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return topFeatures;
  }

  
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
	public static String fullText = "";
public static String sentModel = "";
//public static int[] NGRAMS = { 1 };
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

  public static void main(String[] args) {
   // List<SentenceData> sdList = SentenceDataReader.readSentenceDate("C:/Users/Hospice/backup/USBFiles/annotated_data/400annotatedReduced.txt");
    List<SentenceData> sdList2 = SentenceDataReader.readSentenceDate("C:/Users/Hospice/combinesentences/annotationSentencesPositive.csv");

    ARFFWriter writer = new ARFFWriter();
   // writer.writeARFF("C:/Users/Hospice/backup/USBFiles/annotated_data/ARFF/300annotatedReducedwithMRCMI_15000_withStop2Test.arff", sdList, "C:/Users/Hospice/backup/USBFiles/annotated_data/MIFeatures/annotate_MRCand400reduced_mi1_2_3grams.txt", 15000, false, false);
    writer.writeARFF("C:/Users/Hospice/combinesentences/annotationSentencesPositive.arff", sdList2, "C:/Users/Hospice/annotated_data/corpusout/MRCdata/MRCSelf/Alldata98conf2500andtensetwoclassesbi_Chi.txt", 2500, false, true);

    // writer.writeARFF("C:/Users/Hospice/New_workspace/sentclassification/6classes/annotated3300_tri_chi7000.arff", sdList, "C:/Users/Hospice/New_workspace/sentclassification/6classes/dataset3800All6classes_chi.txt", 7000, false, false);
  }
}