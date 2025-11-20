package doctorhoai.learn.base_domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BaseFilter{
    private Integer pageNo;
    private Integer pageSize;
    private String sort;
    private String order;
    private LocalDate startDate;
    private LocalDate endDate;
    private String search;
}
