import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.stream.IntStream;

import static advanced.clustering.zookeeper.discovery.ZooKeeperDiscoveryIgniteNode.ZOOKEEPER_DISCOVERY_IGNITE_CONFIGURATION_XML;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/18
 */
public class ZooKeeperDiscoveryIgniteNodeClientTest {

    public static final String ZOOKEEPER_DISCOVERY_CACHE = "ZOOKEEPER_DISCOVERY_CACHE";

    @Test
    public void testCacheInClientMode() {
        Ignition.setClientMode(true);
        Ignition.start(ZOOKEEPER_DISCOVERY_IGNITE_CONFIGURATION_XML);
        IgniteCache<Integer, String> cache = Ignition.ignite().getOrCreateCache(buildCacheCfg());
        IntStream.range(0, 100).forEach(i -> cache.put(i, "VALUE-" + i));
        Assertions.assertThat(cache.get(99))
                .isNotBlank()
                .isEqualTo("VALUE-" + 99);
    }

    private CacheConfiguration<Integer, String> buildCacheCfg() {
        CacheConfiguration<Integer, String> cacheCfg = new CacheConfiguration<>();
        cacheCfg.setName(ZOOKEEPER_DISCOVERY_CACHE);
        return cacheCfg;
    }
}