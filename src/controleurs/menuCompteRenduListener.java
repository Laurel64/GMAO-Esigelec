package controleurs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import beans.CompteRendu;
import beans.TicketMaintenance;
import models.Model;
import patternObs.Observable;
import patternObs.Observer;
import vues.E;
import vues.MiniWindow;

public class menuCompteRenduListener implements ActionListener, Observable {

	private ArrayList<Observer> listeObs = new ArrayList<Observer>();

	private MiniWindow pannel;

	private Model model;

	private Control control;

	private JPanel panelDescription;

	private JTextArea panelListeCr;

	private JPanel panelCr;

	private JPanel panelModif;

	private JTextArea panelDes;

	private JButton butModif;

	private JTree arbre;

	private JPanel panelT;

	private ArrayList<TicketMaintenance> liste;

	private JButton butEnregistrer;

	private JButton butAnnuler;

	private JPanel panelBas;

	private JLabel label;

	private JSplitPane split;

	private JTextArea jta;

	private JButton butVal;

	private int idTm;

	private boolean haveMinutes;

	private JLabel label1;

	private JLabel labelDate;

	private int idCr;

	public boolean CrValider;

	private JButton butValiderCr;

	private JButton butInvalider;

	public menuCompteRenduListener(Model model, Control control) {
		super();
		this.model = model;
		this.control = control;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		haveMinutes = false;

		pannel = new MiniWindow();

		if (((JButton) e.getSource()).getText().equals("Liste des Comptes Rendus")) {

			afficheCompteRendu();

		}

		if (((JButton) e.getSource()).getText().equals("Ajouter compte rendu")) {

			ajouterCompteRendu();

		}

		if (e.getSource() == butVal) {

			if (!panelDes.getText().isEmpty()) {

				Connection con = ConnexionBDD.connectDataBase();

				Statement state;

				int nbre = 0;

				try {
					state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

					String query = "INSERT INTO compterendu_cr(cr_id, cr_description, cr_tm_id, cr_isvalidate) VALUES(cr.NEXTVAL, '"
							+ panelDes.getText() + "', " + idTm + ", 0)";

					state.executeQuery(query);
					nbre = 1;

					state.close();
				} catch (SQLException e1) {
					JOptionPane jop = null;
					jop.showMessageDialog(null,
							"Impossible d'accéder à cette requête.\n Vérifiez que vous n'avez pas utilisé le caractère : ' dans votre rédaction.",
							"Infos", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}

				if (nbre == 1) {
					JOptionPane jop = null;
					jop.showMessageDialog(null, "Compte rendu bien enrégistré. ", "Infos",
							JOptionPane.INFORMATION_MESSAGE);
					butVal.setEnabled(false);
					butAnnuler.setEnabled(false);
					panelDes.setText("");
					panelDes.setEditable(false);
					label.setText(
							"Sélectionnez dans le menu de gauche le ticket dont vous voulez éditer un compte rendu.");
					butAnnuler.setEnabled(false);
				}
			} else {
				JOptionPane jop = null;
				jop.showMessageDialog(null,
						"Impossible de sauvegarder ce compte rendu car VIDE. \n Veuillez rédiger du texte dans la zone appropriée.",
						"Infos", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (e.getSource() == butModif) {

			this.panelDes.setEditable(true);
			butModif.setEnabled(false);
			butEnregistrer.setEnabled(true);
			butAnnuler.setEnabled(true);
		}

		if (e.getSource() == butEnregistrer) {
			updateCompteRendu();
		}
	}

	private void updateCompteRendu() {

		if (!panelDes.getText().isEmpty()) {
			Connection con = ConnexionBDD.connectDataBase();

			Statement state;

			int nbre = 0;

			try {
				state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

				String query = "UPDATE compterendu_cr SET cr_description = '" + panelDes.getText() + "', "
						+ "cr_datemisajour = SYSDATE WHERE cr_id = " + idCr;

				state.executeQuery(query);
				nbre = 1;

				state.close();
			} catch (SQLException e1) {
				JOptionPane jop = null;
				jop.showMessageDialog(null,
						"Impossible d'accéder à cette requête.\n Vérifiez que vous n'avez pas utilisé le caractère : ' dans votre rédaction.",
						"Infos", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}

			if (nbre == 1) {
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Compte rendu bien mis à jour. ", "Infos", JOptionPane.INFORMATION_MESSAGE);
				butEnregistrer.setEnabled(false);
				butAnnuler.setEnabled(false);
				panelDes.setText("");
				panelDes.setEditable(false);
				label.setText("Description");
				labelDate.setText("Date");
				label1.setText("");
				butModif.setEnabled(false);
				butAnnuler.setEnabled(false);
			}
		} else {
			JOptionPane jop = null;
			jop.showMessageDialog(null,
					"Impossible de mettre à jour ce compte rendu car VIDE. \n Veuillez rédiger du texte dans la zone appropriée.",
					"Infos", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void ajouterCompteRendu() {

		try {
			liste = getListeMaintenance();

			E.e("oui");
			if (liste.size() != 0 && liste != null) {
				E.e("oui-oui");
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());

				panelT = new JPanel();
				panelT.setBorder(BorderFactory.createLineBorder(Color.RED));
				panelT.setPreferredSize(new Dimension(300, 400));
				panelT.setLayout(new BorderLayout());
				jta = new JTextArea();
				jta.setBorder(BorderFactory.createLineBorder(Color.RED));
				jta.setPreferredSize(new Dimension(300, 400));
				FlowLayout fl = new FlowLayout();
				jta.setLayout(fl);
				jta.setEditable(false);
				JScrollPane jsp = new JScrollPane(jta);
				jsp.setPreferredSize(new Dimension(300, 540));
				jsp.setBorder(BorderFactory.createLineBorder(Color.pink));
				addListeMaintenance();
				panelT.add(jsp, BorderLayout.CENTER);

				panelDescription = new JPanel();
				panelDescription.setBorder(BorderFactory.createLineBorder(Color.green));
				panelDescription.setPreferredSize(new Dimension(600, 600));
				panelDescription.setLayout(new BorderLayout());
				panelModif = new JPanel();
				panelModif.setBorder(BorderFactory.createLineBorder(Color.orange));
				panelModif.setPreferredSize(new Dimension(600, 60));
				label = new JLabel(
						" Sélectionnez dans le menu de gauche le ticket dont vous voulez éditer un compte rendu.");
				panelModif.add(label);
				panelBas = new JPanel();
				panelBas.setBorder(BorderFactory.createLineBorder(Color.orange));
				panelBas.setPreferredSize(new Dimension(600, 60));
				panelDes = new JTextArea();
				panelDes.setEditable(false);
				butVal = new JButton("Valider !");
				butVal.setPreferredSize(new Dimension(250, 30));
				butVal.setBackground(Color.RED);
				butVal.setForeground(Color.WHITE);
				butVal.setFont(new Font("Times New Roman", Font.BOLD, 18));
				butVal.addActionListener(this);
				butAnnuler = new JButton("Fermer");
				butAnnuler.setPreferredSize(new Dimension(180, 30));
				butAnnuler.setBackground(Color.LIGHT_GRAY);
				butAnnuler.setForeground(Color.BLACK);
				butAnnuler.setFont(new Font("Times New Roman", Font.BOLD, 18));
				butAnnuler.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						butVal.setEnabled(false);
						butAnnuler.setEnabled(false);
						panelDes.setEditable(false);
						label.setText(
								" Sélectionnez dans le menu de gauche le ticket dont vous voulez éditer un compte rendu.");
						butAnnuler.setEnabled(false);
					}
				});
				butVal.setEnabled(false);
				butAnnuler.setEnabled(false);
				panelBas.add(butVal);
				panelBas.add(butAnnuler);
				JScrollPane jsp2 = new JScrollPane(panelDes);
				jsp.setPreferredSize(new Dimension(600, 540));
				jsp.setBorder(BorderFactory.createLineBorder(Color.pink));
				panelDescription.add(panelModif, BorderLayout.NORTH);
				panelDescription.add(jsp2, BorderLayout.CENTER);
				panelDescription.add(panelBas, BorderLayout.SOUTH);

				split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelT, panelDescription);

				panel.add(split, BorderLayout.CENTER);
				pannel.add(panel);

				updateObserver(pannel);
			} else {
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Aucun projet de maintenance enrégistré. ", "Infos",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (NullPointerException e) {
			E.e("heho");
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Aucun projet de maintenance enrégistré. ", "Infos",
					JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}

	}

	private void addListeMaintenance() {
		for (int i = 0; i < liste.size(); i++) {

			JButton butM = new JButton(i + 1 + " - " + liste.get(i).getNomMaintenance());
			butM.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String name = ((JButton) e.getSource()).getText().split(" - ")[1];
					panelDes.setEditable(true);
					butVal.setEnabled(true);
					butAnnuler.setEnabled(true);
					label.setText("Nom du Ticket : " + name);
					Connection con = ConnexionBDD.connectDataBase();

					Statement state;

					int nbre = 0;

					try {
						state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "SELECT tm_id FROM ticketmaintenance_tm WHERE tm_nom = '" + name + "'";

						ResultSet rs = state.executeQuery(query);

						if (rs.next()) {
							idTm = Integer.parseInt(rs.getObject("tm_id").toString());
						}
						nbre = 1;

						state.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			});
			jta.add(butM);
		}

	}

	private void afficheCompteRendu() {
		try {
			liste = getListeMaintenance();
			E.e("euy");
			if (liste.size() != 0 && liste != null) {
				E.e("euy-euy");

				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());

				panelT = new JPanel();
				panelT.setBorder(BorderFactory.createLineBorder(Color.RED));
				panelT.setPreferredSize(new Dimension(300, 400));
				panelT.setLayout(new BorderLayout());

				panelDescription = new JPanel();
				panelDescription.setBorder(BorderFactory.createLineBorder(Color.green));
				panelDescription.setPreferredSize(new Dimension(600, 600));
				panelModif = new JPanel();
				panelModif.setBorder(BorderFactory.createLineBorder(Color.orange));
				panelModif.setPreferredSize(new Dimension(600, 160));
				panelModif.setLayout(new GridLayout(4, 1));
				label1 = new JLabel();
				label1.setForeground(Color.RED);
				label1.setFont(new Font("Times New Roman", Font.BOLD, 18));
				label = new JLabel("Description ");
				labelDate = new JLabel("Date");
				butModif = new JButton(new ImageIcon("tn_modif.png"));
				butModif.setToolTipText("Cliquer pour modifier le compte Rendu");
				butModif.setEnabled(false);
				butModif.addActionListener(this);
				if (control.getRole().equals("operateur")) {
					panelModif.add(butModif);
				}
				panelModif.add(label);
				panelModif.add(labelDate);
				panelModif.add(label1);
				panelBas = new JPanel();
				panelBas.setBorder(BorderFactory.createLineBorder(Color.orange));
				panelBas.setPreferredSize(new Dimension(600, 60));
				butEnregistrer = new JButton("Enrégistrer");
				butEnregistrer.setPreferredSize(new Dimension(250, 30));
				butEnregistrer.setBackground(Color.RED);
				butEnregistrer.setForeground(Color.WHITE);
				butEnregistrer.setFont(new Font("Times New Roman", Font.BOLD, 18));
				butEnregistrer.addActionListener(this);
				butAnnuler = new JButton("Fermer");
				butAnnuler.setPreferredSize(new Dimension(180, 30));
				butAnnuler.setBackground(Color.LIGHT_GRAY);
				butAnnuler.setForeground(Color.BLACK);
				butAnnuler.setFont(new Font("Times New Roman", Font.BOLD, 18));
				butAnnuler.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						butModif.setEnabled(true);
						butEnregistrer.setEnabled(false);
						butAnnuler.setEnabled(false);
						panelDes.setText("");
						panelDes.setEditable(false);
						label.setText("Description");
						label1.setText("");
						labelDate.setText("Date");
					}
				});
				butEnregistrer.setEnabled(false);
				butAnnuler.setEnabled(false);
				if (control.getRole().equals("operateur") || control.getRole().equals("client")) {
					panelBas.add(butEnregistrer);
					panelBas.add(butAnnuler);
				}
				panelDes = new JTextArea();
				panelDes.setEditable(false);
				JScrollPane jsp = new JScrollPane(panelDes);
				jsp.setPreferredSize(new Dimension(600, 500));
				jsp.setBorder(BorderFactory.createLineBorder(Color.pink));
				panelDescription.add(panelModif, BorderLayout.NORTH);
				panelDescription.add(jsp, BorderLayout.CENTER);
				panelDescription.add(panelBas, BorderLayout.SOUTH);

				split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelT, panelDescription);

				panel.add(split, BorderLayout.CENTER);
				pannel.add(panel);

				buildTree(liste);
				if (haveMinutes) {
					updateObserver(pannel);
				} else {
					JOptionPane jop = null;
					jop.showMessageDialog(null, "Aucun compte rendu édité. ", "Infos", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Aucun projet de maintenance pour l'instant. ", "Infos",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (NullPointerException e) {
			E.e("heho");
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Aucun projet de maintenance pour l'instant. ", "Infos",
					JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}

	}

	private ArrayList<TicketMaintenance> getListeMaintenance() throws NullPointerException {

		ArrayList<TicketMaintenance> liste = model.getListeMaintenance();

		if (liste.size() != 0 && liste != null) {

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

			}

			if (control.getRole().equals("responsable")) {
				int nbreTm = liste.size();
				E.e("liste.size = " + liste.size());

				ArrayList<TicketMaintenance> listeT = new ArrayList<TicketMaintenance>();
				for (int i = 0; i < nbreTm; i++)
					listeT.add(liste.get(i));

				for (int i = 0; i < nbreTm; i++) {
					int nbreCr = liste.get(i).getListeCompteRendu().size(), j = 0;
					E.e("liste.size = " + liste.size());

					ArrayList<CompteRendu> listeCr = new ArrayList<CompteRendu>();
					j = nbreCr;
					for (int l = 0; l < nbreCr; l++)
						listeCr.add(liste.get(i).getListeCompteRendu().get(l));
					for (int k = 0; k < nbreCr; k++) {
						if (!listeCr.get(k).isValidate()) {
							E.e("entre ici hihi");
							liste.get(i).getListeCompteRendu().remove(Math.abs((nbreCr - k) - j));
							j--;
						}
					}

				}
			}
		}

		return liste;

	}

