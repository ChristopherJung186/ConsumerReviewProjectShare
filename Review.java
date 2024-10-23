import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
/*
import java.util.Random;
import java.io.*;*/

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  private static final String SPACE = " ";
  
  static{
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      //System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      //System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      //System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      //System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }
  
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }
  
  /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
  
 /* Takes an online review and returns the total sentiment value of that review. */
  public static double totalSentiment(String fileName)
  {
      double total = 0;
      String word = "";
      String reviewText = textToString(fileName);

      // moves through the entire review
      for (int i = 0; i < reviewText.length(); i++)
      {
         // checks if a word has been completed
        if (reviewText.substring(i, i+1).equals(" ") || i + 1 == reviewText.length())
         {
            total += sentimentVal(removePunctuation(word));
            word = "";
         }
         else
         {
            word += reviewText.substring(i, i+1);
         }
      }
      return total;
  }

    
  public static double starRating(String fileName)
   {
     double sentiment = totalSentiment(fileName);
     double rating;

     if (sentiment < 0)
     {
       rating = 1;
     }
     else if (sentiment < 3)
     {
       rating = 2.897;
     }
     else if (sentiment < 6)
     {
       rating = 3;
     }
     else if (sentiment < 30)
     {
       rating = 4;
     }
     else
     {
       rating = 5;
     }
     return rating;
  }
  
  /* Returns a computer generated fake online review. 
  Precondition: fileName must be a .txt file. */
    public static String fakeReview(String fileName)
    {
      String word = "";
      String reviewText = textToString(fileName);
      String newReview = "";
      
      // goes through the entire review
      for (int i = 0; i < reviewText.length(); i++)
      {
          if (reviewText.substring(i, i+1).equals(" ") || i == reviewText.length() -1)
          {
              if (i == reviewText.length() -1) //adds last letter to the review
              {
                  word += reviewText.substring(i, i+1);
              }
              
              // finds the adjectives that start with * and changes them
              if (word.startsWith("*"))
              {
                  String newAdjective = "";
                  while (newAdjective.equals(""))
                  {
                      newAdjective = randomAdjective();
                  }
                  // replaces the old adjective with the new and resets word
                  newReview += newAdjective + getPunctuation(word) + " ";
                  word = "";
              }
              else
              {
                  newReview += word + " ";
                  word = "";
              }
          }
          else
          {
              word += reviewText.substring(i, i+1);
          }
      }
      return newReview;
    }

/* Returns a computer generated online review that can be either positive or negative. 
Precondition: fileName must be a .txt file. */
    public static String fakeReviewStronger(String fileName)
    {
        String word = "";
        String reviewText = textToString(fileName);
        String newReview = "";
        
        // goes through the entire review
        for (int i = 0; i < reviewText.length(); i++)
        {
             if (reviewText.substring(i, i+1).equals(" ") || i == reviewText.length() -1)
             {
                   if (i == reviewText.length() -1) //adds last letter to the review
                   {
                      word += reviewText.substring(i, i+1);
                   }
                 
                 // finds the adjectives that start with *
                 if (word.startsWith("*"))
                 {
                     // gets the sentiment value of the word and replaces it with a positive or negative adjective
                     double sentiment = sentimentVal(word);
                     String newAdjective = "";
                     
                     if (sentiment > 0)
                     {
                         while (newAdjective.equals("") || sentimentVal(newAdjective) <= sentiment)
                         {
                           newAdjective = randomPositiveAdj();
                         }
                     }
                     else if (sentiment < 0)
                     {
                         while (newAdjective.equals("") || sentimentVal(newAdjective) >= sentiment)
                         {
                           newAdjective = randomNegativeAdj();
                         }
                     }
                     else
                     {
                        word = word.substring(1);
                        newAdjective = removePunctuation(word);
                     }
                     
                     //replaces the old adjective with the new adjective
                     newReview += newAdjective + getPunctuation(word) + " ";
                     word = "";
                 }
                 else
                 {
                     newReview += word + " ";
                     word = "";
                 }
               }
               else
               {
                  word += reviewText.substring(i, i+1);
               }

        }
        return newReview;
    }
}

