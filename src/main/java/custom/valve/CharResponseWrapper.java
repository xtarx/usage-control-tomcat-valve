package custom.valve;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.catalina.connector.Response;

public class CharResponseWrapper extends Response {
    private CharArrayWriter output;

    public String toString() {
        return output.toString();
    }

    public CharResponseWrapper(Response response) {
        super();
        output = new CharArrayWriter();
    }

    public PrintWriter getWriter() {
        return new PrintWriter(output);
    }
}
