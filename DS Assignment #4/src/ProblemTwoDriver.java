import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * 
 * Run this file given some text file to create a huffman encoding.
 * Then, input a string of text into the console to be encoded. 
 * 
 * @author Marcus Fong
 *
 */
public class ProblemTwoDriver {
	static Scanner scanner;
	static Scanner inputScanner;
	static HuffmanTree theTree;
	
	
	public static void main(String[] args) {
		theTree = new HuffmanTree();
		if (args.length > 0) { 
			File theFile = new File(args[0]);
			
			try {
				scanner = new Scanner(theFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
					
			readFile(scanner);
			theTree.buildPrioQueue();
			theTree.buildHuffmanTree();
				
			System.out.println("This is the table of your Huffman Tree values: \n" 
			+ theTree.getHuffmanTable());
			
			promptUser();
			
		}

	}
	
	/**
	 * This method loops through each line of the given input file.
	 * 
	 * @param scanner the Scanner object to scan through the file.
	 */
	private static void readFile(Scanner scanner) {

		while (scanner.hasNextLine()){
			String line = scanner.nextLine().trim();
			if (line.isEmpty()) {
				continue;
			}
			processLine(line);
		}
	}
	
	/**
	 * 
	 * Adds each character into the HuffmanTree database.
	 * 
	 * @param line the line of text to be processed char by char.
	 */
	private static void processLine(String line) {
	
		for (int i = 0; i < line.length(); i++) {
			 theTree.addChar(line.charAt(i));
		}
		
	}
	
	/**
	 * This method runs the last two parts of the problem. It first 
	 * asks the user to input an encoded String to be translated. Then,
	 * it asks the user to input a not encoded String to encoded. 
	 * Errors in either input will make the console return an error.
	 */
	public static void promptUser() {
		inputScanner = new Scanner(System.in);
		System.out.println("Welcome to my Huffman encoder/decoder!\n"
						+  "Please enter a valid Huffman encoding to be translated.\n"
						+ "An error will be displayed if there is an error in the encoding.\n");
		String encodedInput = inputScanner.nextLine();
		
		System.out.println("This is your decoded line: ");
		System.out.println(theTree.decode(encodedInput)+"\n");
		System.out.println("Now, please input a decoded String of characters to be encoded."
						+ "Make sure the characters you input existed in the input file loaded"
						+ "in the command line. If they don't the console will return "
						+ "an error.");
		
		String decodedInput = inputScanner.nextLine();
		
		System.out.println("This is your encoded line: ");
		System.out.println(theTree.encode(decodedInput));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
