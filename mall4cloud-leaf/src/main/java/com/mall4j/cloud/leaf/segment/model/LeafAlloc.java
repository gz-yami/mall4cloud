package com.mall4j.cloud.leaf.segment.model;

/**
 * @author leaf
 */
public class LeafAlloc {

	private String key;

	private long maxId;

	private int step;

	private String updateTime;

	private int randomStep;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getMaxId() {
		return maxId;
	}

	public void setMaxId(long maxId) {
		this.maxId = maxId;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getRandomStep() {
		return randomStep;
	}

	public void setRandomStep(int randomStep) {
		this.randomStep = randomStep;
	}

}
