package com.udara.pos.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordManager {
    public static String encryptPassword(String plainText){
        return BCrypt.hashpw(plainText,BCrypt.gensalt(10));
    }
}
