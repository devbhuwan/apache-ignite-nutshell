package advanced.clustering.zookeeper.discovery;

import org.apache.ignite.Ignition;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/18
 */
public class ZooKeeperDiscoveryIgniteNode {

    public static final String ZOOKEEPER_DISCOVERY_IGNITE_CONFIGURATION_XML = "zookeeper-discovery-ignite-configuration.xml";

    public static void main(String[] args) {
        Ignition.start(ZOOKEEPER_DISCOVERY_IGNITE_CONFIGURATION_XML);
    }

}
