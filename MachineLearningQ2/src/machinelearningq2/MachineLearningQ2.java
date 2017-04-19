/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

import java.io.FileReader;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Debug.Random;
import weka.core.Instance;

/**
 *
 * @author Luke
 */
public class MachineLearningQ2 {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here

        // paths for the training and test data
        String trainingDataPath = "datasets/crime.arff";
        String testDataPath = "datasets/crime.arff";

        // creating the instances
        Instances trainingData = getData(trainingDataPath);
        Instances testData = getData(testDataPath);
        // building the classifier
        // BasicNaiveBayes classify = new BasicNaiveBayes();
        //classify.buildClassifier(trainingData);        
        //classify.probabilityCrime();
        BasicNaiveBayesV1 classify = new BasicNaiveBayesV1();
        classify.buildClassifier(trainingData);
        
        
        
        NaiveBayes x = new NaiveBayes();
        
        // instance for testing 
        Instance testInstance = testData.get(0);
        classify.classifyInstance(testInstance);
        
        
        
    }

    /**
     * Reads in the data from the file and returns the instances
     *
     * @param filepath
     * @return
     */
    public static Instances getData(String filepath) {
        Instances train = null;
        try {
            FileReader reader = new FileReader(filepath);
            train = new Instances(reader);
            
        } catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }
        return train;
    }
    
}
