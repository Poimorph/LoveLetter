@echo off
echo ========================================
echo    Build Love Letter UTBM
echo ========================================
echo.

:: Nettoyer le dossier build
if exist build rmdir /s /q build
mkdir build\classes

echo [1/3] Compilation des fichiers Java...

:: Compiler tous les fichiers Java (sauf les tests)
javac -encoding UTF-8 -d build\classes ^
    src\Main.java ^
    src\controller\*.java ^
    src\model\*.java ^
    src\model\cartes\*.java ^
    src\ui\*.java ^
    src\ui\dialogs\*.java ^
    src\ui\views\*.java

if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERREUR] La compilation a echoue!
    pause
    exit /b 1
)

echo [2/3] Creation du manifest...

:: Creer le fichier MANIFEST
echo Manifest-Version: 1.0> build\MANIFEST.MF
echo Main-Class: Main>> build\MANIFEST.MF
echo.>> build\MANIFEST.MF

echo [3/3] Creation du JAR...

:: Creer le JAR
cd build\classes
jar cvfm ..\LoveLetter.jar ..\MANIFEST.MF *
cd ..\..

if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERREUR] La creation du JAR a echoue!
    pause
    exit /b 1
)

echo.
echo ========================================
echo    Build termine avec succes!
echo    JAR cree: build\LoveLetter.jar
echo ========================================
echo.
echo Pour lancer le jeu: java -jar build\LoveLetter.jar
echo Ou double-cliquez sur LoveLetter.jar
echo.
pause
