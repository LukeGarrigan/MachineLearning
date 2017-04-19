/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

import java.util.ArrayList;
import java.util.Arrays;
import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Luke
 */
public class ExtendedNaiveBayes implements Classifier {

    private int[] classValueCounts;
    private ArrayList<DataFound2> data = new ArrayList<>();
    private double countData;

    /**
     *
     * This initial classifier will contain a two dimension array of counts
     *
     * @param ins
     * @throws Exception
     */
    @Override
    public void buildClassifier(Instances ins) throws Exception {

        countData = ins.size();
        // assigns the class position of the instance 
        ins.setClassIndex(ins.numAttributes() - 1);
        classValueCounts = new int[ins.numClasses()];
        // store the values
        for (Instance line : ins) {
            double classValue = line.classValue();
            classValueCounts[(int) classValue]++;
            for (int i = 0; i < line.numAttributes() - 1; i++) {
                String attributeValue = line.stringValue(i);
                Double attribute = new Double(attributeValue);
                DataFound2 d = new DataFound2(attribute, classValue, i);

                int index = data.indexOf(d);
                // then it doesn't exist
                if (index == -1) {
                    data.add(d);
                } else {
                    data.get(index).incrementCount();
                }
            }
        }

        // compute the conditional probabilities
        System.out.println(data.size());

        for (DataFound2 x : data) {
            double classValueCount = classValueCounts[(int) x.getClassValue()];
            x.computeConditionalProbability(classValueCount);
            System.out.println(x);
        }

        System.out.println("");

        System.out.println(Arrays.toString(classValueCounts));

    }

    /**
     * The method classifyInstance which should call your previous
     * distributionForInstance method and simply return the prediction as the
     * class with the largest probability
     *
     * @param instnc
     * @return
     * @throws Exception
     */
    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        double[] bayesCalculations = distributionForInstance(instnc);
        double largest = 0;
        double largestIndex = 0;

        for (int i = 0; i < bayesCalculations.length; i++) {
            if (bayesCalculations[i] > largest) {
                largest = bayesCalculations[i];
                largestIndex = i;
            }
        }
        System.out.println("Class Membership: " + largestIndex);
        return largestIndex;
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

        // creates a double array for storing the naive calculations for each class
        double[] naiveBayes = new double[classValueCounts.length];

        // loops through each class and computes the naive bayes 
        for (int c = 0; c < naiveBayes.length; c++) {

            // stores all conditional probabilities for class membership such:
            // P(struct=0|crime=1), P(security=1|crime=1), P(area=1|crime=1)
            // and also it stores the prior probability: P(crime=1)
            ArrayList<Double> conditionalProbs = new ArrayList<>();
            double priorProbability = classValueCounts[c] / countData;
            conditionalProbs.add(priorProbability);
            for (int i = 0; i < instnc.numValues() - 1; i++) {
                String attributeValue = instnc.stringValue(i);
                Integer attribute = new Integer(attributeValue);
                DataFound2 d = new DataFound2(attribute, c, i);

                int index = data.indexOf(d);
                if (index != -1) {
                    conditionalProbs.add(data.get(index).getConditionalProbability());
                }
            }
            System.out.println(conditionalProbs);
            // compute the naive bayes
            double total = 1;
            for (Double x : conditionalProbs) {
                total *= x;
            }
            naiveBayes[c] = total;
        }
        prettyPrintProbabilities(naiveBayes);
        return naiveBayes;
    }

    /**
     *
     * @return
     */
    @Override
    public Capabilities getCapabilities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void prettyPrintProbabilities(double[] x) {
        System.out.println(Arrays.toString(x));
        double total = 0;
        for (int i = 0; i < x.length; i++) {
            total += x[i];
        }

        for (int i = 0; i < x.length; i++) {
            double probability = (x[i] / total);
            System.out.println("Probability of " + i + " Membership :" + (probability * 100) + "%");
        }

    }

}
