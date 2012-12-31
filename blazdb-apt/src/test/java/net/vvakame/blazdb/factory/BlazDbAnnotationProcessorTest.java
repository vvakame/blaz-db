package net.vvakame.blazdb.factory;

import net.vvakame.sample.model.AllSuppotedTypeData;
import net.vvakame.sample.model.ConverterData;
import net.vvakame.sample.model.NotPrimitiveTypeData;
import net.vvakame.sample.model.PrimitiveTypeData;
import net.vvakame.sample.model.PrimitiveWrapperTypeData;

import org.junit.Test;
import org.seasar.aptina.unit.AptinaTestCase;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test case for {@link BlazDbAnnotationProcessor}.
 * 
 * @author vvakame
 */
public class BlazDbAnnotationProcessorTest extends AptinaTestCase {

	/**
	 * Test case.
	 * 
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForPrimitive() throws Exception {
		BlazDbAnnotationProcessor processor = new BlazDbAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(PrimitiveTypeData.class);

		compile();
		{
			@SuppressWarnings("unused")
			String source = getGeneratedSource(PrimitiveTypeData.class
					.getName() + "Meta");
		}
		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Test case.
	 * 
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForPrimitiveWrapper() throws Exception {
		BlazDbAnnotationProcessor processor = new BlazDbAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(PrimitiveWrapperTypeData.class);

		compile();
		{
			@SuppressWarnings("unused")
			String source = getGeneratedSource(PrimitiveWrapperTypeData.class
					.getName() + "Meta");
		}
		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Test case.
	 * 
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForOtherTypes() throws Exception {
		BlazDbAnnotationProcessor processor = new BlazDbAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(NotPrimitiveTypeData.class);
		addCompilationUnit(AllSuppotedTypeData.class);

		compile();
		{
			@SuppressWarnings("unused")
			String source = getGeneratedSource(NotPrimitiveTypeData.class
					.getName() + "Meta");
		}
		{
			@SuppressWarnings("unused")
			String source = getGeneratedSource(AllSuppotedTypeData.class
					.getName() + "Meta");
		}
		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Test case.
	 * 
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForConverter() throws Exception {
		BlazDbAnnotationProcessor processor = new BlazDbAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(ConverterData.class);

		compile();
		{
			@SuppressWarnings("unused")
			String source = getGeneratedSource(ConverterData.class.getName()
					+ "Meta");
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
