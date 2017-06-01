package node.failure.casetest;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.DockerPort;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/06/01
 */
public enum SingleInstanceForAllTest {

    INSTANCE;

    private volatile Ignite ignite;

    SingleInstanceForAllTest() {
        igniteNodesStart();
        igniteClientStart();

    }

    private void igniteNodesStart() {
        DockerComposeRule docker = DockerComposeRule.builder().file("docker/nodes/docker-compose.yml").build();
        try {
            docker.before();
            setZkConnection(docker.containers().container("zk"));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void setZkConnection(Container zk) throws NoSuchFieldException, IllegalAccessException {
        Map<String, String> env = System.getenv();
        Field field = env.getClass().getDeclaredField("m");
        field.setAccessible(true);
        Map<String, String> modifiableEnv = (Map<String, String>) field.get(env);
        DockerPort dockerPort = zk.port(2181);
        modifiableEnv.put("zkConnection", dockerPort.getIp() + ":" + dockerPort.getExternalPort());
    }

    private void igniteClientStart() {
        ignite = Ignition.start("ignite-client-configuration.xml");
    }

    public void setup() {
        if (ignite == null)
            igniteClientStart();
    }
}
