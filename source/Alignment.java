package source;

import java.util.ArrayList;

/* Alignment.java
 * Jonathan Stryer
 * Syracuse University, Computer Science, Class of 2018
 */

/* Terms used */
// Streak - When the index of one sequence equals the index of another sequence one or more times. 

public class Alignment {
	
	// Call this method to align two DNA sequences (contains refinement process)
	public static ArrayList<ArrayList<Character>> twoSeqAlign(
			ArrayList<Character> sequence1, ArrayList<Character> sequence2) {

		ArrayList<ArrayList<Character>> sequences = new ArrayList<ArrayList<Character>>(); // Stores the aligned sequences from biSeqAlign
		ArrayList<Character> seq1 = sequence1; // Stores the first sequence
		ArrayList<Character> seq2 = sequence2; // Stores the second sequence

		sequences = biSeqAlign(seq1, seq2); // Assigns the return value of putting sequence 1 and sequence 2 into biSeqAlign 

		seq1 = sequences.get(0); // Stores the first sequence that was aligned
		seq2 = sequences.get(1); // Stores the second sequence that was aligned

		/*--------------------------------------------------------------------------------------- */
		/* Refinement Process */ 
		/* This process works to eliminate excess '-' by locating areas that start with '-' and end with a streak of four. */
		/*--------------------------------------------------------------------------------------- */

		Boolean init = false; // False when locating a '-'. True when locating a streak of three.
		int markerStart = 0; // The index where the refinement starts.
		int markerEnd = 0; // The index where the refinement ends.
		int index = 0; // Current working index.

		// This loop works through the whole sequences.
		while (index < seq1.size()) {

			/* If the value at the index of sequence 1 or sequence 2 is equal to
			 * '-' and init is equal to false, set markerStart to the working
			 * index, init to true, and increment the index by 1.
			 */
			if ((seq1.get(index) == '-' || seq2.get(index) == '-')
					&& (init == false)) {

				markerStart = index;
				init = true;
				index++;
			}
			
			/* Else if the working index is equal to the size of sequence 1 minus 1 and init is true,
			 * increment index by 1, declare a temporary arraylist and assign the values returned by the refiner method, 
			 * and assign the first value in temp to seq1 (sequence 1) and the second value in temp to seq2 (sequence 2). 
			 */
			
			// The following statement is used if the working index is the last index in the sequence. 
			else if ((index == seq1.size() - 1) && (init == true)) {

				index++;
				ArrayList<ArrayList<Character>> temp = new ArrayList<ArrayList<Character>>();
				// Refine here
				temp = refiner(seq1, seq2, markerStart, index); // Assigns the value to temp returned by refiner by inputting the variables shown. 
				seq1 = temp.get(0);
				seq2 = temp.get(1);

			} 
			
			/* Else if the working index of sequence 1 is equal to the working index of sequence 2 and init is equal to true,
			 * markerEnd equals the working index and index is incremented by 1. This goes through three more times until the 
			 * refine section is reached. 
			 */
			else if ((seq1.get(index) == seq2.get(index)) && (init == true)) {

				markerEnd = index; 
				index++;

				if (seq1.get(index) == seq2.get(index)) {

					index++;

					if (seq1.get(index) == seq2.get(index)) {

						index++;

						if (seq1.get(index) == seq2.get(index)) {

							index++;

							// Refine here. 
							ArrayList<ArrayList<Character>> temp = new ArrayList<ArrayList<Character>>(); // Used the store the values returned by inputting sequence 1 and sequence 2 into refiner. 
							temp = refiner(seq1, seq2, markerStart, markerEnd);
							seq1 = temp.get(0); 
							seq2 = temp.get(1);
							init = false; // Sets init to false so a new refinement process can start if possible. 
						}

					}
				}
			} 
			
			// Increases the index by 1. 
			else {

				index++;
			}

		}

		// Returns aligned and refined sequence 1 and 2 in an ArrayList. 
		ArrayList<ArrayList<Character>> seqs = new ArrayList<ArrayList<Character>>();
		seqs.add(seq1);
		seqs.add(seq2);
		return seqs;
	}

