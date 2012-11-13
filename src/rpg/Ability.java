package rpg;

public class Ability {
	
	private String name;
	
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
}

