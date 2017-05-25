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

import beans.Operateur;
import beans.Responsable;
import beans.User;
import controleurs.ConnexionBDD;
import controleurs.Control;
import patternObs.Observer;

public class BoxOperateur extends JDialog implements Observer {

	private User user;
	private JPanel panelProfil;
	private BouttonUser bouttonProfil;
	private JTextField jtfNom;
	private JTextField jtfPrenom;

	private Control control;

	private boolean clic;
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
	private JTextField jtfSpe;
	private Operateur op;
	private int numero;

	public BoxOperateur(Control control, Operateur op, Frame arg0, String arg1, boolean arg2, int row) {
		super(arg0, arg1, arg2);
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);

		user = new User();
		this.op = op;
		this.numero = row;

		this.control = control;
		this.clic = true;
		this.control.getModel().addObserver(this);
		this.control.addObserver(this);

		initComponent();

	}

	public BoxOperateur(Control control2, String string, int i, Frame arg1, String string2, boolean b, boolean clic) {
		super(arg1, string2, b);
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);

		user = new User();

		this.control = control2;
		this.op = control.getOp();
		this.clic = clic;
		this.control.getModel().addObserver(this);
		this.control.addObserver(this);
		initComponent();
	}

	public void start() {

		if (this.clic) {
			this.jtfNom.setEditable(false);
			this.jtfPrenom.setEditable(false);
			this.jtfPassword.setEditable(false);
			this.jtfAdresse.setEditable(false);
			this.jtfTel.setEditable(false);
			jtfSpe.setEditable(false);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponent() {

		boolean condition = this.clic && (control.getRole().equals("responsable")
				|| control.getOp().getPrenom().equalsIgnoreCase(op.getPrenom()));

		Dimension dimension = new Dimension(200, 35);

		panelProfil = new JPanel();
		panelProfil.setPreferredSize(new Dimension(250, 200));
		panelProfil.setOpaque(false);
		panelProfil.setBackground(Color.WHITE);

		JPanel panelPhoto = new JPanel();
		panelPhoto.setBorder(BorderFactory.createTitledBorder("Photo de profil "));
		bouttonProfil = new BouttonUser(this.user);
		bouttonProfil.setBackground(Color.WHITE);
		bouttonProfil.setPreferredSize(new Dimension(250, 120));
		JPanel panelSpe = new JPanel();
		JLabel labelSpe = new JLabel("Spécialité *      ");
		jtfSpe = new JTextField();
		jtfSpe.setPreferredSize(dimension);
		panelSpe.add(labelSpe);
		panelSpe.add(jtfSpe);
		panelPhoto.add(bouttonProfil);
		panelPhoto.add(panelSpe);

		//
		String str = (condition) ? "Infos Profil" : "( + ) Opérateur";
		JLabel label = new JLabel(str);
		label.setFont(new Font("Times New Roman", Font.BOLD, 50));
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(JLabel.CENTER);
		JLabel labelSession = new JLabel(" Veuillez à remplir les champs obligatoires (*)");
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
		if (condition)
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
		jtfAdresse.setPreferredSize(new Dimension(220, 40));
		panelAdresse.add(jtfAdresse);

		JPanel panelTel = new JPanel();
		panelTel.setBorder(BorderFactory.createTitledBorder("Numéro Portable  "));
		jtfTel = new JTextField();
		jtfTel.setPreferredSize(new Dimension(220, 40));
		panelTel.add(jtfTel);

		panelBoutton = new JPanel();

		//
		butCon = new JButton(" Valider !");
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
		if (this.clic)
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

				op = new Operateur();
				op.setNom(jtfNom.getText());
				op.setPrenom(jtfPrenom.getText());
				op.setPassword(String.valueOf(jtfPassword.getPassword()));
				op.setAdresse(jtfAdresse.getText());
				op.setNumProfil(2);
				op.setNumTel(Integer.parseInt(jtfTel.getText()));
				op.setLogin(jtfPrenom.getText());
				op.setSpecialite(jtfSpe.getText());

				Connection con = ConnexionBDD.connectDataBase();

				Statement state;

				if (!jtfPrenom.getText().isEmpty() && !String.valueOf(jtfPassword.getPassword()).isEmpty()
						&& !jtfSpe.getText().isEmpty()) {
					try {
						state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

						String query = "INSERT INTO operateur_op VALUES(op.NEXTVAL, '" + jtfNom.getText() + "', '"
								+ jtfPrenom.getText() + "', '" + jtfAdresse.getText() + "', "
								+ Integer.parseInt(jtfTel.getText()) + ", '" + jtfSpe.getText() + "')";
						String query2 = "INSERT INTO UTILISATEUR_U VALUES(u.NEXTVAL, '" + jtfPrenom.getText() + "', '"
								+ String.valueOf(jtfPassword.getPassword()) + "' ,2)";

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

					boolean condition = (control.getOp() == null ? false
							: password.equals(control.getOp().getPassword()));
					boolean condition2 = (control.getRespo() == null ? false
							: password.equals(control.getRespo().getPassword()));
					E.e("condition = " + condition + " - condition2 = " + condition2);
					if (condition || condition2) {
						jtfNom.setEditable(true);
						jtfPrenom.setEditable(true);
						jtfPassword.setEditable(true);
						jtfAdresse.setEditable(true);
						jtfTel.setEditable(true);
						jtfSpe.setEditable(true);

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
					E.e("hum");
				}
			}

			if (e.getSource() == butSave) {
				Connection con = ConnexionBDD.connectDataBase();

				Statement state;

				if (!jtfPrenom.getText().isEmpty() && !String.valueOf(jtfPassword.getPassword()).isEmpty()
						&& !jtfSpe.getText().isEmpty()) {
					try {
						state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

						String query = "UPDATE operateur_op SET op_nom = '" + jtfNom.getText() + "', op_prenom = '"
								+ jtfPrenom.getText() + "', op_adresse = '" + jtfAdresse.getText() + "', op_numTel = "
								+ Integer.parseInt(jtfTel.getText()) + ", op_specialite = '" + jtfSpe.getText()
								+ "' WHERE op_prenom = '" + control.getOp().getPrenom() + "' AND  op_adresse = +'"
								+ control.getOp().getAdresse() + "'";

						String query2 = "UPDATE UTILISATEUR_U SET u_login = '" + jtfPrenom.getText()
								+ "', u_password = '" + String.valueOf(jtfPassword.getPassword())
								+ "' WHERE u_login = '" + control.getOp().getPrenom() + "' AND u_pro_id = 2";

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
	 * @return the op
	 */
	public Operateur getOp() {
		return op;
	}

	/**
	 * @param op
	 *            the op to set
	 */
	public void setOp(Operateur op) {
		this.op = op;
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
	 * @return the jtfSpe
	 */
	public JTextField getJtfSpe() {
		return jtfSpe;
	}

	/**
	 * @param jtfSpe
	 *            the jtfSpe to set
	 */
	public void setJtfSpe(String jtfSpe) {
		this.jtfSpe.setText(jtfSpe);
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
