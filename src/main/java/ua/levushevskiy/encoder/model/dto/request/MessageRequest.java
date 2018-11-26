package ua.levushevskiy.encoder.model.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageRequest implements Serializable {

    String message;

}
