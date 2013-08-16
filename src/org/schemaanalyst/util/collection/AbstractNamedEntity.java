package org.schemaanalyst.util.collection;

public abstract class AbstractNamedEntity implements NamedEntity {

    private Name name;
    
    public void setName(String str) {
        if (name == null) {
            name = new Name(str);
        } else {
            name.setName(str);
        }
    }
    
    public String getName() {
        return name.get();
    }
    
    public Name getNameInstance() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        AbstractNamedEntity other = (AbstractNamedEntity) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }    
}
