/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

import java.io.FileReader;
import weka.classifiers.Classifier;
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
        String trainingDataPath = "datasets/diabetes/diabetes-train.arff";
        String testDataPath = "datasets/diabetes/diabetes-test.arff";
        /*
        String trainingDataPath = "datasets/crime.arff";
        String testDataPath = "datasets/crimeTest.arff";
         */
        // creating the instances
        Instances trainingData = getData(trainingDataPath);
        Instances testData = getData(testDataPath);

        /*
        BasicNaiveBayesV1 b = new BasicNaiveBayesV1(true);
        b.buildClassifier(trainingData);

        for (Instance x : testData) {
            b.classifyInstance(x);
        }
         */
        ExtendedNaiveBayes c = new ExtendedNaiveBayes(true, "d");
        c.buildClassifier(trainingData);
        testData.setClassIndex(testData.numAttributes() - 1);

        for (Instance x : testData) {
            c.classifyInstance(x);
        }
        c.getAccuracy();

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
