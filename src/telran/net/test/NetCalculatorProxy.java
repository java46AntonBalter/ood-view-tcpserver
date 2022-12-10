package telran.net.test;

import java.io.Closeable;
import java.io.IOException;

import telran.net.NetworkHandler;

public class NetCalculatorProxy implements Calculator, Closeable {
	private NetworkHandler networkHandler;
	

	@Override
	public void close() throws IOException {
		networkHandler.close();
	}

	@Override
	public double add(double op1, double op2) {
		Double[] data = {op1, op2};
		return networkHandler.send("add", data);
	}

	@Override
	public double subtract(double op1, double op2) {
		Double[] data = {op1, op2};
		return networkHandler.send("subtract", data);
	}

	@Override
	public double divide(double op1, double op2) {
		Double[] data = {op1, op2};
		return networkHandler.send("divide", data);
	}

	@Override
	public double multiply(double op1, double op2) {
		Double[] data = {op1, op2};
		return networkHandler.send("multiply", data);
	}

	public NetCalculatorProxy(NetworkHandler networkHandler) {
		this.networkHandler = networkHandler;
	}

}
