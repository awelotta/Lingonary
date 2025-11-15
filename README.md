cs465 (user interface design) project at uiuc

Our Main Tasks:
1) Listen to a podcast
2) Peek definition and save word
3) Take a quiz

Our Main Features:
1) Tap on a word to see definition
2) Save the vocab to a word library
3) Reinforce learning through informal quizzes

## Getting Started

### Prerequisites
* **Android Studio**
* **JDK** version compatiable with the project's Gradle config (often JDK 17)
* Android SDK platforms and build tools installed (Android Studio will prompt)

### Clone the Repository
```
git clone https://github.com/awelotta/Lingonary.git
cd Lingonary
```

### Open in Android Studio
1. Open **Android Studio**
2. Select **File --> Open...** and choose the `Lingonary` folder
3. Let Gradle sync and install any required SDK components
If Gradle sync fails, check the **Build** tool window for missing SDKs, and install them from the **SDK Manager**.

### Running the App
1. In Android Studio, choose a run configuration:
* Use an existing emulator (Pixel/Nexus,etc.), or 
* Connect a physical Android device with USB debugging enabled.
2. Click the **Run** button
The launcher activity is defined in `AndroidManifest.xml`. 
