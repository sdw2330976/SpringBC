# eureka server

    Spring Cloud Eureka模块提供的是被动式的服务发现
    Eureka Client通过HTTP(或者TCP,UDP)去Eureka server注册和获取服务列表，为了高可用，一般会有多个eureka server组成集群，eureka会移除那些心跳检查未通过的服务。
  
![](/doc/photo/eureka.png)

### maven config
---

`
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-netflix</artifactId>
                <version>1.3.4.RELEASE</version>
                <type>pom</type>        
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <dependencies>    
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
        </dependency>
    </dependencies>
`

