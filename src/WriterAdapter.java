package evaluationTool.writer;

import evaluationTool.exceptions.InitException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: axel
 * Date: 26.03.12
 * Time: 13:56
 * To change this template use File | Settings | File Templates.
 * 
 * the interface of the writer adapter that is used to write to various sources
 * 
 */
public interface WriterAdapter {

    /**
     * initialices the WriterAdapter with Strings from the config xml
     * @param args strings from the config xml
     * @throws InitException is thrown when the initialication failed
     */
    public void init(String[] args) throws InitException;
    
    /**
     * writes the data to the source
     * @param data the data to write
     * @throws IOException is thrown when an io exception occures
     */
    public void write(Object data) throws IOException;

    /**
     * Converts the Object to a type this class can actually use.
     * 
     * @param data the raw data object
     * @return the casted data object
     * @throws ClassCastException is thrown if the object could not be casted
     */
    public void testConversion(Object data) throws ClassCastException;

    public String getDescription();


}
