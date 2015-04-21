package AST;

import java.util.List;

import Lexer.CoolLgException;
import Runner.Environment;

public class NegativeExpr extends ASTList {
	public NegativeExpr(List<ASTree> c) {
		super(c);
	}

	public ASTree operand() {
		return child(0);
	}

	public String toString() {
		return "-" + operand();
	}

	public Object eval(Environment env) {
		Object v = ((ASTree) operand()).eval(env);

		if (v instanceof Integer)
			return new Integer(-((Integer) v).intValue());
		else
			throw new CoolLgException("bad type for -", this);
	}
}
