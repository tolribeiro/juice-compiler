package checker;

import java.util.ArrayList;

import parser.Parser;
import util.AST.AST;
import util.AST.Break;
import util.AST.Function;
import util.AST.Program;
import util.AST.While;
import util.symbolsTable.IdentificationTable;

public class Checker implements Visitor {
	IdentificationTable idTable = null;
	Parser parser = null;
	
	public Checker() {
		this.parser = new Parser(null);
		this.idTable = new IdentificationTable();
	}
	
	public AST check(Program p) {
		try {
			return (AST) p.visit(this, new ArrayList<AST>());
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object visitProgram(Program program, ArrayList<AST> list) throws SemanticException {
		for (Function func: program.getFunctions()) {
			func.visit(this, list);
		}
		
		if (idTable.retrieve("main") == null || !(idTable.retrieve("main") instanceof Function))
			throw new SemanticException("A main function must be declared");
		return program;
	}

	@Override
	public Object visitBreak(Break breakC, ArrayList<AST> list)
			throws SemanticException {
		
		for (Object o : list) {
			if (o instanceof While) {
				return null;
			}
		}
		
		throw new SemanticException("A continue command should be inside a loop.");
	}

	@Override
	public Object visitFunction(Function function, ArrayList<AST> list) {
		// TODO Auto-generated method stub
		return null;
	}
}
