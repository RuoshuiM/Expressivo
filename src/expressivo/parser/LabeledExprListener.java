// Generated from LabeledExpr.g4 by ANTLR 4.5.1

package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LabeledExprParser}.
 */
public interface LabeledExprListener extends ParseTreeListener {
  /**
   * Enter a parse tree produced by {@link LabeledExprParser#root}.
   * @param ctx the parse tree
   */
  void enterRoot(LabeledExprParser.RootContext ctx);
  /**
   * Exit a parse tree produced by {@link LabeledExprParser#root}.
   * @param ctx the parse tree
   */
  void exitRoot(LabeledExprParser.RootContext ctx);
  /**
   * Enter a parse tree produced by the {@code MulDiv}
   * labeled alternative in {@link LabeledExprParser#expr}.
   * @param ctx the parse tree
   */
  void enterMulDiv(LabeledExprParser.MulDivContext ctx);
  /**
   * Exit a parse tree produced by the {@code MulDiv}
   * labeled alternative in {@link LabeledExprParser#expr}.
   * @param ctx the parse tree
   */
  void exitMulDiv(LabeledExprParser.MulDivContext ctx);
  /**
   * Enter a parse tree produced by the {@code AddSub}
   * labeled alternative in {@link LabeledExprParser#expr}.
   * @param ctx the parse tree
   */
  void enterAddSub(LabeledExprParser.AddSubContext ctx);
  /**
   * Exit a parse tree produced by the {@code AddSub}
   * labeled alternative in {@link LabeledExprParser#expr}.
   * @param ctx the parse tree
   */
  void exitAddSub(LabeledExprParser.AddSubContext ctx);
  /**
   * Enter a parse tree produced by the {@code Var}
   * labeled alternative in {@link LabeledExprParser#expr}.
   * @param ctx the parse tree
   */
  void enterVar(LabeledExprParser.VarContext ctx);
  /**
   * Exit a parse tree produced by the {@code Var}
   * labeled alternative in {@link LabeledExprParser#expr}.
   * @param ctx the parse tree
   */
  void exitVar(LabeledExprParser.VarContext ctx);
  /**
   * Enter a parse tree produced by the {@code Parens}
   * labeled alternative in {@link LabeledExprParser#expr}.
   * @param ctx the parse tree
   */
  void enterParens(LabeledExprParser.ParensContext ctx);
  /**
   * Exit a parse tree produced by the {@code Parens}
   * labeled alternative in {@link LabeledExprParser#expr}.
   * @param ctx the parse tree
   */
  void exitParens(LabeledExprParser.ParensContext ctx);
  /**
   * Enter a parse tree produced by the {@code Num}
   * labeled alternative in {@link LabeledExprParser#expr}.
   * @param ctx the parse tree
   */
  void enterNum(LabeledExprParser.NumContext ctx);
  /**
   * Exit a parse tree produced by the {@code Num}
   * labeled alternative in {@link LabeledExprParser#expr}.
   * @param ctx the parse tree
   */
  void exitNum(LabeledExprParser.NumContext ctx);
}