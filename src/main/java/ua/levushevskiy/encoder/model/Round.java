package ua.levushevskiy.encoder.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Round {
    private List<Integer> indexes = new ArrayList<>();

    public void setIndexes(String index) {
        for (int i = index.length() - 1; i > -1; i--) {
            indexes.add(Integer.parseInt(index.charAt(i) + ""));
        }
    }
}
