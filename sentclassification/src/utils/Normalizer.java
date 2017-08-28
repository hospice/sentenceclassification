package utils;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Normalizer
{
  public static String citationReplacement = "#citation";
  public static String numberReplacement = "#NuMBeR";
  private boolean removePunctuations;
  private boolean replaceNumbers;
  private boolean replaceCitations;

  
  private boolean removeNumbers;
  private boolean removeStopwords;
  private boolean useStemmer;
  private boolean lowercase;
  private Set<String> stopWords;
  private Stemmer stemmer;
  
  public Normalizer()
  {
    commonInitialize();
    this.lowercase = true;
    this.removePunctuations = true;
    this.removeStopwords = false;
    this.useStemmer = false;
    this.removeNumbers = false;
    this.replaceNumbers = true;
    this.replaceCitations = false;
  }
  
  public Normalizer(boolean lowercase, boolean removePunctuations, boolean removeStopwords, boolean useStemmer, boolean removeNumbers, boolean replaceNumbers)
  {
    commonInitialize();
    this.lowercase = lowercase;
    this.removePunctuations = removePunctuations;
    this.removeStopwords = removeStopwords;
    this.useStemmer = useStemmer;
    if (useStemmer) {
      this.stemmer = new Stemmer();
    }
    this.removeNumbers = removeNumbers;
    this.replaceNumbers = replaceNumbers;
  }

  public Normalizer(boolean lowercase, boolean removePunctuations, boolean removeStopwords, boolean useStemmer, boolean removeNumbers, boolean replaceNumbers, boolean replaceCitations)
  {
    commonInitialize();
    this.lowercase = lowercase;
    this.removePunctuations = removePunctuations;
    this.removeStopwords = removeStopwords;
    this.useStemmer = useStemmer;
    if (useStemmer) {
      this.stemmer = new Stemmer();
    }
    this.removeNumbers = removeNumbers;
    this.replaceNumbers = replaceNumbers;
    this.replaceCitations = replaceCitations;

  }
  
  private void commonInitialize()
  {
    String[] sw = { "a", "about", "after", "all", "also", "an", "and", "another", "any", "are", "as", "at", "be", "because", "been", "before", "being", "between", "both", "but", "by", "came", "can", "come", "could", "did", "do", "does", "each", "else", "for", "from", "get", "got", "has", "had", "he", "have", "her", "here", "him", "himself", "his", "how", "if", "in", "into", "is", "it", "its", "just", "like", "make", "many", "me", "might", "more", "most", "much", "must", "my", "never", "now", "of", "on", "only", "or", "other", "our", "out", "over", "re", "said", "same", "see", "should", "since", "so", "some", "still", "such", "take", "than", "that", "the", "their", "them", "then", "there", "these", "they", "this", "those", "through", "to", "too", "under", "up", "use", "very", "want", "was", "way", "we", "well", "were", "what", "when", "where", "which", "while", "who", "will", "with", "would", "you", "your" };
  
    this.stopWords = new HashSet(Arrays.asList(sw));
  }
  
  public String normalize(String text)
  {
    if (this.lowercase) {
      text = text.toLowerCase();
    }
    if (this.replaceCitations){
    	//String citationPattern = "\\[ (\\d )* \\d+\\-\\d+ ( \\d)*\\]|\\[ \\d+ \\]";
    	String citationPattern = "\\[[\\d-]+\\]";
    	//String citationPattern = " \\d+\\-\\d* \\[ \\d+ \\]";
    	String brackPattern = "\\[\\d+-*\\d*\\]";
    	String Personsname = "([A-Za-z][a-z'`-]+)"; // Apostrophes like in "D'Alembert" and hyphens like in "Flycht-Eriksson".
        String altern = Personsname + "*(19|20)[0-9][0-9]\\w*| ?\\( *(19|20)[0-9][0-9]\\)" ;
        
        
    	
    	String YearPattern = "(, *(19|20)[0-9][0-9]| ?\\( *(19|20)[0-9][0-9]\\))"; // Either Descartes, 1990 or Descartes (1990) are accepted.
    	String altern2 = Personsname+" "+ "et al."+YearPattern+"*";
    	String etal = Personsname+" "+Personsname+ " et al\\.*"; // You may find this
        String andconj = Personsname + " and " + Personsname;
        String commaconj = Personsname + ", " + "(" + Personsname + "|"+"et al.?"+")"; // Some authors write citations like "A, B, et al. (1995)". The comma before the "et al" pattern is not rare.
        String totcit = Personsname+"?"+etal+"?"+"("+andconj+"|"+commaconj+")*"+etal+"?"+YearPattern; 
        //text = text.replaceAll(brackPattern, citationReplacement);
        text = text.replaceAll(citationPattern+"|"+totcit+"|"+altern+"|"+andconj+"|"+etal+"|"+altern2 , citationReplacement);
    }
    if (this.removePunctuations) {
      text = text.replaceAll("[\\[\\](){!}<>.?',:%\";\\\\*#/@$^+=|&]", "");
    }
    if (this.removeStopwords) {
      text = stopwordsRemove(text);
    }
    if (this.useStemmer) {
      text = stemText(text);
    }
    if (this.removeNumbers) {
    //  text = text.replaceAll("\\b[0-9]+\\S*", "");
    }
    if (this.replaceNumbers) {
      text = text.replaceAll("\\b[0-9]+\\S*", numberReplacement);
    }
    return text.trim();
  }
  
  private String[] words(String text)
  {
    return text.split(" +");
  }
  
  private String stopwordsRemove(String text)
  {
    String[] words = words(text);
    String newText = "";
    for (String word : words) {
      if (!this.stopWords.contains(word.toLowerCase())) {
        newText = newText + word + " ";
      }
    }
    return newText;
  }
  
  private String stemText(String text)
  {
    String[] words = words(text);
    String newText = "";
    for (String word : words)
    {
      this.stemmer.addWord(word);
      this.stemmer.stem();
      newText = newText + this.stemmer.toString() + " ";
    }
    return newText;
  }
  
  public static void main(String[] args)
  {
    String text = "Such activation did not occur in schizophrenic patients  who were also impaired in the task ( Shepard et al. 2006 ) .";
    Normalizer n = new Normalizer(true, true, false, false, false, true, true);
    System.out.println(n.normalize(text));
  }
}
