package AST;

import Lexer.Token;
import Runner.Environment;

public class NumberLiteral extends ASTLeaf {
	public NumberLiteral(Token t) {
		super(t);
	}

	public int value() {
		return token().getNumber();
	}

	public Object eval(Environment e) {
		return value();
	}
}
