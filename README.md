The goal of this web application was to create a Stock Market simulation game using real stock prices and data that were pulled through a REST API. This project was 
made using the Spring MVC application framework.

How to play:<br>
1.) The user creates an account to play from.<br>
2.) The player creates a game and is given the option of it being real or mock (mock stock data due to stocks closing at 4:00PM). The player also invites other accounts to play the game. On this page the user sets the duration in days that the game will last. <br>
3.) The player is then directed to the dashboard which displays the amount of game money that the user has left as well a list of stocks with accurate prices 
to buy and a list of bought stocks and at what price these were bought at.<br>
4.) By clicking on the "Buy" or "Sell" options the user is directed to the transaction page where the user can buy or sell however many of that specific stock that 
 they want.<br>
5.) Once the user clicks "submit" on the transaction page, the user is redirected back to the game dashboard. The amount of game money that the user has is updated 
based upon wether they bought or sold stocks.<br>
6.) The game is played this way until the time that was defined when the match was created is met and each players stocks are all sold and calculated to deffine 
the winner.<br>
7.) Match history can be viewed on the users main dashboard that displays completed games and current games.<br>

How to Set up:<br>
1.) Import package into Eclipse.<br>
2.) To set up the database, use terminal to cd into the database folder and run the "create-capstone-db.sh"
3.) From here you should have everything you need so that when you run the program on a server everything should work as intended.<br>