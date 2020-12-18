import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SpringLayout;
import javax.swing.text.NumberFormatter;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;

public class GUI extends JFrame {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Perso perso;
	private SpringLayout layout;


	public GUI() throws org.json.simple.parser.ParseException {
		this.setSize(1025, 1020);
		
		Container contentPane = this.getContentPane();
		this.layout = new SpringLayout();
		this.setLayout(this.layout);
		
		this.setTitle("Path Finding !");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setBackground(Color.WHITE);
		
		Plateau plateau;
		Find find;
		plateau = new Plateau(10, 10);
		plateau.createPlateauJson("14", "ex-0");
		//plateau.startFinishRandom();
		perso = new Perso(plateau.getStart());
		find = new Find(perso, plateau, 2);
		

//		while(true) {
//			plateau = new Plateau(10, 10);
//			plateau.createPlateau();
//			plateau.startFinishRandom();
//			perso = new Perso(plateau.getStart());
//			find = new Find(perso, plateau, 1);
//			if(find.pfs(plateau.getStart()[0], plateau.getStart()[1])) break;
//			else System.out.println("Pass");
//		}
		
		//perso = new Perso(plateau.getStart());
		
		//Find find = new Find(perso, plateau, 1);
		
		
		layout.putConstraint(SpringLayout.NORTH, plateau, 20, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, plateau, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, plateau, -5, SpringLayout.EAST, contentPane);
		
		
		this.getContentPane().add(plateau);
		
		//this.add(plateau, BorderLayout.CENTER);
		
//		this.getContentPane().add(ex);
		System.out.println(find.play());
		this.setVisible(true);
		

	}

}