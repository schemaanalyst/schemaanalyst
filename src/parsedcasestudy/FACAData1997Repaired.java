package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * FACAData1997Repaired schema.
 * Java code originally generated: 2013/12/13 10:00:46
 *
 */

@SuppressWarnings("serial")
public class FACAData1997Repaired extends Schema {

	public FACAData1997Repaired() {
		super("FACAData1997Repaired");

		Table tableAgencies97 = this.createTable("Agencies97");
		tableAgencies97.createColumn("AID", new IntDataType());
		tableAgencies97.createColumn("ANo", new IntDataType());
		tableAgencies97.createColumn("FY", new TextDataType());
		tableAgencies97.createColumn("AgencyAbbr", new TextDataType());
		tableAgencies97.createColumn("AgencyName", new TextDataType());
		tableAgencies97.createColumn("Address1", new TextDataType());
		tableAgencies97.createColumn("Address2", new TextDataType());
		tableAgencies97.createColumn("City", new TextDataType());
		tableAgencies97.createColumn("State", new TextDataType());
		tableAgencies97.createColumn("Zipcode", new TextDataType());
		tableAgencies97.createColumn("Classification", new TextDataType());
		tableAgencies97.createColumn("Prefix", new TextDataType());
		tableAgencies97.createColumn("FirstName", new TextDataType());
		tableAgencies97.createColumn("MiddleName", new TextDataType());
		tableAgencies97.createColumn("LastName", new TextDataType());
		tableAgencies97.createColumn("Suffix", new TextDataType());
		tableAgencies97.createColumn("Title", new TextDataType());
		tableAgencies97.createColumn("Phone", new TextDataType());
		tableAgencies97.createColumn("Fax", new TextDataType());
		tableAgencies97.createColumn("EMail", new TextDataType());
		tableAgencies97.createColumn("AgencyURL", new TextDataType());
		tableAgencies97.createColumn("DMPrefix", new TextDataType());
		tableAgencies97.createColumn("DMFirstName", new TextDataType());
		tableAgencies97.createColumn("DMMiddleName", new TextDataType());
		tableAgencies97.createColumn("DMLastName", new TextDataType());
		tableAgencies97.createColumn("DMSuffix", new TextDataType());
		tableAgencies97.createColumn("DMTitle", new TextDataType());
		tableAgencies97.createColumn("DMPhone", new TextDataType());
		tableAgencies97.createColumn("DMFax", new TextDataType());
		tableAgencies97.createColumn("DMEMail", new TextDataType());
		this.createUniqueConstraint("Agencies97_PrimaryKey", tableAgencies97, tableAgencies97.getColumn("AID"));

		Table tableCommittees97 = this.createTable("Committees97");
		tableCommittees97.createColumn("CID", new IntDataType());
		tableCommittees97.createColumn("CNo", new IntDataType());
		tableCommittees97.createColumn("AID", new IntDataType());
		tableCommittees97.createColumn("ANo", new IntDataType());
		tableCommittees97.createColumn("GID", new IntDataType());
		tableCommittees97.createColumn("FY", new TextDataType());
		tableCommittees97.createColumn("CommitteeName", new TextDataType());
		tableCommittees97.createColumn("OriginalEstablishmentDate", new DateTimeDataType());
		tableCommittees97.createColumn("TerminationDate", new DateTimeDataType());
		tableCommittees97.createColumn("Prefix", new TextDataType());
		tableCommittees97.createColumn("FirstName", new TextDataType());
		tableCommittees97.createColumn("MiddleName", new TextDataType());
		tableCommittees97.createColumn("LastName", new TextDataType());
		tableCommittees97.createColumn("Suffix", new TextDataType());
		tableCommittees97.createColumn("Title", new TextDataType());
		tableCommittees97.createColumn("Phone", new TextDataType());
		tableCommittees97.createColumn("Fax", new TextDataType());
		tableCommittees97.createColumn("EMail", new TextDataType());
		tableCommittees97.createColumn("PCID", new IntDataType());
		tableCommittees97.createColumn("DMPrefix", new TextDataType());
		tableCommittees97.createColumn("DMFirstName", new TextDataType());
		tableCommittees97.createColumn("DMMiddleName", new TextDataType());
		tableCommittees97.createColumn("DMLastName", new TextDataType());
		tableCommittees97.createColumn("DMSuffix", new TextDataType());
		tableCommittees97.createColumn("DMTitle", new TextDataType());
		tableCommittees97.createColumn("DMPhone", new TextDataType());
		tableCommittees97.createColumn("DMFax", new TextDataType());
		tableCommittees97.createColumn("DMEMail", new TextDataType());
		tableCommittees97.createColumn("AdminInactive", new TextDataType());
		this.createUniqueConstraint("Committees97_CID", tableCommittees97, tableCommittees97.getColumn("CID"));
		this.createUniqueConstraint("Committees97_PrimaryKey", tableCommittees97, tableCommittees97.getColumn("CID"));

		Table tableCommitteesinterestareas = this.createTable("CommitteesInterestAreas");
		tableCommitteesinterestareas.createColumn("CommitteesInterestAreasID", new IntDataType());
		tableCommitteesinterestareas.createColumn("CNo", new IntDataType());
		tableCommitteesinterestareas.createColumn("InterestAreasID", new IntDataType());
		this.createUniqueConstraint("CommitteesInterestAreas_PrimaryKey", tableCommitteesinterestareas, tableCommitteesinterestareas.getColumn("CommitteesInterestAreasID"));

		Table tableCosts97 = this.createTable("Costs97");
		tableCosts97.createColumn("CostsID", new IntDataType());
		tableCosts97.createColumn("CID", new IntDataType());
		tableCosts97.createColumn("CNo", new IntDataType());
		tableCosts97.createColumn("FY", new TextDataType());
		tableCosts97.createColumn("PersPymtNonFedMemCurFY", new IntDataType());
		tableCosts97.createColumn("PersPymtFedMemCurFY", new IntDataType());
		tableCosts97.createColumn("PersPymtFedStaffCurFY", new IntDataType());
		tableCosts97.createColumn("PersPymtConsultCurFY", new IntDataType());
		tableCosts97.createColumn("TravPDiemNonFedMemCurFY", new IntDataType());
		tableCosts97.createColumn("TravPDiemFedMemCurFY", new IntDataType());
		tableCosts97.createColumn("TravPDiemFedStaffCurFY", new IntDataType());
		tableCosts97.createColumn("TravPDiemConsultCurFY", new IntDataType());
		tableCosts97.createColumn("OtherCurFY", new IntDataType());
		tableCosts97.createColumn("TotalActual", new IntDataType());
		tableCosts97.createColumn("ActualFedStaffSupportCurrentYr", new RealDataType());
		tableCosts97.createColumn("PersPymtNonFedMemEstFY", new IntDataType());
		tableCosts97.createColumn("PersPymtFedMemEstFY", new IntDataType());
		tableCosts97.createColumn("PersPymtFedStaffEstFY", new IntDataType());
		tableCosts97.createColumn("PersPymtConsultEstFY", new IntDataType());
		tableCosts97.createColumn("TravPDiemNonFedMemEstFY", new IntDataType());
		tableCosts97.createColumn("TravPDiemFedMemEstFY", new IntDataType());
		tableCosts97.createColumn("TravPDiemFedStaffEstFY", new IntDataType());
		tableCosts97.createColumn("TravPDiemConsultEstFY", new IntDataType());
		tableCosts97.createColumn("OtherEstFY", new IntDataType());
		tableCosts97.createColumn("TotalEstimated", new IntDataType());
		tableCosts97.createColumn("EstimatedFedStaffSupportNextYr", new RealDataType());
		this.createUniqueConstraint("Costs97_Committees97Costs97", tableCosts97, tableCosts97.getColumn("CID"));
		this.createUniqueConstraint("Costs97_PrimaryKey", tableCosts97, tableCosts97.getColumn("CostsID"));

		Table tableGeneralinformation97 = this.createTable("GeneralInformation97");
		tableGeneralinformation97.createColumn("ID", new IntDataType());
		tableGeneralinformation97.createColumn("CID", new IntDataType());
		tableGeneralinformation97.createColumn("CNo", new IntDataType());
		tableGeneralinformation97.createColumn("FY", new TextDataType());
		tableGeneralinformation97.createColumn("DepartmentOrAgency", new TextDataType());
		tableGeneralinformation97.createColumn("NewCommittee", new TextDataType());
		tableGeneralinformation97.createColumn("CurrentCharterDate", new DateTimeDataType());
		tableGeneralinformation97.createColumn("DateOfRenewalCharter", new DateTimeDataType());
		tableGeneralinformation97.createColumn("DateToTerminate", new DateTimeDataType());
		tableGeneralinformation97.createColumn("TerminatedThisFY", new TextDataType());
		tableGeneralinformation97.createColumn("SpecificTerminationAuthority", new TextDataType());
		tableGeneralinformation97.createColumn("ActualTerminationDate", new DateTimeDataType());
		tableGeneralinformation97.createColumn("EstablishmentAuthority", new TextDataType());
		tableGeneralinformation97.createColumn("SpecificEstablishmentAuthority", new TextDataType());
		tableGeneralinformation97.createColumn("EffectiveDateOfAuthority", new DateTimeDataType());
		tableGeneralinformation97.createColumn("CommitteeType", new TextDataType());
		tableGeneralinformation97.createColumn("Presidential", new TextDataType());
		tableGeneralinformation97.createColumn("CommitteeFunction", new TextDataType());
		tableGeneralinformation97.createColumn("CommitteeStatus", new TextDataType());
		tableGeneralinformation97.createColumn("CommitteeURL", new TextDataType());
		tableGeneralinformation97.createColumn("ExemptRenew", new TextDataType());
		tableGeneralinformation97.createColumn("PresidentialAppointments", new TextDataType());
		tableGeneralinformation97.createColumn("MaxNumberofMembers", new TextDataType());
		this.createUniqueConstraint("GeneralInformation97_Committees97GeneralInformation97", tableGeneralinformation97, tableGeneralinformation97.getColumn("CID"));
		this.createUniqueConstraint("GeneralInformation97_PrimaryKey", tableGeneralinformation97, tableGeneralinformation97.getColumn("ID"));

		Table tableInterestareas = this.createTable("InterestAreas");
		tableInterestareas.createColumn("InterestAreasID", new IntDataType());
		tableInterestareas.createColumn("CategoryFull", new TextDataType());
		tableInterestareas.createColumn("Category", new TextDataType());
		tableInterestareas.createColumn("Area", new TextDataType());
		this.createUniqueConstraint("InterestAreas_Area", tableInterestareas, tableInterestareas.getColumn("Area"));
		this.createUniqueConstraint("InterestAreas_PrimaryKey", tableInterestareas, tableInterestareas.getColumn("InterestAreasID"));

		Table tableJustifications97 = this.createTable("Justifications97");
		tableJustifications97.createColumn("ID", new IntDataType());
		tableJustifications97.createColumn("CID", new IntDataType());
		tableJustifications97.createColumn("Cno", new IntDataType());
		tableJustifications97.createColumn("FY", new TextDataType());
		tableJustifications97.createColumn("AccomplishesPurpose", new TextDataType());
		tableJustifications97.createColumn("BalancedMembership", new TextDataType());
		tableJustifications97.createColumn("FrequencyRelevance", new TextDataType());
		tableJustifications97.createColumn("WhyThisCommittee", new TextDataType());
		tableJustifications97.createColumn("WhyCloseMeetings", new TextDataType());
		this.createUniqueConstraint("Justifications97_Committees97Justifications97", tableJustifications97, tableJustifications97.getColumn("CID"));
		this.createUniqueConstraint("Justifications97_PrimaryKey", tableJustifications97, tableJustifications97.getColumn("ID"));

		Table tableLkup = this.createTable("LkUp");
		tableLkup.createColumn("LkUpId", new IntDataType());
		tableLkup.createColumn("LkUpType", new TextDataType());
		tableLkup.createColumn("LkUpValue", new TextDataType());
		tableLkup.createColumn("LkupSort", new IntDataType());
		this.createUniqueConstraint("LkUp_PrimaryKey", tableLkup, tableLkup.getColumn("LkUpId"));

		Table tableMeetings97 = this.createTable("Meetings97");
		tableMeetings97.createColumn("ID", new IntDataType());
		tableMeetings97.createColumn("CID", new IntDataType());
		tableMeetings97.createColumn("CNo", new IntDataType());
		tableMeetings97.createColumn("FY", new TextDataType());
		tableMeetings97.createColumn("Purpose", new TextDataType());
		tableMeetings97.createColumn("MeetingStartDate", new DateTimeDataType());
		tableMeetings97.createColumn("MeetingStopDate", new DateTimeDataType());
		tableMeetings97.createColumn("MeetingType", new TextDataType());
		tableMeetings97.createColumn("MinutesLink", new TextDataType());
		tableMeetings97.createColumn("Location", new TextDataType());
		this.createUniqueConstraint("Meetings97_PrimaryKey", tableMeetings97, tableMeetings97.getColumn("ID"));

		Table tableMembers97 = this.createTable("Members97");
		tableMembers97.createColumn("MembersID", new IntDataType());
		tableMembers97.createColumn("CID", new IntDataType());
		tableMembers97.createColumn("CNo", new IntDataType());
		tableMembers97.createColumn("FY", new TextDataType());
		tableMembers97.createColumn("Prefix", new TextDataType());
		tableMembers97.createColumn("FirstName", new TextDataType());
		tableMembers97.createColumn("MiddleName", new TextDataType());
		tableMembers97.createColumn("LastName", new TextDataType());
		tableMembers97.createColumn("Suffix", new TextDataType());
		tableMembers97.createColumn("Chairperson", new TextDataType());
		tableMembers97.createColumn("OccupationOrAffiliation", new TextDataType());
		tableMembers97.createColumn("StartDate", new DateTimeDataType());
		tableMembers97.createColumn("EndDate", new DateTimeDataType());
		tableMembers97.createColumn("AppointmentType", new TextDataType());
		tableMembers97.createColumn("AppointmentTerm", new TextDataType());
		tableMembers97.createColumn("PayPlan", new TextDataType());
		tableMembers97.createColumn("PaySource", new TextDataType());
		tableMembers97.createColumn("MemberDesignation", new TextDataType());
		tableMembers97.createColumn("RepresentedGroup", new TextDataType());
		this.createUniqueConstraint("Members97_PrimaryKey", tableMembers97, tableMembers97.getColumn("MembersID"));

		Table tableRecommendations97 = this.createTable("Recommendations97");
		tableRecommendations97.createColumn("ID", new IntDataType());
		tableRecommendations97.createColumn("CID", new IntDataType());
		tableRecommendations97.createColumn("CNo", new IntDataType());
		tableRecommendations97.createColumn("FY", new TextDataType());
		tableRecommendations97.createColumn("Recommendation", new TextDataType());
		tableRecommendations97.createColumn("LegislationRequired", new TextDataType());
		tableRecommendations97.createColumn("LegislationStatus", new TextDataType());
		tableRecommendations97.createColumn("Remarks", new TextDataType());
		this.createUniqueConstraint("Recommendations97_Committees97Recommendations97", tableRecommendations97, tableRecommendations97.getColumn("CID"));
		this.createUniqueConstraint("Recommendations97_PrimaryKey", tableRecommendations97, tableRecommendations97.getColumn("ID"));

		Table tableReports97 = this.createTable("Reports97");
		tableReports97.createColumn("ID", new IntDataType());
		tableReports97.createColumn("CID", new IntDataType());
		tableReports97.createColumn("CNo", new IntDataType());
		tableReports97.createColumn("FY", new TextDataType());
		tableReports97.createColumn("ReportTitle", new TextDataType());
		tableReports97.createColumn("ReportDate", new DateTimeDataType());
		tableReports97.createColumn("ReportLink", new TextDataType());
		tableReports97.createColumn("IsPresidentialActionRequired", new TextDataType());
		this.createUniqueConstraint("Reports97_PrimaryKey", tableReports97, tableReports97.getColumn("ID"));
	}
}

