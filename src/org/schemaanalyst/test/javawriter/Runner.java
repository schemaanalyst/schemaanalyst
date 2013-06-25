package org.schemaanalyst.test.javawriter;

import org.schemaanalyst.javawriter.SchemaJavaWriter;

import casestudy.BankAccount;

public class Runner {
	
	public static void main(String[] args) {
		
		SchemaJavaWriter jw = new SchemaJavaWriter(new BankAccount());
		System.out.println(jw.write());
		
		
		
	}

}
