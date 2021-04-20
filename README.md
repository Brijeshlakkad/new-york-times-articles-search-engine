## Create role with username and password:
> CREATE ROLE mas_root_user with SUPERUSER = true AND LOGIN = true and PASSWORD = 'yz1af4va79';

## Login using username and password:
> cqlsh -u mas_root_user -p yz1af4va79

## Create keyspace:
> create keyspace newyorktimesarticles WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : 2};