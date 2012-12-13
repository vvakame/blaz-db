package net.vvakame.blazdb.factory.template;

import java.io.IOException;

import javax.tools.JavaFileObject;

/**
 * Templating facility in general.
 * @author vvakame
 */
public class Template {

	private Template() {
	}

	/**
	 * Generates source code into the given file object from the given data model, utilizing the templating engine.
	 * @param fileObject Target file object
	 * @param model Data model for source code generation
	 * @throws IOException
	 * @author vvakame
	 */
	public static void writeGen(JavaFileObject fileObject, Object model) throws IOException {
		MvelTemplate.writeGen(fileObject, model);
	}
}
