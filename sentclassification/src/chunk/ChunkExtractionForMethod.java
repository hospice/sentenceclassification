package chunk;
import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.ChunkFactory;
import com.aliasi.chunk.Chunking;
import com.aliasi.chunk.ChunkingImpl;
//import com.aliasi.chunk.HmmChunker;

import com.aliasi.hmm.HiddenMarkovModel;
import com.aliasi.hmm.HmmDecoder;

import com.aliasi.tag.Tagging;

import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;

import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.FastCache;
import com.aliasi.util.Strings;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import lemma.StanfordLemmatizer;


/**
 * Expects to chunk 1 sentence at a time.
 */
public class ChunkExtractionForMethod implements Chunker {
	

    // Determiners & Numerals
    // ABN, ABX, AP, AP$, AT, CD, CD$, DT, DT$, DTI, DTS, DTX, OD
	
	//DB, DD, 

    // Adjectives
    // JJ, JJ$, JJR, JJS, JJT

    // Nouns
    // NN, NN$, NNS, NNS$, NP, NP$, NPS, NPS$
	
	//NPP, 

    // Adverbs
    // RB, RB$, RBR, RBT, RN (not RP, the particle adverb)
	
	//RR, RRR, RRT

    // Pronoun
    // PN, PN$, PP$, PP$$, PPL, PPLS, PPO, PPS, PPSS
	
	//PND, PNG, PNR

    // Verbs
    // VB, VBD, VBG, VBN, VBZ, VHG, VHI, VHN, VHZ, 
	
	//VBB, VBI, VDB, VDD, VDG, VDI, VDN, VDZ, VHB, VHD, VHG, VHI, VHN, VHZ, VVB, VVD, VVG, VVI,
	//VVN, VVZ, VVNJ, VVGJ, VVGN

    // Auxiliaries
    // MD, BE, BED, BEDZ, BEG, BEM, BEN, BER, BEZ
	//VM

    // Adverbs
    // RB, RB$, RBR, RBT, RN (not RP, the particle adverb)
    
	//RR, RRR, RRT

    // Punctuation
    // ', ``, '', ., (, ), *, --, :, ,

    static final Set<String> DETERMINER_TAGS = new HashSet<String>();
    static final Set<String> ADJECTIVE_TAGS = new HashSet<String>();
    static final Set<String> NOUN_TAGS = new HashSet<String>();
    static final Set<String> PRONOUN_TAGS = new HashSet<String>();

    static final Set<String> ADVERB_TAGS = new HashSet<String>();

    static final Set<String> VERB_TAGS = new HashSet<String>();
    
    //
    static final Set<String> PREPOSITION_TAGS = new HashSet<String>();
    static final Set<String> AUXILIARY_VERB_TAGS = new HashSet<String>();

    static final Set<String> PUNCTUATION_TAGS = new HashSet<String>();

    static final Set<String> START_VERB_TAGS = new HashSet<String>();
    static final Set<String> CONTINUE_VERB_TAGS = new HashSet<String>();

    static final Set<String> START_NOUN_TAGS = new HashSet<String>();
    static final Set<String> CONTINUE_NOUN_TAGS = new HashSet<String>();
    
    static final Set<String> START_CLAUSE_TAGS = new HashSet<String>();
    static final Set<String> CONTINUE_CLAUSE_TAGS = new HashSet<String>();
    
    static final Set<String> VVGN_TAG = new HashSet<String>();
    
    static final Set<String> CC_TAG = new HashSet<String>();

