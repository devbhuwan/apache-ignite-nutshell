package common.test;

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

    SingleInstanceForAllTest() {
        igniteClientStart();
    }

    private void igniteClientStart() {
        PropertySetterForTest.INSTANCE.initExternalConfig();
        Ignition.setClientMode(true);
        ignite = Ignition.start("ignite-client-configuration.xml");
    }

    public void setup() {
        if (ignite == null) {
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
