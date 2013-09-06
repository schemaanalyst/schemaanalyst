package generatedtest;

public class test {

	int int1;
	int int2;
	int int3;
	int int4;
	int int5;
	int int6;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + int1;
		result = prime * result + int2;
		result = prime * result + int3;
		result = prime * result + int4;
		result = prime * result + int5;
		result = prime * result + int6;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		test other = (test) obj;
		if (int1 != other.int1)
			return false;
		if (int2 != other.int2)
			return false;
		if (int3 != other.int3)
			return false;
		if (int4 != other.int4)
			return false;
		if (int5 != other.int5)
			return false;
		if (int6 != other.int6)
			return false;
		return true;
	}
	
	
}
