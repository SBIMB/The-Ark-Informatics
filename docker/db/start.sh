#!/bin/bash

if [ ! -d /var/lib/mysql/mysql-files ]; then
	echo "creating /var/lib/mysql/mysql-files"
    mkdir -p /var/lib/mysql/mysql-files
    echo "creating /var/lib/mysql/mysql-files ...done"
    
    echo "changing file permissions"
    chown -R mysql:mysql /var/lib/mysql/mysql-files
    echo "changing file permissions ...done"
fi

if [ ! -d /var/log/mysql ]; then
    echo "creating /var/log/mysql"
    mkdir -p /var/log/mysql
    echo "creating /var/log/mysql ...done"
fi
    
if [ ! -f /var/log/mysql/error.log ]; then
    echo "creating error.log in /var/log/mysql"
    touch /var/log/mysql/error.log
    echo "creating error.log in /var/log/mysql ...done"
fi

exec mysqld

