package collectionsperformancecompare;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

public class ArrayListVsLinkedList {

	private static Set<String> l = new HashSet<>();
	//private static List<String> l =  
		// = new ArrayList<>();
		//= new LinkedList<>();

	public static void main(String[] args) {
		printMemmory();
		long t = System.nanoTime();
		for (int i = 0; i < 1000000; i++) {
			String s = RandomStringUtils.random(20);
			l.add(s);
		}

		System.out.println("time: " + (System.nanoTime() - t) / 1000000000.0);
		printMemmory();
	}

	private static void printMemmory() {
		long total = Runtime.getRuntime().totalMemory();
		long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		System.out.println("total:" + total / 1000000.0);
		System.out.println("used:" + used / 1000000.0);
	}

}