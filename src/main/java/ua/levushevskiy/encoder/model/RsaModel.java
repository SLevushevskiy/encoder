package ua.levushevskiy.encoder.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
public class RsaModel implements Serializable {

    @Value("{num.first}")
    private Integer simpleNumFirst = 29;

    @Value("{num.second}")
    private Integer simpleNumSecond = 31;

    /*open, close key*/
    private Integer multNumber;
    /*open key*/
    private Integer funcEuler;

    /**
     * odd positive integer having no common divisors with the result of the Euler function
     */
    @Value("{open.exp}")
    private Integer openExp = 11;

    /*close key*/
    private Integer closeKey;

    @Value("{coefficient}")
    private Integer coef = 8;

    public RsaModel() {
        multNumber = simpleNumFirst * simpleNumSecond;
        funcEuler = (simpleNumFirst - 1) * (simpleNumSecond - 1);
        closeKey = (coef * funcEuler + 1) / openExp;
    }


}
