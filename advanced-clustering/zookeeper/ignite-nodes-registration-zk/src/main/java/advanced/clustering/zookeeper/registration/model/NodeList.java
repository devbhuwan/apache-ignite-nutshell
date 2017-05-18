package advanced.clustering.zookeeper.registration.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/18
 */
@Getter
@Setter
public class NodeList {

    public NodeList() {
        this.nodes = new ArrayList<>();
    }

    private List<Node> nodes;
}
