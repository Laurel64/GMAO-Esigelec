package vues;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controleurs.Control;
import controleurs.menuCompteRenduListener;
import controleurs.menuListener;
import models.Model;

/**
 * Class Main : The main thread of GMAO program.
 * 
 * @author Mangano and Talom
 *
 */
public class Main {

	/***
	 * Thread principal
	 * 
	 * @param args
	 *            paramètre externe à l'application
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// Objet qui se charge de la connexion avec la base de données
		Model model = new Model();

		// Objets aiguilleurs d'informations entre le model et window
		Control control = new Control(model);
		menuListener ml = new menuListener(model, control);
		menuCompteRenduListener menuCompteR = new menuCompteRenduListener(model, control);

		// La GUI principale de l'application
		Window window = new Window(control, ml, menuCompteR);

	}

}
