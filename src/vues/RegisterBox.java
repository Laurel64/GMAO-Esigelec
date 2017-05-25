package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import beans.Responsable;
import beans.User;
import controleurs.ConnexionBDD;
import controleurs.Control;
import patternObs.Observer;

/**
 * 
 * @author Laurel & Mangano
 *
 */
@SuppressWarnings("serial")
public class RegisterBox extends JDialog implements Observer {

	private User user;
	private JPanel panelProfil;
	private BouttonUser bouttonProfil;
	private JTextField jtfNom;
	private JTextField jtfPrenom;

	private Control control;

	private boolean clic = false, ok = false;
	private JButton butCon;
	private boolean valider;
	private JButton butIns;
	private Responsable responsable;
	private JTextField jtfAdresse;
	private JTextField jtfTel;
	private JPasswordField jtfPassword;
	private JButton butModifier;
	private JButton butOK;
	private JPanel panelBoutton;
	private JPanel pannel1;
	private JButton butSave;

	/**
	 * Constructor with fields
	 * 
	 * @param control
	 * @param name
	 * @param priority
	 *            The prority of THread
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public RegisterBox(Control control, String name, int priority, Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		this.setSize(700, 560);
		this.setLocationRelativeTo(null);

		user = new User();
		responsable = new Responsable();

		this.control = control;
		this.clic = true;
		this.ok = true;
		this.control.getModel().addObserver(this);
		this.control.addObserver(this);

		initComponent();

	}

	/**
	 * 
	 * @param control
	 * @param name
	 * @param priority
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param ok
	 */
	public RegisterBox(Control control, String name, int priority, Frame arg0, String arg1, boolean arg2, boolean ok) {
		super(arg0, arg1, arg2);
		this.setSize(700, 560);
		this.setLocationRelativeTo(null);

		user = new User();
		responsable = new Responsable();

		this.control = control;
		this.clic = true;
		this.ok = ok;
		this.control.getModel().addObserver(this);
		this.control.addObserver(this);

		initComponent();

	}

	/**
	 * lauchn the JDialoge
	 */
	public void start() {

		if (this.clic) {
			this.jtfNom.setEditable(false);
			this.jtfPrenom.setEditable(false);
			this.jtfPassword.setEditable(false);
			this.jtfAdresse.setEditable(false);
			this.jtfTel.setEditable(false);
		}

		this.setVisible(true);
	}

	public boolean getClic() {
		return this.clic;
	}

	/**
	 * @param clic
	 *            the clic to set
	 */
	public void setClic(boolean clic) {
		this.clic = clic;
	}

