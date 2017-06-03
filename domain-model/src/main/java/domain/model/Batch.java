package domain.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/06/01
 */
@Getter
@Setter
public class Batch implements Serializable {

    private Long batchId;
    private String batchDescription;
    private LocalDate settlementDate;
}
