/**
 *
 */
package edu.buffalo.cse.irf14.document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pradeep
 * Class that parses a given file into a Document
 */
public class Parser {

    private static Document document;

    /**
     * Static method to parse the given file into the Document object
     *
     * @param filename : The fully qualified filename to be parsed
     * @return The parsed and fully loaded Document object
     * @throws ParserException In case any error occurs during parsing
     */
    public static Document parse(String filename) throws ParserException {

        if (filename == null || filename.equals("")) {
            throw new ParserException("Cannot parse a null/empty filename");
        }

        // Initialize
        document = new Document();
        String content = "";
        List<String> fileContents;

        // Get file path
        Path filePath = Paths.get(filename);

        // Read fileContents
        try (Stream<String> stream = Files.lines(filePath)) {

            fileContents = stream
                    .map(line -> line.trim())
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());

            // fileContents.forEach(System.out::println);

        } catch (IOException e) {
            throw new ParserException(String.format("Cannot open filename [%s]", filename), e);
        }

        // Setting fileId
        document.setFileId(filePath.getFileName().toString());

        // Setting Category
        document.setCategory(filePath.getParent().getFileName().toString());

        boolean isTitle = true, isMetadata = true, hasAuthor = true;

        for (String line : fileContents) {

            // Setting Title
            if (isTitle) {
                document.setTitle(line);
                isTitle = false;

            } else {

                if (hasAuthor && populateAuthorIfPresent(line)) {

                    // set author if present. return false if not
                    hasAuthor = false;

                } else if (isMetadata) {
                    // Setting place and date
                    populatePlaceAndDate(line);
                    isMetadata = false;
                    content += line;

                } else {

                    content += line;
                }

            }
        }

        // Setting content
        document.setContent(content);


        return document;
    }

    private static boolean populateAuthorIfPresent(String line) {

        Pattern pattern = Pattern.compile("<AUTHOR>(.+?)</AUTHOR>");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String[] authorAndOrg = matcher.group(1).split(",");
            document.setAuthor(authorAndOrg[0].trim().replace("By", "").replace("BY", "").trim());
            document.setAuthorOrg(authorAndOrg[1].trim());
            return true;
        }

        return false;
    }

    private static void populatePlaceAndDate(String line) {
        String[] metadata = line
                .split("-")[0]
                .split(",");

        if (metadata.length == 3) {
            // Contains City, State and Date
            document.setPlace(metadata[0].trim() + ", " + metadata[1].trim());
            document.setNewsDate(metadata[2].trim());

        } else {
            // Contains City and Date
            document.setPlace(metadata[0]);
            document.setNewsDate(metadata[1].trim());

        }
    }
}
