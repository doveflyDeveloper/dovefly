package com.test;

import java.util.Date;
import java.util.TimerTask;

public class PlainTimerTask extends TimerTask {
	@Override
	public void run() {
		System.out.println(new Date());
	}
}