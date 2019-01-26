package com.wkcto.maven;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest {

	@Test
	public void testM1() {
		App app = new App();
		String actual = app.m1();
		String expected = "hello";
		assertEquals(expected, actual);
	}

}
