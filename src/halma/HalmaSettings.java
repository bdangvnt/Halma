/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/

package halma;

import java.awt.Color;

/**
 * game settings
 * */
public class HalmaSettings {	
	public enum menu {
		defaultMenu
	}
	
	static int NUMBER_OF_PLAYERS = 2;
	public static Color BGCOLOR = new Color(195, 217, 255);
	public static int BUTTON_SIZE_WIDTH = 20;
	public static int BUTTON_SIZE_HEIGHT = 20;
	
	//messages
	final static int MESSAGE_WIN = 0;
	final static int MESSAGE_ENTERMOVE1 = 1;
	final static int MESSAGE_ENTERMOVE2 = 2;
	final static int MESSAGE_FIGURE_MOVE_WHERE = 3;
	final static int MESSAGE_CHOOSE_FIGURE = 4;	
	
	//ValidationErrors
	final static int ERROR_MOVE_INVALID = 30;
	final static int ERROR_NUMBER_OF_PLAYERS = 31;
	final static int ERROR_FIELD_NOT_FREE = 32;
	final static int ERROR_INVALID_JUMP= 33;
	final static int ERROR_INVALID_TOKEN= 34;
	final static int ERROR_INVALID_BOARDSPACE= 35;
	
	//default size of board
	final static int DEFAULT_WIDTH = 20;
	final static int DEFAULT_HEIGHT = 20;
		
	//field types
	final static int NO_FIELD = -1;
	final static int EMPTY_FIELD = 0;
	
	//validation helpers
	final static int DUMMY_POSITION = 100;
	final static int NEIGHBORHOOD_LENGTH = 5;
	
	//pre-defined board styles
	final static int BOARD_QUADRATICALLY = 0; 
	final static int BOARD_STARLIKE = 1;
	
	//game types
	final static int MODE_MAN_MAN = 0;
	final static int MODE_MAN_MACHINE = 1;
	final static int MODE_MACHINE_MACHINE = 2;
}
