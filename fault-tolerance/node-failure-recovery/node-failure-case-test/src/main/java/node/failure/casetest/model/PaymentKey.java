package node.failure.casetest.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/06/01
 */
@Getter
@Setter
public class PaymentKey implements Serializable {

    private Long paymentId;
    private Long batchId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentKey that = (PaymentKey) o;

        if (paymentId != null ? !paymentId.equals(that.paymentId) : that.paymentId != null) return false;
        return batchId != null ? batchId.equals(that.batchId) : that.batchId == null;
    }

    @Override
    public int hashCode() {
        int result = paymentId != null ? paymentId.hashCode() : 0;
        result = 31 * result + (batchId != null ? batchId.hashCode() : 0);
        return result;
    }
}
