package ua.levushevskiy.encoder.model.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class DesResponse implements Serializable {

    Map<String, String> rounds = new LinkedHashMap<>();

    String message;

    String key;

    String cipher;

}
