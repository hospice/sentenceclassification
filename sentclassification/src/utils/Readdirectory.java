package utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;


public class Readdirectory {
	public Readdirectory(){
		
	}
     public String[] readDirectory(String dir){
    	 File file = new File(dir);

    	 String[] children = file.list();
    	 if (children == null) {
    	     // Either dir does not exist or is not a directory
    	 } else {
    	     for (int i=0; i<children.length; i++) {
    	         // Get filename of file or directory
    	         String filename = children[i];
    	     }
    	 }

    	 // It is also possible to filter the list of returned files.
    	 // This example does not return any files that start with `.'.
    	 FilenameFilter filter = new FilenameFilter() {
    	     public boolean accept(File dir, String name) {
    	         return !name.startsWith(".")&&!name.endsWith(".dtd");
    	     }
    	 };
    	 children = file.list(filter);


    	 // The list of files can also be retrieved as File objects
    	 File[] files = file.listFiles();

    	 // This filter only returns directories
    	 FileFilter fileFilter = new FileFilter() {
    	     public boolean accept(File file) {
    	         return file.isDirectory();
    	     }
    	 };
    	 files = file.listFiles(fileFilter);
		return children;
    	 
    	 
     }
}