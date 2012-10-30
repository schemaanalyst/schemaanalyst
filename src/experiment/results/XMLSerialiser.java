/*
 */
package experiment.results;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;

/**
 * A general purpose XML serialiser and deserialiser. Uses SAX for formatting of
 * the output file and XStream for the IO.
 * @author chris
 */
public class XMLSerialiser {
    
    private static XStream xstream = new XStream(new StaxDriver());
    
    /**
     * Serialises a given object to an XML file. If the file cannot be written, 
     * the cause will be written to the class logger.
     * 
     * @param object The object to serialise
     * @param path The file to serialise to
     * @return Whether serialisation was successful
     */
    public static boolean save(Object object, String path) {
        try {
            File output = new File(path);
            File parent = output.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            try (PrintWriter writer = new PrintWriter(output)) {
                xstream.processAnnotations(object.getClass());
                String xml = format(xstream.toXML(object)); //
                writer.println(xml);
                writer.flush();
            }
            return true;
        } catch (FileNotFoundException fnfEx) {
            Logger.getLogger(XMLSerialiser.class.getName()).log(Level.SEVERE, "Failed to open file "
                    + "to serialise object to", fnfEx);
            return false;
        }
    }
    
    /**
     * Deserialises a file into an object. If the object cannot be loaded, the 
     * cause will be thrown as a RuntimeException.
     * 
     * @param path The file to deserialise
     * @return The object deserialised
     */
    @SuppressWarnings("unchecked")
    public static <Clazz> Clazz load(String path) {
        try {
            File input = new File(path);
            Clazz object = (Clazz)xstream.fromXML(input);
            return object;
        } catch (XStreamException xEx) {
            throw new RuntimeException("Failed to deserialise the target file", xEx);
        }
    }
    
    /**
     * Formats XML to allow better readability.
     * 
     * @param xml The XML to format
     * @return The formatted XML
     */
    private static String format(String xml) {
        try {
            Transformer serializer= SAXTransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Source xmlSource=new SAXSource(new InputSource(new ByteArrayInputStream(xml.getBytes())));
            StreamResult res =  new StreamResult(new ByteArrayOutputStream());            
            serializer.transform(xmlSource, res);
            return new String(((ByteArrayOutputStream)res.getOutputStream()).toByteArray());
        } 
	catch(IllegalArgumentException|TransformerException|TransformerFactoryConfigurationError ex){
            Logger.getLogger(XMLSerialiser.class.getName()).log(Level.SEVERE, "Failed to format"
                    + " XML correctly", ex);
	    return xml;
        }
    }
    
}
