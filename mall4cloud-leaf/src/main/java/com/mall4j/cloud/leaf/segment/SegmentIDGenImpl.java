package com.mall4j.cloud.leaf.segment;

import com.mall4j.cloud.leaf.IDGen;
import com.mall4j.cloud.leaf.common.Result;
import com.mall4j.cloud.leaf.common.Status;
import com.mall4j.cloud.leaf.segment.dao.IDAllocDao;
import com.mall4j.cloud.leaf.segment.model.LeafAlloc;
import com.mall4j.cloud.leaf.segment.model.Segment;
import com.mall4j.cloud.leaf.segment.model.SegmentBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author leaf
 */
public class SegmentIDGenImpl implements IDGen {

	private static final Logger logger = LoggerFactory.getLogger(SegmentIDGenImpl.class);

	/**
	 * IDCache未初始化成功时的异常码
	 */
	private static final long EXCEPTION_ID_IDCACHE_INIT_FALSE = -1;

	/**
	 * key不存在时的异常码
	 */
	private static final long EXCEPTION_ID_KEY_NOT_EXISTS = -2;

	/**
	 * SegmentBuffer中的两个Segment均未从DB中装载时的异常码
	 */
	private static final long EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL = -3;

	/**
	 * 最大步长不超过100,0000
	 */
	private static final int MAX_STEP = 1000000;

	/**
	 * 一个Segment维持时间为15分钟
	 */
	private static final long SEGMENT_DURATION = 15 * 60 * 1000L;

	private final ExecutorService service = new ThreadPoolExecutor(5, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
			new SynchronousQueue<>(), new UpdateThreadFactory());

	private volatile boolean initOk = false;

	private final Map<String, SegmentBuffer> cache = new ConcurrentHashMap<>();

	private IDAllocDao dao;

	private static final SecureRandom RANDOM = new SecureRandom();

	private static final int DEFAULT_LOAD_FACTOR = 2;

	public static class UpdateThreadFactory implements ThreadFactory {

		private static int threadInitNumber = 0;

