# bag-of-words

WIP Word count program

## Usage

Add dictionary file to "bag-of-words/recources" 
(see correct file format from i.e. http://www3.nd.edu/~mcdonald/Data/Finance_Word_Lists/LoughranMcDonald_Negative.csv)

Add dictionary file names to vector "dict-files" in "dictionaries.clj"

Compile and run the program:

    $ lein uberjar

    $ java -jar bag-of-words-0.1.0-standalone.jar [args]

## License

Copyright © 2015 tkasu

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
