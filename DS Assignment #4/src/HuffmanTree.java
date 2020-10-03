import java.util.Collection;
import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 * hello welcome to my huffman tree.
 * 
 * The Huffman Tree is first built by adding each character in the file into the
 * a hastable. This is done to set up a data structure that contains all the
 * characters that exist in the loaded file along with their respective
 * frequencies.
 * 
 * Then, the Huffman tree is built using a minheap. Each HuffmanNode in the
 * hashtable is added to a minHeap. From there, HuffmanNodes will be popped off
 * two at a time to build a tree from the bottom up.
 * 
 * 
 * 
 * yay
 * 
 * 
 * @author Marcus
 *
 */
public class HuffmanTree {

	private PriorityQueue<HuffmanNode> minHeap;
	private Hashtable<Character, HuffmanNode> theHash;
	private HuffmanNode root;
	private int maxEncodeSize;

	public HuffmanTree() {
		minHeap = new PriorityQueue<>();
		theHash = new Hashtable<>();
		root = null;
	}

	/**
	 * Adds a character to the hashtable of characters.
	 * 
	 * @param theChar the character to be added.
	 */
	public void addChar(char theChar) {
		// if the hashtable has the character already, increment
		if (theHash.containsKey(theChar)) {
			theHash.get(theChar).increment();
		}
		// if it does not exist in the thing yet, add a new node
		// to the hashtable.
		else {
			theHash.put(theChar, new HuffmanNode(theChar));
		}

	}

	/**
	 * Builds the priotity queue of HuffmanNodes and also sets the 
	 * value of maxEncodeSize to hashValues.size()-1. For example,
	 * if there were 4 unique characters to be encoded, the max
	 * size of an encoded string would be 4-1=3. 
	 */
	public void buildPrioQueue() {
		Collection<HuffmanNode> hashValues = theHash.values();
		maxEncodeSize = hashValues.size()-1;

		
		for (HuffmanNode node : hashValues) {
			minHeap.offer(node);
		}
	}

	/**
	 * This method builds the Huffman Tree based off of the minHeap PriorityQueue.
	 */
	public void buildHuffmanTree() {
		while (minHeap.size() > 1) {
			HuffmanNode smallerNode = minHeap.poll();
			HuffmanNode biggerNode = minHeap.poll();

			HuffmanNode newInteriorNode = new HuffmanNode(biggerNode, smallerNode);
			
			minHeap.offer(newInteriorNode);
		}
		
		root = minHeap.poll();
	}

	
	/**
	 * This method decodes a given encoded String by traversing
	 * down the Huffman trade according to the encoded String.
	 * If it encounters a 0, it goes left, and if it encounters 
	 * a 1, it goes right. If the traverser encounters a leaf node aka
	 * non interior node, it appends its content to the StringBuilder 
	 * and then resets pointer back to the root to begin traversing for
	 * the next character's encoding. 
	 * 
	 * @param encoded the encoded String to be decoded. 
	 * @return the encoded String from the decoded String. 
	 */
	public String decode(String encoded) {
		StringBuilder sb = new StringBuilder();
		HuffmanNode traverser = root;	
		
		for (int i = 0; i < encoded.length(); i++) {
			//if encounter a leaf node (a non interior node) 
			if (encoded.charAt(i) == '0'){ 
				traverser = traverser.left; 
			}
			else if (encoded.charAt(i) == '1') {
				traverser = traverser.right;
			}
			else {
				return "Invalid encoding! The code should contain only ones and zeroes.";
			}	
			if (!traverser.isInteriorNode()) {
				sb.append(traverser.content);
				traverser = root;
			}

	
		}
		if (traverser != root && traverser.isInteriorNode()) {
			return "Invalid encoding! Incomplete encoding at end of string.";
		}

		
		return sb.toString();
	}
		
