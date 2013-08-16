package org.schemaanalyst.util.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.schemaanalyst.util.Duplicable;

public class NamedEntityInsertOrderedSet<E extends NamedEntity> implements
        Set<E>, Duplicable<NamedEntityInsertOrderedSet<E>>, Serializable {

    private static final long serialVersionUID = -20454495160065002L;

    private LinkedHashMap<Name, E> elements;

    public NamedEntityInsertOrderedSet() {
        elements = new LinkedHashMap<>();
    }

    @Override
    public boolean add(E element) {
        Name name = element.getNameInstance();
        if (contains(element)) {
            return false;
        }
        elements.put(name, element);
        return true;
    }
  
    public E get(String string) {
        return get(new Name(string));
    }
    
    public E get(Name name) {
        return elements.get(name);
    }
    
    protected Name nameFor(Object o) {
        if (o instanceof String) {
            return new Name((String) o);
        } else if (o instanceof Name) {
            return (Name) o;
        } else if (o instanceof NamedEntity) {
            return ((NamedEntity) o).getNameInstance();
        }         
        return null;
    }
    
    @Override
    public boolean contains(Object o) {
        Name name = nameFor(o);        
        if (name == null) {
            return false;
        }
        return elements.containsKey(name);
    }

    @Override
    public boolean remove(Object o) {
        Name name = nameFor(o);       
        if (name == null) {
            return false;
        }
        return elements.remove(name) != null;                        
    }

    @Override
    public Iterator<E> iterator() {
        return elements.values().iterator();
    }
    
    @Override
    public NamedEntityInsertOrderedSet<E> duplicate() {
        NamedEntityInsertOrderedSet<E> duplicate = new NamedEntityInsertOrderedSet<>();
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
        List<E> list = new ArrayList<>();
        for (E element : this) {
            list.add(element);
        }
        return list;
    }
    
    @Override
    public Object[] toArray() {
        Object[] a = new Object[size()];
        int i = 0;
        for (E element : this) {
            a[i] = element;
            i ++;
        }
        return a;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        int size = size();
        
        if (a.length < size) { 
            // If array is too small, allocate the new one with the same component type
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        } else if (a.length > size) {
            // If array is to large, set the first unassigned element to null
            a[size] = null;
        }

        int i = 0;
        for (E e: this) {
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
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((elements == null) ? 0 : elements.hashCode());
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
        @SuppressWarnings("rawtypes")
        NamedEntityInsertOrderedSet other = (NamedEntityInsertOrderedSet) obj;
        if (elements == null) {
            if (other.elements != null)
                return false;
        } else if (!elements.equals(other.elements))
            return false;
        return true;
    }   
}
