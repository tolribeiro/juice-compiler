package parser;

import java.util.ArrayList;

import compiler.Properties;

import scanner.Scanner;
import scanner.Token;
import util.AST.*;

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
	public Parser(String in) {
		this.scanner = new Scanner(in);
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
		ArrayList<Command> commands = new ArrayList<Command>();
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
				commands.add(parseCommand());
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

	private Command parseCommand() throws SyntacticException {
		if (currentToken.getKind() == GrammarSymbols.IF) {
			acceptIt();
			accept(GrammarSymbols.OP);
			
			ArrayList<Command> then = new ArrayList<Command>();
			ArrayList<Command> otherwise = new ArrayList<Command>();
			If branch = new If((Expression) parseExpression(), then, otherwise);
			
			accept(GrammarSymbols.CP);
			accept(GrammarSymbols.OB);
			while (currentToken.getKind() != GrammarSymbols.CB) {
				then.add(parseCommand());
			}
			accept(GrammarSymbols.CB);
			if (currentToken.getKind() == GrammarSymbols.ELSE) {
				acceptIt();
				accept(GrammarSymbols.OB);
				while (currentToken.getKind() != GrammarSymbols.CB) {
					otherwise.add(parseCommand());
				}
				accept(GrammarSymbols.CB);
			}
			return branch;
		} else if (currentToken.getKind() == GrammarSymbols.WHILE) {
			acceptIt();
			accept(GrammarSymbols.OP);

			ArrayList<Command> then = new ArrayList<Command>();
			While repeat = new While((Expression) parseExpression(), then);

			accept(GrammarSymbols.CP);
			accept(GrammarSymbols.OB);
			while (currentToken.getKind() != GrammarSymbols.CB) {
				then.add(parseCommand());
			}
			acceptIt();
			
			return repeat;
		} else if (currentToken.getKind() == GrammarSymbols.RETURN) {
			acceptIt();
			if (currentToken.getKind() == GrammarSymbols.SC) {
				acceptIt();
				return new Return(null);
			} else {
				Expression expression = (Expression)parseExpression();
				accept(GrammarSymbols.SC);
				return new Return(expression);
			}
		} else if (currentToken.getKind() == GrammarSymbols.ID) {
			String id = currentToken.getSpelling();
			acceptIt();
			if (currentToken.getKind() == GrammarSymbols.ASG) {
				acceptIt();
				Expression expression = (Expression) parseExpression();
				accept(GrammarSymbols.SC);
				return new Assignment(expression, id);
			} else if (currentToken.getKind() == GrammarSymbols.OP) {
				ArrayList<AST> arguments = null;
				if (currentToken.getKind() == GrammarSymbols.CP) {
					acceptIt();
					accept(GrammarSymbols.SC);
				} else {
					arguments = new ArrayList<AST>();
					arguments.add(parseExpression());
					while (currentToken.getKind() == GrammarSymbols.C) {
						arguments.add(parseExpression());
					}
				}
				return new FunctionCall(id, arguments);
			} else {
				ArrayList<AST> arguments = new ArrayList<AST>();
				accept(GrammarSymbols.PRINT);
				accept(GrammarSymbols.OP);
				arguments.add(parseExpression());
				accept(GrammarSymbols.CP);
				accept(GrammarSymbols.SC);
				return new FunctionCall("print", arguments);
			}
		} else if (currentToken.getKind() == GrammarSymbols.INT
				|| currentToken.getKind() == GrammarSymbols.BOOL) {
			String type = currentToken.getSpelling();
			acceptIt();
			String id = currentToken.getSpelling();
			accept(GrammarSymbols.ID);
			accept(GrammarSymbols.SC);
			return new Variable(type, id);
		} else if (currentToken.getKind() == GrammarSymbols.BREAK) {
			acceptIt();
			accept(GrammarSymbols.SC);
			return new Break();
		} else if (currentToken.getKind() == GrammarSymbols.CONTINUE) {
			acceptIt();
			accept(GrammarSymbols.SC);
			return new Continue();
		} else {
			throw new SyntacticException("Do not know what this code does!", currentToken);
//			acceptIt();
//			accept(GrammarSymbols.OP);
//			parseExpression();
//			accept(GrammarSymbols.CP);
//			accept(GrammarSymbols.SC);
		}
//		return null;
	}

	private AST parseExpression() throws SyntacticException {
		AST expression = parseAritExpression();
		if (currentToken.getKind() == GrammarSymbols.EQ
				|| currentToken.getKind() == GrammarSymbols.NE
				|| currentToken.getKind() == GrammarSymbols.GT
				|| currentToken.getKind() == GrammarSymbols.GE
				|| currentToken.getKind() == GrammarSymbols.LT
				|| currentToken.getKind() == GrammarSymbols.LE) {
			
			Expression booleanExpression = new Expression();
			booleanExpression.left = expression;
			
			booleanExpression.operator = currentToken.getSpelling();
			acceptIt();
			
			booleanExpression.right = parseAritExpression();
			
			expression = booleanExpression;
		}
		return expression;
	}

	private AST parseAritExpression() throws SyntacticException {
		AST expression = parseMultExpression();
		while (currentToken.getKind() == GrammarSymbols.ADD
				|| currentToken.getKind() == GrammarSymbols.SUB) {
			Expression aritExpression = new Expression();
			aritExpression.left = expression;
			
			aritExpression.operator = currentToken.getSpelling();
			acceptIt();
			
			aritExpression.right = parseMultExpression();
			
			expression = aritExpression;
		}
		return expression;
	}

	private AST parseMultExpression() throws SyntacticException {
		AST expression = parseFactor();
		while (currentToken.getKind() == GrammarSymbols.MULT
				|| currentToken.getKind() == GrammarSymbols.DIV) {
			
			Expression multExpression = new Expression();
			multExpression.left = expression;
			multExpression.operator = currentToken.getSpelling();
			acceptIt();
			multExpression.right = parseFactor();
			
			expression = multExpression;
		}
		return expression;
	}

	private AST parseFactor() throws SyntacticException {
		if (currentToken.getKind() == GrammarSymbols.ID) {
			String id = currentToken.getSpelling();
			acceptIt();
			if (currentToken.getKind() == GrammarSymbols.OP) {
				acceptIt();
				FunctionCall call = new FunctionCall(id);
				if (currentToken.getKind() == GrammarSymbols.CP) {
					acceptIt();
				} else {
					call.parameters = parseArgumentList();
					accept(GrammarSymbols.CP);
				}
				return call;
			}
			return new Factor(id);
		} else if (currentToken.getKind() == GrammarSymbols.NUMBER) {
			Factor factor = new Factor(Integer.parseInt(currentToken.getSpelling()));
			acceptIt();
			return factor;
		} else if (currentToken.getKind() == GrammarSymbols.TRUE) {
			Factor factor = new Factor(true);
			acceptIt();
			return factor;
		} else if (currentToken.getKind() == GrammarSymbols.FALSE) {
			Factor factor = new Factor(false);
			acceptIt();
			return factor;
		} else {
			accept(GrammarSymbols.OP);
			AST expression = parseExpression();
			accept(GrammarSymbols.CP);
			return expression;
		}
	}

	private ArrayList<AST> parseArgumentList() throws SyntacticException {
		ArrayList<AST> arguments = new ArrayList<AST>();
		arguments.add(parseExpression());
		while (currentToken.getKind() != GrammarSymbols.CP) {
			accept(GrammarSymbols.SC);
			arguments.add(parseExpression());
		}
		return arguments;
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
//		System.out.println(currentToken.getSpelling());
		currentToken = scanner.getNextToken();
	}

	public static void main(String[] args) throws SyntacticException {
		Parser test = new Parser(Properties.sourceCodeLocation);
		AST program = test.parse();
		System.out.println(program.toString());
	}
}
