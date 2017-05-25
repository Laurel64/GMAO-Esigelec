package vues;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class BouttonSession extends JButton implements MouseListener {

	private String name;

	public BouttonSession(String name) {
		super(name);
		this.addMouseListener(this);

	}

	public BouttonSession() {
		super();
		this.addMouseListener(this);
		// TODO Auto-generated constructor stub
	}

	public BouttonSession(Action arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BouttonSession(Icon arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BouttonSession(String arg0, Icon arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setBackground(Color.BLUE);
		this.setForeground(Color.WHITE);
		this.revalidate();

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setBackground(Color.WHITE);
		this.setForeground(Color.black);

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.setBackground(Color.BLUE);
		this.setForeground(Color.WHITE);

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
