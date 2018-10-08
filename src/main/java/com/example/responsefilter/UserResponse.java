package com.example.responsefilter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class UserResponse {
    private Integer totalResults;
    private Collection<User> results;
}
