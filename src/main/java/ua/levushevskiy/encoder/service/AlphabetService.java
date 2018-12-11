package ua.levushevskiy.encoder.service;

import org.springframework.stereotype.Service;

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
            result += (char)(indexOf + (Integer.parseInt(newKey.charAt(i) + "") * op))+"";
        }
        return result;
    }
}
