/**
 * 
 */
package expressivo;

import java.util.Map;

/**
 * An immutable data type representing the product of two expressions
 */
public class Times implements Expression {

 // rep
    private final Expression left;
    private final Expression right;
    
    // abstraction function
    //      represents the product of two expressions left*right
    // rep invariant
    //      true
    // safety from rep exposure
    //      all fields are private and final
    
    /** Make a Times which is the product of left and right */
    public Times(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public String toString() {
        String leftString = left.toString();
        String rightString = right.toString();
        if (leftString.contains("+")) {
            leftString = "(" + leftString + ")";
        }
        if (rightString.contains("+")) {
            rightString = "(" + rightString + ")";
        }
        return leftString + "*" + rightString;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
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
        if (!(obj instanceof Times))
            return false;
        Times other = (Times) obj;
        if (left == null) {
            if (other.left != null)
                return false;
        } else if (!left.equals(other.left))
            return false;
        if (right == null) {
            if (other.right != null)
                return false;
        } else if (!right.equals(other.right))
            return false;
        return true;
    }

    @Override
    public double value() {
        return left.value() * right.value();
    }
    
    @Override
    public Expression differentiate(String var) {        
        return Expression.plus(Expression.times(left.differentiate(var), right),
                Expression.times(right.differentiate(var), left));
    }

    @Override
    public Expression simplify(Map<String, Double> env) {
        Expression leftSimp = left.simplify(env);
        Expression rightSimp = right.simplify(env);
//        double leftVal = leftSimp.value();
//        double rightVal = rightSimp.value();
//        Expression leftReturn, rightReturn;
//        
//        if (Double.isNaN(leftVal))
//            leftReturn = leftSimp;
//        else
//            leftReturn = new Num(leftVal);
//        if (Double.isNaN(rightVal))
//            rightReturn = rightSimp;
//        else
//            rightReturn = new Num(rightVal);
//
//        if (!(Double.isNaN(leftVal) || Double.isNaN(rightVal)))
//            return new Num(leftVal * rightVal);
//        
//        return Expression.times(leftReturn, rightReturn);
        Expression simplified = Expression.times(leftSimp, rightSimp);
        Expression numFactors = new Num(simplified.numFactor());
        Expression varFactors = simplified.varFactor();
        return Expression.times(numFactors, varFactors);
    }
    
    @Override
    public void printSelf(String indent) {
        System.out.println(indent + "Times: ");
        System.out.println(indent + "left: ");
        left.printSelf(indent + indent);
        System.out.println(indent + "right: ");
        right.printSelf(indent+indent);
    }

    /**
     * Times represents a product, has nothing to do with a term, so the return
     * value should not have any effect in addition
     */
    @Override
    public double numTerm() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double numFactor() {
        // TODO Auto-generated method stub
        return left.numFactor() * right.numFactor();
    }

    /**
     * In a Times: (left*right), the Times itself is the sum of var
     */
    @Override
    public Expression varTerm() {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Expression varFactor() {
        // TODO Auto-generated method stub
        return Expression.times(left.varFactor(), right.varFactor());
    }
}
