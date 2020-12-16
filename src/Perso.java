
public class Perso {
	private int x;
	private int y;
	private int path;
	private String text = "X";
	
	public Perso(int[] pos) {
		this.x = pos[0];
		this.y = pos[1];
	}
	
	public int[] getPos() {
		int[] arr = {this.x, this.y};
		return arr;
	}
	
	public void left() {
		this.x--;
	}
	
	public void right() {
		this.x++;
	}
	
	public void up() {
		this.y--;
	}
	
	public void down() {
		this.y++;
	}
	
	public void addPath(int val) {
		this.path += val;
	}
	
	public int getPath() {
		return this.path;
	}
	
	public String getText() {
		return this.text;
	}
	
}
