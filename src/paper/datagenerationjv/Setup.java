package paper.datagenerationjv;

import org.schemaanalyst.sqlrepresentation.Schema;

/**
 * Created by phil on 07/04/2014.
 */
public class Setup {

    public static final String[] SUBJECTS = {
            "BankAccount",
            "BookTown",
            "Cloc",
            "CoffeeOrders",
            "CustomerOrder",
            "DellStore",
            "Employee",
            "Examination",
            "Flights",
            "FrenchTowns",
            "Inventory",
            "Iso3166",
            "JWhoisServer",
            "NistDML181",
            "NistDML182",
            "NistDML183",
            "NistWeather",
            "NistXTS748",
            "NistXTS749",
            "Person",
            "Products",
            "RiskIt",
            "StudentResidence",
            "UnixUsage",
            "Usda"
    };

    public static final String[] CRITERIA = {
            "amplifiedConstraintCACWithNullAndUniqueColumnCACCoverage",
            "constraintCACWithNullAndUniqueColumnCACCoverage",
            "amplifiedConstraintCACCoverage",
            "constraintCACCoverage",
            "nullAndUniqueColumnCACCoverage"
    };

    public static final String[] TECHNIQUES = {
            "avsDefaults",
            "avs",
            "directedRandomDefaults",
            "directedRandom",
            "randomDefaults",
            "random"
    };

    public static final long[] SEEDS = {
            -746775254026156467L,
            8894474108780370223L,
            -6214409277416607513L,
            -5757974822948036033L,
            2499830650202821811L,
            -4521612858557550342L,
            6477204520445086666L,
            2151405238229707438L,
            -8323712357294655711L,
            -5210278879147068334L,
            2602243902512604723L,
            1125421264746519754L,
            -7929784454154595108L,
            -5438154230330054808L,
            -3692944113291893880L,
            -7590026984897512778L,
            6102430788920880524L,
            6356107529541056370L,
            -8303475394913082925L,
            2829875433662938073L,
            6353819979203641789L,
            1558147754011023144L,
            -1865051779606222269L,
            934831394913087839L,
            -8148871454783190178L,
            -4837536107832857785L,
            7888325975353700777L,
            6205238278389054134L,
            -4669266926510700988L,
            -3401868706524788975L
    };

    public static Schema instantiateSchema(String schemaName) {
        try {
            return (Schema) Class.forName(schemaName).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
