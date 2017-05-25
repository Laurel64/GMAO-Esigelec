package beans;

public class Responsable extends User {
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

	public Responsable() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param nom
	 * @param prenom
	 * @param numTel
	 * @param adresse
	 */
	public Responsable(String nom, String prenom, int numTel, String adresse) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.numTel = numTel;
		this.adresse = adresse;
	}

	/**
	 * Constructor with parameters from superclass
	 * 
	 * @param login
	 * @param password
	 * @param numProfil
	 */
	public Responsable(String login, String password, int numProfil) {
		super(login, password, numProfil);
		// TODO Auto-generated constructor stub
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

}
