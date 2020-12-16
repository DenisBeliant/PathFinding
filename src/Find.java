import java.awt.event.ActionListener;
import java.util.Timer;

public class Find {
	
	private Perso perso;
	private Plateau plateau;
	private Node[][] node;
	private int algo = 0;
	
	public Find(Perso perso, Plateau plateau, int algo) {
		this.perso = perso;
		this.plateau = plateau;
		this.algo = algo;
	}
	
	public int play() {
		switch(this.algo) {
			case 0: return this.tourneDroite();
			case 1: return this.makePfs();
			default : return 500;
		}
	}
	
	public int tourneDroite() {
		int[] finish = plateau.getFinish();
		int[] posPerso = perso.getPos();
		
		boolean block = false;
		int mv = 5;
		int essais = 0;
		
		while(posPerso[0] != finish[0] || posPerso[1] != finish[1]) {
			
			essais++;
			if(essais > 500) break;
			
			plateau.cases[posPerso[0]][posPerso[1]].setText(String.valueOf(essais));
			
			boolean murs[] = this.plateau.cases[posPerso[0]][posPerso[1]].getMurs();

//			for(byte i = 0; i < 4; i++) {
//				System.out.println(murs[i]);
//			}
			
			if(posPerso[0] < plateau.getColonnes() && murs[1] == false && this.plateau.cases[posPerso[0] + 1][posPerso[1]].getText() == null && this.plateau.cases[posPerso[0] + 1][posPerso[1]].nbMurs() < 3) {
				System.out.println("mv right");
				this.perso.right();
			}
			else if(posPerso[0] > 0 && murs[3] == false && this.plateau.cases[posPerso[0] - 1][posPerso[1]].getText() == null && this.plateau.cases[posPerso[0] - 1][posPerso[1]].nbMurs() < 3) {
				System.out.println("Mv left");
				this.perso.left();
			}
			
			else if(posPerso[1] < plateau.getLignes()  && murs[2] == false && this.plateau.cases[posPerso[0]][posPerso[1] + 1].getText() == null && this.plateau.cases[posPerso[0]][posPerso[1] + 1].nbMurs() < 3) {
				System.out.println("mv down");
				this.perso.down();
			}
			else if(posPerso[1] > 0 && murs[0] == false && this.plateau.cases[posPerso[0]][posPerso[1] - 1].getText() == null && this.plateau.cases[posPerso[0]][posPerso[1] - 1].nbMurs() < 3) {
				System.out.println("mv up");
				this.perso.up();
			}
			
			posPerso = this.perso.getPos();
			
			System.out.println("Perso X : " + posPerso[0]);
			System.out.println("Perso Y : " + posPerso[1]);
			
			System.out.println("Finish X : " + finish[0]);
			System.out.println("Finish Y : " + finish[1]);
			
			System.out.println();
			System.out.println();
			
			perso.addPath(plateau.cases[posPerso[0]][posPerso[1]].getType() + 1);
			
			//plateau.repaint();
		}
		
		if(essais < 50) System.out.println("Win !");
		else System.out.println("Fail !");
		
		return perso.getPath();
	}
	
	public int makePfs() {
		int[] posPerso = perso.getPos();
		if(pfs(posPerso[0], posPerso[1])) {
			System.out.println("Ok");
			return 10;
			}
		else return 20;
	}
	
	public boolean pfs(int x, int y) {
		int[] finish = plateau.getFinish();
		
		if(x == finish[0] && y == finish[1]) return true;
		this.plateau.cases[x][y].setText("V");
		this.plateau.cases[x][y].changeType(1);
		
		if(x < this.plateau.getColonnes() - 1 && this.plateau.cases[x + 1][y].getText() == null && !this.plateau.cases[x][y].getMurs()[1]) {
			if(pfs(x + 1, y)) {
				this.plateau.cases[x][y].setText("X");
				this.plateau.cases[x][y].changeType(2);
				return true;
			}
		}
		
		if(x > 0 && this.plateau.cases[x - 1][y].getText() == null && !this.plateau.cases[x][y].getMurs()[3]) {
			if(pfs(x - 1, y)) {
				this.plateau.cases[x][y].setText("X");
				this.plateau.cases[x][y].changeType(2);
				return true;
			}
		}
		
		if(y < this.plateau.getLignes() - 1 && this.plateau.cases[x][y + 1].getText() == null && !this.plateau.cases[x][y].getMurs()[2]) {
			if(pfs(x, y + 1)) {
				this.plateau.cases[x][y].setText("X");
				this.plateau.cases[x][y].changeType(2);
				return true;
			}
		}
		
		if(y > 0 && this.plateau.cases[x][y - 1].getText() == null && !this.plateau.cases[x][y].getMurs()[0]) {
			if(pfs(x, y - 1)) {
				this.plateau.cases[x][y].setText("X");
				this.plateau.cases[x][y].changeType(2);
				return true;
			}
		}
		
		return false;
		
	}
	
	public boolean bfs() {
		int colonnes = this.plateau.getColonnes();
		int lignes = this.plateau.getLignes();
		
		this.node = new Node[colonnes][lignes];
		
		for(byte c = 0; c < colonnes; c++) {
			for(byte l = 0; l < lignes; l++) {
				int[] pos = {this.plateau.cases[c][l].getX(), this.plateau.cases[c][l].getY()};
				this.node[c][l] = new Node(pos, this.plateau.cases[c][l].getMurs());
			}
		}
		
		return true;
	}

}
