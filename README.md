# 导航网格

## 1)整体介绍
![构建状态](https://img.shields.io/github/actions/workflow/status/ppiastucki/recast4j/gradle.yml?branch=main&logo=github)

![仓库大小](https://img.shields.io/github/repo-size/ppiastucki/recast4j.svg?colorB=lightgray)

[![maven中央仓库版本](https://img.shields.io/maven-central/v/org.recast4j/recast.svg?label=maven%20central)](https://search.maven.org/search?q=g:org.recast4j)

![用到的语言](https://img.shields.io/github/languages/top/ppiastucki/recast4j)

![依赖](https://img.shields.io/librariesio/github/ppiastucki/recast4j)

## 2)Recast4j
java版本的Recast and Detour导航网格模型工具集.
![](./recast-demo/screenshot.png?raw=true)

## 3)Recast
```
Recast是一个为游戏设计的导航网格工具集

Recast is...
    自动化 - 向它扔任何关卡几何体，你都会得到一个强大的导航网格  
    快速 - 关卡设计师的周转时间短
    灵活 - 轻松自定义导航网格生成和运行时导航系统以满足您的特定游戏需求
                    
Recast通过多步光栅化过程构建导航网格：
    1. 首先，Recast通过将三角形栅格化为多层高度场来对输入三角形网格进行体素化   
    2. 通过应用简单的体素数据过滤器来删除角色无法移动的区域中的体素
    3. 然后由体素网格描述的可步行区域被划分为二维多边形区域组
    4. 导航多边形是通过对生成的二维多边形区域进行三角测量并将其拼接在一起而生成的. 
```
   
## 4)Detour
```
Recast附带Detour，这是一个路径查找和空间推理工具包。您可以在Detour中使用任何导航网格，但当然，使用Recast生成的数据非常适合。
Retour提供了一个简单的静态导航网格数据表示，适用于许多简单的情况，它还提供了平铺的导航网格表示，允许您在玩家在世界中前进时输入和输出导航数据，并随着世界的变化重新生成导航网格数据的部分。
[关于Recast and Detour的更多信息清参考c++版本的](https://github.com/recastnavigation/recastnavigation)
```

## 5)java版本的导航网格

```
1.如何使用
    API尽可能接近（c++版本）https://github.com/recastnavigation/recastnavigation，因此大多数信息和提示也适用于recast4j，您可以在 测试中找到很多的实例，例如：
        - 从obj文件中构建导航网格
            https://github.com/ppiastucki/recast4j/blob/master/detour/src/test/java/org/recast4j/detour/RecastTestMeshBuilder.java
  
        - 寻路 
            https://github.com/ppiastucki/recast4j/blob/master/detour/src/test/java/org/recast4j/detour/FindPathTest.java#L94
  
        - 保留导航网格 
            https://github.com/ppiastucki/recast4j/blob/master/detour/src/test/java/org/recast4j/detour/io/MeshSetReaderWriterTest.java
  
        - 动态导航网格
            https://github.com/ppiastucki/recast4j/blob/master/detour-dynamic/src/test/java/org/recast4j/dynamic/DynamicNavMeshTest.java
  
2.java版本的增强
    recast
        - 对多线程构建的开箱即用支持
        - 支持光栅化填充体积：球体，胶囊和盒子
  
    detour
        - 找到受环约束的随机点
  
    detour-tile-cache
        - 由于数据结构减少和LZ4更好的压缩，文件格式更加紧凑
 
    detour-extras
        - [用于导入使用A*寻路项目创建的导航网格的简单工具](https://arongranberg.com/astar/)
      
    detour-dynamic
        - 对动态导航网格的强大支持，将预构建的体素预可自由添加和删除的动态对象相结合。  
```

## 6)构建
```
  1.自己从源码构建
        所有模块都可以使用单个gradle命令构建： 
            ./gradlew clean build shadow
        
        构建完成后，可以按如下方式运行 recast-demo应用程序:
            java -jar ./recast-demo/build/libs/recast-demo-1.5.8-SNAPSHOT-all.jar
            
            
  2.直接使用发布版，Recast4j版本可在Maven中央仓库中获取
     Maven版本：   
        <dependency>
	        <groupId>org.recast4j</groupId>
	        <artifactId>recast</artifactId>
	        <version>1.5.7</version>
        </dependency>
        <dependency>
	        <groupId>org.recast4j</groupId>
	        <artifactId>detour</artifactId>
	        <version>1.5.7</version>
        </dependency>
        <dependency>
	        <groupId>org.recast4j</groupId>
	        <artifactId>detour-crowd</artifactId>
	        <version>1.5.7</version>
        </dependency>
        <dependency>
	        <groupId>org.recast4j</groupId>
	        <artifactId>detour-tile-cache</artifactId>
	        <version>1.5.7</version>
        </dependency>
        <dependency>
	        <groupId>org.recast4j</groupId>
	        <artifactId>detour-extras</artifactId>
	        <version>1.5.7</version>
        </dependency>
        <dependency>
	        <groupId>org.recast4j</groupId>
	        <artifactId>detour-dynamic</artifactId>
	        <version>1.5.7</version>
        </dependency>

    Gradle版本：
        implementation 'org.recast4j:recast:1.5.7'
        implementation 'org.recast4j:detour:1.5.7'
        implementation 'org.recast4j:detour-crowd:1.5.7'
        implementation 'org.recast4j:detour-tile-cache:1.5.7'
        implementation 'org.recast4j:detour-extras:1.5.7'
        implementation 'org.recast4j:detour-dynamic:1.5.7'
```

## 7)License
```
Recast和Detour已获得ZLib许可，请参阅License.txt了解更多信息。
```
