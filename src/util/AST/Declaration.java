package util.AST;

public class Declaration extends Command {
	Variable variable;
	
	public Declaration(Variable variable) {
		super();
		this.variable = variable;
	}
	
	@Override
	public String toString(int level) {
		StringBuffer declaration = new StringBuffer();
		
		declaration.append(variable.toString(0));
		declaration.append(";");
		
		return declaration.toString();
	}
}