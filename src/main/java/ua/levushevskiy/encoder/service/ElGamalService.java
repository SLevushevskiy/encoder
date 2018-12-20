package ua.levushevskiy.encoder.service;

import org.springframework.stereotype.Service;

@Service
public class ElGamalService {

    Long p = 113L, g = 17L, x = 19L, k = 23L, y;


    {
        this.y = (int) Math.pow(g, x) % p;
    }

    public String encode(String message) {
        int hash = message.hashCode();
        long r = (long) Math.pow(g, k) % p;
        long s = ((hash - x * r) * (long) Math.pow(k, -1)) % (p - 1);

        return hash + " " + r + " " + s;
    }

    public Boolean decode(String message, long r, long s) {
        int hash = message.hashCode();
        return (Math.pow(y, r) * Math.pow(r, s)) % p == Math.pow(g, hash) % p;
    }

}