	private void buildTree(ArrayList<TicketMaintenance> liste) {

		// Création d'une racine
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Comptes Rendus");

		for (int i = 0; i < liste.size(); i++) {
			if (liste.get(i).getListeCompteRendu().size() != 0) {
				E.e("size = " + liste.get(i).getListeCompteRendu().size());
				DefaultMutableTreeNode cr = new DefaultMutableTreeNode(
						"Ticket " + i + 1 + " : " + liste.get(i).getNomMaintenance());

				for (int j = 0; j < liste.get(i).getListeCompteRendu().size(); j++) {
					DefaultMutableTreeNode rep2 = new DefaultMutableTreeNode(
							"CR n°" + liste.get(i).getListeCompteRendu().get(j).getIdentifiant() + "--"
									+ liste.get(i).getListeCompteRendu().get(j).getDateEdition());
					haveMinutes = true;

					E.e("On a un compte rendu");

					cr.add(rep2);
				}

				racine.add(cr);
			}
		}

		// Nous créons, avec notre hiérarchie, un arbre
		arbre = new JTree(racine);
		arbre.addTreeSelectionListener(new TreeListener());
		arbre.setBackground(Color.WHITE);

		this.panelT.add(new JScrollPane(arbre));
	}

	class TreeListener implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent e) {

			String name = null;

			if ((name = arbre.getLastSelectedPathComponent().toString()) != null) {

				name = name.split("--")[0].split("°")[1];

				for (int i = 0; i < liste.size(); i++) {

					for (int j = 0; j < liste.get(i).getListeCompteRendu().size(); j++) {
						if (liste.get(i).getListeCompteRendu().get(j).getIdentifiant() == Integer.parseInt(name)) {
							label.setText(getAbsolutePath(e.getPath()));
							panelDes.setText(liste.get(i).getListeCompteRendu().get(j).getDescription());
							label1.setText(liste.get(i).getListeCompteRendu().get(j).isValidate()
									? "Compte rendu VALIDE" : "Compte rendu NON VALIDE");
							labelDate.setText("Dernière mise à jour : "
									+ liste.get(i).getListeCompteRendu().get(j).getDateMisAJour());
							butModif.setEnabled(true);
							idCr = liste.get(i).getListeCompteRendu().get(j).getIdentifiant();
							CrValider = liste.get(i).getListeCompteRendu().get(j).isValidate();
							if (control.getRole().equals("operateur") && CrValider) {
								panelModif.removeAll();
								panelModif.revalidate();
								panelModif.add(label);
								panelModif.add(labelDate);
								panelModif.add(label1);
								panelModif.revalidate();
								panelModif.repaint();
							} else if (!CrValider) {
								panelModif.removeAll();
								panelModif.revalidate();
								panelModif.add(butModif);
								panelModif.add(label);
								panelModif.add(labelDate);
								panelModif.add(label1);
								panelModif.revalidate();
								panelModif.repaint();
							}
							if (control.getRole().equals("client")) {
								butValiderCr = new JButton("Valider le Compte Rendu");
								butValiderCr.setPreferredSize(new Dimension(250, 30));
								butValiderCr.setBackground(Color.white);
								butValiderCr.setForeground(Color.RED);
								butValiderCr.setFont(new Font("Times New Roman", Font.BOLD, 18));
								butValiderCr.addActionListener(new TreeCRListener());
								butInvalider = new JButton("Invalider le Compte Rendu");
								butInvalider.setPreferredSize(new Dimension(250, 30));
								butInvalider.setBackground(Color.white);
								butInvalider.setForeground(Color.BLACK);
								butInvalider.setFont(new Font("Times New Roman", Font.BOLD, 16));
								butInvalider.addActionListener(new TreeCRListener());

								if (CrValider) {
									panelModif.removeAll();
									panelModif.revalidate();
									panelModif.add(label);
									panelModif.add(labelDate);
									panelModif.add(label1);
									panelModif.add(butInvalider);
								} else {
									panelModif.removeAll();
									panelModif.revalidate();
									panelModif.add(butModif);
									panelModif.add(butValiderCr);
									panelModif.add(label);
									panelModif.add(labelDate);
									panelModif.add(label1);
								}
								panelModif.revalidate();
								panelModif.repaint();
							}
							break;
						}

					}

				}

			}

		}

		class TreeCRListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == butValiderCr) {
					panelModif.removeAll();
					panelModif.revalidate();
					panelModif.add(label);
					panelModif.add(labelDate);
					panelModif.add(label1);
					panelModif.add(butInvalider);
					updateValidation(1);
					label1.setText("Compte rendu VALIDE");
					panelModif.revalidate();
					panelModif.repaint();
				} else {
					panelModif.removeAll();
					panelModif.revalidate();
					panelModif.add(label);
					panelModif.add(labelDate);
					panelModif.add(label1);
					panelModif.add(butValiderCr);
					updateValidation(0);
					label1.setText("Compte rendu NON VALIDE");
					panelModif.revalidate();
					panelModif.repaint();
				}

			}

		}

		public void updateValidation(int i) {

			Connection con = ConnexionBDD.connectDataBase();

			Statement state;

			int nbre = 0;

			try {
				state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

				String query = "UPDATE compterendu_cr SET cr_isvalidate = " + i + " WHERE cr_id = " + idCr;

				state.executeQuery(query);
				nbre = 1;

				state.close();
			} catch (SQLException e1) {
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Impossible d'accéder à cette requête.", "Infos",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}

			if (nbre == 1) {
				JOptionPane jop = null;
				jop.showMessageDialog(null, "Opération effectuée. ", "Infos", JOptionPane.INFORMATION_MESSAGE);

			}

		}

		private String getAbsolutePath(TreePath treePath) {
			String str = "";
			// On balaie le contenu de l'objet TreePath
			for (Object name : treePath.getPath()) {
				// Si l'objet a un nom, on l'ajoute au chemin
				if (name.toString() != null)
					str += name.toString() + " / ";
			}
			return str;
		}

	}

	@Override
	public void addObserver(Observer obs) {
		// TODO Auto-generated method stub
		listeObs.add(obs);

	}

	@Override
	public void updateObserver(String chaine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObserver(MiniWindow panel) {
		// TODO Auto-generated method stub
		for (Observer obs : listeObs) {
			obs.update(panel);

		}

	}

	@Override
	public void updateObserverResearch(ArrayList<String> sb) {
		// TODO Auto-generated method stub

	}

}
