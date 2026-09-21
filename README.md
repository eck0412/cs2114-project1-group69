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

### Switching to manual mode

Manual mode lets you supply a time and time zone yourself. It is turned on
by passing the program argument `--manual`.

1. **Run → Run Configurations…**
2. Under **Java Application**, select **FiveOClockApp**
3. Open the **Arguments** tab
4. In the **Program arguments** box, type `--manual`
5. Click **Apply**, then **Run**

The app now prompts you instead of reading the clock:

```
Enter a time (for example 5:30 PM or 17:30): 9:00 AM
Enter a time zone (for example America/New_York): America/New_York

It's five o'clock in Dubai, United Arab Emirates!
  Time zone:  Asia/Dubai
  Local time: 5:00 PM on Sunday, September 20
  Fun fact:   The Burj Khalifa is over half a mile tall.
```

Click inside the Console pane before typing, or your keystrokes go to the
editor instead.

### Switching back to automatic mode

The `--manual` argument is **saved in the run configuration**, so it keeps
applying to every later run until you remove it.

1. **Run → Run Configurations…**
2. Select **FiveOClockApp**
3. Open the **Arguments** tab
4. **Delete `--manual`** from the Program arguments box
5. Click **Apply**, then **Run**

 
