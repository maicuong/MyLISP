package Parser;

import AST.ASTree;
import Lexer.CodeDialog;
import Lexer.Lexer;
import Lexer.ParseException;
import Lexer.Token;

public class ParserRunner {
	public static void main(String[] args) throws ParseException {
		Lexer l = new Lexer(new CodeDialog());
		BasicParser bp = new BasicParser();
		while (l.peek(0) != Token.EOF) {
			ASTree ast = bp.parse(l);
			System.out.println("=> " + ast.toString());
		}
	}

}
