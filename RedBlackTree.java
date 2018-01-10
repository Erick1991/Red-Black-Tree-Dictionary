package sjsu.Rodriguez.cs146.project3;

public class RedBlackTree {	
	static RedBlackTree.Node root; 
	static RedBlackTree.Node trailer;	
	static RedBlackTree.Node empty; 
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------		  

	public static class Node { //changed to static 
		
		  String key;  		  
		  Node parent;
		  Node leftChild;
		  Node rightChild;
		  boolean isRed;
		  int color;
		  		  
		  public Node(String data){
			  this.key = data;
			  leftChild = null;
			  rightChild = null;
			  color = 0;     //0 = red and 1 = black?
		  }		  
		  
		  public int compareTo(Node n){ 	//this < that  <0
		 		return key.compareTo(n.key);  	//this > that  >0
		  }
		  
		  public boolean isLeaf(){
			  if (this.equals(root) && this.leftChild == null && this.rightChild == null) return true;
			  if (this.equals(root)) return false;
			  if (this.leftChild == null && this.rightChild == null) {
				  return true;
			  }
			  return false;
		  }
		  
		  public boolean isRed() {
			  if (color == 1) {
				  isRed = false; 
			  }
			  else {
				  isRed = true;
			  }
			  return isRed;
		  }
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------		
	
	 public boolean isLeaf(RedBlackTree.Node n){
		  if (n.leftChild == null && n.rightChild == null){
			  return true;
		  }
		  return false;
	  }
	
	//public interface Visitor<Key extends Comparable<Key>> {
	public interface Visitor {
		/**
		This method is called at each node.
		@param n the visited node
		*/
		void visit(Node n);  
	}
	
	public void visit(Node n){
		System.out.println(n.key);
	}
	
	public void printTree(){  //preorder: visit, go left, go right
		RedBlackTree.Node currentNode = root;	
		printTree(currentNode);
	}
	
