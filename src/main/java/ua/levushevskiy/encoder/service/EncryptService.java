package ua.levushevskiy.encoder.service;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.levushevskiy.encoder.model.RsaModel;
import ua.levushevskiy.encoder.model.dto.request.MessageRequest;
import ua.levushevskiy.encoder.model.dto.response.DecryptResponse;
import ua.levushevskiy.encoder.model.dto.response.EncryptResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EncryptService {

    private final RsaModel rsaModel = new RsaModel();

    public EncryptResponse encode(MessageRequest messageRequest) {
        EncryptResponse encryptResponse = new EncryptResponse();
        encryptResponse.setOpenKey1(rsaModel.getMultNumber());
        encryptResponse.setOpenKey2(rsaModel.getOpenExp());
        char[] message = messageRequest.getMessage().toCharArray();
        encryptResponse.setMessage(build(message, rsaModel.getOpenExp(), rsaModel.getMultNumber()));
        return encryptResponse;
    }

    public DecryptResponse decode(MessageRequest messageRequest) {
        DecryptResponse decryptResponse = new DecryptResponse();
        decryptResponse.setCloseKey1(rsaModel.getMultNumber());
        decryptResponse.setCloseKey2(rsaModel.getCloseKey());
        String[] message = messageRequest.getMessage().split(" ");
        List<Integer> chars = new ArrayList<>();
        for (String mes : message) {
            chars.add(Integer.parseInt(mes));
        }
        decryptResponse.setMessage(decode(chars, rsaModel.getCloseKey(), rsaModel.getMultNumber()));
        return decryptResponse;
    }

    private String build(char[] message, int exp, int mod) {
        StringBuilder result = new StringBuilder();
        for (char c : message) {
            BigDecimal num = new BigDecimal(c);
            num = num.pow(exp);
            num = num.remainder(new BigDecimal(mod));
            result.append(num + " ");
        }
        return result.toString();
    }

    private String decode(List<Integer> message, int exp, int mod) {
        StringBuilder result = new StringBuilder();
        for (Integer c : message) {
            BigDecimal num = new BigDecimal(c.intValue());
            num = num.pow(exp);
            num = num.remainder(new BigDecimal(mod));
            result.append((char)num.intValue());
        }
        return result.toString();
    }
}
