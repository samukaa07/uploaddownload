package com.example.uploaddownload.exception;

public class FileStorageException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// construtores
	public FileStorageException(String message) { //referenciando classe pai
		super(message);
		
	}
	
	// novo construtor para observar a causa da exceção
	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
		
	}
	
}
