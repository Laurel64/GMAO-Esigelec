package controleurs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import beans.Client;
import beans.Devis;
import beans.Operateur;
import beans.Responsable;
import beans.TicketMaintenance;
import models.Model;
import patternObs.Observable;
import patternObs.Observer;
import vues.BoxOperateur;
import vues.E;
import vues.MiniWindow;

public class menuListener implements ActionListener, Observable {

	private ArrayList<Observer> listeObs = new ArrayList<Observer>();
	private JTextField jtfID;
	private JPasswordField jtfPassword;
	private JTextField jtfNumSiret;
	private JTextField jtfAdresse;
	private JTextField jtfCodeAPE;
	private JTextField jtfNumTel;
	private AbstractButton butVal;
	private MiniWindow pannel;

	private Model model;
	private boolean retour;
	private JTextField jtfDevis;
	private JTextArea jtfDescription;
	private JTextField jtfDate;
	private JTextField jtfType;
	private JTextField jtfDuree;
	private JButton butValTicket;
	private Control control;

	public menuListener(Model model, Control control) {

		this.model = model;
		this.control = control;
	}

	@SuppressWarnings("null")
	@Override
	public void actionPerformed(ActionEvent e) {

		pannel = new MiniWindow();

		if ((((JButton) e.getSource()).getText()).equals("Ajouter Client")) {

			ajouteClient();

		}

		if ((((JButton) e.getSource()).getText()).equals("Ajouter Ticket")) {

			ajouteTicket();

		}

		if ((((JButton) e.getSource()).getText()).equals("Liste de Tickets")) {

			afficheListeTickets();

		}

		if ((((JButton) e.getSource()).getText()).equals("Modifier Opérateur")) {

			control.displayThisUser();
		}

		if ((((JButton) e.getSource()).getText()).equals("Liste Clients")
				|| (((JButton) e.getSource()).getText()).equals("Modifier Client")) {

			ListeOUModifieClient();

		}

		if ((((JButton) e.getSource()).getText()).equals("Liste Opérateur")) {

			afficheListeOperateur();

		}

		if (e.getSource() == butVal) {

			enregistreClient();

		}

		if (((JButton) e.getSource()).getText().equals("Supprimer Client")) {

			supprimeClient();

		}

		if (((JButton) e.getSource()).getText().equals("Supprimer Opérateur")) {

			supprimeOperateur();

		}

		if (((JButton) e.getSource()).getText().equals("Supprimer Ticket")) {

			if (control.getRole().equals("responsable")) {

				supprimeTicket(control.getRespo());
			} else {

				supprimeTicket(control.getCl());
			}

		}

		if (((JButton) e.getSource()).getText().equals("Ajouter Opérateur")) {

			ajouteOperateur();

		}

		if (e.getSource() == butValTicket) {

			valideTicket();

		}

	}

