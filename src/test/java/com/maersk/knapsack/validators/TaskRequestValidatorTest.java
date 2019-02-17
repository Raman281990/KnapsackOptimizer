package com.maersk.knapsack.validators;

import java.util.Arrays;

import javax.validation.ConstraintValidatorContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.maersk.knapsack.dto.Problem;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class TaskRequestValidatorTest {
	
	@InjectMocks
	TaskRequestValidator taskRequestValidator;
	
	@Test
	public void testIsValid() {
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, 3, 30));
		problem.setWeights(Arrays.asList(10, 20, 33));
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		boolean response = taskRequestValidator.isValid(problem, context);
		Assert.assertTrue(response);
	}
	@Test
	public void testForNegativeCapacityValue() {
		Problem problem = new Problem();
		problem.setCapacity(-1);
		problem.setValues(Arrays.asList(10, 3, 30));
		problem.setWeights(Arrays.asList(10, 20, 33));
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		boolean response = taskRequestValidator.isValid(problem, context);
		Assert.assertFalse(response);
	}
	
	@Test
	public void testForNegativeWeights() {
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, 3, 30));
		problem.setWeights(Arrays.asList(-10, 20, 33));
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		boolean response = taskRequestValidator.isValid(problem, context);
		Assert.assertFalse(response);
	}
	@Test
	public void testForNegativeValues() {
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, -3, 30));
		problem.setWeights(Arrays.asList(10, 20, 33));
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		boolean response = taskRequestValidator.isValid(problem, context);
		Assert.assertFalse(response);
	}
	
	@Test
	public void testForUnEqualWeightsAndValue() {
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, 3, 30,40));
		problem.setWeights(Arrays.asList(10, 20, 33));
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		boolean response = taskRequestValidator.isValid(problem, context);
		Assert.assertFalse(response);
	}
	
	@Test
	public void testForNullValues() {
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(null);
		problem.setWeights(Arrays.asList(10, 20, 33));
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		boolean response = taskRequestValidator.isValid(problem, context);
		Assert.assertFalse(response);
	}
	@Test
	public void testForNullWeights() {
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, 3, 30,40));
		problem.setWeights(null);
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		boolean response = taskRequestValidator.isValid(problem, context);
		Assert.assertFalse(response);
	}
	@Test
	public void testForEmptyValues() {
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList());
		problem.setWeights(Arrays.asList(10, 20, 33));
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		boolean response = taskRequestValidator.isValid(problem, context);
		Assert.assertFalse(response);
	}
	@Test
	public void testForEmptyWeights() {
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, 3, 30,40));
		problem.setWeights(Arrays.asList());
		ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
		boolean response = taskRequestValidator.isValid(problem, context);
		Assert.assertFalse(response);
	}

}
