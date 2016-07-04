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
		// TODO Auto-generated method stub
		return null;
	}

}
