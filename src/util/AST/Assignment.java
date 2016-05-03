package util.AST;

public class Assignment extends Command{
	Expression expression;
	String id;
	
	public Assignment(Expression expression, String id) {
		super();
		this.expression = expression;
		this.id = id;
	}
	
}
