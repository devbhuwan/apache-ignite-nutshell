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
public class Payment implements Serializable {

    private Long paymentId;
    private String fromAccount;
    private String toAccount;
    private String paymentDetail;
    private LocalDate paymentAt;
    private Long batchId;
}
