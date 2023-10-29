package app.web.zyncky.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String path;
    private String mesaage;
    private int statusCode;
    LocalDateTime timeStamp;
}
