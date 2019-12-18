package org.schemaanalyst.util;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.OrPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

public class OptimizePredicates {

	private TestRequirements testReqirements;
	private List<Predicate> predicates = new ArrayList<>();

	public OptimizePredicates(TestRequirements trs) {
		this.testReqirements = trs;
	}

	private void getPredicatesIntoList() {
		for (TestRequirement tr : this.testReqirements.getTestRequirements()) {
			predicates.add(tr.getPredicate());
		}
	}

	private void printPredicates() {
		for (Predicate p : this.predicates) {
			System.out.println(p);
			System.out.println("=================");
		}
	}

	private void optimizePredicate(Predicate p) {
		if (p instanceof OrPredicate) {
			List<Predicate> ps = ((OrPredicate) p).getSubPredicates();
			for (int i = 1; i < ps.size(); i++) {
				// ps.remove(i);
				((OrPredicate) p).removeSubPredicate(ps.get(i));
			}
		}

		if (p instanceof ComposedPredicate) {
			for (Predicate ps : ((ComposedPredicate) p).getSubPredicates()) {
				this.optimizePredicate(ps);
			}
		}
	}

	private void optimize() {
		for (TestRequirement tr : this.testReqirements.getTestRequirements()) {
			this.optimizePredicate(tr.getPredicate());
		}
	}

	public void doAll() {
		this.optimize();
		// this.getPredicatesIntoList();
		// this.printPredicates();
	}

}