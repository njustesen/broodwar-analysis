package json;

import java.util.ArrayList;
import java.util.List;

import json.OtherTestObject.Type;

public class TestJson {

	public static void main(String[] args) {
		
		//List<TestObject> testObjects = new ArrayList<TestObject>();
		
		List<OtherTestObject> others1 = new ArrayList<OtherTestObject>();
		others1.add(new OtherTestObject("other1", true, Type.ONE));
		others1.add(new OtherTestObject("other2", false, Type.TWO));
		others1.add(new OtherTestObject("other3", true, Type.THREE));
		TestObject obj1 = new TestObject("obj1",others1);
		/*testObjects.add(new TestObject("obj1",others1));
		
		List<OtherTestObject> others2 = new ArrayList<OtherTestObject>();
		others2.add(new OtherTestObject("other11", true, Type.ONE));
		others2.add(new OtherTestObject("other22", false, Type.TWO));
		others2.add(new OtherTestObject("other33", true, Type.THREE));
		testObjects.add(new TestObject("obj2",others2));
		
		List<OtherTestObject> others3 = new ArrayList<OtherTestObject>();
		testObjects.add(new TestObject("obj3",others3));
		
		List<OtherTestObject> others4 = new ArrayList<OtherTestObject>();
		others4.add(new OtherTestObject("other1111", true, Type.ONE));
		others4.add(new OtherTestObject("other2222", false, Type.TWO));
		others4.add(new OtherTestObject("other3333", true, Type.THREE));
		testObjects.add(new TestObject("obj4",others4));
		*/
		new TestEncoder().encode(obj1);
		
		TestObject decodedTestObject = new TestDecoder().decode();
		
	}

}
