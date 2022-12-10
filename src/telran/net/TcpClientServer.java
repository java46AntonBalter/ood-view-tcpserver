package telran.net;

import java.net.*;
import java.time.Duration;
import java.time.Instant;
import java.io.*;

public class TcpClientServer implements Runnable {
	private static final int READ_TIMEOUT = 100;
	private static final int CLIENT_TIMEOUT = 5000;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ApplProtocol protocol;
	private TcpServer tcpServer;
	private Instant timeOfRequest;

	public TcpClientServer(Socket socket, ApplProtocol protocol, TcpServer tcpServer) throws Exception {
		this.protocol = protocol;
		this.socket = socket;
		this.socket.setSoTimeout(READ_TIMEOUT);
		this.tcpServer = tcpServer;
		this.timeOfRequest = Instant.now();
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());

		tcpServer.connectionsCounterDecrement();
	}

	@Override
	public void run() {

		while (!tcpServer.isShutdown) {
			try {
				Request request = (Request) input.readObject();
				timeOfRequest = Instant.now();
				Response response = protocol.getResponse(request);
				output.writeObject(response);
			} catch (SocketTimeoutException e) {
				if ((tcpServer.getConnectionsCounter() > 0)
						&& (Duration.between(timeOfRequest, Instant.now()).toMillis() >= CLIENT_TIMEOUT)) {
					System.out.println("client timed out and closed connection");
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
