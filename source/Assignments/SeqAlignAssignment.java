package source.Assignments;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import source.Alignment;
import source.Functions;

/* SeqAlignAssignment.java
 * Jonathan Stryer
 * Syracuse University, Computer Science, Class of 2018
 */

public class SeqAlignAssignment {

	public static void main(String[] args) throws IOException {
				
		ArrayList<String> readFile = Functions.reader("Alignment_gaps.txt");
		ArrayList<String> names = Functions.names(readFile);
		ArrayList<ArrayList<Character>> seqs = Functions.sequences(readFile);
		
		ArrayList<ArrayList<Character>> returnVal = Alignment.twoSeqAlign(seqs.get(0), seqs.get(1));
		ArrayList<String> names2 = new ArrayList<String>();
		
		names2.add(names.get(0));
		names2.add(names.get(1));
		
		Functions.printAligned(names2, returnVal);
	}
}