# define base docker image
FROM gcr.io/distroless/java17-debian12

WORKDIR /app

COPY target/student-management-system-0.0.1-SNAPSHOT.jar /app/student-management-system.jar

ENTRYPOINT [ "java"]

CMD ["-jar","student-management-system.jar","0.0.0.0:8084"]
