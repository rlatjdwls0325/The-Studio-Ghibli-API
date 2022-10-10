import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class App {

    public static void main(String[] args) {
        
        boolean status = true;

        try 
        {
                URL url = new URL("https://ghibliapi.herokuapp.com/films/");
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
    
                httpConn.setRequestMethod("GET");
    
                BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
                    
                    System.out.println(httpConn.getResponseCode());
                    System.out.println(httpConn.getResponseMessage());
                    
                    JsonParser movieParser = new JsonParser();
                    JsonArray movieArray = (JsonArray) movieParser.parse(in);

                    int movieCount = 0;

                    for (JsonElement movie: movieArray)
                    {
                        movieCount++;
                        System.out.println("Movie number " + movieCount + ".");
                        System.out.println("Movie Name: " + movie.getAsJsonObject().get("title").getAsString());
                        System.out.println("Movie Description: " + movie.getAsJsonObject().get("description").getAsString());
                        System.out.println();
                    }

                    while (status == true)
                    {
                        Scanner scan = new Scanner(System.in);

                        System.out.println("Please select a movie by typing movie name " + "(type \"quit\" to close the program)");
                        String input = scan.nextLine();
                        System.out.println();

                        if (input.equalsIgnoreCase("quit"))
                        {
                            System.out.println("System closes...");
                            status = false;
                        }
                        else
                        {
                            boolean found = false;
                            
                            for (JsonElement movie: movieArray)
                            {
                                String movieTitle = movie.getAsJsonObject().get("title").getAsString();
                                String movieDescription = movie.getAsJsonObject().get("description").getAsString();
                                String movieReleaseDate = movie.getAsJsonObject().get("release_date").getAsString();
                                String movieProducer = movie.getAsJsonObject().get("producer").getAsString();

                                if (input.equalsIgnoreCase(movieTitle))
                                {
                                    System.out.println("- Information about your selected movie:");
                                    System.out.println("Movie Name: " + movieTitle);
                                    System.out.println("Movie Description: " + movieDescription);
                                    System.out.println("Movie Release Date: " + movieReleaseDate);
                                    System.out.println("Movie Producer: " + movieProducer);
                                    System.out.println();
                                        
                                    found = true;
                                }
                            }
                            if (found == false)
                            {
                                System.out.println("Movie not found...");
                                System.out.println();
                            }
                        }
                    }
        } 
        catch (Exception e) 
        {
            System.out.println("Opps.");
            e.printStackTrace();
        }
    }
}