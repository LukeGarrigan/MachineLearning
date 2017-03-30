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
        // assigns the class position of the instance 
        ins.setClassIndex(ins.numAttributes() - 1);
        for (Instance line : ins) {
            // creates a list and stores attribute values
            ArrayList<Double> attributeValues = new ArrayList<>();
            for (int i = 0; i < line.numAttributes() - 1; i++) {
                attributeValues.add(line.value(i));
            }
            // If its a new class value create new DataFound object
            // else just add to the existing 
            boolean cvExists = false;
            for (DataFound d : new ArrayList<>(allData)) {
                if (d.getClassValue() == line.classValue()) {
                    d.addData(attributeValues);
                    cvExists = true;
                }
            }
            if (cvExists == false) {
                DataFound data = new DataFound(line.classValue());
                data.addData(attributeValues);
                allData.add(data);
            }
        }
        // printing and checking the values
        System.out.println(allData.size());
        for (DataFound x : allData) {
            System.out.println(x.getData() + " " + x.getClassValue());
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
