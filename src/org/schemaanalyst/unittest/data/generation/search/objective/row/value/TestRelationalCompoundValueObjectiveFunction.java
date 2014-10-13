package org.schemaanalyst.unittest.data.generation.search.objective.row.value;

import org.junit.Test;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.generation.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.DistanceObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.value.RelationalValueObjectiveFunction;

import static java.math.BigDecimal.ONE;
import static org.schemaanalyst.data.generation.search.objective.value.RelationalNumericValueObjectiveFunction.K;
import static org.schemaanalyst.logic.RelationalOperator.*;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertEquivalent;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertOptimal;

public class TestRelationalCompoundValueObjectiveFunction {

    @Test
    public void equals_StringsEqual() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("hello"), EQUALS, new StringValue("hello"), false));
    }

    @Test
    public void equals_StringsUnequalLength() {
        ObjectiveValue actualObjVal =
                RelationalValueObjectiveFunction.compute(
                new StringValue("phil"), EQUALS, new StringValue("philm"), false);

        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj val");
        expectedObjVal.add(ObjectiveValue.worstObjectiveValue("Penalty for size diff"));

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test
    public void equals_StringsUnequalByTwoCharacters() {
        ObjectiveValue actualObjVal =
                RelationalValueObjectiveFunction.compute(
                new StringValue("phil"), EQUALS, new StringValue("ohim"), false);

        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj val");
        DistanceObjectiveValue characterObjVal = new DistanceObjectiveValue("Character one value away");
        characterObjVal.setValueUsingDistance(ONE.add(K));
        expectedObjVal.add(characterObjVal);
        expectedObjVal.add(characterObjVal);

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test
    public void notEquals_StringsEqual() {
        ObjectiveValue actualObjVal =
                RelationalValueObjectiveFunction.compute(
                new StringValue("aaa"), NOT_EQUALS, new StringValue("aaa"), false);

        BestOfMultiObjectiveValue expectedObjVal = new BestOfMultiObjectiveValue("Top level obj val");
        DistanceObjectiveValue characterObjVal = new DistanceObjectiveValue("Character equal");
        characterObjVal.setValueUsingDistance(K);
        expectedObjVal.add(characterObjVal);
        expectedObjVal.add(characterObjVal);
        expectedObjVal.add(characterObjVal);

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test
    public void notEquals_StringsUnequal() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("baaaaaaaaa"), NOT_EQUALS, new StringValue("aaaaaaaaaa"), false));
    }

    @Test
    public void notEquals_StringsEmpty() {
        ObjectiveValue actualObjVal =
                RelationalValueObjectiveFunction.compute(
                new StringValue(""), NOT_EQUALS, new StringValue(""), false);

        BestOfMultiObjectiveValue expectedObjVal = new BestOfMultiObjectiveValue("Top level obj val");
        expectedObjVal.add(ObjectiveValue.worstObjectiveValue("Penalty for empty strings"));

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test
    public void notEquals_StringsUnequalLength() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue(""), NOT_EQUALS, new StringValue("a"), false));
    }

    @Test
    public void greater_StringsEqual() {
        ObjectiveValue actualObjVal =
                RelationalValueObjectiveFunction.compute(
                new StringValue("aa"), GREATER, new StringValue("aa"), false);

        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj val");
        expectedObjVal.add(ObjectiveValue.worstObjectiveValue("Penalty for same size, equal strings"));

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test
    public void greater_1stStringAfter2nd() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("b"), GREATER, new StringValue("a"), false));
    }

    @Test
    public void greater_1stStringAfter2nd2Characters() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("ab"), GREATER, new StringValue("aa"), false));
    }

    @Test
    public void greater_1stStringAfter2ndCharacters() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("aab"), GREATER, new StringValue("aaa"), false));
    }

    @Test
    public void greater_1stStringBefore2nd() {
        ObjectiveValue actualObjVal =
                RelationalValueObjectiveFunction.compute(
                new StringValue("a"), GREATER, new StringValue("b"), false);

        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj val");
        DistanceObjectiveValue characterObjVal = new DistanceObjectiveValue("Character one value away");
        characterObjVal.setValueUsingDistance(ONE.add(K));
        expectedObjVal.add(characterObjVal);

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test
    public void greater_1stStringBefore2nd2Chars() {
        ObjectiveValue actualObjVal =
                RelationalValueObjectiveFunction.compute(
                new StringValue("aa"), GREATER, new StringValue("ab"), false);

        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj val");
        DistanceObjectiveValue characterObjVal = new DistanceObjectiveValue("Character one value away");
        characterObjVal.setValueUsingDistance(ONE.add(K));
        expectedObjVal.add(characterObjVal);

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test
    public void greater_1stStringShorter() {
        ObjectiveValue actualObjVal =
                RelationalValueObjectiveFunction.compute(
                new StringValue("a"), GREATER, new StringValue("aa"), false);

        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj val");
        expectedObjVal.add(ObjectiveValue.worstObjectiveValue("Length penalty"));
        expectedObjVal.add(ObjectiveValue.worstObjectiveValue("Length penalty"));

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test
    public void greater_1stStringLonger() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("aa"), GREATER, new StringValue("a"), false));
    }

    @Test
    public void greaterOrEqual_FirstStringsGreater() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("b"), GREATER_OR_EQUALS, new StringValue("a"), false));

    }

    @Test
    public void greaterOrEquals_StringsEqual() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("aa"), GREATER_OR_EQUALS, new StringValue("aa"), false));
    }

    @Test
    public void greaterOrEquals_EmptyStrings() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue(""), GREATER_OR_EQUALS, new StringValue(""), false));
    }

    @Test
    public void greaterOrEquals_EqualStringsFirstStringLonger() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("aa"), GREATER_OR_EQUALS, new StringValue("a"), false));
    }

    @Test
    public void less_1stStringShorter() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("a"), LESS, new StringValue("aa"), false));
    }

    @Test
    public void less_1stStringLonger() {
        ObjectiveValue actualObjVal =
                RelationalValueObjectiveFunction.compute(
                new StringValue("aa"), LESS, new StringValue("a"), false);

        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj val");
        expectedObjVal.add(ObjectiveValue.worstObjectiveValue("Length penalty"));
        expectedObjVal.add(ObjectiveValue.worstObjectiveValue("Length penalty"));

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test
    public void lessOrEquals_1stStringShorter() {
        assertOptimal(
                RelationalValueObjectiveFunction.compute(
                new StringValue("a"), LESS_OR_EQUALS, new StringValue("aa"), false));
    }

    @Test
    public void lessOrEquals_1stStringLonger() {
        ObjectiveValue actualObjVal =
                RelationalValueObjectiveFunction.compute(
                new StringValue("aa"), LESS_OR_EQUALS, new StringValue("a"), false);

        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj val");
        expectedObjVal.add(ObjectiveValue.worstObjectiveValue("Length penalty"));

        assertEquivalent(expectedObjVal, actualObjVal);
    }
}
