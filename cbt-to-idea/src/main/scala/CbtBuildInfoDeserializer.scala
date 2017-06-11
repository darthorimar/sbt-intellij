import scala.xml._
import org.jetbrains.idea.model._

object CbtBuildInfoDeserializer {
  def deserialize(node: Node): Project = deserializeProject(node)

  private def deserializeProject(project: Node) = {
    Project((project \ "name").text,
      (project \ "root").text,
      modules = (project \ "modules" \ "module").map(deserializeModule))
  }

  private def deserializeModule(module: Node) = {
    val moduleDependencies =
      Seq(module \ "moduleDependencies" \ "moduleDependency", module \ "parentBuild")
        .flatten
        .map(d => ModuleDependency(d.text.trim))

    val mavenDependencies = (module \ "mavenDependencies" \ "mavenDependency")
      .map(d => LibraryDependency(d.text.trim))
    val contentRoot = ContentRoot((module \ "root").text,
      sources = Seq((module \ "sourcesRoot").text))

    Module(name = (module \ "name").text,
      contentRoots = Seq(contentRoot),
      moduleDependencies = moduleDependencies,
      libraryDependencies = mavenDependencies)
  }
}
