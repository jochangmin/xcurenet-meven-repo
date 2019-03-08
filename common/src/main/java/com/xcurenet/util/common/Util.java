package com.xcurenet.util.common;

import java.io.ByteArrayOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

	private static final Logger log = LoggerFactory.getLogger(Util.class);

	public static final String UTF8 = "UTF-8";

	public static final String EUCKR = "EUC-KR";

	public static final String EMPTY = "";

	private Util() {
		throw new IllegalStateException("Utility class");
	}

	public static void main(String[] args) {
		CommandLine cl = CommandLine.parse("ipconfig");
		cl.addArgument("/all");
		String re = execAndRtnResult(cl);

		log.info("result : {}", re);
	}

	public static String execAndRtnResult(CommandLine command) {
		return execAndRtnResult(command, EUCKR);
	}

	public static String execAndRtnResult(CommandLine command, String charset) {
		DefaultExecutor executor = new DefaultExecutor();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(baos);
		executor.setStreamHandler(streamHandler);
		try {
			executor.execute(new CommandLine(command));
			return new String(baos.toByteArray(), charset);
		} catch (Exception e) {
			log.error("error:", e);
		} finally {
			IOUtils.closeQuietly(baos);
		}
		return null;
	}

	public static boolean parseBoolean(String value) {
		if ((StringUtils.isNotEmpty(value)) && (("Y".equalsIgnoreCase(value)) || ("T".equalsIgnoreCase(value)) || ("1".equals(value)) || ("YES".equalsIgnoreCase(value)) || ("TRUE".equalsIgnoreCase(value)))) {
			return true;
		}
		return false;
	}

	public static boolean isEquals(Object source, Object target) {
		return ((source == null) ? false : (target == null) ? true : source.equals(target));
	}

	/**
	 * @param target
	 * @return
	 */
	public static boolean isNotEquals(Object source, Object target) {
		return ((source == null) ? true : (target == null) ? false : !source.equals(target));
	}

	/**
	 * Java String isEmpty This Java String isEmpty shows how to check whether
	 * the given string is empty or not using isEmpty method of Java String
	 * class.
	 *
	 * @param target
	 * @return
	 */
	public static boolean isEmpty(Object target) {
		return nvl(target).isEmpty();
	}

	/**
	 * Java String isEmpty This Java String isEmpty shows how to check whether
	 * the given string is empty or not using isEmpty method of Java String
	 * class.
	 *
	 * @param target
	 * @return
	 */
	public static boolean isNotEmpty(Object target) {
		return !isEmpty(target);
	}

	/**
	 * Null to Empty String
	 *
	 * @param target
	 * @return
	 */
	public static String nvl(Object target) {
		return nvl(target, EMPTY);
	}

	/**
	 * Null to Empty String
	 *
	 * @param target
	 * @return
	 */
	public static String nvl(Object target, String defaultStr) {
		if (target != null && !String.valueOf(target).equalsIgnoreCase("null")) return String.valueOf(target);
		return defaultStr;
	}

	public static boolean isOrEquals(Object source, String[] target) {
		if (source == null) return false;
		boolean result = false;
		for (int i = 0; i < target.length; i++) {
			result = source.equals(nvl(target[i]));
			if (result) break;
		}
		return result;
	}

	/**
	 * @param target
	 * @return
	 */
	public static boolean isOrEquals(Object source, Object... target) {
		if (source == null) return false;
		boolean result = false;
		for (int i = 0; i < target.length; i++) {
			result = source.equals(nvl(target[i]));
			if (result) break;
		}
		return result;
	}

	public static boolean isWindow() {
		String os_name = System.getProperty("os.name").toLowerCase();
		if (os_name.startsWith("windows")) return true;
		else return false;
	}
}
