package com.zzw.entity.problem;

import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Problem {
    /**
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 
     */
    private LocalDate insertTime;
}