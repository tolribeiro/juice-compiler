package util.AST;


public class Variable extends Command {
	public String type;
	public String id;
	
	public Variable(String type, String id) {
		super();
		this.type = type;
		this.id = id;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return type + " " + id;
	}
}
