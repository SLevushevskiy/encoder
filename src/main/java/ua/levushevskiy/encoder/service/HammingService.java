package ua.levushevskiy.encoder.service;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Service
public class HammingService {

    public String encodeMessage(String message) {
        message = stringToBinary(message);
        return encode(message);
    }

    private String stringToBinary(String string) {
        byte[] bytes = string.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    private String encode(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int pow = 0; Math.pow(2, pow) <= stringBuilder.length(); pow++) {
            stringBuilder.insert((int) Math.pow(2, pow) - 1, 0);
        }
        char[] chars = stringBuilder.toString().toCharArray();
        List<Integer> vectorSum = buildVectorSum(chars);
        for (int i = 0; i < vectorSum.size(); i++) {
            if (vectorSum.get(i) == 1) {
                chars[(int) Math.pow(2, i) - 1] = '1';
            }
        }
        String res = new String(chars);
        return res;
    }

    private char[] decodeByVectorSum(char[] chars) {
        List<Integer> vectorSum = buildVectorSum(chars);
        String index = "";
        for (int i = vectorSum.size()-1; i >=0 ; i--) {
            index += vectorSum.get(i);
        }
        int indx = Integer.parseInt(index, 2);
        if (indx > 0) {
            indx--;
            chars[indx] = chars[indx] == '0' ? '1' : '0';
        }
        return chars;
    }

    private List<Integer> buildVectorSum(char[] chars) {
        List<Integer> vectorSum = new ArrayList();
        for (int pow = 0; Math.pow(2, pow) <= chars.length; pow++) {
            int powRes = (int) Math.pow(2, pow);
            int sum = 0;
            for (int i = powRes - 1, key = 0; i < chars.length; i++, key++) {
                if (key == powRes) {
                    i += powRes;
                    key = 0;
                }
                if (i >= chars.length)
                    break;
                sum += chars[i];
            }
            vectorSum.add(sum % 2);
        }
        return vectorSum;
    }

    public String decodeMessage(String message) {
        message = decode(message);
        return binaryToString(message);
    }

    private String decode(String message) {
        StringBuilder stringBuilder = new StringBuilder(message);
        char[] chars = stringBuilder.toString().toCharArray();
        chars = decodeByVectorSum(chars);
        stringBuilder = new StringBuilder(new String(chars));
        int val = message.length();
        int pow;
        for (pow = 0; val > 0; pow++) {
            val >>= 1;
        }
        int vectorSum[] = new int[pow];
        for (int i = vectorSum.length - 1; i >= 0; i--) {
            stringBuilder.deleteCharAt((int) Math.pow(2, i) - 1);
        }
        return stringBuilder.toString();
    }

    private String binaryToString(String message) {
        StringBuilder binary = new StringBuilder();
        StringBuilder result = new StringBuilder();


        for (int i = 0; i < message.length(); i++) {
            if (i % 8 == 0 && i != 0) {
                result.append((char) Integer.parseInt(binary.toString(), 2));
                binary = new StringBuilder();
            }
            binary.append(message.charAt(i));
        }
        result.append((char) Integer.parseInt(binary.toString(), 2));
        return result.toString();
    }
}
