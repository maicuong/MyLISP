package AST;

import java.util.List;

import Lexer.CoolLgException;
import Runner.Environment;

public class BinaryExpr extends ASTList {
	public static final int T = 1;
	public static final int Nil = 0;

	public BinaryExpr(List<ASTree> c) {
		super(c);
	}

	public String operator() {
		return ((ASTLeaf) child(1)).token().getText();
	}

	public ASTree left() {
		return child(0);
	}

	public ASTree right() {
		return child(2);
	}

	public Object eval(Environment env) {
		String op = operator();
		if ("setq".equals(op)) {
			Object right = ((ASTree) right()).eval(env);
			return computeAssign(env, right);
		} else {
			Object left = ((ASTree) left()).eval(env);
			Object right = ((ASTree) right()).eval(env);
			// System.out.println(left);
			// int a = Integer.parseInt((String) left);
			// int b = Integer.parseInt((String) right);
			return computeOp(left, op, right);
			// Object left = ((ASTree) left()).eval(env);
			// Object right = right().eval(env);
			// int a = ((Integer) left).intValue();
			// int b = ((Integer) right).intValue();
			// String a = (String) left.get("a");
			// BasicEnvironment e = new BasicEnvironment();
			// e.put(a, 1);
			// return computeNumber(a, op, b);
			// return e;
		}

	}

	protected Object computeAssign(Environment env, Object rvalue) {
		ASTree l = left();
		if (l instanceof Name) {
			env.put(((Name) l).name(), rvalue);
			return (String) (((Name) l).name() + "->" + rvalue);
		} else
			throw new CoolLgException("bad assignment", this);
	}

	protected Object computeOp(Object left, String op, Object right) {
		if (left instanceof Integer && right instanceof Integer) {
			return computeNumber((Integer) left, op, (Integer) right);
		} else {
			int a, b;
			if (!(left instanceof Integer)) {
				a = Integer.valueOf((String) left);
			} else {
				a = (int) left;
			}
			if (!(right instanceof Integer)) {
				b = Integer.valueOf((String) right);
			} else {
				b = (int) right;
			}
			return computeNumber(a, op, b);
		}
		// else if (op.equals("==")) {
		// if (left == null)
		// return right == null ? TRUE : FALSE;
		// else
		// return right == null ? TRUE : FALSE;
		// else
		// throw new CoolLgException("bad type", this);
	}

	protected Object computeNumber(Integer a, String op, Integer b) {
		// int a = left.intValue();
		// int b = right.intValue();
		if (op.equals("+"))
			return a + b;
		else if (op.equals("-"))
			return a - b;
		else if (op.equals("*"))
			return a * b;
		else if (op.equals("/"))
			return a / b;
		else if (op.equals("%"))
			return a % b;
		else if (op.equals("="))
			return a == b ? "T" : "Nil";
		else if (op.equals(">"))
			return a > b ? "T" : "Nil";
		else if (op.equals("<"))
			return a < b ? "T" : "Nil";
		else if (op.equals(">="))
			return a >= b ? "T" : "Nil";
		else if (op.equals("<="))
			return a <= b ? "T" : "Nil";
		else
			throw new CoolLgException("bad operator", this);
	}

}
