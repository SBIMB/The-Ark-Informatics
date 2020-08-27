FROM mysql:8.0

MAINTAINER George Gooden <gecgooden@gmail.com>

RUN apt-get update && apt-get install -y nano

#ADD start.sh /tmp/start.sh
#RUN ["chmod", "+x", "/tmp/start.sh"]
#RUN /tmp/start.sh

ADD my.cnf /etc/mysql/my.cnf

ENV MYSQL_RANDOM_ROOT_PASSWORD yes

#CMD /tmp/start.sh
