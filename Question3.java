package se320woerk;

/*
 * Author: Naimah-Joy Chapman
 * Course: SE320
 * Date: 12/8/2020
 */
public class Question3 {
	public static <E extends Comparable<E>> int linearSearch(E[] list, E value) {

		for (int i = 0; i < list.length; i++) {
			if (list[i].compareTo(value) == 0) {
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		Integer[] list = { 3, 4, 5, 1, -3, -5, -1 };
		System.out.println(linearSearch(list, 2));
		System.out.println(linearSearch(list, 5));
	}
}