package beans;

/**
 * Creation of the class User for the identification
 * 
 * @author Mangano et Talom
 *
 */
public class User {

	/**
	 * Id of the user
	 */
	private String login;
	/**
	 * password of the user
	 */
	private String password;
	/**
	 * Profil number of the user
	 */
	private int numProfil;

	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * 
	 * @param login
	 * @param password
	 * @param numProfil
	 */
	public User(String login, String password, int numProfil) {
		super();
		this.login = login;
		this.password = password;
		this.numProfil = numProfil;
	}

	/**
	 * @return the numProfil
	 */
	public int getNumProfil() {
		return numProfil;
	}

	/**
	 * @param numProfil
	 *            the numProfil to set
	 */
	public void setNumProfil(int numProfil) {
		this.numProfil = numProfil;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
