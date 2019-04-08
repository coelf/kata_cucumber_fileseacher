# FileSearcher

This should read all the text files in the given directory, building an in memory representation of the
files and their contents, and then give a command prompt at which interactive searches can be
performed.

## Build
`./gradlew build`

## Test only
`./gradlew test`

### see cucumber console reporting
`./gradlew [clean] test -i`

## Use

`java -jar FileSearcher.jar <directory>`

## assumptions and limitations

- it's index only words, so misspelling are not handle 
- the order of words and the case are only take on account for a 100% match else it's result to a maximum match up to
 90% with the WorkRanker implementation

## More info

[Searcher.feature](./src/test/resources/Searcher.feature) is a gherkin file where I translate my understanding of the feature.
It's here to guide the design of the feature
