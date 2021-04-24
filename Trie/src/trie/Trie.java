package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */



public class Trie {
	
	// prevent instantiation
	
/*	private static TrieNode subTree(String[] allWords, int index, TrieNode startingNode) {
		
		Indexes ind = new Indexes(index, (short)0, (short)(allWords[index].length()-1));
		
		TrieNode children = new TrieNode (ind, null, null);
		
		int input = -1;
		
		for(int i = 0; i < index; i ++) {
			if(allWords[index].charAt(0) == allWords[i].charAt(0)) {
				input = i;
				break;
			}
			
		}
		
		TrieNode temp = startingNode;
		TrieNode temp2 = null;
		
		if(input == -1) {
			while(temp.sibling != null) {
				temp2 = temp;
				temp = temp.sibling;
			}
			temp2.sibling = children;
		}
		
		return startingNode;
	}
*/	
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		
		TrieNode root = new TrieNode(null, null, null); //the root of the trie
		if(allWords.length == 0){ //return only the root if the ArrayList have no words
			return root;
		}			
		
		else { //the ArrayList contains words
			
			
//			Indexes ind = new Indexes(0, (short)0, (short)0);  //  index(wordIndex, startIndex, endIndex)
//			TrieNode startingNode = new TrieNode(ind, null, null);  // initializing the root,  node(wordIndex, firstChild, sibling)
//			
////			TrieNode first = new TrieNode(in, null, null);
//			
//			

			
			Indexes indexesValues = new Indexes(0, (short)(0), (short)(allWords[0].length() - 1)); // indexes of the root.firstChild
			TrieNode startingNode = new TrieNode(indexesValues, null, null); // making the node of the first child
			
			root.firstChild = startingNode; //linking the root and the child

			for(int i = 1; i < allWords.length; i++) { 
				
				TrieNode ptr = root.firstChild;
				TrieNode prev = root.firstChild;
				TrieNode store = null;
				
				int count = -1;
				int allWordsIndex = ptr.substr.wordIndex; // storing the word index that is in allWords
				int startingIndex = ptr.substr.startIndex; // storing the starting index of the word
				int endingIndex = ptr.substr.endIndex; // storing the ending index of the word
				
				
				String word = allWords[i];
				int worldLength = word.length();
				
				while(ptr != null) { // keep on comparing until there are no nodes in the same level
					
					allWordsIndex = ptr.substr.wordIndex; // storing the word index that is in allWords
					startingIndex = ptr.substr.startIndex; // storing the starting index of the word
					endingIndex = ptr.substr.endIndex; // storing the ending index of the word
					
					if(startingIndex > worldLength) {
						prev = ptr;
						ptr = ptr.sibling;
						continue;
					}
					
					String temp = allWords[allWordsIndex].substring(startingIndex, endingIndex + 1); // storing the substring of the previous word
					String temp2 = word.substring(startingIndex); // storing the substring of the word
					
					int tempLength = temp.length();
					int temp2Length = temp2.length();
					
					
					count = 0; // set counter to 0, index of a string always start at 0,
					
					while(count < tempLength && count < temp2Length && temp.charAt(count) == temp2.charAt(count)) { // compare the two words to see if there are similarities
						count = count + 1;
					}
					
					count = count - 1; // -1 for future index. Index of a string is always 1 less
					
//					for(int i = 1; i < allWords.length; i ++) {
//					startingNode = subTree(allWords, i, startingNode);
//				}
				
					if(count > -1) { // there are matches, increasing the starting Index
						count = count + startingIndex;
					}
											
					if(count == -1) { // no match
						store = ptr;
						ptr = ptr.sibling;
						prev = store;
						
					}
					else {
						if(count == endingIndex) { // fully match
							
							store = ptr;
							ptr = ptr.firstChild;
							prev = store;
							
						}
						else if (count < endingIndex){ // only some matches
							prev = ptr;
							break;
						}
					}
				}
				

				if(ptr == null) { // no matches, then make a sibling node for the first child
					
					Indexes a = new Indexes(i, (short)startingIndex, (short)(worldLength - 1)); // indexes for the node
					TrieNode sib = new TrieNode(a, null, null); // declare and initialize the node
					prev.sibling = sib; // linking the first child and sibling
					
				} 
				
				if(ptr != null) { //similarities so we need a node for the prefix
					
					Indexes prefixIndexes = prev.substr; //saving the indexes for prefix
					TrieNode prefixTwoLevelLower = prev.firstChild; // storing the lower level of the trie for future use
					
//					Indexes ind = new Indexes(0, (short)0, (short)0);  //  index(wordIndex, startIndex, endIndex)
//					TrieNode startingNode = new TrieNode(ind, null, null);  // initializing the root,  node(wordIndex, firstChild, sibling)
//					
////					TrieNode first = new TrieNode(in, null, null);
					short in = prefixIndexes.endIndex;
					
					prefixIndexes.endIndex = (short)count; // changing the end point of the prefix
					
					Indexes prefixFirstChildIndexes = new Indexes(prefixIndexes.wordIndex, (short)(count+1), in); // making the index of the first child of the prefix, 
																											//the node should start after the end point of the prefix
					
					TrieNode prefixFirstChild = new TrieNode(prefixFirstChildIndexes, null, null); // making the node for the child
					prev.firstChild = prefixFirstChild; // linking the prefix with the child
					prev.firstChild.firstChild = prefixTwoLevelLower; // linking the lower level(s) with the child
					
					Indexes sibling = new Indexes((short)i, (short)(count + 1), (short)(worldLength - 1)); //making the indexes for the sibling
					TrieNode prefixFirstChildSibling = new TrieNode(sibling, null, null); //making the node for the sibling
					prev.firstChild.sibling = prefixFirstChildSibling; //linking the child with the sibling
				}
			}
		}
		return root;
	}
	
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		
		ArrayList<TrieNode> str = new ArrayList<TrieNode>();
		TrieNode ptr = root;
		
