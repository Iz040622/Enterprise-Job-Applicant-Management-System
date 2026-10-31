/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package adt;

import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author chinj
 * @param <T>
 */
public class TreeSet<T extends Comparable<? super T>> implements SetInterface<T>{
    
    private Node root;
    private int noOfElement;   
    private Comparator<? super T> comparator;  
    
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    
    private class Node{
        private T value;
        private int size;
        private Node left, right, parent;
        private boolean color;
        
        public Node(T value){
            this.value = value;
            this.size = 1;
            this.left = this.right = this.parent = null;
            this.color = RED;
        }
        
        //**Method to update size of node after insertion/deletion**
        private void updateSize(){
            int leftSize = (left != null) ? left.size : 0;
            int rightSize = (right != null) ? right.size : 0;
            this.size = leftSize + rightSize + 1;
        }
    }
    
    public TreeSet(){

    }
    
    public TreeSet(Comparator<? super T> comparator){
        this.comparator = comparator;
    }

    //**Method to compare the value of node**
    private int compare(T node1, T node2) {
        return comparator != null ? comparator.compare(node1, node2) : node1.compareTo(node2);
    }
    
    @Override
    public boolean add(T newElement) {
        //For empty treeset
        Node newNode = new Node(newElement);
        if (isEmpty()){          
            root = newNode;
            root.color = BLACK;
            noOfElement++;
            return true;
        }
        
        //For treeset with element(s)
        Node currentNode = root;         //Set root as comparing node   
        while(true){
            int cmp = compare(newNode.value, currentNode.value);
            
            //newNode smaller than current comparing node (go left)
            if (cmp < 0){                   
                //Check if comparing node has left child
                if (currentNode.left != null)
                    currentNode = currentNode.left;     //Set left child as comparing node
                else{
                    currentNode.left = newNode;         //Set newNode as left child
                    newNode.parent = currentNode;
                    noOfElement++;
                    fixInsertion(newNode);
                    updateNodesSize(newNode);
                    return true;
                }
            }
            //newNode larger than current comparing node (go right)
            else if (cmp > 0){
                //Check if comparing node has right child
                if (currentNode.right != null)
                    currentNode = currentNode.right;     //Set right child as comparing node
                else{
                    currentNode.right = newNode;         //Set newNode as right child
                    newNode.parent = currentNode;
                    noOfElement++;
                    fixInsertion(newNode);
                    updateNodesSize(newNode);
                    return true;
                }
            }
            //newNode already exists in treeset
            else
                return false;
        }
    }
    
