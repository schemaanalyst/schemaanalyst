package deprecated.test.mutation;

import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.*;
import deprecated.mutation.DBMonsterSQLExecutionReport;
import deprecated.mutation.MutantRecord;
import deprecated.mutation.MutantReport;
import deprecated.mutation.MutationTypeStatusSummary;
import deprecated.mutation.MutationUtilities;
import deprecated.mutation.SQLExecutionRecord;
import deprecated.mutation.SQLExecutionReport;

public class TestMutationReport {

    @Test
    public void testMutantReportNumericalIdentifier() {
        MutantReport.reinitializeNumericalIdentifier();
        MutantReport report = new MutantReport();
        assertEquals(1, report.getNumericalIdentifier());
    }

    @Test
    public void testMutantReportNumericalIdentifierTwoMutantReports() {
        MutantReport.reinitializeNumericalIdentifier();

        MutantReport report1 = new MutantReport();
        assertEquals(1, report1.getNumericalIdentifier());

        MutantReport report2 = new MutantReport();
        assertEquals(2, report2.getNumericalIdentifier());
        assertEquals(1, report1.getNumericalIdentifier());
    }

    @Test
    public void testMutantReportNumericalIdentifierThreeMutantReports() {
        MutantReport.reinitializeNumericalIdentifier();

        MutantReport report1 = new MutantReport();
        assertEquals(1, report1.getNumericalIdentifier());

        MutantReport report2 = new MutantReport();
        assertEquals(2, report2.getNumericalIdentifier());
        assertEquals(1, report1.getNumericalIdentifier());

        MutantReport report3 = new MutantReport();
        assertEquals(3, report3.getNumericalIdentifier());
        assertEquals(2, report2.getNumericalIdentifier());
        assertEquals(1, report1.getNumericalIdentifier());
    }

    @Test
    public void testMutantReportInitialCreateTableState() {
        MutantReport report = new MutantReport();
        ArrayList<SQLExecutionRecord> list = new ArrayList<>();
        assertEquals(report.getCreateTableStatements(), list);
    }

    @Test
    public void testMutantReportInitialMutantRecordState() {
        MutantReport report = new MutantReport();
        ArrayList<MutantRecord> list = new ArrayList<>();
        assertEquals(report.getMutantStatements(), list);
    }

    @Test
    public void testSQLExecutionReportInitialStateWithEmptyLists() {
        SQLExecutionReport report = new SQLExecutionReport();
        ArrayList<SQLExecutionRecord> list = new ArrayList<>();
        assertEquals(report.getCreateTableStatements(), list);
        assertEquals(report.getInsertStatements(), list);
    }

    @Test
    public void testDBMonsertSQLExecutionReportInitialStateWithEmptyLists() {
        DBMonsterSQLExecutionReport report = new DBMonsterSQLExecutionReport();
        ArrayList<SQLExecutionRecord> list = new ArrayList<>();
        assertEquals(report.getCreateTableStatements(), list);
        assertEquals(report.getInsertStatements(), list);
        assertEquals(report.getSelectStatements(), list);
    }

    @Test
    public void testMutantTypeSummaryInformationKillingTheMutants() {
        MutationTypeStatusSummary summary = new MutationTypeStatusSummary();
        summary.killed("not null");
        summary.killed("primary key");
        summary.killed("not null");
        assertEquals(2, summary.getKilledCount("not null"));
        assertEquals(1, summary.getKilledCount("primary key"));
    }

    @Test
    public void testMutantTypeSummaryInformationNotKillingTheMutants() {
        MutationTypeStatusSummary summary = new MutationTypeStatusSummary();
        summary.alive("not null");
        summary.alive("primary key");
        summary.alive("not null");
        assertEquals(2, summary.getAliveCount("not null"));
        assertEquals(1, summary.getAliveCount("primary key"));
    }

    @Test
    public void testMutantTypeSummaryInformationStillBornMutants() {
        MutationTypeStatusSummary summary = new MutationTypeStatusSummary();
        summary.stillBorn("not null");
        summary.stillBorn("primary key");
        summary.stillBorn("not null");
        assertEquals(2, summary.getStillBornCount("not null"));
        assertEquals(1, summary.getStillBornCount("primary key"));
    }

    @Test
    public void testMutantTypeSummaryInformationKillingAndNotKillingTheMutants() {
        MutationTypeStatusSummary summary = new MutationTypeStatusSummary();
        summary.alive("not null");
        summary.killed("not null");
        summary.alive("primary key");
        summary.alive("not null");
        assertEquals(2, summary.getAliveCount("not null"));
        assertEquals(1, summary.getKilledCount("not null"));
        assertEquals(1, summary.getAliveCount("primary key"));
    }

    @Test
    public void testMutantTypeSummaryInformationKillingAndNotKillingTheMutantsAndStillBornForGoodMeasure() {
        MutationTypeStatusSummary summary = new MutationTypeStatusSummary();
        summary.alive("not null");
        summary.killed("not null");
        summary.stillBorn("not null");
        summary.alive("primary key");
        summary.alive("not null");
        assertEquals(2, summary.getAliveCount("not null"));
        assertEquals(1, summary.getKilledCount("not null"));
        assertEquals(1, summary.getStillBornCount("not null"));
        assertEquals(1, summary.getAliveCount("primary key"));
    }

    @Test
    public void testMutantTypeSummaryInformationKillingAndNotKillingTheMutantsAndStillBornForGoodMeasureGetTheList() {
        MutationTypeStatusSummary summary = new MutationTypeStatusSummary();
        summary.alive("not null");
        summary.killed("not null");
        summary.stillBorn("not null");
        summary.alive("primary key");
        summary.alive("not null");
        assertEquals(2, summary.getAliveCount("not null"));
        assertEquals(1, summary.getKilledCount("not null"));
        assertEquals(1, summary.getStillBornCount("not null"));
        assertEquals(1, summary.getAliveCount("primary key"));
        assertTrue(summary.getMutantTypes().contains("not null"));
        assertTrue(!summary.getMutantTypes().contains("foreign key"));
        assertTrue(summary.getMutantTypes().contains("primary key"));
        assertTrue(!summary.getMutantTypes().contains("unique"));
    }

    @Test
    public void testPrefixRemovalForCaseStudyName() {
        String regularName = "casestudy.BankAccount";
        String expectedName = "BankAccount";
        String actualName = MutationUtilities.removePrefixFromCaseStudyName(regularName);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void testPrefixAndSuffixRemovalForCaseStudyNameBankAccount() {
        String regularName = "casestudy.BankAccount.dat";
        String expectedName = "BankAccount";
        String actualName = MutationUtilities.removePrefixAndSuffixFromCaseStudyName(regularName);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void testPrefixAndSuffixRemovalForCaseStudyNameCloc() {
        String regularName = "casestudy.Cloc.dat";
        String expectedName = "Cloc";
        String actualName = MutationUtilities.removePrefixAndSuffixFromCaseStudyName(regularName);
        assertEquals(expectedName, actualName);
    }
}
