package rpg;

import java.io.Serializable;

public class Ability implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private boolean locked;
	private int level;
	
	//0=damage, 1=heal
	private int type;
	
	//0=single target, 1=splash
	private int scope;
	private int modifier;
	
	private int cost;

	public Ability(String name, int type, int scope, int modifier, int cost){
		this.setName(name);
		this.setType(type);
		this.setScope(scope);
		this.setModifier(modifier);
		this.setCost(cost);
		this.lock();
		this.setLevel(1);
	}
	
	public String toString()
	{
		return name;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	int getType() {
		return type;
	}

	void setType(int type) {
		this.type = type;
	}

	int getScope() {
		return scope;
	}

	void setScope(int scope) {
		this.scope = scope;
	}

	int getModifier() {
		return modifier;
	}

	void setModifier(int modifier) {
		this.modifier = modifier;
	}
	
	boolean friendly(){
		boolean returnVal = false;
		if (this.type == 1){
			returnVal = true;
		}
		return returnVal;
	}

	int getCost() {
		return cost;
	}

	void setCost(int cost) {
		this.cost = cost;
	}

	protected String getDescription() {
		return description;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	protected boolean isLocked() {
		return locked;
	}

	protected void lock() {
		this.locked = true;
	}
	
	protected void unlock() {
		this.locked = false;
	}

	protected int getLevel() {
		return level;
	}

	protected void setLevel(int level) {
		this.level = level;
	}
}

