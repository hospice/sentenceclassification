package chunk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MethodMach {

	/**
	 * @param args
	 */
	 static Set<String> methodPhrase = new HashSet<String>();
	@SuppressWarnings("null")
	public static void main(String[] args) {
		//Set<String> methodPhrase = null;

		   File file = null;
		   File methofile = null;
		   BufferedReader inputStream = null;
		   try {
		     file = new File("C:/Users/Hospice/resources/New_workspace/sentclassification/journal/methodsentences/BMC_MethodsFirstSentencereducedNPModified.txt");
		     inputStream = new BufferedReader(new FileReader(file));
		     String line;
		     while ((line = inputStream.readLine()) != null) {
		   	// System.out.println(line);
		       
		       methodPhrase.add(line.trim());
		     }
		   } catch (FileNotFoundException ex) {
		     ex.printStackTrace();
		   }  catch (IOException ex) {
		     ex.printStackTrace();
		   } finally {
		     if (inputStream != null)
		       try {
		         inputStream.close();
		       } catch (IOException ex) {
		         ex.printStackTrace();
		       }
		   }
		   methofile = null;
		   BufferedReader inputStream2 = null;
		   try {
			   methofile = new File("C:/Users/Hospice/resources/New_workspace/sentclassification/journal/BMC_Methods.txt");
		     inputStream2 = new BufferedReader(new FileReader(methofile));
		     String line;
		     while ((line = inputStream2.readLine()) != null) {
		   	// System.out.println(methodPhrase);
		       for (String method:methodPhrase){
		    	   if (line.contains(method)){
		    		   System.out.println(line);
		    		   System.out.println(inputStream2.readLine());
		    	   }
		       }
		     }
		   } catch (FileNotFoundException ex) {
		     ex.printStackTrace();
		   }  catch (IOException ex) {
		     ex.printStackTrace();
		   } finally {
		     if (inputStream2 != null)
		       try {
		         inputStream2.close();
		       } catch (IOException ex) {
		         ex.printStackTrace();
		       }
		   }
	}

}
