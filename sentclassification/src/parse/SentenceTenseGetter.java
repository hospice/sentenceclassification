package parse;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SentenceTenseGetter
  implements Serializable
{
  private LexicalizedParser lp;
  private Map<String, Integer> verbMap;
  
  public SentenceTenseGetter(String grammerLocation)
  {
    this.lp = new LexicalizedParser(grammerLocation);
    this.lp.setOptionFlags(new String[] { "-maxLength", "180", "-retainTmpSubcategories" });
    this.verbMap = new HashMap(3);
    this.verbMap.put("VBZ", Integer.valueOf(1));
    this.verbMap.put("VBP", Integer.valueOf(1));
    this.verbMap.put("VBD", Integer.valueOf(2));
  }
  
  public boolean[] parse(String sentence)
  {
    Tree parse = this.lp.apply(sentence);
    Set<Integer> verbs = new HashSet();
    getAllVerbs(parse, verbs);
    boolean[] retArray = { false, false };
    if (verbs.contains(Integer.valueOf(1))) {
      retArray[0] = true;
    }
    if (verbs.contains(Integer.valueOf(2))) {
      retArray[1] = true;
    }
   // System.out.println(retArray[0]+"\t"+retArray[1]);
    return retArray;
  }
  
  public boolean hasVerb(String sentence)
  {
    Tree parse = this.lp.apply(sentence);
    //parse.pennPrint();
    System.out.println("Parsed tree: " + parse.toString().replaceAll("[0-9\\]\\[\\.]", ""));
    Set<Integer> verbs = new HashSet();
    getAllVerbs(parse, verbs);
    if (verbs.isEmpty()) {
      return false;
    }
    return true;
  }
  
  private void getAllVerbs(Tree tree, Set<Integer> verbs)
  {
    int numChildren = tree.numChildren();
    String nodeString = tree.nodeString();
    if (nodeString.length() >= 3)
    {
      String verb = tree.nodeString().substring(0, 3);
      if (this.verbMap.containsKey(verb)) {
        verbs.add(this.verbMap.get(verb));
      }
    }
    for (int i = 0; i < numChildren; i++) {
      getAllVerbs(tree.getChild(i), verbs);
      //tree.getChild(i).pennPrint();
     // if (tree.getChild(i).label().toString().equals("VP"))
      //System.out.println(tree.getChild(i).getLeaves().toArray()[1]);
    }
  }
  
  public static void main(String[] args)
  {
	String sentence = "Microarray and other high-throughput technologies have led to an explosion in the rate of molecular abundance data generated in the last decade."; 
    SentenceTenseGetter parser = new SentenceTenseGetter("C:/Users/Hospice/backup/imrad_classifier/data/englishPCFG.ser.gz");
   // System.out.println(sentence+", "+parser.parse(sentence)[0]+","+parser.parse(sentence)[1]);

    //boolean hasVerb = parser.hasVerb("Microarray and other high-throughput technologies have led to an explosion in the rate of molecular abundance data generated in the last decade.");
   // System.out.println("Verb: " + hasVerb);
  }
}
