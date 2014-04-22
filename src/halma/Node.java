/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/

package halma;

import java.util.Collection;
import java.util.Vector;


/**
 * tree node, that represents one simulated
 *  move with its rating, used by ai
 * */
public class Node {
	public HalmaMove move = null;
	public Collection<Node> subNodes = new Vector<Node>();
	private Node parent;

	public Node(HalmaMove move, Collection<Node> nodes) {
		this.move = move;
		subNodes = nodes;
	}

	public Node() {
		this.move = null;
	}

	public Node(HalmaMove move) {
		this.move = move;
	}

	public HalmaMove getContent() {
		return move;
	}

	public Collection<Node> getSubNodes() {
		return subNodes;
	}

	public void setSubNodes(Collection<Node> subNodes) {
		this.subNodes = subNodes;
	}

	public void setContent(HalmaMove move) {
		this.move = move;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}