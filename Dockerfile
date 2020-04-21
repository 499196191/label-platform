#基于openjdk8构建
FROM openjdk:8
VOLUME /apps/label
#作者
MAINTAINER Marty
ENV PROFILE prod
# 复制 uild/libs/imageq-label-0.1.0-SNAPSHOT.jar 至 app.jar
COPY imageq-label-1.0-SNAPSHOT.jar app.jar
#类似于 CMD 指令，但其不会被 docker run 的命令行参数指定的指令所覆盖
#ENTRYPOINT ["<executeable>","<param1>","<param2>",...]
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8089","-jar","/app.jar","--spring.profiles.active=${PROFILE}"]
EXPOSE 8081
