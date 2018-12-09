# bag-of-words

Implements a simple bag-of-words sentiment analysis toolkit and data scraper to analyze e.g. financial statements and company disclosures.

Functionalities:

* Loads static web-pages based on provided links
* Cleans data from non-word characters and splits text to list of words
* Saves data to a database that is designed for a simple bag of words analysis
* sql/queries includes analytical queries that can be used to analyze the data based on given dictionaries

The program was used in the master thesis to analyze NASDAQ OMXH financial disclosures.

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
