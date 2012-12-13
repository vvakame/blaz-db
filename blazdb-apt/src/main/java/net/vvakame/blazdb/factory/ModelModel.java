package net.vvakame.blazdb.factory;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.annotation.Model;

/**
 * Model for {@link Model}. :)
 * @author vvakame
 */
public class ModelModel {

	String kind;

	int schemaVersion;

	String schemaVersionName;

	List<AttributeModel> attributes = new ArrayList<AttributeModel>();


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
