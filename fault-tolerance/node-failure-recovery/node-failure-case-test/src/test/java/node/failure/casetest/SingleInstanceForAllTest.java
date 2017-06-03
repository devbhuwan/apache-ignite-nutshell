package node.failure.casetest;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/06/01
 */
public enum SingleInstanceForAllTest {

    INSTANCE;

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
}