    static {
        DETERMINER_TAGS.add("abn");
        DETERMINER_TAGS.add("abx");
        DETERMINER_TAGS.add("ap");
        DETERMINER_TAGS.add("ap$");
        DETERMINER_TAGS.add("at");
        DETERMINER_TAGS.add("cd");
        DETERMINER_TAGS.add("cd$");
        DETERMINER_TAGS.add("dt");
        DETERMINER_TAGS.add("dt$");
        DETERMINER_TAGS.add("dti");
        DETERMINER_TAGS.add("dts");
        DETERMINER_TAGS.add("dtx");
        DETERMINER_TAGS.add("od");
        
        //Medpost
        DETERMINER_TAGS.add("DB");
        DETERMINER_TAGS.add("DD");
        
        ADJECTIVE_TAGS.add("jj");
        ADJECTIVE_TAGS.add("JJ");

        
        ADJECTIVE_TAGS.add("jj$");
        ADJECTIVE_TAGS.add("jjr");
        ADJECTIVE_TAGS.add("jjs");
        ADJECTIVE_TAGS.add("jjt");
        ADJECTIVE_TAGS.add("*");
        ADJECTIVE_TAGS.add("ql");

        NOUN_TAGS.add("nn");
        
        NOUN_TAGS.add("NN");
        NOUN_TAGS.add("NNP");
        NOUN_TAGS.add("NNS");



        
        NOUN_TAGS.add("nn$");
        NOUN_TAGS.add("nns");
        NOUN_TAGS.add("nns$");
        NOUN_TAGS.add("np");
        NOUN_TAGS.add("np$");
        NOUN_TAGS.add("nps");
        NOUN_TAGS.add("nps$");
        NOUN_TAGS.add("nr");
        NOUN_TAGS.add("nr$");
        NOUN_TAGS.add("nrs");
        
        //Medpost 
      //  NOUN_TAGS.add("NPP");
        NOUN_TAGS.add("NNS");

      //  NOUN_TAGS.add("VVGN");
      //  NOUN_TAGS.add("vbg");

        PRONOUN_TAGS.add("pn");
        PRONOUN_TAGS.add("pn$");
        PRONOUN_TAGS.add("pp$");
        PRONOUN_TAGS.add("pp$$");
        PRONOUN_TAGS.add("ppl");
        PRONOUN_TAGS.add("ppls");
        PRONOUN_TAGS.add("ppo");
        PRONOUN_TAGS.add("pps");
        PRONOUN_TAGS.add("ppss");
        
        //Medpost //PND, PNG, PNR
        
        PRONOUN_TAGS.add("PND");
        PRONOUN_TAGS.add("PNG");
       // PRONOUN_TAGS.add("PNR");
        //PRONOUN_TAGS.add("PN");


        VERB_TAGS.add("vb");
        VERB_TAGS.add("vbd");
        //VERB_TAGS.add("vbg");
        VERB_TAGS.add("vbn");
        VERB_TAGS.add("vbz");
        
        //Medpost Verb 
        //VBB, VBI, VDB, VDD, VDG, VDI, VDN, VDZ, VHB, VHD, VHG, VHI, VHN, VHZ, VVB, VVD, VVG, VVI,
    	//VVN, VVZ, VVNJ, VVGJ, VVGN
        
        VERB_TAGS.add("VB");
        VERB_TAGS.add("VBD");
        VERB_TAGS.add("VBG");
        VERB_TAGS.add("VBN");
        VERB_TAGS.add("VBZ");
        
        VERB_TAGS.add("VBB");
        VERB_TAGS.add("VBI");
        VERB_TAGS.add("VDB");
        VERB_TAGS.add("VDD");
        VERB_TAGS.add("VDG");
        
        VERB_TAGS.add("VDI");
        VERB_TAGS.add("VDN");
        VERB_TAGS.add("VDZ");
        VERB_TAGS.add("VHB");
        VERB_TAGS.add("VHD");
        
        VERB_TAGS.add("VHG");
        VERB_TAGS.add("VHI");
        VERB_TAGS.add("VHN");
        VERB_TAGS.add("VHZ");
        VERB_TAGS.add("VVB");
        
        VERB_TAGS.add("VVD");
        VERB_TAGS.add("VVG");
        VERB_TAGS.add("VVI");
        VERB_TAGS.add("VVN");
        VERB_TAGS.add("VVZ");
        
        VERB_TAGS.add("VVNG");
       // VERB_TAGS.add("VVGN");
        
        VERB_TAGS.add("VVGJ");
       // VERB_TAGS.add("VVGN");
        
        VVGN_TAG.add("VVGN");
        
        
        AUXILIARY_VERB_TAGS.add("TO");

        

        AUXILIARY_VERB_TAGS.add("to");
        AUXILIARY_VERB_TAGS.add("md");
        AUXILIARY_VERB_TAGS.add("be");
        AUXILIARY_VERB_TAGS.add("bed");
        AUXILIARY_VERB_TAGS.add("bedz");
        AUXILIARY_VERB_TAGS.add("beg");
        AUXILIARY_VERB_TAGS.add("bem");
        AUXILIARY_VERB_TAGS.add("ben");
        AUXILIARY_VERB_TAGS.add("ber");
        AUXILIARY_VERB_TAGS.add("bez");
        
        //Medpost
        AUXILIARY_VERB_TAGS.add("VM");

        

        ADVERB_TAGS.add("rb");
        ADVERB_TAGS.add("rb$");
        ADVERB_TAGS.add("rbr");
        ADVERB_TAGS.add("rbt");
        ADVERB_TAGS.add("rn");
        ADVERB_TAGS.add("ql");
        ADVERB_TAGS.add("*");  // negation

        //medpost
        //ADVERB_TAGS.add("RR");
        //ADVERB_TAGS.add("RRR");
        //ADVERB_TAGS.add("RRT");
        
        PUNCTUATION_TAGS.add("'");
        // PUNCTUATION_TAGS.add("``");
        // PUNCTUATION_TAGS.add("''");
        PUNCTUATION_TAGS.add(".");
        PUNCTUATION_TAGS.add("*");
         PUNCTUATION_TAGS.add(","); // miss comma-separated phrases
         PUNCTUATION_TAGS.add("(");
         PUNCTUATION_TAGS.add(")");
        // PUNCTUATION_TAGS.add("*"); // negation "not"
        // PUNCTUATION_TAGS.add("--");
         PUNCTUATION_TAGS.add(":");
         
         PREPOSITION_TAGS.add("II");
         
         CC_TAG.add("CC");
    }
    //VVGN
    static {

    	//START_NOUN_TAGS.addAll(DETERMINER_TAGS);
    	START_NOUN_TAGS.addAll(VVGN_TAG);
        START_NOUN_TAGS.addAll(ADJECTIVE_TAGS);
        START_NOUN_TAGS.addAll(NOUN_TAGS);
        //START_NOUN_TAGS.addAll(PRONOUN_TAGS);

        CONTINUE_NOUN_TAGS.addAll(START_NOUN_TAGS);
        CONTINUE_NOUN_TAGS.addAll(NOUN_TAGS);
        CONTINUE_NOUN_TAGS.addAll(CC_TAG);
        CONTINUE_NOUN_TAGS.addAll(VVGN_TAG);
        
        

        START_VERB_TAGS.addAll(VERB_TAGS);
        START_VERB_TAGS.addAll(AUXILIARY_VERB_TAGS);
        START_VERB_TAGS.addAll(ADVERB_TAGS);

        CONTINUE_VERB_TAGS.addAll(START_VERB_TAGS);
        CONTINUE_VERB_TAGS.addAll(PUNCTUATION_TAGS);
        
       // START_CLAUSE_TAGS.addAll(PREPOSITION_TAGS);
     //   START_CLAUSE_TAGS.addAll(NOUN_TAGS);
        
      //  CONTINUE_CLAUSE_TAGS.addAll(NOUN_TAGS);
      //  CONTINUE_CLAUSE_TAGS.addAll(CONTINUE_NOUN_TAGS);

        
    }

