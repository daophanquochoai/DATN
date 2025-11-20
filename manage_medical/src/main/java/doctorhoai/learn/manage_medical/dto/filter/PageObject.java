package doctorhoai.learn.manage_medical.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageObject {
    private int pageNo;
    private int totalPage;
    private Object data;
}
