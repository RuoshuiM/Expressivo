package expressivo;

import java.util.Map;
import java.text.DecimalFormat;

/**
 * An immutable data type representing a nonnegative integer or a floating point
 */
class Num implements Expression {
    
    // rep
    private final double n;
    
    // abstract function
    //      represents a nonnegative integer or a floating point
    // rep invariant
    //      true
    // safety from rep exposure
    //      all fields are immutable and final
    
    // num DecimalFormat, to 20th place precision
    private final DecimalFormat numDF = new DecimalFormat("##0.####################");
    
    public  Num(int n) {
        this.n = (double) n;
    }
    
    public Num(double n) {
        this.n = n;
    }
    
    @Override
    public String toString() {
//        if (Math.floor(n) == n)
//            return String.valueOf((int)n);
//        else
//            return String.valueOf(n);
        // new solution, using DecimalFormat class. Solved all the problems!!! :)
        return numDF.format(n);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(n);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Num))
            return false;
        Num other = (Num) obj;
        if (Double.doubleToLongBits(n) != Double.doubleToLongBits(other.n))
            return false;
        return true;
    }

    @Override
    public double value() {
        return this.n;
    }
    
    @Override
    public Expression differentiate(String var) {
        return new Num(0);
    }

    @Override
    public Expression simplify(Map<String, Double> env) {
        return this;
    }
    
    @Override
    public void printSelf(String indent) {
        System.out.println(indent + "Num: "+n);
    }

    @Override
    public double numTerm() {
        return n;
    }

    @Override
    public double numFactor() {
        // TODO Auto-generated method stub
        return n;
    }

    @Override
    public Expression varTerm() {
        // TODO Auto-generated method stub
        return new Num(0);
    }

    @Override
    public Expression varFactor() {
        // TODO Auto-generated method stub
        return new Num(1);
    }
}
