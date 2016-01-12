package org.schemaanalyst.mutation.supplier;

/**
 * A class with static helper methods for linking 2 or more suppliers together
 * in a chain.
 * 
 * @author Phil McMinn
 * 
 */
public class SupplyChain {

	/**
	 * Links two suppliers together in a chain.  Note that generic
	 * parameters of the suppliers must match, i.e. the second generic type of the
	 * first supplier must match the first generic type of the second
	 * supplier.
	 * @param supplier1 the first supplier in the chain.
	 * @param supplier2 the second supplier in the chain.
	 * @return A {@link LinkedSupplier} linking the two suppliers together.
	 */
	public static <A, B, C> Supplier<A, C> chain(
			Supplier<A, B> supplier1,
			Supplier<B, C> supplier2) {

		return new LinkedSupplier<>(
				supplier1,
				supplier2);
	}	
	
	/**
	 * Links three suppliers together in a chain.  Note that generic
	 * parameters of the suppliers must match -- that is, the second generic 
	 * type of each consecutive supplier must match the first generic type of 
	 * the next supplier specified after it.
	 * @param supplier1 the first supplier in the chain.
	 * @param supplier2 the second supplier in the chain.
	 * @param supplier3 the third supplier in the chain.
	 * @return A {@link LinkedSupplier} linking the three suppliers together.
	 */	
	public static <A, B, C, D> Supplier<A, D> chain(
			Supplier<A, B> supplier1,
			Supplier<B, C> supplier2,
			Supplier<C, D> supplier3) {

		return new LinkedSupplier<>(
				supplier1,
				new LinkedSupplier<>(
						supplier2,
						supplier3));	
	}
	
	/**
	 * Links three suppliers together in a chain.  Note that generic
	 * parameters of the suppliers must match -- that is, the second generic 
	 * type of each consecutive supplier must match the first generic type of 
	 * the next supplier specified after it.
	 * @param supplier1 the first supplier in the chain.
	 * @param supplier2 the second supplier in the chain.
	 * @param supplier3 the third supplier in the chain.
	 * @param supplier4 the fourth supplier in the chain.
	 * @return A {@link LinkedSupplier} linking the four suppliers together.
	 */		
	public static <A, B, C, D, E> Supplier<A, E> chain(
			Supplier<A, B> supplier1,
			Supplier<B, C> supplier2,
			Supplier<C, D> supplier3,
			Supplier<D, E> supplier4) {

		return new LinkedSupplier<>(
				supplier1,
				new LinkedSupplier<>(
						supplier2,
						new LinkedSupplier<>(
								supplier3,
								supplier4)));
	}	
}