//		Indexes ind = new Indexes(0, (short)0, (short)0);  //  index(wordIndex, startIndex, endIndex)
//		TrieNode startingNode = new TrieNode(ind, null, null);  // initializing the root,  node(wordIndex, firstChild, sibling)
//		
////		TrieNode first = new TrieNode(in, null, null);
		
		if(root == null) {
			return null;
		}
		else if (allWords.length == 0) {
			return null;
		}			
		else {
			while(ptr != null) {
				if(ptr.substr == null) { 
					ptr = ptr.firstChild;
				}
				int wordIndex = ptr.substr.wordIndex;
				int endIndex = ptr.substr.endIndex;
				
				String temp = allWords[wordIndex];
				String temp2 = temp.substring(0, endIndex + 1);
				
				if(temp.startsWith(prefix)) {
					if(ptr.firstChild != null) {
						str.addAll(searching(ptr.firstChild, allWords, prefix));
						ptr = ptr.sibling;
					} 
					else {
						str.add(ptr);
						ptr = ptr.sibling;
					}
				} 
				
	//			for(int i = 1; i < allWords.length; i ++) {
//					startingNode = subTree(allWords, i, startingNode);
//				}
					
				else if(prefix.startsWith(temp2)) {
					if(ptr.firstChild != null) { 
						str.addAll(completionList(ptr.firstChild, allWords, prefix));
						ptr = ptr.sibling;
					} 
					else { 
						str.add(ptr);
						ptr = ptr.sibling;
					}
				} 
				else {
					ptr = ptr.sibling;
				}
			}
			return str;
		}
		
		
		
	}
	
	private static ArrayList<TrieNode> searching(TrieNode root, String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		
		ArrayList<TrieNode> str = new ArrayList<TrieNode>();
		TrieNode ptr = root;
		
		
//		Indexes ind = new Indexes(0, (short)0, (short)0);  //  index(wordIndex, startIndex, endIndex)
//		TrieNode startingNode = new TrieNode(ind, null, null);  // initializing the root,  node(wordIndex, firstChild, sibling)
//		
////		TrieNode first = new TrieNode(in, null, null);
		
		
		if(root == null) {
			return null;
		}
		else if (allWords.length == 0) {
			return null;
		}			
		else {
			while(ptr != null) {
				if(ptr.substr == null) { 
					ptr = ptr.firstChild;
				}
				int wordIndex = ptr.substr.wordIndex;
				int endIndex = ptr.substr.endIndex;
				
				String temp = allWords[wordIndex];
				String temp2 = temp.substring(0, endIndex + 1);
				
				if(temp.startsWith(prefix)) {
					if(ptr.firstChild != null) {
						str.addAll(completionList(ptr.firstChild, allWords, prefix));
						ptr = ptr.sibling;
					} 
					else {
						str.add(ptr);
						ptr = ptr.sibling;
					}
				} 
				else if(prefix.startsWith(temp2)) {
					if(ptr.firstChild != null) { 
						str.addAll(completionList(ptr.firstChild, allWords, prefix));
						ptr = ptr.sibling;
					} 
					else { 
						str.add(ptr);
						ptr = ptr.sibling;
					}
				} 
				else {
					ptr = ptr.sibling;
				}
			}
//			for(int i = 1; i < allWords.length; i ++) {
//			startingNode = subTree(allWords, i, startingNode);
//		}
			return str;
		}	
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
