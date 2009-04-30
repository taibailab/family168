package com.family168.guess;

import java.util.Random;


/**
 * @author ybtan
 *
 */
public class Boss {
    public int go() {
        int go = new Random().nextInt(9);
        System.out.println("Boss hands with " + go);

        return go;
    }

    public boolean bingle(String answer) {
        int parse = Integer.parseInt(answer);

        if (parse == go()) {
            return true;
        }

        return false;
    }
}
