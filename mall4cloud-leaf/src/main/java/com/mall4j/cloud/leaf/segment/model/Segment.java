package com.mall4j.cloud.leaf.segment.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author left
 */
public class Segment {

	private AtomicLong value = new AtomicLong(0);

	private volatile long max;

	private volatile int step;

	private volatile int randomStep;

	private final SegmentBuffer buffer;

	public Segment(SegmentBuffer buffer) {
		this.buffer = buffer;
	}

	public AtomicLong getValue() {
		return value;
	}

	public void setValue(AtomicLong value) {
		this.value = value;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public SegmentBuffer getBuffer() {
		return buffer;
	}

	public long getIdle() {
		return this.getMax() - getValue().get();
	}

	@Override
	public String toString() {
		return "Segment(" + "value:" +
				value +
				",max:" +
				max +
				",step:" +
				step +
				")";
	}

	public int getRandomStep() {
		return randomStep;
	}

	public void setRandomStep(int randomStep) {
		this.randomStep = randomStep;
	}

}
