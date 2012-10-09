
public class Ability {
	
	private String name;
	private int type;
	private int scope;
	private int modifier;

	public Ability(String name, int type, int scope, int modifier){
		this.setName(name);
		this.setType(type);
		this.setScope(scope);
		this.setModifier(modifier);
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
}
