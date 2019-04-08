package me.coelf.challenge.filesearcher.main;

import java.util.List;
import java.util.Scanner;

import me.coelf.challenge.filesearcher.adapters.FileSystemRepository;
import me.coelf.challenge.filesearcher.adapters.InMemoryFilesIndex;
import me.coelf.challenge.filesearcher.adapters.WordRanker;
import me.coelf.challenge.filesearcher.domain.RankedFile;
import me.coelf.challenge.filesearcher.usecase.ExitException;
import me.coelf.challenge.filesearcher.usecase.FileSearcher;
import me.coelf.challenge.filesearcher.usecase.NoMatchesFoundException;
import me.coelf.challenge.filesearcher.usecase.PathException;
import me.coelf.challenge.filesearcher.utils.Prettier;

public class MainSearcher
{
    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            throw new IllegalArgumentException("No directory given to index");
        }

        FileSearcher fileSearcher = new FileSearcher(
                new FileSystemRepository(),
                new InMemoryFilesIndex(),
                new WordRanker());

        try
        {
            Scanner keyboard = new Scanner(System.in);
            fileSearcher.read(args[0]);
            while (true)
            {
                try
                {
                    System.out.println("Search>");
                    List<RankedFile> result = fileSearcher.search(keyboard.nextLine());
                    System.out.println(Prettier.print(result));
                }
                catch (NoMatchesFoundException e)
                {
                    System.out.println(e.getMessage());
                }

            }
        }
        catch (PathException e)
        {
            e.printStackTrace();
            System.err.println("exit the application");
        }
        catch (ExitException e)
        {
            System.out.println("bye!");
        }

    }
}
