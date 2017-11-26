# Features

### Efficiency Features (clever processes to improve program efficiency)
* Sessions 

### Cases (clever situations that we thought about and handled)
* Checks to see if ImageFile exists in directory, if it does ask if it's the same, if it's not, ask user to switch name 

### Program Features
* Searchabar with regex search
* Onclose save listener 

### UI Features
* Fonts
* Colors on hover 
* process videos?

# BUGS 

## Critical Bugs
* Revision log not working as expected 
* Make sure directory doesn't get renamed when using the revert button

## Mild Bugs/Flaws 
* only set tags if ImageFile's file attribute changes. (To replicate problem, try adding tag that results in a filename
that exists in database. then save a new name... Filename will be new given name but tag will not be added but will still
show as added)

<!-- - We should implement logic that handles what happens when the user deletes a tag... ask them if they wan't to remove the tag from all their images [Done]. -->
- Methods for alerts are created in Alerts.java so they can be reused and we don't have to keep writing Alert Dialog code (since we use alert dialogs so much). chooseFileAlert(), goToDirectoryYesNo() and fileContainsTagAlert() all do the exact same thing. It'd probably be better to just have one function for that dialog
=======
* We should implement logic that handles what happens when the user deletes a tag... ask them if they wan't to remove the
tag from all their images.
* Methods for alerts are created in Alerts.java so they can be reused and we don't have to keep writing Alert Dialog code (since we use alert dialogs so much). chooseFileAlert(), goToDirectoryYesNo() and fileContainsTagAlert() all do the exact same thing. It'd probably be better to just have one function for that dialog

that takes a parameter for the body. (You can also use alertDialog.setHeader() to set the title on the dialog if you want).

About goToDirectoryYesNo(), there's was already a showYesNoAlert() method in Alerts.java and it can be used  (it's the 2nd one)

# TODO'S 

## TODO (Extras)
* Facebook share btn
* Import facebook friends as tags?
* Try increasing session load speed 
*Searchbar that waits for the user to finish typing, only search through images in preexisting query tag from all their images.

## TODO (Required) 

### Documentation & Tests
* Include good Javadoc. See the [How to Javadoc](http://www.teach.cs.toronto.edu/~csc207h/winter/lectures/javadoc.pdf) * "For more motivation, try typing javadoc */*.java" 
* Choose two interesting classes and write unit tests for them. You are not

### Code 
* Make PrimaryStageManager inherit
* update code to use TagManager through StateManager and serialize TagManager instead of just tagmanager arraylist
* Images in a directory chosen, may include tags that aren't in the tag set. Any such tags should be automatically added to the tag set.
* When viewing an image, the user can select multiple tags at once and apply them to the current image. The user can also remove tags from the image.
* The user should also be able to open (directly in their OS's file viewer) the directory containing the current image file.
** The user wants to be able to view and revert to older file names for the current image. Provided that an image has not been manually moved or renamed using the OS, the user can view all the names that a file has had. This should persist through a quit and restart.

* The user wants to be able to view and revert to older file names for the current image. Provided that an image has not been manually moved or renamed using the OS, the user can view all the names that a file has had. This should persist through a quit and restart.
* The user wants a log of all renaming ever done to all files (old name, new name, and timestamp), and this log should be viewable by the application.
Have name of image under each image in tilepane 


### Design 
* Change home screen image
* Style buttons 
* Make program look better
* Add check boxes to TagManager screen
* Make log screen more viewable

### Presentation 
* See "Presentation" section at the bottom of this file 
* Look through features at the top of this file and decide on how we 
will discuss these features during the presentaiton 
* *** Questions like "What did you want to implement but didn't have time to" .. google vision .. 	After the due date, you will present your final design and implementation in front of two TAs. An announcement with details is coming soon. You'll need to demo your program and discuss the design, including showing interesting code, and each person in the group will need to answer questions about both the code and the design. You'll use a projector.
* Make flowchart of cases handled by ImageFileOperationsManager

### Miscellaneous 
* Everyone write their own entires in duties.txt
* Add a dir of images that work with all our features
* Make UML Diagram PDF
* Add a help.txt with concise IntelliJ AND Terminal instructions
* Run program in lab computers!



#### Presentation

A Software Walkthrough is "A static analysis technique in which a designer or programmer leads members of the development team and other interested parties through a software product, and the participants ask questions and make comments about possible errors, violation of development standards, and other problems. [IEEE Std. 1028-1997, IEEE Standard for Software Reviews, clause 3.8](https://en.wikipedia.org/wiki/IEEE).

The Phase 2 marking will take place in the last two weeks of class. 

Please sign up your group for a presentation time at the following link
by writing your group number in an empty slot:

https://docs.google.com/spreadsheets/d/1sSJHHr-owbcJx9M4DTLXhk4pNCH4AtsBNPWIHnA_8as/edit?usp=sharing

You and your group will present your project to 2 TAs as if it were a software walkthrough. They may ask questions while you present, or they may wait until after the presentation. Your presentation should last no more than 10 minutes. Every group member must speak for at least two minutes.

After that, the TAs will ask you individual questions. Each of you may be asked about any of the code, including the test cases, design patterns, UML, and other implementation details.

In this presentation, you should discuss:

* A quick demo of your project
* Major design decisions made by the group
* An overview of your design, including a brief walk-through of an accurate UML diagram of your code
* A discussion of any design patterns you decided to use or not use (and why or
  why not)
* A summary of any extra features you added

You should each be aware of the basic responsibilities of each class, even the ones you did not write.
	

#### Test-Instagram Credentials
username: csc207
password: Ce*Es*Ce TwoOhSeven##
gelurig@nezdiro.org