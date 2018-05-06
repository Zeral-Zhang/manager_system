package com.zeral.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BadRequestAlertException extends AbstractThrowableProblem {

    private final String entityName;

    private final String errorKey;

    private final String errorMessage;

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.BAD_REQUEST, null, null, null, getAlertParameters(entityName, defaultMessage));
        this.entityName = entityName;
        this.errorKey = errorKey;
        this.errorMessage = defaultMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorMessage) {
        Map<String, Object> parameters = new HashMap<>();
        try {
            parameters.put("message", URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            parameters.put("message", errorMessage);
        }
        parameters.put("params", entityName);
        return parameters;
    }
}
