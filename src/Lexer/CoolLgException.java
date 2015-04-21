package Lexer;

import AST.ASTree;

public class CoolLgException extends RuntimeException {
	public CoolLgException(String m) {
		super(m);
	}

	public CoolLgException(String m, ASTree t) {
		super(m + " " + t.location());
	}

}
