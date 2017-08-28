package datacollector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class SelfLearning {
	
	//Instance variable
	Instances train; //labelled instances
	Instances test; //unlabelled instances
	String trainFile; //labelled file name
	String testFile; //unlabelled file name
	
	
	//Constructor 
	public SelfLearning(String trainFileName, String testFileName) throws IOException{
		
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
	public void classifyInstances() throws Exception{
		
		Instances labeled; //to hold labelled instances
		double clsLabel, predictionProbabilityYes, predictionProbabilityNo,  predictionProbability3; //to hold predicted labels and their probabilities. Only two probabilities because it is a binary classification problem.
		double[] predictionOutput; //To hold the prediction output in an array
		BufferedWriter trainWriter = new BufferedWriter(new FileWriter(trainFile, true)); //Newly labelled instances from the unlabelled file with large confidence will be appended to the labelled file.
		BufferedWriter testWriter = new BufferedWriter(new FileWriter("C:/Users/Hospice/annotated_data/corpusout/IMradCorpusChi/MRCdataNormAll_2500CChiWithTense_80confidence.arff")); //Newly labelled instances from the unlabelled file with large confidence will be appended to the labelled file.

		boolean isConverged = false; //To check whether converged
		int lastTestInstance = test.numInstances(), currentTestInstance = 0; 
		ArrayList<Integer> instanceTracker = new ArrayList<Integer>();
		int k = 0;

		int [] index = new int[test.numInstances()];
		
		//Loop until converges
		while(isConverged == false){
			
			int insertedTestInstance = 0;
			//Checking whether converged. If converges, break the loop.
			if (lastTestInstance == currentTestInstance){
				System.out.println("---Converged!---\n---DONE!---");//train
				isConverged = true;
			}//if (lastTestInstance == currentTestInstance)
			
			//If not converged then do---
			else{
				NaiveBayes nb = new NaiveBayes();         // new instance of tree
				nb.buildClassifier(this.train);   // build classifier
				labeled = new Instances(this.test); //labels of unlabelled instances
		
				// label the unlabelled instances backwards
				for (int i = this.test.numInstances() - 1; i >= 0; i--) {
			
					clsLabel = nb.classifyInstance(test.instance(i)); //class value of instance i
					//System.out.println(clsLabel);
					labeled.instance(i).setClassValue(clsLabel); //setting the class value
					// Get the probability/confidence of the label.
					predictionOutput = nb.distributionForInstance(test.instance(i));
					predictionProbabilityYes = predictionOutput[0];
					predictionProbabilityNo = predictionOutput[1];	
					predictionProbability3 = predictionOutput[2];

					if(!instanceTracker.contains(i)){ //if the unlabelled instance is not classified yet
						if (predictionProbabilityYes >= 0.80 || predictionProbabilityNo >= 0.80|| predictionProbability3>= 0.80){ //Only do this if the algorithm is 98% confident at its labelling
							this.train.add(labeled.instance(i)); //add to the labelled instances
							//System.out.println(predictionProbabilityYes);
							//if (clsLabel == 0.0)

                            //System.out.println(test.instance(i).toString().split(","));
							//System.out.println(labeled.instance(i));
							k++;
							int b = i+1;
							//trainWriter.append("\n" + labeled.instance(i).toString()); //add to the labelled file
							if (test.instance(i).toString().split(",")[2502].equals(labeled.instance(i).toString().split(",")[2502]))
								//testWriter.append("\n" + b+"\t"+ test.instance(i).toString().split(",")[2502]);
								//testWriter.append("\n" + b+"\t"+ test.instance(i).toString());

								testWriter.append("\n" + labeled.instance(i).toString());

							instanceTracker.add(i);
							System.out.println();
							//checking whether yes class or no class
							//if (clsLabel == 0.0){
							//	index[i] = 1; 
							//}
							//this.test.delete(i); // don't need this line
							insertedTestInstance++; //increment the no. of inserted instance
						}//if (predictionProbabilityYes >= 0.98 || predictionProbabilityNo >= 0.98)
						
					}

						
				}//for (int i = 0; i < test.numInstances(); i++)
			}//else
			
			currentTestInstance = test.numInstances() - insertedTestInstance;
			lastTestInstance = currentTestInstance + insertedTestInstance;
		
		}//while(test.numInstances() > 0)
		System.out.println(k);

		trainWriter.close();
		testWriter.close();
		//DenoisedData.denoiseData(index);
		
	}//public void classifyInstances() throws Exception
	
	public static void main(String[] args) throws Exception{
		SelfLearning SL = new SelfLearning("C:/Users/Hospice/annotated_data/corpusout/IMradCorpusChi/annotated_words_tense_3classesMod30Again.arff","C:/Users/Hospice/annotated_data/corpusout/IMradCorpusChi/MRCdataNorm2500CChiWithTense.arff");
		SL.classifyInstances();
	}
}//public class SelfLearning
