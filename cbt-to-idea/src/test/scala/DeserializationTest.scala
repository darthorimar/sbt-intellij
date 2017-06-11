import org.jetbrains.idea.model._
import org.junit._

class DeserializationTest {
  @Test
  def testDeserialization() = {
    val xml =
      <project>
        <name>cbt-project</name>
        <root>/</root>
        <rootModule>cbt-project</rootModule>
        <modules>
          <module>
            <name>cbt-project</name>
            <root>/</root>
            <sourcesRoot>/</sourcesRoot>
            <sources>
              <source>/SomeClass.scala</source>
            </sources>
            <mavenDependencies>
              <mavenDependency>org.scala-lang:scala-library:2.10.6</mavenDependency>
            </mavenDependencies>
            <moduleDependencies></moduleDependencies>
            <parentBuild>build</parentBuild>
          </module>
          <module>
            <name>sub</name>
            <root>/sub</root>
            <sourcesRoot>/sub</sourcesRoot>
            <sources>
              <source>/sub/OtherClass.scala</source>
            </sources>
            <mavenDependencies></mavenDependencies>
            <moduleDependencies>
              <moduleDependency>root</moduleDependency>
            </moduleDependencies>
            <parentBuild>build</parentBuild>
          </module>
          <module>
            <name>build</name>
            <root>/build</root>
            <sourcesRoot>/build</sourcesRoot>
            <sources>
              <source>/build/build.scala</source>
            </sources>
            <mavenDependencies>
              <mavenDependency>org.scala-lang:scala-library:2.11.8</mavenDependency>
              <mavenDependency>org.scala-lang.modules:scala-xml:1.0.6</mavenDependency>
            </mavenDependencies>
            <moduleDependencies></moduleDependencies>
          </module>
        </modules>
      </project>

    val project = Project("cbt-project", "/",
      modules = Seq(
        Module("cbt-project",
          contentRoots = Seq(ContentRoot("/", Seq("/"))),
          libraryDependencies = Seq(LibraryDependency("org.scala-lang:scala-library:2.10.6")),
          moduleDependencies = Seq(ModuleDependency("build"))),
        Module("sub",
          contentRoots = Seq(ContentRoot("/sub", Seq("/sub"))),
          moduleDependencies = Seq(ModuleDependency("root"), ModuleDependency("build"))),
        Module("build",
          contentRoots = Seq(ContentRoot("/build", Seq("/build"))),
          libraryDependencies = Seq(
            LibraryDependency("org.scala-lang:scala-library:2.11.8"),
            LibraryDependency("org.scala-lang.modules:scala-xml:1.0.6")))))

    Assert.assertEquals(project, CbtBuildInfoDeserializer.deserialize(xml))

  }
}
