package sentences;


import com.aliasi.sentences.MedlineSentenceModel;
import com.aliasi.sentences.SentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;

//import com.mycompany.myapp.parser.PaiceStemmer;
//import com.mycompany.myapp.parser.PorterStemmer;
//import com.mycompany.myapp.umlsfiles.Umls;
//import com.mycompany.myapp.wordnetfiles.WordNet;


import com.aliasi.util.Files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.junit.Test;

//import org.apache.commons.lang.StringUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
//import java.util.Arrays;
//import java.util.Iterator;
import java.util.List;

import org.junit.Test;
//import java.util.Set;
public class SentenceBoundary {
	private Set<String> stopwords;
	//private PaiceStemmer paice_stemmer;
	//private PorterStemmer porter_stemmer;
	public Map<String, String> abrevWords;

	private void initComponents(){
		
		
	    this.stopwords = new HashSet<String>();
	    this.abrevWords = new HashMap<String, String>();

	}
	
	 
	public SentenceBoundary() 
	{   
		initComponents();
	    
		//loadStopwords();
		//loadAbrevwords();
		//loadStemmer();
	}
	static final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
    static final SentenceModel SENTENCE_MODEL  = new MedlineSentenceModel();
     

	/**
	 * @param args
	 */
/*	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		File file = new File( "C:/Users/Hospice/backup2/fromDesktop/comparison/compare41.dat");
		String text = Files.readFromFile(file,"ISO-8859-1");
		System.out.println("INPUT TEXT: ");
		//System.out.println(text);
		String text2 = "We have developed a method for amplifying longer DNA sequences, ranging up to 300 bases, from postmortem formalin fixed and paraffin wax embedded tissues, with no modification to the usual DNA extraction procedures";
        ArrayList sentList = new ArrayList();
        sentList = (ArrayList) sentence(text);
        for (int i = 0; i<= sentList.size()-1; i++){
        	
        	System.out.println(sentList.get(i));
        	
        }
		//System.out.println(sentence(text));
	/*	List<String> tokenList = new ArrayList<String>();
		List<String> whiteList = new ArrayList<String>();
		Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(text.toCharArray(),0,text.length());
		tokenizer.tokenize(tokenList,whiteList);

		//System.out.println(tokenList.size() + " TOKENS");
		//System.out.println(whiteList.size() + " WHITESPACES");

		String[] tokens = new String[tokenList.size()];
		String[] whites = new String[whiteList.size()];
		tokenList.toArray(tokens);
		whiteList.toArray(whites);
		int[] sentenceBoundaries = SENTENCE_MODEL.boundaryIndices(tokens,whites);

		System.out.println(sentenceBoundaries.length 
				   + " SENTENCE END TOKEN OFFSETS");
			
		if (sentenceBoundaries.length < 1) {
		    System.out.println("No sentence boundaries found.");
		    return;
		}
		int sentStartTok = 0;
		int sentEndTok = 0;
		for (int i = 0; i < sentenceBoundaries.length; ++i) {
		    sentEndTok = sentenceBoundaries[i];
		    System.out.println("SENTENCE "+(i+1)+": ");
		    for (int j=sentStartTok; j<=sentEndTok; j++) {
			System.out.println(tokens[j]);
			System.out.println(whites[j+1]);

			//System.out.println(tokens[j]+whites[j+1]);
		    }
		    System.out.println();
		    sentStartTok = sentEndTok+1;
		}
		
	}*/
	public static List<String> sentence(String text)throws IOException{
		List<String> sentences = new ArrayList<String>();
		
		loadStemmer();

		List<String> tokenList = new ArrayList<String>();
		List<String> whiteList = new ArrayList<String>();
		Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(text.toCharArray(),0,text.length());
		tokenizer.tokenize(tokenList,whiteList);

		//System.out.println(tokenList.size() + " TOKENS");
		//System.out.println(whiteList.size() + " WHITESPACES");

		String[] tokens = new String[tokenList.size()];
		String[] whites = new String[whiteList.size()];
		tokenList.toArray(tokens);
		whiteList.toArray(whites);
		int[] sentenceBoundaries = SENTENCE_MODEL.boundaryIndices(tokens,whites);

		/*System.out.println(sentenceBoundaries.length 
				   + " SENTENCE END TOKEN OFFSETS");
			
		/*if (sentenceBoundaries.length < 1) {
		    System.out.println("No sentence boundaries found.");
		    return;
		}*/
		int sentStartTok = 0;
		int sentEndTok = 0;
		//StringBuilder sentence = new StringBuilder();
		 String word = "";
		for (int i = 0; i < sentenceBoundaries.length; ++i) {
		    sentEndTok = sentenceBoundaries[i];
		    String sentence =  "";
		    //System.out.println("SENTENCE "+(i+1)+": ");
		    for (int j=sentStartTok; j<=sentEndTok; j++) {
			//System.out.print(tokens[j]+whites[j+1]);
		    // word = this.paice_stemmer.stripAffixes(tokens[j]);
		     //System.out.println(word);
		     //sentence += word+whites[j+1];
			sentence += tokens[j]+whites[j+1];
		    }
		    sentences.add(sentence);
		    //System.out.println();
		    sentStartTok = sentEndTok+1;
		}
		return sentences;
	}
	@Test
	public void testSentenceBoundary() throws IOException{
		
		SentenceBoundary sentB = new SentenceBoundary();
		
		File file = new File( "C:/Users/Hospice/backup2/fromDesktop/comparison/compare41.dat");
		String text = Files.readFromFile(file,"ISO-8859-1");
		System.out.println("INPUT TEXT: ");
		//System.out.println(text);
		String text2 = "We have developed a method for amplifying longer DNA sequences, ranging up to 300 bases, from postmortem formalin fixed and paraffin wax embedded tissues, with no modification to the usual DNA extraction procedures";
        ArrayList sentList = new ArrayList();
        sentList = (ArrayList) sentence(text);
        for (int i = 0; i<= sentList.size()-1; i++){
        	
        	System.out.println(sentList.get(i));
        	
        }
	}
	
