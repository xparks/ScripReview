import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.ArrayList;

public class Quiz{
    Scanner reader;
    String filePath = null;
    Random rand = new Random();
    Integer minimumChapter = null;
    Integer maximumChapter = null;

    public void start() {
        reader = new Scanner(System.in);
        filePath = (filePath == null) ? issuePromptString("Please indicate the book you'd like to review.") : filePath;
        minimumChapter = (minimumChapter == null) ? issuePromptInteger("Please indicate the minimum chapter number you'd like to review.") : minimumChapter;
        maximumChapter = (maximumChapter == null) ? issuePromptInteger("Please indicate the maximum chapter number you'd like to review.") : maximumChapter;
        LinkedHashMap<String, String> scriptureChapterMap = createTextMap(filePath + ".txt", minimumChapter, maximumChapter);
        if (scriptureChapterMap == null) {
            return;
        }

        //take the results and figure out how many there are.
        String scriptureChapter;
        Integer correct = 0;
        Integer total = scriptureChapterMap.size();
        System.out.println("Indicate the chapter in which the following headings are found: "); //adaptive to pluarlity?
        HashMap<String, Integer> incorrectBank = new HashMap<>();
        //cycle through them.
        while (scriptureChapterMap.size() > 0) {
            //determine a random item.
            int  index = rand.nextInt(scriptureChapterMap.size());
            String scriptureChapterHeadingName = scriptureChapterMap.keySet().toArray(new String[scriptureChapterMap.size()])[index];
            //quiz method here.
            scriptureChapter = scriptureChapterMap.get(scriptureChapterHeadingName).toString();
            scriptureChapter = (scriptureChapter == null) ? null : scriptureChapter.trim();
            if (issuePromptStringCompare(scriptureChapterHeadingName, scriptureChapter)) {
                System.out.println("Correct");
                correct++;
                scriptureChapterMap.remove(scriptureChapterHeadingName);
            } else {
                System.out.println("False.  The correct chapter is " + scriptureChapter);
                total++;
                String failureMessage = scriptureChapterHeadingName + " -> " + scriptureChapter;
                if (incorrectBank.get(failureMessage) == null) {
                    incorrectBank.put(failureMessage, 1);
                } else {
                    incorrectBank.put(failureMessage, incorrectBank.get(failureMessage) + 1);
                }
            }
        }


        System.out.println(correct + "/" + total + " " + resolveScoreSlogan(correct, total));
        if (incorrectBank.size() > 0 ) {
            System.out.println("Here are the ones you missed: ");
            for (String incorrectResponse : incorrectBank.keySet()) {
                System.out.println(incorrectResponse + ".  Missed: " + incorrectBank.get(incorrectResponse));
            }
            //add a functionality that tracks the user stats.  Probably need a run counter that determines the run as well as stats concerning the run.
        }

        //add a replay functionality.
        if (issuePromptString("Would you like to play again? (Y/N) ").compareToIgnoreCase("Y") == 0) {
            correct = 0;
            total = 0;
            start();
        }

    }

    /**
     * Takes in the chapter headings and attmepts to add them to an object as it will help with management of the map and accessing random elements.
     * @param filePath
     * @param minimumChapter
     * @param maximumChapter
     * @return
     */
    private ArrayList<ChapterHeading> loadChapterHeadings(String filePath, int minimumChapter, int maximumChapter) {
        ArrayList<ChapterHeading> headings = new ArrayList<>();
        File file = new File (filePath);
        StringBuilder line = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader (new FileReader(file));
            String text = null;
            while ((text = reader.readLine()) != null) {
                String[] textSplit = text.split(",");
                if (textSplit == null || textSplit.length != 2) {
                    System.out.println("Found invalid text: " + text);
                } else if (Integer.parseInt(textSplit[1].trim()) >= minimumChapter && Integer.parseInt(textSplit[1].trim()) <= maximumChapter) {
                    ChapterHeading heading = new ChapterHeading(textSplit[0], Integer.parseInt(textSplit[1].trim()));
                    headings.add(heading);
                } else {
                    //chapter found lies outside of the boundaries.
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
        return headings;
    }

    private LinkedHashMap<String, String> createTextMap(String filePath, int minimumChapter, int maximumChapter) {
        LinkedHashMap<String, String> textMap = new LinkedHashMap<String, String>();
        File file = new File (filePath);
        StringBuilder line = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader (new FileReader(file));
            String text = null;
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