package advanced.clustering.zookeeper.registration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/18
 */
public class CuratorStarter {

    private static final String ZOOKEEPER_PROPERTIES = "zookeeper.properties";
    private static final String ZK_HOST = "zk.host";
    private static final String ZK_PORT = "zk.port";
    private final CuratorFramework curatorFramework;

    private CuratorStarter() {
        try {
            Properties props = new Properties();
            props.load(this.getClass().getClassLoader().getResourceAsStream(ZOOKEEPER_PROPERTIES));
            curatorFramework = CuratorFrameworkFactory
                    .newClient(props.getProperty(ZK_HOST) + ":" + props.getProperty(ZK_PORT), new RetryNTimes(5, 1000));
            curatorFramework.start();
            NodeRegistrar.INSTANCE.doRegistration(curatorFramework);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new CuratorStarter();
    }

}
