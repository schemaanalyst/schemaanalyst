package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * MozillaPlaces schema.
 * Java code originally generated: 2014/04/22 20:00:15
 *
 */

@SuppressWarnings("serial")
public class MozillaPlaces extends Schema {

	public MozillaPlaces() {
		super("MozillaPlaces");

		Table tableMozAnnoAttributes = this.createTable("moz_anno_attributes");
		tableMozAnnoAttributes.createColumn("id", new IntDataType());
		tableMozAnnoAttributes.createColumn("name", new VarCharDataType(32));
		this.createPrimaryKeyConstraint(tableMozAnnoAttributes, tableMozAnnoAttributes.getColumn("id"));
		this.createNotNullConstraint(tableMozAnnoAttributes, tableMozAnnoAttributes.getColumn("name"));
		this.createUniqueConstraint(tableMozAnnoAttributes, tableMozAnnoAttributes.getColumn("name"));

		Table tableMozAnnos = this.createTable("moz_annos");
		tableMozAnnos.createColumn("id", new IntDataType());
		tableMozAnnos.createColumn("place_id", new IntDataType());
		tableMozAnnos.createColumn("anno_attribute_id", new IntDataType());
		tableMozAnnos.createColumn("mime_type", new VarCharDataType(32));
		tableMozAnnos.createColumn("content", new VarCharDataType(256));
		tableMozAnnos.createColumn("flags", new IntDataType());
		tableMozAnnos.createColumn("expiration", new IntDataType());
		tableMozAnnos.createColumn("type", new IntDataType());
		tableMozAnnos.createColumn("dateAdded", new IntDataType());
		tableMozAnnos.createColumn("lastModified", new IntDataType());
		this.createPrimaryKeyConstraint(tableMozAnnos, tableMozAnnos.getColumn("id"));
		this.createNotNullConstraint(tableMozAnnos, tableMozAnnos.getColumn("place_id"));

		Table tableMozBookmarks = this.createTable("moz_bookmarks");
		tableMozBookmarks.createColumn("id", new IntDataType());
		tableMozBookmarks.createColumn("type", new IntDataType());
		tableMozBookmarks.createColumn("fk", new IntDataType());
		tableMozBookmarks.createColumn("parent", new IntDataType());
		tableMozBookmarks.createColumn("position", new IntDataType());
		tableMozBookmarks.createColumn("title", new VarCharDataType(256));
		tableMozBookmarks.createColumn("keyword_id", new IntDataType());
		tableMozBookmarks.createColumn("folder_type", new VarCharDataType(256));
		tableMozBookmarks.createColumn("dateAdded", new IntDataType());
		tableMozBookmarks.createColumn("lastModified", new IntDataType());
		tableMozBookmarks.createColumn("guid", new VarCharDataType(256));
		this.createPrimaryKeyConstraint(tableMozBookmarks, tableMozBookmarks.getColumn("id"));

		Table tableMozBookmarksRoots = this.createTable("moz_bookmarks_roots");
		tableMozBookmarksRoots.createColumn("root_name", new VarCharDataType(16));
		tableMozBookmarksRoots.createColumn("folder_id", new IntDataType());
		this.createUniqueConstraint(tableMozBookmarksRoots, tableMozBookmarksRoots.getColumn("root_name"));

		Table tableMozFavicons = this.createTable("moz_favicons");
		tableMozFavicons.createColumn("id", new IntDataType());
		tableMozFavicons.createColumn("url", new VarCharDataType(256));
		tableMozFavicons.createColumn("data", new VarCharDataType(256));
		tableMozFavicons.createColumn("mime_type", new VarCharDataType(32));
		tableMozFavicons.createColumn("expiration", new IntDataType());
		tableMozFavicons.createColumn("guid", new VarCharDataType(256));
		this.createPrimaryKeyConstraint(tableMozFavicons, tableMozFavicons.getColumn("id"));
		this.createUniqueConstraint(tableMozFavicons, tableMozFavicons.getColumn("url"));

		Table tableMozHistoryvisits = this.createTable("moz_historyvisits");
		tableMozHistoryvisits.createColumn("id", new IntDataType());
		tableMozHistoryvisits.createColumn("from_visit", new IntDataType());
		tableMozHistoryvisits.createColumn("place_id", new IntDataType());
		tableMozHistoryvisits.createColumn("visit_date", new IntDataType());
		tableMozHistoryvisits.createColumn("visit_type", new IntDataType());
		tableMozHistoryvisits.createColumn("session", new IntDataType());
		this.createPrimaryKeyConstraint(tableMozHistoryvisits, tableMozHistoryvisits.getColumn("id"));

		Table tableMozHosts = this.createTable("moz_hosts");
		tableMozHosts.createColumn("id", new IntDataType());
		tableMozHosts.createColumn("host", new VarCharDataType(256));
		tableMozHosts.createColumn("frecency", new IntDataType());
		tableMozHosts.createColumn("typed", new IntDataType());
		tableMozHosts.createColumn("prefix", new VarCharDataType(256));
		this.createPrimaryKeyConstraint(tableMozHosts, tableMozHosts.getColumn("id"));
		this.createNotNullConstraint(tableMozHosts, tableMozHosts.getColumn("host"));
		this.createNotNullConstraint(tableMozHosts, tableMozHosts.getColumn("typed"));
		this.createUniqueConstraint(tableMozHosts, tableMozHosts.getColumn("host"));

		Table tableMozInputhistory = this.createTable("moz_inputhistory");
		tableMozInputhistory.createColumn("place_id", new IntDataType());
		tableMozInputhistory.createColumn("input", new VarCharDataType(256));
		tableMozInputhistory.createColumn("use_count", new IntDataType());
		this.createPrimaryKeyConstraint(tableMozInputhistory, tableMozInputhistory.getColumn("place_id"), tableMozInputhistory.getColumn("input"));
		this.createNotNullConstraint(tableMozInputhistory, tableMozInputhistory.getColumn("place_id"));
		this.createNotNullConstraint(tableMozInputhistory, tableMozInputhistory.getColumn("input"));

		Table tableMozItemsAnnos = this.createTable("moz_items_annos");
		tableMozItemsAnnos.createColumn("id", new IntDataType());
		tableMozItemsAnnos.createColumn("item_id", new IntDataType());
		tableMozItemsAnnos.createColumn("anno_attribute_id", new IntDataType());
		tableMozItemsAnnos.createColumn("mime_type", new VarCharDataType(32));
		tableMozItemsAnnos.createColumn("content", new VarCharDataType(256));
		tableMozItemsAnnos.createColumn("flags", new IntDataType());
		tableMozItemsAnnos.createColumn("expiration", new IntDataType());
		tableMozItemsAnnos.createColumn("type", new IntDataType());
		tableMozItemsAnnos.createColumn("dateAdded", new IntDataType());
		tableMozItemsAnnos.createColumn("lastModified", new IntDataType());
		this.createPrimaryKeyConstraint(tableMozItemsAnnos, tableMozItemsAnnos.getColumn("id"));
		this.createNotNullConstraint(tableMozItemsAnnos, tableMozItemsAnnos.getColumn("item_id"));

		Table tableMozKeywords = this.createTable("moz_keywords");
		tableMozKeywords.createColumn("id", new IntDataType());
		tableMozKeywords.createColumn("keyword", new VarCharDataType(256));
		this.createPrimaryKeyConstraint(tableMozKeywords, tableMozKeywords.getColumn("id"));
		this.createUniqueConstraint(tableMozKeywords, tableMozKeywords.getColumn("keyword"));

		Table tableMozPlaces = this.createTable("moz_places");
		tableMozPlaces.createColumn("id", new IntDataType());
		tableMozPlaces.createColumn("url", new VarCharDataType(256));
		tableMozPlaces.createColumn("title", new VarCharDataType(256));
		tableMozPlaces.createColumn("rev_host", new VarCharDataType(256));
		tableMozPlaces.createColumn("visit_count", new IntDataType());
		tableMozPlaces.createColumn("hidden", new IntDataType());
		tableMozPlaces.createColumn("typed", new IntDataType());
		tableMozPlaces.createColumn("favicon_id", new IntDataType());
		tableMozPlaces.createColumn("frecency", new IntDataType());
		tableMozPlaces.createColumn("last_visit_date", new IntDataType());
		tableMozPlaces.createColumn("guid", new VarCharDataType(256));
		this.createPrimaryKeyConstraint(tableMozPlaces, tableMozPlaces.getColumn("id"));
		this.createNotNullConstraint(tableMozPlaces, tableMozPlaces.getColumn("hidden"));
		this.createNotNullConstraint(tableMozPlaces, tableMozPlaces.getColumn("typed"));
		this.createNotNullConstraint(tableMozPlaces, tableMozPlaces.getColumn("frecency"));
	}
}

