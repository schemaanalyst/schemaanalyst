package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * Mxm schema.
 * Java code originally generated: 2013/12/13 10:00:52
 *
 */

@SuppressWarnings("serial")
public class Mxm extends Schema {

	public Mxm() {
		super("Mxm");

		Table tableWords = this.createTable("words");
		tableWords.createColumn("word", new TextDataType());
		this.createPrimaryKeyConstraint("null", tableWords, tableWords.getColumn("word"));

		Table tableLyrics = this.createTable("lyrics");
		tableLyrics.createColumn("track_id", new IntDataType());
		tableLyrics.createColumn("mxm_tid", new IntDataType());
		tableLyrics.createColumn("word", new TextDataType());
		tableLyrics.createColumn("count", new IntDataType());
		tableLyrics.createColumn("is_test", new IntDataType());
		this.createForeignKeyConstraint("null", tableLyrics, tableLyrics.getColumn("word"), tableWords, tableWords.getColumn("word"));
	}
}

