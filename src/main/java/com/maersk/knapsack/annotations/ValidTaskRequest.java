package com.maersk.knapsack.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.maersk.knapsack.validators.TaskRequestValidator;

/** ValidTaskRequest annotation validates the 
 *  input request for task submission
 *  
 * @author Ramandeep kaur
 *
 */
@Constraint(validatedBy = { TaskRequestValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface ValidTaskRequest {
	
	String message() default "Invalid Task Request";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
