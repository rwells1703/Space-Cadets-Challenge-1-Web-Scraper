import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.URL;
import java.net.MalformedURLException;

public class WebName {
    public static void main(String[] args) throws IOException {
        WebName webName = new WebName();
        String username = webName.getUsername();
        String fullName = webName.getFullName(username);

        // Display the full name of the user
        if (fullName == "") {
            System.out.println("No user could be found with that username!");
        } else {
            System.out.println(fullName);
        }
    }

    public String getUsername() throws IOException {
        String username = "";

        // Prompts user to enter a username
        System.out.print("Enter username: ");
        // Creates a reader object to read from the keyboard
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        username = reader.readLine();

        return username;
    }

    // Returns the full name of a southampton uni user from their username
    public String getFullName(String username) throws IOException, MalformedURLException {
        // Generates a URL corresponding to the username of the user
        String urlRaw = "https://www.ecs.soton.ac.uk/people/" + username;
        URL url = new URL(urlRaw);

        // Initialises an object to read HTML data from the web page
        BufferedReader webReader = new BufferedReader(new InputStreamReader(url.openStream()));

        // Defines the pieces of HTML text that mark where the name starts and ends
        String startString = "property=\"name\">";
        String endString = "<em property=\"honorificSuffix\">";

        // An empty string to hold the name of the user
        String fullName = "";

        // Get a line of HTML from the web page
        String line = webReader.readLine();
        while (line != null) {
            // Look for the starting and ending text markers in the current line of
            int nameStartIndex = line.indexOf(startString) + startString.length();
            int nameEndIndex = line.indexOf(endString);

            // If the starting and ending text markers are found on the current line of html...
            if (nameStartIndex != -1 && nameEndIndex != -1) {
                // Extract the name from the line
                fullName = line.substring(nameStartIndex, nameEndIndex);
                // Exit the while loop
                break;
            }

            // Get another line of HTML from the web page
            line = webReader.readLine();
        }

        // Closes the reader object
        webReader.close();

        return fullName;
    }
}