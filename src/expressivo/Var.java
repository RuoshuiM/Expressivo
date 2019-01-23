/**
 * 
 */
package expressivo;

import java.util.Map;

/**
 * An immutable data type representing a variable in an expression
 */
public class Var implements Expression {
    
    // rep
    private final String var;
    private final double value;
    
    // abstraction function
    //      represents a variable, and its corresponding value
    // rep invariant
    //      only contains [a-zA-Z]+
    // safety from rep exposure
    //      all fields are private; non-final fields can only be accessed through methods
    
    private void checkRep() {
        assert var.matches("^[a-zA-Z]+$");
    }
    
    /** Make a variable */
    public Var(String var) {
        this.var = var;
        this.value = Double.NaN;
        checkRep();
    }
    
    /** Make a variable with an environment value */
    public Var(String var, double value) {
        this.var = var;
        this.value = value;
        checkRep();
    }

    @Override
    public String toString() {
        return var;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Var))
            return false;
        Var other = (Var) obj;
        if (var == null && other.toString() != null)
            return false;
        else if (!var.equals(other.toString()))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((var == null) ? 0 : var.hashCode());
        return result;
    }

    @Override
    public double value() {
        return value;
    }
    
    @Override
    public Expression differentiate(String var) {
        if (this.var.equals(var)) {
            return new Num(1);
        } else {
            return new Num(0);
        }
    }

    @Override
    public Expression simplify(Map<String, Double> env) {
        if (env.containsKey(var))
            return new Num(env.get(var));
        else
            return this;
    }
    
    @Override
    public void printSelf(String indent) {
        System.out.println(indent + "Var: "+var);
    }

    @Override
    public double numTerm() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double numFactor() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public Expression varTerm() {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Expression varFactor() {
        // TODO Auto-generated method stub
        return this;
    }
}
