# jdk7新特性

### JSR292 支持动态类型语言

JRS292的实现增加了一个InvokeDynamic的字节码指令来支持动态类型语言，使得在将源代码编译成字节码时不需要确定方法的签名（方法参数的类型和返回值类型）。
当运行时执行InvokeDynamic指令时，JVM会通过新的动态链接机制MethodHandles寻找真实的方法。

### G1垃圾回收器(Garbage-First Collector)

G1是针对大内存、多核CPU环境的服务端的垃圾回收器，目的在于减少Full GC带来的暂停次数，增加吞吐量。
G1实现:在堆上分配一系列大小相同的连续的区域，在回收的时候先扫描所有的区域，按照每块区域存活对象的大小进行排序，优先处理存活对象小的区域，
即垃圾对象对多的区域，这也是Garbage First名称的由来。G1把要收集的区域内的存活对象合并并且复制到其他区域，从而避免了CMS遇到的内存碎片的问题。
此外，G1采用了一个可预测暂停时间模型来达到软实时的要求。


### JSR334:小的语言改进(Project Coin)

Coin项目提供了一系列语言上的改进，为java开发者提供了更多的便利。其中包括支持了String的switch语句，在try之后自动关闭资源（try-with-resources），
更简洁的泛型，数字可以用下划线分隔和多重catch的改进等等。

### 核心类库改进
+ ClassLoader新增API
为了防止自定义多线程ClassLoad产生死锁问题，java.lang.ClassLoader类增加了一下API。
```java
protected Object getClassLoadingLock(String className) 
protected static boolean registerAsParallelCapable()
```
+ URLClassLoader新增API
URLClassLoader新增close方法可以关闭该类加载器打开的资源。

+ concurrent包的改进
java.util.concurrent包引入了一个轻量级的fork/join的框架来支持多线程的并发计算。此外实现了Phaser类，它类似于CyclicBarrier和CountDownLatch但更灵活。
最后，ThreadLoadRandom类提供了线程安全的伪随机数生成器。

+ 国际化(i18n)
支持unicode6.0。改进java.util.locale以及支持IETF BCP 47和UTR 35，并且在get/setLocale的时候分成了用于显示的locale和用于格式化的locale

### I/O网络

#### Java 平台的更多新 NIO 2 的 API（JSR 203）
NIO2 主要包括了 3 个方面的改进：
+ 新的文件系统 API 支持大量文件属性的访问、文件系统监控服务、平台相关的 API，如 DosFileAttributes 和 PosixFileAttributes 等，以及一个可插拔文件系统的 SPI。
+ Socket 和文件的异步 IO。
+ Socket channel 的功能完善，支持 binding、多播等。

#### 支持 zip/jar 的 FileSystemProvider 实现
NIO2 提供了新的 service provider java.nio.file.spi.FileSystemProvider 来实现一个文件系统，并在 demo 中提供了一个 zip/jar 的文件系统示例。

+ SCTP(Stream Control Transmission Protocol)
实现了 SCTP 协议，即流控制传输协议，由 RFC 2960 规范。它是一种类似于 TCP 的可靠传输协议。SCTP 在两个端点之间提供稳定、有序的数据传递服务（非常类似于 TCP），
并且可以保护数据消息边界（例如 UDP）。然而，与 TCP 和 UDP 不同，SCTP 是通过多宿主（Multi-homing）和多流（Multi-streaming）功能提供这些收益的，这两种功能均可提高可用性 。

+ SDP(Socket Direct Protocol)
SDP，套接字定向协议，提供了高吞吐量低延迟的高性能网络连接。它的设计目标是为了使得应用程序能够透明地利用 RDMA(Remote Direct Memory Access) 通信机制来加速传统的 TCP/IP 网络通信。
最初 SDP 由 Infiniband 行业协会的软件工作组所指定，主要针对 Infiniband 架构，后来 SDP 发展成为利用 RDMA 特性进行传输的重要协议。JDK7 这次实现 Solaris 和 Linux 平台上的 SDP。

+ 使用 Windows Vista 上的 IPv6 栈
更新了网络方面的代码，在 Windows Vista 上，当 IPv6 栈可用时，优先使用 IPv6 栈。

### 图形界面客户端


### 其他模块

+ XML
将最新的 XML 组件更新到相关开源实现的稳定版本：JAXP 1.4、JAXB 2.2a、JAX-WS 2.2。

+ Java 2D
对于现代 X11 桌面系统，提供了基于 XRender 的渲染管线。
加入了 OpenType/CFF 字体的支持。
对 Linux 字体更好的支持，使用 libfontconfig 来选择字体。

+ 安全 / 加密
椭圆曲线加密算法 (ECC)，提供了一个可移植的标准椭圆曲线加密算法实现，所有的 Java 应用都可以使用椭圆曲线加密算法。
JSSE(SSL/TLS)
在证书链认证中设置关闭弱加密算法，比如 MD2 算法已经被证实不太安全。
增加对 TLS(Transport Layer Security) 1.1 和 1.2 的支持，它们对应的规范分别是 RFC 4346 和 RFC 5246。
SNI(Server Name Indication) 支持，其规范定义在 RFC 4366。
TLS 密钥重新协商机制，RFC 5746。

+ 数据库连接 （JDBC）

支持了规范 JDBC 4.1 和 Rowset 1.1。