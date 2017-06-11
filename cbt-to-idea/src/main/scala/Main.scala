import org.jetbrains.idea.Serializer

import scala.xml._

object Main extends App{
  val xml = XML.loadFile("/home/ilya/Documents/projects/sbt-intellij/cbt-to-idea/resources/cbt_project.xml")
  val project = CbtBuildInfoDeserializer.deserialize(xml)
  val ideaXml = Serializer.toXml(project)
  print(new PrettyPrinter(200, 2).format(ideaXml))
}
