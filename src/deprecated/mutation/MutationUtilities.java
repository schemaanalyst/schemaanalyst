package deprecated.mutation;

public class MutationUtilities {

    /**
     * Strip off the prefix from the name of a case study, helpful for producing
     * better formatting graphs in R
     */
    public static String removePrefixFromCaseStudyName(String name) {
        int indexOfPeriod = name.lastIndexOf(".");
        return name.substring(indexOfPeriod + 1);
    }

    /**
     * Strip off the prefix and and suffix from the name of a case study,
     * helpful for producing better formatting graphs in R
     */
    public static String removePrefixAndSuffixFromCaseStudyName(String name) {
        int indexOfLastPeriod = name.lastIndexOf(".");
        int indexOfFirstPeriod = name.indexOf(".");

        return name.substring(indexOfFirstPeriod + 1, indexOfLastPeriod);
    }
}