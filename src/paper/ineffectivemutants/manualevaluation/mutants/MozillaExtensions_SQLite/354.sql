-- 354
-- UCColumnE
-- ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged syncGUID with releaseNotesURI

CREATE TABLE "addon" (
	"internal_id"	INT	PRIMARY KEY,
	"id"	TEXT,
	"syncGUID"	TEXT,
	"location"	TEXT,
	"version"	TEXT,
	"type"	TEXT,
	"internalName"	TEXT,
	"updateURL"	TEXT,
	"updateKey"	TEXT,
	"optionsURL"	TEXT,
	"optionsType"	TEXT,
	"aboutURL"	TEXT,
	"iconURL"	TEXT,
	"icon64URL"	TEXT,
	"defaultLocale"	INT,
	"visible"	INT,
	"active"	INT,
	"userDisabled"	INT,
	"appDisabled"	INT,
	"pendingUninstall"	INT,
	"descriptor"	TEXT,
	"installDate"	INT,
	"updateDate"	INT,
	"applyBackgroundUpdates"	INT,
	"bootstrap"	INT,
	"skinnable"	INT,
	"size"	INT,
	"sourceURI"	TEXT,
	"releaseNotesURI"	TEXT	UNIQUE,
	"softDisabled"	INT,
	"isForeignInstall"	INT,
	"hasBinaryComponents"	INT,
	"strictCompatibility"	INT,
	UNIQUE ("id", "location")
)

CREATE TABLE "addon_locale" (
	"addon_internal_id"	INT,
	"locale"	TEXT,
	"locale_id"	INT,
	UNIQUE ("addon_internal_id", "locale")
)

CREATE TABLE "locale" (
	"id"	INT	PRIMARY KEY,
	"name"	TEXT,
	"description"	TEXT,
	"creator"	TEXT,
	"homepageURL"	TEXT
)

CREATE TABLE "locale_strings" (
	"locale_id"	INT,
	"type"	TEXT,
	"value"	TEXT
)

CREATE TABLE "targetApplication" (
	"addon_internal_id"	INT,
	"id"	TEXT,
	"minVersion"	TEXT,
	"maxVersion"	TEXT,
	UNIQUE ("addon_internal_id", "id")
)

CREATE TABLE "targetPlatform" (
	"addon_internal_id"	INT,
	"os"	TEXT,
	"abi"	TEXT,
	UNIQUE ("addon_internal_id", "os", "abi")
)

