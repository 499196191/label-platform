#基于openjdk8构建
FROM openjdk:8-alpine
#作者
MAINTAINER Marty
# 复制 uild/libs/imageq-label-0.1.0-SNAPSHOT.jar 至 app.jar
COPY build/libs/imageq-label-0.1.0-SNAPSHOT.jar app.jar
#类似于 CMD 指令，但其不会被 docker run 的命令行参数指定的指令所覆盖
#ENTRYPOINT ["<executeable>","<param1>","<param2>",...]
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8081
