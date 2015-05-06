package Runner;

import AST.ASTree;
import AST.NestedEnv;
import AST.NullStmnt;
import ExprParser.ExprParser;
import Lexer.CodeDialog;
import Lexer.Lexer;
import Lexer.ParseException;

public class FuncInterpreter {
	public static void main(String[] args) throws ParseException {

		run(new NestedEnv());
	}

	public static void run(Environment env) throws ParseException {
		Lexer lexer = new Lexer(new CodeDialog());
		// while (lexer.peek(0) != Token.EOF) {
		ExprParser p = new ExprParser(lexer);
		ASTree t = p.expression();
		if (!(t instanceof NullStmnt)) {
			Object r = ((ASTree) t).eval(env);
			// BasicEnvironment r = (BasicEnvironment) ((ASTree) t).eval(env);
			System.out.println(r);
			// }
		}
	}
}
