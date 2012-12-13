package net.vvakame.blazdb.factory;

import net.vvakame.sample.blazmodel.PrimitiveTypeDataWithBlazModel;
import net.vvakame.sample.model.PrimitiveTypeDataWithModel;

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

		addCompilationUnit(PrimitiveTypeDataWithModel.class);
		addCompilationUnit(PrimitiveTypeDataWithBlazModel.class);

		compile();
		{
			@SuppressWarnings("unused")
			String source = getGeneratedSource(PrimitiveTypeDataWithModel.class.getName() + "Meta");
		}
		{
			@SuppressWarnings("unused")
			String source =
					getGeneratedSource(PrimitiveTypeDataWithBlazModel.class.getName() + "Meta");
		}
		assertThat(getCompiledResult(), is(true));
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addSourcePath("src/test/java");
		setCharset("utf-8");
	}
}
