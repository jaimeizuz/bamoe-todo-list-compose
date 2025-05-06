CREATE ROLE "kogito-user" WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    NOREPLICATION
    PASSWORD 'kogito-pass';

CREATE DATABASE kogito
    WITH
    OWNER = "kogito-user"
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE DATABASE "xa-object-store"
    WITH
    OWNER = "kogito-user"
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL PRIVILEGES ON DATABASE postgres TO "kogito-user";
GRANT ALL PRIVILEGES ON DATABASE kogito TO "kogito-user";
GRANT ALL PRIVILEGES ON DATABASE kogito TO postgres;
GRANT ALL PRIVILEGES ON DATABASE "xa-object-store" TO "kogito-user";
GRANT ALL PRIVILEGES ON DATABASE "xa-object-store" TO postgres;

CREATE ROLE "grafana-user" WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    NOREPLICATION
    PASSWORD 'grafana-pass';

CREATE DATABASE grafana
    WITH
    OWNER = "grafana-user"
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;