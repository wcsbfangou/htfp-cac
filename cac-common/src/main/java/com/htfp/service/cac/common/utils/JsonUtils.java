package com.htfp.service.cac.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 19:52
 * @Description Jackson序列化工具类
 */
@Slf4j
public class JsonUtils {
    private static ObjectMapper mapper = null;
    private static ObjectMapper mapperWithoutNull = null;

    private JsonUtils() {
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return json2ObjectThrowException(json, clazz);
        } catch (Exception var3) {
            log.error("json2Object error, json = {}", json, var3);
            return null;
        }
    }

    public static <T> T json2Object(String json, TypeReference<T> typeRef) {
        if (typeRef == null) {
            throw new NullPointerException("typeReference is null");
        } else {
            Type type = typeRef.getType();

            try {
                return readValue(json, type);
            } catch (Exception var4) {
                log.error("json2Object error, json = {}", json, var4);
                return null;
            }
        }
    }

    public static <T> T json2ObjectThrowException(String json, Class<T> clazz) throws Exception {
        try {
            return readValue(json, clazz);
        } catch (Exception var3) {
            log.error("json2ObjectThrowException error, json = {}", json, var3);
            throw var3;
        }
    }

    public static <K, V> Map<K, V> json2Map(String json) {
        return StringUtils.isBlank(json) ? null : (Map) json2Object(json, new TypeReference<Map<K, V>>() {
        });
    }

    public static <T> List<T> json2List(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                List<T> object = (List) objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
                return object;
            } catch (Exception var4) {
                log.warn("json2Object error, json = " + json, var4);
                return null;
            }
        }
    }

    public static String object2Json(Object object) {
        return object2Json(object, true);
    }

    public static String object2Json(Object object, boolean containNull) {
        if (object == null) {
            return "";
        } else {
            try {
                return getInstance(containNull).writeValueAsString(object);
            } catch (Exception var3) {
                log.error("object2Json error:{}", var3.getMessage(), var3);
                return "";
            }
        }
    }

    public static Map json2Map(String json, boolean containNull) {
        return json2Map(json);
    }

    private static ObjectMapper getInstance() {
        return getInstance(true);
    }

    private static ObjectMapper getInstance(boolean containsNull) {
        ObjectMapper m = containsNull ? mapper : mapperWithoutNull;
        if (m != null) {
            return m;
        } else {
            Class var2 = JsonUtils.class;
            synchronized (JsonUtils.class) {
                if (m != null) {
                    return m;
                } else {
                    m = getCommonMapper();
                    if (!containsNull) {
                        m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    }

                    return m;
                }
            }
        }
    }

    private static ObjectMapper getCommonMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    private static <T> T readValue(String json, Type type) throws Exception {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            ObjectMapper mapper = getInstance();
            TypeFactory typeFactory = mapper.getTypeFactory();
            JavaType javaType = typeFactory.constructType(type);
            return mapper.readValue(json, javaType);
        }
    }
}
