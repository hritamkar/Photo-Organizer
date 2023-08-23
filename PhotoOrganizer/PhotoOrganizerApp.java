/*
Created by : Hritam
Objective : a utility to automatically organize your photos by sorting them into directories named according to their creation date.
Date Created : 19-08-2023
Last Modified : 19-08-2023
*/

package PhotoOrganizer;

import java.util.Scanner;

public class PhotoOrganizerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Source Address: ");
        String sourcePath = sc.nextLine();

        PhotoOrganizer ob = new PhotoOrganizer(sourcePath);
        ob.rearrange();
    }
}
