package utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.TreeSet;

	/**
	 * @param args
	 */
	public class ValueComparator implements Comparator<Object> {

	  Map<String, Double> base;
	  public ValueComparator(Map<String, Double> base) {
	      this.base = base;
	  }

	  public int compare(Object a, Object b) {

	    if((Double)base.get(a).doubleValue() < (Double)base.get(b).doubleValue()) {
	      return 1;
	    } else if((Double)base.get(a).doubleValue() == (Double)base.get(b).doubleValue()) {
	      return 0;
	    } else {
	      return -1;
	    }
	  }
	
	/*HashMap<String, Double> sortHashMap(HashMap<String, Double> input){
	    Map<String, Double> tempMap = new HashMap<String, Double>();
	    for (String wsState : input.keySet()){
	        tempMap.put(wsState,input.get(wsState));
	    }

	    List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
	    List<Double> mapValues = new ArrayList<Double>(tempMap.values());
	    HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
	    TreeSet<Double> sortedSet = new TreeSet<Double>(mapValues);
	    Object[] sortedArray = sortedSet.toArray();
	    int size = sortedArray.length;
	    for (int i=0; i<size; i++){
	        sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), 
	                      (Double)sortedArray[i]);
	    }
	    return sortedMap;
	}*/
	
	}
