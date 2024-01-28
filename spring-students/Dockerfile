FROM openjdk:17-oracle

WORKDIR /app

COPY build/libs/spring-students-0.0.3.jar app.jar
COPY build/resources/main/default-students.txt students.txt

CMD ["java", "-jar", "app.jar"]

ENV PROFILE=init
ENV TXT_FILE_PATH=students.txt
ENV SOCKET_SERVER_PORT=8888

# выполнить в терминале
# docker build -t spring-students .

# для работы терминала в docker команда должна быть такая
# docker run -it -p 8888:8888 spring-students
# или  docker run -it --rm  spring-students