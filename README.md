# 5 O'Clock Somewhere

Finds the places in the world where it's currently between 5:00 PM and
5:59 PM, and shows you one of them.

**CS 2114 — Project 1, Group 69**
Erik Kwon, Connor Bo, Vivaan Dutt, Aidan Southwick

## What it does

The app checks 24 cities across 24 time zones against the current time,
finds the ones inside the 5 PM hour, and displays one at random with its
city, country, time zone, local time, and a fun fact. Daylight saving and
unusual offsets (such as Nepal's 45-minute offset) are handled
automatically by Java's `java.time` package.


## Setup in Eclipse

1. **File → Import → Git → Projects from Git → Clone URI**
2. URL: `https://github.com/eck0412/cs2114-project1-group69.git`
3. Sign in with your GitHub username and a personal access token
4. Select the `main` branch and choose a destination folder
5. Choose **Import existing Eclipse projects** and click Finish

## Compiling

Eclipse compiles automatically on save. To force a rebuild, use
**Project → Clean → Clean all projects**.


 
