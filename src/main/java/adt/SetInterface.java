/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author chinj
 * @param <T>
 */
public interface SetInterface<T> {
    boolean add (T newElement);
    boolean remove(T anElement);
    boolean contains (T anElement);
    void clear();
    boolean isEmpty();
    int size();
    T get(int index);
    SetInterface<T> union(SetInterface<T> anotherSet);
    SetInterface<T> intersect(SetInterface<T> anotherSet);
    SetInterface<T> subset(T fromElement, T toElement);
    SetInterface<T> headSet(T toElement, boolean inclusive);
    SetInterface<T> tailSet(T fromElement, boolean inclusive);
    SetInterface<T> sort(Comparator<? super T> newComparator);
    Iterator<T> getIterator();
    Iterator<T> getIterator(T startingElement);
}
