
package myCustom;

import edWood.writer.WriterAdapter;
import edWood.exceptions.InitException;

import org.apache.log4j.Logger;
import java.io.IOException;

public class Writer implements WriterAdapter {

    private static Logger log = Logger.getLogger("myCustom.Writer");

    public void init(String[] args) throws InitException {
      log.info(this.getDescription() + ": no init for me.");
    }

    public void write(Object data) throws IOException {
      log.info( this.getDescription() + ": writing '" + this.convert(data).msg + "'"  );
    }

    public void testConversion(Object data) throws ClassCastException {
      this.convert(data);
    }

    private Data convert(Object data) throws ClassCastException {
      return (Data) data;
    }

    public String getDescription() {
      return "Custom Writer";
    }
}

