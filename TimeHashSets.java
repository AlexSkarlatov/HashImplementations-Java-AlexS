package hw6;



// Program to compare the runtimes of various hash classes.
//The classes timed are:
//  java.util.TreeSet
//  java.util.HashSet
//  MyHashSet (from Y. Daniel Liang's Introduction to Java Programming, Comprehensive Version)
//  MyQuadraticHashSet
//Author:  Jeff Ward
import java.util.*;

public class TimeHashSets
{
	static final int NUM_VALUES = 2000000;
	static final int BOUND = NUM_VALUES;
	static Random rand = new Random(100);

	public static void main(String args[])
	{
		// The following prime numbers are intended to be used
		// as table sizes in hashing with quadratic probing.
		final int[] primesForQuadraticProbing
			= { 17, 37, 79, 163, 331, 673, 1361, 2729, 5471, 10949,
			    21911, 43853, 87719, 175447, 350899, 701819, 1403641,
			    2807303, 5614657, 11229331, 22458671 };

		System.out.println("Each set will be timed on " + NUM_VALUES + " add operations, "
			+ NUM_VALUES + " contains operations, and " + NUM_VALUES + " remove operations.\n");

		System.out.println("Timing java.util.TreeSet");
		timeJcf(new TreeSet<Integer>());
		System.gc();  // Request garbage collection, although it doesn't seem to make much difference

		System.out.println("Timing java.util.HashSet");
		timeJcf(new HashSet<Integer>());
		System.gc();

		System.out.println("Timing MyHashSet from textbook");
		timeMySet(new MyHashSet<Integer>());
		System.gc();

		System.out.println("Timing MyQuadraticHashSet with load threshold = 0.10");
		timeMySet(new MyQuadraticHashSet<Integer>(0.10, primesForQuadraticProbing));
		System.gc();

		System.out.println("Timing MyQuadraticHashSet with load threshold = 0.50");
		timeMySet(new MyQuadraticHashSet<Integer>(0.50, primesForQuadraticProbing));
		System.exit(0);
	}

	static void timeJcf(Set<Integer> jcfSet)
	{
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < NUM_VALUES; i++)
			jcfSet.add(rand.nextInt(BOUND) - BOUND / 2);
		for (int i = 0; i < NUM_VALUES; i++)
			jcfSet.contains(rand.nextInt(BOUND) - BOUND / 2);
		for (int i = 0; i < NUM_VALUES; i++)
			jcfSet.remove(rand.nextInt(BOUND) - BOUND / 2);
		long endTime = System.currentTimeMillis();
		double seconds = (endTime - startTime) / 1000.0;
	    System.out.printf("Runtime:  %1.3f seconds\n\n", seconds);
	}

	static void timeMySet(MySet<Integer> mySet)
	{
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < NUM_VALUES; i++)
			mySet.add(rand.nextInt(BOUND) - BOUND / 2);
		for (int i = 0; i < NUM_VALUES; i++)
			mySet.contains(rand.nextInt(BOUND) - BOUND / 2);
		for (int i = 0; i < NUM_VALUES; i++)
			mySet.remove(rand.nextInt(BOUND) - BOUND / 2);
		long endTime = System.currentTimeMillis();
		double seconds = (endTime - startTime) / 1000.0;
	    System.out.printf("Runtime:  %1.3f seconds\n\n", seconds);
	}
}



