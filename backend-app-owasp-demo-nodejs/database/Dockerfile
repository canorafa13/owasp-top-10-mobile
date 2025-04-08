FROM mysql/mysql-server:5.7

ADD setup.sql /docker-entrypoint-initdb.d

CMD ["--character-set-server=utf8mb4", "--collation-server=utf8mb4_general_ci", "--skip-character-set-client-handshake"]