	private void supprimeOperateur() {

		ArrayList<Operateur> liste = model.getListeOperateur();

		String[] listeOp = new String[liste.size()];

		for (int i = 0; i < liste.size(); i++) {

			listeOp[i] = liste.get(i).afficher();
		}

		JOptionPane jop = null;
		String retour = (String) jop.showInputDialog(null, "Veuillez sélectionner l'opérateur à supprimer :",
				"Suppression Opérateur", JOptionPane.OK_CANCEL_OPTION, null, listeOp, listeOp[0]);

		String[] str = retour.split(" : ");

		if (model.deleteOperateur(str)) {

			jop.showMessageDialog(null, "" + "Suppression bien effectuée !", "Infos", JOptionPane.INFORMATION_MESSAGE);
		} else {
			jop.showMessageDialog(null, "Rapport d'erreur : Impossible d'accéder à cette requête.", "Infos",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void supprimeTicket(Client cl) {
		ArrayList<TicketMaintenance> liste = model.getListeTicket(cl);

		String[] listeCl = new String[liste.size()];

		for (int i = 0; i < liste.size(); i++) {

			listeCl[i] = liste.get(i).afficher();
		}

		JOptionPane jop = null;

		String retour = (String) jop.showInputDialog(null, "Veuillez sélectionner l'entreprise à supprimer :",
				"Suppression Client", JOptionPane.OK_CANCEL_OPTION, null, listeCl, listeCl[0]);
		String[] str = retour.split(" : ");

		if (model.deleteTicket(str)) {

			jop.showMessageDialog(null, "" + "Suppression bien effectuée !", "Infos", JOptionPane.INFORMATION_MESSAGE);
		} else {
			jop.showMessageDialog(null, "Rapport d'erreur : Impossible d'accéder à cette requête.", "Infos",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void supprimeTicket(Responsable respo) {

		ArrayList<TicketMaintenance> liste = model.getListeTicket();

		String[] listeCl = new String[liste.size()];

		for (int i = 0; i < liste.size(); i++) {

			listeCl[i] = liste.get(i).afficher();
		}

		JOptionPane jop = null;

		String retour = (String) jop.showInputDialog(null, "Veuillez sélectionner l'entreprise à supprimer :",
				"Suppression Client", JOptionPane.OK_CANCEL_OPTION, null, listeCl, listeCl[0]);
		String[] str = retour.split(" : ");

		if (model.deleteTicket(str)) {

			jop.showMessageDialog(null, "" + "Suppression bien effectuée !", "Infos", JOptionPane.INFORMATION_MESSAGE);
		} else {
			jop.showMessageDialog(null, "Rapport d'erreur : Impossible d'accéder à cette requête.", "Infos",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void enregistreClient() {

		Client client = new Client(jtfID.getText(), jtfNumSiret.getText(), jtfCodeAPE.getText(), jtfAdresse.getText(),
				Integer.parseInt(jtfNumTel.getText()));
		client.setPassword(String.valueOf(jtfPassword.getPassword()));

		if (!jtfID.getText().isEmpty() && !jtfNumSiret.getText().isEmpty()
				&& !String.valueOf(jtfPassword.getPassword()).isEmpty()) {
			if (this.model.addClient(client)) {
				JOptionPane jop = null;
				jop.showMessageDialog(null,
						"L'entreprise " + client.getNomEntreprise()
								+ " fait parie de la clientèle de GMAO Maintenance désormais.\n"
								+ "\nCette entreprise peut se connecter à présent depuis la page de gestion de session.",
						" Opération réussie", JOptionPane.INFORMATION_MESSAGE);
				retour = true;
			}
		} else {
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires (*) ", "Infos",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void valideTicket() {

		TicketMaintenance tm = new TicketMaintenance(jtfID.getText(), jtfType.getText(), jtfDuree.getText(),
				jtfDate.getText(), jtfDescription.getText());

		Devis devis = new Devis(Float.valueOf(jtfDevis.getText()));

		if (!jtfID.getText().isEmpty() && !jtfDuree.getText().isEmpty() && !jtfDescription.getText().isEmpty()
				&& !jtfDevis.getText().isEmpty()) {
			if (this.model.addTicketM(tm, devis, getControl().getCl().getNomEntreprise())) {
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Le projet de maintenance \" " + tm.getNomMaintenance()
						+ " \" a bien été pris en compte par GMAO Enterprise.\n" + "\n Merci pour votre confiance.",
						" Opération réussie", JOptionPane.INFORMATION_MESSAGE);
				retour = true;
			}
		} else {
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires (*) ", "Infos",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void ajouteOperateur() {

		BoxOperateur rb = new BoxOperateur(control, "Thread", 1, null, "Opérateur GMAO", true, false);

		rb.start();

		if (rb.isValider()) {

			JOptionPane jop = null;
			jop.showMessageDialog(null,
					rb.getOp().getPrenom() + " est désormais un opérateur.\n" + "\nIl peut se connecter à présent.",
					" Opération réussie", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void supprimeClient() {

		ArrayList<Client> liste = model.getListeClient();

		String[] listeCl = new String[liste.size()];

		for (int i = 0; i < liste.size(); i++) {

			listeCl[i] = liste.get(i).afficher();
		}

		JOptionPane jop = null;
		String retour = (String) jop.showInputDialog(null, "Veuillez sélectionner l'entreprise à supprimer :",
				"Suppression Client", JOptionPane.OK_CANCEL_OPTION, null, listeCl, listeCl[0]);

		String[] str = retour.split(" : ");

		if (model.deleteClient(str)) {

			jop.showMessageDialog(null, "" + "Suppression bien effectuée !", "Infos", JOptionPane.INFORMATION_MESSAGE);
		} else {
			jop.showMessageDialog(null, "Rapport d'erreur : Impossible d'accéder à cette requête.", "Infos",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void afficheListeOperateur() {
		ArrayList<Operateur> liste = model.getListeOperateur();

		try {
			if (liste.size() != 0 && liste != null) {

				pannel.setBackground(Color.WHITE);
				FlowLayout fl = new FlowLayout();
				fl.setVgap(20);
				pannel.setLayout(new BorderLayout());
				pannel.setPreferredSize(new Dimension(700, 550));

				JLabel label = new JLabel(" Liste opérateur(s)");
				label.setFont(new Font("Times New Roman", Font.BOLD, 30));
				label.setForeground(Color.RED);
				label.setHorizontalAlignment(JLabel.CENTER);

				// String nom, String prenom, String adresse, int numTel,
				// String specialite
				String[] str;
				Object[][] object = new Object[liste.size()][8];
				String[] header = { "N°", "Nom", "Prénom", "Adresse", "Téléphone", "Spécialité", "Nom Ticket",
						"Plus d'infos" };

				for (int i = 0; i < liste.size(); i++) {

					object[i][0] = i + 1;
					object[i][1] = ((liste.get(i).getNom() == null) ? "null" : liste.get(i).getNom());
					object[i][2] = ((liste.get(i).getPrenom() == null) ? "null" : liste.get(i).getPrenom());
					object[i][3] = ((liste.get(i).getAdresse() == null) ? "null" : liste.get(i).getAdresse());
					object[i][4] = ((String.valueOf(liste.get(i).getNumTel()) == null) ? "null"
							: "0" + liste.get(i).getNumTel());
					object[i][5] = ((liste.get(i).getSpecialite() == null) ? "null" : liste.get(i).getSpecialite());
					object[i][6] = ((liste.get(i).getListeMaintenance().get(0).isValidate())
							? ((liste.get(i).getListeMaintenance().get(0).getNomMaintenance() == null) ? "null"
									: liste.get(i).getListeMaintenance().get(0).getNomMaintenance())
							: "non spécifié");
					object[i][7] = "(+ Plus)";

				}

				TableModel tabModel = new TableModel(object, header);
				JTable table = new JTable(tabModel);
				ButtonEditor be = new ButtonEditor(new JCheckBox());
				table.getColumn("Plus d'infos").setCellEditor(be);
				table.setFont(new Font("Times New Roman", Font.BOLD, 14));
				table.setRowHeight(50);

				be.setControl(control);
				be.setListeOp(liste);

				JScrollPane jsp2 = new JScrollPane(table);
				jsp2.setPreferredSize(new Dimension(500, 400));

				pannel.setTitle(label.getText());
				pannel.add(jsp2);

				updateObserver(pannel);

			} else {
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Aucun opérateur enrégistré. ", "Infos", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (NullPointerException npe) {
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Aucun opérateur enrégistré. ", "Infos", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void ListeOUModifieClient() {

		try {
			ArrayList<Client> liste = model.getListeClient();

			if (control.getRole().equals("client")) {

				int nbreTm = liste.size(), j = 0;
				E.e("liste.size = " + liste.size());

				ArrayList<Client> listeT = new ArrayList<Client>();
				for (int i = 0; i < nbreTm; i++)
					listeT.add(liste.get(i));

				j = nbreTm;
				for (int i = 0; i < nbreTm; i++) {
					if (!listeT.get(i).getNomEntreprise().toLowerCase()
							.equals(control.getCl().getNomEntreprise().toLowerCase())) {
						E.e("entre ici");
						liste.remove(Math.abs((nbreTm - i) - j));
						j--;
					}
				}
			}

			if (liste.size() != 0 && liste != null) {

				pannel.setBackground(Color.WHITE);
				FlowLayout fl = new FlowLayout();
				fl.setVgap(20);
				pannel.setLayout(new BorderLayout());
				pannel.setPreferredSize(new Dimension(700, 550));

				JLabel label = new JLabel(" Liste clients)");
				label.setFont(new Font("Times New Roman", Font.BOLD, 30));
				label.setForeground(Color.RED);
				label.setHorizontalAlignment(JLabel.CENTER);

				// String nom, String prenom, String adresse, int numTel,
				// String specialite
				String[] str;
				Object[][] object = new Object[liste.size()][8];
				String[] header = { "N°", "Nom", "Numéro SIRET", "Adresse", "Code APE", "Date Création", "Téléphone",
						"Plus d'infos" };

				for (int i = 0; i < liste.size(); i++) {

					object[i][0] = i + 1;
					object[i][1] = ((liste.get(i).getNomEntreprise() == null) ? "non spécifié"
							: liste.get(i).getNomEntreprise());
					object[i][2] = ((liste.get(i).getNumSiret() == null) ? "non spécifié" : liste.get(i).getNumSiret());
					object[i][3] = ((liste.get(i).getAdresse() == null) ? "non spécifié" : liste.get(i).getAdresse());
					object[i][4] = ((String.valueOf(liste.get(i).getCodeAPE()) == null) ? "non spécifié"
							: liste.get(i).getCodeAPE());
					object[i][5] = ((liste.get(i).getDateCreation() == null) ? "non spécifié"
							: liste.get(i).getDateCreation());
					object[i][6] = ((liste.get(i).getNumTel() == 0) ? "non spécifié" : "0" + liste.get(i).getNumTel());
					object[i][7] = "(+)";

				}

				TableModel tabModel = new TableModel(object, header);
				JTable table = new JTable(tabModel);
				ButtonEditor be = new ButtonEditor(new JCheckBox());
				table.getColumn("Plus d'infos").setCellEditor(be);
				table.setFont(new Font("Times New Roman", Font.BOLD, 14));
				table.setRowHeight(50);

				be.setControl(control);
				be.setListeCl(liste);

				JScrollPane jsp2 = new JScrollPane(table);
				jsp2.setPreferredSize(new Dimension(500, 400));

				pannel.setTitle(label.getText());
				pannel.add(jsp2);

				updateObserver(pannel);

			} else {
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Aucun client enrégistré. ", "Infos", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (NullPointerException npe) {
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Aucun client enrégistré. ", "Infos", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void ajouteTicket() {

		pannel.setBackground(Color.WHITE);
		FlowLayout fl = new FlowLayout();
		fl.setVgap(20);
		pannel.setLayout(new BorderLayout());
		pannel.setPreferredSize(new Dimension(700, 550));

		JLabel label = new JLabel(" (+) Ticket de maintenance");
		label.setFont(new Font("Times New Roman", Font.BOLD, 50));
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(JLabel.CENTER);

		jtfID = new JTextField();
		jtfType = new JTextField();
		jtfDate = new JTextField();
		jtfDuree = new JTextField();
		jtfDescription = new JTextArea();
		JScrollPane jsp = new JScrollPane(jtfDescription);
		jsp.setPreferredSize(new Dimension(400, 160));
		jtfDevis = new JTextField();
		jtfID.setPreferredSize(new Dimension(200, 45));
		jtfType.setPreferredSize(new Dimension(200, 60));
		jtfDate.setPreferredSize(new Dimension(400, 45));
		jtfDuree.setPreferredSize(new Dimension(200, 45));
		jtfDevis.setPreferredSize(new Dimension(200, 45));
		// jtfDescription.setPreferredSize(new Dimension(400, 100));
		jtfID.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfType.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfDate.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfDuree.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfDevis.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfDescription.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfDescription.setBorder(BorderFactory.createTitledBorder("Description *"));
		jtfDate.setBorder(BorderFactory.createTitledBorder("Date d'amorçage de la maintenance (jj/mm/aaaa)"));
		jtfID.setBorder(BorderFactory.createTitledBorder("Nom du Ticket *"));
		jtfType.setBorder(BorderFactory.createTitledBorder("Type de maintenance à effectuer :"));
		jtfDuree.setBorder(BorderFactory.createTitledBorder("Durée * "));
		jtfDevis.setBorder(BorderFactory.createTitledBorder("Budget total (en €) * "));

		butValTicket = new JButton("Enregistrer !");
		butValTicket.setPreferredSize(new Dimension(250, 30));
		butValTicket.setBackground(Color.LIGHT_GRAY);
		butValTicket.setForeground(Color.BLACK);
		butValTicket.setFont(new Font("Times New Roman", Font.BOLD, 18));
		butValTicket.addActionListener(this);

		JPanel pannel1 = new JPanel();
		pannel1.setPreferredSize(new Dimension(600, 90));
		pannel1.setLayout(fl);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(600, 300));
		JPanel pannel2 = new JPanel();
		pannel2.setPreferredSize(new Dimension(600, 150));
		pannel2.setLayout(new GridLayout(2, 2));
		JPanel pannel3 = new JPanel();
		pannel3.setPreferredSize(new Dimension(600, 240));
		pannel3.setLayout(new BorderLayout());
		JPanel pannel4 = new JPanel();

		pannel1.add(label);
		pannel2.add(jtfID);
		pannel2.add(jtfDate);
		pannel2.add(jtfDuree);
		pannel2.add(jtfDevis);
		pannel3.add(jtfType, BorderLayout.NORTH);
		pannel3.add(jsp, BorderLayout.SOUTH);
		pannel4.add(butValTicket);

		panel.add(pannel2);
		panel.add(pannel3);
		pannel.add(pannel1, BorderLayout.NORTH);
		pannel.add(panel, BorderLayout.CENTER);
		pannel.add(pannel4, BorderLayout.SOUTH);

		updateObserver(pannel);

	}

	private void ajouteClient() {
		pannel.setBackground(Color.WHITE);
		FlowLayout fl = new FlowLayout();
		fl.setVgap(20);
		pannel.setLayout(new BorderLayout());
		pannel.setPreferredSize(new Dimension(700, 550));

		JLabel label = new JLabel("Ajout d'un Client");
		label.setFont(new Font("Times New Roman", Font.BOLD, 50));
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(JLabel.CENTER);
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

		jtfID = new JTextField();
		jtfPassword = new JPasswordField();
		jtfNumSiret = new JTextField();
		jtfAdresse = new JTextField();
		jtfCodeAPE = new JTextField();
		jtfNumTel = new JTextField();
		jtfID.setPreferredSize(new Dimension(200, 45));
		jtfPassword.setPreferredSize(new Dimension(200, 45));
		jtfNumSiret.setPreferredSize(new Dimension(200, 45));
		jtfAdresse.setPreferredSize(new Dimension(200, 45));
		jtfCodeAPE.setPreferredSize(new Dimension(200, 45));
		jtfNumTel.setPreferredSize(new Dimension(200, 45));
		jtfID.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfPassword.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfNumTel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfNumSiret.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfAdresse.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfCodeAPE.setFont(new Font("Times New Roman", Font.BOLD, 14));

		butVal = new JButton("Enregistrer !");
		butVal.setPreferredSize(new Dimension(250, 30));
		butVal.setBackground(Color.LIGHT_GRAY);
		butVal.setForeground(Color.BLACK);
		butVal.setFont(new Font("Times New Roman", Font.BOLD, 18));
		butVal.addActionListener(this);

		JPanel pannel1 = new JPanel();
		// pannel1.setBackground(Color.WHITE);
		pannel1.setPreferredSize(new Dimension(500, 90));
		pannel1.setLayout(fl);
		JPanel pannel2 = new JPanel();
		// pannel2.setBackground(Color.WHITE);
		pannel2.setLayout(new GridLayout(6, 6));
		JPanel pannel3 = new JPanel();
		// pannel3.setBackground(Color.WHITE);

		pannel1.add(label);
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

		updateObserver(pannel);
	}

	private void afficheListeTickets() {

		ArrayList<TicketMaintenance> liste = model.getListeMaintenance();

		try {
			if (liste.size() != 0 && liste != null) {

				pannel.setBackground(Color.WHITE);
				FlowLayout fl = new FlowLayout();
				fl.setVgap(20);
				pannel.setLayout(new BorderLayout());
				pannel.setPreferredSize(new Dimension(700, 550));

				JLabel label = new JLabel(" Liste des Tickets de maintenance");
				label.setFont(new Font("Times New Roman", Font.BOLD, 30));
				label.setForeground(Color.RED);
				label.setHorizontalAlignment(JLabel.CENTER);

				String[] str;

				String[] header = { "N°", "Nom Maintenance", "Type", "Durée", "Date de début", "Description",
						"Entreprise émettrice", "Nom Opérateur", "Validation", "Plus d'infos" };

				if (control.getRole().equals("responsable")) {
					Object[][] object = new Object[liste.size()][10];

					for (int i = 0; i < liste.size(); i++) {

						object[i][0] = i + 1;
						object[i][1] = ((liste.get(i).getNomMaintenance() == null) ? "null"
								: liste.get(i).getNomMaintenance());
						object[i][2] = ((liste.get(i).getTypeMaintenance() == null) ? "null"
								: liste.get(i).getTypeMaintenance());
						object[i][3] = ((liste.get(i).getDureeMaintenance() == null) ? "null"
								: liste.get(i).getDureeMaintenance());
						object[i][4] = ((liste.get(i).getDateDebut() == null) ? "null" : liste.get(i).getDateDebut());
						object[i][5] = ((liste.get(i).getDesciption() == null) ? "null" : liste.get(i).getDesciption());
						object[i][6] = ((liste.get(i).getClient().getNomEntreprise() == null) ? "null"
								: liste.get(i).getClient().getNomEntreprise());
						object[i][7] = ((liste.get(i).getOperateur().getPrenom() == null) ? "null"
								: liste.get(i).getOperateur().getPrenom());
						object[i][8] = liste.get(i).isValidate();
						object[i][9] = "( + )";

					}

					TableModel tabModel = new TableModel(object, header);
					JTable table = new JTable(tabModel);
					ButtonEditor be = new ButtonEditor(new JCheckBox());
					table.getColumn("Plus d'infos").setCellEditor(be);
					table.setFont(new Font("Times New Roman", Font.BOLD, 14));
					table.setRowHeight(50);

					be.setControl(control);
					be.setListeTm(liste);

					JScrollPane jsp2 = new JScrollPane(table);
					jsp2.setPreferredSize(new Dimension(500, 400));

					pannel.add(jsp2);

					updateObserver(pannel);

				}
				if (control.getRole().equals("operateur")) {
					int nbreTm = liste.size(), j = 0;
					E.e("liste.size = " + liste.size());

					ArrayList<TicketMaintenance> listeT = new ArrayList<TicketMaintenance>();
					for (int i = 0; i < nbreTm; i++)
						listeT.add(liste.get(i));

					j = nbreTm;
					for (int i = 0; i < nbreTm; i++) {
						if (!listeT.get(i).getOperateur().getPrenom().toLowerCase()
								.equals(control.getOp().getPrenom().toLowerCase()) || !listeT.get(i).isValidate()) {
							E.e("entre ici");
							liste.remove(Math.abs((nbreTm - i) - j));
							j--;
						}

						E.e(listeT.get(i).getOperateur().getPrenom().toLowerCase() + "-l : c-"
								+ control.getOp().getPrenom().toLowerCase());
					}

					Object[][] object = new Object[liste.size()][10];

					for (int i = 0; i < liste.size(); i++) {

						object[i][0] = i + 1;
						object[i][1] = ((liste.get(i).getNomMaintenance() == null) ? "null"
								: liste.get(i).getNomMaintenance());
						object[i][2] = ((liste.get(i).getTypeMaintenance() == null) ? "null"
								: liste.get(i).getTypeMaintenance());
						object[i][3] = ((liste.get(i).getDureeMaintenance() == null) ? "null"
								: liste.get(i).getDureeMaintenance());
						object[i][4] = ((liste.get(i).getDateDebut() == null) ? "null" : liste.get(i).getDateDebut());
						object[i][5] = ((liste.get(i).getDesciption() == null) ? "null" : liste.get(i).getDesciption());
						object[i][6] = ((liste.get(i).getClient().getNomEntreprise() == null) ? "null"
								: liste.get(i).getClient().getNomEntreprise());
						object[i][7] = ((liste.get(i).getOperateur().getPrenom() == null) ? "null"
								: liste.get(i).getOperateur().getPrenom());
						object[i][8] = liste.get(i).isValidate();
						object[i][9] = "( + )";

					}

					E.e("nbreTm = " + nbreTm);
					if (liste.size() != 0) {
						TableModel tabModel = new TableModel(object, header);
						JTable table = new JTable(tabModel);
						ButtonEditor be = new ButtonEditor(new JCheckBox());
						table.getColumn("Plus d'infos").setCellEditor(be);
						table.setFont(new Font("Times New Roman", Font.BOLD, 14));
						table.setRowHeight(50);

						be.setControl(control);
						be.setListeTm(liste);

						JScrollPane jsp2 = new JScrollPane(table);
						jsp2.setPreferredSize(new Dimension(500, 400));

						pannel.add(label);
						pannel.add(jsp2);

						updateObserver(pannel);
					} else {
						JOptionPane jop = null;
						jop.showMessageDialog(null,
								control.getOp().getPrenom().toUpperCase()
										+ " aucun projet de maintenance ne vous a été affecté pour le moment. ",
								"Infos", JOptionPane.INFORMATION_MESSAGE);
					}

				}
				if (control.getRole().equals("client")) {

					int nbreTm = liste.size(), j = 0;
					E.e("liste.size = " + liste.size());

					ArrayList<TicketMaintenance> listeT = new ArrayList<TicketMaintenance>();
					for (int i = 0; i < nbreTm; i++)
						listeT.add(liste.get(i));

					j = nbreTm;
					for (int i = 0; i < nbreTm; i++) {
						if (!listeT.get(i).getClient().getNomEntreprise().toLowerCase()
								.equals(control.getCl().getNomEntreprise().toLowerCase())) {
							E.e("entre ici");
							liste.remove(Math.abs((nbreTm - i) - j));
							j--;
						}

					}

					Object[][] object = new Object[liste.size()][10];

					for (int i = 0; i < liste.size(); i++) {

						object[i][0] = i + 1;
						object[i][1] = ((liste.get(i).getNomMaintenance() == null) ? "null"
								: liste.get(i).getNomMaintenance());
						object[i][2] = ((liste.get(i).getTypeMaintenance() == null) ? "null"
								: liste.get(i).getTypeMaintenance());
						object[i][3] = ((liste.get(i).getDureeMaintenance() == null) ? "null"
								: liste.get(i).getDureeMaintenance());
						object[i][4] = ((liste.get(i).getDateDebut() == null) ? "null" : liste.get(i).getDateDebut());
						object[i][5] = ((liste.get(i).getDesciption() == null) ? "null" : liste.get(i).getDesciption());
						object[i][6] = ((liste.get(i).getClient().getNomEntreprise() == null) ? "null"
								: liste.get(i).getClient().getNomEntreprise());
						object[i][7] = ((liste.get(i).getOperateur().getPrenom() == null) ? "null"
								: liste.get(i).getOperateur().getPrenom());
						object[i][8] = liste.get(i).isValidate();
						object[i][9] = "( + )";

					}

					E.e("nbreTm = " + nbreTm);
					if (liste.size() != 0) {
						TableModel tabModel = new TableModel(object, header);
						JTable table = new JTable(tabModel);
						ButtonEditor be = new ButtonEditor(new JCheckBox());
						table.getColumn("Plus d'infos").setCellEditor(be);
						table.setFont(new Font("Times New Roman", Font.BOLD, 14));
						table.setRowHeight(50);

						be.setControl(control);
						be.setListeTm(liste);

						JScrollPane jsp2 = new JScrollPane(table);
						jsp2.setPreferredSize(new Dimension(500, 400));

						pannel.add(label);
						pannel.add(jsp2);

						updateObserver(pannel);
					} else {
						JOptionPane jop = null;
						jop.showMessageDialog(null, "Vous n'avez soumis aucun projet de maintenance pour le moment. ",
								"Infos", JOptionPane.INFORMATION_MESSAGE);
					}

				}

			} else {
				E.e("heho");
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Aucun projet de maintenance enrégistré. ", "Infos",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (NullPointerException npe) {
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Aucun projet de maintenance enrégistré. ", "Infos",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * @return the retour
	 */
	public boolean isRetour() {
		return retour;
	}

	/**
	 * @param retour
	 *            the retour to set
	 */
	public void setRetour(boolean retour) {
		this.retour = retour;
	}

	@Override
	public void addObserver(Observer obs) {
		// TODO Auto-generated method stub
		listeObs.add(obs);

	}

	@Override
	public void updateObserver(MiniWindow panel) {
		// TODO Auto-generated method stub
		for (Observer obs : listeObs) {
			obs.update(panel);

		}

	}

	@Override
	public void updateObserver(String chaine) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the control
	 */
	public Control getControl() {
		return control;
	}

	/**
	 * @param control
	 *            the control to set
	 */
	public void setControl(Control control) {
		this.control = control;
	}

	public void changeSize(JTable tableau, int width, int height) {
		// Nous créons un objet TableColumn afin de travailler sur notre colonne
		TableColumn col;
		for (int i = 0; i < tableau.getColumnCount(); i++) {
			// if (i == 4) {
			// On récupère le modèle de la colonne
			col = tableau.getColumnModel().getColumn(i);
			// On lui affecte la nouvelle valeur
			col.setPreferredWidth(width);
			// }
		}
		for (int i = 0; i < tableau.getRowCount(); i++) {
			// On affecte la taille de la ligne à l'indice spécifié !
			// if (i == 4)
			tableau.setRowHeight(i, height);
		}
	}

	@Override
	public void updateObserverResearch(ArrayList<String> sb) {
		// TODO Auto-generated method stub

	}
}
