FROM openjdk:11
WORKDIR .
COPY target/scala-2.13/anti-wordle-assembly-0.1.jar /
EXPOSE 8080
CMD java -cp anti-wordle-assembly-0.1.jar com.sample.RestBoot