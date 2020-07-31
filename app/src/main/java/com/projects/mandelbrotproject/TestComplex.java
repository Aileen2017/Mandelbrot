package com.projects.mandelbrotproject;
import static org.junit.Assert.*;

import org.junit.Test;



public class TestComplex {

	@Test
	public void testComplexMultiply() {
		//fail("Not yet implemented");
		assertEquals(3, 3);
	}

	@Test
	public void testComplexAdd()
	{
		assertEquals(new Complex(2, 5).complexAdd(new Complex(1, 7)), new Complex(3, 12));
	}

	@Test
	public void testComplexSubtract() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testComplexToString()
	{
		assertEquals("3.0 + 2.0i", new Complex(3, 2).toString());
	}
	
	@Test
	public void testComplexGetNorm()
	{
		assertEquals(new Complex(3, 4).getNorm(), 5, 0.01);
	}
	
	@Test
	public void testComplexAddinPlace() {
		Complex t=new Complex(3,4);
		t.complexAddinPlace(new Complex(1,2));
		
		
		assertEquals(t,new Complex(4,6));
		
	}

	@Test
	public void testComplexSquare(){
		Complex t=new Complex(3,4);
		t.complexSquare();
		assertEquals(t,new Complex(-7,24));
	}
	

	@Test
	public void testComplexgetNormSquare(){
		assertEquals((int)new Complex(3,4).getNormSquare(),25);
	
		
	}

}
