import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/06
 */
class ApacheIgniteZookeeperDependencyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.dependencies {
            new ApacheIgniteDependencyPlugin().apply(project);
            compile "org.apache.ignite:ignite-zookeeper:${project.igniteVersion}"
        }

    }

}
