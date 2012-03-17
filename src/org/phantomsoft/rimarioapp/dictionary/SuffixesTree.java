package org.phantomsoft.rimarioapp.dictionary;

import java.util.Comparator;
import java.util.TreeSet;

class SuffixesTree extends TreeSet<String> {

    TreeSet<String> tree;

    public SuffixesTree() {
	super(new ReverseComparator());
    }

}

class ReverseComparator implements Comparator<String> {
    public int compare(String a, String b) {

	StringBuffer str1 = new StringBuffer(a.toLowerCase());
	str1 = str1.reverse();

	StringBuffer str2 = new StringBuffer(b.toLowerCase());
	str2 = str2.reverse();

	return str1.toString().compareTo(str2.toString());
    }
}
