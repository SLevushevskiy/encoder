package ua.levushevskiy.encoder.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class ElGamalService {

    Long p = 23L, g = 5L, x = 7L, k = 5L, y;


    {
        this.y = (int) Math.pow(g, x) % p;
    }

    public String encode(String message) {
        int hash = message.hashCode();
        long r = (long) Math.pow(g, k) % p;
        long s = (hash - x * r) % (p - 1);
        long res = 0;
        for (int i = 0; ; i += p - 1)
            if (s + i > 0 && (s + i) % k == 0) {
                res = (s + i) / k;
                break;
            }

        return "r: " + r + "; s: " + res;
    }

    public Boolean decode(String message, long r, long s) {
        int hash = message.hashCode();
        BigInteger num = BigInteger.valueOf(y);
        BigInteger num2 = BigInteger.valueOf(r);
        num2 = num2.pow((int) s);
        num = num.pow((int) r);
        num = num.multiply(num2);
        num = num.remainder(BigInteger.valueOf(p));

        BigInteger num3 = BigInteger.valueOf(g);
        num3 = num3.modPow(BigInteger.valueOf(hash),BigInteger.valueOf(p));
        return num.equals(num3);
    }

}
