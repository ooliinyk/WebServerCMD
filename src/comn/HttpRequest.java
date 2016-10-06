package comn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * HttpRequest class parses the HTTP Request Line (method, URI, version)
 * and Headers http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html
 */
public class HttpRequest {

    private static Logger log = Logger.getLogger(HttpRequest.class);

    private List<String> headers = new ArrayList<String>();

    private MimeType mimeType = new MimeType("mime.types");


    public Method method;

    public String uri;

    public String version;

    public HttpRequest() {
    }

    public HttpRequest(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String str = reader.readLine();
        parseRequestLine(str);

        while (!str.equals("")) {
            str = reader.readLine();
            parseRequestHeader(str);
        }
    }

    public void parseRequestLine(String str) {
        log.info(str);
        String[] split = str.split("\\s+");
        try {
            method = Method.valueOf(split[0]);
        } catch (Exception e) {
            method = Method.UNRECOGNIZED;
        }
        uri = split[1];
        version = split[2];

    }

    private void parseRequestHeader(String str) {
        log.info(str);
        headers.add(str);
    }

    public String mimeTypeLookUp(String headerName) {

        return mimeType.lookUp(headerName);
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
