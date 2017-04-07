/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Luke
 */
public class DataFound {

    private final double[] attribute;
    private final double classValue;

    public DataFound(double[] attribute, double classValue) {
        this.attribute = attribute;
        this.classValue = classValue;
    }

    public double[] getAttribute() {
        return attribute;
    }

    public double getClassValue() {
        return classValue;
    }

    @Override
    public String toString() {
        return "DataFound{" + "attribute=" + Arrays.toString(attribute) + ", classValue=" + classValue + '}';
    }
    
   
}
