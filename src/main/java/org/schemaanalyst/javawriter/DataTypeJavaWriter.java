package org.schemaanalyst.javawriter;

import org.schemaanalyst.sqlrepresentation.datatype.*;

import java.util.ArrayList;
import java.util.List;

public class DataTypeJavaWriter {

    protected JavaWriter codeWriter;

    public DataTypeJavaWriter(JavaWriter codeWriter) {
        this.codeWriter = codeWriter;
    }

    public String writeConstruction(DataType dataType) {

        class WriterDataTypeCategoryVisitor implements DataTypeCategoryVisitor {

            List<String> params;

            List<String> getParams(DataType type) {
                params = new ArrayList<>();
                type.accept(this);
                return params;
            }

            @Override
            public void visit(DataType type) {
                // no params for standard types -- do nothing
            }

            @Override
            public void visit(LengthLimited type) {
                Integer length = type.getLength();
                if (length != null) {
                    params.add(type.getLength().toString());
                }
            }

            @Override
            public void visit(PrecisionedAndScaled type) {
                Integer precision = type.getPrecision();
                if (precision != null) {
                    params.add(precision.toString());
                    Integer scale = type.getScale();
                    if (scale != null) {
                        params.add(scale.toString());
                    }
                }
            }

            @Override
            public void visit(Signed type) {
                boolean isSigned = type.isSigned();
                if (!isSigned) {
                    params.add("false");
                }
            }
        }

        List<String> params = new WriterDataTypeCategoryVisitor().getParams(dataType);
        return codeWriter.writeConstruction(dataType, params);
    }
}
