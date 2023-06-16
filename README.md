<div align="center">

# 📗 To-do List Desktop App | Java ft MySql
![To-do list Desktop App](https://res.cloudinary.com/dlkmarlgw/image/upload/v1686919463/todo_xe8dbm.png)
</div>

To-do List is a Desktop App that uses remote method invocation(RMI) to allows you create, update, mark as complete and delete your daily tasks.

## Prerequisites

Make sure you have the following software installed on your machine:

- **Java JDK**: [Download JDK](https://www.oracle.com/java/technologies/downloads/#jdk20-windows)
- **Java SDK**: [Download SDK](https://openjfx.io/)
- **Mysql**: [Download Mysql](https://dev.mysql.com/downloads/installer/)
- **Git**: [Download Git](https://git-scm.com/downloads)

## Setup

Clone the repo

```
git clone https://github.com/AyubAli125212/TO-DO-LIST.git
```

Navigate to the created project

```
cd TO-DO-LIST
```

Navigate to NoteServer.java and run it.
Navigate to NoteTakingApp.java and run it. 

This is the database query:

```
CREATE DATABASE todo_app;
USE todo_app;

CREATE TABLE notes(
id int  primary key AUTO_INCREMENT,
note VARCHAR(255),
isCompleted boolean default false
)
```

📌Run the project from both the client folder and server folder

```
npm start
```

Congratulations 🎉 Now you have a functional To-do list App working locally.
**Do not forget to give it a star** ⭐🌟⭐ 