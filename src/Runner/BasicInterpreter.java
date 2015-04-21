package Runner;

import AST.ASTree;
import AST.NullStmnt;
import Lexer.CodeDialog;
import Lexer.Lexer;
import Lexer.ParseException;
import Lexer.Token;
import Parser.BasicParser;

public class BasicInterpreter {
	public static void main(String[] args) throws ParseException {
		run(new BasicParser(), new BasicEnvironment());
	}

	public static void run(BasicParser bp, Environment env)
			throws ParseException {
		Lexer lexer = new Lexer(new CodeDialog());
		while (lexer.peek(0) != Token.EOF) {
			ASTree t = bp.parse(lexer);
			if (!(t instanceof NullStmnt)) {
				Object r = ((ASTree) t).eval(env);
				System.out.println("=> " + r);
			}
		}
	}

}
