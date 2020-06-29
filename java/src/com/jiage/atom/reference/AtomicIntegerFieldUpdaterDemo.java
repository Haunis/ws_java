package com.jiage.atom.reference;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {
	public static class Candidate {
		int id;
		volatile int score;
	}

	public static class Game {
		int id;
		volatile String name;

		public Game(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public String toString() {
			return "Game{" + "id=" + id + ", name='" + name + '\'' + '}';
		}
	}

	static AtomicIntegerFieldUpdater<Candidate> atIntegerUpdater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class,
			"score");

	//atRefUpdate持有 Game类 ，name 属性的引用
	static AtomicReferenceFieldUpdater<Game, String> atRefUpdate = AtomicReferenceFieldUpdater.newUpdater(Game.class,
			String.class, "name");
	// 用于验证分数是否正确
	public static AtomicInteger allScore = new AtomicInteger(0);

	public static void main(String[] args) throws InterruptedException {
		final Candidate candinate = new Candidate();
		Thread[] t = new Thread[10000];
		// 开启10000个线程
		for (int i = 0; i < 10000; i++) {
			t[i] = new Thread() {
				public void run() {
					if (Math.random() > 0.4) {
						atIntegerUpdater.incrementAndGet(candinate);
						allScore.incrementAndGet();
					}
				}
			};
			t[i].start();
		}

		for (int i = 0; i < 10000; i++) {
			t[i].join();//当前线程挂起，等待t[i]线程执行结束
		}
		System.out.println("最终分数score=" + candinate.score);
		System.out.println("校验分数allScore=" + allScore);

		// AtomicReferenceFieldUpdater 简单的使用
		Game game = new Game(2, "zh");
		System.out.println("before "+game.toString());
		atRefUpdate.compareAndSet(game, game.name, "JAVA-HHH");
		System.out.println(game.toString());
	}
}
