package com.testing.pack.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayloadResponse {

    public int statusCode;
    public String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date timestamp;

    @Nullable
    public Object payload;

}
