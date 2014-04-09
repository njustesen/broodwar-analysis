package ID3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import parser.bwhf.model.Replay;

public class ID3Classifier {
	
	ID3Tree tree;
	
	public ID3Classifier(List<Replay> trainingData){
		this.tree = new ID3Tree(trainingData);	
	}
	
	public ID3Classifier(ID3Tree tree){
		this.tree = tree;	
	}
	
	public void checkMushrooms(List <Replay> testData){
		double count = 0;
		double succes = 0;
		for(Replay r: testData){
			count++;
			boolean edible = isMushroomEdible(r);
			System.out.print("edible = "+edible);
			if(r.m_Class  == Class_Label.edible && edible){
				succes++;
				System.out.println(" correct classification - edible");
			}else if(r.m_Class  == Class_Label.poisonous && !edible){
				succes++;
				System.out.println(" correct classification - poisonous");
			}else{
				System.out.println(" error");
			}
		}
		System.out.println("tested "+count+" mushrooms  rateOfSucces = "+(succes/count)*100);
	}
	
	public boolean isMushroomEdible(Replay r){
		if(tree != null){
			ID3Node node = tree.getRoot();
			while(!node.isLeaf()){
				Object attributeObject = r.getAttributeValue(node.getAttribute());
				node = node.getChild(attributeObject);
			}
			return node.isEdible();
		}
		System.out.println("UNABLE TO OBTAIN IF MUSHROOM IS EDIBLE");
		return false;
	}
}

