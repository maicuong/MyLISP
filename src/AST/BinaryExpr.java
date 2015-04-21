package AST;

import java.util.List;

import Lexer.CoolLgException;
import Runner.Environment;

public class BinaryExpr extends ASTList {
	public static final int TRUE = 1;
	public static final int FALSE = 0;

	public BinaryExpr(List<ASTree> c) {
		super(c);
	}

	public ASTree left() {
		return child(0);
	}

	public String operator() {
		return ((ASTLeaf) child(1)).token().getText();
	}

	public ASTree right() {
		return child(2);
	}

	public Object eval(Environment env) {
		String op = operator();
		if ("=".equals(op)) {
			Object right = ((ASTree) right()).eval(env);
			return computeAssign(env, right);
		} else {
			Object left = ((ASTree) left()).eval(env);
			Object right = ((ASTree) right()).eval(env);
			return computeOp(left, op, right);
		}
	}

	protected Object computeAssign(Environment env, Object rvalue) {
		ASTree l = left();
		if (l instanceof Name) {
			env.put(((Name) l).name(), rvalue);
			return rvalue;
		} else
			throw new CoolLgException("bad assignment", this);
	}

	protected Object computeOp(Object left, String op, Object right) {
		if (left instanceof Integer && right instanceof Integer) {
			return computeNumber((Integer) left, op, (Integer) right);
		} else if (op.equals("+"))
			return String.valueOf(left) + String.valueOf(right);
		else if (op.equals("==")) {
			if (left == null)
				return right == null ? TRUE : FALSE;
			else
				return right == null ? TRUE : FALSE;
		} else
			throw new CoolLgException("bad type", this);
	}

	protected Object computeNumber(Integer left, String op, Integer right) {
		int a = left.intValue();
		int b = right.intValue();
		if (op.equals("+"))
			return a + b;
		else if (op.equals("="))
			return a - b;
		else if (op.equals("*"))
			return a * b;
		else if (op.equals("/"))
			return a / b;
		else if (op.equals("%"))
			return a % b;
		else if (op.equals("=="))
			return a == b ? TRUE : FALSE;
		else if (op.equals(">"))
			return a > b ? TRUE : FALSE;
		else if (op.equals("<"))
			return a < b ? TRUE : FALSE;
		else
			throw new CoolLgException("bad operator", this);
	}

}
