package org.schemaanalyst.util.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.schemaanalyst.util.StringUtils;

/**
 * A collection backed by a {@link java.util.LinkedList} for small sets 
 * where the elements need to be mutable (i.e., the dependency on hashCode
 * becomes unreliable). 
 * @author Phil McMinn
 *
 * @param <E> The type of the element to put in the set
 */
public class LinkedSet<E> implements Set<E>, Serializable {

	private static final long serialVersionUID = -5971600781851107925L;
	private LinkedList<E> elements;

	public LinkedSet() {
		elements = new LinkedList<>();
	}

	@Override
	public boolean add(E element) {
		if (contains(element)) {
			return false;
		}
		elements.add(element);
		return true;
	}

	@Override
	public boolean contains(Object o) {
		for (E element : elements) {
			if (element.equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		Iterator<E> iterator = iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(o)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return elements.iterator();
	}

	public LinkedSet<E> duplicate() {
		LinkedSet<E> duplicate = new LinkedSet<>();
		for (E element : this) {
			duplicate.add(element);
		}
		return duplicate;
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	public List<E> toList() {
		return new ArrayList<>(this);
	}

	@Override
	public Object[] toArray() {
		Object[] a = new Object[size()];
		int i = 0;
		for (E element : this) {
			a[i] = element;
			i++;
		}
		return a;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		int size = size();

		if (a.length < size) {
			// If array is too small, allocate the new one with the same
			// component type
			a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
		} else if (a.length > size) {
			// If array is to large, set the first unassigned element to null
			a[size] = null;
		}

		int i = 0;
		for (E e : this) {
			// No need for checked cast - ArrayStoreException will be thrown
			// if types are incompatible, just as required
			a[i] = (T) e;
			i++;
		}
		return a;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object element : c) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean changed = false;
		for (E element : this) {
			if (!c.contains(element)) {
				if (remove(element)) {
					changed = true;
				}
			}
		}
		return changed;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		for (Object element : c) {
			if (remove(element)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public void clear() {
		elements.clear();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean changed = false;
		for (E element : c) {
			if (add(element)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public int hashCode() {
		int result = 1;
		for (E element : elements) {
			result *= element.hashCode();
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkedSet<E> other = (LinkedSet<E>) obj;
		return (size() == other.size() && (containsAll(other.elements)));
	}

	@Override
	public String toString() {
		return "{" + StringUtils.implode(elements) + "}";
	}
}
