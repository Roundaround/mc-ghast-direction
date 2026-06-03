plugins {
  id("me.roundaround.allay")
}

allay {
  displayName.set("Ghast Direction")
  description.set("Fix bug where ghasts always face the same direction while not moving.")
  authors.set(listOf("Roundaround"))
  license.set("MIT")
  homepage.set("https://modrinth.com/mod/ghast-direction")
  repository.set("https://github.com/Roundaround/mc-fabric-ghast-direction")
  issues.set("https://github.com/Roundaround/mc-fabric-ghast-direction/issues")
  logoFile.set("assets/ghastdirection/icon.png")

  modrinth {
    projectId.set("ghast-direction")
  }

  release {
    versionType.set("release")
    sourcesJar.set(true)
  }
}
