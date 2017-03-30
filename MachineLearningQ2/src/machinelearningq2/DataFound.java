/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luke
 */
public class DataFound {
    
    double classValue;
    List<List<Double>> data = new  ArrayList<>();
    public DataFound(double classValue){
        this.classValue = classValue;
    }

    public void addData(List<Double> d){
        data.add(d);
    }
    
    public double getClassValue() {
        return classValue;
    }

    public void setClassValue(double classValue) {
        this.classValue = classValue;
    }
    
    public List<List<Double>> getData(){
        return data;
    }
}
