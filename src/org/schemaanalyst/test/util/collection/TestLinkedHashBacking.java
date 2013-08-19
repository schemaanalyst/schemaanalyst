package org.schemaanalyst.test.util.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import static org.junit.Assert.*;

/**
 * This is just to confirm my understanding of LinkedHashSet and LinkedHashMap
 * are correct.
 * 
 * @author Phil McMinn
 *
 */

public class TestLinkedHashBacking {

    @Test
    public void testLinkedHashSet() {
        
        Column c1 = new Column("apples", new IntDataType());
        Column c2 = new Column("oranges", new IntDataType());
        
        LinkedHashSet<Column> set1 = new LinkedHashSet<>();        
        set1.add(c1); set1.add(c2);
        
        LinkedHashSet<Column> set2 = new LinkedHashSet<>();
        set2.add(c2); set2.add(c1); 
        
        Iterator<Column> it1 = set1.iterator();
        assertSame(c1, it1.next());
        assertSame(c2, it1.next());
        
        Iterator<Column> it2 = set2.iterator();
        assertSame(c2, it2.next());
        assertSame(c1, it2.next());
        
        assertEquals(set1, set2);
        
        set2.add(c2);
        it2 = set2.iterator();
        assertSame(c2, it2.next());
        assertSame(c1, it2.next());
    }
    
    @Test
    public void testLinkedHashMap() {
        
        Column c1 = new Column("apples", new IntDataType());
        Column c2 = new Column("oranges", new IntDataType());
        
        LinkedHashMap<String, Column> map1 = new LinkedHashMap<>();       
        map1.put(c1.getName(), c1);
        map1.put(c2.getName(), c2);
        
        Iterator<Column> it1 = map1.values().iterator();
        assertSame(c1, it1.next());
        assertSame(c2, it1.next());

        LinkedHashMap<String, Column> map2 = new LinkedHashMap<>();       
        map2.put(c2.getName(), c2);
        map2.put(c1.getName(), c1);        
        
        Iterator<Column> it2 = map2.values().iterator();        
        assertSame(c2, it2.next());
        assertSame(c1, it2.next());
        
        map2.put(c2.getName(), c2);        
        it2 = map2.values().iterator();        
        assertSame(c2, it2.next());
        assertSame(c1, it2.next());        
    } 
}
