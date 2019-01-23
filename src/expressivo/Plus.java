/**
 * 
 */
package expressivo;

import java.util.Map;

/**
 * An immutable data type representing a sum in an expression
 * 
 */
public class Plus implements Expression {

    // rep
    private final Expression left;
    private final Expression right;
    
    // abstraction function
    //      represents the sum of two expressions left+right
    // rep invariant
    //      true
    // safety from rep exposure
    //      all fields are private and final
    
    /** Make a Plus which is the sum of left and right */
    public Plus(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public String toString() {
        return left.toString() + "+" + right.toString();
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
        if (!(obj instanceof Plus))
            return false;
        Plus other = (Plus) obj;
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
        return left.value() + right.value();
    }
    
    @Override
    public Expression differentiate(String var) {
        return Expression.plus(left.differentiate(var), right.differentiate(var));
    }

    @Override
    public Expression simplify(Map<String, Double> env) {
        Expression leftSimp = left.simplify(env);
        Expression rightSimp = right.simplify(env);
//        double leftVal = leftSimp.value();
//        double rightVal = rightSimp.value();
//        Expression leftReturn, rightReturn;
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
//            return new Num(leftVal + rightVal);
//
//        return Expression.plus(leftReturn, rightReturn);
        Expression simplified = Expression.plus(leftSimp, rightSimp);
        Expression numTerms = new Num(simplified.numTerm());
        Expression varTerms = simplified.varTerm();
        return Expression.plus(numTerms, varTerms);
    }
    
    
    @Override
    public void printSelf(String indent) {
        System.out.println(indent + "Plus: ");
        System.out.println(indent + "left: ");
        left.printSelf(indent + indent);
        System.out.println(indent + "right: ");
        right.printSelf(indent+indent);
    }

    @Override
    public double numTerm() {
        // TODO Auto-generated method stub
        return left.numTerm() + right.numTerm();
    }

    /**
     * Plus represents 1*(left+right), so the numerical factor of a Plus is 1
     */
    @Override
    public double numFactor() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public Expression varTerm() {
        // TODO Auto-generated method stub
        return Expression.plus(left.varTerm(), right.varTerm());
    }

    /**
     * Plus represents 1*(left+right), so the non-numerical factor is "(left+right)"
     */
    @Override
    public Expression varFactor() {
        // TODO Auto-generated method stub
        return this;
    }
}
