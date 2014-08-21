package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.*;

/**
 * <p>
 * A ValueSQLWriter converts {@link Value} objects into their String 
 * equivalents for use in SQL statements.
 * </p>
 * 
 * <p>
 * This class produces the format needed for most DBMSs, however specialised 
 * subclasses may be provided where DBMSs required different formats.
 * </p>
 */
public class ValueSQLWriter {

    public String writeValue(Value value) {
        class ValueSQLWriterVisitor implements ValueVisitor {

            String sql;

            public String writeValue(Value value) {
                sql = "";
                value.accept(this);
                return sql;
            }

            @Override
            public void visit(BooleanValue value) {
                sql = writeBooleanValue(value);
            }

            @Override
            public void visit(DateValue value) {
                sql = writeDateValue(value);
            }

            @Override
            public void visit(DateTimeValue value) {
                sql = writeDateTimeValue(value);
            }

            @Override
            public void visit(NumericValue value) {
                sql = writeNumericValue(value);
            }

            @Override
            public void visit(StringValue value) {
                sql = writeStringValue(value);
            }

            @Override
            public void visit(TimeValue value) {
                sql = writeTimeValue(value);
            }

            @Override
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
        return new DateWriter().writeDate(dateValue);
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
