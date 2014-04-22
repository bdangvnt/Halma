/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/
package halma;


/**
 * components for basic game functionality
 * */
public abstract class BoardGame {
	HalmaMove move;
	HalmaBoard board;
	HalmaView view;
	
	public BoardGame() {
	}
	
	protected void run() {	
	}
}
