package ru.rusak.fedor.explorer;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
  *  This test is used as a simple yet effective way to verify that some data structures
  *  are NOT thread-sage and therefore should NOT be used in case of concurrent access to them.
  *  Same approach can verify that other data structure are actually thread-safe and are actually
  *  used in this application.
  */
@Ignore
public class SetMapTest {

	private abstract static class DataStructureInteraction implements Runnable {

		private int startValue;
		private int quantityToProcess;
		private CountDownLatch latch;

		public DataStructureInteraction(int startValue, int quantityToProcess, CountDownLatch latch) {
			this.startValue = startValue;
			this.quantityToProcess = quantityToProcess;
			this.latch = latch;
		}

		abstract void add(int i);

		abstract void remove(int i);

		public void run() {
			for (int i = 0; i < quantityToProcess; i++) {
				add(i+startValue);
			}

			for (int i = 0; i < quantityToProcess; i++) {
				remove(i+startValue);
			}

			latch.countDown();
		}
	}

	private static class SetInteraction extends DataStructureInteraction {

		private Set<Integer> set;

		public SetInteraction(Set<Integer> set, int startValue, int quantityToProcess, CountDownLatch latch) {
			super(startValue, quantityToProcess, latch);
			this.set = set;
		}

		@Override
		public void add(int i) {
			set.add(i);
		}

		@Override
		public void remove(int i) {
			set.remove(i);
		}

	}

	private static class MapInteraction extends DataStructureInteraction {

		private Map<Integer, Integer> map;

		public MapInteraction(Map<Integer, Integer> map, int startValue, int quantityToProcess, CountDownLatch latch) {
			super(startValue, quantityToProcess, latch);
			this.map = map;
		}

		@Override
		public void add(int i) {
			map.put(i, i);
		}

		@Override
		public void remove(int i) {
			map.remove(i);
		}

	}


	private static int getSizeAfterConcurrentUsage(Set<Integer> setToTest, Map<Integer, Integer> mapToTest) throws Exception {
		final int THREAD_COUNT = 2;
		CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
		Thread[] threads = new Thread[THREAD_COUNT];

		final int QUANTITY = 500; 

		for (int i = 0; i < THREAD_COUNT; i++) {
			DataStructureInteraction interaction = setToTest != null
				? new SetInteraction(setToTest, i*QUANTITY, QUANTITY, latch)
				: new MapInteraction(mapToTest, i*QUANTITY, QUANTITY, latch);

			threads[i] = new Thread(interaction);
		}

		for (Thread thread: threads) {
			thread.start();
		}

		latch.await();

		return setToTest != null ? setToTest.size() : mapToTest.size();
	}

	private static int getSizeAfterConcurrentUsage(Set<Integer> setToTest) throws Exception {
		return getSizeAfterConcurrentUsage(setToTest, null);
	}

	private static int getSizeAfterConcurrentUsage(Map<Integer, Integer> mapToTest) throws Exception {
		return getSizeAfterConcurrentUsage(null, mapToTest);
	}


	@Test
	public void checkThatHashSetIsNotThreadSafe() throws Exception {
		Set<Integer> setToTest = new HashSet<>();

		int finalSize = getSizeAfterConcurrentUsage(setToTest);

		assertNotEquals(0, finalSize);
	}

	@Test
	public void checkThatSynchronizedSetIsThreadSafe() throws Exception {
		Set<Integer> setToTest = Collections.synchronizedSet(new HashSet<Integer>());

		int finalSize = getSizeAfterConcurrentUsage(setToTest);

		assertEquals(0, finalSize);
	}

	@Test
	public void checkThatHashMapIsNotThreadSafe() throws Exception {
		Map<Integer, Integer> mapToTest = new HashMap<>();

		int finalSize = getSizeAfterConcurrentUsage(mapToTest);

		assertNotEquals(0, finalSize);
	}

	@Test
	public void checkThatConcurrentHashMapIshreadSafe() throws Exception {
		Map<Integer, Integer> mapToTest = new ConcurrentHashMap<>();

		int finalSize = getSizeAfterConcurrentUsage(mapToTest);

		assertEquals(0, finalSize);
	}

}
