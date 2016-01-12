package org.schemaanalyst.data;

import java.util.ArrayList;
import java.util.List;

public class StringValue extends Value implements CompoundValue {

    private static final long serialVersionUID = -2565741196272855452L;
    public static final int UPPER_A_CHAR = 65;
    public static final int LOWER_A_CHAR = 97;
    public static final int LOWER_Z_CHAR = 122;
    public static final int NO_MAX_LENGTH = -1;

    protected int maxLength = NO_MAX_LENGTH;
    protected int characterDefault = LOWER_A_CHAR;
    protected int characterMin = UPPER_A_CHAR;
    protected int characterMax = LOWER_Z_CHAR;
    protected List<NumericValue> characters = new ArrayList<>();

    public StringValue() {
    }

    public StringValue(String string) {
        set(string);
    }

    public StringValue(int maxLength) {
        this.maxLength = maxLength;
    }

    public StringValue(String string, int maxLength) {
        this.maxLength = maxLength;
        set(string);
    }

    public String get() {
        StringBuilder sb = new StringBuilder();
        for (Value value : characters) {
            NumericValue singularValue = (NumericValue) value;
            int character = singularValue.get().intValue();
            sb.appendCodePoint(character);
        }
        return sb.toString();
    }

    @Override
    public List<Value> getElements() {
        List<Value> elements = new ArrayList<>();
        elements.addAll(characters);
        return elements;
    }

    public NumericValue getCharacter(int index) {
        return characters.get(index);
    }

    public int getLength() {
        return characters.size();
    }

    public int getMaxLength() {
        return maxLength;
    }

    protected void setCharacterDefault(int characterDefault) {
        this.characterDefault = characterDefault;
    }

    protected void setCharacterRange(int characterMin, int characterMax) {
        this.characterMin = characterMin;
        this.characterMax = characterMax;
    }

    public void set(String string) {
        characters.clear();

        int length = string.length();
        if (maxLength != NO_MAX_LENGTH && maxLength < length) {
            length = maxLength;
        }

        for (int i = 0; i < length; i++) {
            NumericValue character = createCharacter(string.codePointAt(i));
            characters.add(character);
        }
    }

    public void setCharacter(int index, NumericValue value) {
        characters.set(index, value);
    }

    public void clearCharacters() {
        characters.clear();
    }

    public boolean addCharacter() {
        return addCharacter(createCharacter(characterDefault));
    }

    public boolean addCharacter(NumericValue character) {
        if (maxLength == NO_MAX_LENGTH || getLength() < maxLength) {
            characters.add(character);
            return true;
        }
        return false;
    }

    public boolean removeCharacter() {
        if (getLength() > 0) {
            characters.remove(getLength() - 1);
            return true;
        }
        return false;
    }

    protected NumericValue createCharacter(int character) {
        NumericValue characterValue = new NumericValue(character, characterMin, characterMax);
        return characterValue;
    }

    @Override
    public void accept(ValueVisitor valueVisitor) {
        valueVisitor.visit(this);
    }

    @Override
    public StringValue duplicate() {
        StringValue duplicate = new StringValue();
        duplicate.maxLength = maxLength;
        duplicate.characterDefault = characterDefault;
        duplicate.characterMin = characterMin;
        duplicate.characterMax = characterMax;

        for (NumericValue character : characters) {
            duplicate.characters.add(character.duplicate());
        }

        return duplicate;
    }

    @Override
    public int compareTo(Value v) {
        if (getClass() != v.getClass()) {
            throw new DataException(
                    "Cannot compare StringValues to a " + v.getClass());
        }

        return get().compareTo(((StringValue) v).get());
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + characterDefault;
        result = prime * result + characterMax;
        result = prime * result + characterMin;
        result = prime * result
                + ((characters == null) ? 0 : characters.hashCode());
        result = prime * result + maxLength;
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
        StringValue other = (StringValue) obj;
        if (characterDefault != other.characterDefault)
            return false;
        if (characterMax != other.characterMax)
            return false;
        if (characterMin != other.characterMin)
            return false;
        if (characters == null) {
            if (other.characters != null)
                return false;
        } else if (!characters.equals(other.characters))
            return false;
        return maxLength == other.maxLength;
    }

    @Override
    public String toString() {
        return "'" + get() + "'";
    }
}
