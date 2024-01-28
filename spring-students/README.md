# 2.7 Практическая работа
# Консольное приложение «Учёт студентов»

Приложение запускается посредством запуска метода main в классе SpringStudentsApplication.

Для запуска в docker:

Выполнить в терминале

docker build -t spring-students .

Для работы терминала в docker команда должна быть такая

docker run -it --rm  spring-students

Для старта без инициализации студентов указать профиль, отличный от "init" (init стоит по умолчанию)

docker run -it --rm -e PROFILE=local spring-students

AVAILABLE COMMANDS

    help: Display help about available commands
    stacktrace: Display the full stacktrace of the last error.
    clear: Clear the shell screen.
    quit, exit: Exit the shell.
    history: Display or save the history of previously run commands
    version: Show version info
    script: Read and execute commands from a file.
    
    all: Get all students
    add: Add new student. Example: add --f first name --l last name --age how old is the student
    get: Get student by id. Example: get --id (student id)
    delete all: Deleted all students
    delete: Delete student by id. Example: delete --id (student id)
