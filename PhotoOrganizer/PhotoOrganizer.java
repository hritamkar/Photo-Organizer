/*
Created by : Hritam
Objective : a utility to automatically organize your photos by sorting them into directories named according to their creation date.
Date Created : 19-08-2023
Last Modified : 19-08-2023
*/

package PhotoOrganizer;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoOrganizer {
    private final String sourcePath;

    public PhotoOrganizer(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    /*
    method objective: method to get all pictures, create new folder if not already and move the pictures into their rightful folders
    input: empty
    return: nothing
    */
    public void rearrange() {
        try {
            Path sourceDirectory = Paths.get(sourcePath);

            DirectoryStream.Filter<Path> filter = entry -> {
                String fileName = entry.getFileName().toString().toLowerCase();
                return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
            };

            boolean filesMoved = false;

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDirectory, filter)) {
                for (Path pictureFile : stream) {
                    BasicFileAttributes attributes = Files.readAttributes(pictureFile, BasicFileAttributes.class);
                    FileTime lastModifiedTime = attributes.lastModifiedTime();
                    Date dateModified = new Date(lastModifiedTime.toMillis());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = dateFormat.format(dateModified);

                    Path destinationFolder = sourceDirectory.resolve(formattedDate);
                    if (!Files.exists(destinationFolder)) {
                        Files.createDirectory(destinationFolder);
                    }

                    Path destinationFile = destinationFolder.resolve(pictureFile.getFileName());
                    Files.move(pictureFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);

                    filesMoved = true;
                    System.out.println("Moved: " + pictureFile.getFileName() + " to: " + destinationFolder);
                }
            }

            if (!filesMoved) {
                System.out.println("No picture files found in the source directory.");
            } else
                System.out.print("\nPictures moved successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
