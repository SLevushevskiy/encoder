package ua.levushevskiy.encoder.service;

import org.springframework.stereotype.Service;

@Service
public class GammaService {
    private String alf = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя 0123456789";
    private int k, x, z;
    private String res;

    public String encode(String source, String key) {
        res = "";

        while (key.length() < source.length()) {
            key += key;
            if (key.length() > source.length()) key = key.substring(0, source.length());
        }
        for (int i = 0; i < source.length(); i++) {
            k = alf.indexOf(key.charAt(i));
            x = alf.indexOf(source.charAt(i));
            z = (x + k) % alf.length();
            res += alf.charAt(z);
        }
        return res;
    }

    public String decode(String source, String key) {
        res = "";

        while (key.length() < source.length()) {
            key += key;
            if (key.length() > source.length()) key = key.substring(0,
                    source.length());
        }
        for (int i = 0; i < source.length(); i++) {
            k = alf.indexOf(key.charAt(i));
            x = alf.indexOf(source.charAt(i));
            z = ((x - k) + alf.length()) % alf.length();
            res += alf.charAt(z);
        }
        return res;
    }
}
