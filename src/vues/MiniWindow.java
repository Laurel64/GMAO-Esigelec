package vues;

import javax.swing.JInternalFrame;

/**
 * 
 * Fenetre interne pour la gestion des des composants de donn" es
 * 
 * @author Laurel et Mangano
 *
 */
public class MiniWindow extends JInternalFrame {

	public MiniWindow() {
		this.setClosable(true);
		this.setSize(1000, 700);
		this.setResizable(true);
		this.setVisible(true);
	}

}
