/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/
package halma;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

/**
 * Controller.
 * */
public class HalmaController implements ActionListener {
	static private HalmaController halmaController;

	Player player[] = {};
	Player currentPlayer = new Player();
	Player nextPlayer = new Player();

	private HalmaMove move;
	private HalmaBoard board;
	private HalmaView view;
	private HalmaAI ai;

	/**
	 * This method implements the singleton pattern.
	 * @return instance of HalmaController
	 */
	public static HalmaController getInstance(HalmaBoard board, HalmaView view) {
		if (halmaController == null)
			halmaController = new HalmaController(board, view);
		return halmaController;
	}

	/**
	 * This method implements the singleton pattern.
	 * @return instance of HalmaController
	 */
	public static HalmaController getInstance(HalmaBoard board, HalmaViewMenu menu) {
		if (halmaController == null)
			halmaController = new HalmaController(board, menu);
		return halmaController;
	}

	
	public HalmaController(HalmaBoard board, HalmaView view) {
		this.view = view;
		this.board = board;
		this.ai = new HalmaAI();
	}

	public HalmaController(HalmaBoard board, HalmaViewMenu menu) {
		this.board = board;
		this.ai = new HalmaAI();
	}

	
	/**
	 * This method starts a game of two ai players playing against themselves.
	 */
	public void startMachineVsMachine() {
		while (!board.isGameOver()) {
			currentPlayer = getNextPlayer(player);
			move = ai.getAIMove(board, player, currentPlayer.getId(), 3);
			board.move(currentPlayer, move);
			view.frame.update(view.frame.getGraphics()); //TODO: implement threading
		}
		view.rewardWinner(currentPlayer);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("close")) {
			view.displayClosingOption();
		} else if (e.getActionCommand().equals("about")){
			view.displayAbout();
		} else if (e.getActionCommand().equals("manVsMachine")) {
			view.displayGetPlayerName();
			setupPlayers(HalmaSettings.MODE_MAN_MACHINE);
		} else if (e.getActionCommand().equals("manVsMan")) {
			setupPlayers(HalmaSettings.MODE_MAN_MAN);
		} else if (e.getActionCommand().equals("machineVsMachine")) {
			setupPlayers(HalmaSettings.MODE_MACHINE_MACHINE);
			startMachineVsMachine();
		} else {
			// button has been pressed
			HalmaButton src = (HalmaButton) e.getSource();
			src.setIcon(new ImageIcon(getClass().getResource("yellow.png")));

			if (move instanceof HalmaMove) {
				// set to-coordinates
				move.setToXPosition(src.getField().getXPosition());
				move.setToYPosition(src.getField().getYPosition());

				// if valid execute move
				if (board.isMoveValid(move, currentPlayer)) {
					board.move(currentPlayer, move);

					if (board.isGameOver()) {
						view.rewardWinner(currentPlayer);
					}

					currentPlayer = getNextPlayer(player);
					// check if computer player, then calc move
					if (currentPlayer.isAIPlayer()) {
						move = ai.getAIMove(board, player, currentPlayer.getId(), 3);
						board.move(currentPlayer, move);
						currentPlayer = getNextPlayer(player);
					}
					view.setMoveStatus(0);
				} else {
					view.setMoveStatus(-1);
				}
				// reset
				move = null;
			} else {
				// set from-coordinates
				move = new HalmaMove();
				move.setFromXPosition(src.getField().getXPosition());
				move.setFromYPosition(src.getField().getYPosition());
				view.setMoveStatus(1);
			}
			view.displayStatus(currentPlayer);
		}
	}

	/** 
	 * Iterates over players and returns next one 
	 * @param Array of player
	 * */
	protected Player getNextPlayer(Player players[]) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] == currentPlayer) {
				if (i + 1 == players.length) {
					nextPlayer = players[0];
				} else {
					nextPlayer = players[i + 1];
				}
			}
		}
		return nextPlayer;
	}

	/** 
	 * Initializes players 
	 * @param kind of playing mode defined in HalmaSettings
	 * */
	protected void setupPlayers(int mode) {
		int playerCount = 2;
		player = new Player[playerCount];
		for (int i = 0; i < playerCount; i++) {
			player[i] = new Player(i);
			player[i].setName("Spieler " + i);
		}

		switch (mode) {
		case HalmaSettings.MODE_MAN_MAN:
			player[0].setAIPlayer(false);
			player[1].setAIPlayer(false);
			break;
		case HalmaSettings.MODE_MAN_MACHINE:
			player[0].setAIPlayer(true);
			player[1].setAIPlayer(false);
			break;
		case HalmaSettings.MODE_MACHINE_MACHINE:
			player[0].setAIPlayer(true);
			player[1].setAIPlayer(true);
			break;
		}
		currentPlayer = player[1];
	}

	protected void setupPlayers() {
		// default man vs machine
		int playerCount = 2;
		player = new Player[playerCount];
		for (int i = 0; i < playerCount; i++) {
			player[i] = new Player(i);
			player[i].setName("Spieler " + i);
		}

		player[0].setAIPlayer(true);
		currentPlayer = player[1];
	}

}
