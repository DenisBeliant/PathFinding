
public class Node {
	private boolean murs[];
	private int[] pos;
	
	public Node(int[] pos, boolean[] murs) {
		this.pos = pos;
		this.murs = murs;
	}


	public boolean[] getMurs() {
		return this.murs;
	}
	
	public int[] getPos() {
		return this.pos;
	}
	
	public void setPos(int[] pos) {
		this.pos = pos;
	}
	
	public void setMurs(boolean[] murs) {
		this.murs = murs;
	}
}
