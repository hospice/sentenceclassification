package wordnet;

import java.util.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class WordNet {
	
    public String sWords;
	
	public String getWords(){
		//this.sWords = sWords;
		return this.sWords;
		
	}
	public void setWords(String sWords){
		this.sWords = sWords;
		//return this.sWords;
		
	}
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private static final Pattern WORD_TZ = Pattern.compile("^[t-z]");
	private static final Pattern WORD_QS = Pattern.compile("^[q-s]");
	private static final Pattern WORD_MP = Pattern.compile("^[m-p]");
	private static final Pattern WORD_HL = Pattern.compile("^[h-l]");
	private static final Pattern WORD_EG = Pattern.compile("^[e-g]");
	private static final Pattern WORD_CD = Pattern.compile("^[c-d]");
	//private static final Pattern WORD_AB = Pattern.compile("^[a-b]");
	
	
public List<String> dTable(String word)throws Exception{
		//System.out.println("We start-----");
		List<String> wordList = new ArrayList<String>();
		if (WORD_TZ.matcher(word).find()){
			wordList.add("wn_words_t_z");
		}
		if (WORD_QS.matcher(word).find()){
			wordList.add("wn_words_q_s");
		}
		if (WORD_MP.matcher(word).find()){
			wordList.add("wn_words_m_p");
		}
		if (WORD_HL.matcher(word).find()){
			wordList.add("wn_words_h_l");
		}
		if (WORD_EG.matcher(word).find()){
			wordList.add("wn_words_e_g");
		}
		if (WORD_CD.matcher(word).find()){
			wordList.add("wn_words_c_d");
		}
		if (Pattern.compile("^[m-p]").matcher(word).find()){
			//System.out.println("OK");
			wordList.add("wn_words_m_p");
		}
		/*
		if (WORD_AB.matcher(word).matches()){
			wordList.add("wn_words_t_z");
		}*/
		else 
			wordList.add("wn_words_a_b");	
		
	    return wordList;
	}

    public boolean inDictTable(String word) throws ClassNotFoundException, SQLException{
    	word = word.toLowerCase();
    	String sw = word.substring(0, 2);
    	//System.out.println(sw);
    	String command = "select wnc_words from wn_words_all where " +
        "wnc_start_letters <= " + "'"+sw+"'" +"and" + "'"+sw+"'" + 
        "<= wnc_end_letters";
    	Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=Papa1987");

		// Statemnet allow to issue SQL queries to the database
		statement = connect.createStatement();
		resultSet = statement .executeQuery(command);

		while (resultSet.next()) {
			String wordValue = resultSet.getString("wnc_words");
			String[] synSplit = wordValue.split("\\s+");
			//System.out.println(wordValue);
			for (int i = 0; i < synSplit.length; i++){
				if (synSplit[i].equalsIgnoreCase(word)){
					//System.out.println(synSplit[i]);
					return true;
				}
				
			}

			
		}
		//String[] words = word.split("_");
		//word = words[0];
    	return false;
    }
    
    public boolean in_dictionary(String word){
    	
    	return false;
    }
	
	
	public String getSynsetWords(String synset)throws Exception{
		String words = "";
		try{
		// This will load the MySql driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		
		connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=Papa1987");

		// Statemnet allow to issue SQL queries to the database
		statement = connect.createStatement();
		
		
		
		
		String[] syn = synset.split("\\s+");
		
		String word ="";
		for (int k=0; k< syn.length;k++){
			String[] synSplit = syn[k].split("_");
			
			//System.out.println(synSplit[0]);
			String queryString = "'"+synSplit[0]+"'";
			resultSet = statement.executeQuery("select * from TM.WN_SYNSETS where wnc_synset = "+ queryString);
			//String word = resultSet.getString("wnc_words");
			
			while (resultSet.next() ) {
				//if(resultSet.getString("wnc_synset")== 'm00012817'){
			 word = resultSet.getString("wnc_words");
			words += word + " ";
			
				//}
			}
			//System.out.println(word);
			
		}
		//System.out.println(words);
		return words;

		} catch (Exception e) {
			throw e;
		} finally{
		close();
		
		}
		//System.out.println(words);
		

	}
	public String sWords1 = "";
	
	public String synsets = "";
	public Map<String, String> similarWords(String word) throws Exception{
		
		Map<String, String> similar_words = new HashMap<String, String>();
		word = word.toLowerCase();
		String[] words = word.split("_");
		word = words[0];
		Map<String, String> similarWords = new HashMap<String, String>();
		if (inDictTable(word)){
			
			String command = "select wnc_synsets from "+ dTable(word).get(0)+ " where wnc_word = " + "'" + word + "'";
			try {
				Class.forName("com.mysql.jdbc.Driver");
			
			// Setup the connection with the DB
			//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=Papa1987");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=Papa1987");

			// Statemnet allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement .executeQuery(command);
			while(resultSet.next()){
				
				 synsets = resultSet.getString("wnc_synsets");
				//System.out.println(synsets);
				// getSynsetWords(synsets);
					//System.out.println(getSynsetWords(synsets));

				//setWords(sWordsO);
				//System.out.println(sWords);
				//System.out.println(sWords1);

                
				
			}
				
			
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			close();
		}else{
			Map<String, String> bWords = new HashMap<String, String>();
			bWords = baseWords(word);
			Collection<String> c = bWords.keySet();
			Iterator<String> itr = c.iterator();
			while(itr.hasNext()){
				String commands = "select wnc_synsets from " + dTable(word).get(0)+ " where wnc_word = " +"'" +itr.next()+"'";
				resultSet = statement .executeQuery(commands);
				while(resultSet.next()){
					synsets = resultSet.getString("wnc_synsets");
					//sWords = getSynsetWords(synsets);
				}
			}
			close();
		}
		//String[] sWordsSplit = sWords.split("\\s+");
		//for(int i=0; i< sWordsSplit.length; i++){
		//	System.out.println(sWordsSplit[i]);

		//	similar_words = similarWords(sWordsSplit[i]);
		//}
		//System.out.println("Hello!");
		//System.out.println(getWords());*/
		 similar_words.put(word, getSynsetWords(synsets));
		 String synset = "";
		 ArrayList<String> al = new ArrayList<String>();
		// add elements to al, including duplicates
		HashSet<String> hs = new HashSet<String>();
		String[] sw = getSynsetWords(synsets).split(" ");
		for (int i =0; i < sw.length; i++){
			
		hs.add(sw[i]);
		}
		for (Iterator<String> it = hs.iterator();  it.hasNext();){
			synset += it.next()+ " ";
			
		}
		//similar_words.values().clear();
		
		System.out.println(hs);
		similarWords.put(word, synset);
		return similarWords;
		
			//return similar_words;
		
		
		
	}
	
	
	@Test
	public void vois() throws Exception {
	Map <String, String> smilarWord = new HashMap<String, String>();
	smilarWord = similarWords("jail");
    System.out.println(smilarWord.values());
	
}
	
	//Return a list of base words for a given word
	public Map<String, String> baseWords(String word) throws Exception{
		
		Map<String, String> done = new HashMap<String, String>();
	    String command = "select wnc_type, wnc_base_word from wn_exc_words where " +
        " wnc_excl_word = " + "'" + word + "'";
	   // System.out.println(word);
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Setup the connection with the DB
		//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=Papa1987");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=Papa1987");

		// Statemnet allow to issue SQL queries to the database
		statement = connect.createStatement();
		resultSet = statement.executeQuery(command);
		String type ="";
		while (resultSet.next() ) {
			 type += resultSet.getString("wnc_type");
			  //  System.out.println(type);

			String baseWord = resultSet.getString("wnc_base_word");
			//System.out.println(baseWord);
			
			if (baseWord.matches("\\s"))
			{
				String[] baseWords = baseWord.split("\\s+");
				for (int i =0; i< baseWords.length; i++ ){
					
					done.put(baseWords[i], type);
					
				}
			}
			else {
				done.put(baseWord, type);
			}
		}

		String tWord ="";
		
		String spatt ="s__n      ses_s_n xes_x_n zes_z_n  ches_ch_n shes_sh_n" +
				" men_man_n ies_y_n s__v    ies_y_v  es_e_v    es__v " +
				" ed_e_v    ed__v   ing__v   ing_e_v er__a     est__a " +
				"er_e_a    est_e_a";
		String[] spattS = spatt.split("\\s+");
        for(int i=0; i<spattS.length; i++){
        	String[] SplitS = spattS[i].split("_");
        	 String type1 = SplitS[2];
        	// System.out.println(type1);
        	
     		//System.out.println(wordPos("wish"));

        	//System.out.println("princesses".replace("ses", "s"));
        	tWord = word;
        	if (endsWith(word, SplitS[0])&&inDictTable(word.replaceAll(SplitS[0], SplitS[1]))&&wordPos(word.replaceAll(SplitS[0], SplitS[1])).contains(type1)){
        		//if (inDictTable(word.replaceAll(SplitS[0], SplitS[1]))&&wordPos(word.replaceAll(SplitS[0], SplitS[1]))==type1 ){
        			//System.out.println(word.replaceAll(SplitS[0], SplitS[1]));
        		//System.out.println(word.replaceAll(SplitS[0], SplitS[1])+" "+ wordPos(wordPos(word.replaceAll(SplitS[0], SplitS[1]))));
           	    
        		done.put(word.replaceAll(SplitS[0], SplitS[1]), wordPos(wordPos(word.replaceAll(SplitS[0], SplitS[1]))));

        		//System.out.println(wordPos("wishes"));
        		//}

        	}
        	
        	
        	/*if ((endsWith(word, SplitS[0])) && inDictTable(word) && tWord.matches(type1)) {
            	//System.out.println(SplitS[2]);
        		String wordMatch = word.replaceAll(SplitS[0], SplitS[1]);
        		//System.out.println(wordMatch);
        		done.put(wordMatch, type1);
        		
        	}*/
    	    

        }
        //for (int k =0; k< done.size(); k++)
	   // System.out.println(done.keySet());
        //System.out.println(done.keySet());
	    //System.out.println(done.values());


		return done;
		
	}
	public String  wordPos(String word) throws Exception {
		
		String command = "select wnc_pos from " + dTable(word).get(0) +" where wnc_word = "	+"'" + word + "'";	
	  //  System.out.println("This is word  " +dTable(word).get(0));
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Setup the connection with the DB
		//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=Papa1987");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=Papa1987");

		// Statemnet allow to issue SQL queries to the database
		statement = connect.createStatement();
		resultSet = statement .executeQuery(command);
		String types = "";

		while (resultSet.next()){
			
			String type = resultSet.getString("wnc_pos");
			types+=type;
			}
		
		return types;
		
	}
	public static boolean endsWith(String str, String suffix) {
	      return endsWith(str, suffix, false);
	  }
	private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
	      if (str == null || suffix == null) {
	          return (str == null && suffix == null);
	      }
	      if (suffix.length() > str.length()) {
	          return false;
	      }
	      int strOffset = str.length() - suffix.length();
	      return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
	  }
	 public static boolean isEmpty(String str) {
	      return str == null || str.length() == 0;
	  }
	 

		// You need to close the resultSet
		private void close() {
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {

			}
		}

@Test
	public void in_dict_table() throws Exception{
	
		baseWords("failures");
}

//@Test
	public void wordNetTest() throws Exception {
	WordNet  wn = new WordNet();
		//String synset = "n05669034_7 n00414179_6 n03473966_1 n00947923_0 n03474167_0 n13490909_0";
	String words=	wn.getSynsetWords("n05669034_7 n00414179_6 n03473966_1 n00947923_0 n03474167_0 n13490909_0");
	       System.out.print(words);
	}
}
