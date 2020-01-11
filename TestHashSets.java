package hw6;

//This program tests the correctness of the following two classes:
//MyHashSet (from Y. Daniel Liang's Introduction to Java Programming, Comprehensive Version)
//MyQuadraticHashSet
//The tests involve many add, remove, and contains operations on random integers.
//The results are compared to the results of performing the same operations on a java.util.HashSet.
//Author:  Jeff Ward
import java.util.*;

public class TestHashSets
{
private enum OperationType { ADD, CONTAINS, REMOVE }
static final int NUM_VALUES = 1000000;
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

	System.out.println("Starting tests.");
	HashSet<Integer> jcfHashSet = new HashSet<Integer>();
	MyHashSet<Integer> liangHashSet = new MyHashSet<Integer>();
	MyQuadraticHashSet<Integer> lowLoadSet = new MyQuadraticHashSet<Integer>(0.10, primesForQuadraticProbing);
	MyQuadraticHashSet<Integer> highLoadSet = new MyQuadraticHashSet<Integer>(0.50, primesForQuadraticProbing);

	System.out.println("Add operations ...");
	performTests(jcfHashSet, liangHashSet, lowLoadSet, highLoadSet, OperationType.ADD);
	checkSizes(jcfHashSet, liangHashSet, lowLoadSet, highLoadSet);
	System.out.println("Contains operations ...");
	performTests(jcfHashSet, liangHashSet, lowLoadSet, highLoadSet, OperationType.CONTAINS);
	System.out.println("Remove operations ...");
	performTests(jcfHashSet, liangHashSet, lowLoadSet, highLoadSet, OperationType.REMOVE);
	checkSizes(jcfHashSet, liangHashSet, lowLoadSet, highLoadSet);
	System.out.println("Tests successfully completed.");
	System.exit(0);
}

static void performTests(HashSet<Integer> jcfHashSet, MyHashSet<Integer> liangHashSet,
	MyQuadraticHashSet<Integer> lowLoadSet, MyQuadraticHashSet<Integer> highLoadSet,
	OperationType ot)
{
	for (int i = 0; i < NUM_VALUES; i++)
	{
		int value = rand.nextInt(BOUND) - BOUND / 2;
		boolean jcfReturnVal;
		boolean liangReturnVal;
		boolean lowLoadSetReturnVal;
		boolean highLoadSetReturnVal;
		switch (ot)
		{
			case ADD:
				jcfReturnVal = jcfHashSet.add(value);
				liangReturnVal = liangHashSet.add(value);
				lowLoadSetReturnVal = lowLoadSet.add(value);
				highLoadSetReturnVal = highLoadSet.add(value);
				break;
			case CONTAINS:
				jcfReturnVal = jcfHashSet.contains(value);
				liangReturnVal = liangHashSet.contains(value);
				lowLoadSetReturnVal = lowLoadSet.contains(value);
				highLoadSetReturnVal = highLoadSet.contains(value);
				break;
			default:
				jcfReturnVal = jcfHashSet.remove(value);
				liangReturnVal = liangHashSet.remove(value);
				lowLoadSetReturnVal = lowLoadSet.remove(value);
				highLoadSetReturnVal = highLoadSet.remove(value);
		}
		if (jcfReturnVal != liangReturnVal || jcfReturnVal != lowLoadSetReturnVal
			|| jcfReturnVal != highLoadSetReturnVal)
		{
			System.out.println("Error:");
			System.out.println("On value: " + value);
			System.out.println("The JCF HashSet returned " + jcfReturnVal);
			System.out.println("The MyHashSet returned " + liangReturnVal);
			System.out.println("The .10 load threshold MyQuadraticHashSet returned " + lowLoadSetReturnVal);
			System.out.println("The .50 load threshold MyQuadraticHashSet returned " + highLoadSetReturnVal);
			System.exit(1);
		}
	}
}

static void checkSizes(HashSet<Integer> jcfHashSet, MyHashSet<Integer> liangHashSet,
	MyQuadraticHashSet<Integer> lowLoadSet, MyQuadraticHashSet<Integer> highLoadSet)
{
	int size = jcfHashSet.size();
	System.out.println("Set size:  " + size);

	if (size != liangHashSet.size() || size != lowLoadSet.size() || size != highLoadSet.size())
	{
		System.out.println("Error:");
		System.out.println("The JCF HashSet has size " + size);
		System.out.println("The MyHashSet has size " + liangHashSet.size());
		System.out.println("The .10 load threshold MyQuadraticHashSet has size " + lowLoadSet.size());
		System.out.println("The .50 load threshold MyQuadraticHashSet has size " + highLoadSet.size());
		System.exit(1);
	}
}
}