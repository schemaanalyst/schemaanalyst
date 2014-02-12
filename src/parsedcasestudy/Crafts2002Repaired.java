package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * Crafts2002Repaired schema.
 * Java code originally generated: 2014/02/11 16:22:12
 *
 */

@SuppressWarnings("serial")
public class Crafts2002Repaired extends Schema {

	public Crafts2002Repaired() {
		super("Crafts2002Repaired");

		Table tableTblauthors = this.createTable("tblAuthors");
		tableTblauthors.createColumn("AuthorID", new IntDataType());
		tableTblauthors.createColumn("LastNameFirst", new TextDataType());
		tableTblauthors.createColumn("LastName", new TextDataType());
		tableTblauthors.createColumn("MiddleName", new TextDataType());
		tableTblauthors.createColumn("FirstName", new TextDataType());
		tableTblauthors.createColumn("StreetAddress", new TextDataType());
		tableTblauthors.createColumn("City", new TextDataType());
		tableTblauthors.createColumn("StateProvince", new TextDataType());
		tableTblauthors.createColumn("PostalCode", new TextDataType());
		tableTblauthors.createColumn("Country", new TextDataType());
		tableTblauthors.createColumn("HomePhone", new TextDataType());
		tableTblauthors.createColumn("Workphone", new TextDataType());
		tableTblauthors.createColumn("FaxPhone", new TextDataType());
		tableTblauthors.createColumn("BBSPhone", new TextDataType());
		tableTblauthors.createColumn("CISName", new TextDataType());
		tableTblauthors.createColumn("CISID", new TextDataType());
		tableTblauthors.createColumn("EMailAddress", new TextDataType());
		tableTblauthors.createColumn("WebSite", new TextDataType());
		tableTblauthors.createColumn("Notes", new TextDataType());
		this.createUniqueConstraint("tblAuthors_PrimaryKey", tableTblauthors, tableTblauthors.getColumn("AuthorID"));

		Table tableTblbookauthors = this.createTable("tblBookAuthors");
		tableTblbookauthors.createColumn("BookID", new IntDataType());
		tableTblbookauthors.createColumn("AuthorID", new IntDataType());

		Table tableTblbooksource = this.createTable("tblBookSource");
		tableTblbooksource.createColumn("BookID", new IntDataType());
		tableTblbooksource.createColumn("Source", new TextDataType());

		Table tableTblbookspecs = this.createTable("tblBookSpecs");
		tableTblbookspecs.createColumn("BookID", new IntDataType());
		tableTblbookspecs.createColumn("Specialty", new TextDataType());
		this.createUniqueConstraint("tblBookSpecs_PrimaryKey", tableTblbookspecs, tableTblbookspecs.getColumn("BookID"), tableTblbookspecs.getColumn("Specialty"));

		Table tableTblbooks = this.createTable("tblBooks");
		tableTblbooks.createColumn("BookID", new IntDataType());
		tableTblbooks.createColumn("Title", new TextDataType());
		tableTblbooks.createColumn("TopicID", new IntDataType());
		tableTblbooks.createColumn("CopyrightYear", new IntDataType());
		tableTblbooks.createColumn("ISBNNumber", new TextDataType());
		tableTblbooks.createColumn("Publisher", new TextDataType());
		tableTblbooks.createColumn("PlaceOfPublication", new TextDataType());
		tableTblbooks.createColumn("Translator", new TextDataType());
		tableTblbooks.createColumn("PurchasePrice", new TextDataType());
		tableTblbooks.createColumn("EditionNumber", new IntDataType());
		tableTblbooks.createColumn("CoverType", new TextDataType());
		tableTblbooks.createColumn("DatePurchased", new DateTimeDataType());
		tableTblbooks.createColumn("Pages", new IntDataType());
		tableTblbooks.createColumn("ShelfNumber", new IntDataType());
		tableTblbooks.createColumn("Notes", new TextDataType());
		this.createUniqueConstraint("tblBooks_PrimaryKey", tableTblbooks, tableTblbooks.getColumn("BookID"));

		Table tableTblbooksandvideos = this.createTable("tblBooksAndVideos");
		tableTblbooksandvideos.createColumn("BookID", new IntDataType());
		tableTblbooksandvideos.createColumn("Book", new IntDataType());
		tableTblbooksandvideos.createColumn("Video", new IntDataType());
		tableTblbooksandvideos.createColumn("Kit", new IntDataType());
		tableTblbooksandvideos.createColumn("Title", new TextDataType());
		tableTblbooksandvideos.createColumn("ISBN", new TextDataType());
		tableTblbooksandvideos.createColumn("Cover", new TextDataType());
		tableTblbooksandvideos.createColumn("Language", new TextDataType());
		tableTblbooksandvideos.createColumn("CD", new IntDataType());
		tableTblbooksandvideos.createColumn("Cost", new RealDataType());
		tableTblbooksandvideos.createColumn("CurrencyType", new TextDataType());
		tableTblbooksandvideos.createColumn("PublicationYear", new TextDataType());
		tableTblbooksandvideos.createColumn("Length", new TextDataType());
		tableTblbooksandvideos.createColumn("PublisherCode", new TextDataType());
		tableTblbooksandvideos.createColumn("Copyright_free", new IntDataType());
		tableTblbooksandvideos.createColumn("Modified", new DateTimeDataType());
		this.createUniqueConstraint("tblBooksAndVideos_PrimaryKey", tableTblbooksandvideos, tableTblbooksandvideos.getColumn("BookID"));

		Table tableTblinfo = this.createTable("tblInfo");
		tableTblinfo.createColumn("AppTitle", new TextDataType());
		tableTblinfo.createColumn("PrimaryForm", new TextDataType());
		tableTblinfo.createColumn("FormName", new TextDataType());
		tableTblinfo.createColumn("ReportName", new TextDataType());
		tableTblinfo.createColumn("Specialty", new TextDataType());
		tableTblinfo.createColumn("ReportMode", new IntDataType());
		tableTblinfo.createColumn("FromDate", new DateTimeDataType());
		tableTblinfo.createColumn("ToDate", new DateTimeDataType());
		tableTblinfo.createColumn("NumCopies", new IntDataType());
		tableTblinfo.createColumn("PaperSize", new IntDataType());
		tableTblinfo.createColumn("Orientation", new IntDataType());

		Table tableTblpublisherspecs = this.createTable("tblPublisherSpecs");
		tableTblpublisherspecs.createColumn("PublisherCode", new TextDataType());
		tableTblpublisherspecs.createColumn("Specialty", new TextDataType());
		this.createUniqueConstraint("tblPublisherSpecs_PrimaryKey", tableTblpublisherspecs, tableTblpublisherspecs.getColumn("PublisherCode"), tableTblpublisherspecs.getColumn("Specialty"));

		Table tableTblpublishers = this.createTable("tblPublishers");
		tableTblpublishers.createColumn("PublisherCode", new TextDataType());
		tableTblpublishers.createColumn("Pubname", new TextDataType());
		tableTblpublishers.createColumn("SalesStreetAddress", new TextDataType());
		tableTblpublishers.createColumn("SalesCity", new TextDataType());
		tableTblpublishers.createColumn("SalesProvince", new TextDataType());
		tableTblpublishers.createColumn("SalesPostcode", new TextDataType());
		tableTblpublishers.createColumn("SalesCountry", new TextDataType());
		tableTblpublishers.createColumn("EditorialStreetAddress", new TextDataType());
		tableTblpublishers.createColumn("EditorialCity", new TextDataType());
		tableTblpublishers.createColumn("EditorialProvince", new TextDataType());
		tableTblpublishers.createColumn("EditorialPostcode", new TextDataType());
		tableTblpublishers.createColumn("EditorialCountry", new TextDataType());
		tableTblpublishers.createColumn("GeneralPhone", new TextDataType());
		tableTblpublishers.createColumn("SalesPhone", new TextDataType());
		tableTblpublishers.createColumn("FaxPhone", new TextDataType());
		tableTblpublishers.createColumn("TechPhone", new TextDataType());
		tableTblpublishers.createColumn("BBSPhone", new TextDataType());
		tableTblpublishers.createColumn("CustServPhone", new TextDataType());
		tableTblpublishers.createColumn("Contact", new TextDataType());
		tableTblpublishers.createColumn("CISName", new TextDataType());
		tableTblpublishers.createColumn("CISID", new TextDataType());
		tableTblpublishers.createColumn("EMail", new TextDataType());
		tableTblpublishers.createColumn("WebSite", new TextDataType());
		this.createUniqueConstraint("tblPublishers_PrimaryKey", tableTblpublishers, tableTblpublishers.getColumn("PublisherCode"));

		Table tableTblsourcespecs = this.createTable("tblSourceSpecs");
		tableTblsourcespecs.createColumn("SourceID", new TextDataType());
		tableTblsourcespecs.createColumn("Specialty", new TextDataType());
		this.createUniqueConstraint("tblSourceSpecs_PrimaryKey", tableTblsourcespecs, tableTblsourcespecs.getColumn("SourceID"), tableTblsourcespecs.getColumn("Specialty"));

		Table tableTblsources = this.createTable("tblSources");
		tableTblsources.createColumn("SourceID", new TextDataType());
		tableTblsources.createColumn("SourceName", new TextDataType());
		tableTblsources.createColumn("Retail", new IntDataType());
		tableTblsources.createColumn("Wholesale", new IntDataType());
		tableTblsources.createColumn("Contact", new TextDataType());
		tableTblsources.createColumn("StreetAddress", new TextDataType());
		tableTblsources.createColumn("City", new TextDataType());
		tableTblsources.createColumn("StateProvince", new TextDataType());
		tableTblsources.createColumn("PostalCode", new TextDataType());
		tableTblsources.createColumn("Country", new TextDataType());
		tableTblsources.createColumn("GeneralPhone", new TextDataType());
		tableTblsources.createColumn("SalesPhone", new TextDataType());
		tableTblsources.createColumn("FaxPhone", new TextDataType());
		tableTblsources.createColumn("TechPhone", new TextDataType());
		tableTblsources.createColumn("BBSPhone", new TextDataType());
		tableTblsources.createColumn("CustServPhone", new TextDataType());
		tableTblsources.createColumn("CISName", new TextDataType());
		tableTblsources.createColumn("Amex", new IntDataType());
		tableTblsources.createColumn("MC", new IntDataType());
		tableTblsources.createColumn("Visa", new IntDataType());
		tableTblsources.createColumn("Discover", new IntDataType());
		tableTblsources.createColumn("Diners", new IntDataType());
		tableTblsources.createColumn("None", new IntDataType());
		tableTblsources.createColumn("TechSupport", new IntDataType());
		tableTblsources.createColumn("TechHours", new TextDataType());
		tableTblsources.createColumn("Catalog", new IntDataType());
		tableTblsources.createColumn("CatCost", new RealDataType());
		tableTblsources.createColumn("CurrencyType", new TextDataType());
		tableTblsources.createColumn("Refundable", new IntDataType());
		tableTblsources.createColumn("Showroom", new IntDataType());
		tableTblsources.createColumn("Notes", new TextDataType());
		tableTblsources.createColumn("CISID", new TextDataType());
		tableTblsources.createColumn("EMail", new TextDataType());
		tableTblsources.createColumn("WebSite", new TextDataType());
		tableTblsources.createColumn("Modified", new DateTimeDataType());
		this.createUniqueConstraint("tblSources_PrimaryKey", tableTblsources, tableTblsources.getColumn("SourceID"));

		Table tableTblspecialties = this.createTable("tblSpecialties");
		tableTblspecialties.createColumn("Speccode", new TextDataType());
		tableTblspecialties.createColumn("Specdesc", new TextDataType());
		this.createUniqueConstraint("tblSpecialties_PrimaryKey", tableTblspecialties, tableTblspecialties.getColumn("Speccode"));

		Table tableTlkpforms = this.createTable("tlkpForms");
		tableTlkpforms.createColumn("ObjectName", new TextDataType());
		tableTlkpforms.createColumn("DisplayName", new TextDataType());
		tableTlkpforms.createColumn("RecordSource", new TextDataType());

		Table tableTlkpletters = this.createTable("tlkpLetters");
		tableTlkpletters.createColumn("FileName", new TextDataType());
		tableTlkpletters.createColumn("DisplayName", new TextDataType());

		Table tableTlkpreports = this.createTable("tlkpReports");
		tableTlkpreports.createColumn("ObjectName", new TextDataType());
		tableTlkpreports.createColumn("DisplayName", new TextDataType());
		tableTlkpreports.createColumn("RecordSource", new TextDataType());
		tableTlkpreports.createColumn("Width", new RealDataType());
	}
}

