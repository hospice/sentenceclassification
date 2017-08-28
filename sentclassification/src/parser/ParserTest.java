package parser;

import com.aliasi.util.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sentences.SentenceBoundary;
//import manyaspects.Parser.PaiceStemmer;
//import manyaspects.umlsfiles.Umls;
import sentences.SentenceDetection;


public class ParserTest
{
  private String document;
  private String document2;
  private List<String> sentences;
  private int numSentences;
  private List<Map<String, Double>> sparseSentences;
  private int numUselessSentences;
  private Set<String> stopwords;
  private int numTotalWords;
  private Map<String, Double> dictionary;
  private int numUniqueWords;
  private PaiceStemmerTest paice_stemmer;
  private PorterStemmerTest porter_stemmer;
  private double freqThreshold;
  //private Umls umls;

  public ParserTest(String text) throws Exception
  {
    initComponents(this.freqThreshold); 

    loadText(text);
    detectSentences();
    loadStopwords();
    loadStemmer();
    parseSentenceUmls();
  }
	public ParserTest(String fileName, double freqThreshold) throws Exception
  {
    initComponents(freqThreshold);

    readFile(fileName);
    //detectSentences();
    loadStopwords();
    loadStemmer();
    parseSentence();
   // parseSentenceUmls();
  }

  private void initComponents(double freqThreshold) {
    this.sentences = new ArrayList();
    this.sparseSentences = new ArrayList();
    this.stopwords = new HashSet();
    this.dictionary = new HashMap();
    this.freqThreshold = freqThreshold;
	//this.umls = new Umls();
  }

  private void readFile(String fileName)
  {
    BufferedReader inputStream = null;
    this.numSentences = 0;
    try
    {
      inputStream = new BufferedReader(new FileReader(fileName));
      String l;
      while ((l = inputStream.readLine()) != null) {
        if (l.length() == 0)
          continue;
        this.sentences.add(l.trim());
        this.numSentences += 1;
      }
    }
    catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (inputStream != null)
          inputStream.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    if (this.numSentences < 1) {
      System.err.println("Empty file. System Exiting...");
      System.exit(1);
    }
  }

