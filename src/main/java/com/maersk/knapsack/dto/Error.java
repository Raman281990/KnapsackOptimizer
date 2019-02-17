package com.maersk.knapsack.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Error {
	
	@JsonInclude(Include.NON_NULL)
	String message;
	

	public Error() {
		super();
		
	}

	public Error(String message) {
		super();
		this.message = message;
	}


	public String getError() {
		return message;
	}

	public void setError(String message) {
		this.message = message;
	}
	

}
