package utils;

import java.io.File;
import java.io.IOException;

import com.aliasi.util.Files;

public class CorpusProportion {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String textFromFile = Files.readFromFile(new File("C:/Users/Hospice/combinesentences/tresholdstats3.txt"),"ISO-8859-1");
		String[] listFromFile = textFromFile.split("\n");
		//for (String line:listFromFile){
		for (int i =1; i< listFromFile.length; i++ ){
			
			if (listFromFile[i].startsWith("=====")||listFromFile[i].startsWith("testNumber")&&(listFromFile[i]!=null))
				continue;
			//System.out.println(line);
				
			String lineValues[] = listFromFile[i].split("\t");
			if (Integer.parseInt(lineValues[1]) > 0 ){
				//System.out.println(lineValues	[1]+"\t");
				//System.out.println(lineValues[0]+"\t"+lineValues[1]+"\t"+((double)Integer.parseInt(lineValues[1]) / ((double)Integer.parseInt(lineValues[1])+(double)Integer.parseInt(lineValues[2])))+"\t"+((double)Integer.parseInt(lineValues[3]) / ((double)Integer.parseInt(lineValues[3])+(double)Integer.parseInt(lineValues[4])))+"\t"+((double)Integer.parseInt(lineValues[2]) / ((double)Integer.parseInt(lineValues[2])+(double)Integer.parseInt(lineValues[3]))));
				//System.out.println(((double)Integer.parseInt(lineValues[3]) / ((double)Integer.parseInt(lineValues[3])+(double)Integer.parseInt(lineValues[4]))));
				System.out.println(lineValues[0]+"\t"+((double)Integer.parseInt(lineValues[2]) / ((double)Integer.parseInt(lineValues[2])+(double)Integer.parseInt(lineValues[3]))));
			}
		//}
			i+=6;
		}
	}

}
