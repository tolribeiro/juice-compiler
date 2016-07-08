package util.AST;

public class Expression extends AST {

	public AST left, right;
	public String operator;
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		StringBuffer expression = new StringBuffer();
		expression.append(left.toString(0));
		expression.append(" ");
		expression.append(operator);
		expression.append(" ");
		expression.append(right.toString(0));
		
		return expression.toString();
	}
}
