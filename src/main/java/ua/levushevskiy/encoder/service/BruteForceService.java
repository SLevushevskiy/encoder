package ua.levushevskiy.encoder.service;

import org.springframework.stereotype.Service;
import ua.levushevskiy.encoder.model.ErrorModel;
import ua.levushevskiy.encoder.model.Round;
import ua.levushevskiy.encoder.model.dto.response.MessageResponse;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class BruteForceService {


    public String encodeMessage(String message) {
        StringBuilder controlCode = new StringBuilder();
        message = stringToByte(message);
        Long num = Long.parseLong(message, 2);
        controlCode.append(byteMask((int) (num % 11)));
        controlCode.append(byteMask((int) (num % 13)));
        controlCode.append(byteMask((int) (num % 14)));
        controlCode.append(byteMask((int) (num % 15)));
        controlCode.append(message);
        return controlCode.toString();
    }

    public String decodeMessage(String message) {
        Integer mod11, mod13, mod14, mod15;
        mod11 = Integer.parseInt(message.substring(0, 4), 2);
        mod13 = Integer.parseInt(message.substring(4, 8), 2);
        mod14 = Integer.parseInt(message.substring(8, 12), 2);
        mod15 = Integer.parseInt(message.substring(12, 16), 2);
        String mes = message.substring(16, message.length());
        List<String> blocks = new ArrayList<>();
        List<ErrorModel> errorModels = new ArrayList<>();

        for (int i = 0; i <= mes.length() - 4; i += 4) {
            String tmp = mes.substring(i, i + 4);
            blocks.add(tmp);
            ErrorModel errorModel = generateFixed(tmp, blocks.size());
            if (errorModel != null) {
                errorModels.add(errorModel);
            }
        }
        List<Round> rounds = new ArrayList<>();
        String roundCount = "";
        for (int i = 0; i < errorModels.size(); i++) {
            roundCount += "1";
        }
        if (!roundCount.equals("")) {
            int checkZero = roundCount.length() > 1 ? 1 : 0;
            int roundSize = (Integer.parseInt(roundCount, 2) + checkZero) * 4;
            for (int i = 0; i < roundSize; i++) {
                Round round = new Round();
                round.setIndexes(tetraMask(i, errorModels.size()));
                rounds.add(round);
            }
            for (int i = 0; i < roundSize; i++) {
                String fixedMes = fixed(blocks, errorModels, rounds.get(i));
                Long num = Long.parseLong(fixedMes, 2);
                if (mod11 == (int) (num % 11) && mod13 == (int) (num % 13) && mod14 == (int) (num % 14) && mod15 == (int) (num % 15)) {
                    return cryptToText(mes);
                }
            }
        } else {
            Long num = Long.parseLong(mes, 2);
            if (mod11 == (int) (num % 11) && mod13 == (int) (num % 13) && mod14 == (int) (num % 14) && mod15 == (int) (num % 15)) {
                return cryptToText(mes);
            }
        }
        return "Message has broken";
    }

    private String cryptToText(String message) {
        StringBuilder result = new StringBuilder();
        String tmp = "";
        for (int i = 0; i <= message.length() - 8; i += 8) {
            tmp = message.substring(i, i + 3);
            tmp = tmp + " " + message.substring(i + 4, i + 7);
            String value = tmp;
            result.append(alphabet.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), value))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet()));
        }
        return result.toString();
    }

    private String fixed(List<String> blocks, List<ErrorModel> errorModels, Round round) {

        for (int i = 0, indx; i < errorModels.size(); i++) {
            indx = errorModels.get(i).getIndex();
            blocks.set(indx, errorModels.get(i).getFixedBlock().get(round.getIndexes().get(i)));
        }
        StringBuilder stringBuilder = new StringBuilder();
        blocks.forEach(block -> stringBuilder.append(block));
        return stringBuilder.toString();
    }

    private ErrorModel generateFixed(String tmp, Integer index) {
        ErrorModel errorModel = new ErrorModel();
        int len = countBit(tmp);
        if (len % 2 == 1) {
            errorModel.setIndex(index - 1);
            errorModel.buildFixedBlock(tmp, len);
            return errorModel;
        }
        return null;
    }

    private String tetraMask(int num, int mask) {
        String tmp = Integer.toString(num, 4);
        while (tmp.length() < mask) {
            tmp = "0" + tmp;
        }
        return tmp;
    }

    private String byteMask(int num) {
        String tmp = Integer.toBinaryString(num);
        while (tmp.length() < 4) {
            tmp = "0" + tmp;
        }
        return tmp;
    }

    private String stringToByte(String message) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            result.append(build(message.charAt(i) + ""));
        }
        return result.toString();
    }

    private String build(String str) {
        str = alphabet.get(str);
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = str.split(" ");
        for (String string : strings) {
            stringBuilder.append(string + (countBit(string) % 2));
        }
        return stringBuilder.toString();
    }

    private int countBit(String string) {
        int tmp = 0;
        for (int i = 0; i < string.length(); i++) {
            tmp += Integer.parseInt(string.charAt(i) + "");
        }
        return tmp;
    }

    private final HashMap<String, String> alphabet;

    {
        alphabet = new HashMap<>();
        alphabet.put("а", "000 000");
        alphabet.put("б", "000 001");
        alphabet.put("в", "000 010");
        alphabet.put("г", "000 011");
        alphabet.put("д", "000 100");
        alphabet.put("е", "000 101");
        alphabet.put("ж", "000 110");
        alphabet.put("з", "000 111");
        alphabet.put("и", "001 000");
        alphabet.put("й", "001 001");
        alphabet.put("к", "001 010");
        alphabet.put("л", "001 011");
        alphabet.put("м", "001 100");
        alphabet.put("н", "001 101");
        alphabet.put("о", "001 110");
        alphabet.put("п", "001 111");
        alphabet.put("р", "010 000");
        alphabet.put("с", "010 001");
        alphabet.put("т", "010 010");
        alphabet.put("у", "010 011");
        alphabet.put("ф", "010 100");
        alphabet.put("х", "010 101");
        alphabet.put("ц", "010 110");
        alphabet.put("ч", "010 111");
        alphabet.put("ш", "011 000");
        alphabet.put("щ", "011 001");
        alphabet.put("ъ", "011 010");
        alphabet.put("ы", "011 011");
        alphabet.put("ь", "011 100");
        alphabet.put("э", "011 101");
        alphabet.put("ю", "011 110");
        alphabet.put("я", "011 111");
        alphabet.put(" ", "100 000");
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
