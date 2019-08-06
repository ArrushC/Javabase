# Javabase
[![](https://jitpack.io/v/ArrushC/Javabase.svg)](https://jitpack.io/#ArrushC/Javabase)


## What is it ?
It is a database api that provides you the tools to connect to your database
and make operations in a simple, fast way.

Because it has a lot of OOP concepts, it allows you to use them in a way
that can make your program efficient and maintainable, whilst offering easier
and simpler alternatives.

## How to add it in your project
If you want to add Javabase as a dependency for your project, you
can do so with this code by replacing the VERSION with the [latest version](https://github.com/ArrushC/Javabase/releases)

#### Gradle
```gradle
repositories {
	maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.ArrushC:Javabase:VERSION'
}
```

#### Maven
```xml
<build>
    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
    <dependencies>
        <dependency>
	        <groupId>com.github.ArrushC</groupId>
	        <artifactId>Javabase</artifactId>
	        <version>VERSION</version>
	    </dependency>
    </dependencies>
</build>
```