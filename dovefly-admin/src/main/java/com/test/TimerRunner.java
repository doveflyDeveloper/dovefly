package com.test;

import java.util.Timer;

public class TimerRunner {
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new PlainTimerTask(), 0, 5000);
		System.out.println("-------------------");
		//timer.cancel();
		System.out.println("-------------------");
	}
}