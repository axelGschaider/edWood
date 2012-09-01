package myCustom;

import evaluationTool.reader.ReaderAdapter;
import evaluationTool.data.Input;
import evaluationTool.data.LifeInput;

import org.apache.log4j.Logger;

import java.io.IOException;

public class Reader implements ReaderAdapter {

    private static Logger log = Logger.getLogger("myCustom.Reader");

    public String getDescription() {
      return "Custom Reader";
    }

    public void init(String[] args) {
      log.info(this.getDescription() + ": no init for me.");
    }

    public ReaderAdapter readLife(LifeInput input) throws IOException {
      int a = 1/0;
      log.info(this.getDescription() + ": Reading life input " + input.getStdInput().getIdentifier());
      return this;
    }

    public ReaderAdapter readPostRunSources(Input[] inputs) throws IOException {
        for(int i=0; i<inputs.length; i++) {
          log.info(this.getDescription() + ": Reading input " + inputs[i].getIdentifier());
        }
        return this;
    }

    public Object getData() {
      return new Data("data received from the reader");
    }
    public Object getDummyData() {
      return new Data("just dummy data");
    }
}

