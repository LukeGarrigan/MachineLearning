/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

import java.util.Arrays;
import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Luke
 */
public class BasicNaiveBayesV1 implements Classifier {

    double counts[][] = new double[4][4];
    double condProbs[][] = new double[5][5];

    /**
     *
     * This initial classifier will contain a two dimension array of counts
     *
     * @param ins
     * @throws Exception
     */
    @Override
    public void buildClassifier(Instances ins) throws Exception {
        // assigns the class position of the instance 
        ins.setClassIndex(ins.numAttributes() - 1);

        for (Instance line : ins) {
            double multipler = line.classValue() + 1;
            for (int i = 0; i < line.numAttributes()-1; i++) {
                String attributeValue = line.stringValue(i);
                Integer attribute = new Integer(attributeValue);
                condProbs[attribute][i]++;
            }
        }
        // printing out the 2D array of counts
        for (int i = 0; i < condProbs.length; i++) {
            for (int j = 0; j < condProbs.length; j++) {
                System.out.print(condProbs[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");

        // stores the counts in a 2D array
        for (Instance line : ins) {
            for (int i = 0; i < line.numAttributes(); i++) {
                String attributeValue = line.stringValue(i);
                Integer attribute = new Integer(attributeValue);
                counts[attribute][i]++;
            }
        }
        // printing out the 2D array of counts
        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < counts.length; j++) {
                System.out.print(counts[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     *
     * @param instnc
     * @return
     * @throws Exception
     */
    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * The method distributionForInstance should work out the probabilities of
     * class membership for a single instance.
     *
     * @param instnc
     * @return
     * @throws Exception
     */
    @Override
    public double[] distributionForInstance(Instance instnc) throws Exception {
        // Needs to work out the probability of it being in all classes and then
        // return the array of doubles containing these probabilites
        System.out.println(instnc + "\n");
        // Step 1: Find the counts for the given value
        System.out.println(instnc.attribute(0).numValues());
        for (int i = 0; i < instnc.numValues() - 1; i++) {
            String attributeValue = instnc.stringValue(i);
            Integer attribute = new Integer(attributeValue);
            System.out.println(counts[attribute][i]);

        }

        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public Capabilities getCapabilities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
