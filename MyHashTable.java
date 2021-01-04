package FinalProject_Template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;


public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
    	this.numBuckets = initialCapacity;
        this.numEntries = 0;
        this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(initialCapacity);
        for(int i=0; i<this.numBuckets(); i++) {    	
        	buckets.add(i, new LinkedList<HashPair<K,V>>());
        }
        //ADD YOUR CODE ABOVE THIS
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE
    	int index = this.hashFunction(key);
    	LinkedList<HashPair<K,V>> curBucket = buckets.get(index);
    	int i=0;
        		
    		for(HashPair<K,V> h1 : curBucket) {
    			
    			if(h1.getKey().equals(key)) {
        			V oldValue = h1.getValue();
        			h1.setValue(value);
        			return oldValue;
    			} 
    	    	i++;
    		}
    			curBucket.add(i, new HashPair<K,V>(key,value));
    			this.numEntries++;

    			if((( (double) this.size()/this.numBuckets() > MAX_LOAD_FACTOR))) {
    				
    				rehash();
    			}
    	return null;
        
        //  ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */
    
    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
    	V value = null;
        
        int index = this.hashFunction(key);
		LinkedList<HashPair<K,V>> curBucket = buckets.get(index);
        
		for(HashPair<K,V> h1 : curBucket) {
			
			if(h1.getKey().equals(key)) {
				value = h1.getValue();
			}
		}
        
        
    	return value;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
    	int index = this.hashFunction(key);
 		LinkedList<HashPair<K,V>> curBucket = buckets.get(index);
         
 		for(HashPair<K,V> h1 : curBucket) {
 			
 			if(h1.getKey().equals(key)) {
 				V value = h1.getValue();
 				curBucket.remove(h1);
 				this.numEntries--;
 				return value;
 			}
 		}
    	return null;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /** 
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
        //ADD YOUR CODE BELOW HERE
    	this.numBuckets = this.numBuckets*2;
    	
    	ArrayList<LinkedList<HashPair<K,V>>> update = new ArrayList<LinkedList<HashPair<K,V>>>(this.numBuckets);
    	
    	for(int i=0; i<this.numBuckets; i++) {
    		update.add(i, new LinkedList<HashPair<K,V>>());
    	}
    	
        for(LinkedList<HashPair<K,V>> l1 : this.buckets) {
        	
        	for(HashPair<K,V> h1 : l1) {
        		int position = this.hashFunction(h1.getKey());
        		update.get(position).add(h1);
        		
        	}
        }
        this.buckets = update;
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    
    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
    	ArrayList<K> myKeys = new ArrayList<K>();
    	int i=0;
    	
    	for(LinkedList<HashPair<K,V>> l1 : this.buckets) {
        	
        	for(HashPair<K,V> h1 : l1) {
        		
        		myKeys.add(i, h1.getKey());
        		i++;
        		
        	}
        }
        
    	return myKeys;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE
    	ArrayList<V> filteredValues = new ArrayList<V>();
    	ArrayList<ArrayList<V>> myValues = new ArrayList<ArrayList<V>>(this.numEntries);
    	
    	for(int i=0; i<this.numEntries; i++) {
    		myValues.add(new ArrayList<V>());
    	}
    	
    	for(LinkedList<HashPair<K,V>> l1 : this.buckets) {
        	
        	for(HashPair<K,V> h1 : l1) {
        		
        		if(!myValues.get(Math.abs(h1.getValue().hashCode())%this.numEntries).contains(h1.getValue())) {
        			myValues.get(Math.abs(h1.getValue().hashCode())%this.numEntries).add(h1.getValue());
        		}
        	}
        }
       
    	for(ArrayList<V> v1 : myValues) {
    		
    		for(V value : v1) {
    			filteredValues.add(value);
    		}
    	}
    	
    	
    	return filteredValues;
    	
        //ADD CODE ABOVE HERE
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
        //ADD CODE BELOW HERE
    	ArrayList<HashPair<K,V>> pairs = new ArrayList<HashPair<K,V>>();
    	for(LinkedList<HashPair<K,V>> l1 : results.buckets ) {
    		
    		for(HashPair<K,V> h1 : l1) {
    			
    			pairs.add(h1);
    		}
    	}
    	
    	pairs = sorting(pairs);
    	ArrayList<K> sortedResults = new ArrayList<K>();
    	for(int i=0; i<pairs.size(); i++) {
    		sortedResults.add(i, pairs.get(i).getKey()); 
    	}
    	
    	return sortedResults;
		
        //ADD CODE ABOVE HERE
    }

    private static <K, V extends Comparable<V>> ArrayList<HashPair<K,V>> sorting(ArrayList<HashPair<K,V>> a1){
    	if(a1.size()==1) {
    		return a1;
    	} else {
    		int i1 = (a1.size()-1)/2;
    		ArrayList<HashPair<K,V>> first = separate(a1, 0 , i1);
    		ArrayList<HashPair<K,V>> second = separate(a1, i1+1, a1.size()-1);
    		first = sorting(first);
    		second = sorting(second);
    		return merging(first,second);
    	}
    }
    
    private static <K, V extends Comparable<V>> ArrayList<HashPair<K,V>> merging(ArrayList<HashPair<K,V>> a2, ArrayList<HashPair<K,V>> a3){
    	ArrayList<HashPair<K,V>> list = new ArrayList<HashPair<K,V>>();
    	
    	while(!a2.isEmpty() && !a3.isEmpty()) {
    		if(a2.get(0).getValue().compareTo(a3.get(0).getValue()) > 0) {
    			list.add(a2.remove(0));
    		} else {
    			list.add((a3.remove(0)));
    		}
    	}
    	while(!a2.isEmpty()) {
			list.add(a2.remove(0));
		}
    	while(!a3.isEmpty()) {
			list.add(a3.remove(0));
		}
    	
    	return list;
    }
    
    private static <K, V extends Comparable<V>> ArrayList<HashPair<K,V>> separate(ArrayList<HashPair<K,V>> a2, int n1, int n2){
   	 ArrayList<HashPair<K,V>> temp = new  ArrayList<HashPair<K,V>>();	
   	for(int i=n1; i<=n2; i++) {
   		temp.add(a2.get(i));
   	}
   	return temp;
   }
    
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE
    	private ArrayList<HashPair<K,V>> hashList = new ArrayList<HashPair<K,V>>();
        private int size;
        private int index = 0;
        //ADD YOUR CODE ABOVE HERE
    	
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	for(LinkedList<HashPair<K,V>> l1 : buckets) {
            	
            	for(HashPair<K,V> h1 : l1) {
            		
            		hashList.add(h1);
            		
            	}
        	}
            size = hashList.size();
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
        	
        	return index < size && hashList.get(index) != null;
        	
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	if(this.hasNext()) {
            	return hashList.get(index++); 
            	} else {
            		throw new NoSuchElementException("There are no elements remaining");
            	}
            //ADD YOUR CODE ABOVE HERE
        }
  
    }
}
