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
        //String rand = this.randomAlphabetic(5);
        //top = this.search(rand, lm);

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
		return top;
		
	}
	
	public String generateRandomLMWork(LangModel lm, int addseed) {
		String top = "";
	    RANDOM = new Random(this.seed + addseed);
        //String rand = this.randomAlphabetic(5);
        //top = this.search(rand, lm);

		double topscore = 0;
		for (int i = 0; i < 100000; i++) {
			String rand = this.randomAlphabetic(5);
			double randscore = langModelScore(rand, lm);
			if (randscore > topscore) {
				top = rand;
				topscore = randscore;
			}
		}

		return top;
		
	}
	
    public String search(String rand, LangModel lm)
    {
        int n = rand.length();
        char[] s = rand.toCharArray();
        double firstScore = this.langModelScore(rand, lm);
        for (int i = 1; i < n; i++) 
        {
            char oldChar = s[i]; 
            s[i] = this.randomAlphabetic(1).toCharArray()[0]; 
            if (this.langModelScore(new String(s), lm) < firstScore) {
                s[i] = oldChar;
            }
        }
        return (new String(s));
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

