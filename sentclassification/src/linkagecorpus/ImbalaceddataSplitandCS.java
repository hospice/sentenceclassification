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
import weka.core.AttributeStats;
import weka.core.Instances;
import weka.filters.supervised.instance.StratifiedRemoveFolds;


//import weka.classifiers.functions.LinearRegression;

public class ImbalaceddataSplitandCS {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new FileReader("C:/Users/Hospice/Downloads/edits-3.0.tar/edits/rte/corpusAgain.arff"));
	    Instances data = new Instances(reader);
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
	        fold.setSeed( 1 );
	        fold.setNumFolds(5 );
	        fold.setFold( 5 );
	        fold.setInvertSelection( true );
	        Instances subTrain = Filter.useFilter( data, fold ); // should get 80% of the data
	        
	        fold = new StratifiedRemoveFolds();
	         fold.setInputFormat( data );
	         fold.setSeed( 1 );
	         fold.setNumFolds( 5 );
	        fold.setFold( 5 );
	        
	        fold.setInvertSelection( false );
	        Instances holdoutTrain = Filter.useFilter( data, fold ); // should get the other 20%
	        
	        SMO svm = new SMO();
	        PolyKernel k = new PolyKernel();
	        k.setExponent(2);
	        svm.setKernel(k);
	        
	        NaiveBayes nbFCS = new NaiveBayes();

	        Evaluation eval = new Evaluation(train);
	        nbFCS.buildClassifier(train);
	        eval.evaluateModel(nbFCS, test);
	        System.out.println(eval.toSummaryString());

	        System.out.printf(eval.toMatrixString());
	        System.out.println(eval.toClassDetailsString());
	        
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
		} catch (Exception e) {
			    System.err.println("Exception caught during formatting: " + e.getMessage());
			    return;
			}
	}

}
