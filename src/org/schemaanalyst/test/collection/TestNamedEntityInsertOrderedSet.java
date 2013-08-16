package org.schemaanalyst.test.collection;

import java.util.Iterator;

import org.junit.Test;
import org.schemaanalyst.util.collection.AbstractNamedEntity;
import org.schemaanalyst.util.collection.Name;
import org.schemaanalyst.util.collection.NamedEntityInsertOrderedSet;

import static org.junit.Assert.*;

public class TestNamedEntityInsertOrderedSet {

    class Person extends AbstractNamedEntity {
        
        int age;
        
        Person(String name, int age) {
            setName(name);
            this.age = age;
        }
        
        int getAge() {
            return age;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + age;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            if (getClass() != obj.getClass())
                return false;
            Person other = (Person) obj;
            if (age != other.age)
                return false;
            return true;
        }
    }
    
    @Test
    public void testOneItem() {
        NamedEntityInsertOrderedSet<Person> set = new NamedEntityInsertOrderedSet<>();
        Person p = new Person("Phil", 21); 
        
        assertEquals(0, set.size());
        assertTrue(set.isEmpty());
        
        assertTrue(set.add(p));
        
        assertTrue(set.contains("phil"));
        assertTrue(set.contains("Phil"));
        assertTrue(set.contains(new Name("Phil")));
        assertTrue(set.contains(new Name("phil")));
        assertTrue(set.contains(p));
        assertTrue(set.contains(new Person("Phil", 21)));
        assertEquals(1, set.size());
        
        assertSame(p, set.get("phil"));        
    }
    
    @Test
    public void testTwoItems() {
        NamedEntityInsertOrderedSet<Person> set = new NamedEntityInsertOrderedSet<>();
        Person p1 = new Person("Phil", 21);
        Person p2 = new Person("Greg", 22);
        
        assertTrue(set.add(p1));
        assertTrue(set.add(p2));
        
        assertTrue(set.contains("phil"));
        assertTrue(set.contains("greg"));
        
        assertEquals(2, set.size());
        
        Iterator<Person> it = set.iterator();
        assertSame(p1, it.next());
        assertSame(p2, it.next());
        
        assertFalse(set.add(p1));
        
        assertEquals(2, set.size());
        it = set.iterator();
        assertSame(p1, it.next());
        assertSame(p2, it.next());      
        
        assertSame(p1, set.get("phil"));
        assertSame(p2, set.get("greg"));
    }
    
    @Test
    public void testDuplication() {
        NamedEntityInsertOrderedSet<Person> set1 = new NamedEntityInsertOrderedSet<>();
        Person p1 = new Person("Phil", 21);
        Person p2 = new Person("Greg", 22);        
        Person p3 = new Person("Chris", 23);
        
        set1.add(p1); 
        set1.add(p2);
        set1.add(p3);
        
        NamedEntityInsertOrderedSet<Person> set2 = set1.duplicate();
        
        assertNotSame(set1, set2);
        assertEquals(set1, set2);
    }
    
    @Test
    public void testEquals() {
        NamedEntityInsertOrderedSet<Person> set1 = new NamedEntityInsertOrderedSet<>();
        Person p1 = new Person("Phil", 21);
        Person p2 = new Person("Greg", 22);        
        Person p3 = new Person("Chris", 23);
        
        set1.add(p1); 
        set1.add(p2);
        set1.add(p3);
        
        NamedEntityInsertOrderedSet<Person> set2 = new NamedEntityInsertOrderedSet<>();
        
        set2.add(p3); 
        set2.add(p1);
        set2.add(p2);
                
        assertEquals(set1, set2);
        assertEquals(set1.hashCode(), set2.hashCode());
    }  
    
    @Test
    public void testEqualWithDifferentInstances() {
        NamedEntityInsertOrderedSet<Person> set1 = new NamedEntityInsertOrderedSet<>();
        Person p1a = new Person("Phil", 21);
        Person p2a = new Person("Greg", 22);        
        Person p3a = new Person("Chris", 23);
        
        set1.add(p1a); 
        set1.add(p2a);
        set1.add(p3a);
        
        NamedEntityInsertOrderedSet<Person> set2 = new NamedEntityInsertOrderedSet<>();
        Person p1b = new Person("Phil", 21);
        Person p2b = new Person("Greg", 22);        
        Person p3b = new Person("Chris", 23);
        
        set2.add(p3b); 
        set2.add(p1b);
        set2.add(p2b);
                
        assertEquals(set1, set2);
        assertEquals(set1.hashCode(), set2.hashCode());
    }
    