public static void main(String[] args) {
  // Initialize total sentiment score
  double totalSentiment = 0.0;

  // Loop through review files from 1 to 6
  for (int i = 1; i <= 6; i++) {
      // Convert i to a string and add .txt extension to create the filename
      String file = Integer.toString(i) + ".txt";

      // Get sentiment value for each file using some function
      double reviewSentiment = analyzeSentimentValues(file);

      // Format review sentiment value to 3 decimal places for output
      String stringReviewSentiment = String.format("%.3f", reviewSentiment);

      // Print the review number and its sentiment value
      System.out.println("Review " + Integer.valueOf(i) + " has a value of " + stringReviewSentiment);

      // Add each review's sentiment to the total sentiment score
      totalSentiment += reviewSentiment;
  }

  // Calculate average sentiment over the 6 reviews
  totalSentiment /= 6.0;

  // Format the total (average) sentiment to 3 decimal places
  String stringSentiment = String.format("%.3f", totalSentiment);

  // Print the average sentiment value
  System.out.println("The average rating for whether TikTok should be banned is " + stringSentiment);

  // If the average sentiment is negative, print that TikTok should be banned
  if (totalSentiment < 0) {
      System.out.println("Tiktok should be banned");
  } else {
      // Otherwise, print that TikTok shouldn't be banned
      System.out.println("TikTok shouldn't be banned");
  }
}

public static double analyzeSentimentValues(String filename) {
  // Read file contents as text using Review.textToString
  String fileContents = Review.textToString("./ConsumerLab_Code/" + filename);
  
  // Flags to control the processing of asterisks and spaces
  boolean hasAsterisk = true;
  boolean hasSpace = true;
  
  // Initialize sentiment value accumulator
  double sentimentValue = 0.0;
  
  // Process the file contents to find and analyze adjectives marked with an asterisk
  while (hasAsterisk) {
      // Find the next asterisk in the file contents
      int index = fileContents.indexOf("*");
      int endIndex = index + 1;
      
      // If no asterisk is found, exit the loop
      if (index == -1) {
          hasAsterisk = false;
      } else {
          // Process spaces and whitespace characters following the asterisk
          while (hasSpace && (endIndex < fileContents.length())) {
              // Check if the current character is a space
              if (Character.isWhitespace(fileContents.charAt(endIndex))) {
                  hasSpace = true;
                  endIndex++;
              } else {
                  hasSpace = false;
              }
          }
          
          // Extract the adjective between the asterisk and the endIndex
          String adjective = fileContents.substring(index + 1, endIndex);
          
          // Clean up punctuation from the adjective using Review.removePunctuation
          adjective = Review.removePunctuation(adjective);
          
          // Add the sentiment value for this adjective to the total sentiment value
          sentimentValue += Review.sentimentVal(adjective);
          
          // Remove the processed part of the file content and continue the loop
          fileContents = fileContents.substring(0, index) + " " + fileContents.substring(index + 1);
          hasSpace = false;
      }
  }
  
  // Return the total sentiment value for the file
  return sentimentValue;
}

String stringReviewSentiment = String.format("%.3f", reviewSentiment);
System.out.println("Review " + Integer.valueOf(i) + " has a sentiment value of " + stringReviewSentiment);
totalSentiment += reviewSentiment;

totalSentiment /= 6.0;
String stringSentiment = String.format("%.3f", totalSentiment);
System.out.println("The average rating for whether TikTok should be banned is " + stringSentiment);

String adjective = filecontents.substring(index+1, endIndex);
adjective = Review.removePunctuation(adjective)
sentimentValue += Review.sentimentVal(adjective);

fileContents = fileContents.substring(0, index) + " " + fileContents.substring(index + 1);
hasSpace = false;

while (!hasSpace && (endIndex < fileContents.length())) {
  if (Charcter.isWhitespace(fileContents.charAt(endIndex))) {
    hasSpace = true;
  } else {
    endIndex++;
  }
}

while (!hasSpace) {
  if (endIndex < fileContents.length()) {
    if (Charcter.isWhitespace(fileContents.charAt(endIndex))) {
      hasSpace = true;
    } else {
      endIndex++;
    }
  }
}

int i = 1;
while (i <= 6) {
  String file = Inter.toString(i) + ".txt";
  double reviewSentiment = analyzeSentimentValues(file);
  String stringReviewSentiment = String.format("%.3f", reviewSentiment);
  System.out.println("Review " + Interget.valueOf(i) + " has value of " + stringReviewSentiment);
  totalSentiment += reviewSentiment;
  i++
}