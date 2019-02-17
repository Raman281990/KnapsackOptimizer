package com.maersk.knapsack.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.maersk.knapsack.annotations.ValidTaskRequest;
import com.maersk.knapsack.dto.Problem;

/** 
 * TaskRequestValidator validates the Problem entity and validates the
 * capacity, values and weights of the problem, it checks for neagtive integer and size of values and weights
 * 
 * @author Ramandeep kaur
 *
 */
@Component
public class TaskRequestValidator implements ConstraintValidator<ValidTaskRequest, Problem>{

	@Override
	public void initialize(ValidTaskRequest constraintAnnotation) {
	}

	@Override
	public boolean isValid(Problem value, ConstraintValidatorContext context) {
		boolean isValid = true;
		if(value.getCapacity() < 0 ) {
			isValid = false;
			context.buildConstraintViolationWithTemplate("Capacity must be greater than zero");
		}
		else if(Problem.hasNoValues(value)) {
			isValid = false;
			context.buildConstraintViolationWithTemplate("Values must be provided");
		}
		else if(Problem.hasNoWeights(value)){
			isValid = false;
			context.buildConstraintViolationWithTemplate("Weights must be provided");
		}
				
		else if(Problem.hasNegativeValues(value)){
			isValid = false;
			context.buildConstraintViolationWithTemplate("Only positive Values accepted");
		}
		else if(Problem.hasNegativeWeights(value)) {
			isValid= false;
			context.buildConstraintViolationWithTemplate("Only positive Weights accepted");
		}
		else if(Problem.hasMismatchedSizes(value)) {
			isValid = false;
			context.buildConstraintViolationWithTemplate("Values and Weights must be of the same size");
		}
		
		return isValid;
	}

	

}
