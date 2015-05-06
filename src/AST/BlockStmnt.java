package AST;

import java.util.List;

import Runner.Environment;

public class BlockStmnt extends ASTList {
	public BlockStmnt(List<ASTree> c) {
		super(c);
	}

	public static ASTree create(List<ASTree> c) {
		return new BlockStmnt(c);
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
			if (t != null)
				result = ((ASTree) t).eval(env);
			i = (String) (i + result);
		}
		return i;
	}
}
