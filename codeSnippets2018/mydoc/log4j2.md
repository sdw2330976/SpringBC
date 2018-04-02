# log4j 2.x

> log4j 2.x的语法不兼容log4j 2.x，但都会默认从classpath中读取配置文件log4j.properties或者log4j2.xml

### 关于升级

如果当前系统使用的日志框架是log4j 1.x,LogBack,JCL(Java Commons Logging),JUL(java.util.logging JDK自带的日志框架)都可以通过修改pom进行升级到
log4j 2.x，此外由于语法兼容性的问题，需要修改日志的配置文件。

### example

```xml
<configuration status="warn" monitorInterval="10" strict="true">
    <properties>
        <property name="faerroeFilePath">log/faerror.log</property>
        <property name="crmLogFilePath">log/crmLog.log</property>
    </properties>
    <appenders>
        <console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
        </console>
        <RollingFile name="RollingFileError" fileName="${faerrorFilePath}"
                     filePattern="${faerrorFilePath}.%d{yyyy-MM-dd}.txt" append="false">
            <ThresholdFilter level="ERROR"/>
            <PatternLayout pattern="%d - %c [%t] %-5p %c %x %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
            </Policies>
        </RollingFile>
    </appenders>

    <loggers>
        <logger name="com.sdw.soft" level="DEBUG"></logger>

        <root level="info" includeLocation="true">
            <appender-ref ref="Console"/>
            <appender-ref ref="RollingFileError"/>
        </root>
    </loggers>

</configuration>

```

#### configuration节点

+ monitorInterval: log4j 2定期检查log4j2.xml配置文件的更新情况的时间间隔（单位：秒）
+ status: log4j 2内部事件记录的等级，只对log4j 2本身的事件有效
+ strict: 是否使用XML Schema来检查配置文件
+ schema: Schema文件的位置

#### Properties节点


#### Appenders节点


#### loggers节点


### pom文件修改

+ log4j 1.x依赖如下jar
```xml
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-log4j12</artifactId>
	<version>${slf4j.version}</version>
	<scope>runtime</scope>
</dependency>
<dependency>
	<groupId>log4j</groupId>
	<artifactId>log4j</artifactId>
	<version>1.2.17</version>
</dependency>
```
slf4j-api是日志的门面日志，slf4j-log4j12是实现log4j 1.x到门面日志的绑定，log4j是log4j的实现类
排除log4j1.x的依赖：
```xml
<exclusions>
    <exclusion>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
    </exclusion>
    <exclusion>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
    </exclusion>
</exclusions>
```
例如：
```xml
<dependency>
    <groupId>org.apache.zookeeper</groupId>
    <artifactId>zookeeper</artifactId>
    <version>3.4.6</version>
    <exclusions>
    	<exclusion>
    		<groupId>org.slf4j</groupId>
    		<artifactId>slf4j-log4j12</artifactId>
    	</exclusion>
    	<exclusion>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

+ logback相关jar包
```xml
<dependency>  
    <groupId>ch.qos.logback</groupId>  
    <artifactId>logback-core</artifactId>  
    <version>0.9.28</version>  
    <type>jar</type>  
</dependency>  
<dependency>  
    <groupId>ch.qos.logback</groupId>  
    <artifactId>logback-classic</artifactId>  
    <version>0.9.28</version>  
    <type>jar</type>  
</dependency>
```

+ log4j 2.x的依赖
```xml
<!-- LOGGING -->
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-api</artifactId>
	<version>1.7.6</version>
</dependency>
		<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-api</artifactId>
	<version>2.8.2</version>
</dependency>
<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-core</artifactId>
	<version>2.8.2</version>
</dependency>
<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-web</artifactId>
	<version>2.8.2</version>
</dependency>
<dependency> <!-- 桥接：告诉Slf4j使用Log4j2 -->
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.8.2</version>
</dependency>
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.3.4</version>
</dependency>
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>jcl-over-slf4j</artifactId>
	<version>1.7.6</version>
	<scope>runtime</scope>
</dependency>
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>jul-to-slf4j</artifactId>
	<version>1.7.6</version>
	<scope>runtime</scope>
</dependency>
<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-1.2-api</artifactId>
	<version>2.8.2</version>
</dependency>
```
log4j-api和log4j-core是log4j 2.x的核心jar包，不可缺少。log4j-slf4j-impl是实现log4j 2.x到slf4j门面日志的绑定，
disruptor是实现异步日志的框架。