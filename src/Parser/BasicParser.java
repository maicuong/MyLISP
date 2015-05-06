package Parser;

import static Parser.Parser.*;

import java.util.HashSet;

import AST.ASTree;
import AST.BinaryExpr;
import AST.BlockStmnt;
import AST.IfStmnt;
import AST.Name;
import AST.NegativeExpr;
import AST.NumberLiteral;
import AST.PrimaryExpr;
import AST.StringLiteral;
import Lexer.Lexer;
import Lexer.ParseException;
import Lexer.Token;
import Parser.Parser.Operators;

public class BasicParser {
	HashSet<String> reserved = new HashSet<String>();
	Operators operators = new Operators();
	Parser expr0 = rule();
	Parser primary = rule(PrimaryExpr.class).or(
			rule().sep("(").ast(expr0).sep(")"),
			rule().number(NumberLiteral.class),
			rule().identifier(Name.class, reserved),
			rule().string(StringLiteral.class));
	Parser factor = rule().or(rule(NegativeExpr.class).sep("-").ast(primary),
			primary);
	Parser expr = expr0.expression(BinaryExpr.class, factor, operators);

	Parser statement0 = rule();
	Parser block = rule(BlockStmnt.class).option(statement0)
			.repeat(rule().sep(Token.EOL).option(statement0)).sep(")");
	Parser simple = rule(PrimaryExpr.class).ast(expr);
	Parser statement = statement0.or(rule(IfStmnt.class).sep("(").sep("if")
			.ast(expr).ast(block), simple);
	Parser program = rule().option(statement).sep(Token.EOL);

	public BasicParser() {
		reserved.add(Token.EOL);

		operators.add("=", 1, Operators.RIGHT);
		operators.add("==", 2, Operators.LEFT);
		operators.add(">", 2, Operators.LEFT);
		operators.add("<", 2, Operators.LEFT);
		operators.add("+", 3, Operators.LEFT);
		operators.add("-", 3, Operators.LEFT);
		operators.add("*", 4, Operators.LEFT);
		operators.add("/", 4, Operators.LEFT);
		operators.add("%", 4, Operators.LEFT);
	}

	public ASTree parse(Lexer lexer) throws ParseException {
		return program.parse(lexer);
	}

}
