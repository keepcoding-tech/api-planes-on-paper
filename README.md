# api-planes-on-paper - REST API in Spring Boot

- API Planes on paper is an api developed specifically for "Planes On Paper", a multiplayer mobile game and represents the intermediary that allows the mobile game and the server to talk and interact with each other via HTTP requests. 

- When you use the multiplayer game application on your mobile phone, the app connects to the internet and sends data to a server. The server then retrieves that data, interprets it, performs the necessary actions, and sends it back to your phone. The mobile application then interprets that data and presents you with the information you wanted in a readable way.

- This API also provides a layer of security, the client's phone's data is never fully exposed to the server, and likewise, the server is never fully exposed to the client's phones. Instead, each communicates with small packets of data, sharing only that which is necessary.