		private static synchronized int nextThreadNum() {
			return threadInitNumber++;
		}

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "Thread-Segment-Update-" + nextThreadNum());
		}

	}

	@Override
	public boolean init() {
		logger.info("Init ...");
		// 确保加载到kv后才初始化成功
		updateCacheFromDb();
		initOk = true;
		updateCacheFromDbAtEveryMinute();
		return initOk;
	}

	private void updateCacheFromDbAtEveryMinute() {
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName("check-idCache-thread");
				t.setDaemon(true);
				return t;
			}
		});
		service.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				updateCacheFromDb();
			}
		}, 60, 60, TimeUnit.SECONDS);
	}

	private void updateCacheFromDb() {
		logger.info("update cache from db");
		try {
			List<String> dbTags = dao.getAllTags();
			if (dbTags == null || dbTags.isEmpty()) {
				return;
			}
			List<String> cacheTags = new ArrayList<String>(cache.keySet());
			Set<String> insertTagsSet = new HashSet<>(dbTags);
			Set<String> removeTagsSet = new HashSet<>(cacheTags);
			// db中新加的tags灌进cache
			for (int i = 0; i < cacheTags.size(); i++) {
				String tmp = cacheTags.get(i);
				insertTagsSet.remove(tmp);
			}
			for (String tag : insertTagsSet) {
				SegmentBuffer buffer = new SegmentBuffer();
				buffer.setKey(tag);
				Segment segment = buffer.getCurrent();
				segment.setValue(new AtomicLong(0));
				segment.setMax(0);
				segment.setStep(0);
				cache.put(tag, buffer);
				logger.info("Add tag {} from db to IdCache, SegmentBuffer {}", tag, buffer);
			}
			// cache中已失效的tags从cache删除
			for (int i = 0; i < dbTags.size(); i++) {
				String tmp = dbTags.get(i);
				removeTagsSet.remove(tmp);
			}
			for (String tag : removeTagsSet) {
				cache.remove(tag);
				logger.info("Remove tag {} from IdCache", tag);
			}
		}
		catch (Exception e) {
			logger.warn("update cache from db exception", e);
		}
	}

	@Override
	public Result get(final String key) {
		if (!initOk) {
			return new Result(EXCEPTION_ID_IDCACHE_INIT_FALSE, Status.EXCEPTION);
		}
		SegmentBuffer buffer = cache.get(key);
		if (buffer != null) {
			if (buffer.isInitOk()) {
				synchronized (buffer) {
					if (buffer.isInitOk()) {
						try {
							updateSegmentFromDb(key, buffer.getCurrent());
							logger.info("Init buffer. Update leafkey {} {} from db", key, buffer.getCurrent());
							buffer.setInitOk(true);
						}
						catch (Exception e) {
							logger.warn("Init buffer {} exception", buffer.getCurrent(), e);
						}
					}
				}
			}
			return getIdFromSegmentBuffer(cache.get(key));
		}
		return new Result(EXCEPTION_ID_KEY_NOT_EXISTS, Status.EXCEPTION);
	}

	public void updateSegmentFromDb(String key, Segment segment) {
		SegmentBuffer buffer = segment.getBuffer();
		LeafAlloc leafAlloc;
		if (buffer.isInitOk()) {
			leafAlloc = dao.updateMaxIdAndGetLeafAlloc(key);
			buffer.setStep(leafAlloc.getStep());
			// leafAlloc中的step为DB中的step
			buffer.setMinStep(leafAlloc.getStep());
		}
		else if (buffer.getUpdateTimestamp() == 0) {
			leafAlloc = dao.updateMaxIdAndGetLeafAlloc(key);
			buffer.setUpdateTimestamp(System.currentTimeMillis());
			buffer.setStep(leafAlloc.getStep());
			// leafAlloc中的step为DB中的step
			buffer.setMinStep(leafAlloc.getStep());
		}
		else {
			long duration = System.currentTimeMillis() - buffer.getUpdateTimestamp();
			int nextStep = buffer.getStep();
			if (duration < SEGMENT_DURATION) {
				if (nextStep * DEFAULT_LOAD_FACTOR > MAX_STEP) {
					// do nothing
				}
				else {
					nextStep = nextStep * DEFAULT_LOAD_FACTOR;
				}
			}
			else if (duration < SEGMENT_DURATION * DEFAULT_LOAD_FACTOR) {
				// do nothing with nextStep
			}
			else {
				nextStep = nextStep / DEFAULT_LOAD_FACTOR >= buffer.getMinStep() ? nextStep / DEFAULT_LOAD_FACTOR
						: nextStep;
			}
			logger.info("leafKey[{}], step[{}], duration[{}mins], nextStep[{}]", key, buffer.getStep(),
					String.format("%.2f", ((double) duration / (1000 * 60))), nextStep);
			LeafAlloc temp = new LeafAlloc();
			temp.setKey(key);
			temp.setStep(nextStep);
			leafAlloc = dao.updateMaxIdByCustomStepAndGetLeafAlloc(temp);
			buffer.setUpdateTimestamp(System.currentTimeMillis());
			buffer.setStep(nextStep);
			// leafAlloc的step为DB中的step
			buffer.setMinStep(leafAlloc.getStep());
		}
		// must set value before set max
		long value = leafAlloc.getMaxId() - buffer.getStep();
		segment.getValue().set(value);
		segment.setMax(leafAlloc.getMaxId());
		segment.setStep(buffer.getStep());
		segment.setRandomStep(leafAlloc.getRandomStep());
	}

	public Result getIdFromSegmentBuffer(final SegmentBuffer buffer) {
		while (true) {
			buffer.rLock().lock();
			try {
				final Segment segment = buffer.getCurrent();
				if (!buffer.isNextReady() && (segment.getIdle() < 0.9 * segment.getStep())
						&& buffer.getThreadRunning().compareAndSet(false, true)) {
					service.execute(new Runnable() {
						@Override
						public void run() {
							Segment next = buffer.getSegments()[buffer.nextPos()];
							boolean updateOk = false;
							try {
								updateSegmentFromDb(buffer.getKey(), next);
								updateOk = true;
								logger.info("update segment {} from db {}", buffer.getKey(), next);
							}
							catch (Exception e) {
								logger.warn(buffer.getKey() + " updateSegmentFromDb exception", e);
							}
							finally {
								if (updateOk) {
									buffer.wLock().lock();
									buffer.setNextReady(true);
									buffer.getThreadRunning().set(false);
									buffer.wLock().unlock();
								}
								else {
									buffer.getThreadRunning().set(false);
								}
							}
						}
					});
				}
				long value;
				if (segment.getRandomStep() > 1) {

					// 随机从1-10里面增加
					value = segment.getValue().getAndAdd(randomAdd(segment.getRandomStep()));
				}
				else {
					value = segment.getValue().getAndIncrement();
				}

				if (value < segment.getMax()) {
					return new Result(value, Status.SUCCESS);
				}
			}
			finally {
				buffer.rLock().unlock();
			}
			waitAndSleep(buffer);
			buffer.wLock().lock();
			try {
				final Segment segment = buffer.getCurrent();
				long value = segment.getValue().getAndIncrement();
				if (value < segment.getMax()) {
					return new Result(value, Status.SUCCESS);
				}
				if (buffer.isNextReady()) {
					buffer.switchPos();
					buffer.setNextReady(false);
				}
				else {
					logger.error("Both two segments in {} are not ready!", buffer);
					return new Result(EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL, Status.EXCEPTION);
				}
			}
			finally {
				buffer.wLock().unlock();
			}
		}
	}

	private int randomAdd(int randomStep) {
		return RANDOM.nextInt(randomStep - 1) + 1;
	}

	private void waitAndSleep(SegmentBuffer buffer) {
		int roll = 0;
		while (buffer.getThreadRunning().get()) {
			roll += 1;
			if (roll > 10000) {
				try {
					TimeUnit.MILLISECONDS.sleep(10);
					break;
				}
				catch (InterruptedException e) {
					logger.warn("Thread {} Interrupted, Exception: {}", Thread.currentThread().getName(), e);
					break;
				}
			}
		}
	}

	public List<LeafAlloc> getAllLeafAllocs() {
		return dao.getAllLeafAllocs();
	}

	public Map<String, SegmentBuffer> getCache() {
		return cache;
	}

	public IDAllocDao getDao() {
		return dao;
	}

	public void setDao(IDAllocDao dao) {
		this.dao = dao;
	}

}
