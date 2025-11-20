package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class ManufacturerNotFoundException extends RuntimeException {
  public ManufacturerNotFoundException() {
    super(EMessageException.MANUFACTURER_NOT_FOUND.getMessage());
  }
}
