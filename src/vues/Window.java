package vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import beans.User;
import controleurs.Control;
import controleurs.menuCompteRenduListener;
import controleurs.menuListener;
import patternObs.Observer;

/**
 * 
 * GUI of the application
 * 
 * @author Mangano and Talom
 *
 */

public class Window extends JFrame implements Observer {

	private Control control;

	private JPanel contener;
	private JPanel panelRight;
	private JPanel panelLeft;
	private JPanel panelCenter;
	private JPanel panelTop;
	private JPanel panelBottom;
	private JPanel pannelSession;
	private BouttonSession butSessionRespo;
	private BouttonSession butSessionOp;
	private BouttonSession butSessionCl;
	private JPanel pannelLogo;
	private JButton butLogo;
	private BouttonSession butSessionGar;
	private BouttonSession butSessionGcl;
	private BouttonSession butSessionGop;
	private BouttonSession butSessionGmt;
	private JPanel panelUp;
	private JDesktopPane panelDown;
	private BouttonSession butMonProfil;

	private menuListener ml;

	private JButton butCon;

	private JPasswordField jtfPassword;

	private JTextField jtfID;

	private TitledBorder tb;

	private JMenuItem jmiDeconnect;

	private JMenu jmFichier;

	private JMenuItem jmiQuitter;

	private JButton butUserName;

	private menuCompteRenduListener menuCompteR;

	public Window(Control control, menuListener ml, menuCompteRenduListener menuCompteR) {

		this.setTitle("GMAO Enterprise");
		this.setSize(1600, 1000);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.control = control;
		this.ml = ml;
		this.menuCompteR = menuCompteR;

		this.control.getModel().addObserver(this);
		this.control.addObserver(this);
		this.ml.addObserver(this);
		this.menuCompteR.addObserver(this);

		initComponent();

		this.setVisible(true);

	}

	/**
	 * Initilisation des composantes de la fenêtre
	 */
	private void initComponent() {

		contener = new JPanel();
		contener.setLayout(new BorderLayout());
		contener.setBackground(Color.ORANGE);
		this.setContentPane(contener);

		JMenuBar jmb = new JMenuBar();
		jmFichier = new JMenu("Fichier");
		jmiDeconnect = new JMenuItem("Déconnexion");
		jmiQuitter = new JMenuItem("Quitter");
		JMenuItem jmiAide = new JMenuItem("Aide");
		jmFichier.add(jmiQuitter);
		jmb.add(jmFichier);
		jmb.add(jmiAide);

		jmiDeconnect.addActionListener(new SessionListener());
		jmiQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		jmiAide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tb.setTitle("Poste de Travail - GMAO");
				String info = "GMAO : Gestionnaire de Maintenances Assisté par Ordinateur\n\n"
						+ "\tL'application GMAO est un logiciel permettant de gérer\n et "
						+ "d'optimiser la maintenance d’un ou plusieurs clients \n"
						+ "dont le suivi est assuré par des tickets de maintenance. ";
				JOptionPane jop = null;
				jop.showMessageDialog(null, info, " Le GMAO ??", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		panelRight = new JPanel();
		panelLeft = new JPanel();
		panelCenter = new JPanel();
		panelTop = new JPanel();
		panelBottom = new JPanel();

		panelRight.setBackground(Color.WHITE);
		panelLeft.setBackground(Color.WHITE);
		panelCenter.setBackground(Color.WHITE);
		panelTop.setBackground(Color.WHITE);
		panelBottom.setBackground(Color.WHITE);

		panelLeft.setBorder(BorderFactory.createTitledBorder("GMAO - MENU"));
		tb = new TitledBorder("Poste de Travail - GMAO");
		panelCenter.setBorder(BorderFactory.createTitledBorder(tb));
		panelRight.setBorder(BorderFactory.createTitledBorder("Profil"));
		panelBottom.setBorder(BorderFactory.createTitledBorder("GMAO - Infos"));

		panelRight.setPreferredSize(new Dimension(200, 800));
		panelLeft.setPreferredSize(new Dimension(350, 800));
		panelBottom.setPreferredSize(new Dimension(1300, 30));
		panelTop.setPreferredSize(new Dimension(1300, 25));

		panelTop.add(jmb);

		addPanelSession();

		contener.add(panelTop, BorderLayout.NORTH);
		contener.add(panelLeft, BorderLayout.WEST);
		contener.add(panelRight, BorderLayout.EAST);
		contener.add(panelCenter, BorderLayout.CENTER);
		contener.add(panelBottom, BorderLayout.SOUTH);

	}

	/**
	 * Listener Principal de la fenetre
	 * 
	 * @author nyjn
	 *
	 */
	class SessionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == butLogo || e.getSource() == jmiDeconnect) {

