/**
 * 
 */
package expressivo;

import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import expressivo.parser.LabeledExprBaseListener;
import expressivo.parser.LabeledExprLexer;
import expressivo.parser.LabeledExprParser;
import expressivo.parser.LabeledExprParser.AddSubContext;
import expressivo.parser.LabeledExprParser.MulDivContext;
import expressivo.parser.LabeledExprParser.NumContext;
import expressivo.parser.LabeledExprParser.ParensContext;
import expressivo.parser.LabeledExprParser.RootContext;
import expressivo.parser.LabeledExprParser.VarContext;

/**
 * Helper class to parse expressions and build ASTs
 */
public class Parser {

    
    public static Expression parse(String input) {
        CharStream stream = new ANTLRInputStream(input);
        try {
            
            LabeledExprParser parser = makeParser(stream);
            
            ParseTree tree = parser.root();
            
            ParseTreeWalker walker = new ParseTreeWalker();
            MakeExpr listener = new MakeExpr();
            walker.walk(listener, tree);
            
            return listener.getExpr();
            
        } catch (ParseCancellationException e) {
            throw new IllegalArgumentException("Syntax error. Try again:");
        }
    }
    /**
     * Make a parser ready to parse a stream of characters
     * During parsing, if the parser encounters a syntax error, it will throw a
     * ParseCancellationException.
     */
    private static LabeledExprParser makeParser(CharStream stream) {

        LabeledExprLexer lexer = new LabeledExprLexer(stream);
        lexer.reportErrorsAsExceptions();
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);
        parser.reportErrorsAsExceptions();
        
        return parser;
    }
}

/** Make a Expresion value from a parse tree. */
class MakeExpr extends LabeledExprBaseListener {
    private Stack<Expression> stack = new Stack<>();
    // Invariant: stack contains the Expression value of each parse
    // subtree that has been fully-walked so far, but whose parent has not yet
    // been exited by the walk. The stack is ordered by recency of visit, so that
    // the top of the stack is the Expression for the most recently walked
    // subtree.
    //
    // At the start of the walk, the stack is empty, because no subtrees have
    // been fully walked.
    //
    // Whenever a node is exited by the walk, the Expression values of its
    // children are on top of the stack, in order with the last child on top. To
    // preserve the invariant, we must pop those child Expression values
    // from the stack, combine them with the appropriate Expression
    // producer, and push back an Expression value representing the entire
    // subtree under the node.
    //
    // At the end of the walk, after all subtrees have been walked and the the
    // root has been exited, only the entire tree satisfies the invariant's
    // "fully walked but parent not yet exited" property, so the top of the stack
    // is the IntegerExpression of the entire parse tree.
    
    /**
     * Returns the expression constructed by this listener object.
     * Requires that this listener has completely walked over an Expression
     * parse tree using ParseTreeWalker.
     * @return IntegerExpression for the parse tree that was walked
     */
    public Expression getExpr() {
        return stack.get(0);
    }
    
    /** root : expr EOF */
    @Override
    public void exitRoot(RootContext ctx) {
     // do nothing, root has only one child so its value is 
        // already on top of the stack
    }

    /** expr op=('*'|'/') expr */
    @Override
    public void exitMulDiv(MulDivContext ctx) {
        // this pattern always has 2 children
        assert ctx.expr().size() == 2;
        if (ctx.op.getType() == LabeledExprParser.MUL) {
            // reverse the order of a stack
            Expression first = stack.pop(), second = stack.pop();
            stack.add(Expression.times(second, first));
        } else {
            throw new IllegalArgumentException("Operation \"/\" is not currently supported");
        }
    }
    
    /** expr op=('+'|'-') expr */
    @Override
    public void exitAddSub(AddSubContext ctx) {
        // this pattern always has 2 children
        assert ctx.expr().size() == 2;
        if (ctx.op.getType() == LabeledExprParser.ADD) {
            // reverse the order of a stack
            Expression first = stack.pop(), second = stack.pop();
            stack.add(Expression.plus(second, first));
        } else {
            throw new IllegalArgumentException("Operation \"-\" is not currently supported");
        }
    }

    /** VAR */
    @Override
    public void exitVar(VarContext ctx) {
        stack.push(new Var(ctx.getText()));
    }


    /** NUM */
    @Override
    public void exitNum(NumContext ctx) {
        stack.push(new Num(Double.valueOf(ctx.getText())));
    }

    /** '(' expr ')' */
    @Override
    public void exitParens(ParensContext ctx) {
     // do nothing, because expr's value is already on the stack
    }
}
