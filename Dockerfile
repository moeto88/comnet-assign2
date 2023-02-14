FROM ubuntu
WORKDIR /compnets
RUN apt-get update --fix-missing
RUN apt-get install -y net-tools netcat tcpdump inetutils-ping openjdk-18-jdk
CMD ["/bin/bash"]
