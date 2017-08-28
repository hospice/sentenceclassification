package umls;


import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;


public class UMLS {
	public UMLS(){
		
	}
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public String getCUI(String word) throws ClassNotFoundException, SQLException{
		String cui ="";
		String command = "select distinct CUI from mrconso where STR =" + "'"+word+"'" ;
    	Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/umls2010?"+"user=root&password=");

		// Statemnet allow to issue SQL queries to the database
		statement = connect.createStatement();
		resultSet = statement .executeQuery(command);
		while(resultSet.next()){
			cui += resultSet.getString("CUI") + " ";
		}
		System.out.println(cui);
		return cui;
		
	}
	public String getAUI(String word) throws ClassNotFoundException, SQLException{
		String aui ="";
		String command = "select distinct AUI from mrconso where STR =" + "'"+word+"'" ;
    	Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/umls2010?"+"user=root&password=");

		// Statemnet allow to issue SQL queries to the database
		statement = connect.createStatement();
		resultSet = statement .executeQuery(command);
		while(resultSet.next()){
			aui += resultSet.getString("AUI") + " ";
		}
		//System.out.println(aui);
		return aui;
		
	}
	public String getDef(String word) throws ClassNotFoundException, SQLException{
		String[] cuis = getCUI(word).split(" ");
		for (String cui:cuis){
			String command = "select  DEF from mrdef where CUI =" + "'"+cui+"'" ;
	    	Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/umls2010?"+"user=root&password=");

			// Statemnet allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement .executeQuery(command);
			while(resultSet.next()){
				System.out.println(resultSet.getString("DEF") );
			}
		}
		return word;
		
		
	}
	
	public String getDefWithAUI(String word) throws ClassNotFoundException, SQLException{
		String[] auis = getAUI(word).split(" ");
		for (String aui:auis){
			String command = "select  DEF from mrdef where AUI =" + "'"+aui+"'" ;
	    	Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/umls2010?"+"user=root&password=");

			// Statemnet allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement .executeQuery(command);
			while(resultSet.next()){
				System.out.println(resultSet.getString("DEF") );
			}
		}
		return word;
		
		
	}
	public String getWordSet(String word) throws ClassNotFoundException, SQLException{
		String[] cuis = getCUI(word).split(" ");
		for (String cui:cuis){
			String command = "select distinct STR from mrconso where CUI =" + "'"+cui+"'" ;
	    	Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/umls2010?"+"user=root&password=");

			// Statemnet allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement .executeQuery(command);
			while(resultSet.next()){
				System.out.println(resultSet.getString("STR") );
			}
		}
		return word;
		
		
	}
	
	public String getWordWithCui(String cui) throws ClassNotFoundException, SQLException{
		String command = "select  STR from mrconso where CUI =" + "'"+cui+"'" ;
    	Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/umls2010?"+"user=root&password=");

		// Statemnet allow to issue SQL queries to the database
		statement = connect.createStatement();
		resultSet = statement .executeQuery(command);
		while(resultSet.next()){
			System.out.println(resultSet.getString("STR") );
		}
		return cui;
	}
		
		public String getWordWithAui(String aui) throws ClassNotFoundException, SQLException{
			String command = "select  STR from mrconso where aui =" + "'"+aui+"'" ;
	    	Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/umls2010?"+"user=root&password=");

			// Statemnet allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement .executeQuery(command);
			while(resultSet.next()){
				System.out.println(resultSet.getString("STR") );
			}
			return aui;
	}
	public String getConcept(String word) throws ClassNotFoundException, SQLException{
		String concept = "";
		String[] cuis = getCUI(word).split(" +");
		for (String cui:cuis){
			String command = "select  STY from mrsty where CUI =" + "'"+cui+"'" ;
	    	Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/umls2010?"+"user=root&password=");

			// Statemnet allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement .executeQuery(command);
			while(resultSet.next()){
				//System.out.println(resultSet.getString("STY") );
				concept = resultSet.getString("STY");
				
			}
		}
		return concept;
	}
	
public String getCategory(String word) throws ClassNotFoundException, SQLException{
	String category = "";
		String[] cuis = getCUI(word).split(" ");
		for (String cui:cuis){
			String command = "select  STY from mrsty where CUI =" + "'"+cui+"'" ;
	    	Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			//connect = DriverManager.getConnection("jdbc:mysql://localhost/tm?"+"user=root&password=");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/umls2010?"+"user=root&password=");

			// Statemnet allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement .executeQuery(command);
			
			while(resultSet.next()){
				//System.out.println(resultSet.getString("STY") );
				category += resultSet.getString("STY") +"/n";
			}
		}
		return category;
	}
	//@Test
	public void testCui() throws ClassNotFoundException, SQLException{
		getCUI("dna");
	}
	
	//@Test
	public void testDef() throws ClassNotFoundException, SQLException{
		
		String text = "Soluble hemojuvelin have been detected in serum and blood on human and mouse 40 and 50 kDa peptides by immunoaffinity purification.";
		String[] textArray = text.split(" ");
		
		for (String s: textArray ){
			System.out.print(s+":\t");
			getDef(s.trim());
		    System.out.println();
		}
	}
	@Test
   public void testDefWithAUI() throws ClassNotFoundException, SQLException{
		
		String text = "Soluble HJV have been detected in serum and blood on human and mouse 40 and 50 kDa peptides by immunoaffinity purification.";
		String[] textArray = text.split(" ");
		
		for (String s: textArray ){
			System.out.print(s+":\t");
			getDefWithAUI(s.trim());
		    System.out.println();
		}
	}
	//@Test
	public void testgetWordSet() throws ClassNotFoundException, SQLException{
		getWordSet("Lung Cancer");
	}
	
	//@Test
	public void getWordWithCui() throws ClassNotFoundException, SQLException{
		getWordWithCui("C2348744");
	}
	
	//@Test
	public void getWordWithAui() throws ClassNotFoundException, SQLException{
		getWordWithAui("A15567406");
	}
	//@Test
    public void getConcept() throws ClassNotFoundException, SQLException{
    	
    	getConcept("dna");
    }

}
