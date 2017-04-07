/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Luke
 */
public class BasicNaiveBayes implements Classifier {

    // Contains the counts of the attributes
    // counts.get(1) will return the first attribute and all its possibilities
    ArrayList<Map> counts = new ArrayList<>();
    ArrayList<DataFound> df = new ArrayList<>();

    @Override
    public void buildClassifier(Instances ins) throws Exception {
        // assigns the class position of the instance 
        ins.setClassIndex(ins.numAttributes() - 1);

        // Creates a map for each Attribute and containing the attribute
        // value and the count - which is initalised to zero
        for (int i = 0; i < ins.numAttributes() - 1; i++) {
            Enumeration<Object> att = ins.attribute(i).enumerateValues();
            Map<Double, Double> freq = new HashMap<>();
            while (att.hasMoreElements()) {
                // assigning the the next attribute element to a Integer
                // using .toString() as it requires less memory
                Double d = new Double(att.nextElement().toString());
                double zer = 0;
                freq.put(d, zer);
            }
            counts.add(freq);
        }
        // Increments the counts for all the attribute values
        for (Instance line : ins) {
            double[] attributes = new double[line.numAttributes() - 1];
            for (int i = 0; i < line.numValues() - 1; i++) {
                String attributeValue = line.stringValue(i);
                Double attribute = new Double(attributeValue);
                // increments the count for that given attribute value
                counts.get(i).put(attribute, (Double) counts.get(i).get(attribute) + 1);
                attributes[i] = attribute;
            }
            DataFound data = new DataFound(attributes, line.classValue());
            df.add(data);
        }

        for (Map x : counts) {
            System.out.println(x);
        }
    }

    // This is just a quick test
    public void probabilityCrime() {
        System.out.println("");
        ArrayList<Map> crimeCounts = new ArrayList<>();
        
        for(Map m : counts){
            crimeCounts.add(m);
        }

        
        // find which DataFound objects had crime
        ArrayList<DataFound> crime = new ArrayList<>();
        for (DataFound x : df) {
            if (x.getClassValue() == 1) {
                crime.add(x);
            }
        }

        // loop through all the Data which had crime and assigns them to a new
        // arraylist of maps with a decremented amount
        for (DataFound x : crime) {
            for (int i = 0; i < crimeCounts.size(); i++) {
                crimeCounts.get(i).put(x.getAttribute()[i],
                        (Double) crimeCounts.get(i).get(x.getAttribute()[i]) - 1);
            }
        }
        for (Map x : crimeCounts) {
            System.out.println(x);
        }

        System.out.println("");
        for (DataFound x : crime) {
            for (int i = 0; i < crimeCounts.size(); i++) {
                System.out.println((Double) crimeCounts.get(i).get(x.getAttribute()[i]));
                System.out.println((Double) counts.get(i).get(x.getAttribute()[i]));
                crimeCounts.get(i).put(x.getAttribute()[i],
                        (Double) crimeCounts.get(i).get(x.getAttribute()[i]) / (Double) counts.get(i).get(x.getAttribute()[i])
                );

            }

        }

        System.out.println();

        for (Map x : crimeCounts) {
            System.out.println(x);
        }

    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {

        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * The method distributionForInstance should work out the probabilities of
     * class membership for a single instance.
     *
     * @param instnc
     * @return
     * @throws Exception
     */
    @Override
    public double[] distributionForInstance(Instance instnc) throws Exception {

        return null;

    }

    @Override
    public Capabilities getCapabilities() {
        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

}
