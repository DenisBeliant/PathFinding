import javax.swing.JPanel;

public class Case {
	
	final int ROUTE = 0;
	final int EAU = 1;
	final int HERBE = 2;
	final int MUR = 3;
	final int START = 4;
	final int FINISH = 5;
	
	int type = 0;
	
	protected int x;
	protected int y;
	private String text;
	
	public Case(int type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public int getType() {
		return this.type;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void changeType(int t) {
		this.type = t;
	}
	
}