	public void printTree(RedBlackTree.Node node){
		if (node == null) {
			return;
		}
		System.out.print(node.key);
		if (node.isLeaf()){
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}
	
	// place a new node in the RB tree with data the parameter and color it red. 
	public void addNode(String data){  	//this < that  <0.  this > that  >0
		
		if (root == null) {
			root = new RedBlackTree.Node(data);
			root.color = 1;	//root node is always color black
			empty = new RedBlackTree.Node("");	//initialize empty node
			root.parent = empty;
			root.leftChild = empty;
			root.rightChild = empty; 
			empty.color = 1;
			return;
		}
		RedBlackTree.Node oRoot = root; 

		while (!oRoot.key.equals(empty.key) && !oRoot.key.equals(data)) {
			trailer = oRoot;
			if (oRoot.key.compareTo(data) < 0) {
				oRoot = oRoot.rightChild;
			}
			else {
				oRoot = oRoot.leftChild;
			}
		}
		oRoot = new RedBlackTree.Node(data);
		if(trailer.key.compareTo(oRoot.key) < 0) {
			trailer.rightChild = oRoot;
		}
		else {
			trailer.leftChild = oRoot;
		}
		oRoot.parent = trailer; 		
		//going to make empty string leaf nodes for all added nodes instead of null nodes 
		oRoot.leftChild = empty;
		oRoot.rightChild = empty; 
		fixTree(oRoot);
	}	

	public void insert(String data){
		addNode(data);	
	}
	
	public RedBlackTree.Node lookup(String k){ 
		RedBlackTree.Node oRoot = this.root; 
		while (!oRoot.key.equals("") && !oRoot.key.equals(k)) {
			if (oRoot.key.compareTo(k) < 0) {	//this < that < 0. this > that > 0
				oRoot = oRoot.rightChild;
			}
			else {
				oRoot = oRoot.leftChild;
			}
		}
		return oRoot;
	}
 	
	
	public RedBlackTree.Node getSibling(RedBlackTree.Node n){  
		//
		RedBlackTree.Node parent  = n.parent;
		if (n == parent.leftChild) {
			return parent.rightChild;
		}
		else {
			return parent.leftChild;
		}
	}	
	
	public RedBlackTree.Node getAunt(RedBlackTree.Node n){
		//
		RedBlackTree.Node grandparent = getGrandparent(n);
		if (n.parent == grandparent.leftChild) {
			return grandparent.rightChild;
		}
		else {
			return grandparent.leftChild;
		}
		
	}
	
	public RedBlackTree.Node getGrandparent(RedBlackTree.Node n){
		return n.parent.parent;
	}
	
	//my owm method for my main
	public String getGrandparent(String k) {
		RedBlackTree.Node test = new RedBlackTree.Node(k);
		RedBlackTree.Node grandparent = test.parent.parent;
		return grandparent.key;
	}
	
	public void rotateLeft(RedBlackTree.Node x){
		//
		RedBlackTree.Node y = x.rightChild;
		x.rightChild = y.leftChild; 
		if (y.leftChild != empty) {
			y.leftChild.parent = x; 
		}
		y.parent = x.parent;
		if (x.parent == empty) {
			root = y;   //not sure if im supposed to use root here??
		}
		else if (x == x.parent.leftChild) {
			x.parent.leftChild = y; 
		}
		else {
			x.parent.rightChild = y; 
		}
		y.leftChild = x;
		x.parent = y; 
	}
	
	public void rotateRight(RedBlackTree.Node y){
		//
		RedBlackTree.Node x = y.leftChild;
		y.leftChild = x.rightChild;
		if (x.rightChild != empty) {
			x.rightChild.parent = y; 
		}
		x.parent = y.parent;
		if (y.parent == empty) {
			root = x;
		}
		else if (y == y.parent.rightChild) {
			y.parent.rightChild = x; 
		}
		else {
			y.parent.leftChild = x; 
		}
		x.rightChild = y; 
		y.parent = x;
	}
	
	public void fixTree(RedBlackTree.Node current) {
		//
		while (current.parent.color == 0) {
			if (current.parent == current.parent.parent.leftChild) {
				RedBlackTree.Node y = current.parent.parent.rightChild; 
				if (y.color == 0) {
					current.parent.color = 1; 
					y.color = 1; 
					current.parent.parent.color = 0;
					current = current.parent.parent; 
				}
				else {
					if (current == current.parent.rightChild) {
						current = current.parent; 
						this.rotateLeft(current);
					}
					current.parent.color = 1; 
					current.parent.parent.color = 0; 
					this.rotateRight(current.parent.parent); 
				}
			}
			else {
				RedBlackTree.Node y = current.parent.parent.leftChild;
				if (y.color == 0) {	//Red = 0; Black = 1
					current.parent.color = 1; 
					y.color = 1; 
					current.parent.parent.color = 0; 
					current = current.parent.parent; 
				}
				else {
					if (current == current.parent.leftChild) {
						current = current.parent; 
						this.rotateRight(current);
					}
					current.parent.color = 1; 
					current.parent.parent.color = 0; 
					this.rotateLeft(current.parent.parent);
				}
			}
		}
		root.color = 1; 
	}
	
	public boolean isEmpty(RedBlackTree.Node n){
		if (n.key == null){
			return true;
		}
		return false;
	}
	 
	public boolean isLeftChild(RedBlackTree.Node parent, RedBlackTree.Node child)
	{
		if (child.compareTo(parent) < 0 ) {//child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor v) {
	   	preOrderVisit(root, v);
	}
	 
	private static void preOrderVisit(RedBlackTree.Node n, Visitor v) {
	  	if (n == null) {
	  		return;
	  	}
	  	v.visit(n);
	  	preOrderVisit(n.leftChild, v);
	  	preOrderVisit(n.rightChild, v);
	}	
	
	public static void main(String[] args) {
//		RedBlackTree tree = new RedBlackTree();
//		tree.insert("B");
//		tree.insert("C");
//		tree.insert("F");
//		tree.insert("A");
//		tree.insert("D");
//		RedBlackTree.Node A = tree.lookup("A");                
//		RedBlackTree.Node B = tree.lookup("B");                
//		RedBlackTree.Node C = tree.lookup("C");
//		RedBlackTree.Node D = tree.lookup("D");
//		RedBlackTree.Node F = tree.lookup("F");
//		tree.printTree();
//		System.out.println();
//		//root (C)+color
//		System.out.println("root: "+C.key+C.color);
//		//root (C)'s children+color
//		System.out.println("C's left child: "+C.leftChild.key+C.leftChild.color);
//		System.out.println("C's right child: "+C.rightChild.key+C.rightChild.color);
//		//B's children + color 
//		System.out.println("B's left child: "+B.leftChild.key+B.leftChild.color);
//		System.out.println("B's right child: "+B.rightChild.key+B.rightChild.color);
//		//F's children + color
//		System.out.println("F's left child: "+F.leftChild.key+F.leftChild.color);
//		System.out.println("F's right child: "+F.rightChild.key+F.rightChild.color);
			
		RedBlackTree tree2 = new RedBlackTree(); 
		tree2.insert("B");
		tree2.insert("C");
		tree2.insert("F");
		tree2.insert("A");
		tree2.insert("D");
		tree2.insert("G");
		tree2.insert("K");
		tree2.insert("L");
		tree2.insert("O");
		tree2.insert("P");
		tree2.printTree();
		System.out.println();
		
		RedBlackTree.Node A = tree2.lookup("A");
		RedBlackTree.Node B = tree2.lookup("B");
		RedBlackTree.Node C = tree2.lookup("C");
		RedBlackTree.Node D = tree2.lookup("D");
		RedBlackTree.Node F = tree2.lookup("F");
		RedBlackTree.Node G = tree2.lookup("G");
		RedBlackTree.Node K = tree2.lookup("K");
		RedBlackTree.Node L = tree2.lookup("L");
		RedBlackTree.Node O = tree2.lookup("O");
		RedBlackTree.Node P = tree2.lookup("P");
				
		//root + color
		System.out.println("Root: "+F.key+F.color);
		//root's children + color 
		System.out.println("Root's left child: "+F.leftChild.key+F.leftChild.color);
		System.out.println("Root's right child: "+F.rightChild.key+F.rightChild.color);
		//C's children + color 
		System.out.println("C's left child: "+C.leftChild.key+C.leftChild.color);
		System.out.println("C's right child: "+C.rightChild.key+C.rightChild.color);
		//K's children + color 
		System.out.println("K's left child: "+K.leftChild.key+K.leftChild.color);
		System.out.println("K's right child: "+K.rightChild.key+K.rightChild.color);
		//B's children + color 
		System.out.println("B's left child: "+B.leftChild.key+B.leftChild.color);
		System.out.println("B's right child: "+B.rightChild.key+B.rightChild.color);
		//D's children + color 
		System.out.println("D's left child: "+D.leftChild.key+D.leftChild.color);
		System.out.println("D's right child: "+D.leftChild.key+D.rightChild.color);
		//G's children + color 
		System.out.println("G's left child: "+G.leftChild.key+G.leftChild.color);
		System.out.println("G's right child: "+G.leftChild.key+G.rightChild.color);
		//O's children + color 
		System.out.println("O's left child: "+O.leftChild.key+O.leftChild.color);
		System.out.println("O's right child: "+O.rightChild.key+O.rightChild.color);
		
	}
}

