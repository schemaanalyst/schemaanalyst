/*
 */
package experiment.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores the content data required to write a report. This includes the ordered
 * header information and the values stored in a Result.
 * @author chris
 */
public class Report {
    //TODO: consider renaming this class 'Record', as it more represents one row
    
    private Result result;
    private ArrayList<String> header;
    private String name;
    
    /**
     * Constructor for a Report without a given name.
     */
    public Report() {
        this("");
    }
    
    /**
     * Constructor for a report with a given name.
     * @param name The report name
     */
    public Report(String name) {
        result = new Result();
        header = new ArrayList<>();
    }
    
    /**
     * Getter for the 'name' of the report.
     * @return The name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Inserts a value to be included in the Result of this Report.
     * @param attribute The attribute the value corresponds to
     * @param value The value to store
     */
    public void addValue(String attribute, String value) {
        result.addValue(attribute, value);
        header.add(attribute);
    }
    
    /**
     * The header values corresponding to the values to be written from this 
     * report. These are ordered according to the order in which they were 
     * added to report.
     * @return The ordered header values 
     */
    public Iterable<String> getHeader() {
        return header;
    }
    
    /**
     * The data values to be written from this report. The keys used in the 
     * returned Map are the header values each data value corresponds to. Note 
     * this collection is not copied, therefore changes to this will affect the 
     * original report it is retrieved from.
     * @return The data values
     */
    public Map<String,String> getValues() {
        return result.getValues();
    }
    
    /**
     * Produces a deep copy of a Report.
     * @param original The Report to copy
     * @return The deep copy
     */
    public static Report deepCopyOf(Report original) {
        Report copy = new Report();
        
        // Copy the header
        ArrayList<String> header = new ArrayList<>(original.header.size());
        for (String column: original.header) {
            header.add(column);
        }
        copy.header = header;
        
        // Copy the name
        copy.name = original.name;
        
        // Copy the values
        HashMap<String,String> map = new HashMap<>(original.result.getValues());
        copy.result.setValues(map);
        
        return copy;
    }
}
