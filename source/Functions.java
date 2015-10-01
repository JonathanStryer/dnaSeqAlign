package source;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/* Functions.java
 * Jonathan Stryer
 * Syracuse University, Computer Science, Class of 2018
 */

public class Functions {

	private static Scanner read;

	// Method that reads a text file for DNA sequences
	public static ArrayList<String> reader(String fileName) throws IOException {

		ArrayList<String> sequences = new ArrayList<String>();

		File txt = new File(fileName);
		read = new Scanner(txt);
				
		do {
			sequences.add(read.nextLine());
		}
		while (read.hasNextLine());

		read.close();
		return sequences;
	}

	// Method takes a read text file and puts the name of each species in an
	// ArrayList<String>
	public static ArrayList<String> names(ArrayList<String> file) {

		ArrayList<String> names = new ArrayList<String>();

		int num = 0;

		while (num < file.size()) {

			names.add(file.get(num));
			num += 2;
		}

		return names;
	}

	// Method takes a read text file and puts the sequence of each species in
	// ArrayList<ArrayList<Character>>
	public static ArrayList<ArrayList<Character>> sequences(
			ArrayList<String> file) {

		ArrayList<ArrayList<Character>> seqs = new ArrayList<ArrayList<Character>>();
		int num = 1;

		while (num < file.size()) {

			ArrayList<Character> temp = new ArrayList<Character>();

			for (int i = 0; i < file.get(num).length(); i++) {

				temp.add(file.get(num).charAt(i));
			}

			seqs.add(temp);
			num += 2;
		}

		return seqs;
	}

	// Method prints aligned DNA sequences (for biSeqAlign)
	public static void printAligned(ArrayList<String> names,
			ArrayList<ArrayList<Character>> sequences) {

		ArrayList<ArrayList<Character>> seqs = new ArrayList<ArrayList<Character>>();
		seqs.add(sequences.get(0));
		seqs.add(sequences.get(1));

		ArrayList<Character> comp = new ArrayList<Character>();

		// Compares
		for (int i = 0; i < seqs.get(0).size(); i++) {

			if (seqs.get(0).get(i) == seqs.get(1).get(i)) {

				comp.add('*');
			} else {

				comp.add(' ');
			}
		}

		seqs.add(comp);

		// Prints
		System.out.println(names.get(0));
		System.out.println(names.get(1));

		while (seqs.get(0).size() != 0) {

			int index = seqs.get(0).size();

			for (int i = 0; i < 3; i++) {

				if (index < 50) {

					for (int x = 0; x < index; x++) {

						System.out.print(seqs.get(i).get(x));
					}

					System.out.println();
				} else {
					for (int j = 0; j < 50; j++) {

						System.out.print(seqs.get(i).get(j));
					}

					System.out.println();

				}
			}

			if (index < 50) {

				for (int i = 0; i < index; i++) {

					seqs.get(0).remove(0);
					seqs.get(1).remove(0);
					seqs.get(2).remove(0);
				}
			} else {

				for (int i = 0; i < 50; i++) {
						seqs.get(0).remove(0);
						seqs.get(1).remove(0);
						seqs.get(2).remove(0);
				}
			}
		}
	}

	// Method compares base pair difference between two nucleotide sequences.
	public static int bpDif(String specie1, String specie2) {
		int delta = 0;
		int length = 0;

		if (specie1.length() < specie2.length()) {
			length = specie1.length();
		} else {
			length = specie2.length();
		}

		for (int i = 0; i < length; i++) {
			if (specie1.charAt(i) != specie2.charAt(i)) {
				delta++;
			}
		}

		return delta;
	}

	// Method compares difference between two amino acid sequences.
	public static int aaDif(String specie1, String specie2) {
		int delta = 0;
		int length = 0;

		if (specie1.length() < specie2.length()) {
			length = specie1.length();
		} else {
			length = specie2.length();
		}

		for (int i = 0; i < length; i++) {
			if (specie1.charAt(i) != specie2.charAt(i)) {
				delta++;
			}
		}

		return delta;
	}

	// Method takes two sequences and returns the percent similarity between them. The method will check to see if the smaller (second) sequence is contained in the l
	// larger sequence with an 80% of higher similarity. 
	// The method will also do a reverse primer on the larger sequence to see if the smaller sequence is contained in it. 
	
