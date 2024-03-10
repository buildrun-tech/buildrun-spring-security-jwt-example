package tech.buildrun.security.controller.dto;

import java.util.List;

public record PageDto<T>(List<T> content, int page, int pageSize, int totalPages, long totalElements) {
}
