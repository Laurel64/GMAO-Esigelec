package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import controleurs.ConnexionBDD;
import patternObs.Observable;
import patternObs.Observer;
import vues.MiniWindow;

public class AutoComplete implements DocumentListener, Observable {

	private static enum Mode {
		INSERT, COMPLETION
	};

	private JTextField textField;
	private int numero;
	private String motRecherche;

	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	private ArrayList<String> keywords;

	public AutoComplete(JTextField textField, int num) {
		this.textField = textField;
		this.numero = num;
		this.motRecherche = new String();
		keywords = new ArrayList<String>();
	}

	public String getMotRecherche() {
		return this.motRecherche;
	}

	/**
	 * @param motRecherche
	 *            the motRecherche to set
	 */
	public void setMotRecherche(String motRecherche) {
		this.motRecherche = motRecherche;
	}

	@Override
	public void changedUpdate(DocumentEvent ev) {
	}

	@Override
	public void removeUpdate(DocumentEvent ev) {
	}

	@Override
	public void insertUpdate(DocumentEvent ev) {

		// Window.setNumeroRecherche(this.numero);

		if (ev.getLength() != 1)
			return;

		int pos = ev.getOffset();
		System.out.println("pos = " + pos);
		String content = null;
		try {
			content = textField.getText(0, pos + 1);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Length of " + content + " = " + content.length());

		// try {
		this.motRecherche = content;
		// } catch (BadLocationException e1) {
		// TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		System.out.println("motRecherche = " + this.motRecherche);

		// Find where the word starts

		int w;
		for (w = pos; w >= 0; w--) {
			if (!Character.isLetter(content.charAt(w))) {
				System.out.println("is letter. w =" + w);
				break;
			} else {
				System.out.println("isn't letter. w =" + w);
			}
		}

		// Too few chars
		if (pos - w < 2)
			return;

		System.out.println("Création des objets...");
		keywords = new ArrayList<String>();
		String query = null;

		query = " SELECT op_nom, op_prenom FROM operateur_op WHERE UPPER(op_nom) LIKE UPPER('%" + this.motRecherche
				+ "%') OR UPPER(op_prenom) LIKE UPPER('%" + this.motRecherche + "%') ";

		rechercher(query);

		if (keywords.size() != 0) {

			System.out.println("Nbre de mots = " + keywords.size());
			Collections.sort(keywords);

		} else {
			keywords.add("Aucun résultats trouvés");
		}

		updateObserverResearch(keywords);
	}

	public void rechercher(String query) {

		Statement state = null;
		ResultSet result = null;

		try {
			Connection con = ConnexionBDD.connectDataBase();
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			state.executeQuery(query);
			result = state.getResultSet();
			while (result.next()) {

				keywords.add(result.getString("op_prenom").toString() + " " + result.getString("op_nom").toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addObserver(Observer obs) {

		this.listObserver.add(obs);
	}

	@Override
	public void updateObserverResearch(ArrayList<String> sb) {

		for (Observer obs : listObserver) {
			obs.updateJSP(sb);
		}

	}

	@Override
	public void updateObserver(String chaine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObserver(MiniWindow panel) {
		// TODO Auto-generated method stub

	}

}