LBLUE="\033[1;34m"  #1
LGREEN="\033[1;32m" #2
LRED="\033[1;31m" #3
LCYAN="\033[1;36m" #4
NC="\033[0m" #No Color

function cecho {
    if [ $1 -eq 1 ]; then
        echo -e "${LBLUE}$2${NC}"
    elif [ $1 -eq 2 ]; then
        echo -e "${LGREEN}$2${NC}"
    elif [ $1 -eq 3 ]; then
        echo -e "${LRED}$2${NC}"
    elif [ $1 -eq 4 ]; then
        echo -e "${LCYAN}$2${NC}"
    fi
}

function checkSuccess {
    if [ $1 -ne 0 ]; then
        cecho 3 "An error occurred while executing the last command"
        exit 1
    fi
}

function compilingProject() {
    cecho 2 "*  **  ***  ****  C O M P I L I N G    P R O J E C T  ****  ***  **  *"
    ./gradlew clean bootJar -x test
    checkSuccess $?
}

function startingProject() {
    cecho 2 "*  **  ***  ****  S T A R T I N G    D O C K E R    S E R V I C E S  ****  ***  **  *"
    docker compose up --build
    checkSuccess $?
}

compilingProject
startingProject