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

### Why 1

So I added path to RestController, addet path to GetMapping. And second one ignored first one...

### Why 2

So You run gradle test and see that assertion failed. Literally nothing else. Who needs this kind of test result?

And you need some *exceptionFormat* in your *testLogging* in your *test* section in your *build.gradle*. Sigh...

### Why 3

You want to check if header has some value? Well you have to use either some java 7 *javax.ws.rs.core.MediaType* or use toString on *org.springframework.http.MediaType* object from Spring.

Strings are not cool enough?

### Collections synchronizedMap and synchronizedList

These two methods can give you simple blocking solution to useful data structures. It may be not suitable for high performance. But it definitely simplifies concurrent manipulation with these data structures.

### Atomic java things are great

Want a counter and don't bother with synchronization AtomicInteger and other to rescue!

### Ubuntu(and Mint) has some awesome default key bindings

Ctrl-Alt-(Left/Right) switches between workspaces with funny sound.

Alt-F10 to swith between fullscreen and normal size for application window.

Alt-tab or Ctrl-Alt-Down to show opened windows and arrows to navigate them.

Home and End move on one line. Ctrl-Home beginning of file and Ctrl-End end of file.

### JShell is nice!

Sometimes I want to check simple syntax details of Java language. Compare two Integer(1) using == for example.

Previously I had to use online interpreter or create some one-time file.

With JDK 11 I can use jshell command and have interactive Java interpreter in my console.

Type */help* to get manual or */exit* to exit.

### ResponseEntity

That is decent approach to control status for you http responses. Like it!

### Tests

Java tests can be really slow. Best thing I have now for this is super-simple test to verify response statuses.

And fast CPU can also help.

### JVM is RAM hungry beast

Without default flags my *simple* app uses around 380Mb memory. For simple web-server.

I know JVM has many tuning knobs and additional facilities. But it is still a huge RAM consumption to me.

### Time is hard

All thos timezones and summer saving weirdness. I really hope UTC is the simplest right solution to have consistent creation timestamps.

### ResultMatchers & hamcrest

Looks like super over-engineered way to check some pieces of http response.

Yet it makes test code shorter, so maybe it has some practical benefits for now.