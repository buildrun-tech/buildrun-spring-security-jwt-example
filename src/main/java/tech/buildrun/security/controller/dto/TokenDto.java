package tech.buildrun.security.controller.dto;

public record TokenDto(String accessToken,
                       long expiresIn) {
}
