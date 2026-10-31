/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

import java.util.Iterator;

/**
 *
 * @author chinj
 * @param <K>
 * @param <V>
 */
public interface MapInterface<K, V>{
    boolean put(K key, V value);
    boolean remove(K key);
    boolean containsKey(K key);
    V get(K key);
    SetInterface<K> getKeys();
    SetInterface<V> getValues();
    int size();
    boolean isEmpty();
    Iterator<MapInterface.Entry<K, V>> getIterator();
    
    interface Entry<K, V> {
        K getKey();
        V getValue();
    }
}
