package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.*;

/*
 * BioSQL schema.
 * Java code originally generated: 2013/12/13 09:49:55
 *
 */

@SuppressWarnings("serial")
public class BioSQL extends Schema {

	public BioSQL() {
		super("BioSQL");

		Table tableBiodatabase = this.createTable("biodatabase");
		tableBiodatabase.createColumn("biodatabase_id", new IntDataType());
		tableBiodatabase.createColumn("name", new VarCharDataType(128));
		tableBiodatabase.createColumn("authority", new VarCharDataType(128));
		tableBiodatabase.createColumn("description", new TextDataType());
		this.createPrimaryKeyConstraint("null", tableBiodatabase, tableBiodatabase.getColumn("biodatabase_id"));
		this.createNotNullConstraint("null", tableBiodatabase, tableBiodatabase.getColumn("biodatabase_id"));
		this.createNotNullConstraint("null", tableBiodatabase, tableBiodatabase.getColumn("name"));
		this.createUniqueConstraint("null", tableBiodatabase, tableBiodatabase.getColumn("name"));

		Table tableTaxon = this.createTable("taxon");
		tableTaxon.createColumn("taxon_id", new IntDataType());
		tableTaxon.createColumn("ncbi_taxon_id", new IntDataType());
		tableTaxon.createColumn("parent_taxon_id", new IntDataType());
		tableTaxon.createColumn("node_rank", new VarCharDataType(32));
		tableTaxon.createColumn("genetic_code", new SmallIntDataType());
		tableTaxon.createColumn("mito_genetic_code", new SmallIntDataType());
		tableTaxon.createColumn("left_value", new IntDataType());
		tableTaxon.createColumn("right_value", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableTaxon, tableTaxon.getColumn("taxon_id"));
		this.createNotNullConstraint("null", tableTaxon, tableTaxon.getColumn("taxon_id"));
		this.createUniqueConstraint("XAKtaxon_ncbi_taxon_id", tableTaxon, tableTaxon.getColumn("ncbi_taxon_id"));
		this.createUniqueConstraint("XAKtaxon_left_value", tableTaxon, tableTaxon.getColumn("left_value"));
		this.createUniqueConstraint("XAKtaxon_right_value", tableTaxon, tableTaxon.getColumn("right_value"));

		Table tableTaxonName = this.createTable("taxon_name");
		tableTaxonName.createColumn("taxon_id", new IntDataType());
		tableTaxonName.createColumn("name", new VarCharDataType(255));
		tableTaxonName.createColumn("name_class", new VarCharDataType(32));
		this.createForeignKeyConstraint("FKtaxon_taxonname", tableTaxonName, tableTaxonName.getColumn("taxon_id"), tableTaxon, tableTaxon.getColumn("taxon_id"));
		this.createNotNullConstraint("null", tableTaxonName, tableTaxonName.getColumn("taxon_id"));
		this.createNotNullConstraint("null", tableTaxonName, tableTaxonName.getColumn("name"));
		this.createNotNullConstraint("null", tableTaxonName, tableTaxonName.getColumn("name_class"));
		this.createUniqueConstraint("null", tableTaxonName, tableTaxonName.getColumn("name"), tableTaxonName.getColumn("name_class"), tableTaxonName.getColumn("taxon_id"));

		Table tableOntology = this.createTable("ontology");
		tableOntology.createColumn("ontology_id", new IntDataType());
		tableOntology.createColumn("name", new VarCharDataType(32));
		tableOntology.createColumn("definition", new TextDataType());
		this.createPrimaryKeyConstraint("null", tableOntology, tableOntology.getColumn("ontology_id"));
		this.createNotNullConstraint("null", tableOntology, tableOntology.getColumn("ontology_id"));
		this.createNotNullConstraint("null", tableOntology, tableOntology.getColumn("name"));
		this.createUniqueConstraint("null", tableOntology, tableOntology.getColumn("name"));

		Table tableTerm = this.createTable("term");
		tableTerm.createColumn("term_id", new IntDataType());
		tableTerm.createColumn("name", new VarCharDataType(255));
		tableTerm.createColumn("definition", new TextDataType());
		tableTerm.createColumn("identifier", new VarCharDataType(40));
		tableTerm.createColumn("is_obsolete", new CharDataType(1));
		tableTerm.createColumn("ontology_id", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKont_term", tableTerm, tableTerm.getColumn("ontology_id"), tableOntology, tableOntology.getColumn("ontology_id"));
		this.createNotNullConstraint("null", tableTerm, tableTerm.getColumn("term_id"));
		this.createNotNullConstraint("null", tableTerm, tableTerm.getColumn("name"));
		this.createNotNullConstraint("null", tableTerm, tableTerm.getColumn("ontology_id"));
		this.createUniqueConstraint("null", tableTerm, tableTerm.getColumn("name"), tableTerm.getColumn("ontology_id"), tableTerm.getColumn("is_obsolete"));
		this.createUniqueConstraint("null", tableTerm, tableTerm.getColumn("identifier"));

		Table tableTermSynonym = this.createTable("term_synonym");
		tableTermSynonym.createColumn("synonym", new VarCharDataType(255));
		tableTermSynonym.createColumn("term_id", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableTermSynonym, tableTermSynonym.getColumn("term_id"), tableTermSynonym.getColumn("synonym"));
		this.createForeignKeyConstraint("FKterm_syn", tableTermSynonym, tableTermSynonym.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createNotNullConstraint("null", tableTermSynonym, tableTermSynonym.getColumn("synonym"));
		this.createNotNullConstraint("null", tableTermSynonym, tableTermSynonym.getColumn("term_id"));

		Table tableDbxref = this.createTable("dbxref");
		tableDbxref.createColumn("dbxref_id", new IntDataType());
		tableDbxref.createColumn("dbname", new VarCharDataType(40));
		tableDbxref.createColumn("accession", new VarCharDataType(128));
		tableDbxref.createColumn("version", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableDbxref, tableDbxref.getColumn("dbxref_id"));
		this.createNotNullConstraint("null", tableDbxref, tableDbxref.getColumn("dbxref_id"));
		this.createNotNullConstraint("null", tableDbxref, tableDbxref.getColumn("dbname"));
		this.createNotNullConstraint("null", tableDbxref, tableDbxref.getColumn("accession"));
		this.createNotNullConstraint("null", tableDbxref, tableDbxref.getColumn("version"));
		this.createUniqueConstraint("null", tableDbxref, tableDbxref.getColumn("accession"), tableDbxref.getColumn("dbname"), tableDbxref.getColumn("version"));

		Table tableTermDbxref = this.createTable("term_dbxref");
		tableTermDbxref.createColumn("term_id", new IntDataType());
		tableTermDbxref.createColumn("dbxref_id", new IntDataType());
		tableTermDbxref.createColumn("rank", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableTermDbxref, tableTermDbxref.getColumn("term_id"), tableTermDbxref.getColumn("dbxref_id"));
		this.createForeignKeyConstraint("FKdbxref_trmdbxref", tableTermDbxref, tableTermDbxref.getColumn("dbxref_id"), tableDbxref, tableDbxref.getColumn("dbxref_id"));
		this.createForeignKeyConstraint("FKterm_trmdbxref", tableTermDbxref, tableTermDbxref.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createNotNullConstraint("null", tableTermDbxref, tableTermDbxref.getColumn("term_id"));
		this.createNotNullConstraint("null", tableTermDbxref, tableTermDbxref.getColumn("dbxref_id"));

		Table tableTermRelationship = this.createTable("term_relationship");
		tableTermRelationship.createColumn("term_relationship_id", new IntDataType());
		tableTermRelationship.createColumn("subject_term_id", new IntDataType());
		tableTermRelationship.createColumn("predicate_term_id", new IntDataType());
		tableTermRelationship.createColumn("object_term_id", new IntDataType());
		tableTermRelationship.createColumn("ontology_id", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableTermRelationship, tableTermRelationship.getColumn("term_relationship_id"));
		this.createForeignKeyConstraint("FKtrmsubject_trmrel", tableTermRelationship, tableTermRelationship.getColumn("subject_term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKtrmpredicate_trmrel", tableTermRelationship, tableTermRelationship.getColumn("predicate_term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKtrmobject_trmrel", tableTermRelationship, tableTermRelationship.getColumn("object_term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKontology_trmrel", tableTermRelationship, tableTermRelationship.getColumn("ontology_id"), tableOntology, tableOntology.getColumn("ontology_id"));
		this.createNotNullConstraint("null", tableTermRelationship, tableTermRelationship.getColumn("term_relationship_id"));
		this.createNotNullConstraint("null", tableTermRelationship, tableTermRelationship.getColumn("subject_term_id"));
		this.createNotNullConstraint("null", tableTermRelationship, tableTermRelationship.getColumn("predicate_term_id"));
		this.createNotNullConstraint("null", tableTermRelationship, tableTermRelationship.getColumn("object_term_id"));
		this.createNotNullConstraint("null", tableTermRelationship, tableTermRelationship.getColumn("ontology_id"));
		this.createUniqueConstraint("null", tableTermRelationship, tableTermRelationship.getColumn("subject_term_id"), tableTermRelationship.getColumn("predicate_term_id"), tableTermRelationship.getColumn("object_term_id"), tableTermRelationship.getColumn("ontology_id"));

		Table tableTermRelationshipTerm = this.createTable("term_relationship_term");
		tableTermRelationshipTerm.createColumn("term_relationship_id", new IntDataType());
		tableTermRelationshipTerm.createColumn("term_id", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableTermRelationshipTerm, tableTermRelationshipTerm.getColumn("term_relationship_id"));
		this.createForeignKeyConstraint("FKtrmrel_trmreltrm", tableTermRelationshipTerm, tableTermRelationshipTerm.getColumn("term_relationship_id"), tableTermRelationship, tableTermRelationship.getColumn("term_relationship_id"));
		this.createForeignKeyConstraint("FKtrm_trmreltrm", tableTermRelationshipTerm, tableTermRelationshipTerm.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createNotNullConstraint("null", tableTermRelationshipTerm, tableTermRelationshipTerm.getColumn("term_relationship_id"));
		this.createNotNullConstraint("null", tableTermRelationshipTerm, tableTermRelationshipTerm.getColumn("term_id"));
		this.createUniqueConstraint("null", tableTermRelationshipTerm, tableTermRelationshipTerm.getColumn("term_id"));

		Table tableTermPath = this.createTable("term_path");
		tableTermPath.createColumn("term_path_id", new IntDataType());
		tableTermPath.createColumn("subject_term_id", new IntDataType());
		tableTermPath.createColumn("predicate_term_id", new IntDataType());
		tableTermPath.createColumn("object_term_id", new IntDataType());
		tableTermPath.createColumn("ontology_id", new IntDataType());
		tableTermPath.createColumn("distance", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableTermPath, tableTermPath.getColumn("term_path_id"));
		this.createForeignKeyConstraint("FKtrmsubject_trmpath", tableTermPath, tableTermPath.getColumn("subject_term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKtrmpredicate_trmpath", tableTermPath, tableTermPath.getColumn("predicate_term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKtrmobject_trmpath", tableTermPath, tableTermPath.getColumn("object_term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKontology_trmpath", tableTermPath, tableTermPath.getColumn("ontology_id"), tableOntology, tableOntology.getColumn("ontology_id"));
		this.createNotNullConstraint("null", tableTermPath, tableTermPath.getColumn("term_path_id"));
		this.createNotNullConstraint("null", tableTermPath, tableTermPath.getColumn("subject_term_id"));
		this.createNotNullConstraint("null", tableTermPath, tableTermPath.getColumn("predicate_term_id"));
		this.createNotNullConstraint("null", tableTermPath, tableTermPath.getColumn("object_term_id"));
		this.createNotNullConstraint("null", tableTermPath, tableTermPath.getColumn("ontology_id"));
		this.createUniqueConstraint("null", tableTermPath, tableTermPath.getColumn("subject_term_id"), tableTermPath.getColumn("predicate_term_id"), tableTermPath.getColumn("object_term_id"), tableTermPath.getColumn("ontology_id"), tableTermPath.getColumn("distance"));

		Table tableBioentry = this.createTable("bioentry");
		tableBioentry.createColumn("bioentry_id", new IntDataType());
		tableBioentry.createColumn("biodatabase_id", new IntDataType());
		tableBioentry.createColumn("taxon_id", new IntDataType());
		tableBioentry.createColumn("name", new VarCharDataType(40));
		tableBioentry.createColumn("accession", new VarCharDataType(128));
		tableBioentry.createColumn("identifier", new VarCharDataType(40));
		tableBioentry.createColumn("division", new VarCharDataType(6));
		tableBioentry.createColumn("description", new TextDataType());
		tableBioentry.createColumn("version", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createForeignKeyConstraint("FKtaxon_bioentry", tableBioentry, tableBioentry.getColumn("taxon_id"), tableTaxon, tableTaxon.getColumn("taxon_id"));
		this.createForeignKeyConstraint("FKbiodatabase_bioentry", tableBioentry, tableBioentry.getColumn("biodatabase_id"), tableBiodatabase, tableBiodatabase.getColumn("biodatabase_id"));
		this.createNotNullConstraint("null", tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableBioentry, tableBioentry.getColumn("biodatabase_id"));
		this.createNotNullConstraint("null", tableBioentry, tableBioentry.getColumn("name"));
		this.createNotNullConstraint("null", tableBioentry, tableBioentry.getColumn("accession"));
		this.createNotNullConstraint("null", tableBioentry, tableBioentry.getColumn("version"));
		this.createUniqueConstraint("null", tableBioentry, tableBioentry.getColumn("accession"), tableBioentry.getColumn("biodatabase_id"), tableBioentry.getColumn("version"));
		this.createUniqueConstraint("null", tableBioentry, tableBioentry.getColumn("identifier"), tableBioentry.getColumn("biodatabase_id"));

		Table tableBioentryRelationship = this.createTable("bioentry_relationship");
		tableBioentryRelationship.createColumn("bioentry_relationship_id", new IntDataType());
		tableBioentryRelationship.createColumn("object_bioentry_id", new IntDataType());
		tableBioentryRelationship.createColumn("subject_bioentry_id", new IntDataType());
		tableBioentryRelationship.createColumn("term_id", new IntDataType());
		tableBioentryRelationship.createColumn("rank", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableBioentryRelationship, tableBioentryRelationship.getColumn("bioentry_relationship_id"));
		this.createForeignKeyConstraint("FKterm_bioentryrel", tableBioentryRelationship, tableBioentryRelationship.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKparentent_bioentryrel", tableBioentryRelationship, tableBioentryRelationship.getColumn("object_bioentry_id"), tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createForeignKeyConstraint("FKchildent_bioentryrel", tableBioentryRelationship, tableBioentryRelationship.getColumn("subject_bioentry_id"), tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableBioentryRelationship, tableBioentryRelationship.getColumn("bioentry_relationship_id"));
		this.createNotNullConstraint("null", tableBioentryRelationship, tableBioentryRelationship.getColumn("object_bioentry_id"));
		this.createNotNullConstraint("null", tableBioentryRelationship, tableBioentryRelationship.getColumn("subject_bioentry_id"));
		this.createNotNullConstraint("null", tableBioentryRelationship, tableBioentryRelationship.getColumn("term_id"));
		this.createUniqueConstraint("null", tableBioentryRelationship, tableBioentryRelationship.getColumn("object_bioentry_id"), tableBioentryRelationship.getColumn("subject_bioentry_id"), tableBioentryRelationship.getColumn("term_id"));

		Table tableBioentryPath = this.createTable("bioentry_path");
		tableBioentryPath.createColumn("object_bioentry_id", new IntDataType());
		tableBioentryPath.createColumn("subject_bioentry_id", new IntDataType());
		tableBioentryPath.createColumn("term_id", new IntDataType());
		tableBioentryPath.createColumn("distance", new IntDataType());
		this.createForeignKeyConstraint("FKterm_bioentrypath", tableBioentryPath, tableBioentryPath.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKparentent_bioentrypath", tableBioentryPath, tableBioentryPath.getColumn("object_bioentry_id"), tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createForeignKeyConstraint("FKchildent_bioentrypath", tableBioentryPath, tableBioentryPath.getColumn("subject_bioentry_id"), tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableBioentryPath, tableBioentryPath.getColumn("object_bioentry_id"));
		this.createNotNullConstraint("null", tableBioentryPath, tableBioentryPath.getColumn("subject_bioentry_id"));
		this.createNotNullConstraint("null", tableBioentryPath, tableBioentryPath.getColumn("term_id"));
		this.createUniqueConstraint("null", tableBioentryPath, tableBioentryPath.getColumn("object_bioentry_id"), tableBioentryPath.getColumn("subject_bioentry_id"), tableBioentryPath.getColumn("term_id"), tableBioentryPath.getColumn("distance"));

		Table tableBiosequence = this.createTable("biosequence");
		tableBiosequence.createColumn("bioentry_id", new IntDataType());
		tableBiosequence.createColumn("version", new IntDataType());
		tableBiosequence.createColumn("length", new IntDataType());
		tableBiosequence.createColumn("alphabet", new VarCharDataType(10));
		tableBiosequence.createColumn("seq", new TextDataType());
		this.createPrimaryKeyConstraint("null", tableBiosequence, tableBiosequence.getColumn("bioentry_id"));
		this.createForeignKeyConstraint("FKbioentry_bioseq", tableBiosequence, tableBiosequence.getColumn("bioentry_id"), tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableBiosequence, tableBiosequence.getColumn("bioentry_id"));

		Table tableDbxrefQualifierValue = this.createTable("dbxref_qualifier_value");
		tableDbxrefQualifierValue.createColumn("dbxref_id", new IntDataType());
		tableDbxrefQualifierValue.createColumn("term_id", new IntDataType());
		tableDbxrefQualifierValue.createColumn("rank", new IntDataType());
		tableDbxrefQualifierValue.createColumn("value", new TextDataType());
		this.createPrimaryKeyConstraint("null", tableDbxrefQualifierValue, tableDbxrefQualifierValue.getColumn("dbxref_id"), tableDbxrefQualifierValue.getColumn("term_id"), tableDbxrefQualifierValue.getColumn("rank"));
		this.createForeignKeyConstraint("FKtrm_dbxrefqual", tableDbxrefQualifierValue, tableDbxrefQualifierValue.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKdbxref_dbxrefqual", tableDbxrefQualifierValue, tableDbxrefQualifierValue.getColumn("dbxref_id"), tableDbxref, tableDbxref.getColumn("dbxref_id"));
		this.createNotNullConstraint("null", tableDbxrefQualifierValue, tableDbxrefQualifierValue.getColumn("dbxref_id"));
		this.createNotNullConstraint("null", tableDbxrefQualifierValue, tableDbxrefQualifierValue.getColumn("term_id"));
		this.createNotNullConstraint("null", tableDbxrefQualifierValue, tableDbxrefQualifierValue.getColumn("rank"));

		Table tableBioentryDbxref = this.createTable("bioentry_dbxref");
		tableBioentryDbxref.createColumn("bioentry_id", new IntDataType());
		tableBioentryDbxref.createColumn("dbxref_id", new IntDataType());
		tableBioentryDbxref.createColumn("rank", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableBioentryDbxref, tableBioentryDbxref.getColumn("bioentry_id"), tableBioentryDbxref.getColumn("dbxref_id"));
		this.createForeignKeyConstraint("FKbioentry_dblink", tableBioentryDbxref, tableBioentryDbxref.getColumn("bioentry_id"), tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createForeignKeyConstraint("FKdbxref_dblink", tableBioentryDbxref, tableBioentryDbxref.getColumn("dbxref_id"), tableDbxref, tableDbxref.getColumn("dbxref_id"));
		this.createNotNullConstraint("null", tableBioentryDbxref, tableBioentryDbxref.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableBioentryDbxref, tableBioentryDbxref.getColumn("dbxref_id"));

		Table tableReference = this.createTable("reference");
		tableReference.createColumn("reference_id", new IntDataType());
		tableReference.createColumn("dbxref_id", new IntDataType());
		tableReference.createColumn("location", new TextDataType());
		tableReference.createColumn("title", new TextDataType());
		tableReference.createColumn("authors", new TextDataType());
		tableReference.createColumn("crc", new VarCharDataType(32));
		this.createPrimaryKeyConstraint("null", tableReference, tableReference.getColumn("reference_id"));
		this.createForeignKeyConstraint("FKdbxref_reference", tableReference, tableReference.getColumn("dbxref_id"), tableDbxref, tableDbxref.getColumn("dbxref_id"));
		this.createNotNullConstraint("null", tableReference, tableReference.getColumn("reference_id"));
		this.createNotNullConstraint("null", tableReference, tableReference.getColumn("location"));
		this.createUniqueConstraint("null", tableReference, tableReference.getColumn("dbxref_id"));
		this.createUniqueConstraint("null", tableReference, tableReference.getColumn("crc"));

		Table tableBioentryReference = this.createTable("bioentry_reference");
		tableBioentryReference.createColumn("bioentry_id", new IntDataType());
		tableBioentryReference.createColumn("reference_id", new IntDataType());
		tableBioentryReference.createColumn("start_pos", new IntDataType());
		tableBioentryReference.createColumn("end_pos", new IntDataType());
		tableBioentryReference.createColumn("rank", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableBioentryReference, tableBioentryReference.getColumn("bioentry_id"), tableBioentryReference.getColumn("reference_id"), tableBioentryReference.getColumn("rank"));
		this.createForeignKeyConstraint("FKbioentry_entryref", tableBioentryReference, tableBioentryReference.getColumn("bioentry_id"), tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createForeignKeyConstraint("FKreference_entryref", tableBioentryReference, tableBioentryReference.getColumn("reference_id"), tableReference, tableReference.getColumn("reference_id"));
		this.createNotNullConstraint("null", tableBioentryReference, tableBioentryReference.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableBioentryReference, tableBioentryReference.getColumn("reference_id"));
		this.createNotNullConstraint("null", tableBioentryReference, tableBioentryReference.getColumn("rank"));

		Table tableComment = this.createTable("comment");
		tableComment.createColumn("comment_id", new IntDataType());
		tableComment.createColumn("bioentry_id", new IntDataType());
		tableComment.createColumn("comment_text", new TextDataType());
		tableComment.createColumn("rank", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableComment, tableComment.getColumn("comment_id"));
		this.createForeignKeyConstraint("FKbioentry_comment", tableComment, tableComment.getColumn("bioentry_id"), tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableComment, tableComment.getColumn("comment_id"));
		this.createNotNullConstraint("null", tableComment, tableComment.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableComment, tableComment.getColumn("comment_text"));
		this.createNotNullConstraint("null", tableComment, tableComment.getColumn("rank"));
		this.createUniqueConstraint("null", tableComment, tableComment.getColumn("bioentry_id"), tableComment.getColumn("rank"));

		Table tableBioentryQualifierValue = this.createTable("bioentry_qualifier_value");
		tableBioentryQualifierValue.createColumn("bioentry_id", new IntDataType());
		tableBioentryQualifierValue.createColumn("term_id", new IntDataType());
		tableBioentryQualifierValue.createColumn("value", new TextDataType());
		tableBioentryQualifierValue.createColumn("rank", new IntDataType());
		this.createForeignKeyConstraint("FKbioentry_entqual", tableBioentryQualifierValue, tableBioentryQualifierValue.getColumn("bioentry_id"), tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createForeignKeyConstraint("FKterm_entqual", tableBioentryQualifierValue, tableBioentryQualifierValue.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createNotNullConstraint("null", tableBioentryQualifierValue, tableBioentryQualifierValue.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableBioentryQualifierValue, tableBioentryQualifierValue.getColumn("term_id"));
		this.createNotNullConstraint("null", tableBioentryQualifierValue, tableBioentryQualifierValue.getColumn("rank"));
		this.createUniqueConstraint("null", tableBioentryQualifierValue, tableBioentryQualifierValue.getColumn("bioentry_id"), tableBioentryQualifierValue.getColumn("term_id"), tableBioentryQualifierValue.getColumn("rank"));

		Table tableSeqfeature = this.createTable("seqfeature");
		tableSeqfeature.createColumn("seqfeature_id", new IntDataType());
		tableSeqfeature.createColumn("bioentry_id", new IntDataType());
		tableSeqfeature.createColumn("type_term_id", new IntDataType());
		tableSeqfeature.createColumn("source_term_id", new IntDataType());
		tableSeqfeature.createColumn("display_name", new VarCharDataType(64));
		tableSeqfeature.createColumn("rank", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableSeqfeature, tableSeqfeature.getColumn("seqfeature_id"));
		this.createForeignKeyConstraint("FKterm_seqfeature", tableSeqfeature, tableSeqfeature.getColumn("type_term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKsourceterm_seqfeature", tableSeqfeature, tableSeqfeature.getColumn("source_term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKbioentry_seqfeature", tableSeqfeature, tableSeqfeature.getColumn("bioentry_id"), tableBioentry, tableBioentry.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableSeqfeature, tableSeqfeature.getColumn("seqfeature_id"));
		this.createNotNullConstraint("null", tableSeqfeature, tableSeqfeature.getColumn("bioentry_id"));
		this.createNotNullConstraint("null", tableSeqfeature, tableSeqfeature.getColumn("type_term_id"));
		this.createNotNullConstraint("null", tableSeqfeature, tableSeqfeature.getColumn("source_term_id"));
		this.createNotNullConstraint("null", tableSeqfeature, tableSeqfeature.getColumn("rank"));
		this.createUniqueConstraint("null", tableSeqfeature, tableSeqfeature.getColumn("bioentry_id"), tableSeqfeature.getColumn("type_term_id"), tableSeqfeature.getColumn("source_term_id"), tableSeqfeature.getColumn("rank"));

		Table tableSeqfeatureRelationship = this.createTable("seqfeature_relationship");
		tableSeqfeatureRelationship.createColumn("seqfeature_relationship_id", new IntDataType());
		tableSeqfeatureRelationship.createColumn("object_seqfeature_id", new IntDataType());
		tableSeqfeatureRelationship.createColumn("subject_seqfeature_id", new IntDataType());
		tableSeqfeatureRelationship.createColumn("term_id", new IntDataType());
		tableSeqfeatureRelationship.createColumn("rank", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableSeqfeatureRelationship, tableSeqfeatureRelationship.getColumn("seqfeature_relationship_id"));
		this.createForeignKeyConstraint("FKterm_seqfeatrel", tableSeqfeatureRelationship, tableSeqfeatureRelationship.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKparentfeat_seqfeatrel", tableSeqfeatureRelationship, tableSeqfeatureRelationship.getColumn("object_seqfeature_id"), tableSeqfeature, tableSeqfeature.getColumn("seqfeature_id"));
		this.createForeignKeyConstraint("FKchildfeat_seqfeatrel", tableSeqfeatureRelationship, tableSeqfeatureRelationship.getColumn("subject_seqfeature_id"), tableSeqfeature, tableSeqfeature.getColumn("seqfeature_id"));
		this.createNotNullConstraint("null", tableSeqfeatureRelationship, tableSeqfeatureRelationship.getColumn("seqfeature_relationship_id"));
		this.createNotNullConstraint("null", tableSeqfeatureRelationship, tableSeqfeatureRelationship.getColumn("object_seqfeature_id"));
		this.createNotNullConstraint("null", tableSeqfeatureRelationship, tableSeqfeatureRelationship.getColumn("subject_seqfeature_id"));
		this.createNotNullConstraint("null", tableSeqfeatureRelationship, tableSeqfeatureRelationship.getColumn("term_id"));
		this.createUniqueConstraint("null", tableSeqfeatureRelationship, tableSeqfeatureRelationship.getColumn("object_seqfeature_id"), tableSeqfeatureRelationship.getColumn("subject_seqfeature_id"), tableSeqfeatureRelationship.getColumn("term_id"));

		Table tableSeqfeaturePath = this.createTable("seqfeature_path");
		tableSeqfeaturePath.createColumn("object_seqfeature_id", new IntDataType());
		tableSeqfeaturePath.createColumn("subject_seqfeature_id", new IntDataType());
		tableSeqfeaturePath.createColumn("term_id", new IntDataType());
		tableSeqfeaturePath.createColumn("distance", new IntDataType());
		this.createForeignKeyConstraint("FKterm_seqfeatpath", tableSeqfeaturePath, tableSeqfeaturePath.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKparentfeat_seqfeatpath", tableSeqfeaturePath, tableSeqfeaturePath.getColumn("object_seqfeature_id"), tableSeqfeature, tableSeqfeature.getColumn("seqfeature_id"));
		this.createForeignKeyConstraint("FKchildfeat_seqfeatpath", tableSeqfeaturePath, tableSeqfeaturePath.getColumn("subject_seqfeature_id"), tableSeqfeature, tableSeqfeature.getColumn("seqfeature_id"));
		this.createNotNullConstraint("null", tableSeqfeaturePath, tableSeqfeaturePath.getColumn("object_seqfeature_id"));
		this.createNotNullConstraint("null", tableSeqfeaturePath, tableSeqfeaturePath.getColumn("subject_seqfeature_id"));
		this.createNotNullConstraint("null", tableSeqfeaturePath, tableSeqfeaturePath.getColumn("term_id"));
		this.createUniqueConstraint("null", tableSeqfeaturePath, tableSeqfeaturePath.getColumn("object_seqfeature_id"), tableSeqfeaturePath.getColumn("subject_seqfeature_id"), tableSeqfeaturePath.getColumn("term_id"), tableSeqfeaturePath.getColumn("distance"));

		Table tableSeqfeatureQualifierValue = this.createTable("seqfeature_qualifier_value");
		tableSeqfeatureQualifierValue.createColumn("seqfeature_id", new IntDataType());
		tableSeqfeatureQualifierValue.createColumn("term_id", new IntDataType());
		tableSeqfeatureQualifierValue.createColumn("rank", new IntDataType());
		tableSeqfeatureQualifierValue.createColumn("value", new TextDataType());
		this.createPrimaryKeyConstraint("null", tableSeqfeatureQualifierValue, tableSeqfeatureQualifierValue.getColumn("seqfeature_id"), tableSeqfeatureQualifierValue.getColumn("term_id"), tableSeqfeatureQualifierValue.getColumn("rank"));
		this.createForeignKeyConstraint("FKterm_featqual", tableSeqfeatureQualifierValue, tableSeqfeatureQualifierValue.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createForeignKeyConstraint("FKseqfeature_featqual", tableSeqfeatureQualifierValue, tableSeqfeatureQualifierValue.getColumn("seqfeature_id"), tableSeqfeature, tableSeqfeature.getColumn("seqfeature_id"));
		this.createNotNullConstraint("null", tableSeqfeatureQualifierValue, tableSeqfeatureQualifierValue.getColumn("seqfeature_id"));
		this.createNotNullConstraint("null", tableSeqfeatureQualifierValue, tableSeqfeatureQualifierValue.getColumn("term_id"));
		this.createNotNullConstraint("null", tableSeqfeatureQualifierValue, tableSeqfeatureQualifierValue.getColumn("rank"));
		this.createNotNullConstraint("null", tableSeqfeatureQualifierValue, tableSeqfeatureQualifierValue.getColumn("value"));

		Table tableSeqfeatureDbxref = this.createTable("seqfeature_dbxref");
		tableSeqfeatureDbxref.createColumn("seqfeature_id", new IntDataType());
		tableSeqfeatureDbxref.createColumn("dbxref_id", new IntDataType());
		tableSeqfeatureDbxref.createColumn("rank", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableSeqfeatureDbxref, tableSeqfeatureDbxref.getColumn("seqfeature_id"), tableSeqfeatureDbxref.getColumn("dbxref_id"));
		this.createForeignKeyConstraint("FKseqfeature_feadblink", tableSeqfeatureDbxref, tableSeqfeatureDbxref.getColumn("seqfeature_id"), tableSeqfeature, tableSeqfeature.getColumn("seqfeature_id"));
		this.createForeignKeyConstraint("FKdbxref_feadblink", tableSeqfeatureDbxref, tableSeqfeatureDbxref.getColumn("dbxref_id"), tableDbxref, tableDbxref.getColumn("dbxref_id"));
		this.createNotNullConstraint("null", tableSeqfeatureDbxref, tableSeqfeatureDbxref.getColumn("seqfeature_id"));
		this.createNotNullConstraint("null", tableSeqfeatureDbxref, tableSeqfeatureDbxref.getColumn("dbxref_id"));

		Table tableLocation = this.createTable("location");
		tableLocation.createColumn("location_id", new IntDataType());
		tableLocation.createColumn("seqfeature_id", new IntDataType());
		tableLocation.createColumn("dbxref_id", new IntDataType());
		tableLocation.createColumn("term_id", new IntDataType());
		tableLocation.createColumn("start_pos", new IntDataType());
		tableLocation.createColumn("end_pos", new IntDataType());
		tableLocation.createColumn("strand", new IntDataType());
		tableLocation.createColumn("rank", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableLocation, tableLocation.getColumn("location_id"));
		this.createForeignKeyConstraint("FKseqfeature_location", tableLocation, tableLocation.getColumn("seqfeature_id"), tableSeqfeature, tableSeqfeature.getColumn("seqfeature_id"));
		this.createForeignKeyConstraint("FKdbxref_location", tableLocation, tableLocation.getColumn("dbxref_id"), tableDbxref, tableDbxref.getColumn("dbxref_id"));
		this.createForeignKeyConstraint("FKterm_featloc", tableLocation, tableLocation.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createNotNullConstraint("null", tableLocation, tableLocation.getColumn("location_id"));
		this.createNotNullConstraint("null", tableLocation, tableLocation.getColumn("seqfeature_id"));
		this.createNotNullConstraint("null", tableLocation, tableLocation.getColumn("strand"));
		this.createNotNullConstraint("null", tableLocation, tableLocation.getColumn("rank"));
		this.createUniqueConstraint("null", tableLocation, tableLocation.getColumn("seqfeature_id"), tableLocation.getColumn("rank"));

		Table tableLocationQualifierValue = this.createTable("location_qualifier_value");
		tableLocationQualifierValue.createColumn("location_id", new IntDataType());
		tableLocationQualifierValue.createColumn("term_id", new IntDataType());
		tableLocationQualifierValue.createColumn("value", new VarCharDataType(255));
		tableLocationQualifierValue.createColumn("int_value", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableLocationQualifierValue, tableLocationQualifierValue.getColumn("location_id"), tableLocationQualifierValue.getColumn("term_id"));
		this.createForeignKeyConstraint("FKfeatloc_locqual", tableLocationQualifierValue, tableLocationQualifierValue.getColumn("location_id"), tableLocation, tableLocation.getColumn("location_id"));
		this.createForeignKeyConstraint("FKterm_locqual", tableLocationQualifierValue, tableLocationQualifierValue.getColumn("term_id"), tableTerm, tableTerm.getColumn("term_id"));
		this.createNotNullConstraint("null", tableLocationQualifierValue, tableLocationQualifierValue.getColumn("location_id"));
		this.createNotNullConstraint("null", tableLocationQualifierValue, tableLocationQualifierValue.getColumn("term_id"));
		this.createNotNullConstraint("null", tableLocationQualifierValue, tableLocationQualifierValue.getColumn("value"));
	}
}

