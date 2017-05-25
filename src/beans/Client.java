package beans;

import java.util.*;

public class Client extends User {

	/**
	 * name of enterprise
	 */
	private String nomEntreprise;
	/**
	 * Siret number
	 */
	private String numSiret;
	/**
	 * APE code
	 */
	private String codeAPE;
	/**
	 * adress
	 */
	private String adresse;
	/**
	 * The Enterprise creation date
	 */
	private String dateCreation;
	/**
	 * phone number
	 */
	private int numTel;
	/**
	 * List of maintenance tickets
	 */
	private ArrayList<TicketMaintenance> listeMaintenance;

	/**
	 * Constructeur par dédaut qui instancie la liste des tickets
	 */
	public Client() {
		listeMaintenance = new ArrayList<TicketMaintenance>();
	}

	/**
	 * Constructeur avec paramètres
	 * 
	 * @param nomEntreprise
	 * @param numSiret
	 * @param codeAPE
	 * @param adresse
	 * @param numTel
	 */

	public Client(String nomEntreprise, String numSiret, String codeAPE, String adresse, String dateCreation,
			int numTel) {
		super();
		this.nomEntreprise = nomEntreprise;
		this.numSiret = numSiret;
		this.codeAPE = codeAPE;
		this.adresse = adresse;
		this.dateCreation = dateCreation;
		this.numTel = numTel;
	}

	public Client(String nomEntreprise, String numSiret, String codeAPE, String adresse, int numTel) {
		super();
		this.nomEntreprise = nomEntreprise;
		this.numSiret = numSiret;
		this.codeAPE = codeAPE;
		this.adresse = adresse;
		this.numTel = numTel;
	}

	/**
	 * Constructeur avec les paramètres de la classe User.
	 * 
	 * @see User
	 * @param login
	 * @param password
	 * @param numProfil
	 */
	public Client(String login, String password, int numProfil) {
		super(login, password, numProfil);
		// TODO Auto-generated constructor stub
	}

	public String afficher() {
		return this.nomEntreprise + " : " + this.numSiret + " : " + this.codeAPE;
	}

	/**
	 * @return the liste des Maintenances
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
	 * @return the nomEntreprise
	 */
	public String getNomEntreprise() {
		return nomEntreprise;
	}

	/**
	 * @param nomEntreprise
	 *            the nomEntreprise to set
	 */
	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}

	/**
	 * @return the numSiret
	 */
	public String getNumSiret() {
		return numSiret;
	}

	/**
	 * @param numSiret
	 *            the numSiret to set
	 */
	public void setNumSiret(String numSiret) {
		this.numSiret = numSiret;
	}

	/**
	 * @return the codeAPE
	 */
	public String getCodeAPE() {
		return codeAPE;
	}

	/**
	 * @param codeAPE
	 *            the codeAPE to set
	 */
	public void setCodeAPE(String codeAPE) {
		this.codeAPE = codeAPE;
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
	 * @return the dateCreation
	 */
	public String getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation
	 *            the dateCreation to set
	 */
	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
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

}
