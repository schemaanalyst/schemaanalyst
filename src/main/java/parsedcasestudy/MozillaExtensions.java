package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * MozillaExtensions schema.
 * Java code originally generated: 2014/04/16 12:26:23
 *
 */

@SuppressWarnings("serial")
public class MozillaExtensions extends Schema {

	public MozillaExtensions() {
		super("MozillaExtensions");

		Table tableAddon = this.createTable("addon");
		tableAddon.createColumn("internal_id", new IntDataType());
		tableAddon.createColumn("id", new TextDataType());
		tableAddon.createColumn("syncGUID", new TextDataType());
		tableAddon.createColumn("location", new TextDataType());
		tableAddon.createColumn("version", new TextDataType());
		tableAddon.createColumn("type", new TextDataType());
		tableAddon.createColumn("internalName", new TextDataType());
		tableAddon.createColumn("updateURL", new TextDataType());
		tableAddon.createColumn("updateKey", new TextDataType());
		tableAddon.createColumn("optionsURL", new TextDataType());
		tableAddon.createColumn("optionsType", new TextDataType());
		tableAddon.createColumn("aboutURL", new TextDataType());
		tableAddon.createColumn("iconURL", new TextDataType());
		tableAddon.createColumn("icon64URL", new TextDataType());
		tableAddon.createColumn("defaultLocale", new IntDataType());
		tableAddon.createColumn("visible", new IntDataType());
		tableAddon.createColumn("active", new IntDataType());
		tableAddon.createColumn("userDisabled", new IntDataType());
		tableAddon.createColumn("appDisabled", new IntDataType());
		tableAddon.createColumn("pendingUninstall", new IntDataType());
		tableAddon.createColumn("descriptor", new TextDataType());
		tableAddon.createColumn("installDate", new IntDataType());
		tableAddon.createColumn("updateDate", new IntDataType());
		tableAddon.createColumn("applyBackgroundUpdates", new IntDataType());
		tableAddon.createColumn("bootstrap", new IntDataType());
		tableAddon.createColumn("skinnable", new IntDataType());
		tableAddon.createColumn("size", new IntDataType());
		tableAddon.createColumn("sourceURI", new TextDataType());
		tableAddon.createColumn("releaseNotesURI", new TextDataType());
		tableAddon.createColumn("softDisabled", new IntDataType());
		tableAddon.createColumn("isForeignInstall", new IntDataType());
		tableAddon.createColumn("hasBinaryComponents", new IntDataType());
		tableAddon.createColumn("strictCompatibility", new IntDataType());
		this.createPrimaryKeyConstraint(tableAddon, tableAddon.getColumn("internal_id"));
		this.createUniqueConstraint(tableAddon, tableAddon.getColumn("id"), tableAddon.getColumn("location"));
		this.createUniqueConstraint(tableAddon, tableAddon.getColumn("syncGUID"));

		Table tableAddonLocale = this.createTable("addon_locale");
		tableAddonLocale.createColumn("addon_internal_id", new IntDataType());
		tableAddonLocale.createColumn("locale", new TextDataType());
		tableAddonLocale.createColumn("locale_id", new IntDataType());
		this.createUniqueConstraint(tableAddonLocale, tableAddonLocale.getColumn("addon_internal_id"), tableAddonLocale.getColumn("locale"));

		Table tableLocale = this.createTable("locale");
		tableLocale.createColumn("id", new IntDataType());
		tableLocale.createColumn("name", new TextDataType());
		tableLocale.createColumn("description", new TextDataType());
		tableLocale.createColumn("creator", new TextDataType());
		tableLocale.createColumn("homepageURL", new TextDataType());
		this.createPrimaryKeyConstraint(tableLocale, tableLocale.getColumn("id"));

		Table tableLocaleStrings = this.createTable("locale_strings");
		tableLocaleStrings.createColumn("locale_id", new IntDataType());
		tableLocaleStrings.createColumn("type", new TextDataType());
		tableLocaleStrings.createColumn("value", new TextDataType());

		Table tableTargetapplication = this.createTable("targetApplication");
		tableTargetapplication.createColumn("addon_internal_id", new IntDataType());
		tableTargetapplication.createColumn("id", new TextDataType());
		tableTargetapplication.createColumn("minVersion", new TextDataType());
		tableTargetapplication.createColumn("maxVersion", new TextDataType());
		this.createUniqueConstraint(tableTargetapplication, tableTargetapplication.getColumn("addon_internal_id"), tableTargetapplication.getColumn("id"));

		Table tableTargetplatform = this.createTable("targetPlatform");
		tableTargetplatform.createColumn("addon_internal_id", new IntDataType());
		tableTargetplatform.createColumn("os", new TextDataType());
		tableTargetplatform.createColumn("abi", new TextDataType());
		this.createUniqueConstraint(tableTargetplatform, tableTargetplatform.getColumn("addon_internal_id"), tableTargetplatform.getColumn("os"), tableTargetplatform.getColumn("abi"));
	}
}

