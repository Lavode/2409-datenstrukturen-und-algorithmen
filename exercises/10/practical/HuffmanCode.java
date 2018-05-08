import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class HuffmanCode
{
	private Node root;
	private HashMap<Character, String> codeTable;

	public HuffmanCode() {
	}

	public void prefixCode(String input) {
		this.root = buildTree(input);

		this.codeTable = new HashMap<Character, String>();
		generateCodeTable("", this.root);
	}

	public void printCode(String input) {
		System.out.println(this.encode(input));
	}

	public String encode(String input) {
		StringBuffer out = new StringBuffer();

		for (int i = 0; i < input.length(); i++) {
			out.append(this.codeTable.get(input.charAt(i)));
		}

		return out.toString();
	}

	private void generateCodeTable(String prefix, Node node) {
		if (node.isLeaf()) {
			// System.out.println(node.getValue() + ": " + prefix);
			this.codeTable.put(node.getValue(), prefix);
		}

		if (node.hasLeftChild()) {
			generateCodeTable(prefix + "0", node.getLeftChild());
		}

		if (node.hasRightChild()) {
			generateCodeTable(prefix + "1", node.getRightChild());
		}
	}

	private Node buildTree(String input) {
		WordFrequencyComparator comp = new WordFrequencyComparator();
		PriorityQueue<Node> tree = new PriorityQueue<Node>(comp);

		// Create one leaf node for each character.
		for (Map.Entry<Character, Integer> entry : countCharFrequencies(input).entrySet()) {
			Node node = new Node(entry.getValue());
			node.setValue(entry.getKey());
			tree.add(node);
		}

		// Combine leaf nodes until one left.
		while (tree.size() > 1) {
			// Pull two 'smallest' elements, combine into one node, add back to pool.
			Node node1 = tree.poll();
			Node node2 = tree.poll();

			Node parent = new Node(node1.getFrequency() + node2.getFrequency());
			parent.setLeftChild(node1);
			parent.setRightChild(node2);

			tree.add(parent);
		}

		// Return root node
		return tree.poll();

	}

	private HashMap<Character, Integer> countCharFrequencies(String input) {
		HashMap<Character, Integer> charFrequency = new HashMap<Character, Integer>();

		for (char c : input.toCharArray()) {
			charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
		}

		// for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
		// 	System.out.println(entry.getKey() + ": " + entry.getValue());
		// }

		return charFrequency;
	}
}
