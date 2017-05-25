package beans;

public class Devis {

	/**
	 * The total budget of the maintenance
	 */
	private double montant;

	public Devis() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor with budget as parameter
	 * 
	 * @param montant
	 */
	public Devis(double montant) {
		super();
		this.montant = montant;
	}

	/**
	 * @return the montant
	 */
	public double getMontant() {
		return montant;
	}

	/**
	 * @param montant
	 *            the montant to set
	 */
	public void setMontant(double montant) {
		this.montant = montant;
	}

}
