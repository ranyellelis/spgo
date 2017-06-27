package br.com.ranyel.estrutura.exception;

public class NtpException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NtpException() {
		super();
	}

	public NtpException(String message, Throwable cause) {
		super(message, cause);
	}

	public NtpException(String message) {
		super(message);
	}

	public NtpException(Throwable cause) {
		super(cause);
	}
}