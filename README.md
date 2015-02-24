# bag-of-words

WIP Word count program

## Usage

Currently data is imported to SQL-database from REPL:

    $ lein repl

To add data to database, see:

    $ core/insert-release-data-to-db [release-data]

    $ core/insert-grouped-dict-data-to-db [dict-name group-data]

To import data from .csv, see files.clj

For sample data-sets, see resources/

For data-analysis, see sql/queries.sql

## License

Copyright © 2015 tkasu

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
