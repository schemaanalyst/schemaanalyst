package org.schemaanalyst.javawriter;

import org.schemaanalyst.data.*;

import java.util.ArrayList;
import java.util.List;

public class ValueJavaWriter {

    protected JavaWriter codeWriter;

    public ValueJavaWriter(JavaWriter codeWriter) {
        this.codeWriter = codeWriter;
    }

    public String writeConstruction(Value value) {

        class WriterValueVisitor implements ValueVisitor {

            List<String> params;

            List<String> getParams(Value value) {
                params = new ArrayList<>();
                value.accept(this);
                return params;
            }

            @Override
            public void visit(BooleanValue value) {
                params.add(codeWriter.writeBoolean(value.get()));
            }

            @Override
            public void visit(DateValue value) {
                // ... to classified
            }

            @Override
            public void visit(DateTimeValue value) {
                // ... to classified
            }

            @Override
            public void visit(NumericValue value) {
                params.add(value.get().toString());
            }

            @Override
            public void visit(StringValue value) {
                params.add(codeWriter.writeString(value.get()));
            }

            @Override
            public void visit(TimeValue value) {
                // ... to classified
            }

            @Override
            public void visit(TimestampValue value) {
                // ... to classified
            }
        }

        List<String> params = new WriterValueVisitor().getParams(value);
        return codeWriter.writeConstruction(value, params);
    }
}
