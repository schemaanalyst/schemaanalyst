package org.schemaanalyst.javawriter;

import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.datatype.DataTypeCategoryVisitor;
import org.schemaanalyst.sqlrepresentation.datatype.LengthLimited;
import org.schemaanalyst.sqlrepresentation.datatype.PrecisionedAndScaled;
import org.schemaanalyst.sqlrepresentation.datatype.Signed;

public class DataTypeJavaWriter {

	protected ImportManager importManager;
	
	public DataTypeJavaWriter(ImportManager importManager) {
		this.importManager = importManager;
	}
	
	public String writeConstructor(DataType dataType) {
		
		class SchemaWriterDataTypeCategoryVisitor implements DataTypeCategoryVisitor {

			String java;
			
			String writeParams(DataType type) {
				java = "";
				type.accept(this);
				return "(" + java + ")";
			}
			
			public void visit(DataType type) {
				// no params for standard types -- do nothing
			}

			public void visit(LengthLimited type) {
				Integer length = type.getLength();
				if (length != null) {
					java += type.getLength();
				}
			}

			public void visit(PrecisionedAndScaled type) {
				Integer precision = type.getPrecision(); 
				if (precision != null) {
					java += precision;
					Integer scale = type.getScale();
					if (scale != null) {
						java += ", " + scale;
					}
				}
			}

			public void visit(Signed type) {
				boolean isSigned = type.isSigned();
				if (!isSigned) {
					java += "false";
				}
			}
		}
		
		importManager.addImportFor(dataType);
		String dataTypeClassName = dataType.getClass().getSimpleName();
		return "new " + dataTypeClassName + new SchemaWriterDataTypeCategoryVisitor().writeParams(dataType);
	}	
}
