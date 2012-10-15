package edWood.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: axel
 * Date: 26.04.12
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 *
 * is usually thrown when the initialication of a ReaderAdapter or WriterAdapter failed
 *
 */
public class ReadXmlException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -3234089397145002578L;
  
  public ReadXmlException(String msg) {
    super(msg);
  }

}

