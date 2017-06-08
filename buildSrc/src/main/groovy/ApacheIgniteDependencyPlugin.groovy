import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/06
 */
class ApacheIgniteDependencyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.dependencies {
            compile "org.apache.ignite:ignite-core:${project.igniteVersion}"
            compile "org.apache.ignite:ignite-indexing:${project.igniteVersion}"
            compile "org.apache.ignite:ignite-spring:${project.igniteVersion}"
        }

    }

}
