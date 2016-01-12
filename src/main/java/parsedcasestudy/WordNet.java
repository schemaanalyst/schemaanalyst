package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * WordNet schema.
 * Java code originally generated: 2013/12/13 10:00:57
 *
 */

@SuppressWarnings("serial")
public class WordNet extends Schema {

	public WordNet() {
		super("WordNet");

		Table tableCategorydef = this.createTable("categorydef");
		tableCategorydef.createColumn("categoryid", new DecimalDataType(2, 0));
		tableCategorydef.createColumn("name", new VarCharDataType(32));
		tableCategorydef.createColumn("pos", new CharDataType(1));
		this.createPrimaryKeyConstraint(tableCategorydef, tableCategorydef.getColumn("categoryid"));
		this.createNotNullConstraint(tableCategorydef, tableCategorydef.getColumn("categoryid"));

		Table tableLexlinkref = this.createTable("lexlinkref");
		tableLexlinkref.createColumn("synset1id", new DecimalDataType(9, 0));
		tableLexlinkref.createColumn("word1id", new DecimalDataType(6, 0));
		tableLexlinkref.createColumn("synset2id", new DecimalDataType(9, 0));
		tableLexlinkref.createColumn("word2id", new DecimalDataType(6, 0));
		tableLexlinkref.createColumn("linkid", new DecimalDataType(2, 0));
		this.createPrimaryKeyConstraint(tableLexlinkref, tableLexlinkref.getColumn("word1id"), tableLexlinkref.getColumn("synset1id"), tableLexlinkref.getColumn("word2id"), tableLexlinkref.getColumn("synset2id"), tableLexlinkref.getColumn("linkid"));
		this.createNotNullConstraint(tableLexlinkref, tableLexlinkref.getColumn("synset1id"));
		this.createNotNullConstraint(tableLexlinkref, tableLexlinkref.getColumn("word1id"));
		this.createNotNullConstraint(tableLexlinkref, tableLexlinkref.getColumn("synset2id"));
		this.createNotNullConstraint(tableLexlinkref, tableLexlinkref.getColumn("word2id"));
		this.createNotNullConstraint(tableLexlinkref, tableLexlinkref.getColumn("linkid"));

		Table tableLinkdef = this.createTable("linkdef");
		tableLinkdef.createColumn("linkid", new DecimalDataType(2, 0));
		tableLinkdef.createColumn("name", new VarCharDataType(50));
		tableLinkdef.createColumn("recurses", new CharDataType(1));
		this.createPrimaryKeyConstraint(tableLinkdef, tableLinkdef.getColumn("linkid"));
		this.createNotNullConstraint(tableLinkdef, tableLinkdef.getColumn("linkid"));
		this.createNotNullConstraint(tableLinkdef, tableLinkdef.getColumn("recurses"));

		Table tableSample = this.createTable("sample");
		tableSample.createColumn("synsetid", new DecimalDataType(9, 0));
		tableSample.createColumn("sampleid", new DecimalDataType(2, 0));
		tableSample.createColumn("sample", new TextDataType());
		this.createPrimaryKeyConstraint(tableSample, tableSample.getColumn("synsetid"), tableSample.getColumn("sampleid"));
		this.createNotNullConstraint(tableSample, tableSample.getColumn("synsetid"));
		this.createNotNullConstraint(tableSample, tableSample.getColumn("sampleid"));
		this.createNotNullConstraint(tableSample, tableSample.getColumn("sample"));

		Table tableSemlinkref = this.createTable("semlinkref");
		tableSemlinkref.createColumn("synset1id", new DecimalDataType(9, 0));
		tableSemlinkref.createColumn("synset2id", new DecimalDataType(9, 0));
		tableSemlinkref.createColumn("linkid", new DecimalDataType(2, 0));
		this.createPrimaryKeyConstraint(tableSemlinkref, tableSemlinkref.getColumn("synset1id"), tableSemlinkref.getColumn("synset2id"), tableSemlinkref.getColumn("linkid"));
		this.createNotNullConstraint(tableSemlinkref, tableSemlinkref.getColumn("synset1id"));
		this.createNotNullConstraint(tableSemlinkref, tableSemlinkref.getColumn("synset2id"));
		this.createNotNullConstraint(tableSemlinkref, tableSemlinkref.getColumn("linkid"));

		Table tableSense = this.createTable("sense");
		tableSense.createColumn("wordid", new DecimalDataType(6, 0));
		tableSense.createColumn("casedwordid", new DecimalDataType(6, 0));
		tableSense.createColumn("synsetid", new DecimalDataType(9, 0));
		tableSense.createColumn("rank", new DecimalDataType(2, 0));
		tableSense.createColumn("lexid", new DecimalDataType(2, 0));
		tableSense.createColumn("tagcount", new DecimalDataType(5, 0));
		this.createPrimaryKeyConstraint(tableSense, tableSense.getColumn("synsetid"), tableSense.getColumn("wordid"));
		this.createNotNullConstraint(tableSense, tableSense.getColumn("wordid"));
		this.createNotNullConstraint(tableSense, tableSense.getColumn("synsetid"));
		this.createNotNullConstraint(tableSense, tableSense.getColumn("rank"));
		this.createNotNullConstraint(tableSense, tableSense.getColumn("lexid"));

		Table tableSynset = this.createTable("synset");
		tableSynset.createColumn("synsetid", new DecimalDataType(9, 0));
		tableSynset.createColumn("pos", new CharDataType(1));
		tableSynset.createColumn("categoryid", new DecimalDataType(2, 0));
		tableSynset.createColumn("definition", new TextDataType());
		this.createPrimaryKeyConstraint(tableSynset, tableSynset.getColumn("synsetid"));
		this.createNotNullConstraint(tableSynset, tableSynset.getColumn("synsetid"));
		this.createNotNullConstraint(tableSynset, tableSynset.getColumn("categoryid"));

		Table tableWord = this.createTable("word");
		tableWord.createColumn("wordid", new DecimalDataType(6, 0));
		tableWord.createColumn("lemma", new VarCharDataType(80));
		this.createPrimaryKeyConstraint(tableWord, tableWord.getColumn("wordid"));
		this.createNotNullConstraint(tableWord, tableWord.getColumn("wordid"));
		this.createNotNullConstraint(tableWord, tableWord.getColumn("lemma"));
		this.createUniqueConstraint(tableWord, tableWord.getColumn("lemma"));
	}
}

