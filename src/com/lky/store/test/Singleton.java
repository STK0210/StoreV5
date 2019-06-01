package com.lky.store.test;

public class Singleton {

	private int i;

	private Singleton() {
		i = 0;
	}

	private static class SingletonInstance {
		private static final Singleton INSTANCE = new Singleton();
	}

	public static Singleton getInstance() {
		return SingletonInstance.INSTANCE;
	}

	public void addI() {
		i++;
	}

	public int getI() {
		return i;
	}
}
