package vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import beans.Client;
import controleurs.ConnexionBDD;
import controleurs.Control;
import models.AutoComplete;
import patternObs.Observer;

public class BoxClient extends JDialog implements ActionListener, Observer {

	private Client client;
	private JTextField jtfID;
	private JTextField jtfType;
	private JTextField jtfDate;
	private JTextField jtfDuree;
	private JTextArea jtfDescription;
	private JTextField jtfDevis;
	private JButton butValTicket;
	private boolean clic, validate;
	private Control control;
	private JButton butFermer;
	private int numero;
	private JButton butInvalTicket;
	private boolean invalidate;
	private JTextField jtfOp;
	private AbstractButton butSave;
	private JPanel pannel1;
	private JButton butEnregistrer;
	private JPanel pannel4;
	private AutoComplete autoComplete;
	private JTextField jtfNumTel;
	private JTextField jtfCodeAPE;
	private JTextField jtfAdresse;
	private JTextField jtfNumSiret;
	private JPasswordField jtfPassword;
	private JButton butVal;
	private JPanel pannel3;

	public BoxClient(Control control, Client cl, Frame arg0, String arg1, boolean arg2, int num) {

		super(arg0, arg1, arg2);

		this.setSize(700, 730);
		this.setLocationRelativeTo(null);
		this.validate = false;
		this.invalidate = false;
		this.clic = false;
		this.numero = num;

		this.control = control;
		this.client = cl;

		initComponent();

	}

	public void start() {

		this.jtfID.setEditable(false);
		this.jtfNumSiret.setEditable(false);
		this.jtfPassword.setEditable(false);
		this.jtfCodeAPE.setEditable(false);
		this.jtfDate.setEditable(false);
		this.jtfAdresse.setEditable(false);
		this.jtfNumTel.setEditable(false);

		this.setVisible(true);
	}

