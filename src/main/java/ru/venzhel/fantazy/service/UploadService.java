package ru.venzhel.fantazy.service;

import java.io.IOException;

public interface UploadService {
    boolean uploadRace(byte[] bytes) throws IOException;
}
