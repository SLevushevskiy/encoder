package ua.levushevskiy.encoder.model.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class DecryptResponse implements Serializable {

    private String message;

    private Integer closeKey1;

    private Integer closeKey2;
}
