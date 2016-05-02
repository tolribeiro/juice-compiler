package parser;

import scanner.Scanner;
import scanner.Token;
import util.AST.AST;

/**
 * Parser class
 * 
 * @version 2010-august-29
 * @discipline Projeto de Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Parser {

	// The current token
	private Token currentToken = null;
	// The scanner
	private Scanner scanner = null;

	/**
	 * Parser constructor
	 */
	public Parser() {
		this.scanner = new Scanner();
		this.currentToken = scanner.getNextToken();
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * 
	 * @throws SyntacticException
	 */
	// TODO
	public AST parse() throws SyntacticException {
		return null;
	}

	private void parseProgram() throws SyntacticException {
		while (currentToken.getKind() != GrammarSymbols.EOT) {
			if (currentToken.getKind() == GrammarSymbols.VOID) {
				acceptIt();
				accept(GrammarSymbols.ID);
				parseFunction();
			} else if (currentToken.getKind() == GrammarSymbols.INT
					|| currentToken.getKind() == GrammarSymbols.BOOL) {
				acceptIt();
				accept(GrammarSymbols.ID);
				if (currentToken.getKind() == GrammarSymbols.SC) {
					acceptIt();
					accept(GrammarSymbols.SC);
				} else {
					parseFunction();
				}
			}
		}

	}

	private void parseFunction() throws SyntacticException {
		if (currentToken.getKind() == GrammarSymbols.OP) {
			acceptIt();
			if (currentToken.getKind() == GrammarSymbols.CP) {
				acceptIt();
			} else {
				parseParamTypes();
				accept(GrammarSymbols.CP);
			}
			accept(GrammarSymbols.OB);
			while (currentToken.getKind() != GrammarSymbols.CB) {
				parseCommand();
			}
			acceptIt(); // Quando chegar nesse ponto, já vai ser CB
		}
	}

	private void parseParamTypes() throws SyntacticException {
		if (currentToken.getKind() == GrammarSymbols.INT
				|| currentToken.getKind() == GrammarSymbols.BOOL) {
			acceptIt();
			accept(GrammarSymbols.ID);
		}
		while (currentToken.getKind() == GrammarSymbols.C) {
			acceptIt();
			if (currentToken.getKind() == GrammarSymbols.INT
					|| currentToken.getKind() == GrammarSymbols.BOOL) {
				acceptIt();
				accept(GrammarSymbols.ID);
			}
		}
	}

	private void parseCommand() throws SyntacticException {
		if (currentToken.getKind() == GrammarSymbols.IF) {
			acceptIt();
			accept(GrammarSymbols.OP);
			parseExpression();
			accept(GrammarSymbols.CP);
			accept(GrammarSymbols.OB);
			while (currentToken.getKind() != GrammarSymbols.CB) {
				parseCommand();
			}
			if (currentToken.getKind() == GrammarSymbols.ELSE) {
				acceptIt();
				accept(GrammarSymbols.OB);
				while (currentToken.getKind() != GrammarSymbols.CB) {
					parseCommand();
				}
			} else {
				acceptIt();
			}
		} else if (currentToken.getKind() == GrammarSymbols.WHILE) {
			accept(GrammarSymbols.OP);
			parseExpression();
			accept(GrammarSymbols.CP);
			accept(GrammarSymbols.OB);
			while (currentToken.getKind() != GrammarSymbols.CB) {
				parseCommand();
			}
			acceptIt();
		} else if (currentToken.getKind() == GrammarSymbols.RETURN) {
			acceptIt();
			if (currentToken.getKind() == GrammarSymbols.SC) {
				acceptIt();
			} else {
				parseExpression();
				accept(GrammarSymbols.SC);
			}
		} else if (currentToken.getKind() == GrammarSymbols.ID) {
			acceptIt();
			if (currentToken.getKind() == GrammarSymbols.ASG) {
				acceptIt();
				parseExpression();
				accept(GrammarSymbols.SC);
			} else if (currentToken.getKind() == GrammarSymbols.OP) {
				if (currentToken.getKind() == GrammarSymbols.CP) {
					acceptIt();
					accept(GrammarSymbols.SC);
				} else {
					parseExpression();
					while (currentToken.getKind() == GrammarSymbols.C) {
						parseExpression();
					}
				}
			} else {
				accept(GrammarSymbols.PRINT);
				accept(GrammarSymbols.OP);
				parseExpression();
				accept(GrammarSymbols.CP);
				accept(GrammarSymbols.SC);
			}
		} else if (currentToken.getKind() == GrammarSymbols.INT
				|| currentToken.getKind() == GrammarSymbols.BOOL) {
			acceptIt();
			accept(GrammarSymbols.ID);
			accept(GrammarSymbols.SC);
		} else if (currentToken.getKind() == GrammarSymbols.BREAK) {
			acceptIt();
			accept(GrammarSymbols.SC);
		} else if (currentToken.getKind() == GrammarSymbols.CONTINUE) {
			acceptIt();
			accept(GrammarSymbols.SC);
		} else {
			acceptIt();
			accept(GrammarSymbols.OP);
			parseExpression();
			accept(GrammarSymbols.CP);
			accept(GrammarSymbols.SC);
		}
	}

	private void parseExpression() throws SyntacticException {
		parseAritExpression();
		if (currentToken.getKind() == GrammarSymbols.EQ
				|| currentToken.getKind() == GrammarSymbols.NE
				|| currentToken.getKind() == GrammarSymbols.GT
				|| currentToken.getKind() == GrammarSymbols.GE
				|| currentToken.getKind() == GrammarSymbols.LT
				|| currentToken.getKind() == GrammarSymbols.LE) {
			acceptIt();
			parseAritExpression();
		}
	}

	private void parseAritExpression() throws SyntacticException {
		parseMultExpression();
		while (currentToken.getKind() == GrammarSymbols.ADD
				|| currentToken.getKind() == GrammarSymbols.SUB) {
			acceptIt();
		}
	}

	private void parseMultExpression() throws SyntacticException {
		parseFactor();
		while (currentToken.getKind() == GrammarSymbols.MULT
				|| currentToken.getKind() == GrammarSymbols.DIV) {
			acceptIt();
			parseFactor();
		}
	}

	private void parseFactor() throws SyntacticException {
		if (currentToken.getKind() == GrammarSymbols.ID) {
			acceptIt();
			if (currentToken.getKind() == GrammarSymbols.OP) {
				acceptIt();
				if (currentToken.getKind() == GrammarSymbols.CP) {
					acceptIt();
				} else {
					parseArgumentList();
					accept(GrammarSymbols.CP);
				}
			}
		} else if (currentToken.getKind() == GrammarSymbols.NUMBER) {
			acceptIt();
		} else if (currentToken.getKind() == GrammarSymbols.TRUE
				|| currentToken.getKind() == GrammarSymbols.FALSE) {
			acceptIt();
		} else {
			accept(GrammarSymbols.OP);
			parseExpression();
			accept(GrammarSymbols.CP);
		}
	}

	private void parseArgumentList() throws SyntacticException {
		parseExpression();
		while (currentToken.getKind() != GrammarSymbols.CP) {
			accept(GrammarSymbols.SC);
		}
	}

	/**
	 * Veririfes if the current token kind is the expected one
	 * 
	 * @param kind
	 * @throws SyntacticException
	 */
	// TODO
	private void accept(int kind) throws SyntacticException {
		if (currentToken.getKind() == kind) {
			acceptIt();
		} else {
			throw new SyntacticException("Token not Expected.", currentToken);
		}
	}

	/**
	 * Gets next token
	 */
	// TODO
	private void acceptIt() {
		currentToken = scanner.getNextToken();
	}

	public static void main(String[] args) throws SyntacticException {
		Parser test = new Parser();
		test.parseProgram();
	}
}
