package test.jirareporting.prototyping;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractTest {

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@org.junit.jupiter.api.BeforeEach
	public void setUp() throws Exception {
		System.out.println("before each");
	}

	@org.junit.jupiter.api.AfterEach
	public void tearDown() throws Exception {
		System.out.println("after each");
	}

	@Test
	public void test() throws Exception {

		try {

			System.out.println("inside test()");
			
			this.testHook();

		} catch (Exception e){
			System.out.println(this.getClass().getSimpleName() + " : I'm an exception!");
			throw new Exception(e);
		} catch (Error e){
			System.out.println(this.getClass().getSimpleName() + " : I'm an error!");
			throw new Error(e);
		}

	}

	protected abstract void testHook() throws Exception;

}
