import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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

public class Plateau extends JPanel{

	private static final long serialVersionUID = 1L;
	private SpringLayout layout = new SpringLayout();
	public Mur[][] cases;
	private int[] start = {0, 0};
	private int[] finish = {0, 0};
	
	private int colonnes;
	private int lignes;
	
	
	public Plateau(int lignes, int colonnes) {
		
		this.setPreferredSize(new Dimension(5000, 5000));
		//this.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
		//this.setLayout(layout);
		this.setBackground(Color.RED);
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
				
				g.fillRect(30 * this.cases[c][l].getX(), 30 * this.cases[c][l].getY(), 30, 30);
				g.setColor(new Color(163, 163, 163));
				g.drawRect(30 * c,  30 * l, 30, 30);
				if(text != null) {
					g.setColor(new Color(0, 0, 0));
					g.drawString(text, 30 * c + 10,  30 * l + 18);
				}
				
				boolean murs[] = this.cases[c][l].getMurs();
				
				g.setColor(new Color(163, 163, 163));
				
				if(murs[0]) g.fillRect(30 * this.cases[c][l].getX(), 30 * this.cases[c][l].getY(),  30, 2); // Up OK
				if(murs[1]) g.fillRect(30 * this.cases[c][l].getX() + 28, 30 * this.cases[c][l].getY(),  2 , 30); // Right OK
				if(murs[2]) g.fillRect(30 * this.cases[c][l].getX(), 30 * this.cases[c][l].getY() + 28,  30 , 2); // Down OK
				if(murs[3]) g.fillRect(30 * this.cases[c][l].getX(), 30 * this.cases[c][l].getY(),  2, 30); // Left OK
			}
		}
}
	
	public void createPlateau() {
		
		this.cases = new Mur[this.colonnes][this.lignes];
		
		boolean murs[] = {false, false, false, false};
		
		for(int i = 0; i < colonnes; i++) {
			for(int j = 0; j < lignes; j++) {
				int r = (int) (Math.random()*4);
				this.cases[i][j] = new Mur(r, i, j, murs);
			}
		}
		//this.cases[0] = new Case("Joueur", "", 0);
			
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
	
}