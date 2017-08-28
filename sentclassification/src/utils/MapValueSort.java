package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class MapValueSort {
	
	public MapValueSort()
	{}

	/** inner class to do soring of the map **/
	public static class ValueComparer implements Comparator {
		private Map  _data = null;
		public ValueComparer (Map data){
			super();
			_data = data;
		}

         public int compare(Object o1, Object o2) {
        	 Double e1 = (Double) _data.get(o1);
             Double e2 = (Double) _data.get(o2);
             return e1.compareTo(e2);
         }
	}

public	HashMap<String, Double> sortHashMap(HashMap<String, Double> getTextFrequencyMap){
	    Map<String, Double> tempMap = new HashMap<String, Double>();
	    for (String wsState : getTextFrequencyMap.keySet()){
	        tempMap.put(wsState,getTextFrequencyMap.get(wsState));
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
	}
	
	public static void main(String[] args){

		Map unsortedData = new HashMap();
		unsortedData.put("2", "DEF");
		unsortedData.put("1", "ABC");
		unsortedData.put("4", "ZXY");
		unsortedData.put("3", "BCD");

		SortedMap sortedData = new TreeMap(new MapValueSort.ValueComparer(unsortedData));

		printMap(unsortedData);

		sortedData.putAll(unsortedData);
		System.out.println();
		printMap(sortedData);
	}

	public  static void printMap(Map data) {
		for (Iterator iter = data.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			System.out.println("Value/key:"+data.get(key)+"/"+key);
		}
	}

}