package net.vvakame.blazdb.factory;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import net.vvakame.blaz.annotation.BlazModel;
import net.vvakame.blaz.annotation.Model;
import net.vvakame.blazdb.factory.template.Template;
import static net.vvakame.apt.AptUtil.*;

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

	ModelModel model = new ModelModel();


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

		generator.process();

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

	void process() {
		{
			Model m1 = classElement.getAnnotation(Model.class);
			BlazModel m2 = classElement.getAnnotation(BlazModel.class);
			if (m1 == null && m2 == null) {
				Log.e("Not exists any @Model or @BlazModel decorated.", classElement);
				encountError = true;
			} else if (m1 != null && m2 != null) {
				Log.e("Do not use annotation @Model and @BlazModel at once", classElement);
				encountError = true;
			} else if (m1 != null) {
				processModel(m1);
			} else {
				processBlazModel(m2);
			}
		}

		// TODO attribute
	}

	void processModel(Model model) {
		{
			this.model.setPackageName(getPackageName(elementUtils, classElement));
			this.model.setTarget(getSimpleName(classElement));
			this.model.setTargetNew(getNameForNew(classElement));

			TypeElement superClass = getSuperClassElement(classElement);
			if (superClass.getAnnotation(model.annotationType()) != null) {
				this.model.setTargetBase(getFullQualifiedName(superClass));
				this.model.setExistsBase(true);
			}

			this.model.setPostfix(postfix);
		}
		{
			String kind = model.kind();
			if ("".equals(kind)) {
				kind = getSimpleName(classElement);
			}
			if ("".equals(kind)) {
				Log.e("kind parameter is empty string", classElement);
				encountError = true;
			}
			int schemaVersion = model.schemaVersion();
			String schemaVersionName = model.schemaVersionName();
			if ("".equals(schemaVersionName)) {
				Log.e("schemaVersionName parameter is empty string", classElement);
				encountError = true;
			}

			this.model.setKind(kind);
			this.model.setSchemaVersion(schemaVersion);
			this.model.setSchemaVersionName(schemaVersionName);
		}
	}

	void processBlazModel(BlazModel model) {
		{
			this.model.setPackageName(getPackageName(elementUtils, classElement));
			this.model.setTarget(getSimpleName(classElement));
			this.model.setTargetNew(getNameForNew(classElement));

			TypeElement superClass = getSuperClassElement(classElement);
			if (superClass.getAnnotation(model.annotationType()) != null) {
				this.model.setTargetBase(getFullQualifiedName(superClass));
				this.model.setExistsBase(true);
			}

			this.model.setPostfix(postfix);
		}
		{
			String kind = model.kind();
			if ("".equals(kind)) {
				kind = getSimpleName(classElement);
			}
			if ("".equals(kind)) {
				Log.e("kind parameter is empty string", classElement);
				encountError = true;
			}
			int schemaVersion = model.schemaVersion();
			String schemaVersionName = model.schemaVersionName();
			if ("".equals(schemaVersionName)) {
				Log.e("schemaVersionName parameter is empty string", classElement);
				encountError = true;
			}

			this.model.setKind(kind);
			this.model.setSchemaVersion(schemaVersion);
			this.model.setSchemaVersionName(schemaVersionName);
		}
	}

	/**
	 * Generates the source code.
	 * @throws IOException
	 * @author vvakame
	 */
	public void write() throws IOException {
		Filer filer = processingEnv.getFiler();
		String generateClassName = model.getPackageName() + "." + model.getTarget() + postfix;
		JavaFileObject fileObject = filer.createSourceFile(generateClassName, classElement);
		Template.writeGen(fileObject, model);
	}

	/**
	 * @return the encountError
	 */
	public boolean isEncountError() {
		return encountError;
	}
}
