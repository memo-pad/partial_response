package com.example.responsefilter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ResponseFilterController {

    private final Map<String, User> users = new HashMap<String, User>() {
        {
            put("1", new User("1", "user1", new UserInfo("xxx-xxxx-1111", "XXXXXXXXXXXXX1")));
            put("2", new User("2", "user2", new UserInfo("xxx-xxxx-2222", "XXXXXXXXXXXXX2")));
            put("3", new User("3", "user3", new UserInfo("xxx-xxxx-3333", "XXXXXXXXXXXXX3")));

        }
    };

    @GetMapping("/user/{user_id}")
    MappingJacksonValue getSingleUser(
            @PathVariable("user_id") String userId,
            @RequestParam(value = "fields", defaultValue = "name", required = false) String fieldParam
    ) {
        User user = users.get(userId);

        return createMappingJacksonValue(fieldParam, user);
    }

    @GetMapping("/user")
    MappingJacksonValue getUser(
            @RequestParam(value = "fields", defaultValue = "name", required = false) String fieldParam
    ) {
        UserResponse userResponse = new UserResponse(users.size(), users.values());

        return createMappingJacksonValue(fieldParam, userResponse);
    }

    private MappingJacksonValue createMappingJacksonValue(String fieldParam, Object value) {
        String[] fields = fieldParam.split(",", 0);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(value);
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("User", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }
}
