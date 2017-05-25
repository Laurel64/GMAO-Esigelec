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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import beans.TicketMaintenance;
import controleurs.ConnexionBDD;
import controleurs.Control;
import models.AutoComplete;
import patternObs.Observer;

public class BoxTicketM extends JDialog implements ActionListener, Observer {

	private TicketMaintenance tm;
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
	private JPanel pannel;
	private JButton butInvalTicket;
	private boolean invalidate;
	private JTextField jtfClient;
	private JTextField jtfOp;
	private AbstractButton butSave;
	private JPanel pannel1;
	private JButton butEnregistrer;
	private JPanel pannel4;
	private AutoComplete autoComplete;
	private JPanel pannel2;

	public BoxTicketM(Control control, TicketMaintenance tm, Frame arg0, String arg1, boolean arg2, int num) {
		super(arg0, arg1, arg2);

		this.setSize(700, 680);
		this.setLocationRelativeTo(null);
		this.validate = false;
		this.invalidate = false;
		this.clic = false;
		this.numero = num;

		this.control = control;
		this.tm = tm;

		initComponent();

	}

	public void start() {

		this.jtfClient.setEditable(false);
		this.jtfOp.setEditable(false);
		this.jtfID.setEditable(false);
		this.jtfType.setEditable(false);
		this.jtfDate.setEditable(false);
		this.jtfDuree.setEditable(false);
		this.jtfDescription.setEditable(false);
		this.jtfDevis.setEditable(false);

		this.setVisible(true);
	}