  private void loadFile(String fileName)
  {
    try
    {
      File file = new File(fileName);
      this.document = Files.readFromFile(file, "ISO-8859-1");
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }
  
  private void readSentence(String sentence)
  {
    
      //File file = new File(fileName);
      this.document = sentence;
    
    
  }
  
  private void loadText(String text)
  {
    
      this.document = text;
    
  }

  private void detectSentences()
  {
    SentenceDetection sd = new SentenceDetection(this.document, true);

    this.numSentences = sd.getNumSentences();

    this.sentences = sd.getSentences();
  }

  private void loadStopwords()
  {
    File file = null;
    BufferedReader inputStream = null;
    try {
        file = new File("C:/Users/Hospice/backup/manyaspects_alphawks/manyaspects_alphawks/config/stopword.list");
      inputStream = new BufferedReader(new FileReader(file));
      String line;
      while ((line = inputStream.readLine()) != null) {
        if ((line.length() == 0) || (line.trim().length() == 0))
          continue;
        this.stopwords.add(line.trim());
      }
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }  catch (IOException ex) {
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

  private void loadStemmer()
  {
    File file = null;
    file = new File("C:/Users/Hospice/backup/manyaspects_alphawks/manyaspects_alphawks/config/stemrules.txt");
    this.paice_stemmer = new PaiceStemmerTest(file.getAbsolutePath(), "/p");

    this.porter_stemmer = new PorterStemmerTest();
  }

  private void parseSentence()
  {
    if ((this.sentences == null) || (this.sentences.isEmpty())) {
      return;
    }

    String splitRegex = "[\\_\\(\\)\\[\\]\\-\\'\\`\\;\\|\\{\\}\\:\\,\\\"\\s]+";

    for (String str : this.sentences)
    {
      str = str.trim();

      if ((str.endsWith(".")) || (str.endsWith("?")) || (str.endsWith("!")))
        str = str.substring(0, str.length() - 1);
      if (str.endsWith("...")) {
        str = str.substring(0, str.length() - 3);
      }
      String[] words = str.split(splitRegex);
      for (String word : words) {
        word = word.trim().toLowerCase();

        if ((this.stopwords.contains(word)) || (word.length() == 0))
          continue;
        this.numTotalWords += 1;

        word = this.paice_stemmer.stripAffixes(word);
	
        Double count = (Double)this.dictionary.get(word);
        this.dictionary.put(word, Double.valueOf(count == null ? 1.0D : count.doubleValue() + 1.0D));
      }

    }

    this.numUniqueWords = this.dictionary.size();
    if (this.numUniqueWords == 0) {
      System.err.println("None of the words can be used for summarization. System Exit...");
      System.exit(1);
    }

    for (String str : this.sentences)
    {
      str = str.trim();

      if ((str.endsWith(".")) || (str.endsWith("?")) || (str.endsWith("!")))
        str = str.substring(0, str.length() - 1);
      if (str.endsWith("...")) {
        str = str.substring(0, str.length() - 3);
      }
      Map hm = new HashMap();
      String[] words = str.split(splitRegex);
      for (String word : words) {
        word = word.trim().toLowerCase();

        if ((this.stopwords.contains(word)) || (!this.dictionary.containsKey(this.paice_stemmer.stripAffixes(word))))
        {
          continue;
        }

        hm.put(this.paice_stemmer.stripAffixes(word), Double.valueOf(1.0D));
		
		//create map word-stemmed
      }

      this.sparseSentences.add(hm);
      if (hm.size() == 0)
        this.numUselessSentences += 1;
    }
  }

  private void parseSentenceUmls() throws Exception
  {
    //Umls umls = new Umls();
   // try{
	

    if ((this.sentences == null) || (this.sentences.isEmpty())) {
      return;
    }

    String splitRegex = "[\\_\\(\\)\\[\\]\\-\\'\\`\\;\\|\\{\\}\\:\\,\\\"\\s]+";

    for (String str : this.sentences)
    {
      str = str.trim();

      if ((str.endsWith(".")) || (str.endsWith("?")) || (str.endsWith("!")))
        str = str.substring(0, str.length() - 1);
      if (str.endsWith("...")) {
        str = str.substring(0, str.length() - 3);
      }
      String[] words = str.split(splitRegex);
      for (String word : words) {
        word = word.trim().toLowerCase();

        if ((this.stopwords.contains(word)) || (word.length() == 0))
          continue;
        this.numTotalWords += 1;

        word = this.paice_stemmer.stripAffixes(word);
	
        Double count = (Double)this.dictionary.get(word);
        this.dictionary.put(word, Double.valueOf(count == null ? 1.0D : count.doubleValue() + 1.0D));
      }

    }

    this.numUniqueWords = this.dictionary.size();
    if (this.numUniqueWords == 0) {
      System.err.println("None of the words can be used for summarization. System Exit...");
      System.exit(1);
    }

    for (String str : this.sentences)
    {
      str = str.trim();

      if ((str.endsWith(".")) || (str.endsWith("?")) || (str.endsWith("!")))
        str = str.substring(0, str.length() - 1);
      if (str.endsWith("...")) {
        str = str.substring(0, str.length() - 3);
      }
      Map hm = new HashMap();
      String[] words = str.split(splitRegex);
      for (String word : words) {
        word = word.trim().toLowerCase();

        if ((this.stopwords.contains(word)) || (!this.dictionary.containsKey(this.paice_stemmer.stripAffixes(word))))
        {
          continue;
        } else {
		//if(this.umls.getCategory(word).length()!=0){
					//bf.append(umls.getCategory(tokens.get(t))+ "\n");
					
					//bf.append(tokens.get(t).trim()+" ");
					//words.add(tokens.get(t).trim());
					//bf.append(umls.getCategory(tokens.get(t))+ "\n");
		
			hm.put(this.paice_stemmer.stripAffixes(word), Double.valueOf(1.0D));
		//}
		}
		//create map word-stemmed
      }
      this.sparseSentences.add(hm);
      if (hm.size() == 0)
        this.numUselessSentences += 1;
    }
	
		  /*}catch (Exception ex)
    {
      ex.printStackTrace();
    }*/


  }
  
  
  
  public String getDocument()
  {
    return this.document;
  }

  public List<String> getSentences()
  {
    return this.sentences;
  }

  public List<Map<String, Double>> getSparseSentences()
  {
    return this.sparseSentences;
  }

  public Map<String, Double> getDictionary()
  {
    return this.dictionary;
  }

  public int getNumSentences()
  {
    return this.numSentences;
  }

  public int getNumTotalWords()
  {
    return this.numTotalWords;
  }

  public int getNumUniqueWords()
  {
    return this.numUniqueWords;
  }

  public int getNumUsefulSentences()
  {
    return this.numSentences - this.numUselessSentences;
  }

  public PaiceStemmerTest getPaiceStemmer()
  {
    return this.paice_stemmer;
  }

  public Set<String> getStopWords()
  {
    return this.stopwords;
  }
  
  
  public static void main (String [] args) throws Exception{
	//this.help = new Utilities();
	double freqThreshold = (-1.0D / 0.0D);

	String file = "C:/Users/hospice/Downloads/manyaspects_alphawks/manyaspects_alphawks/testfiles/dna.txt";
	ParserTest documentParser = new ParserTest(file, freqThreshold);
	
	/*for (int i = 0; i < 10; i++) {
        String str = (String)documentParser.getSentences().get(i);
        if (str.indexOf("\r") != -1) {
          str = str.replaceAll("\r", "");
        }
        System.out.println("[" + (i + 1) + "] " + str + "\n");
        
      }*/
    System.out.println(documentParser.getSparseSentences());
	//System.out.println(documentParser.getSparseSentences());
  }
}