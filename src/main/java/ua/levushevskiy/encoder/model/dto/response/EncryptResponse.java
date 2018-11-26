package ua.levushevskiy.encoder.model.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class EncryptResponse implements Serializable {

    private String message;

    private Integer openKey1;

    private Integer openKey2;

}
