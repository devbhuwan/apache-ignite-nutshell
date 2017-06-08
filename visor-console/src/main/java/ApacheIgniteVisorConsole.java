import common.test.PropertySetterForTest;
import org.apache.ignite.visor.commands.VisorConsole;
import org.h2.util.StringUtils;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/06/07
 */
public class ApacheIgniteVisorConsole {

    public static void main(String[] args) {
        if (args == null || args.length < 1 || StringUtils.isNullOrEmpty(args[0]))
            throw new IllegalArgumentException("provide Zookeeper Connection string like '0.0.0.0:32775' as a firstArgument");
        PropertySetterForTest.INSTANCE.getModifiableEnv().put("zkConnection", args[0]);
        VisorConsole.main(args);
    }

}
