package json;

import java.util.List;

public class TestObject {

	String name;
	List<OtherTestObject> others;
	
	public TestObject(String name, List<OtherTestObject> others){
		this.name = name;
		this.others = others;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OtherTestObject> getOthers() {
		return others;
	}

	public void setOthers(List<OtherTestObject> others) {
		this.others = others;
	}
	
}
