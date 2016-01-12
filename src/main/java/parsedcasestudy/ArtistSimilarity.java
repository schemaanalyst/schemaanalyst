package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * ArtistSimilarity schema.
 * Java code originally generated: 2013/12/13 10:00:43
 *
 */

@SuppressWarnings("serial")
public class ArtistSimilarity extends Schema {

	public ArtistSimilarity() {
		super("ArtistSimilarity");

		Table tableArtists = this.createTable("artists");
		tableArtists.createColumn("artist_id", new TextDataType());
		this.createPrimaryKeyConstraint(tableArtists, tableArtists.getColumn("artist_id"));

		Table tableSimilarity = this.createTable("similarity");
		tableSimilarity.createColumn("target", new TextDataType());
		tableSimilarity.createColumn("similar", new TextDataType());
		this.createForeignKeyConstraint(tableSimilarity, tableSimilarity.getColumn("target"), tableArtists, tableArtists.getColumn("artist_id"));
		this.createForeignKeyConstraint(tableSimilarity, tableSimilarity.getColumn("similar"), tableArtists, tableArtists.getColumn("artist_id"));
	}
}

