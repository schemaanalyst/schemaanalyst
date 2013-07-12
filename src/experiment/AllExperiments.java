package experiment;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import experiment.Experiments;

/**
 * Run all of the experiments.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    Experiments.class,})
public class AllExperiments {
}