package util.AST;

public class Expression extends Factor {
	Expression left, right;
	String operator;
	
	public Expression(String id, Expression left, Expression right,
			String operator) {
		super(id);
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}
