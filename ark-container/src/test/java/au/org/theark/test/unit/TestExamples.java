package au.org.theark.test.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@ContextConfiguration(locations = {"file:src/test/resources/applicationContext.xml"})

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:*/applicationContext.xml"})
public class TestExamples {

	@Test
	public void testUnit() {
		System.out.println("This is a unit test");
	}
}
