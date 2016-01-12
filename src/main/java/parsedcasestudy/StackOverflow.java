package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * StackOverflow schema.
 * Java code originally generated: 2014/05/07 09:58:13
 *
 */

@SuppressWarnings("serial")
public class StackOverflow extends Schema {

	public StackOverflow() {
		super("StackOverflow");

		Table tableComments = this.createTable("comments");
		tableComments.createColumn("id", new IntDataType());
		tableComments.createColumn("post_id", new IntDataType());
		tableComments.createColumn("score", new IntDataType());
		tableComments.createColumn("text", new TextDataType());
		tableComments.createColumn("creation_date", new DateDataType());
		tableComments.createColumn("user_id", new IntDataType());
		this.createNotNullConstraint(tableComments, tableComments.getColumn("id"));

		Table tablePosts = this.createTable("posts");
		tablePosts.createColumn("id", new IntDataType());
		tablePosts.createColumn("post_type_id", new SmallIntDataType());
		tablePosts.createColumn("parent_id", new IntDataType());
		tablePosts.createColumn("accepted_answer_id", new IntDataType());
		tablePosts.createColumn("creation_date", new DateDataType());
		tablePosts.createColumn("score", new SmallIntDataType());
		tablePosts.createColumn("view_count", new IntDataType());
		tablePosts.createColumn("body", new TextDataType());
		tablePosts.createColumn("owner_user_id", new IntDataType());
		tablePosts.createColumn("last_editor_user_id", new IntDataType());
		tablePosts.createColumn("last_editor_display_name", new TextDataType());
		tablePosts.createColumn("last_edit_date", new DateDataType());
		tablePosts.createColumn("last_activity_date", new DateDataType());
		tablePosts.createColumn("community_owned_date", new DateDataType());
		tablePosts.createColumn("closed_date", new DateDataType());
		tablePosts.createColumn("title", new TextDataType());
		tablePosts.createColumn("tags", new TextDataType());
		tablePosts.createColumn("answer_count", new SmallIntDataType());
		tablePosts.createColumn("comment_count", new SmallIntDataType());
		tablePosts.createColumn("favorite_count", new IntDataType());
		this.createNotNullConstraint(tablePosts, tablePosts.getColumn("id"));

		Table tableUsers = this.createTable("users");
		tableUsers.createColumn("id", new IntDataType());
		tableUsers.createColumn("reputation", new IntDataType());
		tableUsers.createColumn("creation_date", new DateDataType());
		tableUsers.createColumn("display_name", new TextDataType());
		tableUsers.createColumn("email_hash", new TextDataType());
		tableUsers.createColumn("last_access_date", new DateDataType());
		tableUsers.createColumn("website_url", new TextDataType());
		tableUsers.createColumn("location", new TextDataType());
		tableUsers.createColumn("age", new SmallIntDataType());
		tableUsers.createColumn("about_me", new TextDataType());
		tableUsers.createColumn("views", new IntDataType());
		tableUsers.createColumn("up_votes", new IntDataType());
		tableUsers.createColumn("down_votes", new IntDataType());
		this.createNotNullConstraint(tableUsers, tableUsers.getColumn("id"));

		Table tableVotes = this.createTable("votes");
		tableVotes.createColumn("id", new IntDataType());
		tableVotes.createColumn("post_id", new IntDataType());
		tableVotes.createColumn("vote_type_id", new SmallIntDataType());
		tableVotes.createColumn("creation_date", new DateDataType());
		this.createNotNullConstraint(tableVotes, tableVotes.getColumn("id"));
		this.createNotNullConstraint(tableVotes, tableVotes.getColumn("post_id"));
	}
}

