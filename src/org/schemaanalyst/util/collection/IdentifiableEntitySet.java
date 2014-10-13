package org.schemaanalyst.util.collection;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

/**
 * <p>
 * {@link IdentifiableEntitySet} is an ordered set of {@link IdentifiableEntity} instances
 * with unique identifiers.   The set is maintained in order of element entry.
 * </p>
 * 
 * @author Phil McMinn
 * 
 * @param <E>
 *            the type of element to store in the set.
 */

public class IdentifiableEntitySet<E extends IdentifiableEntity> implements Set<E>, Serializable {

    private static final long serialVersionUID = -20454495160065002L;

    private HashMap<Identifier, E> identifiers;
    private LinkedList<E> elements;

    public IdentifiableEntitySet() {
    	identifiers = new HashMap<>();
        elements = new LinkedList<>();
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) {
            return false;
        }
        elements.add(element);
        identifiers.put(element.getIdentifier(), element);
        element.setBelongingSet(this);
        return true;
    }

    public E get(String string) {
        return get(new Identifier(string));
    }

    public E get(Identifier identifier) {
    	return identifiers.get(identifier);
    }

    protected Identifier nameFor(Object o) {
        if (o instanceof String) {
            return new Identifier((String) o);
        } else if (o instanceof Identifier) {
            return (Identifier) o;
        } else if (o instanceof IdentifiableEntity) {
            return ((IdentifiableEntity) o).getIdentifier();
        }
        return null;
    }

    public boolean updateIdentifier(Identifier identifier) {
    	if (!contains(identifier)) {
    		return false;
    	}
    	E element = get(identifier);
    	Identifier newIdentifier = element.getIdentifier();
    	if (identifier.equals(newIdentifier)) {
    		return false;
    	}
    	identifiers.remove(identifier);
    	identifiers.put(newIdentifier, element);
    	return true;
    }
    
    @Override
    public boolean contains(Object o) {
        Identifier identifier = nameFor(o);
        if (identifier == null) {
            return false;
        }
        return identifiers.containsKey(identifier);
    }

    @Override
    public boolean remove(Object o) {
        Identifier identifier = nameFor(o);
        if (identifier == null) {
            return false;
        }
    	E element = identifiers.remove(identifier);
    	if (element == null) {
    		return false;
    	} else {
    		elements.remove(element);
    		return true;
    	}
    }

    @Override
    public Iterator<E> iterator() {
        return elements.iterator();
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
    	@SuppressWarnings("unchecked")
        IdentifiableEntitySet<E> other = ((IdentifiableEntitySet<E>) obj);
        return (size() == other.size() && (containsAll(other.elements)));
    }

    @Override
    public String toString() {
        return "{" + StringUtils.join(elements, ", ") + "}";
    }
}
