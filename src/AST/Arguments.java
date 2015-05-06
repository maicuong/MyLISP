package AST;

import java.util.List;

import Lexer.CoolLgException;
import Runner.Environment;
import Runner.Function;

public class Arguments extends Postfix {
	public Arguments(List<ASTree> c) {
		super(c);
	}

	public int size() {
		return numChildren();
	}

	public static ASTree create(List<ASTree> c) {
		return new Arguments(c);
	}

	public Object eval(Environment callerEnv, Object value) {
		if (!(value instanceof Function))
			throw new CoolLgException("bad function", this);
		Function func = (Function) value;
		ParameterList params = func.parameters();
		if (size() != params.size())
			throw new CoolLgException("bad name of arguments", this);
		Environment newEnv = func.makeEnv();
		int num = 0;
		for (ASTree a : this)
			params.eval(newEnv, num++, a.eval(callerEnv));
		return func.body().eval(newEnv);
	}

}
