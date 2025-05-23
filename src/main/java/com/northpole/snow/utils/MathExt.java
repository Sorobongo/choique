package com.northpole.snow.utils;

import java.math.BigDecimal;

public class MathExt {

	public static BigDecimal getComplementToOne(BigDecimal x) {
		return BigDecimal.ONE.subtract(x.divide(BigDecimal.valueOf(100.00)));
	}
}
