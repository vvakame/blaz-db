package net.vvakame.blazdb.factory;

import net.vvakame.sample.PrimitiveTypeData;

import org.junit.Test;
import org.seasar.aptina.unit.AptinaTestCase;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test case for {@link BlazDbAnnotationProcessor}.
 * @author vvakame
 */
public class BlazDbAnnotationProcessorTest extends AptinaTestCase {

	/**
	 * Test case.
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForPrimitive() throws Exception {
		BlazDbAnnotationProcessor processor = new BlazDbAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(PrimitiveTypeData.class);

		compile();
		@SuppressWarnings("unused")
		String source = getGeneratedSource(PrimitiveTypeData.class.getName() + "Meta");
		assertThat(getCompiledResult(), is(true));
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addSourcePath("src/test/java");
		setCharset("utf-8");
	}
}
