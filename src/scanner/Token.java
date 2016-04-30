package scanner;

/**
 * Token class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Token {

	// The token kind
	private int kind;
	// The token spelling
	private String spelling;
	// The line and column that the token was found
	private int line, column;
	
	/**
	 * Default constructor
	 * @param kind
	 * @param spelling
	 * @param line
	 * @param column
	 */
	public Token(int kind, String spelling, int line, int column) {
		this.kind = kind;
		this.spelling = spelling;
		this.line = line;
		this.column = column;
	}

	public String getTokenType()
	{
		String tokenType = "";
		switch (getKind()) {
			case 1:
				tokenType = "OP";
				break;
			case 2:
				tokenType = "CP";
				break;
			case 3:
				tokenType = "SC";
				break;
			case 4:
				tokenType = "ASG";
				break;
			case 5:
				tokenType = "OB";
				break;
			case 6:
				tokenType = "CB";
				break;
			case 7:
				tokenType = "C";
				break;
			case 8:
				tokenType = "ARIT_OPERATOR";
				break;
			case 9:
				tokenType = "MULT_OPERATOR";
				break;
			case 10:
				tokenType = "ID";
				break;
			case 11:
				tokenType = "NUMBER";
				break;
			case 12:
				tokenType = "BOOL_OPERATOR";
				break;
			case 13:
				tokenType = "GT";
				break;
			case 14:
				tokenType = "LT";
				break;
			case 15:
				tokenType = "D";
				break;
			case 98:
				tokenType = "EOT";
				break;
			case 16:
				tokenType = "int";
				break;
			case 17:
				tokenType = "void";
				break;
			case 18:
				tokenType = "main";
				break;
			case 19:
				tokenType = "true";
				break;
			case 20:
				tokenType = "false";
				break;
			case 21:
				tokenType = "break";
				break;
			case 22:
				tokenType = "continue";
				break;
			case 23:
				tokenType = "while";
				break;
			case 24:
				tokenType = "bool";
				break;
			case 25:
				tokenType = "if";
				break;
			case 26:
				tokenType = "printf";
				break;
			case 27:
				tokenType = "else";
				break;
			case 28:
				tokenType = "return";
				break;
			}
		
		return "Token: " + tokenType + ", " + spelling + " [" + getLine() + ":" + getColumn() + "]";
	}
	
	/**
	 * Returns token kind
	 * @return
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * Returns token spelling
	 * @return
	 */
	public String getSpelling() {
		return spelling;
	}

	/**
	 * Returns the line where the token was found
	 * @return
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Returns the column where the token was found
	 * @return
	 */	
	public int getColumn() {
		return column;
	}
	
}
