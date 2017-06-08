package replicated.cache.mode;

import com.palantir.docker.compose.DockerComposeRule;
import domain.model.Batch;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import static common.test.SingleInstanceForAllTest.INSTANCE;
import static org.junit.Assert.assertEquals;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/06/08
 */
public class ReplicatedCacheModeTest {

    private static final int FIVE_MILLION = 500;

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            .build();

    @BeforeClass
    public static void setup() {
        INSTANCE.setupByDocker(docker, "replicated-cache-mode.xml");
        IntStream.range(0, FIVE_MILLION).forEach(ReplicatedCacheModeTest::persistBatch);
    }

    private static void persistBatch(int i) {
        Batch batch = new Batch();
        batch.setBatchId(INSTANCE.batchSeq().incrementAndGet());
        batch.setBatchDescription("Batch Description ---" + i);
        batch.setSettlementDate(LocalDate.now());
        INSTANCE.batchCache().put(batch.getBatchId(), batch);
    }

    @Test
    public void oneNodeIsDownThenBackupCopyOfThatNodeShouldBePresentInAnyOtherNodes() throws InterruptedException {
        INSTANCE.stopContainers(docker, "node1");
        INSTANCE.startContainers(docker, "node2", "node3");
        Thread.sleep(100);
        assertRowSizeEqualToInsertedDataAtAnyFailureCase();
    }

    @Test
    public void twoNodesAreDownThenBackupCopyOfNodesShouldBePresentInAnyOtherNodes() throws InterruptedException {
        INSTANCE.stopContainers(docker, "node1", "node2");
        INSTANCE.startContainers(docker, "node3");
        Thread.sleep(100);
        assertRowSizeEqualToInsertedDataAtAnyFailureCase();
    }

    @Test
    public void queryReturnSameSizeEqualToInsertedDataDuringAllNodesAreUp() throws InterruptedException {
        INSTANCE.startContainers(docker, "node1", "node2", "node3");
        Thread.sleep(100);
        assertRowSizeEqualToInsertedDataAtAnyFailureCase();
    }

    private void assertRowSizeEqualToInsertedDataAtAnyFailureCase() {
        List<List<?>> all = INSTANCE.batchCache()
                .query(new SqlFieldsQuery("select count(batchId) from Batch"))
                .getAll();
        for (List<?> row : all) {
            assertEquals((long) FIVE_MILLION, row.get(0));
            break;
        }
    }

}