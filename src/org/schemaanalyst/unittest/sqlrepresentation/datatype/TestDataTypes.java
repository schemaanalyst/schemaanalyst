package org.schemaanalyst.unittest.sqlrepresentation.datatype;

import org.junit.Test;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.sqlrepresentation.datatype.*;
import org.schemaanalyst.util.java.JavaUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class TestDataTypes {
    
    public static final String DATA_TYPE_CLASS_SUFFIX = "DataType";

    // set some defaults that are different from the class-defined defaults
    // so that we can check they are being successfully duplicated.
    DataTypeCategoryVisitor attributeSetter = new DataTypeCategoryVisitor() {
        @Override
        public void visit(DataType type) {
        }

        @Override
        public void visit(LengthLimited type) {
            type.setLength(50);
        }

        @Override
        public void visit(PrecisionedAndScaled type) {
            type.setPrecision(10);
            type.setScale(5);
        }

        @Override
        public void visit(Signed type) {
            type.setSigned(false);
        }        
    };
    
    List<DataType> getDataTypes() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // get the location of the source of the package on the file system
        String dataTypeClassName = DataType.class.getCanonicalName();
        String dataTypePackage = 
                dataTypeClassName.substring(
                        0, dataTypeClassName.length() - DataType.class.getSimpleName().length());  
        
        LocationsConfiguration locationsConfiguration = new LocationsConfiguration();
        File packageDir = new File(locationsConfiguration.getSrcDir() 
                + File.separator + JavaUtils.packageToFileName(dataTypePackage));
        
        // go down the list of files in the directory
        String[] entries = packageDir.list();
        List<DataType> dataTypes = new ArrayList<>();
        String suffix = DATA_TYPE_CLASS_SUFFIX + JavaUtils.JAVA_FILE_SUFFIX;
        for (String entry : entries) {
            // if the file corresponds to a datatype, instantiate it and put it on the list
            if (!entry.equals(suffix) && entry.endsWith(suffix)) {
                String className = dataTypePackage + JavaUtils.fileNameToClassName(entry);
                DataType dataType = (DataType) Class.forName(className).newInstance();
                dataType.accept(attributeSetter);
                dataTypes.add(dataType);
            }
        }
        
        return dataTypes;
    }
    
    @Test
    public void testDataType() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (DataType dataType : getDataTypes()) {
            
            String className = dataType.getClass().getSimpleName();
            DataType duplicate = dataType.duplicate();
            
            assertNotSame(
                    "DataType \"" + className + "\" should not be the same instance as its duplicate",
                    dataType, duplicate);
            
            assertEquals(
                    "DataType \"" + className + "\" should be equal to its duplicate",
                    dataType, duplicate);
        }
    }
}
