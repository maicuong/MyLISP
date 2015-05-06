package AST;

import java.util.List;

import Runner.Environment;

public class ProgramExpr extends ASTList {
	public ProgramExpr(List<ASTree> c) {
		super(c);
	}

	public static ASTree create(List<ASTree> c) {
		return new ProgramExpr(c);
	}

	public String toString() {
		String block = "(";
		ASTree add = null;
		for (int i = 0; i < numChildren(); i++) {
			add = child(i);
			block = block + " " + add;
		}
		block = block + " )";
		return block;
	}

	public Object eval(Environment env) {
		Object result = 0;
		String i = "";
		for (ASTree t : this) {
			if (t != null) {
				result = ((ASTree) t).eval(env);
				i = (String) (i + "=>" + result + "\n");
			}
		}
		return i;
	}
}
