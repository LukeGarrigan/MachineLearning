/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

import java.util.ArrayList;
import java.util.HashSet;
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
    
    ArrayList<DataFound> allData = new ArrayList<>();
    
    @Override
    public void buildClassifier(Instances ins) throws Exception {
        // Assigns the class of the instance 

        ins.setClassIndex(ins.numAttributes() - 1);
        int numClasses = ins.numClasses();
        // loops through the data to retrieve all of the classes
        int classCount = 0;
        
        
        
        
        for (Instance line : ins) {
            ArrayList<Double> attributeValues = new ArrayList<>();
            for (int i = 0; i < line.numAttributes() - 1; i++) {
                attributeValues.add(line.value(i));
            }
            DataFound data = new DataFound(line.classValue(), attributeValues);
            allData.add(data);
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
