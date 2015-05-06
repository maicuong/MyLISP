package AST;

import Lexer.CoolLgException;
import Lexer.Token;
import Runner.Environment;

public class Name extends ASTLeaf {
	public Name(Token t) {
		super(t);
	}

	public String name() {
		return token().getText();
	}

	public Object eval(Environment env) {
		Object value = env.get(name());
		if (value == null)
			throw new CoolLgException("undefined name: " + name(), this);
		else {
			return value;
		}
	}

}
