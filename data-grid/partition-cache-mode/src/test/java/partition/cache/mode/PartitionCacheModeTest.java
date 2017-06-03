package partition.cache.mode;

import domain.model.Batch;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import static common.test.SingleInstanceForAllTest.INSTANCE;
import static org.junit.Assert.assertEquals;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/06/03
 */
public class PartitionCacheModeTest {

    private static final int FIVE_MILLION = 5000;

    @BeforeClass
    public static void setup() {
        INSTANCE.setup();
    }

    @Test
    public void insertFiveMillionRecordsAndGatherReportsFromAllNodes() {
        IntStream.range(0, FIVE_MILLION).forEach(this::persistBatch);
        List<List<?>> all = INSTANCE.batchCache()
                .query(new SqlFieldsQuery("select count(batchId) from Batch"))
                .getAll();
        for (List<?> row : all) {
            assertEquals((long) FIVE_MILLION, row.get(0));
            break;
        }
    }

    private void persistBatch(int i) {
        Batch batch = new Batch();
        batch.setBatchId(INSTANCE.batchSeq().incrementAndGet());
        batch.setBatchDescription("Batch Description ---" + i);
        batch.setSettlementDate(LocalDate.now());
        INSTANCE.batchCache().put(batch.getBatchId(), batch);
    }
}