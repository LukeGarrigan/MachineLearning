/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

import java.util.ArrayList;

/**
 *
 * @author Luke
 */
public class DataFound {
    
    double classValue;
    ArrayList<Double> data;
    public DataFound(double classValue, ArrayList<Double> data){
        this.classValue = classValue;
        this.data = data;
    }

    public double getClassValue() {
        return classValue;
    }

    public void setClassValue(double classValue) {
        this.classValue = classValue;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(ArrayList<Double> data) {
        this.data = data;
    }
    
}