	private void initComponent() {

		JPanel pannel = new JPanel();
		pannel.setBackground(Color.WHITE);
		FlowLayout fl = new FlowLayout();
		fl.setVgap(20);
		pannel.setLayout(new BorderLayout());
		pannel.setPreferredSize(new Dimension(700, 550));

		JLabel label = new JLabel("Client N° " + numero);
		label.setFont(new Font("Times New Roman", Font.BOLD, 50));
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(JLabel.CENTER);
		JLabel label2 = new JLabel(" - Créé le : " + client.getDateCreation());
		label2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label2.setForeground(Color.BLACK);
		label2.setHorizontalAlignment(JLabel.CENTER);

		JLabel labelID = new JLabel("Identifiant (Nom) * ");
		labelID.setFont(new Font("Times New Roman", Font.BOLD, 18));
		JLabel labelPassword = new JLabel("Mot de passe *");
		labelPassword.setFont(new Font("Times New Roman", Font.BOLD, 18));
		JLabel labelNumSiret = new JLabel("Numéro SIRET * ");
		labelNumSiret.setFont(new Font("Times New Roman", Font.BOLD, 18));
		JLabel labelAdresse = new JLabel("Adresse  ");
		labelAdresse.setFont(new Font("Times New Roman", Font.BOLD, 18));
		JLabel labelCodeAPE = new JLabel("Code APE ");
		labelCodeAPE.setFont(new Font("Times New Roman", Font.BOLD, 18));
		JLabel labelNumTel = new JLabel("Numéro Téléphone ");
		labelNumTel.setFont(new Font("Times New Roman", Font.BOLD, 18));

		jtfID = new JTextField(client.getNomEntreprise());
		jtfPassword = new JPasswordField(client.getPassword());
		jtfNumSiret = new JTextField(client.getNumSiret());
		jtfAdresse = new JTextField(client.getAdresse());
		jtfCodeAPE = new JTextField(client.getCodeAPE());
		jtfNumTel = new JTextField("0" + client.getNumTel());
		jtfDate = new JTextField(client.getDateCreation());
		jtfID.setPreferredSize(new Dimension(200, 45));
		jtfDate.setPreferredSize(new Dimension(200, 45));
		jtfPassword.setPreferredSize(new Dimension(200, 45));
		jtfNumSiret.setPreferredSize(new Dimension(200, 45));
		jtfAdresse.setPreferredSize(new Dimension(200, 45));
		jtfCodeAPE.setPreferredSize(new Dimension(200, 45));
		jtfNumTel.setPreferredSize(new Dimension(200, 45));
		jtfID.setFont(new Font("Times New Roman", Font.BOLD, 20));
		jtfPassword.setFont(new Font("Times New Roman", Font.BOLD, 20));
		jtfNumTel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		jtfNumSiret.setFont(new Font("Times New Roman", Font.BOLD, 20));
		jtfAdresse.setFont(new Font("Times New Roman", Font.BOLD, 20));
		jtfCodeAPE.setFont(new Font("Times New Roman", Font.BOLD, 20));
		jtfDate.setFont(new Font("Times New Roman", Font.BOLD, 20));

		butVal = new JButton("Valider");
		butVal.setPreferredSize(new Dimension(250, 30));
		butVal.setBackground(Color.LIGHT_GRAY);
		butVal.setForeground(Color.BLACK);
		butVal.setFont(new Font("Times New Roman", Font.BOLD, 18));
		butVal.addActionListener(this);
		butSave = new JButton("Modifier");
		butSave.setPreferredSize(new Dimension(400, 30));
		butSave.setBackground(Color.LIGHT_GRAY);
		butSave.setForeground(Color.BLACK);
		butSave.setFont(new Font("Times New Roman", Font.BOLD, 18));
		butSave.addActionListener(this);

		pannel1 = new JPanel();
		// pannel1.setBackground(Color.WHITE);
		pannel1.setPreferredSize(new Dimension(500, 140));
		pannel1.setLayout(fl);
		JPanel pannel2 = new JPanel();
		// pannel2.setBackground(Color.WHITE);
		pannel2.setLayout(new GridLayout(6, 6));
		pannel3 = new JPanel();
		// pannel3.setBackground(Color.WHITE);

		pannel1.add(label);
		pannel1.add(label2);
		if (control.getRole().equals("client"))
			pannel1.add(butSave);
		pannel2.add(labelID);
		pannel2.add(labelPassword);
		pannel2.add(jtfID);
		pannel2.add(jtfPassword);
		pannel2.add(labelNumSiret);
		pannel2.add(labelCodeAPE);
		pannel2.add(jtfNumSiret);
		pannel2.add(jtfCodeAPE);
		pannel2.add(labelAdresse);
		pannel2.add(labelNumTel);
		pannel2.add(jtfAdresse);
		pannel2.add(jtfNumTel);
		pannel3.add(butVal);

		pannel.add(pannel1, BorderLayout.NORTH);
		pannel.add(pannel2, BorderLayout.CENTER);
		pannel.add(pannel3, BorderLayout.SOUTH);

		this.getContentPane().add(pannel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == butVal || e.getSource() == butFermer) {

			setVisible(false);
			this.clic = false;
		}

		if (e.getSource() == butSave) {

			this.jtfID.setEditable(true);
			this.jtfNumSiret.setEditable(true);
			this.jtfPassword.setEditable(true);
			this.jtfCodeAPE.setEditable(true);
			this.jtfAdresse.setEditable(true);
			this.jtfNumTel.setEditable(true);

			pannel1.remove(butSave);
			pannel1.revalidate();
			pannel1.repaint();

			butEnregistrer = new JButton("Enrégistrer");
			butEnregistrer.setPreferredSize(new Dimension(250, 30));
			butEnregistrer.setBackground(Color.RED);
			butEnregistrer.setForeground(Color.WHITE);
			butEnregistrer.setFont(new Font("Times New Roman", Font.BOLD, 18));
			butEnregistrer.addActionListener(this);
			butFermer = new JButton("Fermer");
			butFermer.setPreferredSize(new Dimension(180, 30));
			butFermer.setBackground(Color.LIGHT_GRAY);
			butFermer.setForeground(Color.BLACK);
			butFermer.setFont(new Font("Times New Roman", Font.BOLD, 18));
			butFermer.addActionListener(this);

			pannel3.removeAll();
			pannel3.revalidate();
			pannel3.repaint();
			pannel3.add(butEnregistrer);
			pannel3.add(butFermer);

		}

		if (e.getSource() == butEnregistrer) {

			Connection con = ConnexionBDD.connectDataBase();

			Statement state = null;
			try {
				if (!jtfID.getText().isEmpty() && !jtfNumSiret.getText().isEmpty() && !jtfCodeAPE.getText().isEmpty()
						&& !jtfAdresse.getText().isEmpty() && !jtfNumTel.getText().isEmpty()
						&& !String.valueOf(jtfPassword.getPassword()).isEmpty()) {

					state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

					String query = "UPDATE client_cl SET cl_nom = '" + jtfID.getText() + "', cl_numerosiret = '"
							+ jtfNumSiret.getText() + "', cl_codeape = '" + jtfCodeAPE.getText() + "', cl_adresse = '"
							+ jtfAdresse.getText() + "', cl_numtel = " + Integer.parseInt(jtfNumTel.getText())
							+ " WHERE cl_nom = '" + client.getNomEntreprise() + "' AND cl_numerosiret = '"
							+ client.getNumSiret() + "'";

					state.executeQuery("UPDATE utilisateur_u SET u_login = '" + jtfID.getText() + "', u_password = '"
							+ String.valueOf(jtfPassword.getPassword()) + "' WHERE u_login = '"
							+ client.getNomEntreprise() + "'");

					state.executeQuery(query);
					this.clic = true;

					setVisible(false);

					state.close();

				} else {
					JOptionPane jop = null;
					jop.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires (*) ", "Infos",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NumberFormatException e1) {
				JOptionPane jop = null;
				jop.showMessageDialog(null,
						"Rapport d'erreur : " + e1.getMessage()
								+ ". \n Vérifiez que le numéro de téléphone entré a exactement 10 caractères.",
						"Une erreur est survenue", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (SQLException e1) {
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Rapport d'erreur : " + e1.getMessage() + ".", "Une erreur est survenue",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}

		}
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
	 * @return the invalidate
	 */
	public boolean isInvalidate() {
		return invalidate;
	}

	/**
	 * @param invalidate
	 *            the invalidate to set
	 */
	public void setInvalidate(boolean invalidate) {
		this.invalidate = invalidate;
	}

	public void updateJSP(ArrayList<String> sb) {

		JPopupMenu jpm = new JPopupMenu();
		int nbreOp = 0;

		for (int i = 0; i < sb.size(); i++) {
			System.out.println("i = " + i + " : " + sb.get(i));
			JMenuItem butOp = new JMenuItem(sb.get(i));
			butOp.setToolTipText(sb.get(i));
			butOp.setPreferredSize(new Dimension(200, 30));
			butOp.addActionListener(new MyListener());
			jpm.add(butOp);
			nbreOp++;

		}

		jpm.show(pannel1, 450, 160);

		jtfOp.requestFocusInWindow();
		jtfOp.setCaretPosition(autoComplete.getMotRecherche().length());

	}

	class MyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			jtfOp.setText(((JMenuItem) (e.getSource())).getText());

		}
	}

	@Override
	public void update(String chaine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(MiniWindow panel) {
		// TODO Auto-generated method stub

	}

}
