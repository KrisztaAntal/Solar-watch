<div align="center">
    <a href=https://github.com/github_KrisztaAntal/Solar-watch">
      <img src="solar_watch_log_black_stand.png" alt="Logo">
    </a>
  
</div>

<p align="center">
The user can get UTC sunrise and sunset times for the chosen date and city name.</br>
It lists the date, time and city-data(city, state, country) for all the cities with the given name.</br> 
For example there is a Paris in Texas (USA), another in Kentucky (USA) and of course there is the capital of France.</br> 
It connects to a database to store the times, cities and users.
</p>
<p align="center">Only registered users can get sunrise and sunset times.</p>
<p align="center">If the connected PostgreSQL database doesn't have the asked sunset or sunrise time, it connects to external APIs to retrieve and store the needed informations.</br>
To get longitude and latitude values for a city the site uses the <a href="https://openweathermap.org/api">OpenWeather API</a></br> 
To retrieve sunset and sunrise times I used the <a href="https://sunrise-sunset.org/api">Sunrise Sunset API.</a></br> 
The API needs longitude and latitude values and a date to provide the sunrise and sunset times.</p>

![solar_watch_project_page](https://github.com/user-attachments/assets/7c1dc958-8f48-49f9-91d7-ba507a443896)

## Built with

[![JavaScript]](#) [![React][React.js]][React-url] [![CSS]](#) 

[![Java]][Java-url] [![SpringBoot]][SprinBoot-url] 

[![PostgreSQL]][Postgresql-url]

[![IntelliJ]][IntelliJ-url] [![Git]][Git-url] [![Docker]][Docker-url]  [![Figma]][Figma-url] [![Windows]][Windows-url]




## Getting Started

### Prerequisites

* Download <a href="https://www.docker.com/products/docker-desktop/">Docker Desktop</a>
* Get a free API Key at <a href="https://openweathermap.org/price">openweathermap.org.</a> They have a free plan up to 1000 calls/day

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/KrisztaAntal/Solar-watch.git
   ```
2. Generate a JWT secret: a-32-character-ultra-secure-and-ultra-long-secret
3. Set up environment variables like in the ```.env.example```
4. Open Docker Desktop
5. Open a Terminal, then step into the projects directory (use ```cd directory_name``` to step into directory and ```cd ..```to step backwards)
6. Write ```docker compose up --build``` in terminal
7. After the build has finished open <a href="http://localhost:5173/">http://localhost:5173/</a> in browser
8. Sign up with a new user, log in, then get the times


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
<p>I used a photo for the background from <a href="https://www.pexels.com/photo/grass-during-sunset-462023/">Pexels</a>.</p>
<p>I found the badges <a href="https://github.com/inttter/md-badges?tab=readme-ov-file#-idecode-editors">here.</a></p>



[JavaScript]: https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=000

[React.js]: https://img.shields.io/badge/React-%2320232a.svg?logo=react&logoColor=%2361DAFB
[React-url]: https://reactjs.org/

[CSS]: https://img.shields.io/badge/CSS-1572B6?logo=css3&logoColor=fff

[Java]: https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/en/

[SpringBoot]: https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white
[SprinBoot-url]: https://spring.io/projects/spring-boot/

[PostgreSQL]: https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white
[Postgresql-url]: https://www.postgresql.org/

[Figma]: https://img.shields.io/badge/Figma-F24E1E?logo=figma&logoColor=white
[Figma-url]: https://www.figma.com/

[Docker]: https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff
[Docker-url]: https://www.docker.com/

[IntelliJ]: https://img.shields.io/badge/IntelliJIDEA-000000.svg?logo=intellij-idea&logoColor=white
[IntelliJ-url]: https://www.jetbrains.com/idea/

[Git]: https://img.shields.io/badge/Git-F05032?logo=git&logoColor=fff
[Git-url]: https://git-scm.com/

[Windows]: https://custom-icon-badges.demolab.com/badge/Windows-0078D6?logo=windows11&logoColor=white
[Windows-url]: https://www.microsoft.com/en-us/windows?r=1
