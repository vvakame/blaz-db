package net.vvakame.blazdb.factory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import net.vvakame.apt.AptUtil;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.PropertyConverter;
import net.vvakame.blaz.annotation.Attribute;
import net.vvakame.blaz.annotation.BlazAttribute;
import net.vvakame.blaz.annotation.BlazModel;
import net.vvakame.blaz.annotation.Model;
import net.vvakame.blazdb.factory.model.AttributeDelegate;
import net.vvakame.blazdb.factory.model.AttributeModel;
import net.vvakame.blazdb.factory.model.ModelDelegate;
import net.vvakame.blazdb.factory.model.ModelModel;
import net.vvakame.blazdb.factory.template.Template;
import static net.vvakame.apt.AptUtil.*;

/**
 * Annotation processor.
 * 
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
	 * 
	 * @param env
	 * @author vvakame
	 */
	public static void init(ProcessingEnvironment env) {
		processingEnv = env;
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		AptUtil.init(elementUtils, typeUtils);
	}

	/**
	 * Process {@link Element} to generate.
	 * 
	 * @param element
	 * @param classNamePostfix
	 * @return {@link ModelGenerator}
	 * @author vvakame
	 */
	public static ModelGenerator from(Element element, String classNamePostfix) {
		ModelGenerator generator = new ModelGenerator(element, classNamePostfix);

		generator.processModel();
		generator.processAttributes();

		return generator;
	}

	/**
	 * the constructor.
	 * 
	 * @param element
	 * @param classNamePostfix
	 * @category constructor
	 */
	ModelGenerator(Element element, String classNamePostfix) {
		classElement = element;
		postfix = classNamePostfix;
	}

	void processModel() {
		ModelDelegate model = getModelAnnotation(classElement);
		{
			this.model.setPackageName(getPackageName(classElement));
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
				Log.e("schemaVersionName parameter is empty string",
						classElement);
				encountError = true;
			}

			this.model.setKind(kind);
			this.model.setSchemaVersion(schemaVersion);
			this.model.setSchemaVersionName(schemaVersionName);
		}
	}

	void processAttributes() {
		List<Element> enclosedElements = getEnclosedElementsByKind(
				classElement, ElementKind.FIELD);

		// exclude static field
		enclosedElements = filterByNonStaticMember(enclosedElements);

		// detect primary key
		Element key = findPrimaryKeyByAnnotation(enclosedElements);

		if (key == null) {
			key = findPrimaryKeyByType(enclosedElements);
		}
		if (key == null) {
			Log.e("primary key not exists. create Key property or use @Attribug(primaryKey=true)",
					classElement);
			encountError = true;
			return;
		}

		enclosedElements.remove(key);

		{
			AttributeModel keyModel = getAttributeModel(key.asType(), key,
					Kind.KEY);
			keyModel.setPrimaryKey(true);
			model.setPrimaryKey(keyModel);
		}

		// process attributes exclude pk
		for (Element element : enclosedElements) {
			AttributeDelegate attr = getAttributeAnnotation(element);
			if (attr != null && attr.converter() != null) {
				AttributeModel attrModel = getAttributeModel(element.asType(),
						element, Kind.CONVERTER);
				model.getAttributes().add(attrModel);
				continue;
			}

			AttributeModel attrModel = element.asType().accept(
					new ValueExtractVisitor(), element);
			if (attrModel != null) {
				model.getAttributes().add(attrModel);
				continue;
			}
		}
	}

	List<Element> filterByNonStaticMember(List<Element> enclosedElements) {
		List<Element> newElementList = new ArrayList<Element>();
		for (Element element : enclosedElements) {
			if (isStatic(element)) {
				continue;
			}
			newElementList.add(element);
		}
		return newElementList;
	}

	Element findPrimaryKeyByAnnotation(List<Element> enclosedElements) {
		Element key = null;
		for (Element element : enclosedElements) {
			AttributeDelegate attribute = getAttributeAnnotation(element);
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
		TypeElement keyTypeElement = elementUtils.getTypeElement(Key.class
				.getCanonicalName());
		TypeMirror keyType = keyTypeElement.asType();

		TypeMirror type = element.asType();
		return typeUtils.isSameType(keyType, type);
	}

	boolean isStringElement(Element element) {
		TypeElement string = elementUtils.getTypeElement(String.class
				.getCanonicalName());
		return typeUtils.isSameType(string.asType(), element.asType());
	}

	boolean isByteArrayElement(Element element) {
		return "byte[]".equals(element.asType().toString());
	}

	AttributeDelegate getAttributeAnnotation(final Element element) {
		{
			Attribute a1 = element.getAnnotation(Attribute.class);
			BlazAttribute a2 = element.getAnnotation(BlazAttribute.class);

			if (a1 != null && a2 != null) {
				Log.e("Do not use annotation @Model and @BlazModel at once",
						classElement);
				encountError = true;
				return null;
			}
		}
		{
			final Attribute annotation = element.getAnnotation(Attribute.class);
			if (annotation != null) {
				return new AttributeDelegate() {

					@Override
					public boolean primaryKey() {
						return annotation.primaryKey();
					}

					@Override
					public boolean persistent() {
						return annotation.persistent();
					}

					@Override
					public String name() {
						return annotation.name();
					}

					@Override
					public Class<? extends Annotation> annotationType() {
						return annotation.annotationType();
					}

					@Override
					public TypeMirror converter() {
						return getConverter(element);
					}
				};
			}
		}
		{
			final BlazAttribute annotation = element
					.getAnnotation(BlazAttribute.class);
			if (annotation != null) {
				return new AttributeDelegate() {

					@Override
					public boolean primaryKey() {
						return annotation.primaryKey();
					}

					@Override
					public boolean persistent() {
						return annotation.persistent();
					}

					@Override
					public String name() {
						return annotation.name();
					}

					@Override
					public Class<? extends Annotation> annotationType() {
						return annotation.annotationType();
					}

					@Override
					public TypeMirror converter() {
						return getConverter(element);
					}
				};
			}
		}
		return null;
	}

	TypeMirror getConverter(Element el) {

		AnnotationValue converter = null;

		for (AnnotationMirror am : el.getAnnotationMirrors()) {
			Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = am
					.getElementValues();
			for (ExecutableElement e : elementValues.keySet()) {
				if ("converter".equals(e.getSimpleName().toString())) {
					converter = elementValues.get(e);
				}
			}
		}

		if (converter != null) {
			return (TypeMirror) converter.getValue();
		} else {
			return null;
		}
	}

	ModelDelegate getModelAnnotation(Element element) {
		{
			Model m1 = element.getAnnotation(Model.class);
			BlazModel m2 = element.getAnnotation(BlazModel.class);
			if (m1 == null && m2 == null) {
				Log.e("Not exists any @Model or @BlazModel decorated.", element);
				encountError = true;
				return null;
			} else if (m1 != null && m2 != null) {
				Log.e("Do not use annotation @Model and @BlazModel at once",
						element);
				encountError = true;
				return null;
			}
		}
		{
			final Model annotation = element.getAnnotation(Model.class);
			if (annotation != null) {
				return new ModelDelegate() {

					@Override
					public String schemaVersionName() {
						return annotation.schemaVersionName();
					}

					@Override
					public int schemaVersion() {
						return annotation.schemaVersion();
					}

					@Override
					public String kind() {
						return annotation.kind();
					}

					@Override
					public Class<? extends Annotation> annotationType() {
						return annotation.annotationType();
					}
				};
			}
		}
		{
			final BlazModel annotation = element.getAnnotation(BlazModel.class);
			if (annotation != null) {
				return new ModelDelegate() {

					@Override
					public String schemaVersionName() {
						return annotation.schemaVersionName();
					}

					@Override
					public int schemaVersion() {
						return annotation.schemaVersion();
					}

					@Override
					public String kind() {
						return annotation.kind();
					}

					@Override
					public Class<? extends Annotation> annotationType() {
						return annotation.annotationType();
					}
				};
			}
		}
		return null;
	}

	AttributeModel getAttributeModel(TypeMirror t, Element el, Kind kind) {
		if (kind == null) {
			Log.e("invalid state. this is APT bugs.");
			encountError = true;
			return null;
		}

		AttributeModel attrModel = new AttributeModel();
		{
			AttributeDelegate attr = getAttributeAnnotation(el);
			if (attr != null && !attr.persistent()) {
				return null;
			}

			if (attr != null) {
				attrModel.setName(attr.name());
				attrModel.setPersistent(attr.persistent());
			}

			if (attr != null && attr.converter() != null) {
				TypeElement converter = toTypeElement(toDeclaredType(attr
						.converter()));
				attrModel.setConverterFQN(getFullQualifiedName(converter));

				final List<? extends TypeMirror> typeArguments = getTypeArgumentsAtType(
						converter, PropertyConverter.class);
				{
					final TypeMirror parameterType = typeArguments.get(0);

					DeclaredType collection = typeUtils.getDeclaredType(
							toTypeElement(Collection.class), parameterType);
					if (typeUtils.isAssignable(t, parameterType)) {
						attrModel.setConverterType(ConverterType.SINGLE);
					} else if (typeUtils.isAssignable(t, collection)) {
						attrModel.setConverterType(ConverterType.COLLECTION);
					} else {
						// other
						attrModel.setConverterType(ConverterType.UNKNOWN);
					}
					attrModel
							.setConverterParameterTypeWithGenerics(parameterType
									.toString());
					attrModel
							.setConverterParameterType(getFullQualifiedName(parameterType));
				}
				{
					final TypeMirror returnType = typeArguments.get(1);
					attrModel.setConverterReturnTypeWithGenerics(returnType
							.toString());
					attrModel
							.setConverterReturnType(getFullQualifiedName(returnType));
				}
			}
		}

		String simpleName = el.getSimpleName().toString();
		if (attrModel.getName() == null || "".equals(attrModel.getName())) {
			attrModel.setName(simpleName);
		}

		String getter = getElementGetter(el);
		String setter = getElementSetter(el);
		attrModel.setGetter(getter);
		attrModel.setSetter(setter);

		attrModel.setTypeNameFQNWithGenerics(t.toString());
		String fqn = getFullQualifiedName(t);
		attrModel.setTypeNameFQN(fqn);

		attrModel.setKind(kind);

		switch (kind) {
		case BYTE:
		case SHORT:
		case INT:
		case LONG: {
			TypeElement wrapper = elementUtils.getTypeElement(Long.class
					.getCanonicalName());
			attrModel.setCastTo(wrapper.asType().toString());
		}
			break;
		case BYTE_WRAPPER:
		case SHORT_WRAPPER:
		case INT_WRAPPER:
		case LONG_WRAPPER: {
			PrimitiveType primitive = toPrimitive(el);
			attrModel.setCastTo(primitive.toString());
		}
			break;
		case FLOAT:
		case DOUBLE: {
			TypeElement wrapper = elementUtils.getTypeElement(Double.class
					.getCanonicalName());
			attrModel.setCastTo(wrapper.asType().toString());
		}
			break;
		case FLOAT_WRAPPER:
		case DOUBLE_WRAPPER: {
			PrimitiveType primitive = toPrimitive(el);
			attrModel.setCastTo(primitive.toString());
		}
			break;
		case BOOLEAN: {
			TypeElement wrapper = toPrimitiveWrapper(el);
			attrModel.setCastTo(wrapper.asType().toString());
		}
			break;

		case KEY:
		case BOOLEAN_WRAPPER:
		case STRING:
		case BYTE_ARRAY:
			// to do nothing.
			break;
		case LIST:
		case CONVERTER:
			// mod after this method.
			break;
		case CHAR:
		case CHAR_WRAPPER:
		case DATE:
		case ENUM:
		case UNKNOWN:
			if (attrModel.getConverterFQN() != null) {
				return null;
			}

			Log.e("Unsuppoted element type = " + el.asType(), el);
			encountError = true;
			break;
		}

		return attrModel;
	}

	class ValueExtractVisitor extends
			StandardTypeKindVisitor<AttributeModel, Element> {

		@Override
		public AttributeModel visitPrimitiveAsBoolean(PrimitiveType t,
				Element el) {
			return getAttributeModel(t, el, Kind.BOOLEAN);
		}

		@Override
		public AttributeModel visitPrimitiveAsByte(PrimitiveType t, Element el) {
			return getAttributeModel(t, el, Kind.BYTE);
		}

		@Override
		public AttributeModel visitPrimitiveAsChar(PrimitiveType t, Element el) {
			return getAttributeModel(t, el, Kind.CHAR);
		}

		@Override
		public AttributeModel visitPrimitiveAsDouble(PrimitiveType t, Element el) {
			return getAttributeModel(t, el, Kind.DOUBLE);
		}

		@Override
		public AttributeModel visitPrimitiveAsFloat(PrimitiveType t, Element el) {
			return getAttributeModel(t, el, Kind.FLOAT);
		}

		@Override
		public AttributeModel visitPrimitiveAsInt(PrimitiveType t, Element el) {
			return getAttributeModel(t, el, Kind.INT);
		}

		@Override
		public AttributeModel visitPrimitiveAsLong(PrimitiveType t, Element el) {
			return getAttributeModel(t, el, Kind.LONG);
		}

		@Override
		public AttributeModel visitPrimitiveAsShort(PrimitiveType t, Element el) {
			return getAttributeModel(t, el, Kind.SHORT);
		}

		@Override
		public AttributeModel visitString(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.STRING);
		}

		@Override
		public AttributeModel visitList(DeclaredType t, Element el) {
			// TODO Converter周りの処理を入れ込む

			AttributeModel attr = getAttributeModel(t, el, Kind.LIST);

			List<? extends TypeMirror> generics = t.getTypeArguments();
			if (generics.size() != 1) {
				Log.e("expected single type generics.", el);
				encountError = true;
				return defaultAction(t, el);
			}
			TypeMirror tm = generics.get(0);
			if (tm instanceof WildcardType) {
				WildcardType wt = (WildcardType) tm;
				TypeMirror extendsBound = wt.getExtendsBound();
				if (extendsBound != null) {
					tm = extendsBound;
				}
				TypeMirror superBound = wt.getSuperBound();
				if (superBound != null) {
					Log.e("super is not supported.", el);
					encountError = true;
					return defaultAction(t, el);
				}
			}

			Element type = typeUtils.asElement(tm);
			attr.setSubTypeNameFQN(tm != null ? tm.toString() : null);

			if (attr.getConverterFQN() != null) {
				// not do anything
			} else if ("byte[]".equals(tm.toString())) {
				attr.setSubKind(Kind.BYTE_ARRAY);

			} else if (isPrimitiveWrapperBoolean(type)) {
				attr.setSubKind(Kind.BOOLEAN_WRAPPER);

			} else if (isPrimitiveWrapperByte(type)) {
				attr.setSubKind(Kind.BYTE_WRAPPER);
			} else if (isPrimitiveWrapperShort(type)) {
				attr.setSubKind(Kind.SHORT_WRAPPER);
			} else if (isPrimitiveWrapperInteger(type)) {
				attr.setSubKind(Kind.INT_WRAPPER);
			} else if (isPrimitiveWrapperLong(type)) {
				attr.setSubKind(Kind.LONG_WRAPPER);

			} else if (isPrimitiveWrapperFloat(type)) {
				attr.setSubKind(Kind.FLOAT_WRAPPER);
			} else if (isPrimitiveWrapperDouble(type)) {
				attr.setSubKind(Kind.DOUBLE_WRAPPER);

			} else if (type.toString().equals(Date.class.getCanonicalName())) {
				attr.setSubKind(Kind.DATE);
			} else if (type.toString().equals(String.class.getCanonicalName())) {
				attr.setSubKind(Kind.STRING);
			} else if (type.toString().equals(Key.class.getCanonicalName())) {
				attr.setSubKind(Kind.KEY);
			} else if (isEnum(type)) {
				attr.setSubKind(Kind.ENUM);
			} else {
				Log.e("Unsuppoted element type = " + el.asType(), el);
				encountError = true;
				return defaultAction(t, el);
			}

			return attr;
		}

		@Override
		public AttributeModel visitDate(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.DATE);
		}

		@Override
		public AttributeModel visitEnum(DeclaredType t, Element el) {
			if (isInternalType(el.asType())) {
				// InternalなEnum
				TypeElement typeElement = getTypeElement(el);
				if (isPublic(typeElement)) {
					return getAttributeModel(t, el, Kind.ENUM);
				} else {
					Log.e("Internal EnumType must use public & static.", el);
					encountError = true;
					return defaultAction(t, el);
				}
			} else {
				// InternalじゃないEnum
				return getAttributeModel(t, el, Kind.ENUM);
			}
		}

		@Override
		public AttributeModel visitBooleanWrapper(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.BOOLEAN_WRAPPER);
		}

		@Override
		public AttributeModel visitDoubleWrapper(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.DOUBLE_WRAPPER);
		}

		@Override
		public AttributeModel visitLongWrapper(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.LONG_WRAPPER);
		}

		@Override
		public AttributeModel visitByteWrapper(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.BYTE_WRAPPER);
		}

		@Override
		public AttributeModel visitCharacterWrapper(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.CHAR_WRAPPER);
		}

		@Override
		public AttributeModel visitFloatWrapper(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.FLOAT_WRAPPER);
		}

		@Override
		public AttributeModel visitIntegerWrapper(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.INT_WRAPPER);
		}

		@Override
		public AttributeModel visitShortWrapper(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.SHORT_WRAPPER);
		}

		@Override
		public AttributeModel visitKey(DeclaredType t, Element el) {
			return getAttributeModel(t, el, Kind.KEY);
		}

		@Override
		public AttributeModel visitUndefinedClass(DeclaredType t, Element el) {
			AttributeDelegate attr = getAttributeAnnotation(el);
			if (attr != null) {
				return getAttributeModel(t, el, Kind.CONVERTER);
			}

			Log.e("Unsuppoted element type = " + el.asType(), el);
			encountError = true;
			return defaultAction(t, el);
		}

		@Override
		public AttributeModel visitArray(ArrayType t, Element el) {
			TypeMirror component = t.getComponentType();
			if ("byte".equals(component.toString())) {
				return getAttributeModel(t, el, Kind.BYTE_ARRAY);
			}

			Log.e("Unsuppoted element type = " + el.asType(), el);
			encountError = true;
			return defaultAction(t, el);
		}
	}

	/**
	 * Generates the source code.
	 * 
	 * @throws IOException
	 * @author vvakame
	 */
	public void write() throws IOException {
		Filer filer = processingEnv.getFiler();
		String generateClassName = model.getPackageName() + "."
				+ model.getTarget() + postfix;
		JavaFileObject fileObject = filer.createSourceFile(generateClassName,
				classElement);
		Template.writeGen(fileObject, model);
	}

	/**
	 * @return the encountError
	 */
	public boolean isEncountError() {
		return encountError;
	}
}
