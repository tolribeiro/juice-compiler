package util.AST;

public class Return extends Command{
	Expression expression;

	public Return(Expression expression) {
		super();
		this.expression = expression;
	}
	
	@Override
	public String toString(int level) {
		StringBuffer returnC = new StringBuffer();
		
		returnC.append("return ");
		
		if (expression != null) {
			returnC.append(expression.toString(0));
		}
		
		returnC.append(";");
		
		return returnC.toString();
	}
}
