import java.awt.geom.Rectangle2D;

public class Mur extends Case {

	public boolean murs[];
	
	public Mur(int type, int x, int y, boolean murs[]) {
		super(type, x, y);
		// TODO Auto-generated constructor stub
		this.murs = murs;
	}
	
	
	public boolean[] getMurs() {
		return this.murs;
	}
	
	public void setMurs(boolean[] murs) {
		this.murs = murs;
	}
	
	public int nbMurs() {
		int murs = 0;
		for(byte i = 0; i < 4; i++) {
			if(this.murs[i]) murs++;
		}
		return murs;
	}

}
