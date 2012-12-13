package net.vvakame.blazdb.factory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import net.vvakame.blaz.annotation.BlazModel;
import net.vvakame.blaz.annotation.Model;
import static javax.lang.model.util.ElementFilter.*;

/**
 * Annotation processing logic.
 * @author vvakame
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("net.vvakame.blaz.annotation.*")
public class BlazDbAnnotationProcessor extends AbstractProcessor {

	private static final String CLASS_POSTFIX_OPTION = "BlazDbClassPostfix";

	private static final String DEBUG_OPTION = "BlazDbDebug";


	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		Log.init(processingEnv.getMessager());

		String debug = getOption(DEBUG_OPTION);
		if ("true".equalsIgnoreCase(debug)) {
			Log.setDebug(true);
		} else {
			Log.setDebug(false);
		}

		Log.d("init BlazDbAnotationProcessor");
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		ModelGenerator.init(processingEnv);

		Log.d("start process method.");

		final String postfix;
		{
			String optPostfix = getOption(CLASS_POSTFIX_OPTION);
			if (optPostfix == null) {
				postfix = "Meta";
			} else {
				postfix = optPostfix;
			}
		}

		Set<TypeElement> models = new HashSet<TypeElement>();
		models.addAll(typesIn(roundEnv.getElementsAnnotatedWith(Model.class)));
		models.addAll(typesIn(roundEnv.getElementsAnnotatedWith(BlazModel.class)));

		for (Element element : models) {

			Log.d("process " + element.toString());

			ModelGenerator generater = ModelGenerator.from(element, postfix);
			if (generater.isEncountError()) {
				continue;
			}
			try {
				generater.write();
			} catch (IOException e) {
				Log.e(e);
			}
		}

		return true;
	}

	String getOption(String key) {
		Map<String, String> options = processingEnv.getOptions();
		if (options.containsKey(key) && !"".equals(options.get(key))) {
			return options.get(key);
		} else {
			return null;
		}
	}
}
