package telran.net;

import java.net.*;
import java.io.*;

public class TcpClientServer implements Runnable {
	private static final int READ_TIMEOUT = 100;
	private static final int CLIENT_IDLE_TIMEOUT = 5000;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ApplProtocol protocol;
	private TcpServer tcpServer;
	private int timeOfRequest;

	public TcpClientServer(Socket socket, ApplProtocol protocol, TcpServer tcpServer) throws Exception {
		this.protocol = protocol;
		this.socket = socket;
		this.socket.setSoTimeout(READ_TIMEOUT);
		this.tcpServer = tcpServer;
		this.timeOfRequest = 0;
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {

		while (!tcpServer.isShutdown) {
			tcpServer.connectionsCounterDecrement();
			try {
				Request request = (Request) input.readObject();
				timeOfRequest = 0;
				Response response = protocol.getResponse(request);
				output.writeObject(response);
			} catch (SocketTimeoutException e) {
				timeOfRequest += READ_TIMEOUT;
				if ((tcpServer.getConnectionsCounter() > 0) && (timeOfRequest >= CLIENT_IDLE_TIMEOUT)) {
					try {
						System.out.println("client timed out and closed connection");
						socket.close();
					} catch (IOException e1) {
						
					}
					break;
				}

			} catch (EOFException e) {
				System.out.println("client closed connection");
				break;
			} catch (Exception e) {
				System.out.println("abnormal closing connection " + e.getMessage());
				break;
			}
		}
		if (tcpServer.isShutdown) {
			System.out.println("client connection closed by server shutdown");
			try {
				socket.close();
			} catch (IOException e1) {

			}
		}
	}

}
