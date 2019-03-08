package com.xcurenet.util.common;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class PropertyUtil extends Properties {

	private static final long serialVersionUID = -168176975299247400L;

	public PropertyUtil() {
	}

	public PropertyUtil(String path) throws IOException {
		this();
		if (path != null) loadResource(path);
	}

	public PropertyUtil(Properties properties) {
		super.putAll(properties);
	}

	public void loadResource(String path) throws IOException {
		if (path.charAt(0) != '/') {
			InputStream in = null;
			try {
				in = ClassLoader.getSystemResourceAsStream(path);
				load(in);
			} finally {
				IOUtils.closeQuietly(in);
			}
		}
		Reader reader = null;
		try {
			reader = new FileReader(path);
			load(reader);
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

	public void addDefaultProperty(Properties properties) {
		Properties clone = (Properties) clone();
		putAll(properties);
		putAll(clone);
	}

	@Override
	public String getProperty(String key) {
		return StringUtils.trim(super.getProperty(key));
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		return StringUtils.trim(super.getProperty(key, defaultValue));
	}

	public boolean getPropertyBoolean(String key, boolean defaultValue) {
		String value = getProperty(key);
		if (Util.parseBoolean(value)) {
			return true;
		}
		return defaultValue;
	}

	public int getPropertyInt(String key, int defaultValue) {
		String value = getProperty(key);
		return StringUtils.isEmpty(value) ? defaultValue : Integer.parseInt(value);
	}

	public long getPropertyLong(String key, long defaultValue) {
		String value = getProperty(key);
		return StringUtils.isEmpty(value) ? defaultValue : Long.parseLong(value);
	}
}
