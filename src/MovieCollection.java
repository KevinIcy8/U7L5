
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortAlphabetically(ArrayList<String> list){
        Collections.sort(list);
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter an actor/actress's name: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<String> indvCast = new ArrayList<String>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieCast = movies.get(i).getCast();
            movieCast = movieCast.toLowerCase();

            if (movieCast.contains(searchTerm))
            {
                String[] actorList = movieCast.split("\\|");
                for(int j = 0; j < actorList.length; j++){
                    if(actorList[j].contains(searchTerm)){
                        indvCast.add(actorList[j]);
                    }
                }

            }
        }

        // sort the results by title
        sortAlphabetically(indvCast);
        for(int i = 0; i < indvCast.size()-1; i++){
            if(indvCast.get(i).equals(indvCast.get(i+1))){
                indvCast.remove(i+1);
                i--;
            }
        }

        for(int i = 0; i < indvCast.size(); i++){
            String cast = indvCast.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + cast);
        }

        System.out.println("Which cast would like to learn about?");
        System.out.print("Enter number: ");

        int choiceCast = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < movies.size(); i++){
            String movieCast = movies.get(i).getCast();
            movieCast = movieCast.toLowerCase();
            if(movieCast.contains(indvCast.get(choiceCast - 1))){
                int count = 0;
                String[] actorList = movieCast.split("\\|");
                for(int j = 0; j < actorList.length; j++){
                    if(actorList[j].equals(indvCast.get(choiceCast-1))){
                        count++;
                    }
                }
                if(count >= 1){
                    results.add(movies.get(i));
                }
            }
        }

        sortResults(results);


        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeyTerms = movies.get(i).getKeywords();
            movieKeyTerms = movieKeyTerms.toLowerCase();

            if (movieKeyTerms.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String> indvGenre = new ArrayList<String>();
        for(int i = 0; i < movies.size(); i++){
            String[] genres = movies.get(i).getGenres().split("\\|");
            for(int j = 0; j < genres.length; j++){
                indvGenre.add(genres[j]);
            }
        }
        sortAlphabetically(indvGenre);
        for(int i = 0; i < indvGenre.size()-1; i++){
            if(indvGenre.get(i).equals(indvGenre.get(i+1))){
                indvGenre.remove(i+1);
                i--;
            }
        }
        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        for(int i = 0; i < indvGenre.size(); i++){
            String cast = indvGenre.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + cast);
        }

        System.out.println("Which genre would like to learn about?");
        System.out.print("Enter number: ");

        int choiceGenre = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < movies.size(); i++){
            String movieGenre = movies.get(i).getGenres();
            //movieGenre = movieGenre.toLowerCase();
            if(movieGenre.contains(indvGenre.get(choiceGenre - 1))){
               results.add(movies.get(i));
            }
        }

        sortResults(results);


        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        String[] top50Rated = new String[50];
        Double[] ratings = new Double[50];
        for(int j = 0; j < top50Rated.length; j++){
            ratings[j] = movies.get(j).getUserRating();

        }
        Arrays.sort(ratings, Collections.reverseOrder());
        for (int i = 50; i < movies.size(); i++){
            double movieRating = movies.get(i).getUserRating();
            if(ratings[ratings.length-1] < movieRating){
                ratings[ratings.length-1] = movieRating;
                Arrays.sort(ratings, Collections.reverseOrder());
            }
        }
        int index = 0;
        for(int i = 0; i < movies.size(); i++){
            double movieRating = movies.get(i).getUserRating();
            if(index < 50){
                if(ratings[index] == movieRating){
                    top50Rated[index] = movies.get(i).getTitle() + ": " + movies.get(i).getUserRating();
                    index++;
                    if(!ratings[index].equals(ratings[index + 1])){
                        i = 0;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(ratings));
        System.out.println(Arrays.toString(top50Rated));
    }

    private void listHighestRevenue()
    {

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
