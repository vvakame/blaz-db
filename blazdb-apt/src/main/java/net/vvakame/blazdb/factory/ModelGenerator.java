package net.vvakame.blazdb.factory;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import net.vvakame.blazdb.factory.template.Template;
import net.vvakame.sample.PrimitiveTypeData;

/**
 * Annotation processor.
 * @author vvakame
 */
public class ModelGenerator {

	static ProcessingEnvironment processingEnv = null;

	static Types typeUtils;

	static Elements elementUtils;

	final Element classElement;

	final String postfix;

	boolean encountError = false;


	/**
	 * Initialization.
	 * @param env
	 * @author vvakame
	 */
	public static void init(ProcessingEnvironment env) {
		processingEnv = env;
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
	}

	/**
	 * Process {@link Element} to generate.
	 * @param element
	 * @param classNamePostfix 
	 * @return {@link ModelGenerator}
	 * @author vvakame
	 */
	public static ModelGenerator from(Element element, String classNamePostfix) {
		ModelGenerator generator = new ModelGenerator(element, classNamePostfix);

		// do process

		return generator;
	}

	/**
	 * the constructor.
	 * @param element
	 * @param classNamePostfix 
	 * @category constructor
	 */
	ModelGenerator(Element element, String classNamePostfix) {
		classElement = element;
		postfix = classNamePostfix;
	}

	/**
	 * Generates the source code.
	 * @throws IOException
	 * @author vvakame
	 */
	public void write() throws IOException {
		Filer filer = processingEnv.getFiler();
		// TODO
		String generateClassName = PrimitiveTypeData.class.getName() + postfix;
		JavaFileObject fileObject = filer.createSourceFile(generateClassName, classElement);
		Template.writeGen(fileObject, null);
	}

	/**
	 * @return the encountError
	 */
	public boolean isEncountError() {
		return encountError;
	}
}
