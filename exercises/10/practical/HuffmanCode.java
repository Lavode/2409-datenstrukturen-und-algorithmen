import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class HuffmanCode
{
	private Node root;

	public HuffmanCode() {
	}

	public void prefixCode(String input) {
		this.root = buildTree(input);
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

		return charFrequency;
	}
}
