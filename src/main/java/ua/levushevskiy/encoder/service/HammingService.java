package ua.levushevskiy.encoder.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ua.levushevskiy.encoder.model.dto.response.MessageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

@Service
public class HammingService {

    private final Integer BLOCK_SIZE = 11;
    private final Integer ENC_BLOCK_SIZE = 15;

    public String encodeMessage(String message) {
        message = stringToBinary(message);
        List<String> blocks = new ArrayList<>();
        int i;
        for (i = 0; i <= message.length() - BLOCK_SIZE; i += BLOCK_SIZE) {
            blocks.add(message.substring(i, i + BLOCK_SIZE));
        }
        if (i < message.length()) {
            blocks.add(message.substring(i));
        }
        StringBuilder result = new StringBuilder();
        blocks.forEach(block -> result.append(encode(block)));
        return result.toString();
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
        for (int i = vectorSum.size() - 1; i >= 0; i--) {
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
        int i;
        StringBuilder result = new StringBuilder();
        for (i = 0; i <= message.length() - ENC_BLOCK_SIZE; i += ENC_BLOCK_SIZE) {
            result.append(decode(message.substring(i, i + ENC_BLOCK_SIZE)));
        }
        if (i < message.length()) {
            result.append(decode(message.substring(i)));
        }
        return binaryToString(result.toString());
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

    public List<MessageResponse> test(String messageRequest, List<Integer> percents) {
        List<MessageResponse> messageResponses = new ArrayList<>();
        for (Integer percent : percents) {
            MessageResponse messageResponse;
            String message = encodeMessage(messageRequest);
            String errorMessage = buildError(message, percent);
            long startTime = System.nanoTime();
            String result = decodeMessage(errorMessage);
            long finish = System.nanoTime();
            messageResponse = new MessageResponse(result);
            messageResponse.setTime((finish - startTime) + " nanoTime");
            messageResponses.add(messageResponse);
        }
        return messageResponses;
    }

    private String buildError(String message, Integer percent) {
        StringBuilder stringBuilder = new StringBuilder(message);
        List<Integer> errorList = new ArrayList<>();

        int error = message.length() * percent / 100;
        while (error > 0) {
            Random random = new Random(System.currentTimeMillis());
            int index = random.nextInt(message.length());
            while (errorList.contains(index)) {
                index = random.nextInt(message.length());
            }
            errorList.add(index);
            char ch = message.charAt(index) == '0' ? '1' : '0';
            stringBuilder.setCharAt(index, ch);
            error--;
        }
        return stringBuilder.toString();
    }
}
