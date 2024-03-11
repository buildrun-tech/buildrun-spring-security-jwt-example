package tech.buildrun.springsecurity.controller.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
