package linkagecorpus;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Utils;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;

import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.text.DecimalFormat;
/**
 * Performs a single run of cross-validation. Outputs the Confusion matrices
 * for each single fold.
 *
 * Command-line parameters:
 * <ul>
 *    <li>-t filename - the dataset to use</li>
 *    <li>-x int - the number of folds to use</li>
 *    <li>-s int - the seed for the random number generator</li>
 *    <li>-c int - the class index, "first" and "last" are accepted as well;
 *    "last" is used by default</li>
 *    <li>-W classifier - classname and options, enclosed by double quotes; 
 *    the classifier to cross-validate</li>
 * </ul>
 *
 * Example command-line:
 * <pre>
 * java CrossValidationSingleRun -t anneal.arff -c last -x 10 -s 1 -W "weka.classifiers.trees.J48 -C 0.25"
 * </pre>
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class CrossValidationSingleRunVariantWithCostSensitive {

  /**
   * Performs the cross-validation. See Javadoc of class for information
   * on command-line parameters.
   *
   * @param args        the command-line parameters
   * @throws Excecption if something goes wrong
   */
  public static void main(String[] args) throws Exception {
	 // DecimalFormat formatter = new DecimalFormat("0.0000");
	    String dataS = "";

    // loads data and set class index
    Instances data = DataSource.read(Utils.getOption("t", args));
    String clsIndex = Utils.getOption("c", args);
    if (clsIndex.length() == 0)
      clsIndex = "last";
    if (clsIndex.equals("first"))
      data.setClassIndex(0);
    else if (clsIndex.equals("last"))
      data.setClassIndex(data.numAttributes() - 1);
    else
      data.setClassIndex(Integer.parseInt(clsIndex) - 1);
    int k = 0;
    String content = data.stringFreeStructure().toString();
    // classifier
    String[] tmpOptions;
    String classname;
    tmpOptions     = Utils.splitOptions(Utils.getOption("W", args));
   // System.out.println(tmpOptions[3]);
    classname      = tmpOptions[0];
    tmpOptions[0]  = "";
    Classifier cls = (Classifier) Utils.forName(Classifier.class, classname, tmpOptions);

    // other options
    int seed  = Integer.parseInt(Utils.getOption("s", args));
    int folds = Integer.parseInt(Utils.getOption("x", args));

    // randomize data
   // Random rand = new Random(seed);
    Instances randData = new Instances(data);
    //randData.randomize(rand);
    //if (randData.classAttribute().isNominal())
      //randData.stratify(folds);

    // perform cross-validation
    System.out.println();
    System.out.println("=== Setup ===");
    System.out.println("Classifier: " + cls.getClass().getName() + " " + Utils.joinOptions(cls.getOptions()));
    System.out.println("Dataset: " + data.relationName());
    System.out.println("Folds: " + folds);
    System.out.println("Seed: " + seed);
    System.out.println();
	Instances labeled; //to hold labelled instances
	
	double clsLabel;
	double predictionProbabilityNo;
	double predictionProbabilityYes;
	String ontModified = "";

    Evaluation evalAll = new Evaluation(randData);
    //Instances       inst;

    Instances       instNew;
    Remove          removeTest;
    removeTest = new Remove();
    Remove          removeTrain;
    removeTrain = new Remove();
    
    String[] options = new String[2];
    options[0] = "-R";                                    // "range"
    options[1] = "1"; 
    removeTest.setOptions(options); 
    
    
  //  instNew = Filter.useFilter(randData, remove);
   // System.out.println(instNew);
    
    for (int n = 0; n < folds; n++) {
    	
      Evaluation eval = new Evaluation(randData);
      Instances train = randData.trainCV(folds, n);
      
      removeTrain.setInputFormat(train);
	    Instances newTrainData = Filter.useFilter(train, removeTrain);
     // System.out.println(train.stringFreeStructure());
      Instances test = randData.testCV(folds, n);
      NaiveBayes nb = new NaiveBayes();         // new instance of tree

		nb.buildClassifier(newTrainData);   // build classifier
		
		removeTest.setInputFormat(test);
	    Instances newTestData = Filter.useFilter(test, removeTest);
		labeled = new Instances(newTestData); //labels of unlabelled instances
		//System.out.println(test.numInstances());

		for (int i = 0 ; i <= newTestData.numInstances() - 1; i++) {
			
			
			
			clsLabel = nb.classifyInstance(newTestData.instance(i)); //class value of instance i
			//System.out.println(clsLabel);
			labeled.instance(i).setClassValue(clsLabel); //setting the class value;
			double[] predictionOutput = nb.distributionForInstance(test.instance(i));
			predictionProbabilityYes = predictionOutput[0];
			predictionProbabilityNo = predictionOutput[1];
			String [] instancePart = labeled.instance(i).toString().split(",");
            //System.out.println(k+"\t"+labeled.instance(i).toString().split(",")[6]);
			String category = newTestData.instance(i).toString().split(",")[7];
           if (Integer.parseInt(category) ==1 && predictionProbabilityNo >= 0.95){
        	   category = "1";
		//System.out.println(labeled.instance(i).toString()+","+predictionProbabilityYes+","+predictionProbabilityNo);
		   //String instandAdd = instancePart[0]+","+instancePart[1]+","+instancePart[2]+","+instancePart[3]+","+instancePart[4]+","+instancePart[5]+","+predictionProbabilityYes+","+predictionProbabilityNo+","+test.instance(i).toString().split(",")[6];
          //System.out.println(instancePart[0]+","+instancePart[1]+instancePart[2]+","+instancePart[3]+instancePart[4]+","+instancePart[5]+","+predictionProbabilityYes+","+predictionProbabilityNo+","+instancePart[6]);
		   String instandAdd = instancePart[0]+","+instancePart[1]+","+instancePart[2]+","+instancePart[3]+","+instancePart[4]+","+instancePart[5]+","+category;
		   k++;
		  content+= instandAdd+"\n";
		   System.out.println(test.instance(i));
           }
         /*  if (Integer.parseInt(category) ==1 && Double.parseDouble(predictionProbabilityYes) >= 0.9545454545454546){
        	   category = "0";
		//System.out.println(labeled.instance(i).toString()+","+predictionProbabilityYes+","+predictionProbabilityNo);
		   //String instandAdd = instancePart[0]+","+instancePart[1]+","+instancePart[2]+","+instancePart[3]+","+instancePart[4]+","+instancePart[5]+","+predictionProbabilityYes+","+predictionProbabilityNo+","+test.instance(i).toString().split(",")[6];
          //System.out.println(instancePart[0]+","+instancePart[1]+instancePart[2]+","+instancePart[3]+instancePart[4]+","+instancePart[5]+","+predictionProbabilityYes+","+predictionProbabilityNo+","+instancePart[6]);
		   String instandAdd = instancePart[0]+","+instancePart[1]+","+instancePart[2]+","+instancePart[3]+","+instancePart[4]+","+instancePart[5]+","+category;

		   content+= instandAdd+"\n";
           }
           */
           else
        	   content+=test.instance(i).toString()+"\n";
		}
      // the above code is used by the StratifiedRemoveFolds filter, the
      // code below by the Explorer/Experimenter:
      // Instances train = randData.trainCV(folds, n, rand);

      // build and evaluate classifier
     // Classifier clsCopy = Classifier.makeCopy(cls);
    // System.out.println("Number of training instances  ======= : "+ train.numInstances());

     // System.out.println("Number of testing instances  ======= : "+ test.numInstances());
    //  clsCopy.buildClassifier(train);
     // eval.evaluateModel(clsCopy, test);
     // evalAll.evaluateModel(clsCopy, test);

      // output evaluation
      //System.out.println();
    //  System.out.println(eval.toMatrixString("=== Confusion matrix for fold " + (n+1) + "/" + folds + " ===\n"));
     // System.out.println(eval.toClassDetailsString());

    }

    // output evaluation
  //  System.out.println("================================================================");
   // System.out.println(evalAll.toSummaryString("=== " + folds + "-fold Cross-validation ===", false));
   // System.out.println(evalAll.toClassDetailsString());
    
    System.out.println(k);
    
   // writeFile("C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/annotationcorpusAgaindataS.arff",dataS);
    
  }
  private static void writeFile(String file, String text)throws IOException{
	  
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		writer.println(text);
		writer.close();
	  }
}