    //**Method to fix imbalance or violation after insertion**
    private void fixInsertion(Node targetNode) {
        while (targetNode != root && targetNode.parent.color == RED) {
            Node parent = targetNode.parent;
            Node grandparent = parent.parent;

            if (grandparent == null) {
                break;  // No imbalance above this point
            }

            //parent is the left child of the grandparent
            if (parent == grandparent.left) {
                Node uncle = grandparent.right;

                //case 1: uncle is red
                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    targetNode = grandparent;  //move up the tree for further fixing
                } 
                
                //uncle is null or black
                else {
                    //case 2: targetNode is right child
                    if (targetNode == parent.right) {
                        rotateLeft(parent);
                        targetNode = parent;
                        parent = targetNode.parent;
                    }

                    //case 3: targetNode is left child
                    rotateRight(grandparent);
                    parent.color = BLACK;
                    grandparent.color = RED;
                    break;  //no further fixing needed
                }
            } 
            
            //parent is the right child of the grandparent
            else { 
                Node uncle = grandparent.left;

                //case 1: uncle is red
                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    targetNode = grandparent;  //move up the tree for further fixing
                } 
                
                //uncle is null or black
                else {
                    //case 2: targetNode is left child
                    if (targetNode == parent.left) {
                        rotateRight(parent);
                        targetNode = parent;
                        parent = targetNode.parent;
                    }

                    //case 3: targetNode is right child
                    rotateLeft(grandparent);
                    parent.color = BLACK;
                    grandparent.color = RED;
                    break;  //no further fixing needed
                }
            }
        }

        root.color = BLACK;  //ensure root is always black
    }

    
    @Override
    public boolean remove(T anElement) {
        Node deleteNode = searchNode(new Node(anElement));
        if (deleteNode == null)
            return false;
        
        removeNode(deleteNode);
        noOfElement--;
        return true;
    }
    
    private void removeNode(Node node){
        boolean deleteNodeColor = BLACK;
        Node fixupNode = null;
        
        if (node.left == null && node.right == null){      //targetNode has no child
            if (node == root){
                root = null;
            }
            else{
                if (node == node.parent.left)
                    node.parent.left = null;
                else
                    node.parent.right = null;
                
                updateNodesSize(node.parent);
            }
        }
        else if (node.left == null){         //targetNode has right child
            //Assign node for fixup
            deleteNodeColor = node.color;
            fixupNode = node.right;
           
            transpose(node, node.right);
            updateNodesSize(node.parent);
        }
        else if (node.right == null){        //targetNode has left child
            //Assign node for fixup
            deleteNodeColor = node.color;
            fixupNode = node.left;
           
            transpose(node, node.left);
            updateNodesSize(node.parent);
        }
        else{           //targetNode has both child
            //Find successor
            Node successorNode = getMin(node.right);

            //Assign node for fixup
            deleteNodeColor = successorNode.color;
            fixupNode = successorNode.right;
            
            node.value = successorNode.value;     //Change targetNode's value to successorNode's value
            
            if (successorNode.right != null)
                transpose(successorNode, successorNode.right);
            else{
                if (successorNode == successorNode.parent.left) {
                    successorNode.parent.left = null;
                } 
                else {
                    successorNode.parent.right = null;
                }
            }
            
            updateNodesSize(successorNode.parent);
        }
        
        if (deleteNodeColor == BLACK)
            fixDeletion(fixupNode);
    }
    
    private Node getMin(Node node){
        while (node.left != null)
            node = node.left;
        
        return node;
    }
    
    private void transpose(Node deleteNode, Node replaceNode){
        if (deleteNode == root)
            root = replaceNode;
        else if (deleteNode == deleteNode.parent.left)
            deleteNode.parent.left = replaceNode;
        else
            deleteNode.parent.right = replaceNode;
        
        replaceNode.parent = deleteNode.parent;
    }
    
    //**Method to fix imbalance or violation after deletion**
    private void fixDeletion(Node node){
        if (node == null)
            return;
        
        while(node != root && node.color == BLACK){
            Node sibling = node == node.parent.left ? node.parent.right : node.parent.left;     //get sibling based on node position
            
            //sibling is red
            if (sibling.color == RED){
                sibling.color = BLACK;
                node.parent.color = RED;
                
                //Rotate the parent to restore balance & Update sibling
                if (node == node.parent.left) {
                    rotateLeft(node.parent);
                    sibling = node.parent.right;
                } else {
                    rotateRight(node.parent);
                    sibling = node.parent.left;
                }
            }
            
            //sibling is black, both children of sibling is black
            else if ((sibling.left == null || sibling.left.color == BLACK) && (sibling.right == null || sibling.right.color == BLACK)){
                sibling.color = RED;
                node = node.parent;
            }
            
            //at least one child of sibling is red
            else{
                if (node == node.parent.left && sibling.left.color == RED){
                    sibling.left.color = BLACK;
                    sibling.color = RED;
                    rotateRight(node.parent);
                    
                    //Update sibling
                    sibling = node.parent.right;
                }
                else if (node == node.parent.right && sibling.right.color == RED){
                    sibling.right.color = BLACK;
                    sibling.color = RED;
                    rotateLeft(node.parent);
                    
                    //Update sibling
                    sibling = node.parent.left;
                }
                
                sibling.color = node.parent.color;
                node.parent.color = BLACK;
                if (node == node.parent.left){
                    sibling.right.color = BLACK;
                    rotateLeft(node.parent);
                }
                else{
                    sibling.left.color = BLACK;
                    rotateRight(node.parent);
                }
                
                break;  //end loop
            }
        }
        node.color = BLACK;
    }
    
    //**Method for rotating a subtree to left for balancing the tree**
    private void rotateLeft(Node targetNode){
        Node rightChild = targetNode.right;
        
        //Change rightChild become targetNode's parent
        rightChild.parent = targetNode.parent;    //Change parent of rightChild to parent of targetNode
        if (root == targetNode)
            root = rightChild;    //Change root to rightChild (if targetNode is root)
        else if (targetNode == targetNode.parent.left)
            targetNode.parent.left = rightChild;      //Change targetNode's parent's left to rightChild (if targetNode at parent's left)
        else
            targetNode.parent.right = rightChild;     //Change targetNode's parent's right to rightChild (if targetNode at parent's right)

        targetNode.parent = rightChild;   
        
        //Assign rightChild's left become targetNode's right
        if (rightChild.left != null){
            rightChild.left.parent = targetNode;
        }
        targetNode.right = rightChild.left;      //Remarks: Outside IF because if null need change also
        
        rightChild.left = targetNode;
        
        //Update size for targetNode and rightChild
        targetNode.updateSize();
        rightChild.updateSize();
    }
    
    //**Method for rotating a subtree to right for balancing the tree**
    private void rotateRight(Node targetNode) {
        Node leftChild = targetNode.left;

        //Change leftChild become targetNode's parent
        leftChild.parent = targetNode.parent;    //Change parent of leftChild to parent of targetNode
        if (root == targetNode)
            root = leftChild;    //Change root to leftChild (if targetNode is root)
        else if (targetNode == targetNode.parent.left)
            targetNode.parent.left = leftChild;      //Change targetNode's parent's left to leftChild (if targetNode at parent's left)
        else
            targetNode.parent.right = leftChild;     //Change targetNode's parent's right to leftChild (if targetNode at parent's right)

        targetNode.parent = leftChild;   
        
        //Assign leftChild's right become targetNode's left
        if (leftChild.right != null){
            leftChild.right.parent = targetNode;
        }
        targetNode.left = leftChild.right;      //Remarks: Outside IF because if null need change also
        
        leftChild.right = targetNode;
        
        //Update size for targetNode and leftChild
        targetNode.updateSize();
        leftChild.updateSize();
    }
    
    //**Method to update size of subtree after insertion/deletion** 
    private void updateNodesSize(Node node){
        //Moving up the tree continuously to update size
        while (node != null){       
            node.updateSize();
            node = node.parent;
        }
    }
    
    @Override
    public boolean contains(T anElement) {
        return searchNode(new Node(anElement)) != null;
    }
    
    //**Method to locate a certain node in the treeset**
    private Node searchNode(Node targetNode){       
        //For empty treeset
        if (isEmpty()){          
            return null;
        } 
        
        //For treeset with element(s)
        Node currentNode = root;         //Set root as comparing node 
        
        int cmp;
        do{         //Find targetNode
            cmp = compare(targetNode.value, currentNode.value);
            
            //targetNode smaller than current comparing node 
            if (cmp < 0){
                if (currentNode.left != null)           //go left
                    currentNode = currentNode.left;
                else                //targetNode does not exist
                    return null;
            }
            //targetNode bigger than current comparing node 
            else if (cmp > 0){
                if (currentNode.right != null)          //go right
                    currentNode = currentNode.right;
                else                //targetNode does not exist
                    return null;
            }
        } while (cmp != 0);
        return currentNode;     //targetNode found
    }

    @Override
    public void clear() {
        root = null;
        noOfElement = 0;
    }
    
    @Override
    public boolean isEmpty() {
        return noOfElement == 0;
    }

    @Override
    public int size() {
        return noOfElement;
    }
    
    @Override
    public T get(int index){
        //For invalid index or empty treeset
        if (index < 0 || index >= noOfElement || isEmpty())
            return null;
        
        Node node = root;
        int leftSize;
        
        //Finding node by comparing given index with node's size
        while (true){
            leftSize = (node.left != null) ? node.left.size : 0;        //Get left child's size
            if (index < leftSize)       //For nodes at the left subtree
                node = node.left;
            else if (index > leftSize){     //For nodes at the right subtree
                node = node.right;
                index = index - leftSize - 1;       //Find new index after proceeding to right subtree
            }
            else
                return node.value;      //Found node
        }
    }
    
    @Override
    public SetInterface<T> union(SetInterface<T> anotherSet){
        SetInterface<T> newTreeSet = null;
        
        if (anotherSet instanceof TreeSet) {
            //Create a new treeset
            newTreeSet = (comparator != null) ? new TreeSet<T>(comparator) : new TreeSet<>();

            //Add current treeset's values into the new treeset
            Iterator<T> thisIterator = getIterator();
            while (thisIterator.hasNext()) {
                newTreeSet.add(thisIterator.next());
            }

            //Add the given treeset's values into the new treeset
            Iterator<T> anotherIterator = anotherSet.getIterator();
            while (anotherIterator.hasNext()) {
                newTreeSet.add(anotherIterator.next());
            }
        }
        
        return newTreeSet;
    }
    
    @Override
    public SetInterface<T> intersect(SetInterface<T> anotherSet) {
        SetInterface<T> newTreeSet = null;

        if (anotherSet instanceof TreeSet) {
            //Create a new treeset
            newTreeSet = (comparator != null) ? new TreeSet<T>(comparator) : new TreeSet<>();

            //Iterating a smaller set treeset and check whether the value exists in the larger treeset
            Iterator<T> iterator = (size() <= anotherSet.size()) ? getIterator() : anotherSet.getIterator();
            
            SetInterface<T> largerSet = (size() > anotherSet.size()) ? this : anotherSet;
            
            while (iterator.hasNext()) {
                T value = iterator.next();
                
                //Add value into the new treeset if it exists in the given treeset
                if (largerSet.contains(value))
                    newTreeSet.add(value);
            }
        }

        return newTreeSet;
    }
    
    @Override
    public SetInterface<T> subset(T fromElement, T toElement){
        //Create a new treeset
        SetInterface<T> newTreeSet = (comparator != null) ? new TreeSet<T>(comparator) : new TreeSet<>();
        
        //Iterate the current treeset starting from the given fromElement
        Iterator<T> thisIterator = getIterator(fromElement);
            while (thisIterator.hasNext()) {
                T value = thisIterator.next();
                
                //Stop iterating after toElement
                if (compare(value, toElement) > 0)
                    break;
                
                //Add value 
                newTreeSet.add(value);
            }
            
        return newTreeSet;
    }
    
    @Override
    public SetInterface<T> headSet(T toElement, boolean inclusive){
        //Create a new treeset
        SetInterface<T> newTreeSet = (comparator != null) ? new TreeSet<T>(comparator) : new TreeSet<>();

        //Check if toElement exists in the set
        if (!contains(toElement)) {
            return newTreeSet;  
        }
        
        //Iterate the current treeset
        Iterator<T> thisIterator = getIterator();
            while (thisIterator.hasNext()) {
                T value = thisIterator.next();
                
                //Stop iterating at/after toElement
                if (inclusive ? compare(value, toElement) > 0 : compare(value, toElement) >= 0)
                    break;
                
                //Add value
                newTreeSet.add(value);
            }
            
        return newTreeSet;
    }
    
    @Override
    public SetInterface<T> tailSet(T fromElement, boolean inclusive){
        //Create a new treeset
        SetInterface<T> newTreeSet = (comparator != null) ? new TreeSet<T>(comparator) : new TreeSet<>();

        //Check if toElement exists in the set
        if (!contains(fromElement)) {
            return newTreeSet;  
        }
        
        //Iterate the current treeset at fromElement
        Iterator<T> thisIterator = getIterator(fromElement);
            while (thisIterator.hasNext()) {
                T value = thisIterator.next();
                
                //Starting to add value at/after fromElement
                if (inclusive) {
                    if (compare(value, fromElement) >= 0) {
                        newTreeSet.add(value);
                    }
                } else {
                    if (compare(value, fromElement) > 0) {
                        newTreeSet.add(value);
                    }
                }
            }
            
        return newTreeSet;
    }
    
    @Override
    public SetInterface<T> sort(Comparator<? super T> newComparator){
        //Create a new treeset
        SetInterface<T> newTreeSet = (newComparator != null) ? new TreeSet<T>(newComparator) : new TreeSet<>();
        
        //Iterate the current treeset
        Iterator<T> thisIterator = getIterator();
            while (thisIterator.hasNext()) {
                newTreeSet.add(thisIterator.next());
            }
        
        return newTreeSet;
    }
    
    @Override
    public String toString() {
        String strValues = "";
        Iterator<T> values = getIterator();
        
        //Iterate through the treeset to add values into the string
        while (values.hasNext()){
            strValues += values.next();
            if (values.hasNext()) {
                strValues += ", ";  
            }
        }
        
        return "[" + strValues + "]";
    }
    
    @Override
    public Iterator<T> getIterator() {
        return new IteratorForTreeSet();
    }
    
    @Override
    public Iterator<T> getIterator(T startingElement) {
        return new IteratorForTreeSet(searchNode(new Node(startingElement)));
    }
    
    private class IteratorForTreeSet implements Iterator<T> {
        Node currentNode;
        
        public IteratorForTreeSet() {
            if (isEmpty())
                currentNode = null;
            else
                currentNode = getMin(root);
        }
        
        public IteratorForTreeSet(Node currentNode) {
            this.currentNode = currentNode;
        }
        
        @Override
        public boolean hasNext() {
            return (currentNode != null);
        }

        @Override
        public T next() {
            if (hasNext()) {
                T value = currentNode.value;
                
                //Find successor if right child exists (to find the smallest value)
                if (currentNode.right != null) {
                    currentNode = getMin(currentNode.right);
                }

                else {
                    //Go up the tree if there is no right child
                    Node parent = currentNode.parent;    //For left child (go up once)   
                    while (parent != null && currentNode == parent.right) {     //For right child (Go up until it is not right child)
                        currentNode = parent;
                        parent = parent.parent;
                    }
                    currentNode = parent;   //Get the unexplored node
                }

                return value;
            }
            
            return null;
        }
    }
}
