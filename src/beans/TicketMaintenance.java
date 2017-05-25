package beans;

import java.util.ArrayList;

public class TicketMaintenance {

	/**
	 * Name of the ticket
	 */
	private String nomMaintenance;

	/**
	 * The type of the maintenance
	 */
	private String typeMaintenance;
	/**
	 * The time of the maintenance
	 */
	private String dureeMaintenance;
	/**
	 * The date of the maintenance start
	 */
	private String dateDebut;
	/**
	 * The date of the maintenance end
	 */
	private String dateFin;
	/**
	 * Maintenance classification
	 */
	private boolean Validate;
	/**
	 * The description of the maintenance
	 */
	private String desciption;
	/**
	 * Creation of the object devis of the class Devis
	 */
	private Devis devis = new Devis();
	/**
	 * Creation of the object client of the class Client
	 */
	private Client client = new Client();
	private Operateur operateur;
	/**
	 * Creation of a list CompteRendu
	 */
	private ArrayList<CompteRendu> listeCompteRendu;

	/**
	 * Constructor
	 */
	public TicketMaintenance() {
		operateur = new Operateur();
		listeCompteRendu = new ArrayList<CompteRendu>();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialize constructor
	 * 
	 * @param nomMaintenance
	 * @param typeMaintenance
	 * @param dureeMaintenance
	 * @param dateDebut
	 * @param desciption
	 */
	public TicketMaintenance(String nomMaintenance, String typeMaintenance, String dureeMaintenance, String dateDebut,
			String desciption) {
		super();
		this.nomMaintenance = nomMaintenance;
		this.typeMaintenance = typeMaintenance;
		this.dureeMaintenance = dureeMaintenance;
		this.dateDebut = dateDebut;
		this.desciption = desciption;
		listeCompteRendu = new ArrayList<CompteRendu>();
	}

	public String afficher() {
		return this.nomMaintenance + " : " + this.dureeMaintenance + " : " + this.dateDebut;
	}

	/**
	 * @return the validate
	 */
	public boolean isValidate() {
		return Validate;
	}

	/**
	 * @param validate
	 *            the validate to set
	 */
	public void setValidate(boolean validate) {
		Validate = validate;
	}

	/**
	 * @return the devis
	 */
	public Devis getDevis() {
		return devis;
	}

	/**
	 * @param devis
	 *            the devis to set
	 */
	public void setDevis(Devis devis) {
		this.devis = devis;
	}

	/**
	 * @return the listeCompteRendu
	 */
	public ArrayList<CompteRendu> getListeCompteRendu() {
		return listeCompteRendu;
	}

	/**
	 * @param listeCompteRendu
	 *            the listeCompteRendu to set
	 */
	public void setListeCompteRendu(ArrayList<CompteRendu> listeCompteRendu) {
		this.listeCompteRendu = listeCompteRendu;
	}

	/**
	 * @return the client
	 */

	public Client getClient() {
		return client;
	}

	/**
	 * @return the operateur
	 */
	public Operateur getOperateur() {
		return operateur;
	}

	/**
	 * @param operateur
	 *            the operateur to set
	 */
	public void setOperateur(Operateur operateur) {
		this.operateur = operateur;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the nomMaintenance
	 */
	public String getNomMaintenance() {
		return nomMaintenance;
	}

	/**
	 * @param nomMaintenance
	 *            the nomMaintenance to set
	 */
	public void setNomMaintenance(String nomMaintenance) {
		this.nomMaintenance = nomMaintenance;
	}

	/**
	 * @return the typeMaintenance
	 */
	public String getTypeMaintenance() {
		return typeMaintenance;
	}

	/**
	 * @param typeMaintenance
	 *            the typeMaintenance to set
	 */
	public void setTypeMaintenance(String typeMaintenance) {
		this.typeMaintenance = typeMaintenance;
	}

	/**
	 * @return the dureeMaintenance
	 */
	public String getDureeMaintenance() {
		return dureeMaintenance;
	}

	/**
	 * @param dureeMaintenance
	 *            the dureeMaintenance to set
	 */
	public void setDureeMaintenance(String dureeMaintenance) {
		this.dureeMaintenance = dureeMaintenance;
	}

	/**
	 * @return the dateDebut
	 */
	public String getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut
	 *            the dateDebut to set
	 */
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return the dateFin
	 */
	public String getDateFin() {
		return dateFin;
	}

	/**
	 * @param dateFin
	 *            the dateFin to set
	 */
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * @return the desciption
	 */
	public String getDesciption() {
		return desciption;
	}

	/**
	 * @param desciption
	 *            the desciption to set
	 */
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

}
