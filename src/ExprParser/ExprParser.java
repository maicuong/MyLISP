package ExprParser;

import java.util.ArrayList;
import java.util.Arrays;

import AST.ASTLeaf;
import AST.ASTree;
import AST.Arguments;
import AST.BinaryExpr;
import AST.BlockStmnt;
import AST.DefStmnt;
import AST.IfStmnt;
import AST.Name;
import AST.NumberLiteral;
import AST.ParameterList;
import AST.PrimaryExpr;
import AST.ProgramExpr;
import Lexer.CodeDialog;
import Lexer.Lexer;
import Lexer.ParseException;
import Lexer.Token;

public class ExprParser {
	private Lexer lexer;

	public ExprParser(Lexer p) {
		lexer = p;
	}

	public ExprParser() {

	}

	public ASTree expression() throws ParseException {
		ASTree left = null;
		ArrayList<ASTree> list = new ArrayList<ASTree>();
		while (!(isToken(Token.EOL))) {
			ASTree right = term();
			list.add(right);
			left = ProgramExpr.create(list);
			if (isToken(Token.EOL))
				token(Token.EOL);
		}
		return left;
	}

	public ASTree term() throws ParseException {
		ASTree left = null;
		ArrayList<ASTree> list = new ArrayList<ASTree>();
		if (isToken("(")) {
			token("(");
		}

		if (isBinary()) {
			while (isBinary()) {
				ASTLeaf op_term = new ASTLeaf(lexer.read());
				ASTree left_1 = factor();
				ASTree right_1 = factor();
				ASTree onemore = new BinaryExpr(Arrays.asList(left_1, op_term,
						right_1));
				if (left == null) {
					left = onemore;
					list.add(onemore);
				} else {
					list.add(onemore);
					left = BlockStmnt.create(list);
				}
			}
		} else if (lexer.peek(0).isNumber()) {
			left = factor();
		} // else if (lexer.peek(0).isIdentifier() && lexer.peek(1).equals("."))
			// {
			// left = factor();
			// token(".");
		else if (isToken("defun")) {
			token("defun");
			ASTree name = factor();
			if (isToken("("))
				token("(");
			ASTree paras = null;
			while (!(isToken(")"))) {
				ASTree parameter = factor();
				if (paras == null) {
					paras = new ParameterList(Arrays.asList(parameter));
					list.add(parameter);
				} else {
					list.add(parameter);
					paras = ParameterList.create(list);
				}
			}
			if (isToken(")"))
				token(")");
			ASTree block = term();
			BlockStmnt blo = new BlockStmnt(Arrays.asList(block));
			left = new DefStmnt(Arrays.asList(name, paras, blo));
		} else if (isToken("if")) {
			token("if");
			ASTree condition = term();
			ASTree thenblock = term();
			ASTree elseblock = term();
			left = new IfStmnt(Arrays.asList(condition, thenblock, elseblock));
		} else {
			// if (isToken("("))
			// token("(");
			ASTree name = factor();
			if (isToken(")")) {
				left = (Name) name;
				token(")");
			} else {
				if (isToken("("))
					token("(");
				ASTree paras = null;
				ASTree parameter = null;
				while (!(isToken(")"))) {
					// if (isToken("("))
					// token("(");
					if (lexer.peek(0).isIdentifier() && !(isBinary())) {
						parameter = factor();

					} else {
						parameter = term();
					}

					if (paras == null) {
						paras = new Arguments(Arrays.asList(parameter));
						list.add(parameter);
					} else {
						list.add(parameter);
						paras = Arguments.create(list);
					}
					// if (isToken(")"))
					// token(")");

				}

				if (isToken(")"))
					token(")");
				left = new PrimaryExpr(Arrays.asList(name, paras));
			}
		}

		if (isToken(")"))
			token(")");

		return left;
	}

	public ASTree factor() throws ParseException {
		if (isToken("(")) {
			token("(");
			ASTree e = term();
			if (isToken(")"))
				token(")");
			return e;
		} else {
			Token t = lexer.read();
			if (t.isNumber()) {
				NumberLiteral n = new NumberLiteral(t);
				return n;
			} else if (t.isIdentifier()) {
				Name n = new Name(t);
				return n;
			} else {
				throw new ParseException(t);
			}
		}
	}

	void token(String name) throws ParseException {
		Token t = lexer.read();
		if (!(t.isIdentifier() && name.equals(t.getText())))
			throw new ParseException(t);
	}

	boolean isBinary() throws ParseException {
		if (isToken("*") || isToken("+") || isToken("-") || isToken("/")
				|| isToken("%") || isToken(">") || isToken("<")
				|| isToken("<=") || isToken(">=") || isToken("=")
				|| isToken("setq")) {
			return true;
		} else
			return false;
	}

	boolean isToken(String name) throws ParseException {
		Token t = lexer.peek(0);
		return t.isIdentifier() && name.equals(t.getText());
	}

	public static void main(String[] args) throws ParseException {
		Lexer lexer = new Lexer(new CodeDialog());
		ExprParser p = new ExprParser(lexer);
		ASTree t = p.expression();
		System.out.println("=> " + t);
	}

}
