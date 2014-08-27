
package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

/**
 *
 * @author Chris J. Wright
 */
public class Test extends Schema {

    public Test() {
        super("test");
        Table one = createTable("one");
        one.createColumn("a", new IntDataType());
        one.createColumn("b", new IntDataType());
        one.createColumn("c", new IntDataType());
        createPrimaryKeyConstraint(one, one.getColumn("a"));
        createUniqueConstraint(one, one.getColumn("b"));
        createNotNullConstraint(one, one.getColumn("c"));
        
        Table two = createTable("two");
        two.createColumn("a", new IntDataType());
        createForeignKeyConstraint(two, two.getColumn("a"), one, one.getColumn("a"));
    }
    
}
