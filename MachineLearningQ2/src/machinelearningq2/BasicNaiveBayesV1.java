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
public class BasicNaiveBayesV1 implements Classifier {

    int[] classValueCounts = new int[2];
    ArrayList<DataFound2> data = new ArrayList<>();
    double countData;

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
        // store the values
        for (Instance line : ins) {
            double classValue = line.classValue();
            classValueCounts[(int) classValue]++;
            for (int i = 0; i < line.numAttributes() - 1; i++) {
                String attributeValue = line.stringValue(i);
                Double attribute = new Double(attributeValue);
                DataFound2 d = new DataFound2(attribute, classValue, i);

                if (!data.contains(d)) {
                    data.add(d);
                } else {
                    for (int j = 0; j < data.size(); j++) {
                        if (data.get(j).equals(d)) {
                            data.get(j).incrementCount();
                        }
                    }
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
        // Needs to work out the probability of it being in all classes and then
        // return the array of doubles containing these probabilites
        System.out.println(instnc + "\n");
        // Step 1: Find the counts for the given value
        System.out.println(instnc.attribute(0).numValues());

        double[] naiveBayes = new double[classValueCounts.length];
        for (int c = 0; c < naiveBayes.length; c++) {
            ArrayList<Double> conditionalProbs = new ArrayList<>();
            double priorProbability = classValueCounts[c] / countData;
            conditionalProbs.add(priorProbability);
            for (int i = 0; i < instnc.numValues() - 1; i++) {
                String attributeValue = instnc.stringValue(i);
                Integer attribute = new Integer(attributeValue);
                DataFound2 d = new DataFound2(attribute, c, i);
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).equals(d)) {
                        conditionalProbs.add(data.get(j).getConditionalProbability());
                    }
                }
            }

            // compute the naive bayes
            double total = 1;
            for (Double x : conditionalProbs) {
                total *= x;
            }
            naiveBayes[c] = total;
            //System.out.println(total);
            //System.out.println(conditionalProbs);
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
    
    
    public void prettyPrintProbabilities(double[] x){
        System.out.println(Arrays.toString(x));
        
        
    }

}
