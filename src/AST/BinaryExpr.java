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
			return computeOp(left, op, right);
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
	}

	protected Object computeNumber(Integer a, String op, Integer b) {
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