	// In my BacteriaSeqMatch program, the seq1 will be the DNA sequence and seq2 be the Bacteria DNA.
	public static boolean simCheck(ArrayList<Character> seq1, ArrayList<Character> seq2) {
		
		double match; 
		double percent = 0.0; 
		double seq2size = (double) seq2.size();
		
		//-----------------------------------------------------------------------------------------------------------------//
		// Does a reverse primer on seq1.
		
		ArrayList<Character> seq1C = new ArrayList<Character>(); // Reverse complement of sequence1.
		
		for (int i = seq1.size() - 1; i > 0; i--) {
			
			switch (seq1.get(i)) {
			
			case 'A': seq1C.add('T');
					  break; 
			case 'T': seq1C.add('A');
			          break;
			case 'G': seq1C.add('C');
				      break;
			case 'C': seq1C.add('G');
				      break;
			case 'N': seq1C.add('N');
					  break;
		    default:  break;
			}
		}
		//-----------------------------------------------------------------------------------------------------------------//
		
		//-----------------------------------------------------------------------------------------------------------------//
        // Nested for-loops does the comparisons (regular).
		for (int i = 0; i < seq1.size(); i++) {
			
			match = 0.0; 
			
			for (int j = 0; j < seq2.size(); j++) {
				
				if (i + j < seq1.size()) {
					if ((seq1.get(i + j) == seq2.get(j)) || (seq1.get(i+j) == 'N' || seq2.get(j) == 'N')) {
						match += 1.0;
					}
				}

			}
			
			percent = (double) match/seq2size;
			
			if (percent >= 0.80) {
				return true; 
			}
		}
		//-----------------------------------------------------------------------------------------------------------------//
		
		//-----------------------------------------------------------------------------------------------------------------//
        // Nested for-loops does the comparisons (reverse).
		for (int i = 0; i < seq1C.size(); i++) {
			
			match = 0.0; 
			
			for (int j = 0; j < seq2.size(); j++) {
				
				if (i + j < seq1C.size()) {
					if ((seq1C.get(i + j) == seq2.get(j)) || (seq1C.get(i+j) == 'N' || seq2.get(j) == 'N')) {
						match += 1.0;
					}
				}

			}
			
			percent = (double) match/seq2size;
			
			if (percent >= 0.80) {
				return true; 
			}
		}
		//-----------------------------------------------------------------------------------------------------------------//
		
		
		return false;
	}

	// Variation of simCheck to print aligned DNA and Bacteria Sequence
	public static void alignCheck(ArrayList<Character> seq1, ArrayList<Character> seq2) {
		
		double match; 
		double percent = 0.0; 
		double seq2size = (double) seq2.size();
		
		//-----------------------------------------------------------------------------------------------------------------//
		// Does a reverse primer on seq1.
		
		ArrayList<Character> seq1C = new ArrayList<Character>(); // Reverse complement of sequence1.
		
		for (int i = seq1.size() - 1; i > 0; i--) {
			
			switch (seq1.get(i)) {
			
			case 'A': seq1C.add('T');
					  break; 
			case 'T': seq1C.add('A');
			          break;
			case 'G': seq1C.add('C');
				      break;
			case 'C': seq1C.add('G');
				      break;
			case 'N': seq1C.add('N');
					  break;
		    default:  break;
			}
		}
		//-----------------------------------------------------------------------------------------------------------------//
		
		//-----------------------------------------------------------------------------------------------------------------//
        // Nested for-loops does the comparisons (regular).
		for (int i = 0; i < seq1.size(); i++) {
			
			match = 0.0; 
			
			for (int j = 0; j < seq2.size(); j++) {
				
				if (i + j < seq1.size()) {
					if ((seq1.get(i + j) == seq2.get(j)) || (seq1.get(i+j) == 'N' || seq2.get(j) == 'N')) {
						match += 1.0;
					}
				}

			}
			
			percent = (double) match/seq2size;
			
			if (percent >= 0.80) {
				
				for (int x = 0; x < seq1.size(); x++) {
					System.out.print(seq1.get(x));
				}
				System.out.println();
				
				while (i != 0) {
					System.out.print(' '); 
					i--;
				}
				for (int j = 0; j < seq2.size(); j++) {
					System.out.print(seq2.get(j));
				}
				System.out.println();
				return;
			}
		}
		//-----------------------------------------------------------------------------------------------------------------//
		
		//-----------------------------------------------------------------------------------------------------------------//
        // Nested for-loops does the comparisons (reverse).
		for (int i = 0; i < seq1C.size(); i++) {
			
			match = 0.0; 
			
			for (int j = 0; j < seq2.size(); j++) {
				
				if (i + j < seq1C.size()) {
					if ((seq1C.get(i + j) == seq2.get(j)) || (seq1C.get(i+j) == 'N' || seq2.get(j) == 'N')) {
						match += 1.0;
					}
				}

			}
			
			percent = (double) match/seq2size;

			if (percent >= 0.80) {

				for (int x = 0; x < seq1C.size(); x++) {
					System.out.print(seq1C.get(x));
				}
				System.out.println();

				while (i != 0) {
					System.out.print(' ');
					i--;
				}
				for (int j = 0; j < seq2.size(); j++) {
					System.out.print(seq2.get(j));
				}
				System.out.println();
				return;
			}
			
			
		}
		//-----------------------------------------------------------------------------------------------------------------//
	}
}
