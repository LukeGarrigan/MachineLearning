/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearningq2;

/**
 *
 * @author Luke
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Comparator;

/**
 *
 * @author Luke
 */
public class DataFound2 {

    private double attributeValue;
    private double classValue;
    private int attributeIndex;
    private int counts = 1;
    private double conditionalProbability;
    private double mean;
    private double variance;
    
    public DataFound2(double attributeValue, double classValue, int attributeIndex, double mean, double variance) {
        this.attributeValue = attributeValue;
        this.classValue = classValue;
        this.attributeIndex = attributeIndex;
        this.mean = mean;
        this.variance = variance;
    }

    public double getAttributeValue() {
        return attributeValue;
    }

    public double getClassValue() {
        return classValue;
    }

    public int getAttributeIndex() {
        return attributeIndex;
    }

    public int getCounts() {
        return counts;
    }

    public void incrementCount(){
        this.counts++;
    }
    public boolean equals(Object o) {
        if (o instanceof DataFound) {
            DataFound temp = (DataFound) o;
            if (this.attributeValue != temp.getAttributeValue()) {
                return false;
            }
            if(this.attributeIndex != temp.getAttributeIndex()){
                return false;
            }
            if(this.classValue != temp.getClassValue()){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.attributeValue) ^ (Double.doubleToLongBits(this.attributeValue) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.classValue) ^ (Double.doubleToLongBits(this.classValue) >>> 32));
        hash = 97 * hash + this.attributeIndex;
        return hash;
    }

    public double getConditionalProbability() {
        return conditionalProbability;
    }

    
    public void computeConditionalProbability(double priorCount){
        this.conditionalProbability = counts/priorCount;
    }

    @Override
    public String toString() {
        return "DataFound2{" + "attributeValue=" + attributeValue + ", classValue=" + classValue + ", attributeIndex=" + attributeIndex + ", counts=" + counts + ", conditionalProbability=" + conditionalProbability + '}';
    }
   
    
    
    
    
}
