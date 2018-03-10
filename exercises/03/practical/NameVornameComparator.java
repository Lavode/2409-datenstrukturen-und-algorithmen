/* 
 * Comparator which allows to compare two students based on last and first name.
 */
public class NameVornameComparator implements java.util.Comparator<StudentIn>
{

	private String comparisonString(StudentIn student) {
		return student.getName() + student.getVorname();
	}

	public int compare(StudentIn a, StudentIn b) {
		return comparisonString(a).compareTo(comparisonString(b));
	}
}
