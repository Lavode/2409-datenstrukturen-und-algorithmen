import java.util.Comparator;
import java.util.Map;

public class WordFrequencyComparator implements Comparator<Node>
{
    @Override
    public int compare(Node x, Node y) {
	    return x.getFrequency().compareTo(y.getFrequency());
    }
}