    private final HmmDecoder mPosTagger;
    private final TokenizerFactory mTokenizerFactory;
	//private static ArrayList<String> text =null;

    public ChunkExtractionForMethod(HmmDecoder posTagger,
                         TokenizerFactory tokenizerFactory) {
        mPosTagger = posTagger;
        mTokenizerFactory = tokenizerFactory;
    }

    public Chunking chunk(CharSequence cSeq) {
        char[] cs = Strings.toCharArray(cSeq);
        return chunk(cs,0,cs.length);
    }

    public Chunking chunk(char[] cs, int start, int end) {

        // tokenize
        List<String> tokenList = new ArrayList<String>();
        List<String> whiteList = new ArrayList<String>();
        Tokenizer tokenizer = mTokenizerFactory.tokenizer(cs,start,end-start);
        tokenizer.tokenize(tokenList,whiteList);
        String[] tokens
            = tokenList.<String>toArray(new String[tokenList.size()]);
        String[] whites
            = whiteList.<String>toArray(new String[whiteList.size()]);

        // part-of-speech tag
        Tagging<String> tagging = mPosTagger.tag(tokenList);

        ChunkingImpl chunking = new ChunkingImpl(cs,start,end);
        int startChunk = 0;
        for (int i = 0; i < tagging.size(); ) {
            startChunk += whites[i].length();
            
            //System.out.println(startChunk +  " " + whites[i].length());

            if (START_NOUN_TAGS.contains(tagging.tag(i))) {
                int endChunk = startChunk + tokens[i].length();
                ++i;
                while (i < tokens.length && CONTINUE_NOUN_TAGS.contains(tagging.tag(i))) {
                    endChunk += whites[i].length() + tokens[i].length();
                    ++i;
                }
                // this separation allows internal punctuation, but not final punctuation
                int trimmedEndChunk = endChunk;
                for (int k = i;
                     --k >= 0 && PUNCTUATION_TAGS.contains(tagging.tag(k)); ) {
                    trimmedEndChunk -= (whites[k].length() + tokens[k].length());
                }
                if (startChunk >= trimmedEndChunk) {
                    startChunk = endChunk;
                    continue;
                }
                Chunk chunk
                    = ChunkFactory.createChunk(startChunk,trimmedEndChunk,"NP");
                chunking.add(chunk);
                startChunk = endChunk;

            } else if (START_VERB_TAGS.contains(tagging.tag(i))) {
                int endChunk = startChunk + tokens[i].length();
                ++i;
               /* while (i < tokens.length && CONTINUE_VERB_TAGS.contains(tagging.tag(i))) {
                    endChunk += whites[i].length() + tokens[i].length();
                    ++i;
                }*/
                int trimmedEndChunk = endChunk;
                for (int k = i;
                     --k >= 0 && PUNCTUATION_TAGS.contains(tagging.tag(k)); ) {
                    trimmedEndChunk -= (whites[k].length() + tokens[k].length());
                }
                if (startChunk >= trimmedEndChunk) {
                    startChunk = endChunk;
                    continue;
                }
                Chunk chunk = ChunkFactory.createChunk(startChunk,trimmedEndChunk,"VP");
                chunking.add(chunk);
                startChunk = endChunk;

            }
            
            else if (START_CLAUSE_TAGS.contains(tagging.tag(i))) {
                int endChunk = startChunk + tokens[i].length();
                ++i;
                while (i < tokens.length && CONTINUE_CLAUSE_TAGS.contains(tagging.tag(i))) {
                    endChunk += whites[i].length() + tokens[i].length();
                    ++i;
                }
                int trimmedEndChunk = endChunk;
                for (int k = i;
                     --k >= 0 && PUNCTUATION_TAGS.contains(tagging.tag(k)); ) {
                    trimmedEndChunk -= (whites[k].length() + tokens[k].length());
                }
                if (startChunk >= trimmedEndChunk) {
                    startChunk = endChunk;
                    continue;
                }
                Chunk chunk = ChunkFactory.createChunk(startChunk,trimmedEndChunk,"CL");
                chunking.add(chunk);
                startChunk = endChunk;

            }
            
            else {
                startChunk += tokens[i].length();
                ++i;
            }
        }
        return chunking;
    }
    
