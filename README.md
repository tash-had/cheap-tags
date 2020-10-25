# Important
This program was done as part of an assignment for [CSC207](https://archive.is/1TLvt). I did it in a group with three others, and we used a school-run git server for the project. To try it out yourself, download the jar file [here](https://github.com/tash-had/cheap-tags/tree/master/final_jar) and open it (you must have JDK installed). 

Since this was a group project, below, I've linked the code that I wrote.

## Contributions
### src/activities
* [src/activities/HomeScreenViewController.java: Lines 103-241](https://github.com/tash-had/cheap-tags/blob/master/src/activities/HomeScreenViewController.java#L103-#L241)
* [src/activities/BrowseImageFilesViewController.java Lines 205-248](https://github.com/tash-had/cheap-tags/blob/master/src/activities/BrowseImageFilesViewController.java#L206-#L248)
* [src/activities/BrowseImageFilesViewController.java Lines 410-644](https://github.com/tash-had/cheap-tags/blob/master/src/activities/BrowseImageFilesViewController.java#L410-#L644)
### src/gui
* [src/gui/ConfigureJFXControl.java Line 1-92](https://github.com/tash-had/cheap-tags/blob/master/src/gui/ConfigureJFXControl.java#L1-#L92)
* [src/gui/Dialogs.java](https://github.com/tash-had/cheap-tags/blob/master/src/gui/Dialogs.java)
* [src/gui/PrimaryStageManager.java](https://github.com/tash-had/cheap-tags/blob/master/src/gui/PrimaryStageManager.java)
* [src/gui/StageManager.java](https://github.com/tash-had/cheap-tags/blob/master/src/gui/StageManager.java)
### src/model
* [src/model/StateManager.java](https://github.com/tash-had/cheap-tags/blob/master/src/model/StateManager.java)
* [src/model/UserImageFileData.java](https://github.com/tash-had/cheap-tags/blob/master/src/model/UserImageFileData.java)
* [src/model/UserSessionData.java](https://github.com/tash-had/cheap-tags/blob/master/src/model/UserSessionData.java)
### src/utils
* [src/utils/FileOperations.java](https://github.com/tash-had/cheap-tags/blob/master/src/utils/FileOperations.java)
* [src/utils/ImageFileOperations.java](https://github.com/tash-had/cheap-tags/blob/master/src/utils/ImageFileOperations.java)
### src/tests
* [src/tests/TestFileOperations.java](https://github.com/tash-had/cheap-tags/blob/master/src/tests/TestFileOperations.java)

## Screenshots
Import photos by choosing a previously viewed directory, a new directory, or from Instagram or Tumblr. If you choose a directoy, photos from subdirectories are also imported.
![alt text](https://github.com/tash-had/cheap-tags/blob/master/umls%20%2B%20samples/cheaptags%20samples/import%20photos.png "Import photos by choosing a previously viewed directory, a new directory, or from Instagram or Tumblr.")

Add a list of tags so you can use them on any imported set of photos
![alt text](https://github.com/tash-had/cheap-tags/blob/master/umls%20%2B%20samples/cheaptags%20samples/add%20tags.png "Add a list of tags so you can use them on any imported set of photos")

Browse your chosen directory and set tags to your images. The tag will be added to the image name, making the image OS-searchable.
![alt text](https://github.com/tash-had/cheap-tags/blob/master/umls%20%2B%20samples/cheaptags%20samples/settags.png "Browse your chosen directory and set tags to your images.")

If you have lots of images, you can use the search bar to search by image name or by regex.
![alt text](https://github.com/tash-had/cheap-tags/blob/master/umls%20%2B%20samples/cheaptags%20samples/search%20photos.png "Search for images by name or by regex")

Like a picture a lot? Use the button in the menu to share it with Instagram. Your photo tags will be added automatically to the caption.
![alt text](https://github.com/tash-had/cheap-tags/blob/master/umls%20%2B%20samples/cheaptags%20samples/instagramexample1.png "Share images with Instagram")
...![alt text](https://github.com/tash-had/cheap-tags/blob/master/umls%20%2B%20samples/cheaptags%20samples/instagramexample2.png "Photo tags are automatically added to the caption")

Want to revert a change? Undo any change you've ever made, using the revision history dialog.
![alt text](https://github.com/tash-had/cheap-tags/blob/master/umls%20%2B%20samples/cheaptags%20samples/revisionhistory.png "Revert any change ever made")
