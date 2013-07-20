package org.schemaanalyst.test.mutation;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.*;

import org.schemaanalyst.mutation.MutationReport;
import org.schemaanalyst.mutation.MutationReportScores;
import org.schemaanalyst.mutation.MutationReportScore;
import org.schemaanalyst.deprecated.configuration.Configuration;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;

public class TestMutationAnalysisProcess {

    @Test
    public void testSchemaAnalystWithBankAccountGetsCorrectScores() {
        // run the task from the build file
        runTask(new File("build.xml"), "schemaanalyst");

        // set the properties from the command line into Configuration
        Configuration.project = System.getProperty("project");
        Configuration.mutationreport_mrp = System.getProperty("mutationreport_mrp");

        // configuration the MutationReport instances and reload specified default
        MutationReport.configureForReportLoading();
        MutationReport reportReloaded = MutationReport.loadSpecifiedDefault();

        // execute the assertions about the mutation scores
        MutationReportScores scores = reportReloaded.getScores();
        MutationReportScore mutationScore = scores.get("mutationScore");
        assertEquals(10, mutationScore.getNumerator());
        assertEquals(15, mutationScore.getDenominator());
        assertEquals("mutationScore", mutationScore.getName());
    }

    /**
     * Run an ant build task from the Java program
     */
    public static void runTask(File buildFile, String targetName) {
        ProjectHelper projectHelper = ProjectHelper.getProjectHelper();
        Project project = new Project();

        BuildLogger logger = new DefaultLogger();
        // logger.setOutputPrintStream(System.out);
        // logger.setErrorPrintStream(System.out);
        // logger.setMessageOutputLevel(Project.MSG_INFO);

        project.setUserProperty("ant.file", buildFile.getAbsolutePath());

        project.addBuildListener(logger);
        project.init();

        project.addReference("ant.projectHelper", projectHelper);
        projectHelper.parse(project, buildFile);
        try {
            project.executeTarget(targetName);
        } catch (BuildException e) {
            throw new RuntimeException(String.format("Run %s [%s] failed: %s", buildFile, targetName, e.getMessage()), e);
        }
    }
}