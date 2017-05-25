package beans;

import java.util.ArrayList;

public class Operateur extends User {

	/**
	 * The name of operator
	 */
	private String nom;

	/**
	 * The Last name of operateur
	 */
	private String prenom;

	/**
	 * The adress
	 */
	private String adresse;

	/**
	 * Phone number
	 */
	private int numTel;

	/**
	 * The operator domain
	 */
	private String specialite;

	/**
	 * The maintenances list
	 */
	private ArrayList<TicketMaintenance> listeMaintenance;

	public Operateur() {
		listeMaintenance = new ArrayList<TicketMaintenance>();
	}

	/**
	 * Constructor with parameters from class User
	 * 
	 * @see User
	 * @param login
	 * @param password
	 * @param numProfil
	 */
	public Operateur(String login, String password, int numProfil) {
		super(login, password, numProfil);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param nom
	 * @param prenom
	 * @param adresse
	 * @param numTel
	 * @param specialite
	 */
	public Operateur(String nom, String prenom, String adresse, int numTel, String specialite) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.numTel = numTel;
		this.specialite = specialite;
		listeMaintenance = new ArrayList<TicketMaintenance>();
	}

	public String afficher() {
		return this.nom + " " + this.prenom + " : " + this.specialite;
	}

	/**
	 * @return the listeMaintenance
	 */
	public ArrayList<TicketMaintenance> getListeMaintenance() {
		return listeMaintenance;
	}

	/**
	 * @param listeMaintenance
	 *            the listeMaintenance to set
	 */
	public void setListeMaintenance(ArrayList<TicketMaintenance> listeMaintenance) {
		this.listeMaintenance = listeMaintenance;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse
	 *            the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the numTel
	 */
	public int getNumTel() {
		return numTel;
	}

	/**
	 * @param numTel
	 *            the numTel to set
	 */
	public void setNumTel(int numTel) {
		this.numTel = numTel;
	}

	/**
	 * @return the specialite
	 */
	public String getSpecialite() {
		return specialite;
	}

	/**
	 * @param specialite
	 *            the specialite to set
	 */
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

}
