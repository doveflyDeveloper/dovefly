package com.deertt.utils.helper.number;

public class RMBCaptial {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(DX("2220344500.23453"));
	}

	public static String delcommafy(String num) {
		if (num == null || "".equals(num)) {
			return "";
		}
		num = num.replaceAll(",", "");
		return num;
	}

	public static String DX(String n) {
		n = n.trim();

		if (n.indexOf(",") >= 0) {
			n = delcommafy(n);
		}

		if (!n.matches("^(0|[1-9]\\d*)(\\.\\d+)?$"))
			return "";// return "数据非法";
		String unit = "仟佰拾亿仟佰拾万仟佰拾元角分";
		String str = "";
		n += "00";
		int p = n.indexOf('.');
		if (p >= 0)
			n = n.substring(0, p) + n.substring(p + 1, p + 3);
		unit = unit.substring(unit.length() - n.length());
		// System.out.println(unit);
		for (int i = 0; i < n.length(); i++)
			str += "零壹贰叁肆伍陆柒捌玖".substring(
					Integer.parseInt(String.valueOf(n.charAt(i))),
					Integer.parseInt(String.valueOf(n.charAt(i))) + 1)
					+ unit.substring(i, i + 1);
		return str.replaceAll("零(仟|佰|拾|角)", "零").replaceAll("(零)+", "零")
				.replaceAll("零(万|亿|元)", "$1").replaceAll("(亿)万|壹(拾)", "$1$2")
				.replaceAll("^元零?|零分", "").replaceAll("元$", "元整");
	}
}
