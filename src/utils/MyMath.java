package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyMath {

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(value);// (value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue(); // round one place after decimal points !!!!
		// return Math.floor(value * 100) / 100;
	}

	public static double getValueFromPercent(double value, double percent) {
		return MyMath.round((value * (percent / 100.0)), 2);
	}

	public static void main(String args[]) {
		double percent = 0.9;
		double value = 674;
		System.out.println(findXFromPercentAndValue(percent, value));
	}

	public static double findXFromPercentAndValue(double percent, double value) {
		percent = percent / 100.0;
		if (percent < 0) {
			return 0;// convertDecimalToFraction(-percent, value);
		}
		// преобразуваме реално число към дроб за да намерим неизвестното X !!!
		double tolerance = 1.0E-6;
		double h1 = 1;
		double h2 = 0;
		double k1 = 0;
		double k2 = 1;
		double b = percent;
		do {
			double a = Math.floor(b);
			double aux = h1;
			h1 = a * h1 + h2;
			h2 = aux;
			aux = k1;
			k1 = a * k1 + k2;
			k2 = aux;
			b = 1 / (b - a);
		} while (Math.abs(percent - h1 / k1) > percent * tolerance);

		double x = k1 + h1;
		// System.out.printf("%s ", h1 + "/" + k1);
		return ((value * k1) / x);
	}

}
