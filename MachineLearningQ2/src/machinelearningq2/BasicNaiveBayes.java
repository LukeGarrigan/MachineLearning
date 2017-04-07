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

    @Override
    public void buildClassifier(Instances ins) throws Exception {
        // assigns the class position of the instance 
        ins.setClassIndex(ins.numAttributes() - 1);
        //System.out.println(ins.numDistinctValues(0));

        // Creates a map for each Attribute and containing the attribute
        // value and the count - which is initalised to zero
        for (int i = 0; i < ins.numAttributes() - 1; i++) {
            Enumeration<Object> att = ins.attribute(i).enumerateValues();
            Map<Double, Integer> freq = new HashMap<>();
            while (att.hasMoreElements()) {
                // assigning the the next attribute element to a Integer
                // using .toString() as it requires less memory
                Double d = new Double(att.nextElement().toString());
                freq.put(d, 0);
            }
            counts.add(freq);
        }
        for (Map x : counts) {
            System.out.println(x);
            System.out.println("");
        }
        
        // Increments the counts for all the attribute values
        for (Instance line : ins) {
            for (int i = 0; i < line.numValues() - 1; i++) {
                String attributeValue = line.stringValue(i);
                Double attribute = new Double(attributeValue);
                // increments the count for that given attribute value
                counts.get(i).put(attribute, (Integer) counts.get(i).get(attribute) + 1);
            }
        }
        
        for (Map x : counts) {
            System.out.println(x);
            System.out.println("");
        }
    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] distributionForInstance(Instance instnc) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Capabilities getCapabilities() {
        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

}
