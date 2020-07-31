package com.projects.mandelbrotproject;
import static org.junit.Assert.*;

import org.junit.Test;


public class TestSequence {

	@Test
	public void testGetSpeed() {
		assertEquals(10, new Sequence(10).getSpeed(new Complex(0,0)));
		assertEquals(0, new Sequence(10).getSpeed(new Complex(2,2)));
	}

}
