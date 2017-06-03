package node.failure.casetest;

import node.failure.casetest.model.Batch;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.util.stream.IntStream;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/06/01
 */
@Ignore
public class FiveMillionRecordsInsertUseCaseTest {

    private static final int FIVE_MILLION = 50;

    @BeforeClass
    public static void setup() {
        SingleInstanceForAllTest.INSTANCE.setup();
    }

    @Test
    public void insertFiveMillionRecordsAndGatherReportsFromAllNodes() {
        IntStream.range(0, FIVE_MILLION).forEach(this::persistBatch);
    }

    private void persistBatch(int i) {
        Batch batch = new Batch();
        batch.setBatchId(batchSeq().incrementAndGet());
        batch.setBatchDescription("Batch Description ---" + i);
        batch.setSettlementDate(LocalDate.now());
        batchCache().put(batch.getBatchId(), batch);
    }

    private IgniteCache<Object, Object> batchCache() {
        return Ignition.ignite().cache("batchCache");
    }

    private IgniteAtomicSequence batchSeq() {
        return Ignition.ignite().atomicSequence("batchSeq", 1000, true);
    }
}
