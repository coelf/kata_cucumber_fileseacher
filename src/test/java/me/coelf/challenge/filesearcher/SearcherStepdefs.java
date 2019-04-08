package me.coelf.challenge.filesearcher;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import me.coelf.challenge.filesearcher.adapters.InMemoryFilesIndex;
import me.coelf.challenge.filesearcher.adapters.InMemoryFilesRepository;
import me.coelf.challenge.filesearcher.adapters.WordRanker;
import me.coelf.challenge.filesearcher.domain.File;
import me.coelf.challenge.filesearcher.domain.FilesRepository;
import me.coelf.challenge.filesearcher.domain.RankedFile;
import me.coelf.challenge.filesearcher.usecase.ExitException;
import me.coelf.challenge.filesearcher.usecase.FileSearcher;
import me.coelf.challenge.filesearcher.utils.Prettier;

public class SearcherStepdefs implements En
{
    private boolean          exit = false;
    private String           readPrint;
    private List<RankedFile> filesMatch;
    private List<File>       files;
    private FilesRepository  filesRepository;
    private FileSearcher     fileSearcher;

    public SearcherStepdefs()
    {
        Given("^Files in directory \"([^\"]*)\" exist$", (String directoryName, DataTable dataTable) -> {
            List<Map<String, String>> dataMap = dataTable.asMaps(String.class, String.class);
            files = dataMap.stream()
                    .filter(map -> map.get("path").equals(directoryName))
                    .map(map -> new File(map.get("filename"), map.get("content")))
                    .collect(Collectors.toList());
            assertThat(files.size(), equalTo(14));
        });
        Given("^directory \"([^\"]*)\" accessible$", (String directoryName) -> {
            filesRepository = new InMemoryFilesRepository(directoryName, files);
            fileSearcher = new FileSearcher(filesRepository, new InMemoryFilesIndex(), new WordRanker());
            assertNotNull(files);
            assertNotNull(fileSearcher);

        });
        When("^the directory \"([^\"]*)\" is read$", (String directoryName) -> {
            readPrint = fileSearcher.read(directoryName);
            assertNotNull(readPrint);
        });
        Then("^print (\\d+) files read in directory \"([^\"]*)\"$", (Integer nbFiles, String directoryName) -> {
            assertThat(readPrint, equalTo(nbFiles + " file(s) read in directory \"" + directoryName + "\""));
        });
        And("^searching sentence \"([^\"]*)\"$", (String search) -> {
            filesMatch = fileSearcher.search(search);
            assertNotNull(filesMatch);

        });
        Then("^print matching files$", () -> {
            assertThat(Prettier.print(filesMatch), equalTo(String.join("\n",
                    Arrays.asList("file1.txt : 100%", "file2.txt : 90%"))));
        });
        And("^\"([^\"]*)\" is entered$", (String quit) -> {
            try
            {
                fileSearcher.search(quit);
                fail();
            }
            catch (ExitException e)
            {
                exit = true;
            }

        });
        Then("^Exit the Application$", () -> {
            assertTrue(exit);
        });

    }
}
