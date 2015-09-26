package stringsalg;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public class Strtets {
	public static void main(String[] args) {
		String s1 = "Украина примет участие в крупнейших учениях НАТО";
		String s2 = "Украинские военные примут участие в крупнейших учениях НАТО";
		
		int l = Math.min(s1.length(), s2.length());
		
		System.out.println(1.0 * StringUtils.getLevenshteinDistance(s1, s2) / l);
		
		String[] ss1 = s1.split("[\\s\\p{Punct}]");
		String[] ss2 = s2.split("[\\s\\p{Punct}]");

		SortedSet<String> sss1 = new TreeSet<>();
		for (String s : ss1)
			if (!StringUtils.isEmpty(s) && s.length() > 2)
				sss1.add(s);

		SortedSet<String> sss2 = new TreeSet<>();
		for (String s : ss2)
			if (!StringUtils.isEmpty(s) && s.length() > 2)
				sss2.add(s);

		s1 = s2 = "";

		for (String s : sss1)
			s1 += s;
		
		for (String s : sss2)
			s2 += s;
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(1.0 * StringUtils.getLevenshteinDistance(s1, s2) / l);
	}
}