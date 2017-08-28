package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.Comparator;









import org.junit.Test;

import stemmer.PaiceStemmer;
import stemmer.PorterStemmer;

import com.aliasi.util.Files;

import datacollector.MIWriter;

public class TermTokenizerFrequency
  implements Serializable
{
	
	private File f = new File("./AllNgramSort1.txt");
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static int[] DEFAULT_NGRAMS = { 2};
  private int[] nGrams;

  public TermTokenizerFrequency(int[] nGrams)
  {
    this.nGrams = nGrams;
  }

  public TermTokenizerFrequency() {
    this.nGrams = DEFAULT_NGRAMS;
  }

  public HashMap<String, Double> getTextFrequencyMap(String text)
  {
    List<String> textWords = getWords(text);
   // textWords = stemArrayWords(textWords);
    double[] sim1 = new double[textWords.size()];
    HashMap<String, Double> wordFrequency = new HashMap<String, Double>();
    //for (String word : textWords) {
    for (int i = 0; i< sim1.length; i++){
      if (wordFrequency.containsKey(textWords.get(i)))
        wordFrequency.put(textWords.get(i).toLowerCase().trim(), wordFrequency.get(textWords.get(i).toLowerCase().trim()) +1.0/1.0);
      else {
        wordFrequency.put(textWords.get(i).toLowerCase().trim(),   1.0/1.0);
      //}
      }
    }
    return wordFrequency;
  }

  public Set<String> getTextWordSet(String text) {
    List<String> textWords = getWords(text);
    Set<String> wordSet = new HashSet<String>();
    for (String word : textWords) {
      wordSet.add(word);
    }
    return wordSet;
  }

  public List<String> getWords(String text)
  {
    String[] tokens = text.split(" +");
    List<String> wordsList = new ArrayList<String>(tokens.length);

    for (int nGram : this.nGrams) {
      for (int i = 0; i < tokens.length + 1 - nGram; i++) {
        String wordToAdd = "";
        for (int j = 0; j < nGram; j++) {
          wordToAdd = wordToAdd + tokens[(i + j)] + " ";
        }
       //if(!MIWriter.stopwords.contains(wordToAdd.trim()))
        wordsList.add(wordToAdd.trim());
      }
    }
    return wordsList;
  }
  public ArrayList<String> stemArrayWords(List<String> textWords){
		ArrayList<String> arr = new ArrayList<String>();
	   // PorterStemmer s = new PorterStemmer();
		PaiceStemmer s = new PaiceStemmer("C:/Users/Hospice/backup/manyaspects_alphawks/manyaspects_alphawks/config/stemrules.txt", "/p");
	    //System.out.println(s.stripAffixes("implementing"));
		for (String word: textWords){
			 arr.add(s.stripAffixes(word.trim()));
			
		}
		
		return arr;		
		
	}
 // @Test
  public static void main(String[] args) throws IOException
  {   
	  //File f = new File("./AllNgramSort.txt");
	  TermTokenizerFrequency tk = new TermTokenizerFrequency();
	 // tk.f.createNewFile();
	  //PrintWriter out1 = new PrintWriter(new FileWriter(f));
	  List<String> getWord = new ArrayList<String>();
	  Set<String> getTextWordSet = new HashSet<String>();
	  Map<String, Double> getTextFrequencyMap1 = new HashMap<String, Double>();
	  
	  Map<String, Double> getTextFrequencyMap = new HashMap<String, Double>();

	  File file = new File("./ThisExperiment_output.txt");
	  File file2 = new File("./ThisFinding_output.txt");
	  File file3 = new File("./ThisMethod_output.txt");
	  File file4 = new File("./ThisResult_output.txt");

		//File file = new File("./articlesdatapartout3/1471-2105-9-535.txt");
		String text = Files.readFromFile(file,"ISO-8859-1");
		String text2 = Files.readFromFile(file2,"ISO-8859-1");
		String text3 = Files.readFromFile(file3,"ISO-8859-1");
		String text4 = Files.readFromFile(file4,"ISO-8859-1");
		ValueComparator bvc =  new ValueComparator(getTextFrequencyMap1);
		SortedMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
		MapValueSort mv = new MapValueSort();
		
		HashMap<String, Double> sorted = new HashMap<String, Double>();
	  getWord = tk.getWords(text);
	  getTextFrequencyMap = tk.getTextFrequencyMap(text+ " " +text2+" "+text3+" "+text4);
	  //sorted = bvc.sortHashMap((HashMap<String, Double>) getTextFrequencyMap);
		//sorted = mv.sortHashMap((HashMap<String, Double>)getTextFrequencyMap);

	  //sorted_map.putAll(getTextFrequencyMap);
		//SortedMap sortedData = new TreeMap(new MapValueSort.ValueComparer(getTextFrequencyMap));
		//sortedData.putAll(getTextFrequencyMap);

		
		
		printMap(getTextFrequencyMap);
/*
	 // sorted_map.putAll(getTextFrequencyMap);
	  //ValueComparator bvc =  new ValueComparator(getTextFrequencyMap);
	  
	  getTextWordSet = tk.getTextWordSet(text+ " " +text2+" "+text3+" "+text4);
	  Pattern p = Pattern.compile("sentences[-:]\\d+[-:]");
	  Pattern p2 = Pattern.compile("\\d+");
	  
	  Set set = getTextFrequencyMap.entrySet();
		//
		Iterator i = set.iterator();
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			
            //String key=(String)me.getKey();
            
            System.out.println("Key :"+(String)me.getKey() +  "  Value :"+(Double)me.getValue());
			
		}
		*/

	 /* int count = 0;
	  String output = "";
	  for(String s:getTextWordSet){
		  if (p.matcher(s).find()||s.equals("--")||s.trim().length()<3||p2.matcher(s).find())
			  continue;
		  //System.out.println(s);
		  output+= s+"\n";
		  count++;
	  }
	  out1.print(output);
	  out1.close();
	  System.out.println(count);*/

	  
  }
  
  public static void printMap(Map<String, Double> data) throws IOException {
	  TermTokenizerFrequency tk = new TermTokenizerFrequency();

	  tk.f.createNewFile();
	  String out = "";
	  PrintWriter out1 = new PrintWriter(new FileWriter("./AllNgramSort.txt"));
		for (Iterator<String> iter = data.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			//out+=key +"/"+data.get(key)+ "\n";
			System.out.println(key +"\t"+data.get(key));
		}
		out1.print(out);
		out1.close();
	}
  
}