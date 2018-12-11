package ua.levushevskiy.encoder.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorModel implements Serializable {

    Integer index;

    List<String> fixedBlock = new ArrayList<>();



    public void buildFixedBlock(String tmp, int len) {
        char control = len == 3 ? '0' : '1';
        char neg = len == 1 ? '0' : '1';
        StringBuilder stringBuilder;
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp.charAt(i) == control) {
                stringBuilder = new StringBuilder(tmp);
                stringBuilder.setCharAt(i, neg);
                fixedBlock.add(stringBuilder.toString());
            } else {
                stringBuilder = new StringBuilder(tmp);
                stringBuilder.setCharAt(i, control);
                fixedBlock.add(stringBuilder.toString());
            }
        }
    }
}
