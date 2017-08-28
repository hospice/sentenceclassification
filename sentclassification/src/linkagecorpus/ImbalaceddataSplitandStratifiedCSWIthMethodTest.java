package linkagecorpus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.filters.Filter;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.core.Attribute;
import weka.core.AttributeStats;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.supervised.instance.StratifiedRemoveFolds;

public class ImbalaceddataSplitandStratifiedCSWIthMethodTest {

	public static void main(String[] args) throws Exception {
		//BufferedReader reader = new BufferedReader(new FileReader("C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/annotationdata1ModifiedAddAllMethodOnlygreater4.arff"));
		
		BufferedReader reader = new BufferedReader(new FileReader("C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/annotationdata1ModifiedAgainWithMethodfeather2.arff"));

		
		Instances data1 = new Instances(reader);
		data1.setClassIndex(data1.numAttributes() - 1);

		Evaluation evalAll = new Evaluation(data1);
		Evaluation evalAllStrat = new Evaluation(data1);
		for (int seed = 1; seed <=10; seed++){



			Random rand = new Random(seed);
			Instances data = new Instances(data1);
			data.randomize(rand);
			Instances labeledFCS = new Instances(data.stringFreeStructure());
			double clsLabel;
			double predictionProbabilityNo;
			double predictionProbabilityYes;
			int trainSize = (int) Math.round(data.numInstances() * 0.6);
			int devtestSize = data.numInstances() - trainSize;
			Instances train = new Instances(data, 0, trainSize);
			Instances devTrain = new Instances(data, trainSize, devtestSize);
			StratifiedRemoveFolds stf = new StratifiedRemoveFolds();


			int devSize = (int) Math.round(devTrain.numInstances() * 0.5);
			int testSize =  devTrain.numInstances()-devSize;

			Instances dev = new Instances(devTrain, 0, devSize);

			Instances test = new Instances(devTrain, devSize, testSize);
			Instances train1;
			


			try {
				StratifiedRemoveFolds fold = new StratifiedRemoveFolds();
				data.setClassIndex(data.numAttributes() - 1);

				train.setClassIndex(train.numAttributes() - 1);
				dev.setClassIndex(dev.numAttributes() - 1);
				test.setClassIndex(test.numAttributes() - 1);


				fold.setInputFormat( data );
				//fold.setSeed( 1 );
				fold.setNumFolds(5 );
				fold.setFold( 5 );
				fold.setInvertSelection( false );
				Instances subTrain = Filter.useFilter( data, fold ); // should get 80% of the data


				fold.setInputFormat( data );
				//fold.setSeed( 1 );
				fold.setNumFolds(5 );
				fold.setFold( 4);
				fold.setInvertSelection( false );


				Instances subTrain2 = Filter.useFilter( data, fold ); // should get 80% of the data

				subTrain = merge(subTrain, subTrain2);

				fold.setInputFormat( data );
				fold.setSeed( 1 );
				fold.setNumFolds(5 );
				fold.setFold(1);
				fold.setInvertSelection( false );


				Instances subTrain3 = Filter.useFilter( data, fold ); // should get 80% of the data

				subTrain = merge(subTrain, subTrain3);


				fold = new StratifiedRemoveFolds();
				fold.setInputFormat( data );
				fold.setSeed( 1 );
				fold.setNumFolds( 5 );
				fold.setFold( 2 );

				fold.setInvertSelection( false );
				Instances holdoutTrain = Filter.useFilter( data, fold ); // should get the other 20%


				fold = new StratifiedRemoveFolds();
				fold.setInputFormat( data );
				fold.setSeed( 1 );
				fold.setNumFolds( 5 );
				fold.setFold( 3 );

				fold.setInvertSelection( false );
				Instances holdoutTest = Filter.useFilter( data, fold ); // should get the other 20%

				SMO svm = new SMO();
				PolyKernel k = new PolyKernel();
				k.setExponent(2);
				svm.setKernel(k);

				NaiveBayes nbFCS = new NaiveBayes();

				Evaluation eval = new Evaluation(train);
				nbFCS.buildClassifier(subTrain);



				eval.evaluateModel(nbFCS, holdoutTrain);
				evalAll.evaluateModel(nbFCS, holdoutTrain);
				double TH = eval.numFalsePositives(1)/(eval.numFalsePositives(1)+eval.numFalseNegatives(1));
				System.out.println("Cost Sencitive Treshold  :" + TH);
				System.out.println(eval.toSummaryString());

				System.out.printf(eval.toMatrixString());
				System.out.println(eval.toClassDetailsString());

				for (int i = 0 ; i <= holdoutTest.numInstances() - 1; i++) {
					clsLabel = nbFCS.classifyInstance(holdoutTest.instance(i)); //class value of instance i
					subTrain.instance(i).setClassValue(clsLabel); //setting the class value;
					double[] predictionOutput = nbFCS.distributionForInstance(subTrain.instance(i));
					predictionProbabilityYes = predictionOutput[0];
					predictionProbabilityNo = predictionOutput[1];
					//String category = subTrain.instance(i).toString().split(",")[7];
					String category = holdoutTest.classAttribute().value((int) holdoutTest.instance(i).classValue());
					//System.out.println(holdoutTest.classAttribute().value((int) holdoutTest.instance(i).classValue()));
					if (Integer.parseInt(category) ==0 && predictionProbabilityYes >= TH){

						//System.out.println(predictionProbabilityYes +"\t" +predictionProbabilityNo);
						holdoutTest.instance(i).setClassValue(clsLabel); //setting the class value;
						Instance inst = holdoutTest.instance(i);
						category = "1";
						//  System.out.println(labeledCS.instance(i));
						subTrain.add(inst);
						
					}
					else
					{}//labeledFCS.add(subTrain.instance(i));
				}
				
				//labeledFCS
				
				for (int i = 0 ; i <= holdoutTest.numInstances() - 1; i++) {
					//System.out.println(holdoutTest.instance(i).value(7));
					String attValue8 = new Double(holdoutTest.instance(i).value(7)).toString();

					if (attValue8.equals("1.0")){
						labeledFCS.add(holdoutTest.instance(i));
					}
				}

				//labeledFCS.setClassIndex(data.numAttributes() - 1);
				NaiveBayes nbFCSF = new NaiveBayes();
				Evaluation eval2 = new Evaluation(subTrain);
				


				nbFCSF.buildClassifier(subTrain);

				eval2.evaluateModel(nbFCSF, holdoutTest);

				
				evalAllStrat.evaluateModel(nbFCSF, labeledFCS);
				System.out.println(eval2.toSummaryString());

				System.out.printf(eval2.toMatrixString());
				System.out.println(eval2.toClassDetailsString());

				System.out.println( "Size of data = " + data.numInstances() );
				//  System.out.println( "Size of test = " );

				AttributeStats statsd = data.attributeStats(data.classIndex());
				System.out.println(statsd.nominalCounts[1]/statsd.nominalCounts[0]);

				AttributeStats stats = subTrain.attributeStats(subTrain.classIndex());
				System.out.println(stats.nominalCounts[1]/stats.nominalCounts[0]);

				AttributeStats stats2 = holdoutTrain.attributeStats(holdoutTrain.classIndex());
				System.out.println(stats2.nominalCounts[1]/stats2.nominalCounts[0]);

				AttributeStats stats3 = test.attributeStats(test.classIndex());
				System.out.println(stats3.nominalCounts[1]/stats3.nominalCounts[0]);

				System.out.println( "Size of subTrain = " + subTrain.numInstances() );
				System.out.println( "Size of holdoutTrain = " + holdoutTrain.numInstances() );
				// System.out.println(data.toSummaryString());
				// System.out.println(holdoutTrain.toSummaryString());
				System.out.println("============================================================================================");

			} catch (Exception e) {
				System.err.println("Exception caught during formatting: " + e.getMessage());
				return;
			}
			
System.out.println("=========Final Summary ===========================");
			System.out.println(evalAll.toClassDetailsString());
			
System.out.println("=========Final Summary After Cost Sensitive Balancing ===========================");

			
			System.out.println(evalAllStrat.toClassDetailsString());

		}
	}
	public static Instances merge(Instances data1, Instances data2)	throws Exception
			{
		// Check where are the string attributes
		int asize = data1.numAttributes();
		boolean strings_pos[] = new boolean[asize];
		for(int i=0; i<asize; i++)
		{
			Attribute att = data1.attribute(i);
			strings_pos[i] = ((att.type() == Attribute.STRING) ||
					(att.type() == Attribute.NOMINAL));
		}

		// Create a new dataset
		Instances dest = new Instances(data1);
		dest.setRelationName(data1.relationName() + "+" + data2.relationName());

		DataSource source = new DataSource(data2);
		Instances instances = source.getStructure();
		Instance instance = null;
		while (source.hasMoreElements(instances)) {
			instance = source.nextElement(instances);
			dest.add(instance);

			// Copy string attributes
			for(int i=0; i<asize; i++) {
				if(strings_pos[i]) {
					dest.instance(dest.numInstances()-1)
					.setValue(i,instance.stringValue(i));
				}
			}
		}

		return dest;
			}

}
