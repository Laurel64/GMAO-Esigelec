package vues;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import beans.User;
import controleurs.ConnexionBDD;

public class BouttonUser extends JButton implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image img;
	private User user;
	private String path;
	private File file;
	private boolean photoProfil;

	public BouttonUser(User user) {
		super();
		this.user = user;
		photoProfil = false;

		ImageIcon imageIcon = new ImageIcon();

		try {
			// chargeIMG("");
			// img = ImageIO.read(new File("tn_inconnu_homme.jpg"));
			ImageIcon imgs = new ImageIcon("src/vues/tn_inconnu_homme.jpg");
			this.setIcon(imgs);
			this.revalidate();
			this.repaint();

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		Graphics2D gd = (Graphics2D) g;

		gd.drawImage(img, 50, 10, this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		JFileChooser dialogue = new JFileChooser(new File("."));
		dialogue.setDialogTitle("Importaion d'image");

		if (dialogue.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			try {
				img = ImageIO.read(dialogue.getSelectedFile());
				// this.repaint();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			file = dialogue.getSelectedFile();
			path = file.getPath();
			photoProfil = true;
			System.out.println("Path : " + path);
			try {
				saveIMG(path);
				// chargeIMG(path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void saveIMG(String location) throws Exception {

		Connection con = ConnexionBDD.connectDataBase();

		File monImage = new File(location);

		FileInputStream istreamImage = new FileInputStream(monImage);

		PreparedStatement ps = null;

		int id = 0;

		Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = state.executeQuery("SELECT * FROM images");

		while (rs.next())
			id++;

		try {
			if (id == 0) {
				ps = con.prepareStatement("insert into images (id_img, img) values (?, ?)");
				try {
					ps.setInt(1, 1);
					ps.setBinaryStream(2, istreamImage, (int) monImage.length());
					ps.executeUpdate();
				} finally {
					ps.close();
				}
			} else {
				ps = con.prepareStatement("UPDATE images SET img = ? where id_img = ?");
				ps.setBinaryStream(1, istreamImage, (int) monImage.length());
				ps.setInt(2, 1);
				ps.executeUpdate();
			}

		} finally {
			istreamImage.close();
		}
	}

	public void chargeIMG(String location) throws Exception {
		Connection con = ConnexionBDD.connectDataBase();

		File monImage = new File("user/file.txt");
		FileOutputStream ostreamImage = new FileOutputStream(monImage);

		try {
			PreparedStatement ps = con.prepareStatement("select img from images where id_img=?");

			try {
				ps.setInt(1, 1);
				ResultSet rs = ps.executeQuery();

				try {
					if (rs.next()) {
						E.e("ok");
						InputStream istreamImage = rs.getBinaryStream("img");

						byte[] buffer = new byte[1024];

						int length = 0;

						while ((length = istreamImage.read(buffer)) != -1) {
							ostreamImage.write(buffer, 0, length);
							E.e("length - " + length);
						}

						ImageIcon imgC = new ImageIcon(ostreamImage.getChannel().toString());

						this.setIcon(imgC);
						this.repaint();

					}
				} finally {
					rs.close();
				}
			} finally {
				ps.close();
			}
		} finally {
			ostreamImage.close();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

		try {
			img = ImageIO.read(new File("tn_inconnu_homme_edit.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		try {
			img = ImageIO.read(new File("tn_inconnu_homme.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the photoProfil
	 */
	public boolean isPhotoProfil() {
		return photoProfil;
	}

	/**
	 * @param photoProfil
	 *            the photoProfil to set
	 */
	public void setPhotoProfil(boolean photoProfil) {
		this.photoProfil = photoProfil;
	}

}
