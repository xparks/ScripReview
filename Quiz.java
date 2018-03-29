import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class Quiz{
    Scanner reader;

    public void start() {
        reader = new Scanner(System.in);
        String filePath = issuePromptString("Please indicate the book you'd like to review.");
        LinkedHashMap<String, String> scriptureChapterMap = createTextMap(filePath + ".txt");
        if (scriptureChapterMap == null) {
            return;
        }

        //take the results and figure out how many there are.
        Random rand = new Random();
        String scriptureChapter;
        Integer correct = 0;
        Integer total = scriptureChapterMap.size();
        String startQuiz = issuePromptString("Would you like to play a game? (Y/N) ");
        System.out.println("Indicate the chapter in which the following headings are found: "); //adaptive to pluarlity?
        ArrayList<String> incorrectBank = new ArrayList<>();
        //cycle through them.
        while (scriptureChapterMap.size() > 0) {
            //determine a random item.
            int  index = rand.nextInt(scriptureChapterMap.size());
            String scriptureChapterHeadingName = scriptureChapterMap.keySet().toArray(new String[scriptureChapterMap.size()])[index];
            //quiz method here.
            if (startQuiz.compareToIgnoreCase("Y") == 0) {
                scriptureChapter = scriptureChapterMap.get(scriptureChapterHeadingName).toString();
                scriptureChapter = (scriptureChapter == null) ? null : scriptureChapter.trim();
                if (issuePromptStringCompare(scriptureChapterHeadingName, scriptureChapter)) {
                    System.out.println("Correct");
                    correct++;
                } else {
                    System.out.println("False.  The correct chapter is " + scriptureChapter);
                    incorrectBank.add(scriptureChapterHeadingName + " -> " + scriptureChapter);
                }
            } else {
                System.out.println("Quitting program.");
                break;
            }
            scriptureChapterMap.remove(scriptureChapterHeadingName);
        }

        System.out.println(correct + "/" + total + " " + resolveScoreSlogan(correct, total));
        System.out.println("Here are the ones you missed: ");
        for (String incorrectResponse : incorrectBank) {
            System.out.println(incorrectResponse);
        }
    }


    private LinkedHashMap<String, String> createTextMap(String filePath) {
        LinkedHashMap<String, String> textMap = new LinkedHashMap<String, String>();
        File file = new File (filePath);
        StringBuilder line = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader (new FileReader(file));
            String text = null;
            int minimumChapter = issuePromptInteger("Please indicate the minimum chapter number you'd like to review.");
            int maximumChapter = issuePromptInteger("Please indicate the maximum chapter number you'd like to review.");
            //need to put in some sort of validation so that this is okay/safe.

            while ((text = reader.readLine()) != null) {
                String[] textSplit = text.split(",");
                if (textSplit == null || textSplit.length != 2) {
                    System.out.println("Found invalid text: " + text);
                } else if (Integer.parseInt(textSplit[1].trim()) >= minimumChapter && Integer.parseInt(textSplit[1].trim()) <= maximumChapter) {
                    textMap.put(textSplit[0], textSplit[1].trim());
                } else {
                    //do nothing.
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find the file.");
            return null;
        } catch (IOException e) {
            System.out.println("Generic issue detected.");
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return textMap;
    }

    //put the options in a database in the future as it is clugey here.
    private String resolveScoreSlogan(Integer correct, Integer total) {
        Double score = correct.doubleValue()/total.doubleValue();
        if (score > .99) {
            return "Great job!  Feel free to move on to the next difficulty level.";
        } else if (score > .9) {
            return "Good job!  Let's do that one more time.";
        } else if (score > .8) {
            return "Keep practicing!";
        } else if (score > .5) {
            return "You're on the way up.";
        } else {
            return "Let your score speak to you.";
        }
    }


    private String issuePromptString(String prompt) {
        //gets the file path
        System.out.println(prompt);
        String userInput = reader.next();
        return userInput;
    }

    private Integer issuePromptInteger(String prompt) {
        //gets the file path
        System.out.println(prompt);
        Integer userInput = reader.nextInt();
        return userInput;
    }

    private Boolean issuePromptStringCompare(String prompt, String comparitor) {
        //gets the file path
        System.out.println(prompt);
        String userInput = reader.next();
        if (userInput.compareTo(comparitor) == 0) {
            return true;
        } else {
            return false;
        }
    }
}