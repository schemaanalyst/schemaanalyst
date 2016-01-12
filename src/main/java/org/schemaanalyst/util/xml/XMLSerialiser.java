/*
 */
package org.schemaanalyst.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A general purpose XML serialiser and deserialiser. Uses SAX for formatting of
 * the output file and XStream for the IO.
 *
 * @author Chris J. Wright
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
                String xml = XMLFormatter.format(xstream.toXML(object));
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
        try (FileReader reader = new FileReader(path)) {
            Clazz object = (Clazz) xstream.fromXML(reader);
            return object;
        } catch (XStreamException | IOException xEx) {
            throw new RuntimeException("Failed to deserialise the target file", xEx);
        }
    }
}
