package LISP;

public class StringLiteral extends ASTLeaf {
	public StringLiteral(Token t) {
		super(t);
	}

	public String value() {
		return token().getText();
	}

}
