plugins {
  id("me.roundaround.allay")
}

allay {
  displayName.set("Ghast Direction")
  description.set("Fix bug where ghasts always face the same direction while not moving.")
  authors.set(listOf("Roundaround"))
  license.set("MIT")
  homepage.set("https://modrinth.com/mod/ghast-direction")
  repository.set("https://github.com/Roundaround/mc-ghast-direction")
  issues.set("https://github.com/Roundaround/mc-ghast-direction/issues")
  logoFile.set("assets/ghastdirection/banner.png")

  modrinth {
    projectId.set("ghast-direction")
  }

  curseforge {
    projectId.set(1563762)
  }

  release {
    versionType.set("release")
    minecraftVersions("26.1".."26.1.2")
    changelogDir.set(file("changelogs"))
  }
}
