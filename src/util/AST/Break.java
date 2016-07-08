package util.AST;

public class Break extends Command{
	
	@Override
	public String toString(int level) {
		
		StringBuffer breakC = new StringBuffer();
		
		breakC.append("break;");
		
		return breakC.toString();
	}
}
