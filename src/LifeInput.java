package evaluationTool.data;

public interface LifeInput {

  public Input getStdInput();

  public Input getErrInput();

  public Input getCombinedInput();

  /** an identifier of the source
   *
   * @return an identifier
   */
  public String getIdentifier();
}
