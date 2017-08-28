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
import java.util.ArrayList;
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
public class CrossValidationSingleRunVariantWithCostSensitiveAndTreshold {

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
		if (randData.classAttribute().isNominal())
		randData.stratify(folds);

		// perform cross-validation
		;
		Instances labeled; //to hold labelled instances
		Instances labeledCS; //to hold labelled instances

		Instances labeledFCS = null; //to hold labelled instances

		double clsLabel;
		double predictionProbabilityNo;
		double predictionProbabilityYes;
		String ontModified = "";


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

		labeledCS = new Instances(randData);

		//  instNew = Filter.useFilter(randData, remove);
		// System.out.println(instNew);
		labeledFCS = new Instances(randData.stringFreeStructure());
		//System.out.println(labeledFCS.numInstances());
		ArrayList<Double> THV  = new ArrayList<Double>();
		THV = getCSTreshold();
		//System.out.println(THV.size());
		//for (int p = 0; p< THV.size()-1; p++){
		Evaluation evalAll = new Evaluation(labeledCS);

			for (int n = 0; n < folds; n++) {


				Instances train = randData.trainCV(folds, n);

                 train.stratify(10);

				Instances trainCS = train.trainCV(folds, n);
				Instances test = randData.testCV(folds, n);

				Instances testCS = train.testCV(folds, n);
				// Evaluation eval = new Evaluation(trainCS);

				NaiveBayes nbCS = new NaiveBayes();  

				nbCS.buildClassifier(trainCS);


                test.stratify(10);

				// eval.evaluateModel(nbCS, testCS);
				//evalAll.evaluateModel(nbCS, testCS);


				// System.out.println(train.stringFreeStructure());
				//System.out.println(evalAll.toMatrixString());


				// System.out.println(evalAll.toClassDetailsString());

				//System.out.println(k);

				for (int i = 0 ; i <= testCS.numInstances() - 1; i++) {
					clsLabel = nbCS.classifyInstance(testCS.instance(i)); //class value of instance i
					labeledCS.instance(i).setClassValue(clsLabel); //setting the class value;
					double[] predictionOutput = nbCS.distributionForInstance(testCS.instance(i));
					predictionProbabilityYes = predictionOutput[0];
					predictionProbabilityNo = predictionOutput[1];
					String category = trainCS.instance(i).toString().split(",")[7];
					if (Integer.parseInt(category) ==0 && predictionProbabilityYes >= (double) THV.get(n)){
						category = "1";
						//  System.out.println(labeledCS.instance(i));
						labeledFCS.add(labeledCS.instance(i));
					}
					else
						labeledFCS.add(testCS.instance(i));
				}
				NaiveBayes nbFCS = new NaiveBayes();
				nbFCS.buildClassifier(labeledFCS);

				// System.out.println(trainCS.numInstances());
				//System.out.println(train.numInstances());
				Evaluation eval = new Evaluation(labeledFCS);
				eval.evaluateModel(nbFCS, test);
				nbCS.buildClassifier(labeledFCS);
				evalAll.evaluateModel(nbFCS, test);
				
				// evalAll.evaluateModel(nbFCS, test);
				//System.out.println("Test size ============"+test.numInstances());
				//System.out.println(eval.toMatrixString());
				//System.out.println(eval.toClassDetailsString());

				// System.out.println(evalAll.toMatrixString());
				//  System.out.println(evalAll.toClassDetailsString());
			//}

			// output evaluation
			//  System.out.println("================================================================");



			//  writeFile("C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/annotationcorpusAgaindataS.arff",dataS);


		}
			System.out.println(evalAll.toMatrixString());
			System.out.println(evalAll.toClassDetailsString());


	}

	public static ArrayList<Double> getCSTreshold() throws Exception{
		// DecimalFormat formatter = new DecimalFormat("0.0000");
		String dataS = "";
		ArrayList<Double> THValues = new ArrayList<Double> ();
		// loads data and set class index

		//-t C:\Users\Hospice\Downloads\edits-3.0.tar\edits\rte\corpusAgain.arff -c last -x 10 -s 1 -W "weka.classifiers.trees.J48 -C 0.25"
		Instances data = DataSource.read("C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/annotationdata1ModifiedOtherOnlyOctWOlengthlimit.arff");
		String clsIndex = "last";
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
		String[] tmpOptions = null;
		String classname;
		//tmpOptions     =  {"weka.classifiers.trees.J48", "-C", "0.25"};
		//classname      = "weka.classifiers.trees.J48";
		
		classname      = "weka.classifiers.bayes.NaiveBayes";
		//tmpOptions[0]  = "";
		//tmpOptions[1]  = "-C";
		//tmpOptions[1]  = "0.25";
		 Classifier cls = (Classifier) Utils.forName(Classifier.class, classname, tmpOptions);

		// other options
		int seed  = 1;
		int folds = 10;

		// randomize data
		// Random rand = new Random(seed);
		Instances randData = new Instances(data);
		//randData.randomize(rand);
		//if (randData.classAttribute().isNominal())
		//randData.stratify(folds);

		// perform cross-validation

		Instances labeled; //to hold labelled instances
		Instances labeledCS; //to hold labelled instances

		Instances labeledFCS = null; //to hold labelled instances

		double clsLabel;
		double predictionProbabilityNo;
		double predictionProbabilityYes;
		String ontModified = "";


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

		labeledCS = new Instances(randData);

		//  instNew = Filter.useFilter(randData, remove);
		// System.out.println(instNew);
		labeledFCS = new Instances(randData.stringFreeStructure());
		//System.out.println(labeledFCS.numInstances());
		for (int n = 0; n < folds; n++) {


			Instances train = randData.trainCV(folds, n);

			Evaluation evalAll = new Evaluation(train);


			Instances trainCS = train.trainCV(folds, n);

			Instances testCS = train.testCV(folds, n);
			// Evaluation eval = new Evaluation(trainCS);

			NaiveBayes nbCS = new NaiveBayes();  

			cls.buildClassifier(trainCS);




			// eval.evaluateModel(nbCS, testCS);
			evalAll.evaluateModel(cls, testCS);


			// System.out.println(train.stringFreeStructure());
			//System.out.println(evalAll.toMatrixString());
			// System.out.println(evalAll.numFalsePositives(0)/(evalAll.numFalsePositives(0)+evalAll.numFalseNegatives(0)));

			// System.out.println(evalAll.numFalsePositives(0) +" "+ evalAll.numFalseNegatives(0));
			//System.out.println(evalAll.numFalsePositives(1) +" "+ evalAll.numFalseNegatives(1));

			//  System.out.println(evalAll.numFalsePositives(1)/(evalAll.numFalsePositives(1)+evalAll.numFalseNegatives(1)));

			THValues.add(evalAll.numFalsePositives(1)/(evalAll.numFalsePositives(1)+evalAll.numFalseNegatives(1)));
			// System.out.println(evalAll.toClassDetailsString());

			//System.out.println(k);

			// System.out.println(evalAll.toMatrixString());
			//  System.out.println(evalAll.toClassDetailsString());
		}





		// output evaluation
		//  System.out.println("================================================================");



		//  writeFile("C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/annotationcorpusAgaindataS.arff",dataS);

		//System.out.println(THValues.get(1));
		return THValues;



	}

	private static void writeFile(String file, String text)throws IOException{

		PrintWriter writer = new PrintWriter(file, "UTF-8");
		writer.println(text);
		writer.close();
	}
}