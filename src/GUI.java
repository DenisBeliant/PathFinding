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
		
		ActionsUI save = new ActionsUI("Sauvegarder");
		ActionsUI charge = new ActionsUI("Charger") ;
		ActionsUI clean = new ActionsUI("Nettoyer");
		
	    NumberFormat format = NumberFormat.getIntegerInstance();
	    format.setGroupingUsed(false);

	    NumberFormatter numberFormatter = new NumberFormatter(format);
	    numberFormatter.setValueClass(Long.class); 
	    numberFormatter.setAllowsInvalid(false); //this is the key

	    JFormattedTextField ex = new JFormattedTextField(numberFormatter);
		
		ActionsUI placeStart = new ActionsUI("Placer départ", new Color(0, 255, 0));
		ActionsUI placeFinish = new ActionsUI("Placer arrivée", new Color(255, 0, 0));
		
		ActionsUI eau = new ActionsUI("EAU", new Color(0, 0, 255));
		ActionsUI route = new ActionsUI("ROUTE", new Color(156, 161, 157));
		ActionsUI herbe = new ActionsUI("HERBE", new Color(0, 255, 0));
		ActionsUI mur = new ActionsUI("MUR", new Color(255, 0, 0)); 
		
		Plateau plateau = new Plateau(5, 3);
		plateau.createPlateauJson("24", "ex-1");
		//plateau.startFinishRandom();
		
		perso = new Perso(plateau.getStart());
		
		Find find = new Find(perso, plateau, 1);
		
		layout.putConstraint(SpringLayout.NORTH, save, 10, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, save, 20, SpringLayout.WEST, contentPane);
		
		layout.putConstraint(SpringLayout.NORTH, charge, 10, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, charge, 5, SpringLayout.EAST, save);
		
		layout.putConstraint(SpringLayout.NORTH, clean, 10, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, clean, 5, SpringLayout.EAST, charge);
		
		layout.putConstraint(SpringLayout.SOUTH, eau, -30, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, eau, 20, SpringLayout.WEST, contentPane);
		
		layout.putConstraint(SpringLayout.SOUTH, mur, -30, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, mur, 20, SpringLayout.EAST, eau);
		
		layout.putConstraint(SpringLayout.SOUTH, herbe, -30, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, herbe, 20, SpringLayout.EAST, mur);
		
		layout.putConstraint(SpringLayout.SOUTH, route, -30, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, route, 20, SpringLayout.EAST,  herbe);
		
		
		layout.putConstraint(SpringLayout.NORTH, placeFinish, 10, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, placeFinish, -10, SpringLayout.EAST, contentPane);
		
		layout.putConstraint(SpringLayout.NORTH, placeStart, 10, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, placeStart, -10, SpringLayout.WEST, placeFinish);
		
		layout.putConstraint(SpringLayout.NORTH, ex, 10, SpringLayout.SOUTH, placeFinish);
		layout.putConstraint(SpringLayout.EAST, ex, 10, SpringLayout.EAST, contentPane);
		
		
		layout.putConstraint(SpringLayout.NORTH, plateau, 20, SpringLayout.SOUTH, save);
		layout.putConstraint(SpringLayout.WEST, plateau, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, plateau, -5, SpringLayout.EAST, contentPane);
		
		this.getContentPane().add(save);
		this.getContentPane().add(charge);
		this.getContentPane().add(clean);
		
//		this.getContentPane().add(mur);
//		this.getContentPane().add(eau);
//		this.getContentPane().add(herbe);
//		this.getContentPane().add(route);
		
		this.getContentPane().add(placeStart);
		this.getContentPane().add(placeFinish);
		
		this.getContentPane().add(plateau);
		
		//this.add(plateau, BorderLayout.CENTER);
		
//		this.getContentPane().add(ex);

		this.setVisible(true);
		
		System.out.println(find.play());

	}

}