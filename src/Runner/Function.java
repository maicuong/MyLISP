package Runner;

import AST.BlockStmnt;
import AST.ParameterList;

public class Function {
	protected ParameterList parameters;
	protected BlockStmnt body;
	protected Environment env;

	public Function(ParameterList parameters, BlockStmnt body, Environment env) {
		this.parameters = parameters;
		this.body = body;
		this.env = env;
	}

	public ParameterList parameters() {
		return parameters;
	}

	public BlockStmnt body() {
		return body;
	}

	public Environment makeEnv() {
		return new NestedEnv(env);
	}

	public String toString() {
		return "<fun:" + hashCode() + ">";
	}
}
