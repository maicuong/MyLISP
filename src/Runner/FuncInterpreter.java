package Runner;

import AST.ASTree;
import Lexer.Lexer;
import Lexer.ParseException;

public class FuncInterpreter {
	public static void main(String[] args) throws ParseException {
		run(new NestedEnv());
	}

	public static void run(Environment env) throws ParseException {
		Lexer lexer = new Lexer(new CodeDialog());
		ExprParser p = new ExprParser(lexer);
		ASTree t = p.expression();
		if (t != null) {
			Object r = ((ASTree) t).eval(env);
			System.out.println(r);
		}
	}
}
