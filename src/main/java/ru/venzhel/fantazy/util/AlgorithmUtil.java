package ru.venzhel.fantazy.util;

public class AlgorithmUtil {
    static public int calculateFantasyPoints(int place) {
        int points = 0;
        if (place > 0) {
            points = (int)Math.ceil(100 * Math.pow(0.96, place - 1));
        }
        return points;
    }
}
