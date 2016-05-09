package util.AST;

public class Factor extends AST {
	String id;
	int number;
	boolean bool;
	
	public Factor(String id) {
		super();
		this.id = id;
	}
	
	public Factor(boolean bool) {
		super();
		this.bool = bool;
	}

	public Factor(int number) {
		super();
		this.number = number;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
