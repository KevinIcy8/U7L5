import java.util.ArrayList;

public class MovieCollectionRunner
{
    public static void main(String arg[])
    {
        MovieCollection myCollection = new MovieCollection("src/MovieData.csv");
        myCollection.menu();
    }
}