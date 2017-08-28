package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A more robust copy utiliy, with some user input and file access
 * checking. The are of course further enhancments that could be made,
 * e.g. showing more user friendly error message, rather than the
 * stack trace.   
 * 
 * @author Havard Rast Blok
 *
 */
public class CopyFile {
  
  private File src;
  
  private File dest;
  
  public CopyFile(File src, File dest) {
    this.src = src;
    this.dest = dest;
    
    if(!src.exists() || !src.canRead() || !src.isFile()) {
      throw new IllegalArgumentException("Could not access or read file "+src);
    }
    
    if(dest.exists()) {
      throw new IllegalArgumentException("Destination file already exists: "+dest);
    }
  }
  
  /**
   * Copy the specified file of this class to the specifed destination.
   * 
   * @throws IOException if any open, create, read or write error occurs.
   */
  public void copy() throws IOException {
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
    
    int b;
    while( (b = in.read()) != -1) {
      out.write(b);
    }
    
    in.close();
    out.close();
  }
  
  /**
   * This utility takes two arguments, destination and host:
   * java CopyFile <source file> <destination file>
 * @throws IOException 
   * 
   */
  public static void main(String[] args) throws IOException {
	  int t = 0;
   /* if(args == null || args.length != 2) {
      System.out.println("Usage: java CopyFile <source file> <destination file>");
      System.exit(1);
    }*/
    
    Readdirectory readDir = new Readdirectory();
	//String[] files = readDir.readDirectory("C:/Users/Hospice_Houngbo/Downloads/articles/BMC_FTP/content/articles/");
    
    //String[] files = {"1471-2296-9-53.xml", "1471-2296-9-58.xml", "1471-2458-10-53.xml", "1471-2458-8-28.xml", "1471-2458-9-304.xml", "1471-2318-9-38.xml", "1471-2458-8-330.xml", "1471-2458-7-176.xml", "1471-2458-6-138.xml", "1471-2458-10-57.xml", "1471-2377-9-3.xml", "1471-2296-11-41.xml", "1471-2458-7-3.xml", "1471-2458-6-185.xml", "1471-2377-6-2.xml", "1471-2458-9-114.xml", "1471-2458-8-200.xml", "1471-2288-3-17.xml", "1471-2458-6-243.xml", "1471-2458-4-2.xml", "1471-2318-10-50.xml", "1471-2458-7-182.xml", "1471-2458-7-112.xml", "1471-2261-8-6.xml", "1471-2458-7-186.xml", "1471-2458-9-167.xml", "1471-2288-10-80.xml", "1471-2458-2-4.xml", "1471-2296-11-68.xml", "1471-2458-8-88.xml", "1471-2458-8-86.xml", "1471-2458-7-126.xml", "1471-2296-5-14.xml", "1471-2296-11-69.xml", "1471-2458-5-23.xml", "1471-2288-10-99.xml", "1471-2296-6-20.xml", "1471-2458-7-351.xml", "1471-2458-9-149.xml", "1471-2377-10-42.xml", "1471-2288-11-18.xml", "1471-2458-2-16.xml", "1471-2318-4-1.xml", "1471-2458-6-280.xml", "1471-2458-6-206.xml", "1471-2288-7-51.xml", "1471-2318-11-21.xml", "1471-2458-4-12.xml", "1471-2458-6-255.xml", "1471-2458-10-87.xml", "1471-2458-10-119.xml", "1471-2296-11-48.xml", "1471-2458-8-313.xml", "1471-2458-8-392.xml", "1471-2458-9-426.xml", "1471-2458-8-54.xml", "1471-2296-7-43.xml", "1471-2318-7-14.xml", "1471-2458-3-32.xml", "1471-2296-7-72.xml", "1471-2288-7-6.xml", "1471-2288-6-57.xml", "1471-2458-8-282.xml", "1471-2458-7-255.xml", "1471-2458-6-30.xml", "1471-2458-7-138.xml", "1471-2458-8-190.xml", "1471-2296-11-44.xml", "1471-2318-10-64.xml", "1471-2458-7-106.xml", "1471-2458-6-247.xml", "1471-2458-10-74.xml", "1471-2296-10-59.xml", "1471-2458-3-15.xml", "1471-2458-6-11.xml", "1471-2318-10-20.xml", "1471-2261-11-16.xml", "1471-2458-9-436.xml"};
    	//getFileList("C:/Users/hospice/Downloads/XMLParser/fileout/linkagenodelistAgain.csv");
	//BufferedReader br = new BufferedReader(new FileReader(new File ("C:/Users/Hospice/annotated_data/corpusout/MRCdata/MRCSelf/Getcitationsentences1471-2091biochemistryreducedSVMPartlabelled.txt")));
   // String[] files = br.readLine().split(", ");
    String[] files = getFileList("C:/Users/Hospice/annotated_data/corpusout/MRCdata/MRCSelf/Getcitationsentences1471-2091biochemistryreducedSVMPartlabelled.txt");
    //C:\Users\hospice\Downloads\XMLParser\fileout\linkagenodelistAgain.csv
	for (String fXmlFile : files){
		//if (fXmlFile.trim().startsWith("1471-")){
		t++;
    CopyFile cp = new CopyFile(new File("C:/Users/Hospice/BMCArticles/"+fXmlFile), new File("C:/Users/Hospice/articlescitedfilesbiochem/"+t+"_"+fXmlFile));
    try {
      cp.copy();
    } catch (IOException e) {
      System.out.println("An error occured while coping the file: ");
      e.printStackTrace();
      System.exit(1);
    }
		//}
  }
  }
  //Read the list of files in an array
 
public static String[] getFileList(String fileList) throws IOException{
	  String[] fileListinArray = {};
	  File inFile = new File(fileList);
	  List <String> files = new ArrayList<String>();
	  
	  BufferedReader in = new BufferedReader(new FileReader(inFile ));
	  String line = "";
	  int k =0;
	  while((line = in.readLine()) != null){
		  files.add(line.trim().split("\\s+")[0]);
		  //fileListinArray[k] = line.trim();
		  System.out.println(line.trim());
		  k++;
	  }
	  String listofiles = "";
	  for (String s: files)
		  listofiles += s + "\t";
	  fileListinArray= listofiles.split("\t");
	  return fileListinArray;
	  
  }
  

}