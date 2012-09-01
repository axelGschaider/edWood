package evaluationTool.exceptions;

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
public class InitException extends Exception {

  public InitException(String msg) {
    super(msg);
  }

}