	private void initComponent() {

		this.setBackground(Color.GREEN);
		this.getContentPane().setBackground(Color.RED);
		pannel = new JPanel();
		pannel.setBackground(Color.BLUE);
		FlowLayout fl = new FlowLayout();
		fl.setVgap(20);
		pannel.setLayout(new BorderLayout());
		pannel.setPreferredSize(new Dimension(800, 800));

		JLabel label = new JLabel(" Ticket N° " + numero);
		label.setFont(new Font("Times New Roman", Font.BOLD, 25));
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(JLabel.CENTER);
		JLabel label2 = new JLabel(" Ce ticket n'a pas encore été validé par vos soins.");
		label.setFont(new Font("Times New Roman", Font.BOLD, 18));
		label.setHorizontalAlignment(JLabel.CENTER);

		jtfClient = new JTextField(tm.getClient().getNomEntreprise());
		jtfOp = new JTextField(
				tm.getOperateur().getPrenom().equalsIgnoreCase("non spécifié") ? "" : tm.getOperateur().getPrenom());
		jtfID = new JTextField(tm.getNomMaintenance());
		jtfType = new JTextField(tm.getTypeMaintenance());
		jtfDate = new JTextField(tm.getDateDebut());
		jtfDuree = new JTextField(tm.getDureeMaintenance());
		jtfDescription = new JTextArea(tm.getDesciption());
		JScrollPane jsp = new JScrollPane(jtfDescription);
		jsp.setPreferredSize(new Dimension(400, 140));
		jtfDevis = new JTextField(String.valueOf(tm.getDevis().getMontant()));
		jtfClient.setPreferredSize(new Dimension(200, 45));
		jtfOp.setPreferredSize(new Dimension(200, 45));
		jtfID.setPreferredSize(new Dimension(200, 45));
		jtfType.setPreferredSize(new Dimension(200, 60));
		jtfDate.setPreferredSize(new Dimension(400, 45));
		jtfDuree.setPreferredSize(new Dimension(200, 45));
		jtfDevis.setPreferredSize(new Dimension(200, 45));

		jtfClient.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfOp.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfID.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfType.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfDate.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfDuree.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfDevis.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfDescription.setFont(new Font("Times New Roman", Font.BOLD, 14));

		jtfClient.setBorder(BorderFactory.createTitledBorder("Client émetteur "));
		jtfOp.setBorder(BorderFactory.createTitledBorder(" Opérateur concerné "));
		jtfDescription.setBorder(BorderFactory.createTitledBorder("Description *"));
		jtfDate.setBorder(BorderFactory.createTitledBorder("Date d'amorçage de la maintenance (aaaa/mm/jj)"));
		jtfID.setBorder(BorderFactory.createTitledBorder("Nom du Ticket *"));
		jtfType.setBorder(BorderFactory.createTitledBorder("Type de maintenance à effectuer :"));
		jtfDuree.setBorder(BorderFactory.createTitledBorder("Durée * "));
		jtfDevis.setBorder(BorderFactory.createTitledBorder("Budget total (en €) * "));

		autoComplete = new AutoComplete(jtfOp, 1);
		autoComplete.addObserver(this);
		jtfOp.getDocument().addDocumentListener(autoComplete);

		butValTicket = new JButton("Valider le ticket !");
		butFermer = new JButton("Fermer");
		butSave = new JButton("Modifier");
		butFermer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				validate = false;
				invalidate = false;
				clic = false;
				setVisible(false);
			}
		});
		butInvalTicket = new JButton("Invalider le ticket !");
		butValTicket.setPreferredSize(new Dimension(250, 30));
		butValTicket.setBackground(Color.LIGHT_GRAY);
		butValTicket.setForeground(Color.BLACK);
		butValTicket.setFont(new Font("Times New Roman", Font.BOLD, 18));
		butValTicket.addActionListener(this);
		butInvalTicket.addActionListener(this);
		butInvalTicket.setPreferredSize(new Dimension(250, 30));
		butInvalTicket.setBackground(Color.LIGHT_GRAY);
		butInvalTicket.setForeground(Color.BLACK);
		butInvalTicket.setFont(new Font("Times New Roman", Font.BOLD, 18));
		butInvalTicket.addActionListener(this);
		butSave.addActionListener(this);
		butSave.setPreferredSize(new Dimension(400, 30));
		butSave.setBackground(Color.LIGHT_GRAY);
		butSave.setForeground(Color.BLACK);
		butSave.setFont(new Font("Times New Roman", Font.BOLD, 18));
		butSave.addActionListener(this);

		pannel1 = new JPanel();
		pannel1.setPreferredSize(new Dimension(600, 90));
		pannel1.setBackground(Color.WHITE);
		pannel1.setLayout(fl);
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(600, 300));
		pannel2 = new JPanel();
		pannel2.setBackground(Color.WHITE);
		pannel2.setPreferredSize(new Dimension(600, 220));
		pannel2.setLayout(new GridLayout(3, 3));
		JPanel pannel3 = new JPanel();
		pannel3.setBackground(Color.WHITE);
		pannel3.setPreferredSize(new Dimension(600, 260));
		pannel3.setLayout(new BorderLayout());
		pannel4 = new JPanel();
		pannel4.setBackground(Color.WHITE);

		pannel1.add(label);
		pannel2.add(jtfClient);
		pannel2.add(jtfOp);
		pannel2.add(jtfID);
		pannel2.add(jtfDate);
		pannel2.add(jtfDuree);
		pannel2.add(jtfDevis);
		pannel3.add(jtfType, BorderLayout.NORTH);
		pannel3.add(jsp, BorderLayout.SOUTH);

		if (control.getRole().equals("responsable") && !tm.isValidate()) {
			pannel4.add(butValTicket);
			pannel1.add(label2);
		}

		if (control.getRole().equals("responsable") || control.getRole().equals("client"))
			pannel1.add(butSave);

		if (tm.isValidate() && control.getRole().equals("responsable")) {
			pannel4.add(butInvalTicket);
		}
		pannel4.add(butFermer);

		panel.add(pannel2);
		panel.add(pannel3);
		pannel.add(pannel1, BorderLayout.NORTH);
		pannel.add(panel, BorderLayout.CENTER);
		pannel.add(pannel4, BorderLayout.SOUTH);

		this.setContentPane(pannel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == butValTicket || e.getSource() == butInvalTicket) {

			Connection con = ConnexionBDD.connectDataBase();

			Statement state;

			try {
				state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

				if (e.getSource() == butValTicket) {
					state.executeQuery("UPDATE ticketmaintenance_tm SET tm_isvalidate = 1 WHERE tm_nom = '"
							+ tm.getNomMaintenance() + "' AND tm_type = '" + tm.getTypeMaintenance() + "'");
					validate = true;
				} else {
					state.executeQuery("UPDATE ticketmaintenance_tm SET tm_isvalidate = 0 WHERE tm_nom = '"
							+ tm.getNomMaintenance() + "' AND tm_type = '" + tm.getTypeMaintenance() + "'");
					invalidate = true;
				}

				setVisible(false);

				state.close();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		if (e.getSource() == butSave) {

			if (!control.getRole().equals("client"))
				this.jtfOp.setEditable(true);
			this.jtfID.setEditable(true);
			this.jtfType.setEditable(true);
			this.jtfDate.setEditable(true);
			this.jtfDuree.setEditable(true);
			this.jtfDescription.setEditable(true);
			this.jtfDevis.setEditable(true);

			if ((control.getRole().equals("client") || control.getRole().equals("responsable")) && this.clic) {
				if (control.getRole().equals("client")) {
					this.jtfOp.setEditable(false);
				}

			}

			pannel1.remove(butSave);
			pannel1.revalidate();
			pannel1.repaint();

			butEnregistrer = new JButton("Enrégistrer");
			butEnregistrer.setPreferredSize(new Dimension(250, 30));
			butEnregistrer.setBackground(Color.LIGHT_GRAY);
			butEnregistrer.setForeground(Color.BLACK);
			butEnregistrer.setFont(new Font("Times New Roman", Font.BOLD, 18));
			butEnregistrer.addActionListener(this);

			pannel4.removeAll();
			pannel4.revalidate();
			pannel4.repaint();
			pannel4.add(butEnregistrer);
			pannel4.add(butFermer);

		}

		if (e.getSource() == butEnregistrer) {

			this.clic = true;

			Connection con = ConnexionBDD.connectDataBase();

			Statement state = null;

			if (jtfOp.getText().isEmpty()) {
				if (!jtfID.getText().isEmpty() && !jtfDuree.getText().isEmpty() && !jtfDescription.getText().isEmpty()
						&& !jtfDevis.getText().isEmpty()) {

					try {
						state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

						String query = "UPDATE ticketmaintenance_tm SET tm_nom = '" + jtfID.getText() + "', tm_type = '"
								+ jtfType.getText() + "', tm_datecreation = TO_DATE('" + jtfDate.getText().split(" ")[0]
								+ "', 'YYYY/MM/DD'), tm_duree = '" + jtfDuree.getText() + "', tm_description = '"
								+ jtfDescription.getText() + "' " + "WHERE tm_nom = '" + tm.getNomMaintenance()
								+ "' AND tm_type = '" + tm.getTypeMaintenance() + "'";

						ResultSet rsd = state.executeQuery("SELECT * FROM ticketmaintenance_tm WHERE tm_nom = '"
								+ tm.getNomMaintenance() + "' AND tm_type = '" + tm.getTypeMaintenance() + "'");

						rsd.next();
						String query2 = "UPDATE devis_dev SET dev_montant = " + Float.valueOf(jtfDevis.getText())
								+ " WHERE dev_tm_id = " + rsd.getObject("tm_id");

						state.executeQuery(query2);

						state.executeQuery(query);

						setVisible(false);

						state.close();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane jop = null;
					jop.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires (*) ", "Infos",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {

				try {
					state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

					ResultSet rs = state.executeQuery(
							"SELECT * FROM operateur_op WHERE op_prenom = '" + jtfOp.getText().split(" ")[0] + "'");

					if (rs.next()) {

						if (!jtfID.getText().isEmpty() && !jtfDuree.getText().isEmpty()
								&& !jtfDescription.getText().isEmpty() && !jtfDevis.getText().isEmpty()) {

							try {
								state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_UPDATABLE);

								String query = "UPDATE ticketmaintenance_tm SET tm_nom = '" + jtfID.getText()
										+ "', tm_type = '" + jtfType.getText() + "', tm_datecreation = TO_DATE('"
										+ jtfDate.getText().split(" ")[0] + "', 'YYYY/MM/DD'), tm_duree = '"
										+ jtfDuree.getText() + "', tm_description = '" + jtfDescription.getText() + "' "
										+ "WHERE tm_nom = '" + tm.getNomMaintenance() + "' AND tm_type = '"
										+ tm.getTypeMaintenance() + "'";
								ResultSet rsd = state.executeQuery("SELECT * FROM ticketmaintenance_tm WHERE tm_nom = '"
										+ tm.getNomMaintenance() + "' AND tm_type = '" + tm.getTypeMaintenance() + "'");

								rsd.next();
								String query2 = "UPDATE devis_dev SET dev_montant = "
										+ Float.valueOf(jtfDevis.getText()) + " WHERE dev_tm_id = "
										+ rsd.getObject("tm_id");

								int idOp = 1, idTm = Integer.parseInt(rsd.getObject("tm_id").toString());

								state.executeQuery(query2);

								state.executeQuery(query);

								String query5 = "SELECT op_id FROM operateur_op WHERE op_prenom = '"
										+ jtfOp.getText().split(" ")[0] + "'";
								ResultSet rst2 = state.executeQuery(query5);
								if (rst2.next())
									idOp = Integer.parseInt(rst2.getObject("op_id").toString());

								String query4 = "UPDATE operaticket_ot SET ot_id_op = " + idOp + " WHERE ot_id_tm = "
										+ idTm;

								state.executeQuery(query4);

								setVisible(false);

								state.close();

							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane jop = null;
							jop.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires (*) ", "Infos",
									JOptionPane.INFORMATION_MESSAGE);
						}

					} else {
						JOptionPane jop = null;
						jop.showMessageDialog(null,
								"Cet opérateur est inexistant...\n Veuillez vérifier votre saisie. ", "Infos",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (SQLException e2) {
					e2.printStackTrace();
				}

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