	// Helper method for twoSeqAlign (refiner)
	public static ArrayList<ArrayList<Character>> refiner(
			ArrayList<Character> sequence1, ArrayList<Character> sequence2,
			int start, int end) {

		ArrayList<ArrayList<Character>> seqs = new ArrayList<ArrayList<Character>>();
		ArrayList<Character> seq1 = sequence1;
		ArrayList<Character> seq2 = sequence2;

		ArrayList<Character> seg1 = new ArrayList<Character>();
		ArrayList<Character> seg2 = new ArrayList<Character>();

		int temp = end - start;

		while (temp != 0) {

			seg1.add(seq1.get(start));
			seg2.add(seq2.get(start));

			seq1.remove(start);
			seq2.remove(start);

			temp--;
		}

		// Remove excess '-' and check out results

		int i = 0;

		while (seg1.contains('-') && seg2.contains('-')) {

			Boolean erk = false;
			i = 0;

			do {
				if (seg1.get(i) == '-') {

					seg1.remove(i);
					erk = true;
				}
				i++;
			} while (erk == false);

			erk = false;
			i = 0;

			do {
				if (seg2.get(i) == '-') {

					seg2.remove(i);
					erk = true;
				}
				i++;
			} while (erk == false);
		}

		seq1.addAll(start, seg1);
		seq2.addAll(start, seg2);

		seqs.add(seq1);
		seqs.add(seq2);

		return seqs;
	}

	// Method that does the bulk of the work to align two DNA sequences
	public static ArrayList<ArrayList<Character>> biSeqAlign(
			ArrayList<Character> sequence1, ArrayList<Character> sequence2) {

		ArrayList<ArrayList<Character>> temp = new ArrayList<ArrayList<Character>>();
		ArrayList<Character> seq1 = sequence1;
		ArrayList<Character> seq2 = sequence2;

		ArrayList<ArrayList<Character>> seqs = new ArrayList<ArrayList<Character>>();
		ArrayList<Character> s1 = new ArrayList<Character>();
		ArrayList<Character> s2 = new ArrayList<Character>();

		/*--------------------------------------------------------*/

		int index = 0;

		while (index < seq1.size() && index < seq2.size()) {
			if (seq1.get(index) == seq2.get(index)) {

				index++;
			} else {

				break;
			}
		}

		int y = 0;
		while (y < index) {

			s1.add(seq1.get(y));
			s2.add(seq2.get(y));
			y++;
		}

		int rem = 0;

		while (rem < index) {

			seq1.remove(0);
			seq2.remove(0);
			rem++;
		}

		/*--------------------------------------------------------*/
		int numShift = 0;

		if ((longestStreak(seq1, seq2) == 1 && longestStreak(seq2, seq1) == 1)
				|| (longestStreak(seq1, seq2) == 0 && longestStreak(seq2, seq1) == 0)
				|| (longestStreak(seq1, seq2) == 1 && longestStreak(seq2, seq1) == 0)
				|| (longestStreak(seq1, seq2) == 0 && longestStreak(seq2, seq1) == 1)) {

			ArrayList<ArrayList<Character>> end = new ArrayList<ArrayList<Character>>();

			int sizeDifference = 0;

			if (seq1.size() > seq2.size()) {

				sizeDifference = seq1.size() - seq2.size();

				while (sizeDifference > 0) {

					sizeDifference--;
					seq2.add('-');
				}
			} else {

				sizeDifference = seq2.size() - seq1.size();

				while (sizeDifference > 0) {

					sizeDifference--;
					seq1.add('-');
				}
			}

			s1.addAll(seq1);
			s2.addAll(seq2);

			end.add(s1);
			end.add(s2);

			return end;

		} else if (longestStreak(seq1, seq2) > longestStreak(seq2, seq1)) {

			numShift = numShift(seq1, seq2);
			seq1 = shift(seq1, numShift);
		} else {

			numShift = numShift(seq2, seq1);
			seq2 = shift(seq2, numShift);
		}

		int longestStreakStart = 0; // Locates start of longestStreak

		if (longStart(seq1, seq2) < longStart(seq2, seq1)) {

			longestStreakStart = longStart(seq1, seq2);
		} else {

			longestStreakStart = longStart(seq2, seq1);
		}

		ArrayList<Character> tempo1 = new ArrayList<Character>();
		ArrayList<Character> tempo2 = new ArrayList<Character>();

		if (longestStreakStart != 0) {
			int x = 0;
			while (x < longestStreakStart) {
				tempo1.add(seq1.get(x));
				tempo2.add(seq2.get(x));
				x++;
			}
		}

		temp = biBoundsAlign(tempo1, tempo2);
		s1.addAll(temp.get(0));
		s2.addAll(temp.get(1));

		rem = 0;

		while (rem < longestStreakStart) {

			seq1.remove(0);
			seq2.remove(0);
			rem++;
		}

		/*--------------------------------------------------------*/

		ArrayList<ArrayList<Character>> temp2 = new ArrayList<ArrayList<Character>>();
		temp2 = biSeqAlign(seq1, seq2);
		s1.addAll(temp2.get(0));
		s2.addAll(temp2.get(1));

		/*--------------------------------------------------------*/

		seqs.add(s1);
		seqs.add(s2);

		return seqs;
	}

