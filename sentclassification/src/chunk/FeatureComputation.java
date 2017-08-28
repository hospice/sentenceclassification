package chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeatureComputation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public Map<String, ArrayList<String>> getFeatureMap(String nounPhrase){
		Map<String, ArrayList<String>> featureMap = new HashMap<String, ArrayList<String>>();
		featureMap.put(nounPhrase, getFeatures(nounPhrase));
		return featureMap;
		
	}
    public ArrayList<String> getFeatures(String nounPhrase){
    	ArrayList<String> featureList = new ArrayList<String>();
    	
    	//if ()
    	
		return featureList;
    	
    	
    }
    public boolean isSubject(String nounPhrase){
    	
		return false;
    	
    }
    public boolean isObject(String nounPhrase){
		return false;
    	
    	
    }
    
    public boolean hasPrepc_for(String nounPhrase){
		return false;
    	
    }
    
   public boolean hasAmod(String nounPhrase){
	return false;
	   
   }
}