	public String normText(String text){
		text=  text.replaceAll("of[\\s+]around", "UPTO");
		text=  text.replaceAll("of[\\s+]up[\\s+]to", "UPTO");

		text=  text.replaceAll("up[\\s+]to", "UPTO");
		text=  text.replaceAll("no[\\s+]longer[\\s+]than", "UPTO");
		text=  text.replaceAll("of[\\s+]about", "UPTO");
		text=  text.replaceAll("ranging[\\s+]up[\\s+]to", "UPTO");
		  
		  return text;
	  }
	
	public List<String> sentenceStemed(String text)throws IOException{
		List<String> sentences = new ArrayList<String>();
		
		loadStemmer();
    	//System.out.println(this.abrevWords);

		List<String> tokenList = new ArrayList<String>();
		List<String> whiteList = new ArrayList<String>();
		text = normText(text);
		Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(text.toCharArray(),0,text.length());
		tokenizer.tokenize(tokenList,whiteList);

		//System.out.println(tokenList.size() + " TOKENS");
		//System.out.println(whiteList.size() + " WHITESPACES");

		String[] tokens = new String[tokenList.size()];
		String[] whites = new String[whiteList.size()];
		tokenList.toArray(tokens);
		whiteList.toArray(whites);
		int[] sentenceBoundaries = SENTENCE_MODEL.boundaryIndices(tokens,whites);
		/*System.out.println(sentenceBoundaries.length 
				   + " SENTENCE END TOKEN OFFSETS");
			
		/*if (sentenceBoundaries.length < 1) {
		    System.out.println("No sentence boundaries found.");
		    return;
		}*/
		int sentStartTok = 0;
		int sentEndTok = 0;
		//StringBuilder sentence = new StringBuilder();
		 String word = "";
		for (int i = 0; i < sentenceBoundaries.length; ++i) {
		    sentEndTok = sentenceBoundaries[i];
		    String sentence =  "";
		    //System.out.println("SENTENCE "+(i+1)+": ");
		    for (int j=sentStartTok; j<=sentEndTok; j++) {
			//System.out.print(tokens[j]+whites[j+1]);
		    	//if (stopwords.contains(tokens[j].toLowerCase().trim())||tokens[j].toLowerCase().trim().length()<2){
					//{}
						
						//System.out.println(tokens.get(t));
				  //        continue;
					//}
		     //word = this.paice_stemmer.stripAffixes(tokens[j]);
		    	word = tokens[j];
		     //System.out.println(word);
		    // sentence += word+whites[j+1]+" ";
		     if (this.abrevWords.get(word.trim())!=null){
		    	 //System.out.println("This is abbreviation   " +word);
		    	 String[] abr = this.abrevWords.get(word).split("[-\\s]");
		    	 	for(int k= 0; k < abr.length; k++){
		    	 		//String word1  = this.paice_stemmer.stripAffixes(abr[k]);
		    	 		//System.out.println(word1);
		    	 		//sentence += word1+" ";
		    	 		sentence += abr[k]+" ";
		    	 	}
		     }
		     else{
		    	// word = this.paice_stemmer.stripAffixes(tokens[j]);
		    	 //sentence += word+whites[j+1]+" ";
		    	 
		    	 sentence += tokens[j]+whites[j+1]+" ";
		     }
			//sentence += tokens[j]+whites[j+1];
		    }
	    	// System.out.println("This is sentence" +sentence);
            
		    sentences.add(sentence);
		    //System.out.println(sentence);
		    sentStartTok = sentEndTok+1;
		}
		return sentences;
	}
	
