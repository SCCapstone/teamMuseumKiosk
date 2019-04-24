# teamMuseumKiosk  
## Introduction  
This project aims to create a trivia game used for the South Carolina State Museum. The museum intends for the trivia game to implore guests about their knowledge of South Carolina.  

# _For End Users_
## Tutorial Video
<a href="http://www.youtube.com/watch?feature=player_embedded&v=NpEaa2P7qZI
" target="_blank"><img src="http://img.youtube.com/vi/NpEaa2P7qZI/0.jpg" 
alt="teamMuseumKiosk Tutorial Video" width="240" height="180" border="10" /></a>
## How to Use App
* Our app has two main components: The trivia section, and the admin section.
* The application loads up on an advertisement screen, which will cycle through the current ads for the museum.
Clicking the screen will lead guests to the title screen, where they are introduced to the trivia game and can enter their initials and email. This is also where the admin screen can be accessed. 
* If the guest presses continue after entering their information, they will be welcomed to the quiz screen. Guests will go through the game, with the game increasing in difficulty as the quiz goes on. The guest has three strikes: once those three strikes have been reached, or the guest has completed the quiz the guest is greeted with the end screen. Here, the guest can look at past scores, look at their current score, and has the option to play again.
* In the admin section, the admin user is first greeted with a login screen. Once logged in, the admin can choose to add a question, edit or delete a question, or upload an advertisement. If the user clicks on the edit tab, they can choose to edit the amount of strikes in the game, the maximum number of questions used, and which high scores to display. Clicking on the 'Back to Game' button sends the user back to the title screen once again.

## Why Use Our App?
Our app was created with the needs of the South Carolina State museum in mind. The app is not created with networking capabilities to better suit the museum's uses. It also allows for images and videos to be used within the questions.

## Screenshots
![Title Screen](https://user-images.githubusercontent.com/22942073/56668371-6dc3bb80-667d-11e9-9490-a751811b9432.png "Title Screen")
Title Screen

![Quiz Screen](https://user-images.githubusercontent.com/22942073/56668381-70261580-667d-11e9-8e59-ffb28cf5147c.png "Quiz Screen")
Quiz Screen

![End Screen](https://user-images.githubusercontent.com/22942073/56668651-e4f94f80-667d-11e9-8f18-ce297103e17d.png "End Screen")
End Screen

![Admin Dashboard](https://user-images.githubusercontent.com/22942073/56668368-6bf9f800-667d-11e9-87ea-96e53e5054c9.png "Admin Dashboard")
Admin Dashboard

![Admin Edit Screen](https://user-images.githubusercontent.com/22942073/56668655-e6c31300-667d-11e9-8a36-c8b577283f11.png "Admin Edit Screen")
Admin Edit Screen


## About the Team
<table>
    <tbody>
      <tr>
        <th>Name</th>
        <th>E-mail</th>
        <th>Contact</th>
      </tr>
      <tr>
        <td>Phoebe Ngo</td>
        <td>pngo@email.sc.edu</td>
        <td><a href="https://www.linkedin.com/in/phoebe-ngo-1230ba106/">LinkedIn</a></td>
      </tr>
       <tr>
        <td>Michael Cantwell</td>
        <td>mac9@email.sc.edu</td>
        <td><a href="https://www.linkedin.com/in/michaelcantwell4/">LinkedIn</a></td>
      </tr>
       <tr>
        <td>Lauren Nix</td>
        <td>lnix@email.sc.edu</td>
        <td><a href="https://www.linkedin.com/in/lauren-nix-314074135/">LinkedIn</a></td>
      </tr>
      <tr>
        <td>Jacob Cox</td>
        <td>jbc4@email.sc.edu</td>
        <td>N/A</td>
      </tr>
      <tr>
        <td>Nicholas Gause</td>
        <td>ngause@email.sc.edu</td>
        <td>N/A</td>
      </tr>
    </tbody>
</table>

# _For Developers_
## Style Guide  
The team will be using the style guide of Google's <a href="google.github.io/styleguide/javaguide.html"> Java Style Guide</a>. 

## Run Application
* Download .jar file and TriviaQuestions.csv
* Make sure the jar and trivia files are in the same directory
* Open terminal
* Run the command:
``` java -jar teamMuseumKiosk.jar```
* To log into the Admin page - username: museumAdmin password: museumKiosk19

## Installing Development Environment (with gradle installed)
1. Install IntelliJ IDE <a href="https://www.jetbrains.com/idea/download"> here</a>. 
1. Install gradle using a package manager such as <i>Homebrew</i>
    1. ```homebrew install gradle```
1. Git clone this repo:
``` git clone https://github.com/SCCapstone/teamMuseumKiosk.git``` to directory of your choice
<b>or</b> use IntelliJ to clone using "Checkout from Version Control," selecting ```git``` and paste 
```https://github.com/SCCapstone/teamMuseumKiosk.git``` into the URL, and select directory of your choice.
1. Open project in IntelliJ and open "Terminal" tab
1. Write ```gradle build```. Wait until the building process ends and then save & close your project.
1. Reopen your project and click Auto-import, and wait while Gradle is running.

## Installing Development Environment (without gradle installed)
1. Install IntelliJ IDE <a href="https://www.jetbrains.com/idea/download"> here</a>. 
1. Git clone this repo:
``` git clone https://github.com/SCCapstone/teamMuseumKiosk.git``` to directory of your choice
<b>or</b> use IntelliJ to clone using "Checkout from Version Control," selecting ```git``` and paste 
```https://github.com/SCCapstone/teamMuseumKiosk.git``` into the URL, and select directory of your choice.
1. Open project in IntelliJ and open "Terminal" tab
1. Write ```./gradlew build```. Wait until the building process ends and then save & close your project.
1. Reopen your project and click Auto-import, and wait while Gradle is running.


## Test Application
1. Open Terminal in project directory
1. Write ```./gradlew test```, or if gradle is installed on your computer, ```gradle test```
1. View test results in ```build/reports/tests/test/index.html```
