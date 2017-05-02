package org.schemaanalyst.testgeneration;

import java.io.IOException;
import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;

public class RandomLM {
	private Random RANDOM;
	private long seed;
	public RandomLM(long seed) {
		RANDOM = new Random(seed);
		this.seed = seed;
	}
	
	public String generateRandomLMWork(LangModel lm) {
		String top = "";
	    //LangModel lm;
	    
		//try {
			//lm = new LangModel("ukwac_char_lm");
			double topscore = 0;
			for (int i = 0; i < 100000; i++) {
				//int averageEnglishWord = RANDOM.nextInt(6 - 4 + 1) + 4;
				String rand = this.randomAlphabetic(5);
				double randscore = langModelScore(rand, lm);
				if (randscore > topscore) {
					top = rand;
					topscore = randscore;
				}
			}
			
			//System.out.println("Word \t = "  + top);
			//System.out.println("score \t = " + topscore);
		/*
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return top;
		
	}
	
	public String generateRandomLMWork(LangModel lm, int addseed) {
		String top = "";
	    //LangModel lm;
	    RANDOM = new Random(this.seed + addseed);
		//try {
			//lm = new LangModel("ukwac_char_lm");
			double topscore = 0;
			for (int i = 0; i < 100000; i++) {
				String rand = this.randomAlphabetic(5);
				double randscore = langModelScore(rand, lm);
				if (randscore > topscore) {
					top = rand;
					topscore = randscore;
				}
			}
			
			//System.out.println("Word \t = "  + top);
			//System.out.println("score \t = " + topscore);
		/*
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return top;
		
	}
	
	
    protected double langModelScore(String string, LangModel lm) {
    	try {
    	    return lm.score(string, false);
	    } catch (Exception e) {
    	    System.err.println("Error: " + e.getMessage());
    	    return 0;
	    }
    }
    
    private String randomAlphabetic(int count) {
            return RandomStringUtils.random(count, 0, 0, true, false, null, RANDOM);
    }
}

