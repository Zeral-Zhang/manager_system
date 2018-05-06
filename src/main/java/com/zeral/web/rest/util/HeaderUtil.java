package com.zeral.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private static final String APPLICATION_NAME = "managerApp";

    private HeaderUtil() {
    }

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("X-managerApp-alert", URLEncoder.encode(message, StandardCharsets.UTF_8.toString()));
            headers.add("X-managerApp-params", URLEncoder.encode(param, StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            headers.add("X-managerApp-alert", message);
            headers.add("X-managerApp-params", param);
        }
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".deleted", param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("X-managerApp-error", URLEncoder.encode(defaultMessage, StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            headers.add("X-managerApp-error", defaultMessage);
        }
        headers.add("X-managerApp-params", entityName);
        headers.add("X-managerApp-errorKey", errorKey);
        return headers;
    }
}
