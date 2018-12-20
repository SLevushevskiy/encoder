package ua.levushevskiy.encoder.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlphabetService {

    public String crypt(String key, String message, Boolean encode) {
        String result = "";
        int op = encode ? +1 : -1, offset, indexOf = 0;
        String newKey = key;
        while (newKey.length() < message.length()) {
            newKey += (key);
        }
        if (newKey.length() > message.length()) {
            newKey = newKey.substring(0, message.length());
        }

        for (int i = 0; i < message.length(); i++) {
            indexOf = message.charAt(i);
            result += (char) (indexOf + (Integer.parseInt(newKey.charAt(i) + "") * op)) + "";
        }
        return result;
    }

    public List<String> hack(String messageRequest) {
        List<String> res = new ArrayList<>();
        int[] mas = new int[messageRequest.length()];
        for (int i = mas.length - 1; mas[0] <= 9; i--) {
            if (i < 0) {
                i = mas.length;
            }
            String word = "";
            for (int j = 0; j < messageRequest.length(); j++) {
                int inx = alf.indexOf(messageRequest.charAt(j)) - mas[j];
                if (inx < 0)
                    inx += (alf.length() - 1);
                word += alf.charAt(inx);
            }

            for (int z = mas.length - 1; z >= 0; z--) {
                if (mas[z] == 9)
                    mas[z] = 0;
                else {
                    mas[z]++;
                    break;
                }
            }

            res.add(word);


        }
        return res;

    }


    private String alf = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя ";
    //private String alf = "абвгґдеєжзиіїйклмнопрстуфхцчшщьюя ";
}
