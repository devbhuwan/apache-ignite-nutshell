package common.test;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.DockerPort;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/06/01
 */
public enum SingleInstanceForAllTest {

    INSTANCE;

    public static final String ZK_IGNITE_CLIENT_NODE = "zkIgniteClientNode";
    private volatile Ignite ignite;

    private void igniteClientStart(String file) {
        Ignition.setClientMode(true);
        ignite = Ignition.start(file == null ? "ignite-client-configuration.xml" : file);
    }

    public void setup(String file) {
        if (ignite == null) {
            PropertySetterForTest.INSTANCE.initExternalConfig();
            igniteClientStart(file);
        }
    }

    public void setupByDocker(DockerComposeRule docker, String file) {
        if (ignite == null) {
            Container zkContainer = docker.containers().container("zk");
            DockerPort dockerPort = zkContainer.port(2181);
            PropertySetterForTest.INSTANCE.getModifiableEnv().put("zkConnection", dockerPort.getIp() + ":" + dockerPort.getExternalPort());
            igniteClientStart(file);
        }
    }

    public IgniteCache<Object, Object> batchCache() {
        return getIgnite().cache("batchCache");
    }

    public Ignite getIgnite() {
        return Ignition.ignite(ZK_IGNITE_CLIENT_NODE);
    }

    public IgniteAtomicSequence batchSeq() {
        return getIgnite().atomicSequence("batchSeq", 1000, true);
    }

    public void stopContainers(DockerComposeRule docker, String... names) {
        try {
            for (String name : names)
                docker.containers().container(name).stop();
            docker.containers().allContainers().forEach(this::printContainerState);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void printContainerState(Container container) {
        try {
            System.out.printf("Container [name=%s, state=%s, isUp=%s]\n---------------\n", container.getContainerName(), container.state(), container.state().isUp());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void startContainers(DockerComposeRule docker, String... names) {
        try {
            for (String name : names)
                docker.containers().container(name).start();
            docker.containers().allContainers().forEach(this::printContainerState);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

}
