package ID3.niels;

public class DecisionTree {

	ID3Node root;

	public DecisionTree(ID3Node root) {
		super();
		this.root = root;
	}

	public void trim(double minSupport){
		
		root.trim(root, minSupport);
		
	}

	public void print() {
		root.print(0, null);
	}

}