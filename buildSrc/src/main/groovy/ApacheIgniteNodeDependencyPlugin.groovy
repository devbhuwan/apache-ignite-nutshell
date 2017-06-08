import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/06
 */
class ApacheIgniteNodeDependencyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.dependencies {
            new ApacheIgniteZookeeperDependencyPlugin().apply(project);
            compile "org.apache.ignite:ignite-ssh:${project.igniteVersion}"
            compile "org.apache.ignite:ignite-log4j:${project.igniteVersion}"
            compile "org.apache.ignite:ignite-rest-http:${project.igniteVersion}"
        }

    }

}
