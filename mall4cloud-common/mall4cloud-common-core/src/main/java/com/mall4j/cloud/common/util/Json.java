package com.mall4j.cloud.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/7/11
 */
public class Json {

	private static final Logger logger = LoggerFactory.getLogger(Json.class);

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		// 如果为空则不输出
		OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		// 对于空的对象转json的时候不抛出错误
		OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		// 禁用序列化日期为timestamps
		OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// 禁用遇到未知属性抛出异常
		OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	/**
	 * 对象转json
	 * @param object 对象
	 * @return json
	 */
	public static String toJsonString(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			logger.error("toJsonString() error: {}", e.getMessage());
		}
		return "";
	}

	/**
	 * json转换换成对象
	 * @param json json
	 * @param clazz clazz
	 * @return Class
	 */
	public static <T> T parseObject(String json, Class<T> clazz) {
		if (json == null) {
			return null;
		}
		T result = null;
		try {
			result = OBJECT_MAPPER.readValue(json, clazz);
		}
		catch (Exception e) {
			logger.error("parseObject() error: {}", e.getMessage());
		}
		return result;
	}

	/**
	 * json转换换成对象
	 * @param src src
	 * @param clazz clazz
	 * @return Class
	 */
	public static <T> T parseObject(byte[] src, Class<T> clazz) {
		T result = null;
		try {
			result = OBJECT_MAPPER.readValue(src, clazz);
		}
		catch (Exception e) {
			logger.error("parseObject() error: {}", e.getMessage());
		}
		return result;
	}

	public static ObjectMapper getObjectMapper() {
		return OBJECT_MAPPER;
	}

	/**
	 * *
	 * https://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects
	 * * List<MyClass> myObjects = Arrays.asList(mapper.readValue(json, MyClass[].class))
	 * * works up to 10 time faster than TypeRefence.
	 * @return List数组
	 */
	public static <T> List<T> parseArray(String json, Class<T[]> clazz) {
		if (json == null) {
			return null;
		}
		T[] result = null;
		try {
			result = OBJECT_MAPPER.readValue(json, clazz);
		}
		catch (Exception e) {
			logger.error("parseArray() error: {}", e.getMessage());
		}
		if (result == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(result);
	}

	public static <T> List<T> parseArray(byte[] src, Class<T[]> clazz) {
		T[] result = null;
		try {
			result = OBJECT_MAPPER.readValue(src, clazz);
		}
		catch (Exception e) {
			logger.error("parseArray() error: {}", e.getMessage());
		}
		if (result == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(result);
	}


	/**
	 * 转换成json节点，即map
	 * @param jsonStr jsonStr
	 * @return JsonNode
	 */
	public static JsonNode parseJson(String jsonStr) {
		if (jsonStr == null) {
			return null;
		}
		JsonNode jsonNode = null;
		try {
			jsonNode = OBJECT_MAPPER.readTree(jsonStr);
		}
		catch (Exception e) {
			logger.error("parseJson() error: {}", e.getMessage());
		}
		return jsonNode;
	}

}
