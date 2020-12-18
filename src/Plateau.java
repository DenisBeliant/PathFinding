import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Plateau extends JPanel implements MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private SpringLayout layout = new SpringLayout();
	public Mur[][] cases;
	public Find find;
	private int[] start = {0, 0};
	private int[] finish = {0, 0};
	
	private int tCase = 30;
	
	private int colonnes;
	private int lignes;
	
	
	public Plateau(int lignes, int colonnes) {
		
		this.setPreferredSize(new Dimension(5000, 5000));
		//this.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
		//this.setLayout(layout);
		this.setBackground(Color.RED);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.colonnes = colonnes;
		this.lignes = lignes;

	}

	public void paintComponent(Graphics g) {
	
		System.out.println("Call me !");
		System.out.println(colonnes);
		System.out.println(lignes);
		
		for(int c = 0; c < colonnes; c++) {
			for(int l = 0; l < lignes; l++) {
				int type = this.cases[c][l].getType();
				String text = this.cases[c][l].getText();
				
				switch(type) {
					case 0: g.setColor(new Color(29, 236, 240)); break;
					case 1: g.setColor(new Color(0, 0, 255)); break;
					case 2: g.setColor(new Color(50, 157, 35)); break;
					case 3: g.setColor(new Color(156, 161, 157)); break;
					case 4: g.setColor(new Color(255, 255, 255)); break;
					case 5: g.setColor(new Color(255, 0, 0)); break;
				default: break;
				}
				
				g.fillRect(tCase * this.cases[c][l].getX(), tCase * this.cases[c][l].getY(), tCase, tCase);
				//g.setColor(new Color(163, 163, 163));
				//g.drawRect(30 * c,  30 * l, 30, 30);
				if(text != null) {
					g.setColor(new Color(0, 0, 0));
					g.drawString(text, tCase * c + 10,  tCase * l + 18);
				}
				
				boolean murs[] = this.cases[c][l].getMurs();
				
				g.setColor(new Color(163, 163, 163));
				
				if(murs[0]) g.fillRect(tCase * this.cases[c][l].getX(), tCase * this.cases[c][l].getY(),  tCase, 2); // Up OK
				if(murs[1]) g.fillRect(tCase * this.cases[c][l].getX() + 28, tCase * this.cases[c][l].getY(),  2 , tCase); // Right OK
				if(murs[2]) g.fillRect(tCase * this.cases[c][l].getX(), tCase * this.cases[c][l].getY() + 28,  tCase, 2); // Down OK
				if(murs[3]) g.fillRect(tCase * this.cases[c][l].getX(), tCase * this.cases[c][l].getY(),  2, tCase); // Left OK
			}
		}
}
	
	public void createPlateau() {
		
		this.cases = new Mur[this.colonnes][this.lignes];
		boolean murs[]  = {false, false, false, false};
			
		for(int i = 0; i < colonnes; i++) {
			for(int j = 0; j < lignes; j++) {
				for(byte r = 0; r < 4; r++) {
					double s = Math.random()*1;
					murs[r] = s > 0.5 ? true : false;
				}
				this.cases[i][j] = new Mur(0, i, j, murs.clone());
			}
		}
			
	}
	
	public void createPlateauJson(String nb, String ex) throws ParseException {
		
		ArrayList<Mur> buff = new ArrayList<Mur>();

		
		JSONParser parser = new JSONParser();

        try {

            JSONObject arr = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + "/src/lab.json"));

            JSONObject jo = (JSONObject) arr;
            JSONObject map = (JSONObject) jo.get(nb);
            
            JSONArray arrayLab = (JSONArray) map.get(ex);
            
            Iterator<JSONObject> iterator = arrayLab.iterator();

            boolean[] walls = new boolean[4];

            while (iterator.hasNext()) {
            	
                JSONObject cell_json = iterator.next();

                // create boolean array
                JSONArray wallsArray = (JSONArray) cell_json.get("walls");
                for (int i=0; i< wallsArray.size(); i++){
                    walls[i] = (boolean)wallsArray.get(i);
                }

               buff.add(new Mur(0, ((Long) cell_json.get("posY")).intValue(), ((Long) cell_json.get("posX")).intValue(), walls.clone()));
            }
            
            System.out.println(buff.size());
            
    		this.cases = new Mur[(int) Math.sqrt(buff.size())][(int) Math.sqrt(buff.size())];
    		this.colonnes = (int) Math.sqrt(buff.size());
    		this.lignes = (int) Math.sqrt(buff.size());

    		buff.forEach((el) -> this.cases[el.getX()][el.getY()] = el);
    		
            this.cases[0][0].changeType(4);
            this.cases[this.colonnes - 1][this.lignes - 1].changeType(5);
            
    		this.start[0] = 0;
    		this.start[1] = 0;
    		
    		this.finish[0] = colonnes - 1;
    		this.finish[1] = lignes - 1;
           //loop array
           

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void startFinishRandom() {
		int xStart = (int) (Math.random() * this.colonnes);
		int yStart = (int) (Math.random() * this.lignes);
		
		int xFinish = (int) (Math.random() * this.colonnes);
		int yFinish = (int) (Math.random() * this.lignes);
		
		this.cases[yStart][xStart].changeType(4);
		this.cases[yFinish][xFinish].changeType(5);
		
		this.cases[yStart][xStart].setText("S");
		this.cases[yFinish][xFinish].setText("F");
		
		this.start[0] = yStart;
		this.start[1] = xStart;
		
		this.finish[0] = yFinish;
		this.finish[1] = xFinish;
	}
	
	public void placeStart(int pos[]) {
		this.start[0] = pos[0];
		this.start[1] = pos[1];
	}
	
	public void placeFinish(int pos[]) {
		this.finish[0] = pos[0];
		this.finish[1] = pos[1];
	}
	
	public int[] getStart() {
		return this.start;
	}
	
	public int[] getFinish() {
		return this.finish;
	}
	
	public int getColonnes() {
		return this.colonnes;
	}
	
	public int getLignes()	 {
		return this.lignes;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Click");
		
		int button = e.getButton();
		
		System.out.println(button);
		
		System.out.println("X : " + e.getX() + " Y : " + e.getY());
		
		Mur current;
		
		if((int) Math.floor(e.getX() / tCase) >= 0 && (int) Math.floor(e.getX() / tCase) < this.colonnes && (int) Math.floor(e.getY() / tCase) >= 0 && (int) Math.floor(e.getY() / tCase) < this.lignes) current = this.cases[(int) Math.floor(e.getX() / tCase)][(int) Math.floor(e.getY() / tCase)];
		else current = null;

		if(current != null) {
			if(button == 1) {
				this.cases[this.finish[0]][this.finish[1]].changeType(0);
				this.finish[0] = current.getX();
				this.finish[1] = current.getY();
				
				
				current.changeType(5);
				current.setText("");
			}
			else if(button == 2) {
				current.changeType(0);
				current.setText("");
			}
			else {
				this.cases[this.start[0]][this.start[1]].changeType(0);
				this.start[0] = current.getX();
				this.start[1] = current.getY();
				
				
				current.changeType(4);
				current.setText("");
			}
		}
		
		repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getKeyChar());
	}
	
	
}