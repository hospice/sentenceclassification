package parse;

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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aliasi.sentences.MedlineSentenceModel;
import com.aliasi.sentences.SentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.Files;

import umls.UMLS;
import utils.TermTokenizer;
import stemmer.PaiceStemmer;

public class ThisSentence {
	static final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
    static final SentenceModel SENTENCE_MODEL  = new MedlineSentenceModel();
	private Set<String> stopwords;
	private PaiceStemmer paice_stemmer;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
    HashMap<String, Double> wordFrequency = new HashMap<String, Double>();

    public ThisSentence(){
    	initComponents();
    	loadStopwords();
    	paice_stemmer = new PaiceStemmer("C:/Users/Hospice/manyaspects_alphawks/manyaspects_alphawks/config/stemrules.txt","/pre");
    }
    private void initComponents(){
		
	    this.stopwords = new HashSet<String>();
	    //this.abrevWords = new HashMap<String, String>();

	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
			  Sentences sentences = new Sentences();
		  HashMap<String, Double> hm = new HashMap<String, Double>();

		try{
			File f = new File("C:/Users/Hospice/New_workspace/sentclassification/journal/ThisOut/This_BMC_Bioinformatics.txt");
			  TermTokenizer tk = new TermTokenizer();
			  f.createNewFile();
			  PrintWriter out1 = new PrintWriter(new FileWriter(f));
			  // Open the file that is the first 
			  // command line parameter
			  String output = "";
			 FileInputStream fstream = new FileInputStream("C:/Users/Hospice/New_workspace/sentclassification/journal/BMC_Bioinformatics.txt");
			  // Get the object of DataInputStream
			  
			  //FileInputStream fstream = new FileInputStream("./AllNgram.txt");

			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null )   {
				//  System.out.println(strLine);
				  if (strLine.startsWith("--")||strLine.endsWith("Line does not have a line to pair with; either it is Line 1 or it is a line in a group of 3 or more lines"))
				  {}
				 // if (strLine.isEmpty())
				  //{}

				  
				  else{
					  
			  // Print the content on the console
					  String[] sentencesT = strLine.split("sentences[-:]\\d+[-:]");
					  if (!sentencesT[1].startsWith("This"))
						 // System.out.println (sentences[1]);
					      output+=sentencesT[1]+"\n";
					//  hm = sentences.getTextFrequencyMap(strLine);
					  
				  }
			  }
			 
			  out1.print(output);
			  //Close the input stream
			  out1.close();
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
			    
			    sentences.printMap(hm);
			    
			   // ThisSentence Sentence = new ThisSentence();
			  //  Sentence.normalizeSentence("Subsequently the RNA synthesis in this region was analysed by RT PCR in cells growing exponentially in minimal medium .");
    

	}
    
   // public static void main (String args) throws Exception{
   // 	Sentences umls1 = new Sentences();
   // 	umls1.normalizeSentence("The majority of our patients  #NUMBER or #NUMBER or #NUMBER PERCENT  were intubated at outlying facilities and received varied therapy before transfer to our institution .");
   // }
	public HashMap<String, Double> getTextFrequencyMap(String text)
	  {
	    //List<String> textWords = getWords(text);
	   // double[] sim1 = new double[textWords.size()];
	    //for (String word : textWords) {
	   // for (int i = 0; i< sim1.length; i++){
	      if (wordFrequency.containsKey(text.toLowerCase().trim()))
	        wordFrequency.put(text.toLowerCase().trim(), wordFrequency.get(text.toLowerCase().trim()) +1.0/1.0);
	      else {
	        wordFrequency.put(text.toLowerCase().trim(),   1.0/1.0);
	      //}
	     // }
	    }
	    return wordFrequency;
	  }
	

