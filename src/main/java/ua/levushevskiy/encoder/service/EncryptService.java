package ua.levushevskiy.encoder.service;

import org.springframework.stereotype.Service;
import ua.levushevskiy.encoder.model.RsaModel;
import ua.levushevskiy.encoder.model.dto.request.MessageRequest;
import ua.levushevskiy.encoder.model.dto.response.DecryptResponse;
import ua.levushevskiy.encoder.model.dto.response.EncryptResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EncryptService {

    private final RsaModel rsaModel = new RsaModel();

    public EncryptResponse encode(MessageRequest messageRequest) {
        EncryptResponse encryptResponse = new EncryptResponse();
        encryptResponse.setOpenKey1(rsaModel.getMultNumber());
        encryptResponse.setOpenKey2(rsaModel.getOpenExp());
        char[] message = messageRequest.getMessage().toCharArray();
        encryptResponse.setMessage(encode(message, rsaModel.getOpenExp(), rsaModel.getMultNumber()));
        return encryptResponse;
    }

    public DecryptResponse decode(MessageRequest messageRequest) {
        DecryptResponse decryptResponse = new DecryptResponse();
        decryptResponse.setCloseKey1(rsaModel.getMultNumber());
        decryptResponse.setCloseKey2(rsaModel.getCloseKey());
        decryptResponse.setMessage(decode(messageRequest.getMessage(), rsaModel.getCloseKey(), rsaModel.getMultNumber()));
        return decryptResponse;
    }

    private String encode(char[] message, int exp, int mod) {
        StringBuilder result = new StringBuilder();
        for (char c : message) {
            BigDecimal num = new BigDecimal(c);
            num = num.pow(exp);
            num = num.remainder(new BigDecimal(mod));
            result.append(stringToBinary(num + ""));
        }
        return result.toString();
    }

    private String stringToBinary(String string) {
        StringBuilder binary = new StringBuilder();
        binary.append(Integer.toBinaryString(Integer.parseInt(string)));
        while (binary.length() < 12) {
            binary.insert(0, "0");
        }
        return binary.toString();
    }

    private List<Integer> binaryToString(String message) {
        List<Integer> result = new ArrayList<>();
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            if (i % 12 == 0 && i != 0) {
                result.add(Integer.parseInt(binary.toString(), 2));
                binary = new StringBuilder();
            }
            binary.append(message.charAt(i));
        }
        result.add(Integer.parseInt(binary.toString(), 2));
        return result;
    }

    private String decode(String message, int exp, int mod) {
        List<Integer> listNum = binaryToString(message);
        StringBuilder result = new StringBuilder();
        for (Integer c : listNum) {
            BigDecimal num = new BigDecimal(c.intValue());
            num = num.pow(exp);
            num = num.remainder(new BigDecimal(mod));
            result.append((char) num.intValue());
        }
        return result.toString();
    }
}
