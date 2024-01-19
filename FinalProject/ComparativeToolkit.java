package FinalProject;

public class ComparativeToolkit {
    //object containing methods that compare multiple sequences

    //Method that runs Smith-Waterman procedure to compare two sequences
    public static double performAlignment(String sequence1, String sequence2, int matchscore, int mismatchscore, int gappenalty) {
        int[][] scoreMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];

        // Initialize the top row and leftmost column to 0
        for (int i = 0; i <= sequence1.length(); i++) {
            scoreMatrix[i][0] = 0;
        }
        for (int j = 0; j <= sequence2.length(); j++) {
            scoreMatrix[0][j] = 0;
        }

        int maxScore = 0;

        // Fill the score matrix
        for (int i = 1; i <= sequence1.length(); i++) {
            for (int j = 1; j <= sequence2.length(); j++) {
                int match = scoreMatrix[i - 1][j - 1] + (sequence1.charAt(i - 1) == sequence2.charAt(j - 1) ? matchscore : mismatchscore);
                int delete = scoreMatrix[i - 1][j] + gappenalty;
                int insert = scoreMatrix[i][j - 1] + gappenalty;
                scoreMatrix[i][j] = Math.max(0, Math.max(Math.max(match, delete), insert));

                if (scoreMatrix[i][j] > maxScore) {
                    maxScore = scoreMatrix[i][j];
                }
            }
        }
        return maxScore;
    }

}
