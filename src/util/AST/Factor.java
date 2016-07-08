package util.AST;

public class Factor extends Expression {
	String id;
	String number;
	String bool;
	
	public Factor(String id, String number, String bool) {
		super();
		this.id = id;
		this.number = number;
		this.bool = bool;
	}

	@Override
	public String toString(int level) {
		
		StringBuffer factor = new StringBuffer();
		
		if (id != null) {
			factor.append(id);
		} else if (number != null) {
			factor.append(number);
		} else {
			factor.append(bool);
		}
		
		return factor.toString();
	}
}
