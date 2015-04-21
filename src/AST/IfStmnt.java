package AST;

import java.util.List;

import Runner.Environment;

public class IfStmnt extends ASTList {
	public static final int TRUE = 1;
	public static final int FALSE = 0;

	public IfStmnt(List<ASTree> c) {
		super(c);
	}

	public ASTree condition() {
		return child(0);
	}

	public ASTree thenBlock() {
		return child(1);
	}

	public ASTree elseBlock() {
		return numChildren() > 2 ? child(2) : null;
	}

	public String toString() {
		return "(if " + condition() + " " + thenBlock() + " else "
				+ elseBlock() + ")";
	}

	public Object eval(Environment env) {
		Object c = ((ASTree) condition()).eval(env);
		if (c instanceof Integer && ((Integer) c).intValue() != FALSE)
			return ((ASTree) thenBlock()).eval(env);
		else {
			ASTree b = elseBlock();
			if (b == null)
				return 0;
			else
				return ((ASTree) b).eval(env);
		}
	}
}