	public List<String> queryToken(String text)throws Exception{
		List<String> qTokens = new ArrayList<String>();
		
		List<String> tokenList = new ArrayList<String>();
		List<String> whiteList = new ArrayList<String>();
		Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(text.toCharArray(),0,text.length());
		tokenizer.tokenize(tokenList,whiteList);

		//System.out.println(tokenList.size() + " TOKENS");
		//System.out.println(whiteList.size() + " WHITESPACES");

		String[] tokens = new String[tokenList.size()];
		String[] whites = new String[whiteList.size()];
		tokenList.toArray(tokens);
		whiteList.toArray(whites);
		int[] sentenceBoundaries = SENTENCE_MODEL.boundaryIndices(tokens,whites);

		/*System.out.println(sentenceBoundaries.length 
				   + " SENTENCE END TOKEN OFFSETS");
			
		/*if (sentenceBoundaries.length < 1) {
		    System.out.println("No sentence boundaries found.");
		    return;
		}*/
		int sentStartTok = 0;
		int sentEndTok = 0;
		//StringBuilder sentence = new StringBuilder();
		//WordNet  wn = new WordNet();

		for (int i = 0; i < sentenceBoundaries.length; ++i) {
		    sentEndTok = sentenceBoundaries[i];
		    String token =  "";
		    //System.out.println("SENTENCE "+(i+1)+": ");
		    for (int j=sentStartTok; j<=sentEndTok; j++) {
			//System.out.print(tokens[j]+whites[j+1]);
			token= tokens[j]+whites[j+1];
			qTokens.add(token);

			//Map<String, String> smilarWord =
			//String[] simWord = wn.similarWords(token).values().toString().split(" ");
				//System.out.println(simWord.length-1);

				//String[] simWord = smilarWord.values().toString().split(" ");
			//if(token.length()> 3){
	
			/*	for(int k = 1; k < simWord.length-1; k ++){
					System.out.println(simWord[k]);
					qTokens.add(simWord[k]);
				}*/
			//}	
		    }
		   
		    //System.out.println();
		    sentStartTok = sentEndTok+1;
		   // break;

		}
		HashSet<String> hs = new HashSet<String>();
		List<String> qTokens2 = new ArrayList<String>();
		hs.addAll(qTokens);
		for (Iterator<String> it = hs.iterator();  it.hasNext();){
			qTokens2.add(it.next());
			
		}
		
		return qTokens2;
	}
	
	
	public List<String> tokenize(String text)throws Exception{
		List<String> Tokens = new ArrayList<String>();
		
		List<String> tokenList = new ArrayList<String>();
		List<String> whiteList = new ArrayList<String>();
		Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(text.toCharArray(),0,text.length());
		tokenizer.tokenize(tokenList,whiteList);

		//System.out.println(tokenList.size() + " TOKENS");
		//System.out.println(whiteList.size() + " WHITESPACES");

		String[] tokens = new String[tokenList.size()];
		String[] whites = new String[whiteList.size()];
		tokenList.toArray(tokens);
		whiteList.toArray(whites);
		int[] sentenceBoundaries = SENTENCE_MODEL.boundaryIndices(tokens,whites);

		/*System.out.println(sentenceBoundaries.length 
				   + " SENTENCE END TOKEN OFFSETS");
			
		/*if (sentenceBoundaries.length < 1) {
		    System.out.println("No sentence boundaries found.");
		    return;
		}*/
		int sentStartTok = 0;
		int sentEndTok = 0;
		//StringBuilder sentence = new StringBuilder();
		//WordNet  wn = new WordNet();

		for (int i = 0; i < sentenceBoundaries.length; ++i) {
		    sentEndTok = sentenceBoundaries[i];
		    String token =  "";
		    //System.out.println("SENTENCE "+(i+1)+": ");
		    for (int j=sentStartTok; j<=sentEndTok; j++) {
			//System.out.print(tokens[j]+whites[j+1]);
			token= tokens[j]+whites[j+1];
			Tokens.add(token);

			//Map<String, String> smilarWord =
			//String[] simWord = wn.similarWords(token).values().toString().split(" ");
				//System.out.println(simWord.length-1);

				//String[] simWord = smilarWord.values().toString().split(" ");
			//if(token.length()> 3){
	
			/*	for(int k = 1; k < simWord.length-1; k ++){
					System.out.println(simWord[k]);
					qTokens.add(simWord[k]);
				}*/
			//}	
		    }
		   
		    //System.out.println();
		    sentStartTok = sentEndTok+1;
		   // break;

		}
		/*HashSet<String> hs = new HashSet<String>();
		List<String> qTokens2 = new ArrayList<String>();
		hs.addAll(qTokens);
		for (Iterator<String> it = hs.iterator();  it.hasNext();){
			qTokens2.add(it.next());
			
		}*/
		
		return Tokens;
	}
	 