				panelCenter.removeAll();
				panelCenter.revalidate();
				panelCenter.repaint();
				panelLeft.removeAll();
				panelLeft.revalidate();
				panelLeft.repaint();
				panelRight.removeAll();
				panelRight.revalidate();
				panelRight.repaint();

				jmFichier.removeAll();
				jmFichier.add(jmiQuitter);
				jmFichier.revalidate();

				addPanelSession();
			}

			if (e.getSource() != butCon && e.getSource() != butLogo && e.getSource() != jmiDeconnect
					&& e.getSource() != butUserName && e.getSource() != butMonProfil) {
				E.e("bbbbbbbbb++++");

				if (control.respoExisted()) {
					E.e("bbbbbaaa");
					control.affichePanelConnexion(((JButton) (e.getSource())).getText());

				} else {
					RegisterBox rb = new RegisterBox(control, "Thread", 1, null, "Responsable GMAO", true, false);
					rb.setClic(false);
					rb.start();

					if (rb.isValider()) {

						pannelSession.add(butSessionOp);
						pannelSession.add(butSessionCl);
						pannelSession.revalidate();

						JOptionPane jop = null;
						jop.showMessageDialog(null,
								rb.getResponsable().getPrenom() + " est désormais un responsable.\n"
										+ "\nVeuillez vous connecter à présent.",
								" Opération réussie", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} else {

				if (e.getSource() == butUserName || e.getSource() == butMonProfil) {
					E.e("bbbbbbbbb");
					control.displayThisUser();
				}

				if (e.getSource() == butCon) {

					if (control.testerConnexion(jtfID.getText(), String.valueOf(jtfPassword.getPassword()))) {

						jmFichier.removeAll();
						jmFichier.add(jmiDeconnect);
						jmFichier.add(jmiQuitter);
						jmFichier.revalidate();

						if (control.getRole().equals("responsable"))
							addMenuResponsable();
						if (control.getRole().equals("operateur"))
							addMenuOperateur();
						if (control.getRole().equals("client"))
							addMenuClient();

						panelUp = new JPanel();
						panelDown = new JDesktopPane();
						panelDown.setPreferredSize(new Dimension(1000, 800));

						panelCenter.add(panelUp, BorderLayout.NORTH);
						panelCenter.add(panelDown, BorderLayout.SOUTH);
						panelCenter.revalidate();
						panelCenter.repaint();

					}
				}

			}

		}

		/**
		 * Ajout du menu du client
		 */
		private void addMenuClient() {
			panelCenter.removeAll();
			panelCenter.revalidate();
			panelCenter.repaint();

			panelLeft.remove(pannelSession);
			panelLeft.revalidate();
			panelLeft.repaint();

			//
			FlowLayout fl1 = new FlowLayout();
			fl1.setVgap(20);
			panelLeft.setLayout(fl1);
			//

			pannelSession = new JPanel();
			FlowLayout fl = new FlowLayout();
			fl.setVgap(50);
			pannelSession.setLayout(fl);
			pannelSession.setPreferredSize(new Dimension(280, 580));
			JLabel labelSession = new JLabel("Session Client");
			labelSession.setFont(new Font("Times New Roman", Font.BOLD, 20));
			butSessionGmt = new BouttonSession("Gestion de Tickets");
			butSessionGcl = new BouttonSession("Gestion Clients");
			butSessionGmt.setPreferredSize(new Dimension(250, 55));
			butSessionGcl.setPreferredSize(new Dimension(250, 55));
			butSessionGcl.setBackground(Color.LIGHT_GRAY);
			butSessionGmt.setBackground(Color.LIGHT_GRAY);

			butSessionGmt.addActionListener(new compteListener());
			butSessionGcl.addActionListener(new compteListener());

			pannelSession.add(labelSession);
			pannelSession.add(butSessionGmt);
			pannelSession.add(butSessionGcl);
			panelLeft.add(pannelSession);
			panelLeft.revalidate();
			panelLeft.repaint();

			/** Panel de profil utilisateur */

			panelRight.removeAll();
			panelRight.revalidate();
			panelRight.repaint();

			JButton butProfil = new BouttonUser((User) (control.getCl()));
			butProfil.setBackground(Color.WHITE);
			butProfil.setPreferredSize(new Dimension(250, 140));
			butProfil.setBackground(Color.WHITE);
			JLabel labelUser = new JLabel("Bonjour ");
			// labelUser.setHorizontalAlignment(JLabel.CENTER);
			JLabel labelUserName = new JLabel(control.getCl().getNomEntreprise().toUpperCase());
			labelUserName.setHorizontalAlignment(JLabel.CENTER);
			labelUserName.setFont(new Font("Times New Roman", Font.BOLD, 20));
			labelUser.setFont(new Font("Times New Roman", Font.BOLD, 20));

			panelRight.add(butProfil);
			panelRight.add(labelUser);
			panelRight.add(labelUserName);
			panelRight.revalidate();
			panelRight.repaint();

		}

		/**
		 * Ajout du menu de l'opérateur
		 */
		private void addMenuOperateur() {

			panelCenter.removeAll();
			panelCenter.revalidate();
			panelCenter.repaint();

			panelLeft.remove(pannelSession);
			panelLeft.revalidate();
			panelLeft.repaint();

			//
			FlowLayout fl1 = new FlowLayout();
			fl1.setVgap(20);
			panelLeft.setLayout(fl1);
			//

			pannelSession = new JPanel();
			FlowLayout fl = new FlowLayout();
			fl.setVgap(50);
			pannelSession.setLayout(fl);
			pannelSession.setPreferredSize(new Dimension(280, 580));
			JLabel labelSession = new JLabel("Session Opérateur");
			labelSession.setFont(new Font("Times New Roman", Font.BOLD, 20));

			butSessionGmt = new BouttonSession("Gestion de Tickets");
			butSessionGop = new BouttonSession("Gestion Opérateurs");
			butSessionGar = new BouttonSession("Autres Responsables");
			butSessionGmt.setPreferredSize(new Dimension(250, 55));
			butSessionGop.setPreferredSize(new Dimension(250, 55));
			butSessionGar.setPreferredSize(new Dimension(250, 55));
			butSessionGop.setBackground(Color.LIGHT_GRAY);
			butSessionGar.setBackground(Color.LIGHT_GRAY);
			butSessionGmt.setBackground(Color.LIGHT_GRAY);

			butSessionGmt.addActionListener(new compteListener());
			butSessionGop.addActionListener(new compteListener());
			butSessionGar.addActionListener(new compteListener());

			pannelSession.add(labelSession);
			pannelSession.add(butSessionGmt);
			pannelSession.add(butSessionGop);
			pannelSession.add(butSessionGar);
			panelLeft.add(pannelSession);
			panelLeft.revalidate();
			panelLeft.repaint();

			/** Panel de profil utilisateur */

			panelRight.removeAll();
			panelRight.revalidate();
			panelRight.repaint();

			JButton butProfil = new BouttonUser((User) (control.getOp()));
			butProfil.setBackground(Color.WHITE);
			butProfil.setPreferredSize(new Dimension(250, 140));
			butProfil.setBackground(Color.WHITE);
			JLabel labelUser = new JLabel(" Connecté ");
			labelUser.setFont(new Font("Times New Roman", Font.BOLD, 20));
			String userName = control.getOp().getPrenom().toUpperCase() + " "
					+ ((control.getOp().getNom().isEmpty()) ? "" : (control.getOp().getNom().toUpperCase()));

			butUserName = new JButton(userName);
			butUserName.setFont(new Font("Times New Roman", Font.BOLD, 20));
			butUserName.setBackground(Color.WHITE);
			butUserName.addActionListener(new SessionListener());

			panelRight.add(butProfil);
			panelRight.add(butUserName);
			panelRight.add(labelUser);
			panelRight.revalidate();
			panelRight.repaint();

		}

		/**
		 * Listener écoutant le menu après la connexion d'un profil
		 * 
		 * @author nyjn
		 *
		 */
		class compteListener implements ActionListener {

			private BouttonSession butModifierM;
			private BouttonSession butAjouterM;
			private BouttonSession butListerM;
			private BouttonSession butListerOp;
			private BouttonSession butAjouterOp;
			private BouttonSession butModifierOp;
			private BouttonSession butListerAr;
			private BouttonSession butAjouterAr;
			private BouttonSession butSupprimerAr;
			private BouttonSession butListerCl;
			private BouttonSession butAjouterCl;
			private BouttonSession butSupprimerCl;
			private BouttonSession butModifierCl;
			private BouttonSession butListeCr;
			private BouttonSession butAjouterCr;
			private BouttonSession butSuppOp;

			@Override
			public void actionPerformed(ActionEvent e) {

				ml.setControl(control);

				if (e.getSource() == butSessionGmt) {

					tb.setTitle("Gestion de tickets de maintenance");
					panelUp.removeAll();
					panelUp.revalidate();
					panelUp.repaint();
					butListerM = new BouttonSession("Liste de Tickets");
					butAjouterM = new BouttonSession("Ajouter Ticket");
					butModifierM = new BouttonSession("Supprimer Ticket");
					butListeCr = new BouttonSession("Liste des Comptes Rendus");
					butAjouterCr = new BouttonSession("Ajouter compte rendu");
					butListerM.setPreferredSize(new Dimension(250, 30));
					butAjouterM.setPreferredSize(new Dimension(250, 30));
					butModifierM.setPreferredSize(new Dimension(250, 30));
					butListeCr.setPreferredSize(new Dimension(250, 30));
					butAjouterCr.setPreferredSize(new Dimension(250, 30));
					butAjouterCr.setPreferredSize(new Dimension(250, 30));
					butListerM.setBackground(Color.LIGHT_GRAY);
					butAjouterM.setBackground(Color.LIGHT_GRAY);
					butModifierM.setBackground(Color.LIGHT_GRAY);
					butListeCr.setBackground(Color.LIGHT_GRAY);
					butAjouterCr.setBackground(Color.LIGHT_GRAY);
					butAjouterCr.setBackground(Color.LIGHT_GRAY);

					butListerM.addActionListener(ml);
					butAjouterM.addActionListener(ml);
					butModifierM.addActionListener(ml);
					butListeCr.addActionListener(menuCompteR);
					butAjouterCr.addActionListener(menuCompteR);

					if (control.getRole().equals("responsable")) {
						panelUp.add(butListerM);
						panelUp.add(butModifierM);
						panelUp.add(butListeCr);

					}
					if (control.getRole().equals("operateur")) {
						panelUp.add(butListerM);
						panelUp.add(butListeCr);
						panelUp.add(butAjouterCr);

					}
					if (control.getRole().equals("client")) {
						panelUp.add(butAjouterM);
						panelUp.add(butListerM);
						panelUp.add(butModifierM);
						panelUp.add(butListeCr);

					}

					panelUp.revalidate();
					panelUp.repaint();

				}

				if (e.getSource() == butSessionGop) {

					tb.setTitle("Gestion d'opérateurs");
					panelUp.removeAll();
					panelUp.revalidate();
					panelUp.repaint();
					butListerOp = new BouttonSession("Liste Opérateur");
					butAjouterOp = new BouttonSession("Ajouter Opérateur");
					butModifierOp = new BouttonSession("Modifier Opérateur");
					butSuppOp = new BouttonSession("Supprimer Opérateur");
					butSuppOp.setPreferredSize(new Dimension(250, 30));
					butListerOp.setPreferredSize(new Dimension(250, 30));
					butAjouterOp.setPreferredSize(new Dimension(250, 30));
					butModifierOp.setPreferredSize(new Dimension(250, 30));
					butSuppOp.setBackground(Color.LIGHT_GRAY);
					butListerOp.setBackground(Color.LIGHT_GRAY);
					butAjouterOp.setBackground(Color.LIGHT_GRAY);
					butModifierOp.setBackground(Color.LIGHT_GRAY);

					butSuppOp.addActionListener(ml);
					butListerOp.addActionListener(ml);
					butAjouterOp.addActionListener(ml);
					butModifierOp.addActionListener(ml);

					if (control.getRole().equals("responsable")) {
						panelUp.add(butListerOp);
						panelUp.add(butAjouterOp);
						panelUp.add(butSuppOp);

					}
					if (control.getRole().equals("operateur")) {
						panelUp.add(butListerOp);
						panelUp.add(butModifierOp);
					}
					panelUp.revalidate();
					panelUp.repaint();
				}

				if (e.getSource() == butSessionGcl) {

					tb.setTitle("Gestion de clients");
					panelUp.removeAll();
					panelUp.revalidate();
					panelUp.repaint();
					butListerCl = new BouttonSession("Liste Clients");
					butAjouterCl = new BouttonSession("Ajouter Client");
					butSupprimerCl = new BouttonSession("Supprimer Client");
					butModifierCl = new BouttonSession("Modifier Client");
					butListerCl.setPreferredSize(new Dimension(250, 30));
					butAjouterCl.setPreferredSize(new Dimension(250, 30));
					butModifierCl.setPreferredSize(new Dimension(250, 30));
					butSupprimerCl.setPreferredSize(new Dimension(250, 30));
					butListerCl.setBackground(Color.LIGHT_GRAY);
					butAjouterCl.setBackground(Color.LIGHT_GRAY);
					butSupprimerCl.setBackground(Color.LIGHT_GRAY);
					butModifierCl.setBackground(Color.LIGHT_GRAY);
					butListerCl.addActionListener(ml);
					butAjouterCl.addActionListener(ml);
					butSupprimerCl.addActionListener(ml);
					butModifierCl.addActionListener(ml);

					if (control.getRole().equals("responsable")) {
						panelUp.add(butListerCl);
						panelUp.add(butAjouterCl);
						panelUp.add(butSupprimerCl);

					}
					if (control.getRole().equals("operateur")) {
						panelUp.add(butListerCl);

					}
					if (control.getRole().equals("client")) {
						panelUp.add(butModifierCl);

					}

					panelUp.revalidate();
					panelUp.repaint();
				}

				if (e.getSource() == butSessionGar) {

					tb.setTitle("Gestion de responsables GMAO");
					panelUp.removeAll();
					panelUp.revalidate();
					panelUp.repaint();
					butListerAr = new BouttonSession("Liste Responsable");
					butAjouterAr = new BouttonSession("Ajouter Responsable");
					butSupprimerAr = new BouttonSession("Supprimer Responsable");
					butMonProfil = new BouttonSession("Mon Profil");
					butListerAr.setPreferredSize(new Dimension(250, 30));
					butAjouterAr.setPreferredSize(new Dimension(250, 30));
					butSupprimerAr.setPreferredSize(new Dimension(250, 30));
					butMonProfil.setPreferredSize(new Dimension(250, 30));
					butListerAr.setBackground(Color.LIGHT_GRAY);
					butAjouterAr.setBackground(Color.LIGHT_GRAY);
					butSupprimerAr.setBackground(Color.LIGHT_GRAY);
					butMonProfil.setBackground(Color.LIGHT_GRAY);

					butListerAr.addActionListener(ml);
					butAjouterAr.addActionListener(ml);
					butSupprimerAr.addActionListener(ml);
					butMonProfil.addActionListener(new SessionListener());

					if (control.getRole().equals("responsable")) {
						panelUp.add(butListerAr);
						panelUp.add(butAjouterAr);
						panelUp.add(butSupprimerAr);
						panelUp.add(butMonProfil);

					}
					if (control.getRole().equals("operateur")) {
						panelUp.add(butListerAr);

					}

					panelUp.revalidate();
					panelUp.repaint();

				}

			}

		}

		/**
		 * Ajout du menu responsable
		 */
		private void addMenuResponsable() {

			panelCenter.removeAll();
			panelCenter.revalidate();
			panelCenter.repaint();

			panelLeft.remove(pannelSession);
			panelLeft.revalidate();
			panelLeft.repaint();

			//
			FlowLayout fl1 = new FlowLayout();
			fl1.setVgap(20);
			panelLeft.setLayout(fl1);
			//

			pannelSession = new JPanel();
			FlowLayout fl = new FlowLayout();
			fl.setVgap(50);
			pannelSession.setLayout(fl);
			pannelSession.setPreferredSize(new Dimension(280, 580));
			JLabel labelSession = new JLabel("Session Responsable");
			labelSession.setFont(new Font("Times New Roman", Font.BOLD, 20));
			butSessionGmt = new BouttonSession("Gestion de Tickets");
			butSessionGop = new BouttonSession("Gestion Opérateurs");
			butSessionGcl = new BouttonSession("Gestion Clients");
			butSessionGar = new BouttonSession("Autres Responsables");
			butSessionGmt.setPreferredSize(new Dimension(250, 55));
			butSessionGop.setPreferredSize(new Dimension(250, 55));
			butSessionGcl.setPreferredSize(new Dimension(250, 55));
			butSessionGar.setPreferredSize(new Dimension(250, 55));
			butSessionGop.setBackground(Color.LIGHT_GRAY);
			butSessionGcl.setBackground(Color.LIGHT_GRAY);
			butSessionGar.setBackground(Color.LIGHT_GRAY);
			butSessionGmt.setBackground(Color.LIGHT_GRAY);

			butSessionGmt.addActionListener(new compteListener());
			butSessionGop.addActionListener(new compteListener());
			butSessionGcl.addActionListener(new compteListener());
			butSessionGar.addActionListener(new compteListener());

			pannelSession.add(labelSession);
			pannelSession.add(butSessionGmt);
			pannelSession.add(butSessionGop);
			pannelSession.add(butSessionGcl);
			pannelSession.add(butSessionGar);
			panelLeft.add(pannelSession);
			panelLeft.revalidate();
			panelLeft.repaint();

			/** Panel de profil utilisateur */

			panelRight.removeAll();
			panelRight.revalidate();
			panelRight.repaint();

			JButton butProfil = new BouttonUser((User) (control.getRespo()));
			butProfil.setBackground(Color.WHITE);
			butProfil.setPreferredSize(new Dimension(250, 140));
			butProfil.setBackground(Color.WHITE);
			JLabel labelUser = new JLabel(" Connecté ");
			labelUser.setFont(new Font("Times New Roman", Font.BOLD, 20));
			String userName = control.getRespo().getPrenom().toUpperCase() + " "
					+ ((control.getRespo().getNom().isEmpty()) ? "" : (control.getRespo().getNom().toUpperCase()));

			butUserName = new JButton(userName);
			butUserName.setFont(new Font("Times New Roman", Font.BOLD, 20));
			butUserName.setBackground(Color.WHITE);
			butUserName.addActionListener(new SessionListener());

			panelRight.add(butProfil);
			panelRight.add(butUserName);
			panelRight.add(labelUser);
			panelRight.revalidate();
			panelRight.repaint();

		}

	}

	private void addPanelSession() {

		FlowLayout fl1 = new FlowLayout();
		fl1.setVgap(30);
		panelLeft.setLayout(fl1);
		pannelLogo = new JPanel();
		pannelLogo.setBackground(Color.WHITE);
		butLogo = new JButton(new ImageIcon("src/vues/logo-maintenance-gmao.png"));
		butLogo.setPreferredSize(new Dimension(350, 100));
		butLogo.setBackground(Color.WHITE);
		panelLeft.add(butLogo);

		pannelSession = new JPanel();
		// pannelSession.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		FlowLayout fl = new FlowLayout();
		fl.setVgap(50);
		pannelSession.setLayout(fl);
		pannelSession.setPreferredSize(new Dimension(280, 500));
		JLabel labelSession = new JLabel("SESSIONS");
		labelSession.setFont(new Font("Times New Roman", Font.BOLD, 20));
		butSessionRespo = new BouttonSession("RESPONSABLE");
		butSessionOp = new BouttonSession("OPERATEUR");
		butSessionCl = new BouttonSession("CLIENT");
		butSessionRespo.setPreferredSize(new Dimension(200, 70));
		butSessionOp.setPreferredSize(new Dimension(200, 70));
		butSessionCl.setPreferredSize(new Dimension(200, 70));
		butSessionRespo.setBackground(Color.LIGHT_GRAY);
		butSessionOp.setBackground(Color.LIGHT_GRAY);
		butSessionCl.setBackground(Color.LIGHT_GRAY);

		butLogo.addActionListener(new SessionListener());
		butSessionRespo.addActionListener(new SessionListener());
		butSessionOp.addActionListener(new SessionListener());
		butSessionCl.addActionListener(new SessionListener());

		pannelSession.add(labelSession);
		pannelSession.add(butSessionRespo);
		if (control.respoExisted()) {
			pannelSession.add(butSessionOp);
			pannelSession.add(butSessionCl);
		}
		panelLeft.add(pannelSession);
		panelLeft.revalidate();
		panelLeft.repaint();

	}

	/**
	 * Méthide permettant de mettre à jour les composants sur la fenêtre
	 * 
	 * @see Observer
	 */
	@Override
	public void update(String chaine) {

		JPanel pannelConnexion = new JPanel();
		pannelConnexion.setBackground(Color.WHITE);
		FlowLayout fl = new FlowLayout();
		fl.setVgap(20);
		pannelConnexion.setLayout(new BorderLayout());
		pannelConnexion.setPreferredSize(new Dimension(400, 300));

		JLabel label = new JLabel("Connexion");
		label.setFont(new Font("Times New Roman", Font.BOLD, 50));
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(JLabel.CENTER);
		JLabel labelSession = new JLabel(chaine);
		labelSession.setFont(new Font("Times New Roman", Font.BOLD, 20));
		JLabel labelID = new JLabel("Identifiant : ");
		labelID.setFont(new Font("Times New Roman", Font.BOLD, 18));
		JLabel labelPassword = new JLabel("Mot de passe : ");
		labelPassword.setFont(new Font("Times New Roman", Font.BOLD, 18));

		jtfID = new JTextField();
		jtfPassword = new JPasswordField();
		jtfID.setPreferredSize(new Dimension(200, 30));
		jtfPassword.setPreferredSize(new Dimension(200, 30));
		jtfID.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jtfPassword.setFont(new Font("Times New Roman", Font.BOLD, 14));

		butCon = new JButton("Se connecter");
		butCon.setPreferredSize(new Dimension(250, 30));
		butCon.setBackground(Color.LIGHT_GRAY);
		butCon.setForeground(Color.BLACK);
		butCon.setFont(new Font("Times New Roman", Font.BOLD, 18));
		butCon.addActionListener(new SessionListener());
		JPanel pannel1 = new JPanel();
		pannel1.setBackground(Color.WHITE);
		pannel1.setPreferredSize(new Dimension(500, 90));
		pannel1.setLayout(fl);
		JPanel pannel2 = new JPanel();
		pannel2.setBackground(Color.WHITE);
		pannel2.setLayout(new GridLayout(4, 1));
		JPanel pannel3 = new JPanel();
		pannel3.setBackground(Color.WHITE);

		pannel1.add(label);
		pannel1.add(labelSession);
		pannel2.add(labelID);
		pannel2.add(jtfID);
		pannel2.add(labelPassword);
		pannel2.add(jtfPassword);
		pannel3.add(butCon);

		pannelConnexion.add(pannel1, BorderLayout.NORTH);
		pannelConnexion.add(pannel2, BorderLayout.CENTER);
		pannelConnexion.add(pannel3, BorderLayout.SOUTH);

		panelCenter.removeAll();
		panelCenter.revalidate();
		panelCenter.add(pannelConnexion);
		panelCenter.revalidate();

	}

	/**
	 * Méthode de misr à jour du panel Espace de travail
	 * 
	 * @see MiniWindow
	 * @see Obderver
	 */
	@Override
	public void update(MiniWindow panel) {

		// panelDown.removeAll();
		// panelDown.revalidate();
		panelDown.add(panel);
		panelDown.revalidate();
		panelDown.repaint();

	}

	@Override
	public void updateJSP(ArrayList<String> sb) {
		// TODO Auto-generated method stub

	}

}
