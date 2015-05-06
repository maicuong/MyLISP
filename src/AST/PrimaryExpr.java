package AST;

import java.util.List;

import Runner.Environment;

public class PrimaryExpr extends ASTList {
	public PrimaryExpr(List<ASTree> c) {
		super(c);
	}

	public static ASTree create(List<ASTree> c) {
		return c.size() == 1 ? c.get(0) : new PrimaryExpr(c);
	}

	public ASTree operand() {
		return child(0);
	}

	public Postfix postfix(int nest) {
		return (Postfix) child(nest);
	}

	public String toString() {
		return operand() + "" + child(1);
	}

	public boolean hasPostfix(int nest) {
		return numChildren() - nest > 1;
	}

	public Object eval(Environment env) {
		Object res = ((ASTree) operand()).eval(env);
		int n = numChildren();
		for (int i = 1; i < n; i++)
			res = postfix(i).eval(env, res);
		return res;
	}

}