	// Helper method for biSeqAlign to count the longestStreak
	public static int longestStreak(ArrayList<Character> seq1,
			ArrayList<Character> seq2) {

		int longest = 0;
		int temp = 0;

		for (int i = 0; i < seq1.size(); i++) {

			for (int j = 0; j < seq2.size() && j < seq1.size()
					&& i + j < seq2.size(); j++) {

				if (seq1.get(j) == seq2.get(i + j)) {

					temp++;
				} else {

					if (temp > longest) {

						longest = temp;
					}

					temp = 0;
				}
			}
		}

		return longest;
	}

	// Helper method for biSeqAlign to shift DNA sequence according to
	// longestStreak
	public static ArrayList<Character> shift(ArrayList<Character> seq,
			int numShift) {

		int shift = numShift;

		while (shift != 0) {

			seq.add(0, '-');
			shift--;
		}

		return seq;
	}

	// Helper method for shift to find the shift from a variation of
	// longestStreak method
	public static int numShift(ArrayList<Character> seq1,
			ArrayList<Character> seq2) {

		int longest = 0;
		int temp = 0;
		int shift = 0;

		for (int i = 0; i < seq1.size(); i++) {

			for (int j = 0; j < seq2.size() && j < seq1.size()
					&& i + j < seq2.size(); j++) {

				if (seq1.get(j) == seq2.get(i + j)) {

					temp++;
				} else {

					if (temp > longest) {

						longest = temp;
						shift = i;
					}

					temp = 0;
				}
			}
		}

		return shift;
	}

	// Helper method for biSeqAlign to find the start of longestStreak
	public static int longStart(ArrayList<Character> seq1,
			ArrayList<Character> seq2) {

		int longestStart = 0;
		int tempLongStart = 0;
		int longest = 0;
		int temp = 0;

		for (int i = 0; i < seq1.size(); i++) {

			for (int j = 0; j < seq2.size() && j < seq1.size()
					&& i + j < seq2.size(); j++) {

				if (seq1.get(j) == seq2.get(i + j)) {

					if (temp == 0) {

						tempLongStart = i + j;
					}

					temp++;
				} else {

					if (temp > longest) {

						longest = temp;
						longestStart = tempLongStart;
					}

					temp = 0;
				}
			}
		}

		return longestStart;
	}

	// Helper method for biSeqAlign to align two DNA sequences (left to
	// longestStreak/there are bounds now)
	public static ArrayList<ArrayList<Character>> biBoundsAlign(
			ArrayList<Character> sequence1, ArrayList<Character> sequence2) {

		ArrayList<ArrayList<Character>> sequences = new ArrayList<ArrayList<Character>>();
		ArrayList<Character> temp = new ArrayList<Character>();
		temp.add('-');
		sequence1.removeAll(temp);
		sequence2.removeAll(temp);

		sequences.add(sequence1);
		sequences.add(sequence2);

		sequences = biSeqAlign(sequences.get(0), sequences.get(1));

		return sequences;
	}
}
