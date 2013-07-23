package org.schemaanalyst.mutation;

public class MutationUtilities {

    /**
     * Squash the description of a mutant type by removing all of the spaces
     */
    public static String squashMutantDescription(String description) {
        return description.replace(" ", "");
    }

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

        System.out.println(name.charAt(indexOfFirstPeriod));
        System.out.println(name.charAt(indexOfLastPeriod));

        return name.substring(indexOfFirstPeriod + 1, indexOfLastPeriod);
    }
}