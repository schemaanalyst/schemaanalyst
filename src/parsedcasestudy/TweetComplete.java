package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * TweetComplete schema.
 * Java code originally generated: 2013/08/17 00:31:04
 *
 */

@SuppressWarnings("serial")
public class TweetComplete extends Schema {

	public TweetComplete() {
		super("TweetComplete");

		Table tableTweets = this.createTable("Tweets");
		tableTweets.createColumn("tweet_id", new IntDataType());
		tableTweets.createColumn("in_reply_to_status_id", new IntDataType());
		tableTweets.createColumn("in_reply_to_user_id", new IntDataType());
		tableTweets.createColumn("retweeted_status_id", new IntDataType());
		tableTweets.createColumn("retweeted_status_user_id", new IntDataType());
		tableTweets.createColumn("timestamp", new DateTimeDataType());
		tableTweets.createColumn("source", new TextDataType());
		tableTweets.createColumn("text", new VarCharDataType(140));
		this.createPrimaryKeyConstraint(tableTweets, tableTweets.getColumn("tweet_id"));

		Table tableExpandedUrls = this.createTable("Expanded_URLS");
		tableExpandedUrls.createColumn("tweet_id", new IntDataType());
		tableExpandedUrls.createColumn("expanded_url", new TextDataType());
		this.createPrimaryKeyConstraint(tableExpandedUrls, tableExpandedUrls.getColumn("tweet_id"), tableExpandedUrls.getColumn("expanded_url"));
		this.createForeignKeyConstraint(tableExpandedUrls, tableExpandedUrls.getColumn("tweet_id"), tableTweets, tableTweets.getColumn("tweet_id"));
	}
}

