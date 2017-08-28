package datacollector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import data.SentenceData;
import utils.Readdirectory;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class SelfLearningGetSentencesForPapers {
	
	//Instance variable
	Instances train; //labelled instances
	Instances test; //unlabelled instances
	String trainFile; //labelled file name
	String testFile; //unlabelled file name
	static String content;

	int numAttributes;
	List<Attribute> attributes;
	//Constructor 
	public SelfLearningGetSentencesForPapers(String trainFileName, SentenceData sd) throws IOException{
		
		//Setting the labelled and unlabelled files
		this.trainFile = trainFileName;
		
		//Reading the instances in labelled file
		BufferedReader trainReader = new BufferedReader(new FileReader(trainFileName));;
		this.train = new Instances (trainReader);
		trainReader.close();
		//Setting the class attribute of the labelled file (the last attribute is the class attribute)
		this.train.setClassIndex(train.numAttributes() -1);
		
		//Reading the instances in unlabelled file

		
	}//public SelfLearning(String trainFileName, String testFileName) throws IOException
public SelfLearningGetSentencesForPapers(String trainFileName) throws IOException{
		
		//Setting the labelled and unlabelled files
		this.trainFile = trainFileName;
		
		//Reading the instances in labelled file
		BufferedReader trainReader = new BufferedReader(new FileReader(trainFileName));;
		this.train = new Instances (trainReader);
		trainReader.close();
		//Setting the class attribute of the labelled file (the last attribute is the class attribute)
		this.train.setClassIndex(train.numAttributes() -1);
		this.content ="";
		//Reading the instances in unlabelled file

		
	}//public SelfLearning(String trainFileName, String testFileName) throws IOException
	
	
	public SelfLearningGetSentencesForPapers(String trainFileName, String testFileName) throws IOException{
		
		//Setting the labelled and unlabelled files
		this.trainFile = trainFileName;
		this.testFile = testFileName;
		
		//Reading the instances in labelled file
		BufferedReader trainReader = new BufferedReader(new FileReader(trainFileName));;
		this.train = new Instances (trainReader);
		trainReader.close();
		//Setting the class attribute of the labelled file (the last attribute is the class attribute)
		this.train.setClassIndex(train.numAttributes() -1);
		
		//Reading the instances in unlabelled file
		BufferedReader testReader = new BufferedReader(new FileReader(testFileName));		
		this.test = new Instances (testReader);
		testReader.close();
		//Setting the class attribute of the unlabelled file (the last attribute is the class attribute and contains "?")
		this.test.setClassIndex(test.numAttributes() -1);
		
	}//public SelfLearning(String trainFileName, String testFileName) throws IOException
	//Method to classify instances
	public void classifyInstances(ArrayList<String> corpusArrayList, String writeToFile ) throws Exception{
	
		Instances labeled; //to hold labelled instances
		double clsLabel, predictionProbabilityYes, predictionProbabilityNo,  predictionProbability3; //to hold 
		double[] predictionOutput; //To hold the prediction output in an array
		
		BufferedWriter testWriter = new BufferedWriter(new FileWriter(writeToFile)); //Newly labelled instances from the unlabelled file with large confidence will be appended to the labelled file.

		boolean isConverged = false; //To check whether converged
		int lastTestInstance = test.numInstances(), currentTestInstance = 0; 
		ArrayList<Integer> instanceTracker = new ArrayList<Integer>();
		int k = 0;

		int [] index = new int[test.numInstances()];
		
		
				NaiveBayes nb = new NaiveBayes();         // new instance of tree

				nb.buildClassifier(this.train);   // build classifier
				labeled = new Instances(this.test); //labels of unlabelled instances
		
				// label the unlabelled instances backwards
				for (int i = 0 ; i <= this.test.numInstances() - 1; i++) {
			         
					clsLabel = nb.classifyInstance(test.instance(i)); 
					labeled.instance(i).setClassValue(clsLabel); 
					predictionOutput = nb.distributionForInstance(test.instance(i));
					predictionProbabilityYes = predictionOutput[0];
					predictionProbabilityNo = predictionOutput[1];	
				
                    testWriter.append(corpusArrayList.get(i)+"\t"+labeled.instance(i).toString().split(",")[2502]+"\n");
					
		}//while(test.numInstances() > 0)

		testWriter.close();
		
	}//public void classifyInstances() throws Exception
	
	@SuppressWarnings("null")
	public static ArrayList<String> loadCorpus(String file){
		ArrayList<String> corpusArrayList = new ArrayList<String>();
		ArrayList<String> corpusList = new ArrayList<String>();
	    BufferedReader inputStream = null;
	    try {
		  
	      inputStream = new BufferedReader(new FileReader(file));
	      String line;
	      while ((line = inputStream.readLine()) != null) {
	        if ((line.length() == 0) || (line.trim().length() == 0))
	            continue;
	    	corpusList.add(line.trim());
	      }
	    } catch (FileNotFoundException ex) {
	      ex.printStackTrace();
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    } 
		return corpusList;
		
		
	}
	
	public Instance buildInstance(String sent) throws Exception {
		SentenceTenseGetterFlattForPapers STG = new SentenceTenseGetterFlattForPapers("C:/Users/Hospice/backup/imrad_classifier/data/englishPCFG.ser.gz");
		sent = STG.sentenceWithTenseCSV(sent);
		SentenceData sd = SentenceDataReader.readSentenceData(sent);
		//System.out.println(sent);
		
	    ARFFWriterForPapers writer = new ARFFWriterForPapers();
	    String vector =  writer.writeARFFSent("", sd, "C:/Users/Hospice/annotated_data/corpusout/MRCdata/MRCSelf/Alldata98conf2500andtensetwoclassesbi_Chi.txt", 2500, false, true, "method,other");
        //System.out.println(vector);
	    
	    String[] vectorFeat = vector.split(",");
        
         
         Instance inst = new Instance(this.train.numAttributes());
         inst.setDataset(this.train);
      
 		 
 		this.numAttributes = this.train.numAttributes();
 	    //  this.train.setClassIndex(this.numAttributes - 1);
 	      
 	      this.attributes = new ArrayList(this.numAttributes);
 	      for (int i = 0; i < this.numAttributes; i++) {
 	        this.attributes.add(this.train.attribute(i));
 	      }
 		 int k = 0;
 		 /*for (Attribute attribute : this.attributes){
 			// System.out.println(vectorFeat[k]);
 			 inst.setValue(attribute,vectorFeat[k]);
 			 k++;
 		 }*/
         for(int i = 0; i< vectorFeat.length; i++){
        	// System.out.println(inst.attribute(i));
        	 inst.setValue(this.attributes.get(i), Integer.parseInt(vectorFeat[i].trim()));
         }
         
 		 //System.out.println(inst.toString());
         //inst.setClassValue(vectorFeat[vectorFeat.length-1]);
 		
		return inst;
	}
	
	public void classifyInstances(Instance inst) throws Exception{
		Instance labeled; //to hold labelled instances
		labeled = inst; //labels of unlabelled instances
		NaiveBayes nb = new NaiveBayes();         // new instance of tree

		nb.buildClassifier(this.train);   // build classifier
		double clsLabel;
		clsLabel = nb.classifyInstance(inst); //class value of instance i
		
		//System.out.println(clsLabel);
		labeled.setClassValue(clsLabel); //setting the class value
		this.content+=labeled.toString().split(",")[labeled.toString().split(",").length-1]+"\n";
		System.out.println(labeled.toString().split(",")[labeled.toString().split(",").length-1]);

	}
	
	
	private static void writeFile(String file, String text)throws IOException{
		  
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		writer.println(text);
		writer.close();
	}
	
	public static void main(String[] args) throws Exception{
		ArrayList<String> corpusArrayList =loadCorpus("C:/Users/Hospice/combinesentences/annotationSentencesPositiveSentencesNBlabelled.txt");

		for (String s : corpusArrayList){
		SelfLearningGetSentencesForPapers SL = new SelfLearningGetSentencesForPapers("C:/Users/Hospice/annotated_data/corpusout/MRCdata/MRCSelf/Alldata98conf2500andtensetwoclassesbi.arff");
		
		String sentence = "Arg has one of the lowest TA/TA-propensities (among all the dinucleotide steps) whereas Gly and Pro have significantly higher values for the same dinucleotide step.";
		Instance inst = SL.buildInstance(s.split("\t")[0]);
		content+=s.split("\t")[0]+"\t";
		System.out.println(s.split("\t")[0]);
		SL.classifyInstances(inst);
		}
		writeFile("C:/Users/Hospice/combinesentences/annotationSentencesPositiveSentencesNBlabelledTestAgain.txt", content);
		
	}
}//public class SelfLearning
