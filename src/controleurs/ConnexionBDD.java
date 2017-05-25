package controleurs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Creation of the class ConnexionBDD to get the connection for the database
 * 
 * @author Mangano et Talom
 *
 */
public class ConnexionBDD {

	/**
	 * URL for the connection to the database
	 */
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	/**
	 * User name for the connection to the database
	 */
	private static final String user = "gmao";
	/**
	 * Password for the connection to the database
	 */
	private static final String password = "gmao";

	/**
	 * Creation of an object of connection
	 */
	private static Connection con;

	/**
	 * Default constructor
	 */
	public ConnexionBDD() {

	}

	/**
	 * Initialize constructor where there is the connection of the database
	 * 
	 * @return Object Connection to the database
	 */
	public static Connection connectDataBase() {

		if (con == null) {

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("Driver OK");

				con = DriverManager.getConnection(url, user, password);
				System.out.println("Connecté à la BDD !");

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return con;
		}

		return con;

	}

}
