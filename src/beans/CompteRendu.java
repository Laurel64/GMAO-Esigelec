package beans;

public class CompteRendu {

	private int identifiant;
	/**
	 * Description of Minutes
	 */
	private String description;

	/**
	 * Edition date
	 */
	private String dateEdition;

	/**
	 * Update Date
	 */
	private String dateMisAJour;

	private boolean validate;

	public CompteRendu() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param description
	 * @param dateEdition
	 */
	public CompteRendu(String description, String dateEdition, String dateMisAJour, int id) {
		super();
		this.description = description;
		this.dateEdition = dateEdition;
		this.dateMisAJour = dateMisAJour;
		this.identifiant = id;
	}

	public CompteRendu(String description, String dateMisAJour) {
		super();
		this.description = description;
		this.dateMisAJour = dateMisAJour;
	}

	/**
	 * @return the validate
	 */
	public boolean isValidate() {
		return validate;
	}

	/**
	 * @param validate
	 *            the validate to set
	 */
	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	/**
	 * @return the identifiant
	 */
	public int getIdentifiant() {
		return identifiant;
	}

	/**
	 * @param identifiant
	 *            the identifiant to set
	 */
	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * @return the dateMisAJour
	 */
	public String getDateMisAJour() {
		return dateMisAJour;
	}

	/**
	 * @param dateMisAJour
	 *            the dateMisAJour to set
	 */
	public void setDateMisAJour(String dateMisAJour) {
		this.dateMisAJour = dateMisAJour;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dateEdition
	 */
	public String getDateEdition() {
		return dateEdition;
	}

	/**
	 * @param dateEdition
	 *            the dateEdition to set
	 */
	public void setDateEdition(String dateEdition) {
		this.dateEdition = dateEdition;
	}

}
