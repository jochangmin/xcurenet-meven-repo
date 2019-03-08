package com.xcurenet.util.prs;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xcurenet.util.common.PropertyUtil;
import com.xcurenet.util.common.Util;

public class PrsUtil {

	private static final Logger log = LoggerFactory.getLogger(PrsUtil.class);

	private static final String MASTER_CONF_PATH = "/users/prs/conf/config.conf";
	private static final String MASTER_CONF_KEY = "HA";
	private static final String[] MASTER_CONF_VAL = {"0", "1"}; //# HA 0=STANDALONE, 1=ACTIVE, 2=STANDBY

	private PrsUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean isMaster() {
		PropertyUtil property = null;
		try {
			property = new PropertyUtil(MASTER_CONF_PATH);
			String ha = property.getProperty(MASTER_CONF_KEY);
			return Util.isOrEquals(ha, MASTER_CONF_VAL);
		} catch (IOException e) {
			log.error("{}", e);
		} finally {
			if (property != null) property.clear();
		}
		return false;
	}
}
