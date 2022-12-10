package telran.net;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 1L;
	public ResponseCode code;
	public Serializable responseData;
	public Response(ResponseCode code, Serializable responseData) {
		this.code = code;
		this.responseData = responseData;
	}
	

}
