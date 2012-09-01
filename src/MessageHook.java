
package evaluationTool.messageHooks;

public interface MessageHook {
  
  public static int IMPORTANCE_HIGH = 1;
  public static int IMPORTANCE_MIDDLE = 2;
  public static int IMPORTANCE_LOW = 3;

  public void error(String message);

  public void success(String message);

  public void generic(String message, int importance);

}


