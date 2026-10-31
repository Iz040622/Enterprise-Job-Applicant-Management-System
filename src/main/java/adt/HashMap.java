/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.util.Iterator;

/**
 *
 * @author chinj
 * @param <K>
 * @param <V>
 */
public class HashMap<K extends Comparable<? super K>, V extends Comparable<? super V>> implements MapInterface<K, V>{

    private Node<K, V>[] nodesArray;
    private static final int DEFAULT_CAPACITY = 10;
    private int noOfEntries;
    
    public static class Node<K, V> implements MapInterface.Entry<K, V>{
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        this.nodesArray = (Node<K, V>[]) new Node[capacity];
        this.noOfEntries = 0;
    }
    
    private int hash(K key){
        return Math.abs(key.hashCode()) % nodesArray.length;    //Get the index of the nodesArray based on hashcode and array length
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean put(K key, V value) {
        int entryIndex = hash(key);         //Get nodesArray index based on key
        Node<K, V> entry = nodesArray[entryIndex];      //Get first node of the specified nodesArray index
        
        while (entry != null){              //Specified index of the nodeArray contains pair(s)
            if ((entry.key).equals(key)){       //Update value if key already exists
                entry.value = value;
                return true;
            }
            
            entry = entry.next;             //Continue check next entry within the same index
        }
        
        //When key doesn't exist
        Node<K, V> newEntry = new Node(key, value);
        newEntry.next = nodesArray[entryIndex];     //Set current first node to newEntry's next
        nodesArray[entryIndex] = newEntry;     //Set newEntry to first node
        
        noOfEntries++;
        
        //resize after filling 75% of the capacity
        if ((1.0 * noOfEntries) / nodesArray.length > 0.75) {
            resize();
        }
        
        return true;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldNodesArray = nodesArray;
        nodesArray = (Node<K, V>[]) new Node[oldNodesArray.length * 2];
        noOfEntries = 0;

        //rehash all existing entries into new array
        for (Node<K, V> entry : oldNodesArray) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }
    
    @Override
    public boolean remove(K key) {
        int entryIndex = hash(key);         //Get nodesArray index based on key
        Node<K, V> entry = nodesArray[entryIndex];      //Get first node of the specified nodesArray index
        Node<K, V> entryBefore = null;
        
        while (entry != null){              //Specified index of the nodeArray contains pair(s)
            if ((entry.key).equals(key)){       //Remove key already exists
                if (entryBefore == null)    //Given key is the first node in the index
                    nodesArray[entryIndex] = entry.next;
                else                        //Given key is the not first node in the index
                    entryBefore.next = entry.next;
                
                noOfEntries--;
                return true;
            }
            
            entryBefore = entry;
            entry = entry.next;             //Continue check next entry within the same index
        }
        
        return false;
    }

    @Override
    public boolean containsKey(K key){
        int entryIndex = hash(key);         //Get nodesArray index based on key
        Node<K, V> entry = nodesArray[entryIndex];      //Get first node of the specified nodesArray index
        
        while (entry != null){              //Specified index of the nodeArray contains pair(s)
            if ((entry.key).equals(key)){       //Update value if key already exists
                return true;
            }
            
            entry = entry.next;             //Continue check next entry within the same index
        }
        return false;
    }
    
    @Override
    public V get(K key) {
        int entryIndex = hash(key);                     //Get nodesArray index based on key
        Node<K, V> entry = nodesArray[entryIndex];      //Get first node of the specified nodesArray index

        while (entry != null) {
            if ((entry.key).equals(key)) //Found key
                return entry.value;

            entry = entry.next;
        }

        return null;    //Key not found
    }

    @SuppressWarnings("unchecked")
    @Override
    public SetInterface<K> getKeys(){
        SetInterface<K> keys = new TreeSet<>();
        Iterator<MapInterface.Entry<K, V>> pairs = getIterator();
        
        while (pairs.hasNext()){
            keys.add(pairs.next().getKey());            //Get all keys in a treeset
        }
        
        return keys;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public SetInterface<V> getValues(){
        SetInterface<V> values = new TreeSet<>();
        Iterator<MapInterface.Entry<K, V>> pairs = getIterator();
        int count = 0;
        
        while (pairs.hasNext()){
            values.add(pairs.next().getValue());        //Get all values in a treeset
            count++;
        }
        
        return values;
    }
    
    @Override
    public int size() {
        return noOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return noOfEntries == 0;
    }
    
    @Override
    public String toString() {
        String strPairs = "";
        Iterator<MapInterface.Entry<K, V>> pairs = getIterator();
        
        //Iterate through the treeset to add values into the string
        while (pairs.hasNext()){
            strPairs += pairs.next();
            if (pairs.hasNext()) {
                strPairs += ", ";  
            }
        }
        
        return "{" + strPairs + "}";
    }
    
    @Override
    public Iterator<MapInterface.Entry<K, V>> getIterator() {
        return new HashMapIterator();
    }
    
    private class HashMapIterator implements Iterator<MapInterface.Entry<K, V>> {

        private Node<K, V> currentNode;
        private int currentIndex;

        public HashMapIterator() {
            if (isEmpty())          //For empty HashMap
                currentNode = null;
            else{       //Find occupied index's node in the array
                for (int i = 0; i < nodesArray.length; i++) {
                    if (nodesArray[i] != null){
                        currentNode = nodesArray[i];
                        currentIndex = i;
                        break;
                    } 
                }
            }
        }
        
        @Override
        public boolean hasNext() {
            return (currentNode != null);
        }

        @Override
        public Node<K, V> next() {
            if (hasNext()) {
                Node<K, V> value = currentNode;

                currentNode = currentNode.next;     //Get currentNode's next
                if (currentNode == null) {           //Find the next index if currentNode's next is null
                    for (int i = currentIndex + 1; i < nodesArray.length; i++) {
                        if (nodesArray[i] != null) {
                            currentNode = nodesArray[i];
                            currentIndex = i;
                            break;
                        }
                    }
                }

                return value;
            }
            return null;
        }
    }
}
