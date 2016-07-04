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
		return toString();
	}

	@Override
	public String toString() {
		StringBuffer text = new StringBuffer();
		
		for (int i = 0; i < globals.size(); i++) {
			text.append(globals.get(i).toString(0));
			text.append(";\n");
		}
		
		for (int i = 0; i < functions.size(); i++) {
			text.append(functions.get(i).toString(0));
			text.append("\n");
		}
		
		return text.toString();
	}
	
}
