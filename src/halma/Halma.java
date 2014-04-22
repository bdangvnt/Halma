/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/
package halma;

/**
 * Main class. This is where the magic starts.
 * */
public class Halma {
	public static void main(String[] args) {
		HalmaBoard board = new HalmaBoard(HalmaSettings.BOARD_STARLIKE);
		board.createInitialField();	
		HalmaView view = new HalmaView(board);
		board.addObserver(view);
		HalmaController controller = HalmaController.getInstance(board, view);
		controller.setupPlayers();
		view.displayStatus(controller.currentPlayer);
	}
}
