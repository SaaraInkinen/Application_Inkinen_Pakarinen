# Application_Inkinen_Pakarinen

Course CT60A2411, BMI-calculator app 25.4.2021, Made by Saara Inkinen and Aino Pakarinen

Introduction video: https://drive.google.com/file/d/1MKokk_Tvt6_1U9yUBdsDvd_bCzYhR9J8/view?usp=sharing


## Description of the application 

The application is a BMI calculator, where the user can register to find out their Body Mass Index. The user creates an account or logs in with an existing account. The user is registered by asking email, secure password, name, age and gender. The password has to be at least 12 characters and include at least one uppercase and lowercase letter, number and special character. All this information will be saved in Firebase, where it can later be fetched from.  

After the account has been created the app goes to log in view, where the user can log in using their email and password. The user can then calculate their Body Mass Index by entering current height, weight and date. By clicking results, the app calculates the user's BMI. The app also tells if the BMI is considered underweight, normal or overweight. By clicking button “see more info”, the app goes to info activity view, which tells how many percent of the 24-64-year-olds of the same gender as the user were overweighted in the year they were born in. If the user registered their gender as “other”, the app shows both female and male percentages, because the API only has gendered data. If the user is “other”, they still get some idea what the percentage is. 

Each BMI result is saved as an entry to log, which can be read by clicking “your progress” button on the home activity page. If the user hasn’t calculated their BMI yet, the app asks the user to calculate it first. The app asks date when asking height and weight for calculating BMI so that the user can see their progress in the log.  

In every view where it’s logical, there is a title bar and a log out button. 

The application has many different features, such as log in and registering, which makes it easier to save user’s data and see it later. Having an own account also makes it more personal for the user.  


## Authors 

Together we had to learn how to use GitHub, Android Studio and Firebase. We both took part in planning the application.  

Saara did layouts for every view, except Aino did the one for registering. Saara did the layouts and transfers for home, calculator, progress and info pages. She made sure that the registering and login work properly. After we decided to use Firebase authentication, she changed the login and register to work with a valid email. She also made the buttons to transfer between layouts.  

Saara made the inputs and outputs from the screen, so we could use them. She also managed the transferring data between activities. She also toom care of possible exceptions. She complied all the code together and learnt how to use Android Studio well. 

Aino did most of the work with Firebase, such as connecting the application to Firebase’s authentication and Firestore database. Firestore cloud is used to store all the created accounts and their account details to an external database. Firebase authentication is used to make a list about every registered account’s email and UID. Firebase authentication is also used to select a sign-in method, which in our project is with email/password. This means that the user has to enter a valid email to sign in. Saara helped how to get information out of Firebase so we could use it. 

Aino did the code in Java which calculates the BMI and tells what it means. Saara connected this code as a part of the application. She also wrote the code for writing and reading user inputs as a log.  Aino wrote the document for the design turn on and final project turn in. We filmed the video together.  

Responsibilities were taken seriously during the project. Aino focused more on research and writing separate code sections where as Saara connected everything, made the layouts and polished everything on Android Studio. More specific division can be seen on workloads. 


## The implementation 
 
We used Android Studio, GitHub and Google’s Firebase on the project. We programmed the whole app in Android Studio. We shared the program to each other via GitHub and WhatsApp. We also used GitHub to do version control. We used Firebase as a database to store accounts and their information.  

We didn’t use any other libraries except for Android Studio and Java’s libraries. 

We worked efficiently as a team. We shared the tasks and worked together well. We kept a diary to see who did what, so it would be fair in the final documentation.  

We used Figma to plan the user interfaces and see how the app would look like. While making the plan we thought about how different layouts would be connected to each other through buttons.  
 

## Class diagram

https://drive.google.com/file/d/1XbxSf3s-8DkL1lBVXUUmXM1pEfb8pi6F/view?usp=sharing


## Workloads

We worked also seperately but mostly together. When we worked together, we worked 3-6 hours a day. We did the project in a span of a month. In a week we had meetings twice a week, but later on we worked more. We also reserved 13 15 minute-appointments with the assistant teachers, which helped us to keep going. We did a work diary to memorise the most important parts of the workload. The following chart is based on those notes. In reality, we worked on the project longer, because we didn’t always make an update if we didn’t finish a feature. We also didn’t count the research we had to do on our own.

in hours: https://drive.google.com/file/d/11OO6C3fF2X5cCVFBlLpCxqcZD8dkl2hf/view?usp=sharing

