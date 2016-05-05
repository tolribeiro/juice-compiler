package parser;

import java.util.ArrayList;

import scanner.Scanner;
import scanner.Token;
import util.AST.AST;
import util.AST.Command;
import util.AST.Function;
import util.AST.Program;
import util.AST.Variable;

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
		return parseProgram();
	}

	private Program parseProgram() throws SyntacticException {
		ArrayList<Function> functions = new ArrayList<Function>();
		ArrayList<Variable> globals = new ArrayList<Variable>();
		Program p = new Program(functions, globals);
		while (currentToken.getKind() != GrammarSymbols.EOT) {
			if (currentToken.getKind() == GrammarSymbols.VOID) {
				String type = currentToken.getSpelling();
				acceptIt();
				String id = currentToken.getSpelling();
				accept(GrammarSymbols.ID);
				functions.add(parseFunction(type, id));
			} else if (currentToken.getKind() == GrammarSymbols.INT
					|| currentToken.getKind() == GrammarSymbols.BOOL) {
				String type = currentToken.getSpelling();
				acceptIt();
				String id = currentToken.getSpelling();
				accept(GrammarSymbols.ID);
				if (currentToken.getKind() == GrammarSymbols.SC) {
					acceptIt();
					globals.add(new Variable(type, id));
				} else {
					functions.add(parseFunction(type, id));
				}
			}
		}
		return p;
	}

	private Function parseFunction(String type, String id) throws SyntacticException {
		ArrayList<Variable> parameters = null;
		ArrayList<Command> commands = null;
		if (currentToken.getKind() == GrammarSymbols.OP) {
			acceptIt();
			if (currentToken.getKind() == GrammarSymbols.CP) {
				acceptIt();
			} else {
				parameters = parseParamTypes();
				accept(GrammarSymbols.CP);
			}
			accept(GrammarSymbols.OB);
			while (currentToken.getKind() != GrammarSymbols.CB) {
				// TODO
				// commands = parseCommands() returns commands list.
				parseCommand();
			}
			acceptIt(); // Quando chegar nesse ponto, já vai ser CB
		}
		Function function = new Function(parameters, commands, type, id);
		return function;
	}

	private ArrayList<Variable> parseParamTypes() throws SyntacticException {
		ArrayList<Variable> variables = new ArrayList<Variable>();
		if (currentToken.getKind() == GrammarSymbols.INT
				|| currentToken.getKind() == GrammarSymbols.BOOL) {
			String type = currentToken.getSpelling();
			acceptIt();
			String id = currentToken.getSpelling();
			variables.add(new Variable(type, id));
			accept(GrammarSymbols.ID);
		}
		while (currentToken.getKind() == GrammarSymbols.C) {
			acceptIt();
			if (currentToken.getKind() == GrammarSymbols.INT
					|| currentToken.getKind() == GrammarSymbols.BOOL) {
				String type = currentToken.getSpelling();
				acceptIt();
				String id = currentToken.getSpelling();
				variables.add(new Variable(type, id));
				accept(GrammarSymbols.ID);
			}
		}
		return variables;
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
			accept(GrammarSymbols.CB);
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
			parseMultExpression();
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
		System.out.println(currentToken.getSpelling());
		currentToken = scanner.getNextToken();
	}

	public static void main(String[] args) throws SyntacticException {
		Parser test = new Parser();
		AST program = test.parseProgram();
		System.out.println("done");
	}
}
