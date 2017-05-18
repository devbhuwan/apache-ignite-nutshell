package advanced.clustering.zookeeper.registration.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/18
 */
@Getter
@Setter
public class Node {

    private String name;
    private String host;
    private String port;

}