  /* private  void loadFile()
    {
 		    
    ChunkExtractionForMethod pc = new ChunkExtractionForMethod(mPosTagger, mTokenizerFactory);
    File file = null;
    BufferedReader inputStream = null;
    try {
      file = new File("C:/Users/Hospice/New_workspace/sentclassification/ThisExperiment_output1.txt");
      inputStream = new BufferedReader(new FileReader(file));
      String line;
      
	  FileInputStream fstream = new FileInputStream("C:/Users/Hospice/New_workspace/sentclassification/ThisMethod_output.txt");
	  DataInputStream in = new DataInputStream(fstream);
	  BufferedReader br = new BufferedReader(new InputStreamReader(in));
      while ((line = br.readLine()) != null) {
    	  if (line.isEmpty())
		  {}

		  
		  else{
    	 System.out.println(line);
        //if ((line.length() == 0) || (line.trim().length() == 0))
       //   continue;
       // this.text.add(line.trim());
		  }
      }
    }   catch (IOException ex) {
      ex.printStackTrace();
    } 
  }*/
	private static Set<String> cuelist;
	public static String preprocess(String sent){
		String s = "";
		sent =sent.replaceAll("/\\b[B-Zb-z]\\b/", "");
		sent = sent.replaceAll("/\\b[^B-Zb-z0-9]\\b/", " ");
		sent = sent.replaceAll("\\d+", " ");

		sent = sent.replaceAll("\\W ", "");
		sent = sent.replaceAll("-", " ");
		/*sent = sent.replaceAll("finding", "find");
		sent = sent.replaceAll("findings", "find");

		sent = sent.replaceAll("found", "find");
		sent = sent.replaceAll("methods", "method");
		sent = sent.replaceAll("", "result");
		sent = sent.replaceAll("methods", "method");
		sent = sent.replaceAll("fig", "figure");
 
        sent = sent.replaceAll("results", "result");
        */
		//sent = sent.replaceAll("\\b[\\w']{1}\\b", "");
		//sent = sent.replaceAll("\\s{1,}", " ");		
		return sent;
	}

