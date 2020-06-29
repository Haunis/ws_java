package com.jiage.excel;

/**
 * 将每一行数据封装成Person,并且Person带section,value属性
 *
 */
public class Person implements Comparable<Person> {
	public String[] infos;
	public String section;
	public int value;
	public boolean isTopRanked = false;// 排名靠前
	public boolean isBotRanked = false;// 排名靠后 

	public Person(String[] infos, String section, int value) {
		this.infos = infos;
		this.section = section;
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (infos != null) {
			for (String str : infos) {
				sb.append(str).append(" ");
			}
		}
		return sb.toString();
	}

	@Override
	public int compareTo(Person o) {

		return o.value - this.value;
	}

}
