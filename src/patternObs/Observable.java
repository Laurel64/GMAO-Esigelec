package patternObs;

import java.util.ArrayList;

import controleurs.menuListener;
import models.Model;
import vues.MiniWindow;
import vues.RegisterBox;

/**
 * The interface that match to some classes like
 * 
 * @see Model
 * @see menuListener
 * @see Control
 * @see RegisterBox
 * @author Mangano and Talom
 *
 */
public interface Observable {

	public void addObserver(Observer obs);

	public void updateObserver(String chaine);

	public void updateObserver(MiniWindow panel);

	public void updateObserverResearch(ArrayList<String> sb);

}
