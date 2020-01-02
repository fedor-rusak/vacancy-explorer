# Development comments

While doing this project I may stumble upon something that I consider worth commenting.

That is the file with such information

## Random

### My machine & software setup

I am using Linux Mint 19.1 Cinnamon for my development machine.

Java installed is OpenJDK 11.

When installing build tools I prefer to download them separately and not through apt. They can be really old.

I installed gradle in *~/Software/gradle6* but before bash can use it I have to modify **PATH** variable.

I modified *.bashrc* to use gradle for every bash instance with my current user.

### Initial project generation

I am used to having existing gradle configuration.

Today for this I tried [Official gradl guide for Springboot 2](https://guides.gradle.org/building-spring-boot-2-projects-with-gradle/).

After answering some questions I got initial setup. Nice!

No need for gitattributes, gradlew and gradle folder.

Not sure why guava is there by default... don't need it right now.

And I really like using Make for calling steps via CLI (command-line interface).

Aha .gradle and build should be ignored by git.

Some dependencies look weird to me but lets keep it now.