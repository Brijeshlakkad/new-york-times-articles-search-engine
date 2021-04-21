## Create role with username and password:
> CREATE ROLE mas_root_user with SUPERUSER = true AND LOGIN = true and PASSWORD = 'yz1af4va79';

## Login using username and password:
> cqlsh -u mas_root_user -p yz1af4va79

## Create keyspace:
> create keyspace newyorktimesarticles WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : 2};

## Load data into db using DSBulk
> dsbulk load -k newyorktimesarticles -t article -url [filename] --schema.mapping "year = year, sentence = sentence, now() = articleid" -h [hostnames]
#### Example
> dsbulk load -k newyorktimesarticles -t article -url df_1920.csv --schema.mapping "year = year, sentence = sentence, now() = articleid" -h '54.243.172.59'