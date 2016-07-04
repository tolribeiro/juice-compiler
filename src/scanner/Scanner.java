package scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import parser.GrammarSymbols;
import util.Arquivo;
import compiler.Properties;

/**
 * Scanner class
 * 
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Scanner {

	// The file object that will be used to read the source code
	private Arquivo file;
	// The last char read from the source code
	private char currentChar;
	// The kind of the current token
	private int currentKind;
	// Buffer to append characters read from file
	private StringBuffer currentSpelling;
	// Current line and column in the source file
	private int line, column;

	/**
	 * Default constructor
	 * @throws IOException 
	 */
	public Scanner(String in) {
		try {
			File temp = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
			FileWriter writer = new FileWriter(temp);
			try (BufferedReader br = new BufferedReader(new FileReader(in))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			       int i = line.indexOf("//");
			       if (i == -1) {
			    	   writer.write(line);
			       } else {
			    	   writer.write(line.substring(0, i));
			       }
			       writer.write("\n");
			    }
			}
			writer.close();
			
			this.file = new Arquivo(temp.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.line = 0;
		this.column = 0;
		this.currentChar = this.file.readChar();
	}

	/**
	 * Returns the next token
	 * 
	 * @return
	 */
	// TODO
	public Token getNextToken() {
		// Initializes the string buffer
		// Ignores separators
		// Clears the string buffer
		// Scans the next token
		// Creates and returns a token for the lexema identified
		currentSpelling = new StringBuffer("");
		try {
			if (this.file.isEndOfFile()) {
				//this.currentChar = '\000';
				currentKind = GrammarSymbols.EOT;
			} else {
				while (isSeparator(currentChar)) {
					scanSeparator();
				}
				currentSpelling = new StringBuffer("");
				currentKind = scanToken();
			}
		} catch (LexicalException exception) {
			System.err.println(exception.toString());
			System.exit(0);
		}
		return new Token(currentKind, currentSpelling.toString(), line, column);
	}

	/**
	 * Returns if a character is a separator
	 * 
	 * @param c
	 * @return
	 */
	private boolean isSeparator(char c) {
		if (c == '#' || c == ' ' || c == '\n' || c == '\t') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Reads (and ignores) a separator
	 * 
	 * @throws LexicalException
	 */
	// TODO
	private void scanSeparator() throws LexicalException {
		if (currentChar == '#') {
			getNextChar();
			while (isGraphic(currentChar)) {
				getNextChar();
			}
			if (currentChar == '\n') {
				getNextChar();
			} else {
				throw new LexicalException("Character not expected.", currentChar, line, column);
			}
		} else {
			this.getNextChar();
		}
	}

	/**
	 * Gets the next char
	 */
	private void getNextChar() {
		// Appends the current char to the string buffer
		this.currentSpelling.append(this.currentChar);
		// Reads the next one
		this.currentChar = this.file.readChar();	
		// Increments the line and column
		this.incrementLineColumn();
	}

	/**
	 * Increments line and column
	 */
	private void incrementLineColumn() {
		// If the char read is a '\n', increments the line variable and assigns
		// 0 to the column
		if (this.currentChar == '\n') {
			this.line++;
			this.column = 0;
			// If the char read is not a '\n'
		} else {
			// If it is a '\t', increments the column by 4
			if (this.currentChar == '\t') {
				this.column = this.column + 4;
				// If it is not a '\t', increments the column by 1
			} else {
				this.column++;
			}
		}
	}

	/**
	 * Returns if a char is a digit (between 0 and 9)
	 * 
	 * @param c
	 * @return
	 */
	private boolean isDigit(char c) {
		if (c >= '0' && c <= '9') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns if a char is a letter (between a and z or between A and Z)
	 * 
	 * @param c
	 * @return
	 */
	private boolean isLetter(char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns if a char is a graphic (any ASCII visible character)
	 * 
	 * @param c
	 * @return
	 */
	private boolean isGraphic(char c) {
		if (c >= ' ' && c <= '~') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Scans the next token Simulates the DFA that recognizes the language
	 * described by the lexical grammar
	 * 
	 * @return
	 * @throws LexicalException
	 */
	// TODO
	private int scanToken() throws LexicalException {
		// The initial automata state is 0
		// While loop to simulate the automata
		int state = 0;

		while (true) {
			switch (state) {
			case 0:
				if (currentChar == '(') {
					state = 1;
					getNextChar();
				} else if (currentChar == ')') {
					state = 2;
					getNextChar();
				} else if (currentChar == ';') {
					state = 3;
					getNextChar();
				} else if (currentChar == '=') {
					state = 4;
					getNextChar();
				} else if (currentChar == '{') {
					state = 5;
					getNextChar();
				} else if (currentChar == '}') {
					state = 6;
					getNextChar();
				} else if (currentChar == '{') {
					state = 5;
					getNextChar();
				} else if (currentChar == '}') {
					state = 6;
					getNextChar();
				} else if (currentChar == ',') {
					state = 7;
					getNextChar();
				} else if (currentChar == '+') {
					state = 8;
					getNextChar();
				} else if (currentChar == '-') {
					state = 36;
					getNextChar();
				} else if (currentChar == '*') {
					state = 39;
					getNextChar();
				} else if (currentChar == '>') {
					state = 12;
					getNextChar();
				} else if(currentChar == '<') {
					state = 13;
					getNextChar();
				} else if(currentChar == '!') {
					state = 14;
					getNextChar();
				} else if (isLetter(currentChar)) {
					state = 10;
					getNextChar();
				} else if(isDigit(currentChar)) {
					state = 15;
					getNextChar();
				} else if(currentChar == '\000') {
					state = 98;
				} else {
					state = 99;
				}
				break;
			case 1:
				return GrammarSymbols.OP;
			case 2:
				return GrammarSymbols.CP;
			case 3:
				return GrammarSymbols.SC;
			case 4:
				if (currentChar == '=') {
					state = 30;
					getNextChar();
				} else {
					return GrammarSymbols.ASG;
				}
				break;
			case 30:
				return GrammarSymbols.EQ;
			case 5:
				return GrammarSymbols.OB;
			case 6:
				return GrammarSymbols.CB;
			case 7:
				return GrammarSymbols.C;
			case 8:
				return GrammarSymbols.ADD;
			case 36:
				return GrammarSymbols.SUB;
			case 39:
				return GrammarSymbols.MULT;
			case 40:
				return GrammarSymbols.DIV;
			case 9:
				return GrammarSymbols.MULT_OPERATOR;
			case 10:
				while (isLetter(currentChar) || isDigit(currentChar)) {
					getNextChar();
				}
				String spelling = currentSpelling.toString();
				if (spelling.equals("int")) {
					return GrammarSymbols.INT;
				} else if (spelling.equals("void")) {
					return GrammarSymbols.VOID;
//				} else if (spelling.equals("main")) {
//					return GrammarSymbols.MAIN;
				} else if (spelling.equals("true")) {
					return GrammarSymbols.TRUE;
				} else if (spelling.equals("false")) {
					return GrammarSymbols.FALSE;
				} else if (spelling.equals("break")) {
					return GrammarSymbols.BREAK;
				} else if (spelling.equals("continue")) {
					return GrammarSymbols.CONTINUE;
				} else if (spelling.equals("while")) {
					return GrammarSymbols.WHILE;
				} else if (spelling.equals("bool")) {
					return GrammarSymbols.BOOL;
				} else if (spelling.equals("if")) {
					return GrammarSymbols.IF;
				} else if (spelling.equals("else")) {
					return GrammarSymbols.ELSE;
				} else if (spelling.equals("return")) {
					return GrammarSymbols.RETURN;
				} else if (spelling.equals("printf")) {
					return GrammarSymbols.PRINT;
				} else {
					return GrammarSymbols.ID;
				}
			case 11:
				return GrammarSymbols.BOOL_OPERATOR;
			case 12: 
				if (currentChar == '=') {
					state = 31;
					getNextChar();
				} else {
					return GrammarSymbols.GT;
				}
				break;
			case 31:
				return GrammarSymbols.GE;
			case 13:
				if (currentChar == '=') {
					state = 32;
					getNextChar();
				} else {
					return GrammarSymbols.LT;
				}
				break;
			case 32:
				return GrammarSymbols.LE;
			case 14:
				if (currentChar == '=') {
					state = 33;
					getNextChar();
				} else {
					state = 99;
				}
				break;
			case 33:
				return GrammarSymbols.NE;
			case 15:
				while (isDigit(currentChar)) {
					getNextChar();
				}
				return GrammarSymbols.NUMBER;
			case 98:
				return GrammarSymbols.EOT;
			case 99:
				throw new LexicalException("Character not expected.", currentChar, line, column);
			}
		}
	}
}
