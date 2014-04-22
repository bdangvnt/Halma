/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/

package halma;

/**
 * player class
 * 
 * */
public class Player {
	private String name;
	private String color;
	private int id = 0;
	private boolean isAIPlayer;
	
	public String toString() {
		return name;
	}
	
	public Player(int id) {
		this.name = "Player" + id;
		this.id = id;
	}
	
	public Player() {
		this.name = Integer.toString(id++);
	}
	
	public Player(String name) {
		this.name = name;
	}	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isAIPlayer() {
		return isAIPlayer;
	}

	public void setAIPlayer(boolean isAIPlayer) {
		this.isAIPlayer = isAIPlayer;
	}
}
