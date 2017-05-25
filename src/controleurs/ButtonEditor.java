package controleurs;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import beans.Client;
import beans.Operateur;
import beans.TicketMaintenance;
import vues.BoxClient;
import vues.BoxOperateur;
import vues.BoxTicketM;
import vues.E;

public class ButtonEditor extends DefaultCellEditor {

	private JButton button;
	private BouttonListener bListener;
	private ArrayList<TicketMaintenance> listeTm;
	private ArrayList<Operateur> listeOp;
	private ArrayList<Client> listeCl;
	private Control control;

	public ButtonEditor(JCheckBox box) {

		// Par défaut, ce type d'objet travaille avec un JCheckBox
		super(box);
		// On crée à nouveau un bouton
		listeTm = new ArrayList<TicketMaintenance>();
		listeOp = new ArrayList<Operateur>();
		bListener = new BouttonListener();
		button = new JButton();
		// On lui attribue un listener
		button.addActionListener(bListener);

		E.e("cs");
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// On précise le numéro de ligne à notre listener
		bListener.setRow(row);
		// Idem pour le numéro de colonne
		bListener.setColumn(column);
		// On passe aussi le tableau en paramètre pour des actions potentielles
		bListener.setTable(table);
		// On réaffecte le libellé au bouton
		button.setText((value == null) ? "" : value.toString());
		table.setDefaultRenderer(JButton.class, new TableCellRenderer());
		E.e("csxxxxxxx");
		// On renvoie le bouton
		return button;
	}

	class BouttonListener implements ActionListener {

		private int column, row;
		private JTable table;
		private int nbre = 0;

		public void setColumn(int col) {
			this.column = col;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public void setTable(JTable table) {
			this.table = table;
		}

		public void actionPerformed(ActionEvent e) {
			// On affiche un message, mais vous pourriez effectuer les
			// traitements que vous voulez
			System.out.println("coucou du bouton : " + ((JButton) e.getSource()).getText());
			System.out.println("Ligne : " + this.row);
			System.out.println("Colonne : " + this.column);

			if (((JButton) (e.getSource())).getText().equals("(+ Plus)")) {

				displayOperateur(this.row);
			}

			if (((JButton) (e.getSource())).getText().equals("( + )")) {
				displayTicket(this.row);
			}

			if (((JButton) (e.getSource())).getText().equals("(+)")) {
				displayClient(this.row);
			}

			table.setDefaultRenderer(JButton.class, new TableCellRenderer());
		}
	}

	public void displayTicket(int row) {

		BoxTicketM boxTicketM = new BoxTicketM(control, listeTm.get(row), null,
				"Ticket : " + listeTm.get(row).getNomMaintenance(), true, row + 1);

		boxTicketM.start();

		if (boxTicketM.isValidate()) {
			JOptionPane jop = null;
			jop.showMessageDialog(null,
					"Ticket validé !\n\n L'opérateur concerné peut désormais accéder à cette maintenance.\n Le client émetteur peut également voir les comptes rendus y associés.",
					"Opération réussie", JOptionPane.INFORMATION_MESSAGE);
		}
		if (boxTicketM.isInvalidate()) {
			JOptionPane jop = null;
			jop.showMessageDialog(null,
					"Ticket invalider !\n\n L'opérateur concerné ne pourra plus accéder à cette maintenance.",
					"Opération réussie", JOptionPane.INFORMATION_MESSAGE);
		}

		if (boxTicketM.getClic()) {
			JOptionPane jop = null;
			jop.showMessageDialog(null, "Ticket modifié !\n\n .", "Opération réussie", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void displayClient(int row) {

		BoxClient rb = new BoxClient(control, listeCl.get(row), null, "Client : " + listeCl.get(row).getNomEntreprise(),
				true, row + 1);

		rb.start();

		if (rb.getClic()) {
			JOptionPane jop1 = new JOptionPane();
			jop1.showMessageDialog(null, "Modifications bien effectuées.", "Infos", JOptionPane.INFORMATION_MESSAGE);

		}

	}

	public void displayOperateur(int row) {

		BoxOperateur rb = new BoxOperateur(control, listeOp.get(row), null,
				"Opératur : " + listeOp.get(row).getPrenom(), true, row + 1);

		rb.setJtfNom(listeOp.get(row).getNom());
		rb.setJtfPrenom(listeOp.get(row).getPrenom());
		rb.setJtfPassword("******");
		rb.setJtfTel(String.valueOf(listeOp.get(row).getNumTel()));
		rb.setJtfAdresse(listeOp.get(row).getAdresse());
		rb.setJtfSpe(listeOp.get(row).getSpecialite());

		rb.start();

		if (rb.isValider()) {
			JOptionPane jop1 = new JOptionPane();
			jop1.showMessageDialog(null, "Modifications bien effectuées.", "Infos", JOptionPane.INFORMATION_MESSAGE);

		}

	}

	/**
	 * @return the listeCl
	 */
	public ArrayList<Client> getListeCl() {
		return listeCl;
	}

	/**
	 * @param listeCl
	 *            the listeCl to set
	 */
	public void setListeCl(ArrayList<Client> listeCl) {
		this.listeCl = listeCl;
	}

	/**
	 * @return the listeTm
	 */
	public ArrayList<TicketMaintenance> getListeTm() {
		return listeTm;
	}

	/**
	 * @param listeTm
	 *            the listeTm to set
	 */
	public void setListeTm(ArrayList<TicketMaintenance> listeTm) {
		this.listeTm = listeTm;
	}

	/**
	 * @return the listeOp
	 */
	public ArrayList<Operateur> getListeOp() {
		return listeOp;
	}

	/**
	 * @param listeOp
	 *            the listeOp to set
	 */
	public void setListeOp(ArrayList<Operateur> listeOp) {
		this.listeOp = listeOp;
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

}
