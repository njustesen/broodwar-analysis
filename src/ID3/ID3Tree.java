package ID3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import parser.bwhf.model.Replay;

public class ID3Tree {

	private ID3Node root;
	
	public ID3Tree(List<Replay> examples){
		this.root = new ID3Node(null, 0);
		List <Object> attributes = Replay.getAttributeList();
		generateTree(examples, attributes, root);
	}

/*
	public boolean safeToEat(Mushroom m){
		
	}
*/	
	public static void generateTree(List<Replay> examples, List <Object> attributes, ID3Node thisNode){
		System.out.println("examples.size = " + examples.size());
		//Check if all shrooms are poisonous/edible
		//All samples for a given node belong to the same class
		boolean allEdible = true;
		boolean allPoisonous = true;
		for(Replay r: examples){
			if(r.m_Class  == Class_Label.edible){
				allPoisonous = false;
			}else{
				allEdible = false;
			}
		}
				
		
		//There are no remaining attributes for further partitoning
		if(attributes.isEmpty()){
			thisNode.setReasonForCutoff("'ATTRIBUTES EMPTY'");
			int edibleCount = 0;
			for(Replay r : examples){
				if(r.m_Class  == Class_Label.edible){
					edibleCount++;
				}else{
					edibleCount--;
				}
			}			
			if(edibleCount < 0 ){
				thisNode.nodeIsNotEdible();
			}else{
				thisNode.nodeIsEdible();
			}
		}
	
		//all mushrooms are edible
		if(allEdible){
			thisNode.setReasonForCutoff("ALL EDIBLE");
			//System.out.println("all edible");
			thisNode.nodeIsEdible();
			return;
		}
		
		//all mushrooms are poisonous
		if(allPoisonous){
			thisNode.setReasonForCutoff("ALL POISONOUS");
			//System.out.println("all poisonous");
			thisNode.nodeIsNotEdible();
			return;
		}
		
		//Select Attribute (random)
		//Random  r = new Random();
		//int index = r.nextInt(attributes.size());
		//Object selectedAttribute = attributes.get(index);
		Object selectedAttribute = null;
		double highestGain = Integer.MIN_VALUE;
		for(Object att: attributes){
			double gain = getInfoGain(att, examples);
			System.out.println("GAIN="+gain);
			if(gain > highestGain){
				System.out.println("new highest GAIN="+gain);
				selectedAttribute = att;
				highestGain = gain;
			}
		}
		
		thisNode.setAttribute((Class)selectedAttribute);
		
		for(Object obj : ((Class)selectedAttribute).getEnumConstants()){
			//make sublists
			List subExamples = getSubset(examples, selectedAttribute, obj);
			List subAttributes = getSubset(attributes, selectedAttribute);
			//create new node
			ID3Node child = new ID3Node(thisNode, thisNode.getLevel()+1);
			thisNode.addChild(obj, child);
			thisNode.notLeaf();
			//recursive call
			generateTree(subExamples, subAttributes, child);
		}
		
		return;
	}
	
	public void printTree(){
		System.out.println();
		System.out.println("- -- --- ---- ----- ------ FINAL TREE START ------ ----- ---- --- -- -");
		root.printNode();
		System.out.println("- -- --- ---- ----- ------ FINAL TREE END ------ ----- ---- --- -- -");
	}
	
	private static List<Object> getSubset(List<Object> attributes, Object attribute){
		
		System.out.println("attributes="+attributes);
		System.out.println("size="+attributes.size());
		System.out.println("attribute="+attribute);
		
		List<Object> subset = new ArrayList<Object>(attributes);
		
		subset.remove(attribute);
		
		return subset;
	}
	
	public static double getInfoGain(Object att, List<Replay> examples){
		double info = getInfo(examples);
		double infoA = getInfoA(att, examples);
		double infoGain = info - infoA;
		System.out.println("info="+info+" infoA="+infoA+" infoGain="+infoGain);
		return infoGain;
	}
	
	public static double getInfo(List<Replay> examples){
		System.out.println("print TEST "+testInfo());
		if(examples.isEmpty()){
			return 1;
		}
		double info = 0;
	//	System.out.println("examples="+examples);
		double D = examples.size();
		double DjEdible = 0;
		double DjPoison = 0;
			
		for(Replay r: examples){
			if(r.m_Class  == Class_Label.edible){
				DjEdible++;
			}else{
				DjPoison++;
			}	
		}
		
		//(Math.log(pi)/Math.log(2)); ??
		double infoEdible = ((DjEdible/D) * (Math.log(DjEdible/D)/Math.log(2)));
		double infoPoison = ((DjPoison/D) * (Math.log(DjPoison/D)/Math.log(2)));
		
		if (DjEdible == 0)
			infoEdible = 0;
		if (DjPoison == 0)
			infoPoison = 0;

		info = -infoEdible-infoPoison;

		return info;
/*		
		double pi = (Dj/D);
		double logpi = Math.log(pi);
		double logpilog2 = (Math.log(pi)/Math.log(2));
		info -= pi*(Math.log(pi)/Math.log(2));
		//System.out.println("getInfo  Dj="+Dj+"  D="+D+"  pi="+pi+"  logpi="+logpi+" logpilog2="+logpilog2+"  info="+info);
		
		return info;
*/
	}
	
	public static double getInfoA(Object att, List<Replay> examples){
		
		if(examples.isEmpty()){
			return 1;
		}
		
		double infoA = 0;
		List<Replay> subExamples = new ArrayList<Replay>();
		System.out.println();
		System.out.println("- -- --- ---- ----- GET INFO A ----- ---- --- -- -");
		for(Object obj : ((Class)att).getEnumConstants()){
			
			double D = examples.size();
			double Dj = 0;
			for(Replay r: examples){
	//			System.out.println("obj="+obj.toString()+"  m.getAttributeValue(att)="+m.getAttributeValue(att));
				if(r.getAttributeValue(att) == obj){
					Dj++;
					subExamples.add(r);
				}
			}
	//		System.out.println("SUB");
			System.out.println();
			double division = (Dj/D);
			double subEx = getInfo(subExamples);
			double result = division * subEx;
			infoA = infoA + result;
			System.out.println("getInfoA  Dj="+Dj+"  D="+D+"  division="+division+"  subEx="+subEx+"  result="+result+"  infoA="+infoA);
		}
		System.out.println("getInfoA  infoA="+infoA);
		return infoA;
	}

	private static List<Replay> getSubset(List<Replay> mushrooms, Object attribute, Object value) {

		ArrayList<Replay> subset = new ArrayList<Replay>();

		for(Replay r : mushrooms){
			if (r.getAttributeValue(attribute).equals(value))
				subset.add(r);
		}

		return subset;
	}
	
	public ID3Node getRoot(){
		return root;
	}
	
	public static double testInfo(){
		double info = 0;
		double Dj = 7;
		double D = 17;
		//return info -= pi*(Math.log(pi)/Math.log(2));
		double tal = (Dj/D);
		return tal;
	}

}
