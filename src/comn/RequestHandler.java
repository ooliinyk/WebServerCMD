package comn;



import java.net.Socket;

/**
 * Class <code>RequestHandler</code> - Thread class that answer the requests in the socket
 */
public class RequestHandler implements Runnable {

	private static Logger log = Logger.getLogger(RequestHandler.class);

	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			HttpRequest req = new HttpRequest(socket.getInputStream());
			HttpResponse res = new HttpResponse(req,socket.getOutputStream());
			res.write(socket.getOutputStream());
			socket.close();
		} catch (Exception e) {
			log.error("Runtime Error", e);
		}
	}
}
