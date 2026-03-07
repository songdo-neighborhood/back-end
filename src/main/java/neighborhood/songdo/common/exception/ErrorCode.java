package neighborhood.songdo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(400, "파라미터 값을 확인해주세요."),
    EMPTY_FILE(400, "파일을 등록해주세요"),

    // 404 NOT_FOUND 잘못된 리소스 접근
    FORBIDDEN(403, "접근 권한이 없습니다."),
    RESOURCE_NOT_FOUND(404, "리소스를 찾을 수 없습니다."),
    ENTITY_NOT_FOUND(404, "엔티티를 찾을 수 없습니다."),
    DUPLICATE_BOOKMARK(409, "이미 북마크된 리소스입니다."),

    ALREADY_RESERVATION(404, "이미 예약된 날짜입니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 서버 관리자에게 연락해주세요.");

    private final int status;
    private final String message;
}
