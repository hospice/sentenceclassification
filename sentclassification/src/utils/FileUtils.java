package utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.TreeMap;
import java.util.TreeSet;

public class FileUtils {
	TreeMap<String, String> abrevDict = null;
	boolean abrevDictLoaded = false;
	TreeSet<String> abrevWords = null;

    private static FileUtils instance;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		instanceFactory().wordExpand("FNR");
	}
    public static FileUtils instanceFactory(){
    	if (instance == null)
    		instance = new FileUtils();
		return instance;
    	
    }
    public  void loadAbrevDictionary() throws IOException{
    	if (this.abrevDict != null) {
    	      return;
    	    }
    	
    	try {
			//InputStream swStream = getClass().getResourceAsStream("C:/Users/Hospice/resources/New_workspace/LDATests/linkagedata/abreviations.txt");
			
    		FileReader fileReader = new FileReader(new File("C:/Users/Hospice/resources/New_workspace/LDATests/linkagedata/abreviations.txt"));
    		
    		BufferedReader swFile = new BufferedReader(fileReader);
			String abrevLine = null;
			this.abrevDict = new TreeMap<String, String>();
			this.abrevWords = new TreeSet<String>();
			while ((abrevLine = swFile.readLine()) != null) {
			//	System.out.println(abrevLine);
			  this.abrevDict.put(abrevLine.split("\t")[0], abrevLine.split("\t")[1]);
			  this.abrevWords.add(abrevLine.split("\t")[0]);
			}
			swFile.close();
			this.abrevDictLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public boolean isAbrevWord(String word) throws IOException
    {
      if (!this.abrevDictLoaded) {
    	  loadAbrevDictionary();
      }
      return this.abrevWords.contains(word);
    }
    
    public String wordExpand(String word) throws IOException{
    	if (isAbrevWord(word)){
    		word = abrevDict.get(word);
    	}
    	System.out.println(word);
		return word;
    }
    public static String sentExpand(String sent){
    	
    	String[] sentence = sent.split("\\s+");
    	String sentExpand = "";
    	for(String s:sentence){
    		sentExpand+=s+" ";
    	}
		return sentExpand;
    	
    }
	
	
}
