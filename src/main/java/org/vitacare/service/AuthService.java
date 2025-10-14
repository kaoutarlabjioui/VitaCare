package org.vitacare.service;

import org.vitacare.dto.auth.LoginRequestDTO;
import org.vitacare.dto.auth.LoginResponseDTO;
import org.vitacare.dto.auth.RegisterRequestDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO dto);

    void register(RegisterRequestDTO dto);
}
