package comn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * HttpResponse class defines the HTTP Response Status Line (method, URI, version)
 * and Headers http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html
 */
public class HttpResponse {

    private static Logger log = Logger.getLogger(HttpResponse.class);

    public static final String VERSION = "HTTP/1.0";

    List<String> headers = new ArrayList<String>();

    byte[] body;
      public HttpResponse(HttpRequest req, OutputStream out) throws IOException {

//        if(!checkAuthentication(req, out)){
        if (!checkAuthentication(req, out)){
            fillHeaders(Status._403);
            write(out);
        } else {

            switch (req.method) {
                case HEAD:
                    fillHeaders(Status._200);
                    break;
                case GET:

                    String documentRoot = new HttpDConfig("httpd.conf").getConfigurationMap().get("DocumentRoot").get(0);

                    try {
                        fillHeaders(Status._200);
                        File file = new File(documentRoot + req.uri);
                        System.out.println(documentRoot + req.uri);
                        if (file.isDirectory()) {
                            headers.add(ContentType.HTML.toString());
                            StringBuilder result = new StringBuilder("<html><head><title>Index of ");
                            result.append(req.uri);
                            result.append("</title></head><body><h1>Index of ");
                            result.append(req.uri);
                            result.append("</h1><hr><pre>");

                            File[] files = file.listFiles();
                            for (File subfile : files) {
                                result.append(" <a href=\"" + subfile.getPath() + "\">" + subfile.getPath() + "</a>\n");
                            }
                            result.append("<hr></pre></body></html>");
                            fillResponse(result.toString());
                        } else if (file.exists()) {
                            setContentType(req.uri, headers);
                            fillResponse(getBytes(file));
                        } else {
                            log.info("File not found:1" + req.uri);
                            fillHeaders(Status._404);
                            fillResponse(Status._404.toString());
                        }
                    } catch (Exception e) {
                        log.error("Response Error", e);
                        fillHeaders(Status._400);
                        fillResponse(Status._400.toString());
                    }

                    break;
                case POST:
//                    String fileSeparator = System.getProperty("file.separator");
//                    String documentRoot = new HttpDConfig("httpd.conf").getConfigurationMap().get("DocumentRoot").get(0);
//                    FileWriter fstream = new FileWriter(documentRoot + fileSeparator + "post.html");// fileWriter will write to this
//                    BufferedWriter postToFile = new BufferedWriter(fstream);

//
                    break;
                case PUT:

                    break;
                case UNRECOGNIZED:
                    fillHeaders(Status._400);
                    fillResponse(Status._400.toString());
                    break;
                default:
                    fillHeaders(Status._501);
                    fillResponse(Status._501.toString());
            }
        }
    }

    private byte[] getBytes(File file) throws IOException {
        int length = (int) file.length();
        byte[] array = new byte[length];
        InputStream in = new FileInputStream(file);
        int offset = 0;
        while (offset < length) {
            int count = in.read(array, offset, (length - offset));
            offset += count;
        }
        in.close();
        return array;
    }

    private void fillHeaders(Status status) {
        headers.add(HttpResponse.VERSION + " " + status.toString());
        headers.add("Connection: close");
        headers.add("Server: My");
    }

    private void fillResponse(String response) {
        body = response.getBytes();
    }

    private void fillResponse(byte[] response) {
        body = response;
    }

    public void write(OutputStream os) throws IOException {

        DataOutputStream output = new DataOutputStream(os);
        for (String header : headers) {
            output.writeBytes(header + "\r\n");
        }
        output.writeBytes("\r\n");
        if (body != null) {
            output.write(body);
        }
        output.writeBytes("\r\n");
        output.flush();
    }

    private void setContentType(String uri, List<String> list) {
        try {
            String ext = uri.substring(uri.indexOf(".") + 1);
            MimeType mimeType = new MimeType("mime.types");
//			String str = ext.toLowerCase().toString()
            mimeType.getMimeTypesMap().get(ext.toLowerCase());
            list.add("Content-type: " + mimeType.getMimeTypesMap().get(ext.toLowerCase()));

//			list.add(ContentType.valueOf(ext.toUpperCase()).toString());
        } catch (Exception e) {
            log.error("ContentType not found: " + e, e);
        }
    }
    public Boolean checkAuthentication(HttpRequest req, OutputStream out) throws FileNotFoundException, IOException{
// check for Forbiden and Unauthorized

        return true;}
//        String fileSeparator = System.getProperty("file.separator");
//        String documentRoot = new HttpDConfig("httpd.conf").getConfigurationMap().get("DocumentRoot").get(0);
////        String documentRoot = getDocumentRoot();
//        String file_path = HttpDConfig.getAccessFileName() ;
//        if(file_path.contains("private")){
//            return false;
//        }
//
//        StringTokenizer auSt = new StringTokenizer(file_path);
//
//        String temp = auSt.nextToken("/");
//
//        while(auSt.hasMoreElements()){
//            if(authentication.authIsNeeded(temp)){
//
//                //check if user has already included authentication
//                if(true){
//                   // ok
//                    }
//                    else{
//                      //forbidden false
//
//                    fillHeaders(Status._401);
//                        write(out);//block User
//                    break;
//                    }
//                }
//
//                else{
//                //forbidden false
//                fillHeaders(Status._401);
//
//                System.out.println("Exception");
//
//                }
//            }
//            temp = temp +"/" + auSt.nextToken("/");
//        return false;
//        }
//        return true;
//    }}
}