	/**
	 * @param ok
	 *            the ok to set
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * Initialisation des composants
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponent() {

		Dimension dimension = new Dimension(200, 35);

		panelProfil = new JPanel();
		panelProfil.setPreferredSize(new Dimension(250, 140));
		panelProfil.setOpaque(false);
		panelProfil.setBackground(Color.WHITE);

		JPanel panelPhoto = new JPanel();
		panelPhoto.setBorder(BorderFactory.createTitledBorder("Photo de profil "));
		bouttonProfil = new BouttonUser(this.user);
		bouttonProfil.setBackground(Color.WHITE);
		bouttonProfil.setPreferredSize(new Dimension(250, 140));
		panelPhoto.add(bouttonProfil);

		//
		String str = (this.clic && this.ok) ? "Infos Profil" : "Inscription";
		JLabel label = new JLabel(str);
		label.setFont(new Font("Times New Roman", Font.BOLD, 50));
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(JLabel.CENTER);
		JLabel labelSession = new JLabel(" Aucun Responsable enrégistré pour l'instant. Veuillez en créer un.");
		labelSession.setFont(new Font("Times New Roman", Font.BOLD, 20));
		butModifier = new JButton("Modifier");
		butModifier.addActionListener(new SessionListener());
		butModifier.setBackground(Color.WHITE);
		butModifier.setPreferredSize(new Dimension(410, 30));
		pannel1 = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setVgap(20);
		pannel1.setPreferredSize(new Dimension(600, 140));
		pannel1.setLayout(fl);
		pannel1.add(label);
		if (this.clic && this.ok)
			pannel1.add(butModifier);
		else
			pannel1.add(labelSession);
		//

		JPanel panelNom = new JPanel();
		JLabel labelUserNom = new JLabel("Nom        ");
		jtfNom = new JTextField();
		jtfNom.setPreferredSize(dimension);
		panelNom.add(labelUserNom);
		panelNom.add(jtfNom);

		JPanel panelPrenom = new JPanel();
		JLabel labelUserPrenom = new JLabel("Identifiant * ");
		jtfPrenom = new JTextField();
		jtfPrenom.setPreferredSize(dimension);
		panelPrenom.add(labelUserPrenom);
		panelPrenom.add(jtfPrenom);
		//////////////

		JLabel labelPassword = new JLabel("Mot de passe * ");
		jtfPassword = new JPasswordField();
		jtfPassword.setPreferredSize(dimension);
		JPanel pannel2 = new JPanel();
		pannel2.add(labelPassword);
		pannel2.add(jtfPassword);

		///////////

		JPanel panelIdentifiant = new JPanel();
		panelIdentifiant.setPreferredSize(new Dimension(310, 200));
		panelIdentifiant.setBorder(BorderFactory.createTitledBorder("Identifiants "));
		panelIdentifiant.add(panelNom);
		panelIdentifiant.add(panelPrenom);
		panelIdentifiant.add(pannel2);

		JPanel panelAdresse = new JPanel();
		panelAdresse.setBorder(BorderFactory.createTitledBorder("Adresse domicile * "));
		jtfAdresse = new JTextField();
		jtfAdresse.setPreferredSize(new Dimension(320, 40));
		panelAdresse.add(jtfAdresse);

		JPanel panelTel = new JPanel();
		panelTel.setBorder(BorderFactory.createTitledBorder("Numéro Portable  "));
		jtfTel = new JTextField();
		jtfTel.setPreferredSize(new Dimension(250, 40));
		panelTel.add(jtfTel);

		panelBoutton = new JPanel();

		//
		butCon = new JButton("Je m'inscris !");
		butCon.setPreferredSize(new Dimension(250, 30));
		butCon.setBackground(Color.LIGHT_GRAY);
		butCon.setForeground(Color.BLACK);
		butCon.setFont(new Font("Times New Roman", Font.BOLD, 18));
		butCon.addActionListener(new SessionListener());
		butIns = new JButton("Annuler");
		butIns.setPreferredSize(new Dimension(100, 30));
		butIns.setBackground(Color.RED);
		butIns.setForeground(Color.WHITE);
		butIns.setFont(new Font("Times New Roman", Font.BOLD, 18));
		butIns.addActionListener(new SessionListener());
		butCon.addMouseListener(new Mouse());
		butIns.addMouseListener(new Mouse());
		if (this.clic && this.ok)
			butIns.setText("Valider");
		else {
			panelBoutton.add(butCon);
		}
		panelBoutton.add(butIns);

		panelProfil.add(pannel1);
		panelProfil.add(panelPhoto);
		panelProfil.add(panelIdentifiant);
		panelProfil.add(panelAdresse);
		panelProfil.add(panelTel);
		panelProfil.add(panelBoutton);

		this.getContentPane().add(panelProfil);
	}

	@Override
	public void update(String chaine) {
		// TODO Auto-generated method stub

	}

	class SessionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			JOptionPane jop = null;

			if (e.getSource() == butCon) {

				responsable.setNom(jtfNom.getText());
				responsable.setPrenom(jtfPrenom.getText());
				responsable.setPassword(String.valueOf(jtfPassword.getPassword()));
				responsable.setAdresse(jtfAdresse.getText());
				responsable.setNumProfil(1);
				responsable.setNumTel(Integer.parseInt(jtfTel.getText()));
				responsable.setLogin(jtfPrenom.getText());

				Connection con = ConnexionBDD.connectDataBase();

				Statement state;

				if (!jtfPrenom.getText().isEmpty() && !String.valueOf(jtfPassword.getPassword()).isEmpty()) {
					try {
						state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

						String query = "INSERT INTO RESPONSABLE_RESPO VALUES(respo.NEXTVAL, '" + jtfNom.getText()
								+ "', '" + jtfPrenom.getText() + "', '" + jtfAdresse.getText() + "', "
								+ Integer.parseInt(jtfTel.getText()) + ")";

						String query2 = "INSERT INTO UTILISATEUR_U VALUES(u.NEXTVAL, '" + jtfPrenom.getText() + "', '"
								+ String.valueOf(jtfPassword.getPassword()) + "' ,1)";

						ResultSet result = state.executeQuery(query);

						ResultSet result2 = state.executeQuery(query2);

						valider = true;
						setVisible(false);

						result.close();
						state.close();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {

					jop.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires (*) ", "Infos",
							JOptionPane.INFORMATION_MESSAGE);

				}

			}

			if (e.getSource() == butModifier) {

				JOptionPane jop1 = new JOptionPane();

				try {
					String password = (String) jop1.showInputDialog(null, "Entrez votre mot de passe actuel : ",
							"Mot de Passe");

					if (password.equals(control.getRespo().getPassword())) {
						jtfNom.setEditable(true);
						jtfPrenom.setEditable(true);
						jtfPassword.setEditable(true);
						jtfAdresse.setEditable(true);
						jtfTel.setEditable(true);

						pannel1.remove(butModifier);
						pannel1.revalidate();
						pannel1.repaint();
						panelBoutton.removeAll();
						butSave = new JButton("Enrégistrer");
						butSave.addActionListener(new SessionListener());
						panelBoutton.add(butSave);
						butIns.setText("Annuler");
						panelBoutton.add(butIns);
						panelBoutton.revalidate();
						panelBoutton.repaint();

					} else {
						jop1.showMessageDialog(null,
								"Mot de passe Invalide.\n\nVous ne pouvez par conséquent effectuer aucune modifications.",
								"Infos", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (NullPointerException ee) {

				}
			}

			if (e.getSource() == butSave) {
				Connection con = ConnexionBDD.connectDataBase();

				Statement state;

				if (!jtfPrenom.getText().isEmpty() && !String.valueOf(jtfPassword.getPassword()).isEmpty()) {
					try {
						state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

						String query = "UPDATE RESPONSABLE_RESPO SET respo_nom = '" + jtfNom.getText()
								+ "', respo_prenom = '" + jtfPrenom.getText() + "', respo_adresse = '"
								+ jtfAdresse.getText() + "', respo_numTel = " + Integer.parseInt(jtfTel.getText())
								+ " WHERE respo_prenom = '" + control.getRespo().getPrenom()
								+ "' AND  respo_adresse = +'" + control.getRespo().getAdresse() + "'";

						String query2 = "UPDATE UTILISATEUR_U SET u_login = '" + jtfPrenom.getText()
								+ "', u_password = '" + String.valueOf(jtfPassword.getPassword())
								+ "' WHERE u_login = '" + control.getRespo().getPrenom() + "' AND u_pro_id = 1";

						state.executeQuery(query);

						state.executeQuery(query2);

						valider = true;
						setVisible(false);

						state.close();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

			if (e.getSource() == butIns) {
				valider = false;
				setVisible(false);
			}

		}

	}

	/**
	 * @return the responsable
	 */
	public Responsable getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable
	 *            the responsable to set
	 */
	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	/**
	 * @return the valider
	 */
	public boolean isValider() {
		return valider;
	}

