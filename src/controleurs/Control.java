package controleurs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import beans.Client;
import beans.Operateur;
import beans.Responsable;
import beans.User;
import models.Model;
import patternObs.Observable;
import patternObs.Observer;
import vues.BoxOperateur;
import vues.MiniWindow;
import vues.RegisterBox;

/**
 * Class to set the link between View and Model
 * 
 * @author nyjn
 *
 */

public class Control implements Observable {

	/**
	 * List of Observators class
	 */
	private ArrayList<Observer> listeObs = new ArrayList<Observer>();
	/**
	 * Model of App
	 */
	private Model model;
	/**
	 * The rule
	 */
	private String role;

	/**
	 * Objects Users that extends to User
	 * 
	 * @see User
	 */
	private Responsable respo;
	private Operateur op;
	private Client cl;

	public Control(Model model) {
		this.model = model;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param role
	 */
	public void affichePanelConnexion(String role) {
		this.role = role.toLowerCase();
		this.updateObserver(role);

	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role.toLowerCase();
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @param id
	 * @param mdp
	 * @return true or false in order to the success of connexion or not
	 */
	public boolean testerConnexion(String id, String mdp) {

		if (role.equals("responsable")) {
			respo = model.findRespo(id, mdp, role.toLowerCase());

			if (respo != null)
				return true;

		} else if (role.equals("operateur")) {

			op = model.findOp(id, mdp, role.toLowerCase());

			if (op != null)
				return true;

		} else {

			cl = model.findClient(id, mdp, role.toLowerCase());

			if (cl != null)
				return true;
		}

		return false;

	}

	/**
	 * @return the respo
	 */
	public Responsable getRespo() {
		return respo;
	}

	/**
	 * @param respo
	 *            the respo to set
	 */
	public void setRespo(Responsable respo) {
		this.respo = respo;
	}

	/**
	 * @return the op
	 */
	public Operateur getOp() {
		return op;
	}

	/**
	 * @param op
	 *            the op to set
	 */
	public void setOp(Operateur op) {
		this.op = op;
	}

	/**
	 * @return the cl
	 */
	public Client getCl() {
		return cl;
	}

	/**
	 * @param cl
	 *            the cl to set
	 */
	public void setCl(Client cl) {
		this.cl = cl;
	}

	/*
	 * public ArrayList<Responsable> getResponsables() { ArrayList<Responsable>
	 * listeRepo = new ArrayList<Responsable>(); }
	 */
	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public void addObserver(Observer obs) {
		// TODO Auto-generated method stub
		listeObs.add(obs);

	}

	@Override
	public void updateObserver(String chaine) {
		// TODO Auto-generated method stub
		for (Observer obs : listeObs) {
			obs.update(chaine);

		}

	}

	public boolean respoExisted() {

		boolean retour = false;

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;
		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "SELECT * FROM RESPONSABLE_RESPO";

			ResultSet result = state.executeQuery(query);

			int nbre = 0;
			while (result.next())
				nbre++;

			if (nbre != 0)
				retour = true;

			result.close();
			state.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retour;
	}

	@Override
	public void updateObserver(MiniWindow panel) {
		// TODO Auto-generated method stub

	}

	public void displayThisUser() {

		if (this.role.equals("responsable")) {

			RegisterBox rb = new RegisterBox(this, "Box", 1, null,
					"Profil Utilisateur : " + respo.getPrenom().toUpperCase(), true);

			rb.setJtfNom(respo.getNom());
			rb.setJtfPrenom(respo.getPrenom());
			rb.setJtfPassword(respo.getPassword());
			rb.setJtfTel(String.valueOf(respo.getNumTel()));
			rb.setJtfAdresse(respo.getAdresse());

			rb.setClic(true);

			rb.start();

			if (rb.isValider()) {
				JOptionPane jop1 = new JOptionPane();
				jop1.showMessageDialog(null, "Modifications bien effectuées.", "Infos",
						JOptionPane.INFORMATION_MESSAGE);

			}

		}
		if (this.role.equals("operateur")) {

			BoxOperateur rb = new BoxOperateur(this, "Box", 1, null,
					"Profil Utilisateur : " + op.getPrenom().toUpperCase(), true, true);

			rb.setJtfNom(op.getNom());
			rb.setJtfPrenom(op.getPrenom());
			rb.setJtfPassword(op.getPassword());
			rb.setJtfTel(String.valueOf(op.getNumTel()));
			rb.setJtfAdresse(op.getAdresse());
			rb.setJtfSpe(op.getSpecialite());

			rb.setClic(true);

			rb.start();

			if (rb.isValider()) {
				JOptionPane jop1 = new JOptionPane();
				jop1.showMessageDialog(null, "Modifications bien effectuées.", "Infos",
						JOptionPane.INFORMATION_MESSAGE);

			}

		}
		if (this.role.equals("client")) {

		}

	}

	@Override
	public void updateObserverResearch(ArrayList<String> sb) {
		// TODO Auto-generated method stub

	}

}
