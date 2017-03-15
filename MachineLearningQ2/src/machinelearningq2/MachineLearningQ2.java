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

/**
 *
 * @author Luke
 */
public class MachineLearningQ2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String trainingDataPath = "C:\\Users\\Luke\\Documents\\NetBeansProjects\\MachineLearningQ2\\datasets\\crime.arff";
        String testDataPath = "C:\\Users\\Luke\\Documents\\NetBeansProjects\\MachineLearningQ2\\datasets\\crime.arff";
        
        Instances trainingData = getData(trainingDataPath);
        Instances testData = getData(testDataPath);
        trainingData.setClassIndex(trainingData.numAttributes()-1);
     
        BasicNaiveBayes classify = new BasicNaiveBayes();
        classify.buildClassifier(trainingData);
        
    }
    
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
