package util.AST;

import java.util.ArrayList;

public class Function extends  AST {

	ArrayList<Variable> parameters;
	ArrayList<Command> commands;
	String type;
	String id;
	
	public Function(ArrayList<Variable> parameters,
			ArrayList<Command> commands, String type, String id) {
		super();
		this.parameters = parameters;
		this.commands = commands;
		this.type = type;
		this.id = id;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

}
