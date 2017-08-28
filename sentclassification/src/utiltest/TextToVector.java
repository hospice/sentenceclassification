package utiltest;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class TextToVector
{
  public static final int[] DEFAULTNGRAMS = { 1 };
  private Set<String> words;
  private List<String> wordList;
  private List<String> texts;
  private List<List<Integer>> textVectors;
  private boolean useFrequency;
  private int[] nGrams;

  public TextToVector(boolean useFrequency)
  {
    this.words = new HashSet();
    this.wordList = new ArrayList();
    this.texts = new ArrayList();
    this.textVectors = new ArrayList();
    this.useFrequency = useFrequency;
    int[] ng = DEFAULTNGRAMS;
    this.nGrams = ng;
  }

  public int[] getNGrams()
  {
    return this.nGrams;
  }

  public void setNGrams(int[] nGrams)
  {
    this.nGrams = nGrams;
  }

  public void addText(String text)
  {
    this.texts.add(text);
  }

  public void convertToVectors()
  {
    int documentNumber = this.texts.size();
    Map documentWordFrequency = generateWordList();

    for (int i = 0; i < documentNumber; i++) {
      Map wordFrequency = (Map)documentWordFrequency.get(Integer.valueOf(i));
      this.textVectors.add(textVectorFromFrequency(wordFrequency));
    }
  }

  private List<Integer> textVectorFromFrequency(Map<String, Integer> wordFrequency)
  {
    List textVector = new ArrayList();
    for (String word : this.wordList) {
      if (wordFrequency.containsKey(word)) {
        if (this.useFrequency)
          textVector.add(wordFrequency.get(word));
        else
          textVector.add(Integer.valueOf(1));
      }
      else {
        textVector.add(Integer.valueOf(0));
      }
    }
    return textVector;
  }

  public List<Integer> getTextVector(String text)
  {
    return textVectorFromFrequency(getTextFrequencyMap(text));
  }

  public Map<String, Integer> getTextFrequencyMap(String text)
  {
    List<String> textWords = getWords(text);
    Map wordFrequency = new HashMap();
    for (String word : textWords) {
      if (wordFrequency.containsKey(word))
        wordFrequency.put(word, Integer.valueOf(((Integer)wordFrequency.get(word)).intValue() + 1));
      else {
        wordFrequency.put(word, Integer.valueOf(1));
      }
      this.words.add(word);
    }
    return wordFrequency;
  }

  public Map<Integer, Map<String, Integer>> generateWordList()
  {
    Map documentWordFrequency = new HashMap();
    int documentNumber = 0;

    for (String text : this.texts) {
      documentWordFrequency.put(Integer.valueOf(documentNumber), getTextFrequencyMap(text));
      documentNumber++;
    }
    this.wordList = new ArrayList(this.words);
    Collections.sort(this.wordList);
    return documentWordFrequency;
  }

  private List<String> getWords(String text)
  {
    String[] tokens = text.split(" +");
    List wordsList = new ArrayList(tokens.length);

    for (int nGram : this.nGrams) {
      for (int i = 0; i < tokens.length + 1 - nGram; i++) {
        String wordToAdd = "";
        for (int j = 0; j < nGram; j++) {
          wordToAdd = wordToAdd + tokens[(i + j)] + " ";
        }
        wordsList.add(wordToAdd.trim());
      }
    }
    return wordsList;
  }

  public List<List<Integer>> getTextVectors()
  {
    return this.textVectors;
  }

  public List<String> getWordList()
  {
    return this.wordList;
  }

  private static String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }
        String text = stringBuilder.toString();
        	
        text=  text.replaceAll("\\d+-* ", "");
		text=  text.replaceAll("\\d+-*", "");

		text=  text.replaceAll(", ", "");

	//	text=  text.replaceAll("\\d+[\\.*\\d+]\\d ", "NNUMBER ");
	//	text=  text.replaceAll("% ", "PPERCENT ");
		text=  text.replaceAll("'", " ");
		text=  text.replaceAll("[\\[:()/\\]]", "");
		text=  text.replaceAll("[:\"+]", "");
		text=  text.replaceAll("[:\"]", "");
		text=  text.replaceAll("[^A-Za-z]", " ");
		text=  text.replaceAll(" +[A-Za-z +] ", "");

	   // return stringBuilder.toString();
		 return text;
	}
  
  
  public static void main(String[] args) throws IOException {
	  
	//HashMap<String,Double> map = new HashMap<String,Double>();
	File expFile = new File("C:/Users/Hospice/ThisCorpusOut/experiment3G.txt");
	FileWriter expFileWriter = new FileWriter(expFile);
	PrintWriter outExp = new PrintWriter(new BufferedWriter(expFileWriter), true);

	String fileContent = readFile("C:/Users/Hospice/New_workspace/sentclassification/ThisExperiment_output.txt");
   
    TextToVector i = new TextToVector(true);
    int[] ng = {3};
    i.setNGrams(ng);
    i.addText(fileContent);
    i.generateWordList();

    for (String word : i.getWordList()) {
    //  System.out.print(word + "|");
    }
    System.out.println();
    for (Iterator i$ = i.getTextVector("bob is 10 years old bob is benny best friend").iterator(); i$.hasNext(); ) { int val = ((Integer)i$.next()).intValue();
     // System.out.print(val + "|");
    }
    System.out.println();

    Map<String, Integer> map =    i.getTextFrequencyMap(fileContent);
    ValueComparator bvc =  new ValueComparator(map);
    TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
    
   // System.out.println("unsorted map: "+map);
   
    sorted_map.putAll(map);
    Iterator $k;
    for (Entry<String, Integer> key:sorted_map.entrySet()){
    	System.out.println(key.toString() +"\t" );
    	outExp.write(key.toString().replace("=", "\t")+"\n");
    	
    }
 //   System.out.println("results: "+sorted_map);
    
    Iterator $i;
    for (List vector : ((TextToVector) i).getTextVectors()) {
      System.out.println();
      for ($i = vector.iterator(); $i.hasNext(); ) { int val = ((Integer)$i.next()).intValue();
        System.out.print(val + "|");
      }
    }
    outExp.close();
  }
}