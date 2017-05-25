package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import beans.Client;
import beans.CompteRendu;
import beans.Devis;
import beans.Operateur;
import beans.Responsable;
import beans.TicketMaintenance;
import controleurs.ConnexionBDD;
import patternObs.Observable;
import patternObs.Observer;
import vues.E;
import vues.MiniWindow;

/**
 * Class Model to join directly the database
 * 
 * @author Mangano and Talom
 *
 */

public class Model implements Observable {

	private ArrayList<Observer> listeObs = new ArrayList<Observer>();
	private int idDevis;
	private int idCl;

	public Model() {
		// TODO Auto-generated constructor stub
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

		}

	}

	public Responsable findRespo(String id, String mdp, String profil) {

		Connection con = ConnexionBDD.connectDataBase();

		Responsable user = null;
		try {
			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Statement state2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "SELECT * FROM UTILISATEUR_U";

			ResultSet result = state.executeQuery(query);

			int n = 0;
			while (result.next()) {

				System.out.println("mince");
				System.out.println(result.getObject("u_login").toString().toLowerCase() + "---"
						+ result.getObject("u_password").toString().toLowerCase());
				if (result.getObject("u_login").toString().equals(id)
						&& result.getObject("u_password").toString().equals(mdp)) {
					String query2 = getRequest(profil, id, mdp);

					ResultSet result2 = state2.executeQuery(query2);

					if (result2.next()) {

						user = new Responsable(result2.getObject("respo_nom").toString(),
								result2.getObject("respo_prenom").toString(),
								Integer.parseInt(result2.getObject("respo_numTel").toString()),
								result2.getObject("respo_adresse").toString());
						user.setPassword(result.getObject("u_password").toString());

						System.out.println("Connecté!");
						n = 1;
						result.close();
						state.close();
						return user;
					}
				}
			}

			if (n == 0) {
				System.out.println("Echec");
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Compte inexistant ! / Identifiant / mot de passe invalide",
						" Echec de connexion", JOptionPane.INFORMATION_MESSAGE);
			}

			result.close();
			state.close();

		} catch (SQLException e) {
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Identifiant / mot de passe invalide", " Echec de connexion",
					JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}

		return user;
	}

	public Operateur findOp(String id, String mdp, String profil) {
		Connection con = ConnexionBDD.connectDataBase();

		Operateur user = null;
		try {
			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Statement state2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "SELECT * FROM UTILISATEUR_U";

			ResultSet result = state.executeQuery(query);

			int n = 0;
			while (result.next()) {

				System.out.println("mince");
				System.out.println(result.getObject("u_login").toString().toLowerCase() + "---"
						+ result.getObject("u_password").toString().toLowerCase());
				if (result.getObject("u_login").toString().equals(id)
						&& result.getObject("u_password").toString().equals(mdp)) {
					String query2 = getRequest(profil, id, mdp);

					ResultSet result2 = state2.executeQuery(query2);

					if (result2.next()) {

						user = new Operateur(result2.getObject("op_nom").toString(),
								result2.getObject("op_prenom").toString(), result2.getObject("op_adresse").toString(),
								Integer.parseInt(result2.getObject("op_numTel").toString()),
								result2.getObject("op_specialite").toString());
						user.setPassword(result.getObject("u_password").toString());

						System.out.println("Connecté!");
						n = 1;
						result.close();
						state.close();
						return user;
					}

				}
			}

			if (n == 0) {
				System.out.println("Echec");
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Compte inexistant ! / Identifiant / mot de passe invalide",
						" Echec de connexion", JOptionPane.INFORMATION_MESSAGE);
			}

			result.close();
			state.close();

		} catch (SQLException e) {
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Identifiant / mot de passe invalide", " Echec de connexion",
					JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}

		return user;
	}

	public Client findClient(String id, String mdp, String profil) {
		Connection con = ConnexionBDD.connectDataBase();

		Client user = null;
		try {
			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Statement state2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "SELECT * FROM UTILISATEUR_U";

			ResultSet result = state.executeQuery(query);

			int n = 0;
			while (result.next()) {

				System.out.println("mince");
				System.out.println(result.getObject("u_login").toString().toLowerCase() + "---"
						+ result.getObject("u_password").toString().toLowerCase());
				if (result.getObject("u_login").toString().equals(id)
						&& result.getObject("u_password").toString().equals(mdp)) {
					String query2 = getRequest(profil, id, mdp);

					ResultSet result2 = state2.executeQuery(query2);

					if (result2.next()) {

						user = new Client(result2.getObject("cl_nom").toString(),
								result2.getObject("cl_numeroSiret").toString(),
								result2.getObject("cl_codeAPE").toString(), result2.getObject("cl_adresse").toString(),
								Integer.valueOf(result2.getObject("cl_numTel").toString()));

						if (result2.getObject("cl_dateCreation") != null)
							user.setDateCreation(result2.getObject("cl_dateCreation").toString());

						System.out.println("Connecté!");
						n = 1;

						result2.close();
						state2.close();
						return user;
					}

				}
			}
			result.close();
			state.close();

			if (n == 0) {
				System.out.println("Echec");
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Compte inexistant ! / Identifiant / mot de passe invalide",
						" Echec de connexion", JOptionPane.INFORMATION_MESSAGE);
			}

			result.close();
			state.close();

		} catch (SQLException e) {
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Identifiant / mot de passe invalide", " Echec de connexion",
					JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}

		return user;

	}

	public String getRequest(String role, String id, String mdp) {
		if (role.toLowerCase().equals("responsable")) {
			return "SELECT * FROM responsable_respo INNER JOIN utilisateur_u ON respo_prenom = u_login WHERE u_login = '"
					+ id + "' AND u_password = '" + mdp + "'";
		} else if (role.toLowerCase().equals("operateur")) {
			return " SELECT * FROM operateur_op INNER JOIN utilisateur_u ON op_prenom = u_login WHERE u_login = '" + id
					+ "' AND u_password = '" + mdp + "'";
		} else {
			return " SELECT * FROM client_cl INNER JOIN utilisateur_u ON cl_nom = u_login WHERE cl_nom = '" + id
					+ "' AND u_password = '" + mdp + "'";
		}
	}

	@Override
	public void updateObserver(MiniWindow panel) {
		// TODO Auto-generated method stub

	}

	public boolean addClient(Client client) {

		boolean retour = false;

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;

		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "INSERT INTO client_cl VALUES(cl.NEXTVAL, '" + client.getNomEntreprise() + "', '"
					+ client.getNumSiret() + "', '" + client.getAdresse() + "', '" + client.getCodeAPE()
					+ "', SYSDATE, " + client.getNumTel() + ")";

			String query2 = "INSERT INTO UTILISATEUR_U VALUES(u.NEXTVAL, '" + client.getNomEntreprise() + "', '"
					+ client.getPassword() + "' ,3, null)";

			ResultSet result = state.executeQuery(query);

			ResultSet result2 = state.executeQuery(query2);

			retour = true;

			result.close();
			state.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return retour;

	}

	public boolean addTicketM(TicketMaintenance tm, Devis devis, String cl_nom) {
		boolean retour = false;

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;

		idCl = 1;
		int idTm = 1;
		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = state.executeQuery("SELECT * FROM client_cl WHERE cl_nom = '" + cl_nom + "'");
			String nomM = null;
			if (rs.next()) {
				idCl = Integer.valueOf(rs.getObject("cl_id").toString());
				String name = tm.getNomMaintenance().replaceAll(" ", "_");
				nomM = rs.getObject("cl_nom").toString().toUpperCase() + "_" + name.toLowerCase();
			}

			String query = "INSERT INTO ticketmaintenance_tm VALUES(tm.NEXTVAL, '" + nomM + "', '"
					+ tm.getTypeMaintenance() + "', TO_DATE('" + tm.getDateDebut() + "', 'DD/MM/YYYY'), '"
					+ tm.getDureeMaintenance() + "', 0, '" + tm.getDesciption() + "', " + idCl + ")";

			state.executeQuery(query);

			ResultSet rst = state.executeQuery("SELECT tm_id FROM ticketmaintenance_tm WHERE tm_nom = '" + nomM + "'");

			if (rst.next()) {
				idTm = Integer.valueOf(rst.getObject("tm_id").toString());
			}

			String query2 = "INSERT INTO devis_dev VALUES(dev.NEXTVAL, " + devis.getMontant() + ", " + idTm + ")";

			state.executeQuery(query2);

			String query4 = "INSERT INTO operaticket_ot VALUES(" + idTm + ", null)";
			state.executeQuery(query4);

			retour = true;

			state.close();

		} catch (SQLSyntaxErrorException e1) {
			JOptionPane jop = null;
			jop.showMessageDialog(null,
					"Rapport d'erreur : " + e1.getMessage()
							+ "\n Vérifiez de ne pas avoir rempli certains champs en faisant usage d'un caractère spécial tel que : ' ",
					" Infos", JOptionPane.ERROR_MESSAGE);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return retour;

	}

	public ArrayList<TicketMaintenance> getListeMaintenance() {

		ArrayList<TicketMaintenance> retour = new ArrayList<TicketMaintenance>();

		ArrayList<Devis> al = new ArrayList<Devis>();

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;

		int nbre = 0;

		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "SELECT * FROM TICKETMAINTENANCE_TM " + "INNER JOIN CLIENT_CL ON (cl_id = tm_cl_id) "
					+ "LEFT OUTER JOIN OPERATICKET_OT ON (tm_id = ot_id_tm) "
					+ "LEFT OUTER JOIN OPERATEUR_OP ON (op_id = ot_id_op) "
					+ "INNER JOIN DEVIS_DEV ON (dev_tm_id = tm_id)";

			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {

				nbre = 1;

				Devis dev = new Devis(Double.valueOf(rs.getObject("dev_montant").toString()));
				Client client = new Client(rs.getObject("cl_nom").toString(), rs.getObject("cl_numerosiret").toString(),
						rs.getObject("cl_codeape").toString(), rs.getObject("cl_adresse").toString(),
						Integer.parseInt(rs.getObject("cl_numTel").toString()));

				Operateur op = new Operateur(
						(rs.getObject("op_nom") == null) ? "non spécifié" : rs.getObject("op_nom").toString(),
						(rs.getObject("op_prenom") == null) ? "non spécifié" : rs.getObject("op_prenom").toString(),
						(rs.getObject("op_adresse") == null) ? "non spécifié" : rs.getObject("op_adresse").toString(),
						(rs.getObject("op_numTel") == null) ? 0
								: Integer.parseInt(rs.getObject("op_numTel").toString()),
						(rs.getObject("op_specialite") == null) ? "non spécifié"
								: rs.getObject("op_specialite").toString());

				TicketMaintenance tm = new TicketMaintenance(
						(rs.getObject("tm_nom") == null) ? "non spécifié" : rs.getObject("tm_nom").toString(),
						(rs.getObject("tm_type") == null) ? "non spécifié" : rs.getObject("tm_type").toString(),
						(rs.getObject("tm_duree") == null) ? "non spécifié" : rs.getObject("tm_duree").toString(),
						(rs.getObject("tm_datecreation") == null) ? "non spécifié"
								: rs.getObject("tm_datecreation").toString(),
						(rs.getObject("tm_description") == null) ? "non spécifié"
								: rs.getObject("tm_description").toString());

				if (Integer.parseInt(rs.getObject("tm_isvalidate").toString()) == 1) {
					tm.setValidate(true);
				} else {
					tm.setValidate(false);
				}

				String query2 = "SELECT * FROM TICKETMAINTENANCE_TM INNER JOIN COMPTERENDU_CR ON (cr_tm_id = tm_id) WHERE tm_id = "
						+ rs.getObject("tm_id");
				Statement state2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs2 = state2.executeQuery(query2);
				while (rs2.next()) {
					if (rs2.getObject("cr_description") != null) {
						CompteRendu cr = new CompteRendu(
								(rs2.getObject("cr_description") == null) ? "non spécifié"
										: rs2.getObject("cr_description").toString(),
								(rs2.getObject("cr_datecreation") == null) ? "non spécifié"
										: rs2.getObject("cr_datecreation").toString(),
								(rs2.getObject("cr_datemisajour") == null) ? "non spécifié"
										: rs2.getObject("cr_datemisajour").toString(),
								Integer.parseInt(rs2.getObject("cr_id").toString()));
						boolean bool = (Integer.parseInt(rs2.getObject("cr_isvalidate").toString()) == 1) ? true
								: false;
						cr.setValidate(bool);
						tm.getListeCompteRendu().add(cr);
					}

				}

				tm.setDevis(dev);
				tm.setClient(client);
				tm.setOperateur(op);

				retour.add(tm);

			}

			if (nbre == 0)
				retour = null;

			rs.close();
			state.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return retour;

	}

	public ArrayList<Operateur> getListeOperateur() {

		ArrayList<Operateur> retour = new ArrayList<Operateur>();

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;

		int nbre = 0;

		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "SELECT DISTINCT * FROM operateur_op LEFT OUTER JOIN OPERATICKET_OT ON (op_id = ot_id_op) LEFT OUTER JOIN ticketmaintenance_tm ON (tm_id = ot_id_tm)";

			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {

				nbre = 1;

				Operateur op = new Operateur(
						(rs.getObject("op_nom") == null) ? "non spécifié" : rs.getObject("op_nom").toString(),
						(rs.getObject("op_prenom") == null) ? "non spécifié" : rs.getObject("op_prenom").toString(),
						(rs.getObject("op_adresse") == null) ? "non spécifié" : rs.getObject("op_adresse").toString(),
						(rs.getObject("op_numTel") == null) ? 0
								: Integer.parseInt(rs.getObject("op_numTel").toString()),
						(rs.getObject("op_specialite") == null) ? "non spécifié"
								: rs.getObject("op_specialite").toString());

				TicketMaintenance tm = new TicketMaintenance(
						(rs.getObject("tm_nom") == null) ? "non spécifié" : rs.getObject("tm_nom").toString(),
						(rs.getObject("tm_type") == null) ? "non spécifié" : rs.getObject("tm_type").toString(),
						(rs.getObject("tm_duree") == null) ? "non spécifié" : rs.getObject("tm_duree").toString(),
						(rs.getObject("tm_datecreation") == null) ? "non spécifié"
								: rs.getObject("tm_datecreation").toString(),
						(rs.getObject("tm_description") == null) ? "non spécifié"
								: rs.getObject("tm_description").toString());

				tm.setValidate(((rs.getObject("tm_isvalidate") == null) ? false
						: ((Integer.parseInt(rs.getObject("tm_isvalidate").toString()) == 1) ? true : false)));

				op.getListeMaintenance().add(tm);

				retour.add(op);

			}

			if (nbre == 0)
				retour = null;

			rs.close();
			state.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return retour;

	}

	@Override
	public void updateObserverResearch(ArrayList<String> sb) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Client> getListeClient() {
		ArrayList<Client> listeCl = new ArrayList<Client>();

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;

		int nbre = 0;

		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "SELECT * FROM client_cl INNER JOIN utilisateur_u on u_login = cl_nom";

			ResultSet rs = state.executeQuery(query);
			// String nomEntreprise, String numSiret, String codeAPE, String
			// adresse, int numTel
			while (rs.next()) {
				Client cl = new Client(rs.getObject("cl_nom").toString(), rs.getObject("cl_numerosiret").toString(),
						rs.getObject("cl_codeape").toString(), rs.getObject("cl_adresse").toString(),
						rs.getObject("cl_datecreation").toString(),
						Integer.valueOf(rs.getObject("cl_numTel").toString()));
				cl.setPassword(rs.getObject("u_password").toString());

				E.e("num = " + Integer.valueOf(rs.getObject("cl_numTel").toString()));
				listeCl.add(cl);
				nbre++;
			}
			rs.close();
			state.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeCl;
	}

	public boolean deleteClient(String[] str) {

		boolean retour = false;

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;

		int nbre = 0;

		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "DELETE FROM client_cl WHERE cl_nom = '" + str[0] + "' AND cl_numerosiret = '" + str[1]
					+ "' AND cl_codeape = '" + str[2] + "'";
			String query2 = "DELETE FROM utilisateur_u WHERE u_login = '" + str[0] + "'";

			state.executeQuery(query);
			state.executeQuery(query2);

			retour = true;

			state.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retour;

	}

	public ArrayList<TicketMaintenance> getListeTicket() {

		ArrayList<TicketMaintenance> listeTm = new ArrayList<TicketMaintenance>();

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;

		int nbre = 0;

		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "SELECT * FROM ticketmaintenance_tm INNER JOIN client_cl ON cl_id = tm_cl_id";

			ResultSet rs = state.executeQuery(query);
			// String nomMaintenance, String typeMaintenance, String
			// dureeMaintenance, String dateDebut,
			// String desciption
			while (rs.next()) {
				TicketMaintenance tm = new TicketMaintenance(rs.getObject("tm_nom").toString(),
						rs.getObject("tm_type").toString(), rs.getObject("tm_duree").toString(),
						rs.getObject("tm_datecreation").toString(), rs.getObject("tm_description").toString());

				listeTm.add(tm);
				nbre++;
			}
			rs.close();
			state.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeTm;
	}

	public boolean deleteTicket(String[] str) {
		boolean retour = false;

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;

		int nbre = 0;

		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query1 = "SELECT tm_id FROM ticketmaintenance_tm WHERE tm_nom = '" + str[0] + "' AND tm_duree = '"
					+ str[1] + "' AND tm_datecreation = TO_DATE('" + str[2].split(" ")[0] + "', 'YYYY/MM/DD')";
			ResultSet rs1 = state.executeQuery(query1);
			rs1.next();

			String query2 = "DELETE FROM operaticket_ot WHERE ot_id_tm = " + rs1.getObject("tm_id");
			state.executeQuery(query2);

			String query3 = "DELETE FROM ticketmaintenance_tm WHERE tm_nom = '" + str[0] + "' AND tm_duree = '" + str[1]
					+ "' AND tm_datecreation = TO_DATE('" + str[2].split(" ")[0] + "', 'YYYY/MM/DD')";
			state.executeQuery(query3);

			retour = true;

			state.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retour;

	}

	public ArrayList<TicketMaintenance> getListeTicket(Client cl) {
		ArrayList<TicketMaintenance> listeTm = new ArrayList<TicketMaintenance>();

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;

		int nbre = 0;

		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "SELECT * FROM ticketmaintenance_tm INNER JOIN client_cl ON cl_id = tm_cl_id WHERE cl_nom = '"
					+ cl.getNomEntreprise() + "'";

			ResultSet rs = state.executeQuery(query);
			// String nomMaintenance, String typeMaintenance, String
			// dureeMaintenance, String dateDebut,
			// String desciption
			while (rs.next()) {
				TicketMaintenance tm = new TicketMaintenance(rs.getObject("tm_nom").toString(),
						rs.getObject("tm_type").toString(), rs.getObject("tm_duree").toString(),
						rs.getObject("tm_datecreation").toString(), rs.getObject("tm_description").toString());

				listeTm.add(tm);
				nbre++;
			}
			rs.close();
			state.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeTm;
	}

	public boolean deleteOperateur(String[] str) {
		boolean retour = false;

		Connection con = ConnexionBDD.connectDataBase();

		Statement state;

		int nbre = 0;

		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "DELETE FROM operateur_op WHERE op_nom = '" + str[0].split(" ")[0] + "' AND op_prenom = '"
					+ str[0].split(" ")[1] + "' AND op_specialite = '" + str[1] + "'";
			String query2 = "DELETE FROM utilisateur_u WHERE u_login = '" + str[0].split(" ")[1] + "'";

			state.executeQuery(query);
			state.executeQuery(query2);

			retour = true;

			state.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retour;
	}

}
