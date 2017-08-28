package datacollector;

import data.SentenceData;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SentenceTenseGetter
  implements Serializable
{
  private LexicalizedParser lp;
  private static Map<String, Integer> verbMap;
  private static Set<String> sentVerbs ;
  public SentenceTenseGetter(String grammerLocation)
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
	File class4verbs = new File ("C:/Users/Hospice/New_workspace/sentclassification/6classes/6class4verbs.csv");
	class4verbs.createNewFile();
	PrintWriter pw = new PrintWriter (class4verbs);
	//SentenceTenseGetter parser = new SentenceTenseGetter("C:/Users/Hospice/imrad_classifier/data/englishPCFG.ser.gz");

	SentenceDataReader sdr = new SentenceDataReader();
    List<SentenceData> sdList = SentenceDataReader.readSentenceDate("C:/Users/Hospice/New_workspace/sentclassification/6classes/6classes.csv");
    for (SentenceData sd : sdList) {
    	SentenceTenseGetter parser = new SentenceTenseGetter("C:/Users/Hospice/backup/imrad_classifier/data/englishPCFG.ser.gz");

    	if (!sd.getNormalizedSentence().isEmpty()){
    	sentVerbs = new HashSet<String>();
        
    	//verbMap = null;
    //	sentVerbs = null;
    //	parser.hasVerb(sd.getNormalizedSentence());
    	getAllVerbs(parser.hasVerb(sd.getNormalizedSentence()));
      //  System.out.println(sentVerbs);
    
    String sent = sd.getNormalizedSentence();
   // System.out.println("///////////////////////////////////");
    if (sentVerbs.contains("VBZ")){
    	sent = sent +","+true;   	
    	
    }
    else 
    	sent = sent +","+false;
    
    if (sentVerbs.contains("VBP")){
    	sent = sent +","+true;
    	
    	
    	
    }
    else 
    	sent = sent +","+false;
    if (verbMap.keySet().contains("VBD")){
    	sent = sent +","+true;
    	
    	
    	
    }
    else 
    	sent = sent +","+false;
    
    if (verbMap.keySet().contains("VBG")){
    	sent = sent +","+true;
    	
    	
    	
    }
    else 
    	sent = sent +","+false;
    
    sent+=","+ sd.getAssignedCategory();
   // System.out.println("///////////////////////////////////");
    
    //System.out.println(sent);
    pw.append(sent+"\n");
    //output+=sent+"\n";
    }
    }
   // pw.print(output);
    pw.close();
  }
}