package scanner;

import parser.GrammarSymbols;

public class Test {
	public static void main(String[] args)
	{
		Scanner s = new Scanner();
		Token a;
		do {
			a = s.getNextToken();
			System.out.println(a.getKind());
		} while (a.getKind()!= GrammarSymbols.EOT);
	}
}
