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
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;

/**
 *
 * @author Luke
 */
public class ExtendedNaiveBayes extends BasicNaiveBayesV1 {

    private int[] classValueCounts;

    private double[][] attributeMeans;
    private double[][] attributeVariance;
    private ArrayList<DataFound> data = new ArrayList<>();
    private double countData;

    private double[] binCount;
    private double testCount = 0;
    private double correctCount = 0;
    private final String gausianOrDiscretise;

    public ExtendedNaiveBayes(boolean laplace, String gausianOrDiscretise) {
        super(laplace);
        this.gausianOrDiscretise = gausianOrDiscretise;
    }

    /**
     *
     * Build classifier will either build a gaussian or a discrete classifier
     * dependent on user input
     *
     * @param ins
     * @throws Exception
     */
    @Override
    public void buildClassifier(Instances ins) throws Exception {
        if ("d".equals(gausianOrDiscretise)) {
            buildDiscreteClassifier(ins);
        } else {
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
    }

    /**
     * The method buildDiscreteClassifier discretizes the data and then builds a
     * classifer
     *
     * @param instnc
     * @return
     * @throws Exception
     */
    public void buildDiscreteClassifier(Instances ins) throws Exception {
        ins = discretize(ins);
        ins.setClassIndex(ins.numAttributes() - 1);
        countData = ins.size();
        // assigns the class position of the instance 
        classValueCounts = new int[ins.numClasses()];
        // store the values
        for (Instance line : ins) {
            double classValue = line.classValue();
            classValueCounts[(int) classValue]++;
            for (int i = 0; i < line.numAttributes() - 1; i++) {
                double attributeValue = line.value(i);
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
        double[] bayesCalculations;
        double actualClassValue = instnc.classValue();
        if ("d".equals(gausianOrDiscretise)) {
            bayesCalculations = distributionForDiscrete(instnc);
        } else {
            bayesCalculations = distributionForInstance(instnc);
        }
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

    public Instances discretize(Instances instnc) throws Exception {
        Discretize d = new Discretize();
        d.setInputFormat(instnc);
        Instances newData = Filter.useFilter(instnc, d);

        binCount = new double[d.getBins()];

        for (Instance line : newData) {
            for (int j = 0; j < newData.numAttributes() - 1; j++) {
                binCount[(int) line.value(j)]++;
            }
        }
        return newData;
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

        if ("d".equals(gausianOrDiscretise)) {
            return super.distributionForInstance(instnc);
        }
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
     * The method distributionForInstance should work out the probabilities of
     * class membership for a single instance.
     *
     * @param instnc
     * @return
     * @throws Exception
     */
    public double[] distributionForDiscrete(Instance instnc) throws Exception {

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
                double attributeValue = instnc.value(i);
                DataFound d = new DataFound(attributeValue, c, i);

                int index = data.indexOf(d);
                if (index != -1) {
                    double classValueCount = classValueCounts[(int) d.getClassValue()];
                    conditionalProbs.add(data.get(index).getConditionalProbability((int) classValueCount));
                }
            }
            // compute the naive bayes
            double total = 1;
            for (Double x : conditionalProbs) {
                total *= x;
            }
            naiveBayes[c] = total;
        }
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

    public void getAccuracy() {
        double percent = (correctCount / testCount) * 100;
        DecimalFormat df = new DecimalFormat("#.####");
        System.out.print(df.format(percent) + " %");
    }
}
