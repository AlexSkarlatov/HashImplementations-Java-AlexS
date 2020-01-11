package hw6;



//This program does some small tests of correctness on MyQuadraticHashSet.
//Author:  Jeff Ward

public class TestHashSetsSmall
{
	public static void main(String args[])
	{
		// The following prime numbers are intended to be used
		// as table sizes in hashing with quadratic probing.
		final int[] primesForQuadraticProbing
			= { 17, 37, 79, 163, 331, 673, 1361, 2729, 5471, 10949,
			    21911, 43853, 87719, 175447, 350899, 701819, 1403641,
			    2807303, 5614657, 11229331, 22458671 };

		System.out.println("Starting tests.");
		MyQuadraticHashSet<Integer> set = new MyQuadraticHashSet<Integer>(0.10, primesForQuadraticProbing);
		int[] values = new int[] { 1, 3, 19, 6, 8, 53 };
		for (int val : values)
			if (set.add(val) != true)
				throw new RuntimeException();
		if (set.size() != values.length)
			throw new RuntimeException("Wrong size");
		for (int val : values)
			if (set.add(val) != false)
				throw new RuntimeException();
		if (set.size() != values.length)
			throw new RuntimeException("Wrong size");
		for (int val : values)
			if (set.remove(val) != true)
				throw new RuntimeException();
		for (int val : values)
			if (set.contains(val) != false)
				throw new RuntimeException();
		for (int val : values)
			if (set.remove(val) != false)
				throw new RuntimeException();

		System.out.println("Tests successfully completed.");
		System.exit(0);
	}
}