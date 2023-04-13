package com.dimm.wbmanager.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
@Getter
public class Token {
    private String TOKEN = "";

    public Token() {

            try {
                FileInputStream fileInputStream = new FileInputStream("token.txt");
                int i;
                while ((i = fileInputStream.read()) != -1) {
                    TOKEN = TOKEN + (char) i;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }


}
