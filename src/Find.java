import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
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
			case 2: return this.bfs();
			default : return 500;
		}
	}
	
	public int tourneDroite() {
		int[] finish = plateau.getFinish();
		int[] posPerso = perso.getPos();
		
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
			return 1;
			}
		else return 2;
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
	
	private void rempliCasePossible(Mur kaze,Queue <Mur> file) {
        if (kaze.getMurs()[0] == false && this.plateau.cases[kaze.getX()][kaze.getY() - 1].getText() == null) {
            file.add(this.plateau.cases[kaze.getX()][kaze.getY() - 1]);
        } 
        if (kaze.getMurs()[1] == false && this.plateau.cases[kaze.getX() + 1][kaze.getY()].getText() == null) {
            file.add(this.plateau.cases[kaze.getX() + 1][kaze.getY()]);
        } 
        if (kaze.getMurs()[2] == false && this.plateau.cases[kaze.getX()][kaze.getY() + 1].getText() == null){
            file.add(this.plateau.cases[kaze.getX()][kaze.getY() + 1]);
        }
        if (kaze.getMurs()[3] == false && this.plateau.cases[kaze.getX() - 1][kaze.getY()].getText() == null) {
            file.add(this.plateau.cases[kaze.getX() - 1][kaze.getY()]);
        }
    }
    private int bfs() {
    	Mur caseD = this.plateau.cases[this.plateau.getStart()[0]][this.plateau.getStart()[1]];
    	Mur caseF = this.plateau.cases[this.plateau.getFinish()[0]][this.plateau.getFinish()[1]];
    	
        Mur caseActuelle  = caseD;
        Queue<Mur> casesEnCour = new LinkedList<>();
        casesEnCour.add(caseActuelle);
        
        while (caseActuelle  != caseF) {
        	
            caseActuelle = casesEnCour.peek();
            caseActuelle.setText("X");
            rempliCasePossible(caseActuelle, casesEnCour);
            casesEnCour.remove();
            
        }
        System.out.println("gagne");
        return 10;
    }

}
