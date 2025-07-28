package org.example.generics;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApiResponse<T> {
    String message;
    boolean success;
    T data;
}
