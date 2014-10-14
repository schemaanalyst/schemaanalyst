package org.schemaanalyst.unittest.util.collection;

import org.junit.Test;
import org.schemaanalyst.util.collection.IdentifiableEntity;
import org.schemaanalyst.util.collection.IdentifiableEntitySet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestIdentifiableEntity {

    class NamedEntityMock extends IdentifiableEntity {
        public NamedEntityMock(String str) {
            super(str);
        }        
    }
    
    @Test
    public void testNamedEntity() {
        NamedEntityMock ne1 = new NamedEntityMock("Phil");
        assertEquals("Phil", ne1.getName());
        
        NamedEntityMock ne2 = new NamedEntityMock("Phil");
        assertEquals(ne1, ne2);
        
        NamedEntityMock ne3 = new NamedEntityMock("Chris");
        assertFalse(ne1.equals(ne3));
    }
    
    @Test
    public void testInSet() {
    	IdentifiableEntitySet<NamedEntityMock> set = new IdentifiableEntitySet<>();
    	
    	NamedEntityMock phil = new NamedEntityMock("Phil");
    	set.add(phil);
    	
    	NamedEntityMock chris = new NamedEntityMock("Chris");
    	set.add(chris);
    	
    	chris.setName("Phil");    	
    	assertEquals(
    			"The name change should not happen as Phil is already in the set",
    			"Chris", chris.getName());
    	
    	chris.setName("phil");    	
    	assertEquals(
    			"The name change should not happen as case is not important",
    			"Chris", chris.getName());
    	
    	chris.setName("Greg");
    	assertEquals(
    			"The name change should happen as Greg is not in the set",
    			"Greg", chris.getName());
    }
}
