package evaluationTool.data;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: axel
 * Date: 26.03.12
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */

/**
 * used to iterate of sources
 */
public interface Input {

    /** an identifier of the source
     *
     * @return an identifier
     */
    public String getIdentifier();

    /**
     * gets an iterator for the source
     * @return iterator for the source
     * @throws IOException if an io exception occured
     */
    public Iterator<String> getIterator() throws IOException;
}
