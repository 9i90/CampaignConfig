package com.luo90.campaign.expression;

import java.util.Date;

public class PubFunctions {
	public static boolean timeEquals(Date sTime,Date eTime){
		return sTime.getTime()>eTime.getTime();
	}
}
