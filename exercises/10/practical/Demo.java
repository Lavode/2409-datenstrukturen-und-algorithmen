public class Demo
{
	public static void main(String[] args) {
		demonstrate("Jobs launched into a sermon about how the Macintosh and its software would be so easy to use that there would be no manuals.");
		System.out.println("-------");
		demonstrate("An academic career in which a person is forced to produce scientific writings in great amounts creates a danger of intellectual superficiality, Einstein said.");
	}

	private static void demonstrate(String input) {
		HuffmanCode hc = new HuffmanCode();
		hc.prefixCode(input);

		System.out.println("Input:");
		System.out.println(input);

		System.out.println("Output:");
		String output = hc.encode(input);
		System.out.println(output);

		int inputLength = input.length();
		int outputLength = output.length();
		float bitsPerChar = (float)outputLength / inputLength;
		System.out.printf("Input length [chars]: %d, Output length [bits]: %d, Bits per char: %.2f\n", inputLength, outputLength, bitsPerChar);
	}
}
