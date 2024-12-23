# Naruto-Dodge-Kunai

A challenging and fun Android game where you dodge incoming kunai using either button controls or device motion sensors! Earn points, avoid collisions, and strive for the highest score!

<p align="center">
  <img src="https://github.com/user-attachments/assets/04f4cddd-62fb-4d3a-859b-e4ef346f4ae6" alt="Game Screenshot" width="300">
</p>




## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Gameplay](#gameplay)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

"Naruto-Dodge-Kunai" is an engaging Android game developed using Java and Android Studio. The core gameplay involves controlling a character (represented by an invisible line) to dodge incoming kunai. Players can control the character either through on-screen buttons or by tilting their device, providing a flexible and dynamic gaming experience. The game also includes a scoring system, lives, and location tracking that saves the best 10 scores with a related address of the user when he was playing.

## Features

*   **Dynamic Gameplay:** Dodge falling kunai with either button controls or motion sensors.
*   **Dual Control Modes:** Choose between traditional button controls or immersive sensor-based controls.
*   **Scoring System:** Earn points for surviving and collecting coins.
*   **Lives System:**  Avoid collisions with kunai; lose a life on impact.
*   **Coin Collection:** Collect special coins for bonus points.
*   **Location Tracking:** Saves location data when the game ends for score tracking.
*   **Persistent Data:** Top 10 scores are saved and displayed with address, time, and location in a score list.
*   **Sound Effects:** Immersive background music and collision sound effects enhance the gaming experience.
*   **Vibration Feedback:** Device vibrates upon kunai impact.
*   **Clean UI:** Easy-to-understand and friendly user interface.

## Gameplay

The player controls the position of an invisible line on the screen. Kunai and coins fall from the top of the screen. The player must move the line to avoid being hit by kunai to preserve their 3 lives and to try to collect coins for additional score.

### Control Modes
*   **Button Mode:** Use left and right buttons on the screen to control the character's horizontal movement.
*   **Sensor Mode:** Tilt your device left or right to move, and tilt the device forwards or backwards to control the game's speed.

### Scoring

*   +10 points for each passed cycle (new line of falling obstacles).
*   +100 points for every coin collected.
*   -50 points for collision with kunai.

## Technologies Used

*   **Android Studio:** The primary IDE for development.
*   **Java:** The programming language used.
*   **Gradle:** Build tool.
*   **Google Maps SDK:** Displaying the score locations on the map.
*   **Android Sensor Framework:** To use the accelerometer for tilt control.
*   **SharedPreferences:** For persistent data storage of scores.
*   **Gson:** For serializing and deserializing score data.
*   **Glide:** Image loading library.
*   **MediaPlayer:** For handling background music and sound effects.
*   **Vibrator:** For vibration feedback on collisions.
*   **Location Services:** For fetching device location.

## Installation

1.  Clone the repository:

    ```bash
    git clone https://github.com/yourusername/Naruto-Dodge-Kunai.git
    ```
2.  Open the project in Android Studio.
3.  Build and run the project on an Android emulator or a physical device.

## Usage

1.  Upon launching the app, you'll be presented with a main menu.
2.  Select either **Button Mode** or **Sensor Mode** to start the game.
3.  Dodge the falling kunai, collect coins, and try to achieve the highest score.
4.  Your score will be recorded with location, date, and address.
5.  Navigate to the record screen to review your top scores and the location where each score was achieved on the Map.

