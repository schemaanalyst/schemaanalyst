package org.schemaanalyst.util.collection;

public abstract class NamedEntity {

    private Identifier id;
    
    public NamedEntity() {        
    }
    
    public NamedEntity(String str) {
        setName(str);
    }
    
    public String getName() {
        return id.get();
    }
    
    public void setName(String str) {
        id = new Identifier(str);
    }
    
    public boolean hasName() {
        return getName() != null;
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
        NamedEntity other = (NamedEntity) obj;
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
