package scanner;

import java.util.HashMap;
import java.util.Map;

import parser.GrammarSymbols;

public class Test {
	public static void main(String[] args)
	{
		Scanner s = new Scanner();
		Token a;
		do {
			a = s.getNextToken();
//			System.out.println(a.getKind());
			System.out.println(a.getTokenType());
		} while (a.getKind()!= GrammarSymbols.EOT);
	}
}
