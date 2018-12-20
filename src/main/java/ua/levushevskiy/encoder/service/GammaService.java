package ua.levushevskiy.encoder.service;

import org.springframework.stereotype.Service;

@Service
public class GammaService {
    private String alf = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя 0123456789";
    private int k, x, z;
    private String res;

    private Long a = 1664525L, b = 1013904223L, m = 32L;

    public String encode(String source, String key) {
        if (key == null) {
            key = generateGamma(10, 32L).trim();
        }
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
        if (key == null) {
            key = generateGamma(10, 32L).trim();
        }
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

    public String generateGamma(Integer k, Long x0) {
        StringBuilder res = new StringBuilder();
        res.append(x0 + " ");
        m = (long) Math.pow(2, m);
        for (int i = 0; i < k; i++) {
            x0 = (a * x0 + b) % m;
            res.append(x0 + " ");
        }
        return res.toString();
    }

    public String period(Long x0) {
        StringBuilder res = new StringBuilder();
        res.append(x0 + " ");
        m = (long) Math.pow(2, m);
        Boolean flag = true;
        for (int i = 0; flag; i++) {
            x0 = (a * x0 + b) % m;
            if(res.toString().contains(x0 + "")){
                return i+" period";
            }
            res.append(x0 + " ");
        }
        return res.toString();
    }

}
