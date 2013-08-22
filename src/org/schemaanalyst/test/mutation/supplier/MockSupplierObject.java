package org.schemaanalyst.test.mutation.supplier;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.util.Duplicable;

/**
 * Mock classes producing objects with IDs and which record the number of times
 * they've been duplicated -- so as to enable us to track them with suppliers.
 * 
 * @author Phil McMinn
 * 
 */

abstract class MockSupplierObject implements Duplicable<MockSupplierObject> {

    String id;
    int timesDuplicated;

    public MockSupplierObject(String id, int timesDuplicated) {
        this.id = id;
        this.timesDuplicated = timesDuplicated;
    }
}

class MockSupplierList extends MockSupplierObject {

    List<MockSupplierInt> list;

    public MockSupplierList(String id, int timesDuplicated,
            List<MockSupplierInt> list) {
        super(id, timesDuplicated);
        this.list = list;
    }

    public MockSupplierList duplicate() {
        List<MockSupplierInt> duplicateList = new ArrayList<>();
        for (MockSupplierInt msi : list) {
            duplicateList.add(msi.duplicate());
        }

        return new MockSupplierList(id, timesDuplicated++, duplicateList);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MockSupplierList other = (MockSupplierList) obj;
        if (list == null) {
            if (other.list != null)
                return false;
        } else if (!list.equals(other.list))
            return false;
        return true;
    }

}

class MockSupplierInt extends MockSupplierObject {

    int intValue;

    public MockSupplierInt(String id, int timesDuplicated, int intValue) {
        super(id, timesDuplicated);
        this.intValue = intValue;
    }

    public MockSupplierInt duplicate() {
        return new MockSupplierInt(id, timesDuplicated++, intValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MockSupplierInt other = (MockSupplierInt) obj;
        if (intValue != other.intValue)
            return false;
        return true;
    }
}