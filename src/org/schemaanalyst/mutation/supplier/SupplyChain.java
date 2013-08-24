package org.schemaanalyst.mutation.supplier;

public class SupplyChain {

	public static <A, B, C> Supplier<A, C> chain(
			Supplier<A, B> supplier1,
			Supplier<B, C> supplier2) {

		return new LinkedSupplier<A, B, C>(
				supplier1,
				supplier2);
	}	
	
	public static <A, B, C, D> Supplier<A, D> chain(
			Supplier<A, B> supplier1,
			Supplier<B, C> supplier2,
			Supplier<C, D> supplier3) {

		return new LinkedSupplier<A, B, D>(
				supplier1,
				new LinkedSupplier<B, C, D>(
						supplier2,
						supplier3));	
	}
	
	public static <A, B, C, D, E> Supplier<A, E> chain(
			Supplier<A, B> supplier1,
			Supplier<B, C> supplier2,
			Supplier<C, D> supplier3,
			Supplier<D, E> supplier4) {

		return new LinkedSupplier<A, B, E>(
				supplier1,
				new LinkedSupplier<B, C, E>(
						supplier2,
						new LinkedSupplier<C, D, E>(
								supplier3,
								supplier4)));
	}	
}
