package Runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import AST.ASTree;
import Lexer.Lexer;
import Lexer.ParseException;

public class FuncInterpreter_ReadFile {
	public static void main(String[] args) throws ParseException, IOException {

		run(new NestedEnv());
	}

	public static void run(Environment env) throws ParseException, IOException {
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		System.out.println("ファイル名を入力してください。");
		String filename = br.readLine();

		FileReader filereader = null;
		try {
			File file = new File(filename);
			filereader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

		Lexer lexer = new Lexer(filereader);
		ExprParser p = new ExprParser(lexer);
		ASTree t = p.expression();
		if (t != null) {
			Object r = ((ASTree) t).eval(env);
			System.out.println(r);
		}
	}
}
