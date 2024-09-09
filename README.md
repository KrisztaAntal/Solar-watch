# Solar-watch
Tells utc sunrise and sunset times given a date and a city name.
It connects to a database to store users, sunset and sunrise times.
Only registered users can get sunset and sunrise times.
If the connected postgresql database doesn't have the asked sunset or sunrise time it connects to external apis to retrieve and store the needed informations
(https://openweathermap.org/api - to get longitude and latitude value sending city name)
(https://sunrise-sunset.org/api - to get sunset and sunrise times sending date, longitude and latitude)

![solar-watch](https://github.com/user-attachments/assets/5a2bbe16-01af-42c4-bfbb-9843b9432e43)


## How to run
Prequisites:
Docker
Registration to https://openweathermap.org/ to obtain api key (it has a free plan up to 1000 calls/day)
Generate a JWT secret: a-32-character-ultra-secure-and-ultra-long-secret
Fill in a .env in the projects root directory as the .env.example shows
Write docker compose up in root directory of the project in the terminal 

## Used Technologies
Java spring boot - as a backend server
Postgresql - as database
Vite-React.js - as frontend

## Pages of website
### Main page 
Clicking on the SolarWatch text also redirects here

![main-page](https://github.com/user-attachments/assets/0620223c-682f-4c30-938a-3154987fc02b)

### SignUp page
If the email input is not the usual example@example.com form separated with a '@' and a '.' it shows and error.
If the repeated password dowen't match the first, it also shows an error

![sign-up-page](https://github.com/user-attachments/assets/0cf53ff0-c3c7-49ce-9bc2-2e030c10c132)

### Login page
It asks for a username and a password

![login-page](https://github.com/user-attachments/assets/37991f7c-0309-4d0b-a8f9-9d447f05ca81)

### Get time page
There are two buttons, one to get sunrise and one to get sunset.
Clicking on a button calls a from to get a date and a cityname.
A request is sent for the backend so get the required infor from the database, if not in database, it connects to external apis to get the info.
Than lists out the utc times, date and cities' informations.
On many occasion there are many cities in different countries with the same name, so it shows a list.

![solar-watch-page-base](https://github.com/user-attachments/assets/7a6dd9ab-40a3-46a4-8125-dc527a5a43d2)

![solar-watch-form-to-get-time](https://github.com/user-attachments/assets/0d2c31c2-681d-474b-8e87-9e3391d9adbb)

![solar-watch-page-times-listed](https://github.com/user-attachments/assets/acb38ea0-9015-4fe7-a6e1-98b812b3f2a1)

## Responsivity and UI
The app is fully responsive

![image 1 (6)](https://github.com/user-attachments/assets/9abb2692-36d6-44a2-8e5b-8fb43156a59a)

## Credits
I used a photo for the background from pexels
https://www.pexels.com/photo/grass-during-sunset-462023/