	/**
	 * Encodes a decoded String of characters based on the Huffman Tree constructed
	 * via the command line file. 
	 * 
	 * @param decoded the decoded String to be encoded.
	 * @return the encoded String from the decoded String. 
	 */
	public String encode(String decoded) {
		StringBuilder encoded = new StringBuilder(); 
		
		for (int i = 0; i < decoded.length(); i++) {
			//the encodedChar is a String because it is a String of 1's and 0's
			String encodedChar = getEncoded(decoded.charAt(i));
			if (encodedChar == "") {
				return "Invalid input! Please input a String that has valid characters\n"
					 + "that exist in the Huffman Tree!";
			}
			encoded.append(encodedChar);
		}
		
		return encoded.toString();
	}
	

	
	/**
	 * Recursively loops through the Huffman tree to find the 
	 * corresponding char. 
	 * 
	 * @param theChar
	 * @return
	 */
	private String getEncoded(char theChar) {
		StringBuilder code = new StringBuilder();
		
		return getEncoded(theChar, root, code, "");
	}
	
	
	/**
	 * Recursively loops through the Huffman tree to find the 
	 * corresponding char. 
	 * 
	 * For each traversal, either 1 or 0 is added to the 
	 * StringBuilder code variable. Left is zero and right is one.
	 * When theChar is found, the function returns the toString() of 
	 * codeSoFar, assigning it to the String result.  
	 * If the char is never found, result will remain as an 
	 * empty String, signaling to the program that the inputted
	 * character does not have an existing Huffman encoding. 
	 *
	 * 
	 * @param theChar the char to search for in the Huffman tree. 
	 * @param t the current root node the program is on in the Huffman Tree.
	 * @param code the encoded version of the character that will be 
	 * incrementally built after each traversal, returning itself at the end.
	 * 
	 * @return the encoded version of theChar.  
	 */
	private String getEncoded(char theChar, HuffmanNode t, StringBuilder codeSoFar, String result) {
		StringBuilder newEncoding = new StringBuilder(codeSoFar);
		if (t != null) {
			//return encoding if character found
			if (!t.isInteriorNode() && t.content == theChar) {
				return codeSoFar.toString();
			}
			
			newEncoding.append("0");
			result = getEncoded(theChar, t.left, newEncoding, result);
			
			//traverse only if it hasn't been found in left tree
			if (result.isEmpty()) {
				newEncoding.replace(newEncoding.length()-1, newEncoding.length(), "1");
				result = getEncoded(theChar, t.right, newEncoding, result);
			}
			
			return result;
		}
		return "";
		
	}
	
	
	
	/**
	 * Uses a recursive method to print the tree. 
	 * @return a huffman table and its contents. 
	 */
	public String getHuffmanTable() {
		StringBuilder table = new StringBuilder();
		StringBuilder encode = new StringBuilder();
		
		return "Huffman Table: \n" +
				"Character | Frequency | Encoding \n"
			+ getHuffmanTable(root, table, encode);
	}
	
	/**
	 * Recursively traverses the Huffman tree to print its contents.
	 * The tree is traversed via prefix traversal.
	 * 
	 * @param t the next Huffman node to be traversed
	 * @param sb the StringBuilder that builds the String containing
	 * the Huffman tree table. 
	 * @return the Huffman Tree table. 
	 */
	private String getHuffmanTable(HuffmanNode t, StringBuilder table, StringBuilder codeSoFar) {
		StringBuilder nextEncoding = new StringBuilder(codeSoFar);
		
		if (t != null) {
			if (!t.isInteriorNode()) {
				table.append(t.getFrequencyAndContent() + codeSoFar.toString() + "\n");
			}
			nextEncoding.append("0");
			getHuffmanTable(t.left, table, nextEncoding);
			
			nextEncoding.replace(nextEncoding.length()-1, nextEncoding.length(), "1");
			getHuffmanTable(t.right, table, nextEncoding);
			
			return table.toString();
		}
		return "";
	}

	
	
	

	/**
	 * 
	 * This class represents a node in the Huffman Tree. It is either an interior
	 * node or a node that contians a character and its frequency from the input
	 * file.
	 * 
	 * @author Marcus
	 *
	 */
	private static class HuffmanNode implements Comparable<HuffmanNode> {

		HuffmanNode left;
		HuffmanNode right;
		char content;
		int frequency;
		boolean isInteriorNode;

		/**
		 * Create a node with content
		 * 
		 * @param content
		 */
		public HuffmanNode(char content) {
			frequency = 1;
			this.content = content;
			isInteriorNode = false;
		}

		/**
		 * Create interior node.
		 * 
		 * @param frequency
		 * @param left
		 * @param right
		 */
		public HuffmanNode(HuffmanNode left, HuffmanNode right) {
			this.left = left;
			this.right = right;
			frequency = left.frequency + right.frequency;
			isInteriorNode = true;
		}

		public void increment() {
			frequency++;
		}

		public boolean isInteriorNode() {
			return isInteriorNode;
		}
		
		public String getFrequencyAndContent() {
			return "" + content + " | " + frequency + " | ";
		}
		
		public String toString() {
			if (isInteriorNode) {
				return "interior: " + frequency;
			}
			return "character: '" + content + "': " + frequency;
			
		}
		
		
		/**
		 * This method is added so the priority queue is able to build the minheap.
		 */
		public int compareTo(HuffmanNode otherNode) {
			if (frequency > otherNode.frequency) {
				return 1;
			} else if (frequency < otherNode.frequency) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
