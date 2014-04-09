package json;

public class OtherTestObject {

	public enum Type {
		ONE, TWO, THREE
	}
	
	String name;
	boolean b;
	Type type;
	
	public OtherTestObject(String name, boolean b, Type type) {
		super();
		this.name = name;
		this.b = b;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isB() {
		return b;
	}

	public void setB(boolean b) {
		this.b = b;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