	 private void loadStopwords()
   {
		    

   File file = null;
   BufferedReader inputStream = null;
   try {
     file = new File("C:/Users/Hospice/manyaspects_alphawks/manyaspects_alphawks/config/stopword.list");
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
	public static void printMap(Map<String, Double> data) throws IOException {
		 // TermTokenizer tk = new TermTokenizer();

		  //tk.f.createNewFile();
		  String out = "";
		  PrintWriter out1 = new PrintWriter(new FileWriter("./AllNgramSort2.txt"));
			for (Iterator<String> iter = data.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				out+=key +"/"+data.get(key)+ "\n";
				System.out.println("key/value:"+key +"/"+data.get(key));
			}
			out1.print(out);
			out1.close();
		}
	
	public String normText(String text){
		/*text=  text.replaceAll("of[\\s+]around", "UPTO");
		text=  text.replaceAll("of[\\s+]up[\\s+]to", "UPTO");

		text=  text.replaceAll("up[\\s+]to", "UPTO");
		text=  text.replaceAll("no[\\s+]longer[\\s+]than", "UPTO");
		text=  text.replaceAll("of[\\s+]about", "UPTO");
		text=  text.replaceAll("ranging[\\s+]up[\\s+]to", "UPTO");
		*/
		text=  text.replaceAll(", ", "");
		text=  text.replaceAll("\\d+", "#NUMBER");
		text=  text.replaceAll("%", "%PERCENT");
		text=  text.replaceAll("[:\"]", "");
		text=  text.replaceAll("[:\"]", "");
		text=  text.replaceAll("\\W*", "");





		  return text;
	  }
	
	public List<String> sentenceStemed(String text)throws IOException{
		List<String> sentences = new ArrayList<String>();
		
		//loadStemmer();
    	//System.out.println(this.abrevWords);


		List<String> tokenList = new ArrayList<String>();
		List<String> whiteList = new ArrayList<String>();
		//text = normText(text);
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
		     
		    	// word = this.paice_stemmer.stripAffixes(tokens[j]);
		    	 //sentence += word+whites[j+1]+" ";
		    	 
		    	 sentence += tokens[j]+whites[j+1]+" ";
		     }
			//sentence += tokens[j]+whites[j+1];
		   // }
	    	// System.out.println("This is sentence" +sentence);
            
		    sentences.add(sentence);
		    //System.out.println(sentence);
		    sentStartTok = sentEndTok+1;
		}
		return sentences;
	}
	public void normalizeSentence(String sentence) throws Exception{
		FileWriter fw = new FileWriter("C:/Users/Hospice/New_workspace/sentclassification/data/output2316withStop.csv");
	      BufferedWriter bw = new BufferedWriter(fw);
		//SentenceBoundary sb = new SentenceBoundary();
		FileInputStream fstream = new FileInputStream("C:/Users/Hospice/New_workspace/sentclassification/annotated2316.csv");
		DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		//Set<String> stops = null;
		//loadStopwords();
		//System.out.println(sb.stopwords);
		///*System.out.println(stopwords);
		FileWriter fr =  new FileWriter("C:/Users/hospice/New_workspace/sentclassification/normalizedSentence.dat");
		BufferedWriter bf = new BufferedWriter(fr);
		int i =0;
		  while ((strLine = br.readLine()) != null)   {
			  i++;
          String[] line = strLine.split(",");
		//File file = new File("C:/Users/hospice/Desktop/SentBoundUmls/comp16bound.dat");
		//String sentence = Files.readFromFile(file,"ISO-8859-1");
		UMLS umls = new UMLS();
		String sentence1 = "";
		List<String> tokens = new ArrayList<String>();

		//sentence = text;
		//for (String sentence: sentences){
			ArrayList<String> words = new ArrayList<String>();
			//System.out.println(sentence);
			//bf.append(sentence).append(",");
			String NornSentence = normText(sentence);
			tokens = tokenize(line[0]);
			//System.out.println(this.stopwords);
			for (int t=0 ; t < tokens.size(); t++){
				//if (tokens.get(t).toLowerCase().trim().length()<2||this.stopwords.contains(tokens.get(t).trim())){
				//{}
				if (tokens.get(t).toLowerCase().trim().length()<2){
					//System.out.println(tokens.get(t));
			          continue;
				}
				else{	
				//bf.append(tokens.get(t)+"\n");
				
				//System.out.println(tokens.get(t));
				//if(umls.getConcept(tokens.get(t).trim()).length()!=0){
					//bf.append(umls.getCategory(tokens.get(t))+ "\n");
					//System.out.print(paice_stemmer.stripAffixes(tokens.get(t))+ " ");
					sentence1+=paice_stemmer.stripAffixes(tokens.get(t))+ " ";
					//System.out.print(": " + umls.getConcept(tokens.get(t)).split("/")[0]);
					//System.out.println(": " + umls.getCategory(tokens.get(t)).split("/")[0]);

					//bf.append(tokens.get(t).trim()+" ");
					//words.add(tokens.get(t).trim());
					//bf.append(tokens.get(t).trim()).append(" ").append(umls.getCategory(tokens.get(t))).append("\n");
				//}
				}
				//System.out.println();
			}
			/*String first = words.get(0);
			bf.append(first+" ");
			for (int k =1; k< words.size(); k++){
				bf.append(words.get(k)+" ");
				
			}*/
			//bf.append(".");
		//}
			//System.out.println(i + "  "+sentence1+","+line[1]);
			bw.write(sentence1+","+line[1]);
			bw.newLine();
		//bf.close();
		  }
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
	 
}
