package com.dimm.wbmanager.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
@Getter
public class Token {
    @Value("${token}")
    private String token;
}
