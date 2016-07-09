package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

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
		StringBuffer paramTypes = new StringBuffer();
		
		paramTypes.append(type);
		paramTypes.append(" ");
		paramTypes.append(id);
		paramTypes.append("(");
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				paramTypes.append(parameters.get(i).toString(0));
				if (i+1 != parameters.size()) paramTypes.append(",");
			}
		}
		paramTypes.append(")");
		paramTypes.append("{\n");
		if (commands != null) {
			for (int i = 0; i < commands.size(); i++) {
				paramTypes.append("\t");
				paramTypes.append(commands.get(i).toString(level+1));
				paramTypes.append("\n");
			}
		}
		paramTypes.append("}");
		
		return paramTypes.toString();
	}

	public ArrayList<Variable> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<Variable> parameters) {
		this.parameters = parameters;
	}

	public ArrayList<Command> getCommands() {
		return this.commands;
	}

	public void setCommands(ArrayList<Command> commands) {
		this.commands = commands;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object visit(Visitor vi, ArrayList<AST> list) throws SemanticException {
		return vi.visitFunction(this, list);
	}

}