    public static void main(String[] args) throws IOException {
    	int k = 0;
    	cuelist = new HashSet<String>();
        // parse input params
       // File hmmFile = new File(args[0]);
    	File hmmFile = new File("C:/Users/Hospice/lingpipe-4.1.0/demos/models/pos-en-bio-medpost.HiddenMarkovModel");
    	File biogenchunk = new File("C:/Users/Hospice/lingpipe-4.1.0/demos/models/ne-en-bio-genetag.HmmChunker");
    	
    	
    	File f = new File("C:/Users/Hospice/New_workspace/sentclassification/journal/ThisOut/This_BMC_BioinformaticsNounPhaseAgain1.txt");
        int cacheSize = Integer.valueOf("50000");
        FastCache<String,double[]> cache = new FastCache<String,double[]>(cacheSize);
        f.createNewFile();
		  PrintWriter out1 = new PrintWriter(new FileWriter(f));
		  // Open the file that is the first 
		  // command line parameter
		  String output = "";
		 // StanfordLemmatizer sl = new StanfordLemmatizer();
        // read HMM for pos tagging
        HiddenMarkovModel posHmm;
        //@SuppressWarnings("unused")
    	Chunker hmChunk;
        try {
        	
            posHmm
                = (HiddenMarkovModel)
                AbstractExternalizable.readObject(hmmFile);
            hmChunk
            = (	Chunker)
            AbstractExternalizable.readObject(biogenchunk);
        } catch (IOException e) {
            System.out.println("Exception reading model=" + e);
            e.printStackTrace(System.out);
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("Exception reading model=" + e);
            e.printStackTrace(System.out);
            return;
        }

        // construct chunker
        //HmmDecoder hmChunkDec = new HmmDecoder();
        HmmDecoder posTagger  = new HmmDecoder(posHmm,null,cache);
        TokenizerFactory tokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;
        ChunkExtractionForMethod chunker = new ChunkExtractionForMethod(posTagger,tokenizerFactory);
       // HmmChunker chunker2 = new HmmChunker(tokenizerFactory,posTagger);

        String[] Sentences = {"This transition , located in exon 10 of the gene , produces an Arg-to-Gln substitution at amino acid 506 of the protein."};
       // loadFile();
        ArrayList<String> text = new ArrayList<String>();
        ArrayList<String> textR = new ArrayList<String>();
        File file = null;
        BufferedReader inputStream = null;
        try {
          //file = new File("C:/Users/Hospice/New_workspace/sentclassification/data2/CSVThisResult_output.csv");
          //inputStream = new BufferedReader(new FileReader(file));
          String line;
          
    	  FileInputStream fstream = new FileInputStream("C:/Users/Hospice/New_workspace/sentclassification/journal/ThisOut/This_BMC_Bioinformatics.txt");
    	  DataInputStream in = new DataInputStream(fstream);
    	  BufferedReader br = new BufferedReader(new InputStreamReader(in));
          while ((line = br.readLine()) != null) {
        	  if (line.isEmpty())
    		  {}

    		  
    		  else{
        	 //System.out.println(line);
            //if ((line.length() == 0) || (line.trim().length() == 0))
           //   continue;
    			  //String str = new String(line.getBytes("US-ASCII")); // Optional,
            text.add(preprocess(new String(line.getBytes("US-ASCII"))));
            //text.add(line.split(",")[0]);
    		  }
          }
        }   catch (IOException ex) {
          ex.printStackTrace();
        } 
        // apply chunker & pos tagger
        int count = 0;
        for (int i = 0; i < text.size(); ++i) {
        	//System.out.print(text.get(i).replaceAll(",", " ").toLowerCase()+", ");
          //  output+=text.get(i).replaceAll(",", "").toLowerCase()+", ".trim();

          //  System.out.println("\n" + Sentences[i]);
        	if(text.get(i).contains("method")||text.get(i).contains("technique")||text.get(i).contains("analysis")||text.get(i).contains("approach")||text.get(i).contains("algorithm")){
            	System.out.println(text.get(i));
        		count++;
            }
        	else{
            String[] tokens 
                = tokenizerFactory
                .tokenizer(text.get(i).toCharArray(),0,text.get(i).length())
                .tokenize();
            List<String> tokenList = Arrays.asList(tokens);
            Tagging<String> tagging = posTagger.tag(tokenList);
            //System.out.println();
            for (int j = 0; j < tokenList.size(); ++j){
            	//if (VERB_TAGS.contains(tagging.tag(j))){
            		
               // System.out.print(tokens[j] + "_" + tagging.tag(j) + " ");
                output+=tagging.token(j) + "_" + tagging.tag(j) + " ";
           	//	 System.out.print(sl.lemmatize1(tokens[j] )+"_" + tagging.tag(j) + " ");
            		// output+=sl.lemmatize1(tokens[j] )+" _" + tagging.tag(j) + " ";
            //	output+=sl.lemmatize1(tokens[j] )+" ";
            	}
            output+="\n";
       
               // output+=tokens[j] + "_" + tagging.tag(j) + " ";
           // output+=text.get(i).trim();
            //output+=sl.lemmatize(text.get(i).trim());
           output+=" "+text.get(i);
            output+="\n";
        
           // System.out.println();
           // System.out.println(text.get(i).trim());

            Chunking chunking = chunker.chunk(text.get(i));
            CharSequence cs = chunking.charSequence();
           // System.out.println("Chuncking----"+ chunking);

            for (Chunk chunk : chunking.chunkSet()) {
                String type = chunk.type();
                int start = chunk.start();
                int end = chunk.end();
                CharSequence text1 = cs.subSequence(start,end);
                if (type == "VP"){
               // System.out.println("  " + type + "(" + start + "," + end + ") " + text);
                	//output+="  " +text1+"\n";
                    // System.out.println(">>" + text1);

                }
            }
           
           for (Chunk chunk : chunking.chunkSet()) {
                String type = chunk.type();
                int start = chunk.start();
                int end = chunk.end();
                CharSequence text1 = cs.subSequence(start,end).toString();
                
                String textS = text1.toString();
                //if (type == "NP"&& (!textS.trim().endsWith("s"))&& !textS.contains("and")){
                if (type == "NP"&& !textS.contains("and")){
              // System.out.println("  " + type + "(" + start + "," + end + ") " + text1);
                	output+=textS+"\n";
                	//cuelist.add(e)
                    // System.out.println(">>" + text1);
                	k++;

                }
            }
           output+="\n";
            //System.out.println(text.get(i).trim());
        }
        }
        System.out.println(k);
        System.out.println(count);

      //  out1.print(output);
        out1.close();
    }

}