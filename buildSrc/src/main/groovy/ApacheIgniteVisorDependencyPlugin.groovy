import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/06
 */
class ApacheIgniteVisorDependencyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.dependencies {
            new ApacheIgniteZookeeperDependencyPlugin().apply(project);
            compile "org.apache.ignite:ignite-visor-console:${project.igniteVersion}"
        }

    }

}
