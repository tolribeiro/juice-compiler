package util.AST;

import java.util.ArrayList;

public class FunctionCall extends Command {
	public String id;
	public ArrayList<AST> parameters;
	
	public FunctionCall(String id) {
		super();
		this.id = id;
		this.parameters = null;
	}
	
	public FunctionCall(String id, ArrayList<AST> factors) {
		super();
		this.id = id;
		this.parameters = factors;
	}

	@Override
	public String toString(int level) {
		StringBuffer func = new StringBuffer();
		
		func.append("\t");
		func.append(id.toString());
		func.append("(");
		
		for (int i = 0; i < parameters.size(); i++) {
			func.append(parameters.get(i).toString(0));
			if (i+1 != parameters.size()) func.append(",");
		}
		
		func.append(")");
		func.append(";");
		
		return func.toString();
	}

}