	 private void loadStopwords()
    {
		    

    File file = null;
    BufferedReader inputStream = null;
    try {
      file = new File("C:/Users/hospice/Desktop/SentBoundUmls/stopword.list");
      inputStream = new BufferedReader(new FileReader(file));
      String line;
      while ((line = inputStream.readLine()) != null) {
    	// System.out.println(line);
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
	 
	 
	 private void loadAbrevwords()
	  {
			    

	  File file = null;
	  BufferedReader inputStream = null;
	  try {
	    file = new File("C:/Users/hospice/Desktop/SentBoundUmls/abrelist.txt");
	    inputStream = new BufferedReader(new FileReader(file));
	    String line;
	    while ((line = inputStream.readLine()) != null) {
	  	// System.out.println(line);
	      if ((line.length() == 0) || (line.trim().length() == 0))
	        continue;
	      String[] abrev = line.split("\\s+");
	      abrevWords.put(abrev[0].trim(), line.substring(abrev[0].length()).trim());
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
	 
	 
	 
	 
	 
	 
	 private static void loadStemmer() throws IOException
	  {
		 
	    File file = null;
	    //file = new File(getClass().getResource("/stemrules.txt").toURI());
		file = new File("C:/Users/hospice/Desktop/SentBoundUmls/stemrules.txt");
		//this.paice_stemmer = new PaiceStemmer(file.getAbsolutePath(), "/p");

	   // this.porter_stemmer = new PorterStemmer();
		  
		 
	    
	  }
	 
	//@Test
	public void testSentence() throws Exception{
		SentenceBoundary sb = new SentenceBoundary();
		
		//Set<String> stops = null;
		//loadStopwords();
		//System.out.println(sb.stopwords);
		///*System.out.println(stopwords);
		FileWriter fr =  new FileWriter("C:/Users/Hospice/backup2/fromDesktop/comparison/compare41WriteTest.dat");
		BufferedWriter bf = new BufferedWriter(fr);
		
		
		File file = new File("C:/Users/Hospice/backup2/fromDesktop/comparison/compare41.dat");
		String text = Files.readFromFile(file,"ISO-8859-1");
		//Umls umls = new Umls();
		List<String> sentences = new ArrayList<String>();
		List<String> tokens = new ArrayList<String>();

		sentences = sentence(text);
		for (String sentence: sentences){
			ArrayList<String> words = new ArrayList<String>();
			//System.out.println(sentence);
			bf.append(sentence);
			tokens = tokenize(sentence);
			for (int t=0 ; t < tokens.size(); t++){
				if ((sb.stopwords.contains(tokens.get(t).toLowerCase().trim()))||tokens.get(t).toLowerCase().trim().length()<2){
				//{}
					
					//System.out.println(tokens.get(t));
			          continue;
				}
				else{	
				//bf.append(tokens.get(t)+"\n");
				
				//System.out.println(tokens.get(t));
				//if(umls.getCategory(tokens.get(t).trim()).length()!=0){
					//bf.append(umls.getCategory(tokens.get(t))+ "\n");
					
					//bf.append(tokens.get(t).trim()+" ");
					words.add(tokens.get(t).trim());
					//bf.append(umls.getCategory(tokens.get(t))+ "\n");
				//}
				}
				//System.out.println();
			}
			String first = words.get(0).toUpperCase();
			bf.append(first+" ");
			for (int k =1; k< words.size(); k++){
				bf.append(words.get(k)+" ");
				
			}
			bf.append(".\n");
		}
		bf.close();
	}
	
	
	
	//@Test
    public void testSentExpand() throws IOException{
    	File file = new File("C:/Users/hospice/Desktop/sharefolder/comparison/compare19.dat");
		String text = Files.readFromFile(file,"ISO-8859-1");
    	sentenceStemed(text);
    }
}
