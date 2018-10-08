package com.example.responsefilter;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonFilter("User")
public class User {
    private String id;
    private String name;
    private UserInfo userInfo;
}
