package paper.datagenerationjv;

/**
 * Created by phil on 23/08/2014.
 */
public class NistWeatherProblem extends RunCoverageExpt {

    public NistWeatherProblem(String resultsDatabaseFileName) {
        super(resultsDatabaseFileName);
    }

    public static void main(String[] args) {
        NistWeatherProblem n = new NistWeatherProblem(args[0]);

        n.expt("NistWeather", "AICC", "random", "SQLite", 2);
    }


}
