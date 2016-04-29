package parser;


/**
 * This class contains codes for each grammar terminal
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class GrammarSymbols {

	// Language terminals (starts from 0)
	public static final int OP = 1, CP = 2, SC = 3, ASG = 4, OB = 5, CB = 6, 
							C = 7, ARIT_OPERATOR = 8, MULT_OPERATOR = 9, ID = 10, 
							NUMBER = 11, BOOL_OPERATOR = 12, GT = 13, LT = 14, D = 15, EOT = 98;
	// Reserved words
	public static final int INT = 16;
	public static final int VOID = 17;
	public static final int MAIN = 18;
	public static final int TRUE = 19;
	public static final int FALSE = 20;
	public static final int BREAK = 21;
	public static final int CONTINUE = 22;
	public static final int WHILE = 23;
	public static final int BOOL = 24;
	public static final int IF = 25; 
	public static final int PRINT = 26;
}
