package ua.levushevskiy.encoder.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MessageResponse implements Serializable {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String time;

    public MessageResponse(String message) {
        this.message = message;
    }
}
