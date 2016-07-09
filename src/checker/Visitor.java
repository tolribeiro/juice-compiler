package checker;

import java.util.ArrayList;

import util.AST.*;

public interface Visitor {
	public Object visitProgram(Program program, ArrayList<AST> list) throws SemanticException;
	public Object visitBreak(Break breakC, ArrayList<AST> list) throws SemanticException;
	public Object visitFunction(Function function, ArrayList<AST> list);
}
