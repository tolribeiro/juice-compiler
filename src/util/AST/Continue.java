package util.AST;

public class Continue extends Command{
	@Override
	public String toString(int level) {
		
		StringBuffer continueC = new StringBuffer();
		
		continueC.append("\tcontinue;");
		
		return continueC.toString();
	}
}
