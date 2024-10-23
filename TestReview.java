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