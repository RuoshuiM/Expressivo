/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    // Datatype definition
    //   Expression = Num(n:Double)
    //                + Var(v:String)
    //                + Plus(left:Expression, right:Expression) 
    //                + Times(left:Expression, right:Expression)

    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        return Parser.parse(input);
    }

    // This repo has some intelligent ideas in simplifying the structure of the AST
    // https://github.com/Riksi/Expressivo/blob/master/src/expressivo/Expression.java
    
    static void visitAll(Expression exp, String indent) {
        exp.printSelf(indent);
    }
    
    public void printSelf(String indent);
    
    /**
     * Make a plus, striping unnecessary zeros
     * @param left
     * @param right
     * @return an Expression representing left+right, but without the 0s
     */
    public static Expression plus(Expression left, Expression right) {
        Expression zero = new Num(0);
        if (left.equals(zero))
            return right;
        if (right.equals(zero))
            return left;
        
        // no need to check for both sides = 0, b/c if both = 0, we can return either side
        
        
        return new Plus(left, right);
    }
    
    /**
     * Make a times, stripping unnecessary 1s and 0s
     * @param left
     * @param right
     * @return Expression representing left*right, without 1s. When any side = 0, return 0
     */
    public static Expression times(Expression left, Expression right) {
        Expression zero = new Num(0);
        Expression one = new Num(1);
        
        if (left.equals(zero) || right.equals(zero))
            return zero;
        if (left.equals(one))
            return right;
        if (right.equals(one))
            return left;
        
        return new Times(left, right);
    }
    
    /**
     * @return represents the sum of the numerical terms
     */
    public double numTerm();
    
    /**
     * @return represents the product of the numerical factors
     */
    public double numFactor();
    
    /**
     * @return represents the sum of the non-numerical terms
     */
    public Expression varTerm();
    
    /**
     * @return represents the product of the non-numerical factors
     */
    public Expression varFactor();

    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     * 
     * toString() should not have spaces in the output.
     * toString() should output the operations in the same order as the input
     * toString() should use the minimum possible parentheses
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS1 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    // TODO more instance methods
    /** @return value of the expression */
    public double value();
    
    /**
     * The symbolic differentiation operation takes an expression and a variable,
     * and produces an expression with the derivative of the input with respect to
     * the variable.
     * 
     * <p>
     * The Expression object itself is the expression to be differentiated.
     * </p>
     * 
     * @param var The variable to differentiate by
     * @return Differentiated expression, unsimplified
     */
    public Expression differentiate(String var);
    
    /**
     * The simplification operation takes an expression and an environment (a
     * mapping of variables to values). It substitutes the values for those
     * variables into the expression, and attempts to simplify the substituted
     * polynomial as much as it can.
     * 
     * <p>
     * The Expression object itself is the expression to be simplified.
     * </p>
     * 
     * @param env The mapping of variable to a value.
     *            <p>
     *            The set of variables in the environment is allowed to be different
     *            than the set of variables actually found in the expression. Any
     *            variables in the expression but not the environment remain as
     *            variables in the substituted polynomial. Any variables in the
     *            environment but not the expression are simply ignored.
     *            </p>
     * @return Simplified expression.
     */
    public Expression simplify(Map<String,Double> env);
}

