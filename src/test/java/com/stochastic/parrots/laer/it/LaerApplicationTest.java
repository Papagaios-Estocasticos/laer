package com.stochastic.parrots.laer.it;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class LaerApplicationTest {

	@Test
	public void loadContext() {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext();

		assertThat(applicationContext, is(not(nullValue())));
	}
}
