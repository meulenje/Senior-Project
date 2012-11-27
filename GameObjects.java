package rpg;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * GameObjects class
 * 
 * A serializable class that just holds the necessary
 * information to play a saved game. Below is a list of the objects:
 * - ArrayList of the board(GridObjects)
 * - ArrayList of Characters(Entities)
 * - ArrayList of Items(Item)
 * - ArrayList of Quests(QuestObjects)
 * @author Austin
 *
 */
public class GameObjects implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private GridObject[][] board; // the board/map
	private ArrayList<QuestObject> quests; // list of quest messages
	private ArrayList<Entity> characters; // list of characters
	private Item [] items; // array inventory items
	
	public GameObjects()
	{		
		setBoard(new GridObject[1][1]);
		setQuests(new ArrayList<QuestObject>());
		setCharacters(new ArrayList<Entity>());
		//setItems();
	}

	public GridObject[][] getBoard() {
		return board;
	}

	public void setBoard(GridObject[][] board) {
		this.board = board;
	}

	public ArrayList<QuestObject> getQuests() {
		return quests;
	}

	public void setQuests(ArrayList<QuestObject> quests) {
		this.quests = quests;
	}

	public ArrayList<Entity> getCharacters() {
		return characters;
	}

	public void setCharacters(ArrayList<Entity> characters) {
		this.characters = characters;
	}

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item [] items) {		
		this.items = items;
	}
}
