package org.schemaanalyst.util.collection;

public abstract class IdentifiableEntity {

    private Identifier id;
    private IdentifiableEntitySet<?> set;
    
    public IdentifiableEntity() {        
    }
    
    public IdentifiableEntity(String name) {
        setName(name);
    }
    
    public String getName() {
        return id.get();
    }
    
    public void setName(String name) {
    	if (set != null) {
    		if (set.contains(name)) {
    			return;
    		}
    	}
        id = new Identifier(name);
    }
    
    void setBelongingSet(IdentifiableEntitySet<?> set) {
    	this.set = set;
    }
    
    public boolean hasIdentifier() {
        return getIdentifier() != null;
    }
    
    public Identifier getIdentifier() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        IdentifiableEntity other = (IdentifiableEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