    @Test
    public void testContainsAll() {
        NamedEntityInsertOrderedSet<Person> set1 = new NamedEntityInsertOrderedSet<>();
        Person p1a = new Person("Phil", 21);
        Person p2a = new Person("Greg", 22);        
        Person p3a = new Person("Chris", 23);
        
        set1.add(p1a); 
        set1.add(p2a);
        set1.add(p3a);
        
        NamedEntityInsertOrderedSet<Person> set2 = new NamedEntityInsertOrderedSet<>();
        Person p1b = new Person("Phil", 21);
        Person p2b = new Person("Greg", 22);        
        Person p3b = new Person("Chris", 23);
        
        set2.add(p3b); 
        set2.add(p1b);
        set2.add(p2b);
                
        assertTrue(set1.containsAll(set2));
        
        set2.add(new Person("Mark", 42));        
        assertFalse(set1.containsAll(set2));
        assertTrue(set2.containsAll(set1));
    }  
    
    @Test
    public void testRemove() {
        NamedEntityInsertOrderedSet<Person> set1 = new NamedEntityInsertOrderedSet<>();
        Person p1 = new Person("Phil", 21);
        Person p2 = new Person("Greg", 22);        
        Person p3 = new Person("Chris", 23);
        
        set1.add(p1); 
        set1.add(p2);
        set1.add(p3);    
        
        assertTrue(set1.remove("phil"));
        assertFalse(set1.contains("phil"));
        assertFalse(set1.contains("Phil"));
        assertFalse(set1.remove("phil"));
        
        assertTrue(set1.remove("Greg"));
        assertFalse(set1.contains("greg"));
        assertFalse(set1.remove("greg"));
        
        assertTrue(set1.remove(new Person("Chris", 23)));
        assertFalse(set1.contains("chris"));
    }
    
    @Test
    public void testRemoveAll() {
        NamedEntityInsertOrderedSet<Person> set1 = new NamedEntityInsertOrderedSet<>();
        Person p1a = new Person("Phil", 21);
        Person p2a = new Person("Greg", 22);        
        Person p3a = new Person("Chris", 23);
        
        set1.add(p1a); 
        set1.add(p2a);
        set1.add(p3a);
        
        NamedEntityInsertOrderedSet<Person> set2 = new NamedEntityInsertOrderedSet<>();
        Person p1b = new Person("Phil", 21);        
        Person p3b = new Person("Chris", 23);
        
        set2.add(p3b); 
        set2.add(p1b);
                
        assertTrue(set1.removeAll(set2));
        assertEquals(1, set1.size());
    }      
    
    @Test
    public void testToArraySuppliedArrayTooSmall() {
        NamedEntityInsertOrderedSet<Person> set = new NamedEntityInsertOrderedSet<>();
        Person p1 = new Person("Phil", 21);
        Person p2 = new Person("Greg", 22);        
        Person p3 = new Person("Chris", 23);
        set.add(p1);
        set.add(p2);
        set.add(p3);
        
        Person[] array = set.toArray(new Person[0]);
        assertEquals(3, set.size());
        assertEquals(3, array.length);
        assertSame(p1, array[0]);
        assertSame(p2, array[1]);
        assertSame(p3, array[2]);
    }
    
    @Test
    public void testToArraySuppliedArrayTooBig() {
        NamedEntityInsertOrderedSet<Person> set = new NamedEntityInsertOrderedSet<>();
        Person p1 = new Person("Phil", 21);
        Person p2 = new Person("Greg", 22);        
        Person p3 = new Person("Chris", 23);
        set.add(p1);
        set.add(p2);
        set.add(p3);
        
        Person[] array = set.toArray(new Person[8]);
        assertEquals(3, set.size());
        assertEquals(8, array.length);
        assertSame(p1, array[0]);
        assertSame(p2, array[1]);
        assertSame(p3, array[2]);        
        assertNull(array[3]);
    }
    
    @Test
    public void testToArraySuppliedArrayCorrectSize() {
        NamedEntityInsertOrderedSet<Person> set = new NamedEntityInsertOrderedSet<>();
        Person p1 = new Person("Phil", 21);
        Person p2 = new Person("Greg", 22);        
        Person p3 = new Person("Chris", 23);
        set.add(p1);
        set.add(p2);
        set.add(p3);
        
        Person[] array = set.toArray(new Person[3]);
        assertEquals(3, set.size());
        assertEquals(3, array.length);
        assertSame(p1, array[0]);
        assertSame(p2, array[1]);
        assertSame(p3, array[2]);
    }    
}
