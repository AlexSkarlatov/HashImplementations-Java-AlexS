package hw6;

/**
 * Name:Alexander Skarlatov
 * Assignment: HW6
 * date:4/20/18
 * This program implements a hash using quadratic probing.
 * this program
 */

import java.util.*;
import java.util.Iterator;

public class MyQuadraticHashSet<E> implements MySet<E> {
	// data fields
	private int capacity;
	private int rehashI = 1;
	private int numRemoved = 0;
	private double loadFactorThreshold;
	private int[] array;
	private int size = 0;
	private Object[] table;
	private final static Object REMOVED = new Object();
	// my constructor
	public MyQuadraticHashSet(double threshold, int[] primesForQuadraticProbing) {
		this.loadFactorThreshold = threshold;
		this.array = primesForQuadraticProbing;
		this.table = new Object[array[0]];
		this.capacity = array[0];
	}

	@Override
	public boolean remove(Object e) {
		if (contains(e)) {
			int indexOfE = probeIndex(e.hashCode(),0,table.length);
			if (table[indexOfE] != null && table[indexOfE] != REMOVED) {
				table[indexOfE] = REMOVED;
				numRemoved++;
				size--;
				return true;
			}
			else {
				long modifier = 0;
				for (int i = 0; i < table.length / 2; i++, modifier++) {
					indexOfE = probeIndex(e.hashCode(), modifier, table.length);
					if (table[indexOfE].equals(e)) {
						table[indexOfE] = REMOVED;
						numRemoved++;
						size--;
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean add(Object e) {
		if (contains(e)) {
			return false;// no dublicates are allowed
		}
		if (size + numRemoved + 1 > capacity * loadFactorThreshold) {
			rehash();
		}
		int indexOfE = probeIndex(e.hashCode(),0,table.length);
		if (table[indexOfE] == null) {
			table[indexOfE] = e;
			size++;
			return true;
		}
		else {
			long modifier = 0;
			while(true){
				indexOfE = probeIndex(e.hashCode(),modifier,table.length);
				if(table[indexOfE] == null){
					table[indexOfE] = e;
					size++;
					return true;
				}
				else if(table[indexOfE] == REMOVED){
					table[indexOfE] = e;
					numRemoved--;
					size++;
					return true;
				}
				else{
					modifier++;
				}
			}
		}
	}

	/**
	 * This method will be called when the size of the hash exceeds the given threshold
	 * and the hash must be expanded to avoidrunning out of open addresses that can be probed.
	 */
	private void rehash() {
		Object[] oldTable = table;
		table = new Object[array[rehashI]];
		this.capacity = array[rehashI];
		rehashI++;
		numRemoved = 0;
		size = 0;
		for (int i = 0; i < oldTable.length; i++) {
			if (oldTable[i] != REMOVED && oldTable[i] != null) {
				add(oldTable[i]);
			}
		}
	}

	@Override
	public boolean contains(Object e) {
		long modifier = 0;
		while(true){
			int indexOfE = probeIndex(e.hashCode(),modifier,table.length);
			if(table[indexOfE] == null)
			{
				return false;
			}
			else if(table[indexOfE].equals(e)){
				return true;
			}
			else{
				modifier++;
			}
		}
	}

	@Override
	public void clear() {
		size = 0;
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}

	/**this method will reutrn true only if hash is empty */
	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	/** this method will return the size */
	@Override
	public int size() {
		return size;
	}

	/** write a probe method that is helped by this following helper method */
	private static int probeIndex(int hashCode, long modifier, int tableLength) {
		//
		return (int) ((hashCode % tableLength + tableLength + modifier * modifier) % tableLength);
	}

	@Override
	public Iterator iterator() {
		throw new UnsupportedOperationException();
		//return null;
	}
}
