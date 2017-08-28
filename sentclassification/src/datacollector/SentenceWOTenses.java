package datacollector;

import data.SentenceData;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.Normalizer;
import utils.Readdirectory;

public class SentenceWOTenses
  implements Serializable
{
  private LexicalizedParser lp;
  private static Map<String, Integer> verbMap;
  private static Set<String> sentVerbs ;
  public SentenceWOTenses(String grammerLocation)
  {
	  
	// VB, VBD, VBG, VBN, VBZ, VHG, VHI, VHN, VHZ,
    this.lp = new LexicalizedParser(grammerLocation);
    this.lp.setOptionFlags(new String[] { "-maxLength", "180", "-retainTmpSubcategories" });
    this.verbMap = new HashMap(4);
    this.verbMap.put("VBZ", Integer.valueOf(1));
    this.verbMap.put("VBP", Integer.valueOf(1));
    this.verbMap.put("VBD", Integer.valueOf(2));
    this.verbMap.put("VBG", Integer.valueOf(3));
    
  //  this.verbMap.put("VBN", Integer.valueOf(2));
  }

 /* public boolean[] parse(String sentence)
  {
    Tree parse = this.lp.apply(sentence);
    Set verbs = new HashSet();
    getAllVerbs(parse);
    boolean[] retArray = { false, false };
    if (verbs.contains(Integer.valueOf(1))) {
      retArray[0] = true;
    }
    if (verbs.contains(Integer.valueOf(2))) {
      retArray[1] = true;
    }
    return retArray;
  }*/

  public Tree  hasVerb(String sentence)
  {
    Tree parse = this.lp.apply(sentence);
   
		return parse;

    //return !verbs.isEmpty();
  }

  private static void getAllVerbs(Tree tree)
  {
	    int numChildren = tree.numChildren();

    String nodeString = tree.nodeString();
    if (nodeString.length() >= 3) {
      String verb = tree.nodeString().substring(0, 3);
    //  System.out.println(verbMap.keySet());
      if (verbMap.containsKey(verb.trim())) {
    	 // System.out.println(verb);
    	  sentVerbs.add(verb);
     }
    }
  for (int i = 0; i < numChildren; i++)
    getAllVerbs(tree.getChild(i));
  //    System.out.println(parse.getChild(i));
 }
   
  
  private void getAllVerbs(Tree tree, Set<Integer> verbs)
  {
    int numChildren = tree.numChildren();
    String nodeString = tree.nodeString();
    if (nodeString.length() >= 3) {
      String verb = tree.nodeString().substring(0, 3);
      if (this.verbMap.containsKey(verb)) {
        verbs.add(this.verbMap.get(verb));
      }
    }
    for (int i = 0; i < numChildren; i++)
      getAllVerbs(tree.getChild(i), verbs);
  }

  public static void main(String[] args) throws IOException
  {
	String output = "";
	Readdirectory rd = new Readdirectory();
	String [] dirList = rd.readDirectory("C:/Users/Hospice/annotated_data/PaperOneSentences");
	for (String file: dirList ){
		File class2verbs = new File ("C:/Users/Hospice/annotated_data/PaperOneSentencesWithOTense/"+file);

		class2verbs.createNewFile();
	
	//File class4verbs = new File ("C:/Users/Hospice/Downloads/Thesis/datacsvfromarticles/1471-2091-9-9dwithverbs.csv");
	//class4verbs.createNewFile();
	PrintWriter pw = new PrintWriter (class2verbs);
	//SentenceTenseGetter parser = new SentenceTenseGetter("C:/Users/Hospice/imrad_classifier/data/englishPCFG.ser.gz");
   // Normalizer n = new Normalizer(true, true, false, false, false, true, true);

	SentenceDataReader sdr = new SentenceDataReader();
    //List<SentenceData> sdList = SentenceDataReader.readSentenceDate("C:/Users/Hospice/New_workspace/sentclassification/6classes/6classes.csv");
    ArrayList<String> sList = loadCorpus("C:/Users/Hospice/annotated_data/PaperOneSentences/"+file);
    
    for (String sd : sList) {
    	//SentenceWOTenses parser = new SentenceWOTenses("C:/Users/Hospice/backup/imrad_classifier/data/englishPCFG.ser.gz");
    	
    	if (!sd.isEmpty()){
    	//sentVerbs = new HashSet<String>();
        
    	//verbMap = null;
    //	sentVerbs = null;
    //	parser.hasVerb(sd.getNormalizedSentence());
    	//getAllVerbs(parser.hasVerb(sd));
      //  System.out.println(sentVerbs);
    
    		String sent = sd;
   // System.out.println("///////////////////////////////////");
    
   /* if (verbMap.keySet().contains("VBG")){
    	sent = sent +","+true;
    	
    	
    	
    }
    else 
    	sent = sent +","+false;
    */
   // System.out.println("///////////////////////////////////");
    
    //System.out.println(sent);
    pw.append(sent+"\n");
    //output+=sent+"\n";
    }
  //  }
   // pw.print(output);
	}
    pw.close();

	}
  }
  @SuppressWarnings("resource")
public static ArrayList<String> loadCorpus(String file){
		//ArrayList<String> corpusArrayList = new ArrayList<String>();
		//File file = null;
	    BufferedReader inputStream = null;
	    ArrayList<String> corpusList = new ArrayList<String>();
		try {
		      //file = new File("C:/Users/Hospice/annotated_data/corpusout/MRCdata/MRCdataNorm.csv");
	      inputStream = new BufferedReader(new FileReader(new File(file)));
	      String line;
	      while ((line = inputStream.readLine()) != null) {
	    	 // System.out.println(line);
	        if ((line.length() == 0) || (line.trim().length() == 0))
	            continue;
	    	corpusList .add(line.trim());
	      }
	    } catch (FileNotFoundException ex) {
	      ex.printStackTrace();
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    } 
		return corpusList;
		
		
	}
}