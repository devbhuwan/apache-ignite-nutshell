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

    private void igniteClientStart() {
        Ignition.setClientMode(true);
        ignite = Ignition.start("ignite-client-configuration.xml");
    }

    public void setup() {
        if (ignite == null) {
            PropertySetterForTest.INSTANCE.initExternalConfig();
            igniteClientStart();
        }
    }

    public void setupByDocker(DockerComposeRule docker) {
        if (ignite == null) {
            Container zkContainer = docker.containers().container("zk");
            DockerPort dockerPort = zkContainer.port(2181);
            PropertySetterForTest.INSTANCE.getModifiableEnv().put("zkConnection", dockerPort.getIp() + ":" + dockerPort.getExternalPort());
            igniteClientStart();
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


}
