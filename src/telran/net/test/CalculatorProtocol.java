package telran.net.test;

import java.io.Serializable;
import java.lang.reflect.Method;
import telran.net.ApplProtocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CalculatorProtocol implements ApplProtocol {
	Calculator calculator;

	public CalculatorProtocol(Calculator calculator) {
		this.calculator = calculator;
	}

	@Override
	public Response getResponse(Request request) {
		Response response;

		try {
			Method method = this.getClass().getDeclaredMethod(request.requestType, Double[].class);
			method.setAccessible(true);
			Double[] args = getArguments(request.requestData);
			response = (Response) method.invoke(this, new Object[] {args});
		} catch (Exception e) {
			response = new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
		return response;
	}

	private Double[] getArguments(Serializable requestData) throws Exception {
		try {
			Double[] res = (Double[]) requestData;
			if (res.length != 2) {
				throw new Exception("no two operands");
			}
			return res;
		} catch (ClassCastException e) {
			throw new Exception("no array of doubles");
		}

	}

	Response add(Double[] operands) {
		Double res = calculator.add(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}

	Response subtract(Double[] operands) {
		Double res = calculator.subtract(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}

	Response divide(Double[] operands) {
		Double res = calculator.divide(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}

	Response multiply(Double[] operands) {
		Double res = calculator.multiply(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}

}
