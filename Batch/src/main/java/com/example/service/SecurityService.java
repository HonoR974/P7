package com.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface SecurityService
{
    String authticate() throws IOException, InterruptedException;

}
