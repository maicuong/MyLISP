package LISP;

public class Name extends ASTLeaf {
	public Name(Token t) {
		super(t);
	}

	public String name() {
		return token().getText();
	}

}