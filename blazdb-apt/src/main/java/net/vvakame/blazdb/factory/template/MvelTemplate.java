package net.vvakame.blazdb.factory.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import javax.tools.JavaFileObject;

import net.vvakame.blazdb.factory.Log;
import net.vvakame.blazdb.factory.model.ModelModel;

import org.mvel2.templates.TemplateRuntime;

/**
 * MVEL templating facility.
 * 
 * @author vvakame
 */
public class MvelTemplate {

	private MvelTemplate() {
	}

	/**
	 * Generates source code into the given file object from the given data
	 * model, utilizing the templating engine.
	 * 
	 * @param fileObject
	 *            Target file object
	 * @param model
	 *            Data model for source code generation
	 * @throws IOException
	 * @author vvakame
	 */
	public static void writeGen(JavaFileObject fileObject, ModelModel model)
			throws IOException {
		Writer writer = fileObject.openWriter();
		String generated = (String) TemplateRuntime.eval(
				getTemplateString("BlazModelMeta.java.mvel"), model);
		writer.write(generated);
		writer.flush();
		writer.close();
	}

	static String getTemplateString(String resourceName) {
		InputStream stream = MvelTemplate.class.getClassLoader()
				.getResourceAsStream(resourceName);
		try {
			String template = streamToString(stream);
			// Log.d(template);
			return template;
		} catch (IOException e) {
			Log.e(e);
			return null;
		}
	}

	static String streamToString(InputStream is) throws IOException {
		if (is == null) {
			Log.e("not expected null value.");
			throw new IllegalStateException("not expected null value.");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				"utf-8"));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			builder.append(line).append("\n");
		}

		return builder.toString();
	}
}
