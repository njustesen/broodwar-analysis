package ID3.niels;

public class DecisionTree {

	ID3Node root;

	public DecisionTree(ID3Node root) {
		super();
		this.root = root;
	}

//	public boolean isEdible(Mushroom mushroom) {
//
//		return root.isEdible(mushroom);
//
//	}
	
	public void printClean() {
		root.printClean("", null);
	}

	public void print() {
		root.print(0, null);
	}

}