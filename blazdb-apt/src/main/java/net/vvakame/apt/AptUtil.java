/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.vvakame.apt;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;

/**
 * Defines misc utils.
 * 
 * @author vvakame
 */
public class AptUtil {

	static Elements elementUtil;
	static Types typeUtil;

	public static void init(Elements elementUtil, Types typeUtil) {
		AptUtil.elementUtil = elementUtil;
		AptUtil.typeUtil = typeUtil;
	}

	private AptUtil() {
	}

	/**
	 * Retrieves the super class of the given {@link Element}. Returns null if
	 * {@link Element} represents {@link Object}, or something other than
	 * {@link ElementKind#CLASS}.
	 * 
	 * @param element
	 *            target {@link Element}.
	 * @return {@link Element} of its super class.
	 * @author vvakame
	 */
	public static TypeElement getSuperClassElement(Element element) {
		if (element.getKind() != ElementKind.CLASS) {
			return null;
		}
		TypeMirror superclass = ((TypeElement) element).getSuperclass();
		if (superclass.getKind() == TypeKind.NONE) {
			return null;
		}
		DeclaredType kind = (DeclaredType) superclass;
		return (TypeElement) kind.asElement();
	}

	/**
	 * Tests if the given element is a kind of {@link Enum}.
	 * 
	 * @param element
	 * @return true if the element passed is kind of {@link Enum}, false
	 *         otherwise.
	 * @author vvakame
	 */
	public static boolean isEnum(Element element) {
		if (element == null) {
			return false;
		} else if (element.getKind() == ElementKind.ENUM) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tests if the given element is a primitive wrapper.
	 * 
	 * @param element
	 * @return true if the element is a primitive wrapper, false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapper(Element element) {
		if (element == null) {
			return false;
		} else if (element.asType().toString()
				.equals(Boolean.class.getCanonicalName())) {
			return true;
		} else if (element.asType().toString()
				.equals(Integer.class.getCanonicalName())) {
			return true;
		} else if (element.asType().toString()
				.equals(Long.class.getCanonicalName())) {
			return true;
		} else if (element.asType().toString()
				.equals(Byte.class.getCanonicalName())) {
			return true;
		} else if (element.asType().toString()
				.equals(Short.class.getCanonicalName())) {
			return true;
		} else if (element.asType().toString()
				.equals(Character.class.getCanonicalName())) {
			return true;
		} else if (element.asType().toString()
				.equals(Double.class.getCanonicalName())) {
			return true;
		} else if (element.asType().toString()
				.equals(Float.class.getCanonicalName())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test if the given element is primitive wrapper boolean.
	 * 
	 * @param element
	 * @return True if the type is a primitive wrapper boolean. false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapperBoolean(Element element) {
		return Boolean.class.getCanonicalName().equals(
				element.asType().toString());
	}

	/**
	 * Test if the given element is primitive wrapper byte.
	 * 
	 * @param element
	 * @return True if the type is a primitive wrapper byte. false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapperByte(Element element) {
		return Byte.class.getCanonicalName()
				.equals(element.asType().toString());
	}

	/**
	 * Test if the given element is primitive wrapper short.
	 * 
	 * @param element
	 * @return True if the type is a primitive wrapper short. false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapperShort(Element element) {
		return Short.class.getCanonicalName().equals(
				element.asType().toString());
	}

	/**
	 * Test if the given element is primitive wrapper integer.
	 * 
	 * @param element
	 * @return True if the type is a primitive wrapper integer. false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapperInteger(Element element) {
		return Integer.class.getCanonicalName().equals(
				element.asType().toString());
	}

	/**
	 * Test if the given element is primitive wrapper long.
	 * 
	 * @param element
	 * @return True if the type is a primitive wrapper integer. false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapperLong(Element element) {
		return Long.class.getCanonicalName()
				.equals(element.asType().toString());
	}

	/**
	 * Test if the given element is primitive wrapper float.
	 * 
	 * @param element
	 * @return True if the type is a primitive wrapper float. false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapperFloat(Element element) {
		return Float.class.getCanonicalName().equals(
				element.asType().toString());
	}

	/**
	 * Test if the given element is primitive wrapper double.
	 * 
	 * @param element
	 * @return True if the type is a primitive wrapper double. false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapperDouble(Element element) {
		return Double.class.getCanonicalName().equals(
				element.asType().toString());
	}

	/**
	 * Tests if the given element is a integral number primitive.<br>
	 * byte or short ot int or long.
	 * 
	 * @param element
	 * @return true if the element is a primitive, false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapperIntegral(Element element) {
		String type = element.asType().toString();
		if (Byte.class.getCanonicalName().equals(type)) {
			return true;
		} else if (Short.class.getCanonicalName().equals(type)) {
			return true;
		} else if (Integer.class.getCanonicalName().equals(type)) {
			return true;
		} else if (Long.class.getCanonicalName().equals(type)) {
			return true;
		}

		return false;
	}

	/**
	 * Tests if the given element is a real number primitive.<br>
	 * byte or short ot int or long.
	 * 
	 * @param element
	 * @return true if the element is a primitive, false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapperReal(Element element) {
		String type = element.asType().toString();
		if (Float.class.getCanonicalName().equals(type)) {
			return true;
		} else if (Double.class.getCanonicalName().equals(type)) {
			return true;
		}

		return false;
	}

	/**
	 * Tests if the given element is a primitive.
	 * 
	 * @param element
	 * @return true if the element is a primitive, false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitive(Element element) {
		String type = element.asType().toString();
		if ("boolean".equals(type)) {
			return true;
		} else if ("char".equals(type)) {
			return true;
		} else if ("byte".equals(type)) {
			return true;
		} else if ("short".equals(type)) {
			return true;
		} else if ("int".equals(type)) {
			return true;
		} else if ("long".equals(type)) {
			return true;
		} else if ("float".equals(type)) {
			return true;
		} else if ("double".equals(type)) {
			return true;
		}

		return false;
	}

	/**
	 * Tests if the given element is a integral number primitive.<br>
	 * byte or short ot int or long.
	 * 
	 * @param element
	 * @return true if the element is a primitive, false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveIntegral(Element element) {
		String type = element.asType().toString();
		if ("byte".equals(type)) {
			return true;
		} else if ("short".equals(type)) {
			return true;
		} else if ("int".equals(type)) {
			return true;
		} else if ("long".equals(type)) {
			return true;
		}

		return false;
	}

	/**
	 * Tests if the given element is a real number primitive.<br>
	 * byte or short ot int or long.
	 * 
	 * @param element
	 * @return true if the element is a primitive, false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveReal(Element element) {
		String type = element.asType().toString();
		if ("float".equals(type)) {
			return true;
		} else if ("double".equals(type)) {
			return true;
		}

		return false;
	}

	/**
	 * convert primitive to primitive wrapper.
	 * 
	 * @param element
	 * 
	 * @return primitive wrapper
	 * @author vvakame
	 */
	public static TypeElement toPrimitiveWrapper(Element element) {
		if (!isPrimitive(element)) {
			throw new IllegalArgumentException(element.toString()
					+ " is not primitive");
		}
		String type = element.asType().toString();
		if ("boolean".equals(type)) {
			return elementUtil.getTypeElement(Boolean.class.getCanonicalName());
		} else if ("char".equals(type)) {
			return elementUtil.getTypeElement(Character.class
					.getCanonicalName());
		} else if ("byte".equals(type)) {
			return elementUtil.getTypeElement(Byte.class.getCanonicalName());
		} else if ("short".equals(type)) {
			return elementUtil.getTypeElement(Short.class.getCanonicalName());
		} else if ("int".equals(type)) {
			return elementUtil.getTypeElement(Integer.class.getCanonicalName());
		} else if ("long".equals(type)) {
			return elementUtil.getTypeElement(Long.class.getCanonicalName());
		} else if ("float".equals(type)) {
			return elementUtil.getTypeElement(Float.class.getCanonicalName());
		} else if ("double".equals(type)) {
			return elementUtil.getTypeElement(Double.class.getCanonicalName());
		}

		return null;
	}

	/**
	 * convert primitive wapper to primitive.
	 * 
	 * @param element
	 * 
	 * @return primitive
	 * @author vvakame
	 */
	public static PrimitiveType toPrimitive(Element element) {
		if (!isPrimitiveWrapper(element)) {
			throw new IllegalArgumentException(element.toString()
					+ " is not primitive wrapper");
		}
		String type = element.asType().toString();
		if (Boolean.class.getCanonicalName().equals(type)) {
			return typeUtil.getPrimitiveType(TypeKind.BOOLEAN);
		} else if (Character.class.getCanonicalName().equals(type)) {
			return typeUtil.getPrimitiveType(TypeKind.CHAR);
		} else if (Byte.class.getCanonicalName().equals(type)) {
			return typeUtil.getPrimitiveType(TypeKind.BYTE);
		} else if (Short.class.getCanonicalName().equals(type)) {
			return typeUtil.getPrimitiveType(TypeKind.SHORT);
		} else if (Integer.class.getCanonicalName().equals(type)) {
			return typeUtil.getPrimitiveType(TypeKind.INT);
		} else if (Long.class.getCanonicalName().equals(type)) {
			return typeUtil.getPrimitiveType(TypeKind.LONG);
		} else if (Float.class.getCanonicalName().equals(type)) {
			return typeUtil.getPrimitiveType(TypeKind.FLOAT);
		} else if (Double.class.getCanonicalName().equals(type)) {
			return typeUtil.getPrimitiveType(TypeKind.DOUBLE);
		}

		return null;
	}

	/**
	 * Test if the given element is primitive boolean.
	 * 
	 * @param element
	 * @return True if the type is a primitive boolean. false otherwise.
	 * @author vvakame
	 */
	public static boolean isPrimitiveBoolean(Element element) {
		return "boolean".equals(element.asType().toString());
	}

	/**
	 * Test if the given type is an internal type.
	 * 
	 * @param type
	 * 
	 * @return True if the type is an internal type, false otherwise.
	 * @author vvakame
	 */
	public static boolean isInternalType(TypeMirror type) {
		Element element = ((TypeElement) typeUtil.asElement(type))
				.getEnclosingElement();
		return element.getKind() != ElementKind.PACKAGE;
	}

	/**
	 * Retrieves the corresponding {@link TypeElement} of the given element.
	 * 
	 * @param element
	 * 
	 * @return The corresponding {@link TypeElement}.
	 * @author vvakame
	 */
	public static TypeElement getTypeElement(Element element) {
		TypeMirror type = element.asType();
		return (TypeElement) typeUtil.asElement(type);
	}

	/**
	 * Retrieves {@link Element}s matching the given annoation and kind (only if
	 * given,) from children of the given root.
	 * 
	 * @param parent
	 *            The element search from.
	 * @param annotation
	 *            Annotation looking for
	 * @param kind
	 *            {@link ElementKind} looking for
	 * @return {@link Element}s matched
	 * @author vvakame
	 */
	public static List<Element> getEnclosedElementsByAnnotation(Element parent,
			Class<? extends Annotation> annotation, ElementKind kind) {
		List<? extends Element> elements = parent.getEnclosedElements();
		List<Element> results = new ArrayList<Element>();

		for (Element element : elements) {
			if (kind != null && element.getKind() != kind) {
				continue;
			}
			Object key = element.getAnnotation(annotation);
			if (key == null) {
				continue;
			}
			results.add(element);
		}

		return results;
	}

	/**
	 * Retrieves {@link Element}s matching the given kind (only if given,) from
	 * children of the given root.
	 * 
	 * @param parent
	 *            The element search from.
	 * @param kind
	 *            {@link ElementKind} looking for
	 * @return {@link Element}s matched
	 * @author vvakame
	 */
	public static List<Element> getEnclosedElementsByKind(Element parent,
			ElementKind kind) {
		if (parent.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		List<Element> results = new ArrayList<Element>();
		List<? extends Element> elements = parent.getEnclosedElements();
		for (Element element : elements) {
			if (element.getKind() != kind) {
				continue;
			}
			results.add(element);
		}

		return results;
	}

	/**
	 * Returns the package name of the given element. NB: This method requires
	 * the given element has the kind of {@link ElementKind#CLASS}.
	 * 
	 * @param element
	 * 
	 * @return the package name
	 * @author vvakame
	 */
	public static String getPackageName(Element element) {
		return elementUtil.getPackageOf(element).getQualifiedName().toString();
	}

	/**
	 * Returns the package name of the given {@link TypeMirror}.
	 * 
	 * @param type
	 * 
	 * @return the package name
	 * @author backpaper0
	 * @author vvakame
	 */
	public static String getPackageName(TypeMirror type) {
		TypeVisitor<DeclaredType, Object> tv = new SimpleTypeVisitor6<DeclaredType, Object>() {

			@Override
			public DeclaredType visitDeclared(DeclaredType t, Object p) {
				return t;
			}
		};
		DeclaredType dt = type.accept(tv, null);
		if (dt != null) {
			ElementVisitor<TypeElement, Object> ev = new SimpleElementVisitor6<TypeElement, Object>() {

				@Override
				public TypeElement visitType(TypeElement e, Object p) {
					return e;
				}
			};
			TypeElement el = typeUtil.asElement(dt).accept(ev, null);
			if (el != null && el.getNestingKind() != NestingKind.TOP_LEVEL) {
				return AptUtil.getPackageName(el);
			}
		}
		return AptUtil.getPackageNameSub(type);
	}

	private static String getPackageNameSub(TypeMirror type) {
		String s = type.toString();
		return s.substring(0, s.lastIndexOf('.'));
	}

	/**
	 * Returns unqualified class name (e.g. String, if java.lang.String) NB:
	 * This method requires the given element has the kind of
	 * {@link ElementKind#CLASS}.
	 * 
	 * @param element
	 * @return unqualified class name
	 * @author vvakame
	 */
	public static String getSimpleName(Element element) {
		if (element.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = element.asType().toString();
		int i = str.lastIndexOf(".");
		return str.substring(i + 1);
	}

	/**
	 * Returns unqualified class name (e.g. String, if java.lang.String) NB:
	 * This method requires the given element has the kind of
	 * {@link ElementKind#CLASS}.
	 * 
	 * @param element
	 * @return unqualified class name
	 * @author vvakame
	 */
	public static String getNameForNew(Element element) {
		if (element.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		return getNameForNew("", element);
	}

	static String getNameForNew(String current, Element element) {
		if (element.getKind() == ElementKind.PACKAGE) {
			return current;
		} else {
			String str = element.asType().toString();
			int i = str.lastIndexOf(".");
			String now = str.substring(i + 1);
			if ("".equals(current)) {
				return getNameForNew(now, element.getEnclosingElement());
			} else {
				return getNameForNew(now + "." + current,
						element.getEnclosingElement());
			}
		}
	}

	/**
	 * Returns unqualified class name (e.g. String, if java.lang.String) NB:
	 * This method requires the given element has the kind of
	 * {@link ElementKind#CLASS}.
	 * 
	 * @param tm
	 * @return unqualified class name
	 * @author vvakame
	 */
	public static String getSimpleName(TypeMirror tm) {
		String str = tm.toString();
		int i = str.lastIndexOf(".");
		return str.substring(i + 1);
	}

	/**
	 * Returns the fully qualified name.
	 * 
	 * @param tm
	 * @return The fully qualified name
	 * @author vvakame
	 */
	public static String getFullQualifiedName(TypeMirror tm) {
		String str = typeUtil.erasure(tm).toString();
		int i = str.lastIndexOf("<");
		if (0 < i) {
			return str.substring(0, i);
		} else {
			return str;
		}
	}

	/**
	 * Returns the fully qualified name.
	 * 
	 * @param element
	 * @return The fully qualified name
	 * @author vvakame
	 */
	public static String getFullQualifiedName(Element element) {
		return element.toString();
	}

	public static TypeElement toTypeElement(Class<?> clazz) {
		return elementUtil.getTypeElement(clazz.getCanonicalName());
	}

	public static List<? extends TypeMirror> getTypeArgumentsAtType(
			TypeElement type, final Class<?> base) {
		// わかりにくいので日本語で書いておく。typeから、interfaceやsuperclassを調べてbaseTypeに指定のクラス換算でのTypeArgumentsを調べる。
		if (isSameTypeWithoutGenerics(type.asType(),
				toDeclaredType(toTypeElement(Object.class).asType()))) {
			return null;
		}

		TypeMirror baseType = toTypeElement(base).asType();

		List<? extends TypeMirror> interfaces = type.getInterfaces();
		for (TypeMirror typeMirror : interfaces) {
			if (isSameTypeWithoutGenerics(baseType, typeMirror)) {
				return toDeclaredType(typeMirror).getTypeArguments();
			}
		}

		DeclaredType superType = toDeclaredType(type.getSuperclass());
		if (isSameTypeWithoutGenerics(superType, baseType)) {
			return superType.getTypeArguments();
		}

		TypeElement superElement = toTypeElement(superType);
		return getTypeArgumentsAtType(superElement, base);
	}

	static boolean checkModifier(Element element, Modifier modifier) {
		for (Modifier m : element.getModifiers()) {
			if (modifier == m) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests if the given element has the public visibility.
	 * 
	 * @param element
	 * @return true if public, false otherwise
	 * @author vvakame
	 */
	public static boolean isPublic(Element element) {
		return checkModifier(element, Modifier.PUBLIC);
	}

	/**
	 * Tests if the given element has the protected visibility.
	 * 
	 * @param element
	 * @return true if protected, false otherwise
	 * @author vvakame
	 */
	public static boolean isProtected(Element element) {
		return checkModifier(element, Modifier.PROTECTED);
	}

	/**
	 * Tests if the given element has the private visibility.
	 * 
	 * @param element
	 * @return true if private, false otherwise
	 * @author vvakame
	 */
	public static boolean isPrivate(Element element) {
		return checkModifier(element, Modifier.PRIVATE);
	}

	/**
	 * Tests if the given element has the package-private visibility.
	 * 
	 * @param element
	 * @return true if package-private, false otherwise
	 * @author vvakame
	 */
	public static boolean isPackagePrivate(Element element) {
		if (isPublic(element)) {
			return false;
		} else if (isProtected(element)) {
			return false;
		} else if (isPrivate(element)) {
			return false;
		}
		return true;
	}

	/**
	 * Tests if the given element has static scope.
	 * 
	 * @param element
	 * @return true if static, false otherwise
	 * @author vvakame
	 */
	public static boolean isStatic(Element element) {
		return checkModifier(element, Modifier.STATIC);
	}

	/**
	 * Tests if the given element has the method with the given name.<br>
	 * NB: This method requires the given element has the kind of
	 * {@link ElementKind#CLASS}.<br>
	 * Also tests the method qualifies all of modifiers if any {@link Modifier}
	 * are also given.
	 * 
	 * @param element
	 * @param methodName
	 * @param modifiers
	 * @return true if a match is found, false otherwise
	 * @author vvakame
	 */
	public static boolean isMethodExists(Element element, String methodName,
			Modifier... modifiers) {
		if (element.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		List<Modifier> modifiersList = Arrays.asList(modifiers);

		List<ExecutableElement> methods = ElementFilter.methodsIn(element
				.getEnclosedElements());
		for (ExecutableElement method : methods) {
			if (method.getSimpleName().toString().equals(methodName)
					&& method.getModifiers().containsAll(modifiersList)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSameTypeWithGenerics(TypeMirror t1, TypeMirror t2) {
		return typeUtil.isSameType(t1, t2);
	}

	public static boolean isSameTypeWithoutGenerics(TypeMirror t1, TypeMirror t2) {
		TypeMirror t1a = typeUtil.erasure(t1);
		TypeMirror t2a = typeUtil.erasure(t2);
		return typeUtil.isSameType(t1a, t2a);
	}

	static String cutAfterString(String base, char key) {
		if (base == null) {
			return null;
		}
		int lastIndexOf = base.lastIndexOf(key);
		if (lastIndexOf < 0) {
			return base;
		} else {
			return base.substring(0, lastIndexOf);
		}
	}

	/**
	 * Returns the name of corresponding setter.
	 * 
	 * @param element
	 *            the field
	 * @return setter name
	 * @author vvakame
	 */
	public static String getElementSetter(Element element) {
		// 後続処理注意 hogeに対して sethoge が取得される. setHoge ではない.
		String setterName = null;
		if (isPrimitiveBoolean(element)) {
			Pattern pattern = Pattern.compile("^is[^a-z].*$");
			Matcher matcher = pattern.matcher(element.getSimpleName()
					.toString());
			if (matcher.matches()) {
				// boolean isHoge; に対して setIsHoge ではなく setHoge が生成される
				setterName = "set"
						+ element.getSimpleName().toString().substring(2);
			}
		}
		if (setterName == null) {
			setterName = "set" + element.getSimpleName().toString();
		}

		Element setter = null;
		for (Element method : ElementFilter.methodsIn(element
				.getEnclosingElement().getEnclosedElements())) {
			String methodName = method.getSimpleName().toString();

			if (setterName.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method)
						|| isPackagePrivate(method)) {
					setter = method;
					break;
				}
			}
		}
		if (setter != null) {
			return setter.getSimpleName().toString();
		} else {
			return null;
		}
	}

	/**
	 * Returns the name of corresponding getter.
	 * 
	 * @param element
	 *            the field
	 * @return getter name
	 * @author vvakame
	 */
	public static String getElementGetter(Element element) {
		// TODO 型(boolean)による絞り込みをするべき
		String getterName1 = "get" + element.getSimpleName().toString();
		String getterName2 = "is" + element.getSimpleName().toString();
		String getterName3 = element.getSimpleName().toString();

		Element getter = null;
		for (Element method : ElementFilter.methodsIn(element
				.getEnclosingElement().getEnclosedElements())) {
			String methodName = method.getSimpleName().toString();

			if (getterName1.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method)
						|| isPackagePrivate(method)) {
					getter = method;
					break;
				}
			} else if (getterName2.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method)
						|| isPackagePrivate(method)) {
					getter = method;
					break;
				}
			} else if (getterName3.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method)
						|| isPackagePrivate(method)) {
					getter = method;
					break;
				}
			}
		}
		if (getter != null) {
			return getter.getSimpleName().toString();
		} else {
			return null;
		}
	}

	// TODO Javadoc
	public static DeclaredType toDeclaredType(TypeMirror type) {
		if (type instanceof DeclaredType) {
			return (DeclaredType) type;
		} else {
			return null;
		}
	}

	// TODO Javadoc
	public static TypeElement toTypeElement(DeclaredType type) {
		Element element = type.asElement();
		if (element instanceof TypeElement) {
			return (TypeElement) element;
		} else {
			return null;
		}
	}
}
