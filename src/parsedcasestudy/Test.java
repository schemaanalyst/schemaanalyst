
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
        createPrimaryKeyConstraint(one, one.getColumn("a"));
        
        Table two = createTable("two");
        two.createColumn("a", new IntDataType());
//        createForeignKeyConstraint(two, two.getColumn("a"), one, one.getColumn("a"));
//        createCheckConstraint(two, new NullExpression(new ColumnExpression(two, two.getColumn("a")), true));
    }
    
}
