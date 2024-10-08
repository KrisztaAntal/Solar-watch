<a id="readme-top"></a>

<div align="center">
    <a href=https://github.com/github_KrisztaAntal/Solar-watch">
      <img src="solar_watch_log_black_stand.png" alt="Logo">
    </a>
</div>
<div align="center">  
    <p>
        The User can get UTC sunrise and sunset times for the chosen date and city name.</br>
        It lists the date, time and city-data(city, state, country) for all the cities with the given name.</br> 
        For example there is a Paris in Texas (USA), another in Kentucky (USA) and of course there is the capital of France.</br> 
        It connects to a database to store the times, cities and users.
    </p>
    <p align="center">Only registered users can get sunrise and sunset times.</p>
    <p align="center">If the connected PostgreSQL database doesn't have the asked sunset or sunrise time, it connects to external APIs to retrieve and store the needed informations.</br>
        To get longitude and latitude values for a city the site uses the <a href="https://openweathermap.org/api">OpenWeather API</a></br> 
        To retrieve sunset and sunrise times I used the <a href="https://sunrise-sunset.org/api">Sunrise Sunset API.</a></br> 
        The API needs longitude and latitude values and a date to provide the sunrise and sunset times.</p>
</div>


![solar_watch_project_page](https://github.com/user-attachments/assets/7c1dc958-8f48-49f9-91d7-ba507a443896)

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#built-with">Built With</a></li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
        <a href="#pages-of-website">Pages of website</a>
        <ul>
            <li><a href="#main-page">Main page</a></li>
            <li><a href="#signup-page">SignUp page</a></li>
            <li><a href="#login-page">Login page</a></li>
            <li><a href="#get-times-page">Get times page</a></li>
            <li><a href="#user-page">User page</a></li>
        </ul>
    </li>
    <li><a href="#responsivity-and-ui">Responsivity and UI</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

## Built with

[![JavaScript]](#) [![React][React.js]][React-url] [![CSS]](#) 

[![Java]][Java-url] [![SpringBoot]][SprinBoot-url] 

[![PostgreSQL]][Postgresql-url]

[![IntelliJ]][IntelliJ-url] [![Git]][Git-url] [![Docker]][Docker-url]  [![Figma]][Figma-url] [![Windows]][Windows-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Getting Started

### Prerequisites

* Download <a href="https://www.docker.com/products/docker-desktop/">Docker Desktop</a>
* Get a free API Key at <a href="https://openweathermap.org/price">openweathermap.org.</a> They have a free plan up to 1000 calls/day

<p align="right">(<a href="#readme-top">back to top</a>)</p>


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

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Pages of website
<div align="center">
    <h3>Main page</h3>
        <p>Clicking on the logo also redirects here.</p>
        <img src="https://github.com/user-attachments/assets/1b0a5065-0526-4efe-b321-5b7b374de8ea" alt="main-page" width="600px">
        <p align="right">(<a href="#readme-top">back to top</a>)</p>
    <h3>SignUp page</h3>
        <p>If the User puts malformed values in the input fields, or the password and repeated password doesn't match, an error message appears for the corresponding input. 
            With correct values the browser sends the new user's data to the server and it is saved in the sql database with encoded password.
        </p>
        <img src="https://github.com/user-attachments/assets/7e90d601-facd-4a4c-b923-f5c095399b15" alt="signup-page" width="600px">
        <p align="right">(<a href="#readme-top">back to top</a>)</p>
    <h3>Login page</h3>
        <p>The User needs to provide a valid username and password. If the server could not find the User with the provided credentials in the database, the User gets an error message.</p>
        <img src="https://github.com/user-attachments/assets/c9916534-50bc-466b-ac9e-9833eb187bea" alt="login-page" width="600px">
        <p align="right">(<a href="#readme-top">back to top</a>)</p>
    <h3>Get times page</h3>
        <p>There are two buttons, one to get sunrise and one to get sunset.
            Clicking on a button calls a form to select a date and a city's name.
            A request is sent for the server to get the required information from the database. If the database does not have the wanted time/times, the server connects to external apis to get the info.
            The server then sends a list to the browser with one or more elements based on how many city are there with the same name.
        </p>
        <img src="https://github.com/user-attachments/assets/305d21a6-fafd-42c7-a31b-830c81392edf" alt="get-times-page" width="600px">
        <p align="right">(<a href="#readme-top">back to top</a>)</p>
    <h3>User page</h3>
        <p>On the User page the logged in User can see and change their username and email. Clicking on the pencil icon, an input appears with the current value, the User can submit the change or step back with the "x" button.</br> 
            The password can be changed as well. Pushing the "change password" button calls a form with a "password" and a "repeated password" input like on the signup page.</br>
            Only one user data can be changed at a time.        
        </p>
        <img src="https://github.com/user-attachments/assets/5068074a-7ce0-4e5a-97db-01048226f088" alt="user-page" width="600px">
        <p align="right">(<a href="#readme-top">back to top</a>)</p>
</div>


## Responsivity and UI
The site is fully responsive.

![image 1 (4)](https://github.com/user-attachments/assets/0d352490-a63a-4bbd-acd8-56671a6bf31e)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Acknowledgments
<p>I used a photo for the background from <a href="https://www.pexels.com/photo/grass-during-sunset-462023/">Pexels</a>.</p>
<p>I found the badges for the "Built with" section <a href="https://github.com/inttter/md-badges?tab=readme-ov-file#-idecode-editors">here.</a></p>
<p align="right">(<a href="#readme-top">back to top</a>)</p>


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


