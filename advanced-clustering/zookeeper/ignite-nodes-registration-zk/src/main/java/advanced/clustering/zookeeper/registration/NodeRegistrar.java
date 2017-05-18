package advanced.clustering.zookeeper.registration;

import advanced.clustering.zookeeper.registration.model.Node;
import advanced.clustering.zookeeper.registration.model.NodeList;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/18
 */
public enum NodeRegistrar {

    INSTANCE;

    public static final String ZNODE_SERVICE_ROOT_URL = "/service/";
    public static final String IGNITE_NODES_CONFIGURATION = "ignite-nodes.yml";
    private final NodeList nodes;

    NodeRegistrar() {
        Constructor constructor = new Constructor(NodeList.class);
        TypeDescription configDesc = new TypeDescription(NodeList.class);
        constructor.addTypeDescription(configDesc);
        Object load = new Yaml(constructor).load(this.getClass().getClassLoader().getResourceAsStream(IGNITE_NODES_CONFIGURATION));
        this.nodes = load == null ? new NodeList() : (NodeList) load;
    }

    public void doRegistration(CuratorFramework curatorFramework) {
        nodes.getNodes().forEach(node -> this.nodeRegister(node, curatorFramework));
    }

    private void nodeRegister(Node node, CuratorFramework curatorFramework) {
        String znode = getZnode(node.getName());
        try {
            if (curatorFramework.checkExists().forPath(znode) == null) {
                curatorFramework.create().creatingParentsIfNeeded().forPath(znode);
            }
            curatorFramework
                    .create()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(znode + "/_", getBytes(node));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private byte[] getBytes(Node node) {
        return new byte[0];
    }

    private String getZnode(String name) {
        return ZNODE_SERVICE_ROOT_URL + name;
    }

}
