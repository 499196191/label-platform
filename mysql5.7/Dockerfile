FROM mysql:5.7.19
#将所需文件放到容器中(将会自动执行)
COPY ./imageq-mind.sql /docker-entrypoint-initdb.d
COPY ./xxl-job.sql /docker-entrypoint-initdb.d