package com.htfp.service.cac.app.controller.test;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author sunjipeng
 * @Date 2022-05-17 19:08
 */
@RestController
public class MonitorController {

    private static final Map<String, String> RESULT = ImmutableMap.of("status", "alive");

    @GetMapping("/monitor/alive")
    public Map<String, String> monitorAlive() {
        return RESULT;
    }
}
