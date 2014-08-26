package paper.datagenerationjv;

/**
 * Created by phil on 23/08/2014.
 */
public class InvestigateExptProblem extends RunCoverageExpt {

    public InvestigateExptProblem(String resultsDatabaseFileName) {
        super(resultsDatabaseFileName);
    }

    public static void main(String[] args) {
        InvestigateExptProblem n = new InvestigateExptProblem(args[0]);

        n.expt("BookTown", "APC", "random", "HyperSQL", 16);
    }


}
