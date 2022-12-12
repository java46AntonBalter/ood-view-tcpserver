package telran.net;

import java.net.*;
import java.io.*;

public class TcpClientServer implements Runnable {
	private static final int READ_TIMEOUT = 100;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ApplProtocol protocol;
	private TcpServer tcpServer;
	private static final int CLIENT_IDLE_TIMEOUT = 60000;
	private int idlePeriod;

	public TcpClientServer(Socket socket, ApplProtocol protocol, TcpServer tcpServer) throws Exception {
		this.protocol = protocol;
		this.socket = socket;
		this.socket.setSoTimeout(READ_TIMEOUT);
		this.tcpServer = tcpServer;
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		
		while (!tcpServer.isShutdown) {
			try {
				Request request = (Request) input.readObject();
				idlePeriod = 0;
				Response response = protocol.getResponse(request);
				output.writeObject(response);
			} catch (SocketTimeoutException e) {
				idlePeriod += READ_TIMEOUT;
				if (idlePeriod > CLIENT_IDLE_TIMEOUT &&
						tcpServer.clientsCounter.get() > tcpServer.nThreads) {
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
		if (tcpServer.isShutdown | idlePeriod > CLIENT_IDLE_TIMEOUT) {
			try {
				socket.close();
			} catch (IOException e1) {

			}
			if (tcpServer.isShutdown) {
				System.out.println("client connection closed by server shutdown");
			} else {
				System.out.println("client connection closed due to Idle Timeout");
			}

		}
		tcpServer.clientsCounter.getAndDecrement();
	}

}
