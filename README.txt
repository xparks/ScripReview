Hello,

Thanks for taking a look at ScripReview.  This program was made to assist people with remembering the different chapter headings in the books of the bible.  I hope that you'll find a lot of value in this simple tool.

===================
Getting Started:
===================

Pull the code.
Ensure that you have a java runtime environment.  You'll know right away whether or not you have a "JRE" if you try to run the following command (without the dollar sign for the less technical):

$ javac ScripReview.java

The screen should prompt you to download the latest JRE if you don't have it.

Once you've compiled the java file, simply run the following command: 

$ java ScripReview

====================
Playing the Game
====================
The game sources the chapter headings and chapter number from a .txt file.  The code is not super smart.  It decides which file to reference by taking the Book name you provide and appending the .txt suffix.

The game will prompt you for a minimum and maximum chapter number.  This is immensely useful in the event in which you only want to review a couple of chapters.

The prompt will ask you to indicate the chapter in which a heading appears.  Reply with a number indicating the chapter, press return or enter on the keyboard, and it will indicate whether you were correct or not.  If you incorrectly responded, the program will ask you for the chapter again (later on).

I hope that you enjoy playing the game.  Feel free to contribute or drop me a message if you have a feature that you'd like to have implemented.  Some of the preliminary features I'm thinking about are features that store statistics of how well we remember certain chapters, headings, etc.

Thanks,
xparks
