package util.AST;

public class Return extends Command{
	Expression expression;

	public Return(Expression expression) {
		super();
		this.expression = expression;
	}
	
}