	/**
	 * @param valider
	 *            the valider to set
	 */
	public void setValider(boolean valider) {
		this.valider = valider;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	class Mouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			((JButton) e.getSource()).setBackground(Color.DARK_GRAY);
			((JButton) e.getSource()).setForeground(Color.WHITE);

		}

		@Override
		public void mouseEntered(MouseEvent e) {

			((JButton) e.getSource()).setBackground(Color.DARK_GRAY);
			((JButton) e.getSource()).setForeground(Color.WHITE);
		}

		@Override
		public void mouseExited(MouseEvent e) {

			if (e.getSource() == butCon) {
				((JButton) e.getSource()).setBackground(Color.GREEN);
				((JButton) e.getSource()).setForeground(Color.WHITE);
			}
			if (e.getSource() == butIns) {
				((JButton) e.getSource()).setBackground(Color.RED);
				((JButton) e.getSource()).setForeground(Color.WHITE);
			}

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void update(MiniWindow panel) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the jtfNom
	 */
	public JTextField getJtfNom() {
		return jtfNom;
	}

	/**
	 * @param jtfNom
	 *            the jtfNom to set
	 */
	public void setJtfNom(String jtfNom) {
		this.jtfNom.setText(jtfNom);
	}

	/**
	 * @return the jtfPrenom
	 */
	public JTextField getJtfPrenom() {
		return jtfPrenom;
	}

	/**
	 * @param jtfPrenom
	 *            the jtfPrenom to set
	 */
	public void setJtfPrenom(String jtfPrenom) {
		this.jtfPrenom.setText(jtfPrenom);
	}

	/**
	 * @return the jtfAdresse
	 */
	public JTextField getJtfAdresse() {
		return jtfAdresse;
	}

	/**
	 * @param jtfAdresse
	 *            the jtfAdresse to set
	 */
	public void setJtfAdresse(String jtfAdresse) {
		this.jtfAdresse.setText(jtfAdresse);
	}

	/**
	 * @return the jtfTel
	 */
	public JTextField getJtfTel() {
		return jtfTel;
	}

	/**
	 * @param jtfTel
	 *            the jtfTel to set
	 */
	public void setJtfTel(String jtfTel) {
		this.jtfTel.setText(jtfTel);
	}

	/**
	 * @return the jtfPassword
	 */
	public JPasswordField getJtfPassword() {
		return jtfPassword;
	}

	/**
	 * @param jtfPassword
	 *            the jtfPassword to set
	 */
	public void setJtfPassword(String jtfPassword) {
		this.jtfPassword.setText(jtfPassword);
	}

	@Override
	public void updateJSP(ArrayList<String> sb) {
		// TODO Auto-generated method stub

	}

}
