package FinalProject_Template;

import java.util.ArrayList;

import FinalProject_Template.Tweet;

public class Twitter {
	
	//ADD YOUR CODE BELOW HERE
	MyHashTable<String,ArrayList<Tweet>> authors;	
	MyHashTable<String, ArrayList<Tweet>> dating;
	MyHashTable<String, Integer> trends;
	ArrayList<Tweet> allTweets;
	ArrayList<String> stopWords;
	
	//ADD CODE ABOVE HERE 
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE
		authors = new MyHashTable<String,ArrayList<Tweet>>(tweets.size());	
		dating = new MyHashTable<String,ArrayList<Tweet>>(tweets.size());
		trends = new MyHashTable<String, Integer>(tweets.size());
		allTweets = new ArrayList<Tweet>(tweets.size());
		this.stopWords = new ArrayList<String>(stopWords.size());
		
		for(int i=0; i<stopWords.size(); i++) {
			String wd = stopWords.get(i).toLowerCase();
			this.stopWords.add(i, wd);
		}
		
		
		for(int i=0; i<tweets.size(); i++) {
			this.addTweet(tweets.get(i));
			allTweets.add(i, tweets.get(i));
			}
		//ADD CODE ABOVE HERE 
	}
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		//ADD CODE BELOW HERE
		String date = t.getDateAndTime().substring(0, 10);
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		ArrayList<Tweet> dateTweets = new ArrayList<Tweet>();
		if(authors.get(t.getAuthor())== null) {
			tweets.add(t);
		} else {
			tweets = authors.get(t.getAuthor());
			tweets.add(t);
		}
		
		if(dating.get(date)== null) {
			dateTweets.add(t);
		} else {
			dateTweets = dating.get(date);
			dateTweets.add(t);
		}
		
		authors.put(t.getAuthor(), tweets);
		dating.put(date, dateTweets);
		//ADD CODE ABOVE HERE 
	}
	

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
        //ADD CODE BELOW HERE
    	Tweet latest = null;
    	
    	if(authors.get(author) != null) latest = authors.get(author).get(authors.get(author).size()-1);

    	return latest;
    	
        //ADD CODE ABOVE HERE 
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        //ADD CODE BELOW HERE
    	ArrayList<Tweet> dates = null;
    	
    	if(dating.get(date) != null) dates = dating.get(date);		

    	return dates;
    	
        //ADD CODE ABOVE HERE
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
        //ADD CODE BELOW HERE

    		
    		for(Tweet t1 : allTweets) {
    			ArrayList<String> words = Twitter.getWords(t1.getMessage());
    			ArrayList<String> filter = new ArrayList<String>();
    			
    			for(String wd : words) {
    				
    				wd = wd.toLowerCase();
    				
    				if(!stopWords.contains(wd)) {
	    				
    					if(!filter.contains(wd)) {
	    				
	    					filter.add(wd);
	    				
	    				if(trends.get(wd) == null) {
	    					trends.put(wd, 1);
	    				} else {
	    					trends.put(wd, trends.get(wd)+1);
	    						}
	    				}
    				}
    			}
    	}
    	
    	ArrayList<String> topics = MyHashTable.fastSort(trends);
    	
    	
    	return topics;
    	
        //ADD CODE ABOVE HERE    	
    }
    
    
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }

    

}
