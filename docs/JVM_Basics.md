# JVM Basics

## JDK, JRE and JVM

JVM (Java Virtual Machine) is what actually runs a Java program. It doesn't run my .java files directly, it runs the compiled .class files and turns them into instructions my computer understands. Mac, Windows and Linux each have their own JVM.

JRE (Java Runtime Environment) is the JVM plus the standard libraries a program needs while running, like ArrayList and Scanner. That's enough if you only want to run Java programs. Newer Java versions don't ship a separate JRE anymore, it just comes inside the JDK.

JDK (Java Development Kit) is the JRE plus the tools for writing Java, mainly javac, the compiler. You need the JDK to build a project like this one.

So basically: JDK contains the JRE, and the JRE contains the JVM.

## Bytecode

When I compile Main.java with javac, I get Main.class. That file isn't Java code anymore, and it isn't machine code for my Mac either. It's bytecode, a set of instructions meant for the JVM.

```
Main.java  -->  javac  -->  Main.class (bytecode)  -->  JVM runs it
```

In this project, `./gradlew build` creates the .class files in build/classes/java/main, and the `java` command starts the JVM to run them.

## Write once, run anywhere

Because javac produces bytecode and not machine code, the same .class files can run on any computer that has a JVM. The JVM on each machine takes care of turning the bytecode into instructions for that particular system.

So I can build LearnTrack on my Mac and run the same compiled files on Windows or Linux without changing or recompiling anything. That's what "write once, run anywhere" means.
