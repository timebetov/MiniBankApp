package com.github.timebetov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "ResponseDTO",
        description = "Schema to hold response information"
)
public class ResponseDTO {

    @Schema(description = "Status Code representing the handled case", example = "CREATED")
    private HttpStatus statusCode;

    @Schema(description = "Specified message representing handled case", example = "User Registered Successfully")
    private String message;
}
