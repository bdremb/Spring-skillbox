FROM openjdk:17-oracle

WORKDIR /app

COPY build/libs/spring-students-0.0.3.jar app.jar
COPY build/resources/main/default-students.txt students.txt

CMD ["java", "-jar", "app.jar"]

ENV PROFILE=init
ENV TXT_FILE_PATH=students.txt

# выполнить в терминале
# docker build -t spring-students .

# docker run --rm spring-students