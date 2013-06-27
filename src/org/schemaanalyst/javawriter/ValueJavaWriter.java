package org.schemaanalyst.javawriter;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.DateValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.ValueVisitor;

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
			
			public void visit(BooleanValue value) {
				params.add(codeWriter.writeBoolean(value.get()));
			}

			public void visit(DateValue value) {
				// ... to complete
			}

			public void visit(DateTimeValue value) {
				// ... to complete
			}

			public void visit(NumericValue value) {
				params.add(value.get().toString());
			}

			public void visit(StringValue value) {
				params.add(codeWriter.writeString(value.get()));
			}

			public void visit(TimeValue value) {
				// ... to complete				
			}

			public void visit(TimestampValue value) {
				// ... to complete				
			}
			
		}
	
		List<String> params = new WriterValueVisitor().getParams(value); 
		return codeWriter.writeConstruction(value, params);
	}
}
