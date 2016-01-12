package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * ArtistTerm schema.
 * Java code originally generated: 2013/12/13 10:00:45
 *
 */

@SuppressWarnings("serial")
public class ArtistTerm extends Schema {

	public ArtistTerm() {
		super("ArtistTerm");

		Table tableArtists = this.createTable("artists");
		tableArtists.createColumn("artist_id", new TextDataType());
		this.createPrimaryKeyConstraint(tableArtists, tableArtists.getColumn("artist_id"));

		Table tableMbtags = this.createTable("mbtags");
		tableMbtags.createColumn("mbtag", new TextDataType());
		this.createPrimaryKeyConstraint(tableMbtags, tableMbtags.getColumn("mbtag"));

		Table tableTerms = this.createTable("terms");
		tableTerms.createColumn("term", new TextDataType());
		this.createPrimaryKeyConstraint(tableTerms, tableTerms.getColumn("term"));

		Table tableArtistMbtag = this.createTable("artist_mbtag");
		tableArtistMbtag.createColumn("artist_id", new TextDataType());
		tableArtistMbtag.createColumn("mbtag", new TextDataType());
		this.createForeignKeyConstraint(tableArtistMbtag, tableArtistMbtag.getColumn("artist_id"), tableArtists, tableArtists.getColumn("artist_id"));
		this.createForeignKeyConstraint(tableArtistMbtag, tableArtistMbtag.getColumn("mbtag"), tableMbtags, tableMbtags.getColumn("mbtag"));

		Table tableArtistTerm = this.createTable("artist_term");
		tableArtistTerm.createColumn("artist_id", new TextDataType());
		tableArtistTerm.createColumn("term", new TextDataType());
		this.createForeignKeyConstraint(tableArtistTerm, tableArtistTerm.getColumn("artist_id"), tableArtists, tableArtists.getColumn("artist_id"));
		this.createForeignKeyConstraint(tableArtistTerm, tableArtistTerm.getColumn("term"), tableTerms, tableTerms.getColumn("term"));
	}
}

