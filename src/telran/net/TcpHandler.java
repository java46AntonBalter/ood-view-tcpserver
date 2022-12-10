package telran.net;

import java.io.*;
import java.net.*;
public class TcpHandler implements NetworkHandler {
private Socket socket;
private ObjectOutputStream output;
private ObjectInputStream input;
public TcpHandler(String hostname, int port) throws Exception{
	socket = new Socket(hostname, port);
	output = new ObjectOutputStream(socket.getOutputStream());
	input = new ObjectInputStream(socket.getInputStream());
}
	@SuppressWarnings("unchecked")
	@Override
	public <T> T send(String requestType, Serializable requestData) {
		Request request = new Request(requestType, requestData);
		try {
			output.writeObject(request);
			Response response = (Response) input.readObject();
			if (response.code != ResponseCode.OK) {
				throw new Exception(response.responseData.toString());
			}
			return (T) response.responseData;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}

}
