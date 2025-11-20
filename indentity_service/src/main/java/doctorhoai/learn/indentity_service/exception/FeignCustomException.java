package doctorhoai.learn.indentity_service.exception;

import feign.Request;
import feign.RetryableException;
import lombok.Getter;

@Getter
public class FeignCustomException extends RetryableException {

  private final int status;
  private final String responseBody;

  public FeignCustomException(int status, String message, String responseBody, Request request) {
    super(status, message, request.httpMethod(), (Long) null, request);
    this.status = status;
    this.responseBody = responseBody;
  }
}