package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * SongTrackMetadata schema.
 * Java code originally generated: 2013/12/13 10:00:54
 *
 */

@SuppressWarnings("serial")
public class SongTrackMetadata extends Schema {

	public SongTrackMetadata() {
		super("SongTrackMetadata");

		Table tableSongs = this.createTable("songs");
		tableSongs.createColumn("track_id", new TextDataType());
		tableSongs.createColumn("title", new TextDataType());
		tableSongs.createColumn("song_id", new TextDataType());
		tableSongs.createColumn("release", new TextDataType());
		tableSongs.createColumn("artist_id", new TextDataType());
		tableSongs.createColumn("artist_mbid", new TextDataType());
		tableSongs.createColumn("artist_name", new TextDataType());
		tableSongs.createColumn("duration", new RealDataType());
		tableSongs.createColumn("artist_familiarity", new RealDataType());
		tableSongs.createColumn("artist_hotttnesss", new RealDataType());
		tableSongs.createColumn("year", new IntDataType());
		tableSongs.createColumn("track_7digitalid", new IntDataType());
		tableSongs.createColumn("shs_perf", new IntDataType());
		tableSongs.createColumn("shs_work", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableSongs, tableSongs.getColumn("track_id"));
	}
}

