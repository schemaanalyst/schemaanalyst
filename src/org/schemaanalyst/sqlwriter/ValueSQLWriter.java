package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.DateValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.ValueVisitor;

public class ValueSQLWriter {

    public String writeValue(Value value) {
        class ValueSQLWriterVisitor implements ValueVisitor {

            String sql;

            public String writeValue(Value value) {
                sql = "";
                value.accept(this);
                return sql;
            }

            public void visit(BooleanValue value) {
                sql = writeBooleanValue(value);
            }

            public void visit(DateValue value) {
                sql = writeDateValue(value);
            }

            public void visit(DateTimeValue value) {
                sql = writeDateTimeValue(value);
            }

            public void visit(NumericValue value) {
                sql = writeNumericValue(value);
            }

            public void visit(StringValue value) {
                sql = writeStringValue(value);
            }

            public void visit(TimeValue value) {
                sql = writeTimeValue(value);
            }

            public void visit(TimestampValue value) {
                sql = writeTimestampValue(value);
            }
        }

        if (value == null) {
            return writeNullValue();
        } else {
            return (new ValueSQLWriterVisitor()).writeValue(value);
        }
    }

    public String writeNullValue() {
        return "NULL";
    }

    public String writeBooleanValue(BooleanValue booleanValue) {
        return booleanValue.toString();
    }

    public String writeDateValue(DateValue dateValue) {
        return dateValue.toString();
    }

    public String writeDateTimeValue(DateTimeValue dateTimeValue) {
        return dateTimeValue.toString();
    }

    public String writeNumericValue(NumericValue numericValue) {
        return numericValue.toString();
    }

    public String writeStringValue(StringValue stringValue) {
        return stringValue.toString();
    }

    public String writeTimeValue(TimeValue timeValue) {
        return timeValue.toString();
    }

    public String writeTimestampValue(TimestampValue timestampValue) {
        return timestampValue.toString();
    }
}
