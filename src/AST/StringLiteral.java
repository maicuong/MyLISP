package AST;

import Lexer.Token;
import Runner.Environment;

public class StringLiteral extends ASTLeaf {
	public StringLiteral(Token t) {
		super(t);
	}

	public String value() {
		return token().getText();
	}

	public Object eval(Environment e) {
		return value();
	}
}
