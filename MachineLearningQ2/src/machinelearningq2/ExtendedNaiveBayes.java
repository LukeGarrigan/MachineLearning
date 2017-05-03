/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

import java.text.DecimalFormat;
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

    private double[][] attributeMeans;
    private double[][] attributeVariance;
    private ArrayList<DataFound> data = new ArrayList<>();
    private double countData;

    private double testCount = 0;
    private double correctCount = 0;

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
        attributeMeans = new double[ins.numClasses()][ins.numAttributes() - 1];
        attributeVariance = new double[ins.numClasses()][ins.numAttributes() - 1];
        // store the values
        for (Instance line : ins) {
            double classValue = line.classValue();
            classValueCounts[(int) classValue]++;
            for (int i = 0; i < line.numAttributes() - 1; i++) {
                double attributeValue = line.value(i);
                attributeMeans[(int) classValue][i] += attributeValue;
                DataFound d = new DataFound(attributeValue, classValue, i);

                int index = data.indexOf(d);
                // then it doesn't exist
                if (index == -1) {
                    data.add(d);
                } else {
                    data.get(index).incrementCount();
                }
            }
        }
        System.out.println("Attribute Totals: " + Arrays.deepToString(attributeMeans));
        // computes the means
        for (int j = 0; j < classValueCounts.length; j++) {
            for (int i = 0; i < ins.numAttributes() - 1; i++) {
                attributeMeans[j][i] = attributeMeans[j][i] / classValueCounts[j];
            }
        }

        // calculate the variance
        for (int i = 0; i < data.size(); i++) {
            double cv = data.get(i).getClassValue();
            double atIn = data.get(i).getAttributeIndex();
            double squareDifference = Math.pow(data.get(i).getAttributeValue() - attributeMeans[(int) cv][(int) atIn], 2);
            attributeVariance[(int) cv][(int) atIn] += squareDifference;
        }
        for (int j = 0; j < classValueCounts.length; j++) {
            for (int i = 0; i < ins.numAttributes() - 1; i++) {
                attributeVariance[j][i] = attributeVariance[j][i] / (classValueCounts[j] - 1);
                attributeVariance[j][i] = Math.sqrt(attributeVariance[j][i]);
            }
        }
        System.out.println("Attribute Means: " + Arrays.deepToString(attributeMeans));
        System.out.println("Variance: " + Arrays.deepToString(attributeVariance));
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
        testCount++;
        double actualClassValue = instnc.classValue();
        double[] bayesCalculations = distributionForInstance(instnc);
        double largest = 0;
        double largestIndex = 0;

        for (int i = 0; i < bayesCalculations.length; i++) {
            if (bayesCalculations[i] > largest) {
                largest = bayesCalculations[i];
                largestIndex = i;
            }
        }
        if (largestIndex == actualClassValue) {
            correctCount++;
        }

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
        double[] prediction = new double[classValueCounts.length];
        for (int c = 0; c < classValueCounts.length; c++) {
            ArrayList<Double> likelihoods = new ArrayList<>();
            double priorProbability = classValueCounts[c] / countData;
            likelihoods.add(priorProbability);
            for (int i = 0; i < instnc.numAttributes() - 1; i++) {
                double currentMean = attributeMeans[c][i];
                double currentVariance = attributeVariance[c][i];
                double attributeValue = instnc.value(i);

                double likelihood = 1 / (Math.sqrt(2 * Math.PI) * currentVariance)
                        * Math.exp(-Math.pow(attributeValue - currentMean, 2)
                                / (2 * Math.pow(currentVariance, 2)));
                likelihoods.add(likelihood);
            }
            double total = 1;
            for (Double x : likelihoods) {
                total *= x;
            }
            prediction[c] = total;

        }
        return prediction;
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

    public void getAccuracy() {
        double percent = (correctCount / testCount) * 100;
        DecimalFormat df = new DecimalFormat("#.####");
        System.out.print(df.format(percent) + " %");
    }
}
