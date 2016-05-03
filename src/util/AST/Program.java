package util.AST;

import java.util.ArrayList;

public class Program extends AST{
	
	ArrayList<Function> functions;
	ArrayList<Variable> globals;
	
	public Program(ArrayList<Function> functions, ArrayList<Variable> globals) {
		super();
		this.functions = functions;
		this.globals = globals;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return toString(0);
	}
	
}
