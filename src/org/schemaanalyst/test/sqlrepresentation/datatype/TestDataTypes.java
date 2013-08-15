package org.schemaanalyst.test.sqlrepresentation.datatype;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.util.JavaUtils;

public class TestDataTypes {
    
    public static final String DATA_TYPE_CLASS_SUFFIX = "DataType";

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
                dataTypes.add((DataType) Class.forName(className).newInstance());
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
