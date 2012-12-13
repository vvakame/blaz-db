package net.vvakame.blazdb.factory;

import java.io.IOException;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.annotation.Attribute;
import net.vvakame.blaz.annotation.BlazAttribute;
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
		{
			processAttributes();
		}
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

	void processAttributes() {
		List<Element> enclosedElements = getEnclosedElementsByKind(classElement, ElementKind.FIELD);

		// TODO static field

		// detect primary key
		Element key = findPrimaryKeyByAnnotation(enclosedElements);

		if (key == null) {
			key = findPrimaryKeyByType(enclosedElements);
		}
		if (key == null) {
			Log.e("primary key not exists. create Key property or use @Attribug(primaryKey=true)",
					classElement);
			encountError = true;
		}

		enclosedElements.remove(key);

		{
			AttributeModel keyModel = processAttribute(key);
			keyModel.setPrimaryKey(true);
			model.setPrimaryKey(keyModel);
		}

		// process attributes exclude pk
		for (Element element : enclosedElements) {
			AttributeModel attrModel = processAttribute(element);
			if (attrModel == null) {
				continue;
			}
			model.getAttributes().add(attrModel);
		}
	}

	AttributeModel processAttribute(Element element) {
		AttributeModel attrModel = new AttributeModel();
		{
			Attribute a1 = element.getAnnotation(Attribute.class);
			BlazAttribute a2 = element.getAnnotation(BlazAttribute.class);

			if (a1 != null && a2 != null) {
				Log.e("Do not use annotation @Model and @BlazModel at once", classElement);
				encountError = true;
			} else if (a1 != null) {
				processAttribute(a1, attrModel);
			} else if (a2 != null) {
				processAttribute(a2, attrModel);
			}
		}
		if (attrModel.isPersistent()) {
			return null;
		}

		String simpleName = element.getSimpleName().toString();
		if (attrModel.getName() == null || "".equals(attrModel.getName())) {
			attrModel.setName(simpleName);
		}

		String getter = getElementGetter(element);
		String setter = getElementSetter(element);
		attrModel.setGetter(getter);
		attrModel.setSetter(setter);

		String typeName = getFullQualifiedName(element.asType());
		attrModel.setTypeNameFQN(typeName);
		if (isPrimitive(element)) {
			TypeElement wrapper = toPrimitiveWrapper(elementUtils, element);
			attrModel.setPrimitiveWrapper(wrapper.asType().toString());
		}

		return attrModel;
	}

	void processAttribute(Attribute attr, AttributeModel attrModel) {
		attrModel.setName(attr.name());
		attrModel.setPersistent(attr.persistent());
	}

	void processAttribute(BlazAttribute attr, AttributeModel attrModel) {
		attrModel.setName(attr.name());
		attrModel.setPersistent(attr.persistent());
	}

	Element findPrimaryKeyByAnnotation(List<Element> enclosedElements) {
		Element key = null;
		for (Element element : enclosedElements) {
			{
				Attribute attribute = element.getAnnotation(Attribute.class);
				if (attribute != null && attribute.primaryKey()) {
					if (key != null) {
						Log.e("primary key was duplicated.", key);
						Log.e("primary key was duplicated.", element);
						encountError = true;
						break;
					}
					key = element;
				}
			}
			{
				BlazAttribute attribute = element.getAnnotation(BlazAttribute.class);
				if (attribute != null && attribute.primaryKey()) {
					if (key != null) {
						Log.e("primary key was duplicated.", key);
						Log.e("primary key was duplicated.", element);
						encountError = true;
						break;
					}
					key = element;
				}
			}
		}
		return key;
	}

	Element findPrimaryKeyByType(List<Element> enclosedElements) {
		Element key = null;
		for (Element element : enclosedElements) {
			if (isKeyElement(element)) {
				if (key != null) {
					Log.e("primary key was duplicated.", key);
					Log.e("primary key was duplicated.", element);
					encountError = true;
					break;
				}
				key = element;
			}
		}
		return key;
	}

	boolean isKeyElement(Element element) {
		TypeElement keyTypeElement = elementUtils.getTypeElement(Key.class.getCanonicalName());
		TypeMirror keyType = keyTypeElement.asType();

		TypeMirror type = element.asType();
		return typeUtils.isSameType(keyType, type);
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
