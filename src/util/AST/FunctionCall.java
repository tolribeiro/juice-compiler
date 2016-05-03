package util.AST;

import java.util.ArrayList;

public class FunctionCall extends Command {
	String id;
	ArrayList<Factor> factors;
	
	public FunctionCall(String id, ArrayList<Factor> factors) {
		super();
		this.id = id;
		this.factors = factors;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

}
