public class Node
{
	private Node leftChild;
	private Node rightChild;
	private Character value;
	private Integer frequency;

	public Node(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getFrequency() {
		return this.frequency;
	}

	public Character getValue() {
		return this.value;
	}

	public void setValue(Character value) {
		this.value = value;
	}

	public Node getLeftChild() {
		return this.leftChild;
	}

	public void setLeftChild(Node node) {
		this.leftChild = node;
	}

	public Node getRightChild() {
		return this.rightChild;
	}

	public void setRightChild(Node node) {
		this.rightChild = node;
	}
}
