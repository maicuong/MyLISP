package AST;

import java.util.List;

import Runner.Environment;

public class ParameterList extends ASTList {
	public ParameterList(List<ASTree> c) {
		super(c);
	}

	public String name(int i) {
		return ((ASTLeaf) child(i)).token().getText();

	}

	public static ASTree create(List<ASTree> c) {
		return new ParameterList(c);
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

	public int size() {
		return numChildren();
	}

	public void eval(Environment env, int index, Object value) {
		env.putNew(name(index), value);
	}
}
