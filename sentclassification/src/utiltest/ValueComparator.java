package utiltest;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ValueComparator implements Comparator<String>{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   HashMap<String,Integer> map = new HashMap<String,Integer>();
	        ValueComparator bvc =  new ValueComparator(map);
	        TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);

	       

	        System.out.println("unsorted map: "+map);

	       

	        System.out.println("results: "+sorted_map);
	    }

	
	Map<String, Integer> base;
    public ValueComparator(Map<String, Integer> map) {
        this.base = map;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
