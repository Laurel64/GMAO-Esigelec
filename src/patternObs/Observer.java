package patternObs;

import java.util.ArrayList;

import vues.MiniWindow;

/**
 * The interface that match a Window class
 * 
 * @see Window
 * 
 * @author Mangano and Talom
 *
 */
public interface Observer {

	public void update(String chaine);

	public void update(MiniWindow panel);

	public void updateJSP(ArrayList<String> sb);

}
