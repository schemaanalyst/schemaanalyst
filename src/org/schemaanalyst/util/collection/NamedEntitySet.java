package org.schemaanalyst.util.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.schemaanalyst.util.Duplicable;
import org.schemaanalyst.util.StringUtils;

public class NamedEntitySet<E extends NamedEntity> implements
        Set<E>, Duplicable<NamedEntitySet<E>>, Serializable {

    private static final long serialVersionUID = -20454495160065002L;

    private List<E> elements;

    public NamedEntitySet() {
        elements = new ArrayList<>();
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) {
            return false;
        }
        elements.add(element);
        return true;
    }
  
    public E get(String string) {
        return get(new Identifier(string));
    }
    
    public E get(Identifier identifier) {
        for (E element : elements) {
            if (element.getIdentifier().equals(identifier)) {
                return element;
            }
        }
        return null;
    }
    
    protected Identifier nameFor(Object o) {
        if (o instanceof String) {
            return new Identifier((String) o);
        } else if (o instanceof Identifier) {
            return (Identifier) o;
        } else if (o instanceof NamedEntity) {
            return ((NamedEntity) o).getIdentifier();
        }         
        return null;
    }
    
    @Override
    public boolean contains(Object o) {
        Identifier identifier = nameFor(o);        
        if (identifier == null) {
            return false;
        }
        for (E element : elements) {
            if (element.getIdentifier().equals(identifier)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        Identifier identifier = nameFor(o);       
        if (identifier == null) {
            return false;
        }
        for (int i=0; i < elements.size(); i++) {
            E element = elements.get(i);
            if (element.getIdentifier().equals(identifier)) {
                elements.remove(i);
                return true;
            }
        }
        return false;                        
    }

    @Override
    public Iterator<E> iterator() {
        return elements.iterator();
    }
    
    @Override
    public NamedEntitySet<E> duplicate() {
        NamedEntitySet<E> duplicate = new NamedEntitySet<>();
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
        NamedEntitySet other = (NamedEntitySet) obj;
        if (elements == null) {
            if (other.elements != null)
                return false;
        } else if (!elements.equals(other.elements))
            return false;
        return true;
    }   
    
    @Override
    public String toString() {
        return "{" + StringUtils.implode(elements) + "}";
    }
}
