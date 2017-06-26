# jwxt_crawler
## Introduction
This is a collection of crawlers for SUSTech educational administration system. It is written in Java. **Use at your own risk!**

## Dependencies
[jsoup](https://jsoup.org/): A Java HTML parser.

## Setup
Put a file named login.properties containing login information for CAS in the root directory of this project.

Sample:
```properties
id=xxxxxxxx
password=xxxxxx
```

## Runnable Modules
1. major.SearchAllMajors: List all students with their major selections.
2. grade.SearchPeopleOfClass: List grade of all students of a class (you should know the class id first).
