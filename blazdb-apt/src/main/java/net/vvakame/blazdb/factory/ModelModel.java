package net.vvakame.blazdb.factory;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.annotation.Model;

/**
 * Model for {@link Model}. :)
 * @author vvakame
 */
public class ModelModel {

	String packageName = "invalid";

	String postfix = "Invalid";

	boolean existsBase = false;

	String targetBase = "Invalid";

	String target = "Invalid";

	String targetNew = "Invalid";

	// info from annotation

	String kind;

	int schemaVersion;

	String schemaVersionName;

	AttributeModel primaryKey;

	List<AttributeModel> attributes = new ArrayList<AttributeModel>();


	/**
	 * @return the packageName
	 * @category accessor
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 * @category accessor
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the postfix
	 * @category accessor
	 */
	public String getPostfix() {
		return postfix;
	}

	/**
	 * @param postfix the postfix to set
	 * @category accessor
	 */
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	/**
	 * @return the existsBase
	 * @category accessor
	 */
	public boolean isExistsBase() {
		return existsBase;
	}

	/**
	 * @param existsBase the existsBase to set
	 * @category accessor
	 */
	public void setExistsBase(boolean existsBase) {
		this.existsBase = existsBase;
	}

	/**
	 * @return the targetBase
	 * @category accessor
	 */
	public String getTargetBase() {
		return targetBase;
	}

	/**
	 * @param targetBase the targetBase to set
	 * @category accessor
	 */
	public void setTargetBase(String targetBase) {
		this.targetBase = targetBase;
	}

	/**
	 * @return the target
	 * @category accessor
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 * @category accessor
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the targetNew
	 * @category accessor
	 */
	public String getTargetNew() {
		return targetNew;
	}

	/**
	 * @param targetNew the targetNew to set
	 * @category accessor
	 */
	public void setTargetNew(String targetNew) {
		this.targetNew = targetNew;
	}

	/**
	 * @return the kind
	 * @category accessor
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 * @category accessor
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the schemaVersion
	 * @category accessor
	 */
	public int getSchemaVersion() {
		return schemaVersion;
	}

	/**
	 * @param schemaVersion the schemaVersion to set
	 * @category accessor
	 */
	public void setSchemaVersion(int schemaVersion) {
		this.schemaVersion = schemaVersion;
	}

	/**
	 * @return the schemaVersionName
	 * @category accessor
	 */
	public String getSchemaVersionName() {
		return schemaVersionName;
	}

	/**
	 * @param schemaVersionName the schemaVersionName to set
	 * @category accessor
	 */
	public void setSchemaVersionName(String schemaVersionName) {
		this.schemaVersionName = schemaVersionName;
	}

	/**
	 * @return the primaryKey
	 * @category accessor
	 */
	public AttributeModel getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @param primaryKey the primaryKey to set
	 * @category accessor
	 */
	public void setPrimaryKey(AttributeModel primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * @return the attributes
	 * @category accessor
	 */
	public List<AttributeModel> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 * @category accessor
	 */
	public void setAttributes(List<AttributeModel> attributes) {
		this.attributes = attributes;
	}
}
