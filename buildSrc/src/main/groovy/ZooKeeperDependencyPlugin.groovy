import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/18
 */
class ZooKeeperDependencyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.dependencies {
            compile "org.apache.curator:curator-framework:${project.apacheCuratorVersion}"
        }

    }
}
